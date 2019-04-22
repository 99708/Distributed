package org.xyq.cms.service;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xyq.basic.model.Pager;
import org.xyq.cms.dao.IGroupDao;
import org.xyq.cms.dao.IUserDao;
import org.xyq.cms.model.CmsException;
import org.xyq.cms.model.Group;
import org.xyq.cms.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-applicationContext.xml")
public class TestGroupService {
	@Inject
	private IGroupService groupService;
	@Inject
	private IUserDao userDao;
	@Inject
	private IGroupDao groupDao;
	private Group baseGroup = new Group(1,"财务部");

	@Test
	public void testAdd() {
		reset(groupDao);
		expect(groupDao.add(baseGroup)).andReturn(baseGroup);
		replay(groupDao);
		groupService.add(baseGroup);
		verify(groupDao);
	}

	@Test
	public void testDeleteNoUsers() {
		reset(groupDao,userDao);
		int gid = 1;
		expect(userDao.listGroupUsers(gid)).andReturn(null);
		groupDao.delete(gid);
		expectLastCall();
		replay(groupDao,userDao);
		groupService.delete(gid);
		verify(groupDao,userDao);
	}
	
	@Test(expected=CmsException.class)
	public void testDeleteHasUsers() {
		reset(groupDao,userDao);
		int gid = 1;
		List<User> us = Arrays.asList(new User());
		expect(userDao.listGroupUsers(gid)).andReturn(us);
		groupDao.delete(gid);
		expectLastCall();
		replay(groupDao,userDao);
		groupService.delete(gid);
		verify(groupDao,userDao);
	}

	@Test
	public void testLoad() {
		reset(groupDao);
		int id = 1;
		expect(groupDao.load(id)).andReturn(baseGroup);
		replay(groupDao);
		groupService.load(id);
		verify(groupDao);
	}

	@Test
	public void testUpdate() {
		reset(groupDao);
		groupDao.update(baseGroup);
		expectLastCall();
		replay(groupDao);
		groupService.update(baseGroup);
		verify(groupDao);
	}

	@Test
	public void testListGroup() {
		reset(groupDao);
		expect(groupDao.listGroup()).andReturn(new ArrayList<Group>());
		expectLastCall();
		replay(groupDao);
		groupService.listGroup();
		verify(groupDao);
	}

	@Test
	public void testFindGroup() {
		reset(groupDao);
		expect(groupDao.findGroup()).andReturn(new Pager<Group>());
		expectLastCall();
		replay(groupDao);
		groupService.findGroup();
		verify(groupDao);
	}

	@Test
	public void testDeleteGroupUsers() {
		reset(groupDao);
		int gid = 1;
		groupDao.deleteGroupUsers(gid);
		expectLastCall();
		replay(groupDao);
		groupService.deleteGroupUser(gid);
		verify(groupDao);
	}

}
