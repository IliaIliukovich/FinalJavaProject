package com.epam.periodicals.client;

import java.util.List;

import com.epam.periodicals.shared.Journal;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface PeriodicalService extends RemoteService {
	
	// client to server
	String loginServer(boolean newUser, String nameToServer, String pwdToServer);
	
	boolean signoutServer();
	
	void addNewJournals(List<Journal> journals);
	
	void addMyJournal(Long journalId);

	void deleteMyJournal(Long journalId);
	
	void pay(Long sum);

	// server to client
	List<Journal> loadData(); 

	List<Long> selectedIDs(); 

	List<Long> paidIDs(); 

	Long sumToPay(); 

	
	
	
	
}
