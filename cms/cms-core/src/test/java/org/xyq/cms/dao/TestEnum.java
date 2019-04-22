package org.xyq.cms.dao;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.xyq.basic.test.util.EntitiesHelper;
import org.xyq.basic.util.EnumUtils;
import org.xyq.cms.model.ChannelType;
import org.xyq.cms.model.RoleType;

public class TestEnum {

	@Test
	public void testEnumList() {
		List<String> actuals = Arrays.asList("ROLE_ADMIN","ROLE_PUBLISH","ROLE_AUDIT");
		List<String> expectes = EnumUtils.enum2Name(RoleType.class);
		EntitiesHelper.assertObjects(expectes, actuals);
	}
	@Test
	public void testEnumUtils(){
		System.out.println(EnumUtils.enumProp2List(ChannelType.class, "name"));
		System.out.println(EnumUtils.enumProp2BasicMap(ChannelType.class, "name"));
		System.out.println(EnumUtils.enumProp2NameMap(ChannelType.class, "name"));

	}
	
}
