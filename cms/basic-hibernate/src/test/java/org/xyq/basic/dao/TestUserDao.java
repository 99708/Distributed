package org.xyq.basic.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.xyq.basic.model.Pager;
import org.xyq.basic.model.SystemContext;
import org.xyq.basic.model.User;
import org.xyq.basic.test.util.AbstractDbUnitTestCase;
import org.xyq.basic.test.util.EntitiesHelper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestUserDao extends AbstractDbUnitTestCase{
	@Inject
	private SessionFactory sessionFactory;
	@Inject
	private IUserDao userDao;
	
	@Before
	public void setup() throws DataSetException, SQLException, IOException{
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
		this.backupAllTable();
	}
	@Test
	public void testLoad() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		System.out.println("000");
		User user = userDao.load(1);
		EntitiesHelper.assertUser(user);
	}
	@Test(expected = ObjectNotFoundException.class)
	public void testDelete() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		userDao.delete(1);
		User user = userDao.load(1);
		System.out.println(user.getUsername());
	}
	
	@Test
	public void testListByArgs() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		List<User> expected = userDao.list("from User where id>? and id<?", new Object[]{1,4}); 
		List<User> actuals = Arrays.asList(new User(2, "admin2"), new User(3, "admin3"));
		assertNotNull(expected);
		assertTrue(expected.size()==2);
		EntitiesHelper.assertUsers(expected, actuals);
	}
	@Test
	public void testListByArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		Map<String, Object> alias = new HashMap<String, Object>();
		alias.put("ids", Arrays.asList(1, 2, 3, 5, 6, 7, 8, 10));
		List<User> expected = userDao.list("from User where id>? and id<? and id in (:ids)", new Object[]{1,5}, alias); 
		List<User> actuals = Arrays.asList(new User(2, "admin2"), new User(3, "admin3"));
		assertNotNull(expected);
		assertTrue(expected.size()==2);
		EntitiesHelper.assertUsers(expected, actuals);
	}
	
	@Test
	public void testFindByArgs() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.setPagersize(3);
		SystemContext.setPageroffset(0);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		Pager<User> expected = userDao.find("from User where id>=? and id<=?", new Object[]{1,10}); 
		List<User> actuals = Arrays.asList(new User(1, "admin1"), new User(2, "admin2"), new User(3, "admin3"));
		assertNotNull(expected);
		assertTrue(expected.getTotal()==10);
		assertTrue(expected.getSize()==3);
		assertTrue(expected.getOffset()==0);
		EntitiesHelper.assertUsers(expected.getDatas(), actuals);
	}
	@Test
	public void testFindByArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.removeOrder();
		SystemContext.removeSort();
		SystemContext.setPagersize(3);
		SystemContext.setPageroffset(0);
		Map<String, Object> alias = new HashMap<String, Object>();
		alias.put("ids", Arrays.asList(1, 2, 4, 5, 6, 7, 8, 10));
		Pager<User> expected = userDao.find("from User where id>=? and id<=? and id in (:ids)", new Object[]{1,10}, alias); 
		List<User> actuals = Arrays.asList(new User(1, "admin1"), new User(2, "admin2"), new User(4, "admin4"));
		assertNotNull(expected);
		assertTrue(expected.getTotal()==8);
		assertTrue(expected.getSize()==3);
		assertTrue(expected.getOffset()==0);
		EntitiesHelper.assertUsers(expected.getDatas(), actuals);
	}
	
	@Test
	public void testListSqlByArgs() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		List<User> expected = userDao.listUserBySql("select * from t_user where id>? and id<?", new Object[]{1,4}, User.class, true); 
		List<User> actuals = Arrays.asList(new User(2, "admin2"), new User(3, "admin3"));
		assertNotNull(expected);
		assertTrue(expected.size()==2);
		EntitiesHelper.assertUsers(expected, actuals);
	}
	@Test
	public void testListSqlByArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.setOrder("asc");
		SystemContext.setSort("id");
		Map<String, Object> alias = new HashMap<String, Object>();
		alias.put("ids", Arrays.asList(1, 2, 3, 5, 6, 7, 8, 10));
		List<User> expected = userDao.listUserBySql("select * from t_user where id>? and id<? and id in (:ids)", new Object[]{1,5}, alias, User.class, true); 
		List<User> actuals = Arrays.asList(new User(2, "admin2"), new User(3, "admin3"));
		assertNotNull(expected);
		assertTrue(expected.size()==2);
		EntitiesHelper.assertUsers(expected, actuals);
	}
	@Test
	public void testFindSqlByArgs() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.setPagersize(3);
		SystemContext.setPageroffset(0);
		SystemContext.setOrder("desc");
		SystemContext.setSort("id");
		Pager<User> expected = userDao.findUserBySql("select *from t_user where id>=? and id<=?", new Object[]{1,10}, User.class, true); 
		List<User> actuals = Arrays.asList(new User(1, "admin1"), new User(2, "admin2"), new User(3, "admin3"));
		assertNotNull(expected);
		assertTrue(expected.getTotal()==10);
		assertTrue(expected.getSize()==3);
		assertTrue(expected.getOffset()==0);
		EntitiesHelper.assertUsers(expected.getDatas(), actuals);
	}
	@Test
	public void testFindSqlByArgsAndAlias() throws DatabaseUnitException, SQLException{
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
		SystemContext.removeOrder();
		SystemContext.removeSort();
		SystemContext.setPagersize(3);
		SystemContext.setPageroffset(0);
		Map<String, Object> alias = new HashMap<String, Object>();
		alias.put("ids", Arrays.asList(1, 2, 4, 5, 6, 7, 8, 10));
		Pager<User> expected = userDao.findUserBySql("select * from t_user where id>=? and id<=? and id in (:ids)", new Object[]{1,10}, alias, User.class, true); 
		List<User> actuals = Arrays.asList(new User(1, "admin1"), new User(2, "admin2"), new User(4, "admin4"));
		assertNotNull(expected);
		assertTrue(expected.getTotal()==8);
		assertTrue(expected.getSize()==3);
		assertTrue(expected.getOffset()==0);
		EntitiesHelper.assertUsers(expected.getDatas(), actuals);
	}
	
	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException{
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession(); 
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		this.resumeTable();
	}
	
}
