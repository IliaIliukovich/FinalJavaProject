package com.epam.periodicals.server;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface Table1Mapper {
	
	@Select("select * from table1 where user_id = #{user_id}")
	List<Table1> getTable1(Long user_id);

	@Insert("INSERT INTO table1 (user_id, journal_id) VALUES (#{user_id}, #{journal_id})")
	void addTable1(Table1 table1);
	
	@Delete("DELETE FROM table1 WHERE user_id =#{user_id}")
	void deleteTable1(Long user_id);
	
}
