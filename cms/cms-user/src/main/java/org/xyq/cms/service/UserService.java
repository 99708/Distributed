package org.xyq.cms.service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;
import org.xyq.basic.model.Pager;
import org.xyq.basic.util.SecurityUtil;
import org.xyq.cms.dao.IGroupDao;
import org.xyq.cms.dao.IRoleDao;
import org.xyq.cms.dao.IUserDao;
import org.xyq.cms.model.CmsException;
import org.xyq.cms.model.Group;
import org.xyq.cms.model.Role;
import org.xyq.cms.model.User;

@Service("userService")
public class UserService implements IUserService {
	
	private IUserDao userDao;
	private IRoleDao roleDao;
	private IGroupDao groupDao;

	public IUserDao getUserDao() {
		return userDao;
	}
	
	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	@Inject
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public IGroupDao getGroupDao() {
		return groupDao;
	}

	@Inject
	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	private void addRoles(User user, int rid){
		//1、检查角色对象是否存在，如果不存在就抛出异常
		Role role = roleDao.load(rid);
		if(role == null) throw new CmsException("你添加的用户角色不存在");
		//2、检查用户角色对象是否存在，如果存在，就不添加
		userDao.addUserRoles(user, role);
	}
	
	private void addGroups(User user, int gid){
		//1、检查角色对象是否存在，如果不存在就抛出异常
		Group group = groupDao.load(gid);
		if(group == null) throw new CmsException("你添加的用户组不存在");
		//2、检查用户角色对象是否存在，如果存在，就不添加
		userDao.addUserGroups(user, group);
	}
	@Override
	public void add(User user, Integer[] rids, Integer[] gids) {
		User tu = userDao.loadByUsername(user.getUsername());
		if(tu != null)throw new CmsException("你添加的用户已存在，不能重复添加");
		user.setCreatDate(new Date());
		try {
			user.setPassword(SecurityUtil.md5(user.getUsername(),user.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			throw new CmsException("密码加密失败："+e.getMessage());
		}
		userDao.add(user);
		//添加角色对象
		for(Integer rid : rids){
			addRoles(user, rid);
		}
		//添加用户组对象
		for(Integer gid : gids){
			addGroups(user, gid);
		}
	}

	@Override
	public void delete(int id) {
		//TODO 需要判断用户是否有文章
		//1、删除用户关联的角色对象
		userDao.deleteUserRoles(id);
		//2、删除用户关联的用户组对象
		userDao.deleteUserGroups(id);
		userDao.delete(id);
	}

	@Override
	public void update(User user, Integer[] rids, Integer[] gids) {
		//1、获取用户已经存在的组id角色id
		List<Integer> erids = userDao.listUserRoleIds(user.getId());
		List<Integer> egids = userDao.listUserGroupIds(user.getId());
		//2、增加
		//2.1、如果erids中不存在rids添加用户角色对象
		for (Integer rid:rids){
			if(!erids.contains(rid)){
				addRoles(user, rid);
			}
		}
		//2.2、如果egids中不存在gids添加用户组对象
		for(Integer gid:gids){
			if(!egids.contains(gid)){
				addGroups(user, gid);
			}
		}
		//3、删除
		for(Integer erid:erids){
			if(!ArrayUtils.contains(rids, erid)){
				userDao.deleteUserRole(user.getId(), erid);
			}
		}
		for(Integer egid:egids){
			if(!ArrayUtils.contains(gids, egid)){
				userDao.deleteUserGroup(user.getId(), egid);
			}
		}
	}

	@Override
	public void updateStatus(int id) {
		User user = userDao.load(id);
		if(user == null)throw new CmsException("修改的用户不存在");
		if(user.getStutas() == 0) user.setStutas(1);
		else user.setStutas(0);
		userDao.update(user);
	}

	@Override
	public Pager<User> findUser() {
		return userDao.findUser();

	}

	@Override
	public User load(int id) {
		return userDao.load(id);
	}

	@Override
	public List<Role> listUserRoles(int id) {
		return userDao.listUserRoles(id);
	}

	@Override
	public List<Group> listUserGroups(int id) {
		return userDao.listUserGroups(id);
	}

	@Override
	public List<Integer> listUserRoleIds(int id) {
		return userDao.listUserRoleIds(id);
	}

	@Override
	public List<Integer> listUserGroupIds(int id) {
		return userDao.listUserGroupIds(id);
	}

	@Override
	public List<User> listGroupUsers(int gid) {
		return userDao.listGroupUsers(gid);
	}

	@Override
	public List<User> listRoleUsers(int rid) {
		return userDao.listRoleUsers(rid);
	}
	@Override
	public User login(String username,String password){
		User user = userDao.loadByUsername(username);
		if(user == null){
			throw new CmsException("用户名或密码不正确");
		}
		try {
			if(!SecurityUtil.md5(username, password).equals(user.getPassword())){
				throw new CmsException("用户名或密码不正确");
			}
		} catch (NoSuchAlgorithmException e) {
			throw new CmsException("密码加密失败："+e.getMessage());
		}
		if(user.getStutas()== 0) throw new CmsException("用户已经停用，请与管理员联系");
		return user;
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void updatePwd(int uid, String oldPwd, String newPwd) {
		User user = userDao.load(uid);
		try {
			if(!SecurityUtil.md5(user.getUsername(), oldPwd).equals(user.getPassword())){
				throw new CmsException("原始密码不正确");
			}
			user.setPassword(SecurityUtil.md5(user.getUsername(), newPwd));
			userDao.update(user);
		} catch (NoSuchAlgorithmException e) {
			throw new CmsException("更新密码失败"+e.getMessage());
		}
	}
}
