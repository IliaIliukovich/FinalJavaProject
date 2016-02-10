package com.epam.periodicals.server;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.epam.periodicals.shared.Journal;


public class DBconnection {
	
	private static SqlSessionFactory sqlSessionFactory;

	
	public static User getUser(String nameToServer, String pwdToServer) throws IOException {
		
		Reader resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
		sqlSessionFactory.getConfiguration().addMapper(UserMapper.class);
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			return userMapper.getUser(nameToServer);
		} finally {
			session.close();
		}
	}
	
	
	public static boolean verifyUser(String nameToServer, String pwdToServer) throws IOException {
		
		Reader resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
		sqlSessionFactory.getConfiguration().addMapper(UserMapper.class);
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			User user = userMapper.getUser(nameToServer);
			if (user != null) {
				if (user.getPassword().equals(pwdToServer)){
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
			
		} finally {
			session.close();
		}

	}

	public static boolean addUser(String nameToServer, String pwdToServer) throws IOException {

		Reader resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
		sqlSessionFactory.getConfiguration().addMapper(UserMapper.class);
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			User user = userMapper.getUser(nameToServer);
			if (user != null) {
				return false;
			} else {
				User newUser = new User(0L, nameToServer, pwdToServer);
				userMapper.createUser(newUser);
				session.commit();
				return true;
			}
			
		} finally {
			session.close();
		}
	}


	public static List<Journal> loadJournals() throws IOException {

		
		Reader resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
		sqlSessionFactory.getConfiguration().addMapper(JournalMapper.class);
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			JournalMapper journalMapper = session.getMapper(JournalMapper.class);
			return journalMapper.getJournal();
		} finally {
			session.close();
		}
	}


	public static Journal getJournalById(Long id) throws IOException {
		
		Reader resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
		sqlSessionFactory.getConfiguration().addMapper(JournalMapper.class);
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			JournalMapper journalMapper = session.getMapper(JournalMapper.class);
			return journalMapper.getJournalbyId(id);
		} finally {
			session.close();
		}
	}
	
	public static void addJournal(Journal journal) throws IOException {

		Reader resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
		sqlSessionFactory.getConfiguration().addMapper(JournalMapper.class);
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			JournalMapper journalMapper = session.getMapper(JournalMapper.class);
			journalMapper.addJournal(journal);
				session.commit();
		} finally {
			session.close();
		}
		
	}
	
	
	public static List<Table1> getTable1(Long user_id) throws IOException {

		Reader resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
		sqlSessionFactory.getConfiguration().addMapper(Table1Mapper.class);
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Table1Mapper table1Mapper = session.getMapper(Table1Mapper.class);
			return table1Mapper.getTable1(user_id);
		} finally {
			session.close();
		}
		
	}
	
	public static void addTable1(Table1 table1) throws IOException {

		Reader resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
		sqlSessionFactory.getConfiguration().addMapper(Table1Mapper.class);
		
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Table1Mapper table1Mapper = session.getMapper(Table1Mapper.class);
			table1Mapper.addTable1(table1);
				session.commit();
		} finally {
			session.close();
		}
		
	}
	
	
}

