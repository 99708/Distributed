package org.xyq.cms.service;

import java.util.List;

import org.xyq.basic.model.Pager;
import org.xyq.cms.model.Group;
import org.xyq.cms.model.Role;
import org.xyq.cms.model.User;

public interface IUserService {
	/**
	 * 添加用户，需要判断用户是否存在，如果存在，抛出异常
	 * @param user 用户对象
	 * @param rids 用户的所有角色信息
	 * @param gids 用户的所有组信息
	 */
	public void add(User user, Integer[]rids, Integer[]gids);
	
	/**
	 * 删除用户，需要把用户和组和角色的关系删除，如果用户存在相应的文章不能删除
	 * @param id
	 */
	public void delete(int id);
	
	/**
	 * 更新用户信息，如果rids中的角色在用户中已经存在，就不做操作，不存在就要添加
	 * 如果用户角色不存在rids中，需要删除，对Group也一样
	 * @param user
	 * @param rids
	 * @param gids
	 */
	public void update(User user, Integer[]rids, Integer[]gids);
	public void update(User user);
	/**
	 * 更新密码
	 * @param uid
	 * @param oldPwd
	 * @param newPwd
	 */
	public void updatePwd(int uid, String oldPwd, String newPwd);
	/**
	 * 更新用户状态
	 * @param id
	 */
	public void updateStatus(int id);
	/**
	 * 列表用户
	 */
	public Pager<User> findUser();
	/**
	 * 获取用户信息
	 * @param id
	 */
	public User load(int id);
	/**
	 * 获取用户的所有角色信息
	 * @param id
	 * @return
	 */
	public List<Role> listUserRoles(int id);
	/**
	 * 获取用户的所有组信息
	 * @param id
	 * @return
	 */
	public List<Group> listUserGroups(int id);
	/**
	 * 获取用户的所有角色id
	 * @param id
	 * @return
	 */
	public List<Integer> listUserRoleIds(int id);
	/**
	 * 获取用户的所有组id
	 * @param id
	 * @return
	 */
	public List<Integer> listUserGroupIds(int id);
	
	public List<User> listGroupUsers(int gid);
	public List<User> listRoleUsers(int rid);
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username, String password);
	
	
}