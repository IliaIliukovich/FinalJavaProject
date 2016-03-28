package com.epam.periodicals.server;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface Table2Mapper {
	
	@Select("select * from table2 where user_id = #{user_id}")
	List<Table2> getTable2(Long user_id);

	@Insert("INSERT INTO table2 (user_id, journal_id) VALUES (#{user_id}, #{journal_id})")
	void addTable2(Table2 table2);

	
}
