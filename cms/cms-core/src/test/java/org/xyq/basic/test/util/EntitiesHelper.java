package org.xyq.basic.test.util;

import java.util.List;

import junit.framework.Assert;

import org.xyq.cms.model.Channel;
import org.xyq.cms.model.Group;
import org.xyq.cms.model.Role;
import org.xyq.cms.model.User;


public class EntitiesHelper {
	private static User baseUser = new User(1,"admin1","123","admin1","admin1@admin.com","10086",1);
	
	public static void assertUser(User expected,User actual) {
		Assert.assertNotNull(expected);
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getUsername(), actual.getUsername());
//		Assert.assertEquals(expected.getPassword(), actual.getPassword());
		Assert.assertEquals(expected.getNickname(), actual.getNickname());
		Assert.assertEquals(expected.getEmail(), actual.getEmail());
		Assert.assertEquals(expected.getStutas(), actual.getStutas());
	}
	
	public static void assertUsers(List<User> expected,List<User> actuals) {
		for(int i=0;i<expected.size();i++) {
			User eu = expected.get(i);
			User au = actuals.get(i);
			assertUser(eu, au);
		}
	}
	
	public static void assertRole(Role expected,Role actual) {
		Assert.assertNotNull(expected);
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getRoleType(), actual.getRoleType());
	}
	
	public static void assertRoles(List<Role> expected,List<Role> actuals) {
		for(int i=0;i<expected.size();i++) {
			Role er = expected.get(i);
			Role ar = actuals.get(i);
			assertRole(er, ar);
		}
	}
	
	public static void assertObjects(List<?> expected,List<?> actuals) {
		for(int i=0;i<expected.size();i++) {
			Object er = expected.get(i);
			Object ar = actuals.get(i);
			Assert.assertEquals(er, ar);
		}
	}
	
	
	public static void assertGroup(Group expected,Group actual) {
		Assert.assertNotNull(expected);
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getName(), actual.getName());
	}
	
	public static void assertGroups(List<Group> expected,List<Group> actuals) {
		for(int i=0;i<expected.size();i++) {
			Group eg = expected.get(i);
			Group ag = actuals.get(i);
			assertGroup(eg, ag);
		}
	}
	public static void assertChannel(Channel expected,Channel actual) throws Exception{
		TestUtil.assertObjByClz(expected, actual, Channel.class, new String[]{"parent"});
	}
	public static void assertUser(User expected) {
		assertUser(expected, baseUser);
	}
}
