package org.xyq.cms.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.xyq.basic.model.Pager;
import org.xyq.basic.model.SystemContext;
import org.xyq.basic.test.util.AbstractDbUnitTestCase;
import org.xyq.basic.test.util.EntitiesHelper;
import org.xyq.cms.model.Group;
import org.xyq.cms.model.Role;
import org.xyq.cms.model.RoleType;
import org.xyq.cms.model.User;
import org.xyq.cms.model.UserGroup;
import org.xyq.cms.model.UserRole;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestUserDao extends AbstractDbUnitTestCase{
	@Inject
	private SessionFactory sessionFactory;
	@Inject
	private IUserDao userDao;
	@Inject
	private IRoleDao roleDao;
	@Inject
	private IGroupDao groupDao;
	
	@Before
	public void setup() throws SQLException, IOException, DatabaseUnitException{
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
		this.backupAllTable();
		IDataSet ds = createDateSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, ds);
	}
	
	@Test
	public void testListUserRoles() throws DatabaseUnitException, SQLException{
		List<Role> actuals = Arrays.asList(new Role(2, "文章发布人员", RoleType.ROLE_PUBLISH),new Role(3,"文章审核人员",RoleType.ROLE_AUDIT));
		List<Role> roles = userDao.listUserRoles(2);
		EntitiesHelper.assertRoles(roles, actuals);
	}
	
	@Test
	public void testListUserRoleIds() throws DatabaseUnitException, SQLException{
		List<Integer> actuals = Arrays.asList(2,3);
		List<Integer> expected = userDao.listUserRoleIds(2);
		EntitiesHelper.assertObjects(expected, actuals);
	}
	
	@Test
	public void testListUserGroups() throws DatabaseUnitException, SQLException{
		List<Group> actuals = Arrays.asList(new Group(1,"财务部"), new Group(3,"宣传部"));
		List<Group> roles = userDao.listUserGroups(3);
		EntitiesHelper.assertGroups(roles, actuals);
	}
	
	@Test
	public void testListUserGroupIds() throws DatabaseUnitException, SQLException{
		List<Integer> actuals = Arrays.asList(2,3);
		List<Integer> expected = userDao.listUserRoleIds(3);
		EntitiesHelper.assertObjects(expected, actuals);
	}
	
	@Test
	public void testLoadUserRole() throws DatabaseUnitException, SQLException{
		int uid = 1;
		int rid = 1;
		UserRole userRole = userDao.loadUserRole(uid, rid);
		User user = new User(1, "admin1", "123", "admin1", "admin1@admin.com", "10086", 1);
		Role role = new Role(1, "管理员", RoleType.ROLE_ADMIN);
		EntitiesHelper.assertUser(userRole.getUser(), user);
		EntitiesHelper.assertRole(role, userRole.getRole());
	}
	
	@Test
	public void testLoadUserGroup() throws DatabaseUnitException, SQLException{
		int uid = 2;
		int gid = 1;
		UserGroup userGroup = userDao.loadUserGroup(uid, gid);
		User user = new User(2, "admin2", "123", "admin2", "admin2@admin.com", "10086", 1);
		Group group  = new Group(1, "财务部");
		EntitiesHelper.assertUser(userGroup.getUser(), user);
		EntitiesHelper.assertGroup(group, userGroup.getGroup());
	}
	
	@Test
	public void testLoadByUsername() throws DatabaseUnitException, SQLException{
		String username = "admin1";
		User au = new User(1, "admin1", "123", "admin1", "admin1@admin.com", "10086", 1);
		User eu = userDao.loadByUsername(username);
		EntitiesHelper.assertUser(au, eu);
	}
	
	@Test
	public void testListRoleUsers() throws DatabaseUnitException, SQLException{
		int rid = 2;
		List<User> aus = Arrays.asList(new User(2, "admin2", "123", "admin2", "admin2@admin.com", "10086", 1),
				new User(3, "admin3", "123", "admin3", "admin3@admin.com", "10086", 1));
		List<User> eus = userDao.listRoleUsers(rid);
		EntitiesHelper.assertUsers(aus, eus);
	}
	
	@Test
	public void testListRoleUsersByRoleType() throws DatabaseUnitException, SQLException{
		List<User> aus = Arrays.asList(new User(2, "admin2", "123", "admin2", "admin2@admin.com", "10086", 1),
				new User(3, "admin3", "123", "admin3", "admin3@admin.com", "10086", 1));
		List<User> eus = userDao.listRoleUsers(RoleType.ROLE_PUBLISH);
		EntitiesHelper.assertUsers(aus, eus);
	}
	
	@Test
	public void testListGroupUsers() throws DatabaseUnitException, SQLException{
		int groupid = 1;
		List<User> aus = Arrays.asList(new User(2, "admin2", "123", "admin2", "admin2@admin.com", "10086", 1),
				new User(3, "admin3", "123", "admin3", "admin3@admin.com", "10086", 1));
		List<User> eus = userDao.listGroupUsers(groupid);
		EntitiesHelper.assertUsers(aus, eus);
	}
	
	@Test
	public void testAddUserRoles() throws DatabaseUnitException, SQLException{
		Role role = roleDao.load(1);
		User user = userDao.load(1);
		userDao.addUserRoles(user, role);
		UserRole ur = userDao.loadUserRole(1, 1);
		assertNotNull(ur);
		assertEquals(ur.getRole().getId(), 1);
		assertEquals(ur.getUser().getId(), 1);
	}
	
	@Test
	public void testAddUserGroups() throws DatabaseUnitException, SQLException{
		Group group = groupDao.load(1);
		User user = userDao.load(1);
		userDao.addUserGroups(user, group);
		UserGroup ug = userDao.loadUserGroup(1, 1);
		assertNotNull(ug);
		assertEquals(ug.getGroup().getId(), 1);
		assertEquals(ug.getUser().getId(), 1);
	}
	
	@Test
	public void testDeleteUserRoles() throws DatabaseUnitException, SQLException{
		int uid = 2;
		userDao.deleteUserRoles(uid);
		List<Role> roles = userDao.listUserRoles(uid);
		assertTrue(roles.size()<=0);
	}
	
	@Test
	public void testDeleteUserGroups() throws DatabaseUnitException, SQLException{
		int uid = 2;
		userDao.deleteUserGroups(uid);
		List<Group> groups = userDao.listUserGroups(uid);
		assertTrue(groups.size()<=0);
	}
	
	@Test
	public void testindUser() throws DatabaseUnitException, SQLException{
		SystemContext.setPageroffset(0);
		SystemContext.setPagersize(15);
		List<User> aus = Arrays.asList(new User(1, "admin1", "123", "admin1", "admin1@admin.com", "10086", 1),
				new User(2, "admin2", "123", "admin2", "admin2@admin.com", "10086", 1),
				new User(3, "admin3", "123", "admin3", "admin3@admin.com", "10086", 1));
		Pager<User> pagers = userDao.findUser();
		assertNotNull(pagers);
		assertEquals(pagers.getTotal(), 3);
		EntitiesHelper.assertUsers(aus, pagers.getDatas());
	}
	
	@Test(expected=AssertionError.class)
	public void testDeleteUserRole() throws DatabaseUnitException, SQLException{
		int uid = 1;
		int rid = 1;
		userDao.deleteUserRole(uid, rid);
		assertNotNull(userDao.loadUserRole(uid, rid));
	}
	
	@Test(expected=AssertionError.class)
	public void testDeleteUserGroup() throws DatabaseUnitException, SQLException{
		int uid = 1;
		int gid = 2;
		userDao.deleteUserRole(uid, gid);
		assertNotNull(userDao.loadUserGroup(uid, gid));
	}
	
	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException, SQLException{
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession(); 
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		try {
			this.resumeTable();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
