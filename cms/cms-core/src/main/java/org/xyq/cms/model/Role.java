package org.xyq.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色对象，用来对应可以访问的功能，本系统只有发布人员，管理员，审核员
 * @author Dell
 *
 */
@Entity
@Table(name="t_role")
public class Role {
	private int id;
	/**
	 * 角色姓名，中文
	 */
	private String name;
	/**
	 * 角色编号枚举类型
	 */
	private RoleType roleType;
	
	public Role(int id, String name, RoleType roleType) {
		this.id = id;
		this.name = name;
		this.roleType = roleType;
	}
	
	public Role() {
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Enumerated(EnumType.STRING)//ORDINAL数字，将其对应为0,1,2....
	@Column(name="role_type")
	public RoleType getRoleType() {
		return roleType;
	}
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
}
