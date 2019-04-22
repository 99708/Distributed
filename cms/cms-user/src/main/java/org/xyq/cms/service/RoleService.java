package org.xyq.cms.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.xyq.cms.dao.IRoleDao;
import org.xyq.cms.dao.IUserDao;
import org.xyq.cms.model.CmsException;
import org.xyq.cms.model.Role;
import org.xyq.cms.model.User;

@Service("roleService")
public class RoleService implements IRoleService {
	private IRoleDao roleDao;
	private IUserDao userDao;
	
	public IRoleDao getRoleDao() {
		return roleDao;
	}

	@Inject
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}
	
	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void add(Role role) {
		roleDao.add(role);
	}

	@Override
	public void delete(int id) {
		List<User> users = userDao.listRoleUsers(id);
		if(users != null && users.size()>0){
			throw new CmsException("你删除的角色中还有用户，请检查你的操作");
		}else{
			roleDao.delete(id);
		}
	}

	@Override
	public void update(Role role) {
		roleDao.update(role);
	}

	@Override
	public Role load(int id) {
		return roleDao.load(id);
	}

	@Override
	public List<Role> listRole() {
		return roleDao.listRole();
	}

	@Override
	public void deleteRoleUser(int rid) {
		roleDao.deleteRoleUsers(rid);
	}

	

}
