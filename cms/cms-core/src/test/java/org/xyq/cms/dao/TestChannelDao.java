package org.xyq.cms.dao;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.xyq.basic.test.util.AbstractDbUnitTestCase;
import org.xyq.basic.test.util.TestUtil;
import org.xyq.cms.model.Channel;
import org.xyq.cms.model.ChannelTree;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestChannelDao extends AbstractDbUnitTestCase{
	@Inject
	private SessionFactory sessionFactory;
	@Inject
	private IChannelDao channelDao;
	
	@Before
	public void setUp() throws SQLException, IOException, DatabaseUnitException {
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
		this.backupAllTable();
		IDataSet ds = createDateSet("t_channel");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon,ds);
	}
	
	
	@Test
	public void testListByParent() {
		List<Channel> cs = channelDao.listByParent(null);
		Assert.assertNotNull(cs);
		Assert.assertEquals(cs.size(),4);
	}

	@Test
	public void testGetMaxOrderByParent() {
		Integer max = channelDao.getMaxOrdelByParent(1);
		Assert.assertEquals(max, new Integer(4));
		max = channelDao.getMaxOrdelByParent(2);
		Assert.assertEquals(max, new Integer(0));
	}
	
	@Test
	public void testGenerateTree() {
		List<ChannelTree> cts = channelDao.generateTree();
		Assert.assertNotNull(cts);
		Assert.assertEquals(cts.size(), 21);
	}
	
	@Test
	public void testGenerateTreeByParent() {
		List<ChannelTree> cts = channelDao.generateTreeByParent(null);
		Assert.assertNotNull(cts);
		Assert.assertEquals(cts.size(), 4);
	}
	
	@Test
	public void testListPublishChannel() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		List<Channel> cs = channelDao.listPublishChannel();
		for(Channel c:cs){
			System.out.println(c.getName()+","+c.getType());
		}
		Assert.assertEquals(cs.size(), 6);
		List<Channel> ls = Arrays.asList(new Channel(2,"用户管理1"),
				new Channel(3,"用户管理2"),
				new Channel(5,"用户管理4"),
				new Channel(7,"文章管理1"),
				new Channel(8,"文章管理2"),
				new Channel(9,"文章管理3"));
		TestUtil.assertListByClz(ls, cs, Channel.class, 
				new String[]{"parent","type","customLink","customLinkUrl",
			"isIndex","isTopNav","isCommend","isStatus","orders"});
	}
	
	@After
	public void tearDown() throws DatabaseUnitException, SQLException, IOException {
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession(); 
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		this.resumeTable();
	}
	
	
}
