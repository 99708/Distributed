package org.xyq.cms.dao;

import java.util.List;

import org.xyq.basic.dao.IBasicDao;
import org.xyq.basic.model.Pager;
import org.xyq.cms.model.Group;
import org.xyq.cms.model.Role;
import org.xyq.cms.model.RoleType;
import org.xyq.cms.model.User;
import org.xyq.cms.model.UserGroup;
import org.xyq.cms.model.UserRole;


public interface IUserDao extends IBasicDao<User>{
	/**
	 * 获取用户的所有角色信息
	 * @param userid
	 * @return
	 */
	public List<Role> listUserRoles(int userid);
	/**
	 * 获取用户的所有角色id
	 * @param userid
	 * @return
	 */
	public List<Integer> listUserRoleIds(int userid);
	/**
	 * 获取用户的所有组信息
	 * @param userid
	 * @return
	 */
	public List<Group> listUserGroups(int userid);
	/**
	 * 获取用户的所有组id
	 * @param userid
	 * @return
	 */
	public List<Integer> listUserGroupIds(int userid);
	/**
	 * 根据用户id和组id获取用户组对象
	 * @param userid
	 * @param roleid
	 * @return
	 */
	public UserRole loadUserRole(int userid, int roleid);
	/**
	 * 根据用户id和角色id获取用户角色对象
	 * @param userid
	 * @param groupid
	 * @return
	 */
	public UserGroup loadUserGroup(int userid, int groupid);
	/**
	 * 根据用户名获取用户对象
	 * @param username
	 * @return
	 */
	public User loadByUsername (String username);
	/**
	 * 根据角色id获取用户列表
	 * @param roleid
	 * @return
	 */
	public List<User> listRoleUsers (int roleid);
	/**
	 * 根据角色类型获取用户列表
	 * @param roleType
	 * @return
	 */
	public List<User> listRoleUsers (RoleType roleType);
	/**
	 * 根据组id获取用户列表
	 * @param groupid
	 * @return
	 */
	public List<User> listGroupUsers (int groupid);
	
	/**
	 * 添加用户角色对象
	 * @param user
	 * @param role
	 */
	public void addUserRoles(User user, Role role);
	/**
	 * 添加用户组对象
	 * @param user
	 * @param group
	 */
	public void addUserGroups(User user, Group group);
	
	/**
	 * 删除用户角色对象
	 * @param user
	 * @param role
	 */
	public void deleteUserRoles(int uid);
	/**
	 * 删除用户组对象
	 * @param user
	 * @param group
	 */
	public void deleteUserGroups(int uid);
	
	public Pager<User> findUser();
	
	/**
	 * 删除用户角色对象
	 * @param uid
	 * @param rid
	 */
	public void deleteUserRole(int uid, int rid);
	/**
	 * 删除用户组对象
	 * @param uid
	 * @param gid
	 */
	public void deleteUserGroup(int uid, int gid);
}
