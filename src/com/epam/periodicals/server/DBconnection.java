package com.epam.periodicals.server;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.periodicals.shared.Journal;


public class DBconnection {
	
	private static SqlSessionFactory sqlSessionFactory;
	private static Logger log = LogManager.getLogger(DBconnection.class);
	
	public static User getUser(String nameToServer, String pwdToServer) {
		Reader resourceReader;
		try {
			resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(UserMapper.class);
		} catch (IOException e) {
			log.error(e);
		}
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			return userMapper.getUser(nameToServer);
		} finally {
			session.close();
		}
	}
	
	
	public static boolean verifyUser(String nameToServer, String pwdToServer) {
		Reader resourceReader;
		try {
			resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(UserMapper.class);
		} catch (IOException e) {
			log.error(e);
		}
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

	public static boolean addUser(String nameToServer, String pwdToServer) {
		Reader resourceReader;
		try {
			resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(UserMapper.class);
		} catch (IOException e) {
			log.error(e);
		}
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


	public static List<Journal> loadJournals() {
		Reader resourceReader;
		try {
			resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(JournalMapper.class);
		} catch (IOException e) {
			log.error(e);
		}
		SqlSession session = sqlSessionFactory.openSession();
		try {
			JournalMapper journalMapper = session.getMapper(JournalMapper.class);
			return journalMapper.getJournal();
		} finally {
			session.close();
		}
	}


	public static Journal getJournalById(Long id) {
		Reader resourceReader;
		try {
			resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(JournalMapper.class);
		} catch (IOException e) {
			log.error(e);
		}
		SqlSession session = sqlSessionFactory.openSession();
		try {
			JournalMapper journalMapper = session.getMapper(JournalMapper.class);
			return journalMapper.getJournalbyId(id);
		} finally {
			session.close();
		}
	}

	
	public static void addJournal(Journal journal) {
		Reader resourceReader;
		try {
			resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(JournalMapper.class);
		} catch (IOException e) {
			log.error(e);
		}
		SqlSession session = sqlSessionFactory.openSession();
		try {
			JournalMapper journalMapper = session.getMapper(JournalMapper.class);
			journalMapper.addJournal(journal);
				session.commit();
		} finally {
			session.close();
		}
		
	}
	
	
	public static List<Table1> getTable1(Long user_id) {
		Reader resourceReader;
		try {
			resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(Table1Mapper.class);
		} catch (IOException e) {
			log.error(e);
		}
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Table1Mapper table1Mapper = session.getMapper(Table1Mapper.class);
			return table1Mapper.getTable1(user_id);
		} finally {
			session.close();
		}
		
	}
	
	public static void addTable1(Table1 table1) {
		Reader resourceReader;
		try {
			resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(Table1Mapper.class);
		} catch (IOException e) {
			log.error(e);
		}
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Table1Mapper table1Mapper = session.getMapper(Table1Mapper.class);
			table1Mapper.addTable1(table1);
			session.commit();
		} finally {
			session.close();
		}
		
	}
	
	public static void deleteTable1(Table1 table1) {
		Reader resourceReader;
		try {
			resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(Table1Mapper.class);
		} catch (IOException e) {
			log.error(e);
		}
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Table1Mapper table1Mapper = session.getMapper(Table1Mapper.class);
			table1Mapper.deleteTable1(table1);
			session.commit();
		} finally {
			session.close();
		}
	}

	
	public static List<Table2> getTable2(Long user_id) {
		Reader resourceReader;
		try {
			resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(Table2Mapper.class);
		} catch (IOException e) {
			log.error(e);
		}
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Table2Mapper table2Mapper = session.getMapper(Table2Mapper.class);
			return table2Mapper.getTable2(user_id);
		} finally {
			session.close();
		}
		
	}
	
	public static void addTable2(Table2 table2) {
		Reader resourceReader;
		try {
			resourceReader = Resources.getResourceAsReader("com/epam/periodicals/server/config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceReader);
			sqlSessionFactory.getConfiguration().addMapper(Table2Mapper.class);
		} catch (IOException e) {
			log.error(e);
		}
		SqlSession session = sqlSessionFactory.openSession();
		try {
			Table2Mapper table2Mapper = session.getMapper(Table2Mapper.class);
			table2Mapper.addTable2(table2);
			session.commit();
		} finally {
			session.close();
		}
		
	}


}

