package org.xyq.cms.service;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xyq.basic.model.Pager;
import org.xyq.cms.dao.IGroupDao;
import org.xyq.cms.dao.IRoleDao;
import org.xyq.cms.dao.IUserDao;
import org.xyq.cms.model.CmsException;
import org.xyq.cms.model.Group;
import org.xyq.cms.model.Role;
import org.xyq.cms.model.RoleType;
import org.xyq.cms.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-applicationContext.xml")
public class TestUserService {
	@Inject
	private IUserService userService;
	@Inject
	private IUserDao userDao;
	@Inject
	private IRoleDao roleDao;
	@Inject
	private IGroupDao groupDao;
	private User baseUser = new User(1, "admin1", "123", "admin1", "admin1@admin.com", "10086", 0);
	
	@Test
	public void testDelete(){
		reset(userDao);
		int uid = 2;
		userDao.deleteUserRoles(uid);
		expectLastCall();
		userDao.deleteUserGroups(uid);
		expectLastCall();
		userDao.delete(uid);
		expectLastCall();
		replay(userDao);
		userService.delete(uid);
		verify(userDao);
	}
	
	@Test
	public void testUpdateStatus(){
		reset(userDao);
		int uid = 2;
		expect(userDao.load(uid)).andReturn(baseUser);
		userDao.update(baseUser);
		expectLastCall();
		replay(userDao);
		userService.updateStatus(uid);
		Assert.assertEquals(baseUser.getStutas(), 1);
		verify(userDao);
	}
	
	@Test(expected=CmsException.class)
	public void testUpdateStatusNoUser(){
		reset(userDao);
		int uid = 2;
		expect(userDao.load(uid)).andReturn(null);
		userDao.update(baseUser);
		expectLastCall();
		replay(userDao);
		userService.updateStatus(uid);
		Assert.assertEquals(baseUser.getStutas(), 1);
		verify(userDao);
	}
	
	@Test
	public void testFindUser(){
		reset(userDao);
		expect(userDao.findUser()).andReturn(new Pager<User>());
		replay(userDao);
		userService.findUser();
		verify(userDao);
	}
	
	@Test
	public void testAdd1() {
		reset(userDao,roleDao,groupDao);
		Integer[] rids = {1,2};
		Integer[] gids = {2,3};
		Role r = new Role(1,"管理员",RoleType.ROLE_ADMIN);
		Group g = new Group(2,"财务处");
		expect(userDao.loadByUsername("admin1")).andReturn(null);
		expect(userDao.add(baseUser)).andReturn(baseUser);
		expect(roleDao.load(rids[0])).andReturn(r);
		userDao.addUserRoles(baseUser, r);
		expectLastCall();
		r.setId(2);
		expect(roleDao.load(rids[1])).andReturn(r);
		
		userDao.addUserRoles(baseUser, r);
		expectLastCall();
		
		expect(groupDao.load(gids[0])).andReturn(g);
		userDao.addUserGroups(baseUser, g);
		expectLastCall();
		
		r.setId(3);
		expect(groupDao.load(gids[1])).andReturn(g);
		userDao.addUserGroups(baseUser, g);
		expectLastCall();
		
		replay(userDao,roleDao,groupDao);
		userService.add(baseUser, rids, gids);
		verify(userDao,roleDao,groupDao);
	}
	
	@Test
	public void testAdd2() {
		reset(userDao,roleDao,groupDao);
		Integer[] rids = {1,2,5,6};
		Integer[] gids = {2,3,4};
		Role r = new Role(1,"管理员",RoleType.ROLE_ADMIN);
		Group g = new Group(2,"财务处");
		expect(userDao.loadByUsername("admin1")).andReturn(null);
		//添加role
		expect(userDao.add(baseUser)).andReturn(baseUser);
		//动态参数
		expect(roleDao.load(EasyMock.gt(0))).andReturn(r).times(4);
		userDao.addUserRoles(baseUser, r);
		expectLastCall().times(4);
		//添加group
		expect(groupDao.load(EasyMock.gt(0))).andReturn(g).times(3);
		userDao.addUserGroups(baseUser, g);
		expectLastCall().times(3);
		
		replay(userDao,roleDao,groupDao);
		userService.add(baseUser, rids, gids);
		verify(userDao,roleDao,groupDao);
	}
	
