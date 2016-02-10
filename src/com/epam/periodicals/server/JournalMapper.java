package com.epam.periodicals.server;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import com.epam.periodicals.shared.Journal;

public interface JournalMapper {

	@Select("select * from journals")
	List<Journal> getJournal();

	@Select("select * from journals where id = #{id}")
	Journal getJournalbyId(Long id);
	
	@Insert("INSERT INTO journals (name, description, price) VALUES (#{name}, #{description}, #{price})")
	void addJournal(Journal journal);
}
