package com.epam.periodicals.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.epam.periodicals.client.PeriodicalService;
import com.epam.periodicals.shared.Journal;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class PeriodicalServiceImpl extends RemoteServiceServlet implements PeriodicalService {
	
	private static User user = new User(0L,"unsigned","");

	@Override
	public String loginServer(boolean newUser, String nameToServer, String pwdToServer) throws IllegalArgumentException {
		
		System.out.println(nameToServer);
		System.out.println(pwdToServer);
		
		if (!newUser) {
			try {
				if (DBconnection.verifyUser(nameToServer,pwdToServer)) {
					System.out.println("signed in!");
					user = DBconnection.getUser(nameToServer, pwdToServer);
					return "Hello, " + nameToServer + " !";
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				if (DBconnection.addUser(nameToServer,pwdToServer)) {
					System.out.println("new user added!");
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
			System.out.println("List of journals loaded!");
			return list;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addNewJournals(List<Journal> journals) {
		if (user.getLogin().equals("admin")){
			System.out.println("adding new...");
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
				System.out.println("!!!");
				if (!journalById.equals(null)) {
					System.out.println("!!!!");
					Table1 table1 = new Table1(user.getId(),journalById.getId());
					DBconnection.addTable1(table1);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
	}

	@Override
	public void removeMyJournal(Long removeJournalId) {
		if (!user.getLogin().equals("admin") && !user.getLogin().equals("unsigned")) {
			System.out.println("deleting " + removeJournalId);
			try {
				Journal journalById = DBconnection.getJournalById(removeJournalId);
				if (!journalById.equals(null)) {
					DBconnection.deleteTable1(journalById.getId());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}

	@Override
	public void pay() {
		if (!user.getLogin().equals("admin") && !user.getLogin().equals("unsigned")) {
			try {
				List<Table1> table1 = DBconnection.getTable1(user.getId());
				if (!table1.equals(null)) {
					for (Table1 table : table1) {
						DBconnection.addTable2(new Table2(user.getId(),table.getUser_id()));
						DBconnection.deleteTable1(user.getId());
					}
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
	public List<Long> paidIDs() {
		if (!user.getLogin().equals("admin") && !user.getLogin().equals("unsigned")) {
			try {
				List<Table2> table2 = DBconnection.getTable2(user.getId());
				List<Long> paidIds = new ArrayList<>();
				for (Table2 i : table2) {
					paidIds.add(i.getJournal_id());
				}
				return paidIds;
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
					System.out.println(sum);
					return sum;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 0L;
	}

}