	@Test(expected=CmsException.class)
	public void testAddNoRole() {
		reset(userDao,roleDao,groupDao);
		Integer[] rids = {1,2,5,6};
		Integer[] gids = {2,3,4};
		Role r = new Role(1,"管理员",RoleType.ROLE_ADMIN);
		Group g = new Group(2,"财务处");
		expect(userDao.loadByUsername("admin1")).andReturn(null);
		//添加role
		expect(userDao.add(baseUser)).andReturn(baseUser);
		//动态参数
		expect(roleDao.load(EasyMock.gt(0))).andReturn(null).times(4);
		userDao.addUserRoles(baseUser, r);
		expectLastCall().times(4);
		//添加group
		expect(groupDao.load(EasyMock.gt(0))).andReturn(g).times(3);
		userDao.addUserGroups(baseUser, g);
		expectLastCall().times(3);
		
		replay(userDao,roleDao,groupDao);
		userService.add(baseUser, rids, gids);
		verify(userDao,roleDao,groupDao);
	}
	
	@Test(expected=CmsException.class)
	public void testAddNoGroup() {
		reset(userDao,roleDao,groupDao);
		Integer[] rids = {1,2,5,6};
		Integer[] gids = {2,3,4};
		Role r = new Role(1,"管理员",RoleType.ROLE_ADMIN);
		Group g = new Group(2,"财务处");
		expect(userDao.loadByUsername("admin1")).andReturn(null);
		//添加role
		expect(userDao.add(baseUser)).andReturn(baseUser);
		//动态参数
		expect(roleDao.load(EasyMock.gt(0))).andReturn(r).times(4);
		userDao.addUserRoles(baseUser, r);
		expectLastCall().times(4);
		//添加group
		expect(groupDao.load(EasyMock.gt(0))).andReturn(null).times(3);
		userDao.addUserGroups(baseUser, g);
		expectLastCall().times(3);
		
		replay(userDao,roleDao,groupDao);
		userService.add(baseUser, rids, gids);
		verify(userDao,roleDao,groupDao);
	}
	
	
	@Test(expected=CmsException.class)
	public void testAddHasUser() {
		reset(userDao,roleDao,groupDao);
		Integer[] rids = {1,2,5,6};
		Integer[] gids = {2,3,4};
		expect(userDao.loadByUsername("admin1")).andReturn(baseUser);
		
		replay(userDao,roleDao,groupDao);
		userService.add(baseUser, rids, gids);
		verify(userDao,roleDao,groupDao);
	}
	
	@Test
	public void testUpdateUser() {
		reset(userDao,roleDao,groupDao);
		Integer[] nids = {1,2};
		List<Integer> eids = Arrays.asList(2,3);
		Role r = new Role();
		Group g = new Group();
		
		//验证获取存在的角色id和组id
		expect(userDao.listUserRoleIds(baseUser.getId())).andReturn(eids);
		expect(userDao.listUserGroupIds(baseUser.getId())).andReturn(eids);
		expect(roleDao.load(1)).andReturn(r);
		//验证添加角色和组是否正确
		userDao.addUserRoles(baseUser, r);
		expectLastCall();
		expect(groupDao.load(1)).andReturn(g);
		userDao.addUserGroups(baseUser, g);
		
		//验证删除角色和组是否正确
		userDao.deleteUserRole(baseUser.getId(), 3);
		userDao.deleteUserGroup(baseUser.getId(), 3);
		
		replay(userDao,roleDao,groupDao);
		userService.update(baseUser, nids, nids);
		
		verify(userDao,roleDao,groupDao);
	}
}
