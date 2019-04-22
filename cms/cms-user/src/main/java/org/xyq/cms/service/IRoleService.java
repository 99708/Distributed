package org.xyq.cms.service;

import java.util.List;

import org.xyq.cms.model.Role;


public interface IRoleService {
	public void add(Role role);
	public void delete(int id);
	public void update(Role role);
	public Role load(int id);
	
	public List<Role> listRole();
	public void deleteRoleUser(int rid);
}
