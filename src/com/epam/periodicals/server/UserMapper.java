package com.epam.periodicals.server;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

	@Select("select * from user where login = #{login}")
	User getUser(String login);
	
	@Insert("INSERT INTO user (login, password) VALUES (#{login}, #{password})")
	void createUser(User user);
	
}