package com.epam.periodicals.server;

public class Table2 {

	private Long user_id;
	private Long journal_id;
	
	
	public Table2() {
	}
	
	public Table2(Long id_user, Long id_journal) {
		this.user_id = id_user;
		this.journal_id = id_journal;
	}


	public Long getUser_id() {
		return user_id;
	}


	public void setUser_id(Long id_user) {
		this.user_id = id_user;
	}


	public Long getJournal_id() {
		return journal_id;
	}


	public void setJournal_id(Long id_journal) {
		this.journal_id = id_journal;
	}
	
}
