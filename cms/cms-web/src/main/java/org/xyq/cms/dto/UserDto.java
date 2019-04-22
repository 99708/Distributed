package org.xyq.cms.dto;


import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.xyq.cms.model.User;


public class UserDto {
	private int id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 用户中文名称
	 */
	private String nickname;
	/**
	 * 用户email
	 */
	private String email;
	/**
	 * 用户电话
	 */
	private String phone;
	/**
	 * 用户状态 0表示停用，1表示启用
	 */
	private int stutas;
	/**
	 * 创建时间
	 */
	private Date creatDate;
	/**
	 * 角色id
	 */
	private Integer[] roleIds;
	/**
	 * 组id
	 */
	private Integer[] groupIds;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@NotEmpty(message="用户名不能为空")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@NotEmpty(message="密码不能为空")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Email(message="邮件格式不正确")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getStutas() {
		return stutas;
	}
	public void setStutas(int stutas) {
		this.stutas = stutas;
	}
	public Date getCreatDate() {
		return creatDate;
	}
	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}
	public Integer[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(Integer[] roleIds) {
		this.roleIds = roleIds;
	}
	public Integer[] getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(Integer[] groupIds) {
		this.groupIds = groupIds;
	}
	public User getUser(){
		User u = new User();
		u.setId(id);
		u.setEmail(email);
		u.setNickname(nickname);
		u.setPassword(password);
		u.setPhone(phone);
		u.setStutas(stutas);
		u.setUsername(username);
		return u;
		
	}
	public UserDto(User user) {
		this.setEmail(user.getEmail());
		this.setId(user.getId());
		this.setNickname(user.getNickname());
		this.setPassword(user.getPassword());
		this.setPhone(user.getPhone());
		this.setStutas(user.getStutas());
		this.setUsername(user.getUsername());
	}
	public UserDto(User user, Integer[] roles, Integer[] groups) {
		this.setEmail(user.getEmail());
		this.setId(user.getId());
		this.setNickname(user.getNickname());
		this.setPassword(user.getPassword());
		this.setPhone(user.getPhone());
		this.setStutas(user.getStutas());
		this.setUsername(user.getUsername());
		this.setRoleIds(roles);
		this.setGroupIds(groups);
	}
	
	public UserDto(User user, List<Integer> roleIds, List<Integer> groupIds) {
		this.setEmail(user.getEmail());
		this.setId(user.getId());
		this.setNickname(user.getNickname());
		this.setPassword(user.getPassword());
		this.setPhone(user.getPhone());
		this.setStutas(user.getStutas());
		this.setUsername(user.getUsername());
		this.setRoleIds(listArry(roleIds));
		this.setGroupIds(listArry(groupIds));
	}
	
	private Integer[] listArry(List<Integer> datas){
		Integer[] nums = new Integer[datas.size()];
		for (int i = 0; i < datas.size(); i++) {
			nums[i] = datas.get((int) i);
		}
		return nums;
	}
	public UserDto() {
	}
	
}
