package org.xyq.cms.dao;

import java.util.List;

import org.xyq.basic.dao.IBasicDao;
import org.xyq.cms.model.Role;

public interface IRoleDao extends IBasicDao<Role> {
	public List<Role> listRole();
	public void deleteRoleUsers(int rid);

}
