package com.epam.periodicals.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.epam.periodicals.client.PeriodicalService;
import com.epam.periodicals.shared.Journal;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


@SuppressWarnings("serial")
public class PeriodicalServiceImpl extends RemoteServiceServlet implements PeriodicalService {
	
	private static User user = new User(0L,"unsigned","");

	@Override
	public String loginServer(boolean newUser, String nameToServer, String pwdToServer) throws IllegalArgumentException {
		
		if (!newUser) {
			try {
				if (DBconnection.verifyUser(nameToServer,pwdToServer)) {
					user = DBconnection.getUser(nameToServer, pwdToServer);
					return "Hello, " + nameToServer + " !";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				if (DBconnection.addUser(nameToServer,pwdToServer)) {
					user = DBconnection.getUser(nameToServer, pwdToServer);
					return "Hello, " + nameToServer + " !";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		throw new IllegalArgumentException();
	}

	@Override
	public List<Journal> loadData() {
		try {
			List<Journal> list = DBconnection.loadJournals();
			return list;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addNewJournals(List<Journal> journals) {
		if (user.getLogin().equals("admin")){
			for (Journal journal : journals) {
				System.out.println(journal.getName());
				try {
					DBconnection.addJournal(journal);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	@Override
	public void addMyJournal(Long journalId) {
		if (!user.getLogin().equals("admin") && !user.getLogin().equals("unsigned")) {
			System.out.println("adding " + journalId);
			try {
				Journal journalById = DBconnection.getJournalById(journalId);
				if (!journalById.equals(null)) {
					Table1 table1 = new Table1(user.getId(),journalById.getId());
					DBconnection.addTable1(table1);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
	}

	

	@Override
	public List<Long> selectedIDs() {
			
		if (!user.getLogin().equals("admin") && !user.getLogin().equals("unsigned")) {
			try {
				List<Table1> table1 = DBconnection.getTable1(user.getId());
				List<Long> selectedIds = new ArrayList<>();
				for (Table1 i : table1) {
					selectedIds.add(i.getJournal_id());
				}
				return selectedIds;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	@Override
	public Long sumToPay() {
		if (!user.getLogin().equals("admin") && !user.getLogin().equals("unsigned")) {
			try {
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
					return sum;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0L;
	}

	@Override
	public void signoutServer() {
		user.setId(0L);
		user.setLogin("unsigned");
		user.setPassword("");
	}

}
