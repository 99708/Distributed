package org.xyq.basic.dao;

import java.util.List;
import java.util.Map;

import org.xyq.basic.model.Pager;
import org.xyq.basic.model.User;
import org.xyq.basic.dao.IBasicDao;

public interface IUserDao extends IBasicDao<User> {
	
	List<User> list(String string, Object[] objects);

	List<User> list(String string, Object[] objects, Map<String, Object> alias);

	Pager<User> find(String string, Object[] objects, Map<String, Object> alias);

	List<User> listUserBySql(String string, Object[] objects, Class<User> class1,
			boolean b);

	List<User> listUserBySql(String string, Object[] objects,
			Map<String, Object> alias, Class<User> class1, boolean b);

	Pager<User> findUserBySql(String string, Object[] objects, Class<User> class1,
			boolean b);

	Pager<User> findUserBySql(String string, Object[] objects,
			Map<String, Object> alias, Class<User> class1, boolean b);

	Pager<User> find(String string, Object[] objects);

}
