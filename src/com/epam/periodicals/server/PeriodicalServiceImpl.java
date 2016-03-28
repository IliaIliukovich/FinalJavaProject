package com.epam.periodicals.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.periodicals.client.PeriodicalService;
import com.epam.periodicals.shared.Journal;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


@SuppressWarnings("serial")
public class PeriodicalServiceImpl extends RemoteServiceServlet implements PeriodicalService {
	
	private static Logger log = LogManager.getLogger(PeriodicalServiceImpl.class);
    
	
	private static User user = new User(0L,"unsigned","");

	
	@Override
	public String loginServer(boolean newUser, String nameToServer, String pwdToServer) throws IllegalArgumentException {
		if (!newUser) {
			if (DBconnection.verifyUser(nameToServer,pwdToServer)) {
				user = DBconnection.getUser(nameToServer, pwdToServer);
				log.info("Hello, " + nameToServer + " !");
				return "Hello, " + nameToServer + " !";
			}
		} else {
			if (DBconnection.addUser(nameToServer,pwdToServer)) {
				user = DBconnection.getUser(nameToServer, pwdToServer);
				log.info("Hello, " + nameToServer + " !");
				return "Hello, " + nameToServer + " !";
			}
		}
		throw new IllegalArgumentException();
	}
	
	
	@Override
	public boolean signoutServer() {
		user.setId(0L);
		user.setLogin("unsigned");
		user.setPassword("");
		log.info("signed out");
		return true;
	}

	
	@Override
	public List<Journal> loadData() {
		List<Journal> list = DBconnection.loadJournals();
		log.info("Journals loaded from DB");
		return list;
	}

	
	@Override
	public void addNewJournals(List<Journal> journals) {
		if (user.getLogin().equals("admin")){
			for (Journal journal : journals) {
				log.info(journal.getName());
				DBconnection.addJournal(journal);
				log.info("journal " + journal.getName() + " added to DB");
			}
		}
	}

	
	@Override
	public void addMyJournal(Long journalId) {
		if (!user.getLogin().equals("admin") && !user.getLogin().equals("unsigned")) {
			log.info("adding My journal by id = " + journalId);
			Journal journalById = DBconnection.getJournalById(journalId);
			if (!journalById.equals(null)) {
				log.info("adding " + journalById.getName());
				Table1 table1 = new Table1(user.getId(),journalById.getId());
				DBconnection.addTable1(table1);
				log.info("success!");
			}
		}
			
	}

	
	@Override
	public void deleteMyJournal(Long journalId) {
		if (!user.getLogin().equals("admin") && !user.getLogin().equals("unsigned")) {
			log.info("deleting My journal by id = " + journalId);
			DBconnection.deleteTable1(new Table1(user.getId(),journalId));
			log.info("success!");
		}
	}

	
	@Override
	public List<Long> selectedIDs() {
			
		if (!user.getLogin().equals("admin") && !user.getLogin().equals("unsigned")) {
			List<Table1> table1 = DBconnection.getTable1(user.getId());
			List<Long> selectedIds = new ArrayList<>();
			for (Table1 i : table1) {
				selectedIds.add(i.getJournal_id());
			}
			log.info("got selected Ids from DB");
			return selectedIds;
		}
		return null;
	}

	
	@Override
	public List<Long> paidIDs() {
		if (!user.getLogin().equals("admin") && !user.getLogin().equals("unsigned")) {
			List<Table2> table2 = DBconnection.getTable2(user.getId());
			List<Long> paidIds = new ArrayList<>();
			for (Table2 i : table2) {
				paidIds.add(i.getJournal_id());
			}
			log.info("got paid Ids from DB");
			return paidIds;
		}
		return null;
	}
	
	
	@Override
	public Long sumToPay() {
		if (!user.getLogin().equals("admin") && !user.getLogin().equals("unsigned")) {
			List<Table1> table1 = DBconnection.getTable1(user.getId());
			if (!table1.equals(null)) {
				List<Journal> journalList = new ArrayList<>();
				for (Table1 i : table1) {
					journalList.add(DBconnection.getJournalById(i.getJournal_id()));
				}
				Long sum = 0L;
				for (Journal journal : journalList) {
					sum += journal.getPrice();
				}
				log.info("got sum to pay");
				return sum;
			}
		}
		return 0L;
	}

	
	@Override
	public void pay(Long sum) {
		if (!user.getLogin().equals("admin") && !user.getLogin().equals("unsigned")) {
			log.info("paying " + sum + " RUB");
			log.info(" sumToPay " + sumToPay());
			if (sum.equals(sumToPay())) {
				List<Long> selectedJournals = selectedIDs();
				for (Long id : selectedJournals) {
					DBconnection.addTable2(new Table2(user.getId(),id));
					DBconnection.deleteTable1(new Table1(user.getId(), id));
				}
				log.info("success !");
			}
		}
	}


}
