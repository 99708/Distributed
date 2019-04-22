package org.xyq.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.xyq.basic.dao.BaseDao;
import org.xyq.basic.model.Pager;
import org.xyq.cms.model.Group;
import org.xyq.cms.model.Role;
import org.xyq.cms.model.RoleType;
import org.xyq.cms.model.User;
import org.xyq.cms.model.UserGroup;
import org.xyq.cms.model.UserRole;

@SuppressWarnings("unchecked")
@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {

	@Override
	public List<Role> listUserRoles(int userid) {
		String hql = "select ur.role from UserRole ur where ur.user.id=?";
		System.out.println(hql);
		return this.getSession().createQuery(hql).setParameter(0, userid).list();
	}

	@Override
	public List<Integer> listUserRoleIds(int userid) {
		String hql = "select ur.role.id from UserRole ur where ur.user.id = ?";
		return this.getSession().createQuery(hql).setParameter(0, userid).list();
	}

	@Override
	public List<Group> listUserGroups(int userid) {
		String hql = "select ug.group from UserGroup ug where ug.user.id = ?";
		return this.getSession().createQuery(hql).setParameter(0, userid).list();
	}

	@Override
	public List<Integer> listUserGroupIds(int userid) {
		String hql = "select ug.group.id from UserGroup ug where ug.user.id = ?";
		return this.getSession().createQuery(hql).setParameter(0, userid).list();
	}

	@Override
	public UserRole loadUserRole(int userid, int roleid) {
		String hql = "select ur from UserRole ur left join fetch ur.user u left join fetch ur.role r where u.id=? and r.id=?";
		return (UserRole) this.getSession().createQuery(hql).setParameter(0, userid).setParameter(1, roleid).uniqueResult();
	}

	@Override
	public UserGroup loadUserGroup(int userid, int groupid) {
		String hql = "select ug from UserGroup ug left join fetch ug.user u left join fetch ug.group g where u.id=? and g.id=?";
		return (UserGroup) this.getSession().createQuery(hql).setParameter(0, userid).setParameter(1, groupid).uniqueResult();
	}

	@Override
	public User loadByUsername(String username) {
		String hql = "from User where username=?";
		return (User) this.queryObject(hql, username);
	}

	@Override
	public List<User> listRoleUsers(int roleid) {
		String hql = "select ur.user from UserRole ur where ur.role.id=? ";
		return this.list(hql, roleid);
	}

	@Override
	public List<User> listRoleUsers(RoleType roleType) {
		String hql = "select ur.user from UserRole ur where ur.role.roleType=?";
		return this.list(hql, roleType);
	}

	@Override
	public List<User> listGroupUsers(int groupid) {
		String hql = "select ug.user from UserGroup ug where ug.group.id=?";
		return this.list(hql, groupid);
	}

	@Override
	public void addUserRoles(User user, Role role) {
		UserRole userRole = this.loadUserRole(user.getId(), role.getId());
		if(userRole != null) return;
		userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		this.getSession().save(userRole);
	}

	@Override
	public void addUserGroups(User user, Group group) {
		UserGroup userGroup = this.loadUserGroup(user.getId(), group.getId());
		if(userGroup != null) return;
		userGroup = new UserGroup();
		userGroup.setGroup(group);
		userGroup.setUser(user);
		this.getSession().save(userGroup);
	}

	@Override
	public void deleteUserRoles(int uid) {
		String hql = "delete UserRole ur where ur.user.id=?";
		this.updateByHql(hql, uid);
	}

	@Override
	public void deleteUserGroups(int uid) {
		String hql = "delete UserGroup ug where ug.user.id=?";
		this.updateByHql(hql, uid);
		
	}

	@Override
	public Pager<User> findUser() {
		return this.find("from User");
	}

	@Override
	public void deleteUserRole(int uid, int rid) {
		String hql = "delete UserRole ur where ur.user.id=? and ur.role.id=?";
		this.updateByHql(hql, new Object[]{uid, rid});
	}

	@Override
	public void deleteUserGroup(int uid, int gid) {
		String hql = "delete UserGroup ug where ug.user.id=? and ug.group.id=?";
		this.updateByHql(hql, new Object[]{uid, gid});
		
	}


}
