package com.epam.periodicals.client;

import java.util.List;

import com.epam.periodicals.shared.Journal;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PeriodicalServiceAsync {
	
	void loginServer(boolean newUser, String nameToServer, String pwdToServer, AsyncCallback<String> callback);

	void loadData(AsyncCallback<List<Journal>> callback);

	void addNewJournals(List<Journal> journals, AsyncCallback<Void> callback);



	void pay(AsyncCallback<Void> callback);

	void selectedIDs(AsyncCallback<List<Long>> callback);

	void paidIDs(AsyncCallback<List<Long>> callback);

	void sumToPay(AsyncCallback<Long> callback);

	void addMyJournal(Long journalId, AsyncCallback<Void> callback);

	void removeMyJournal(Long removeJournalId, AsyncCallback<Void> callback);
}
