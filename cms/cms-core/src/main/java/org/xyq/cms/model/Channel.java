package org.xyq.cms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="t_channel")
public class Channel {
	public static final String ROOT_NAME = "网站系统栏目";
	public static final int ROOT_ID = 0;
	/**
	 * 栏目主键
	 */
	private int id;
	/**
	 * 栏目名称
	 */
	private String name;
	/**
	 * 栏目是否值自定义连接，0表示否，1表示是
	 */
	private int customLink;
	/**
	 * 自定义连接的地址
	 */
	private String customLinkUrl;
	/**
	 * 栏目的类别，枚举
	 */
	private ChannelType type;
	/**
	 * 是否值首页栏目，1表示是，0表示否
	 */
	private int isIndex;
	/**
	 * 是否是顶部栏目，1表示是，0表示否
	 */
	private int isTopNav;
	/**
	 * 是否是推荐栏目，1表示是，0表示否
	 */
	private int isCommend;
	/**
	 * 状态，0表示停用，1表示启用
	 */
	private int isStatus;
	/**
	 * 栏目的序号
	 */
	private int orders;
	/**
	 * 父类栏目
	 */
	private Channel parent;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@NotEmpty(message="栏目名称不能为空")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="custom_link")
	public int getCustomLink() {
		return customLink;
	}
	public void setCustomLink(int customLink) {
		this.customLink = customLink;
	}
	@Column(name="custom_link_url")
	public String getCustomLinkUrl() {
		return customLinkUrl;
	}
	public void setCustomLinkUrl(String customLinkUrl) {
		this.customLinkUrl = customLinkUrl;
	}
	public ChannelType getType() {
		return type;
	}
	public void setType(ChannelType type) {
		this.type = type;
	}
	@Column(name="is_index")
	public int getIsIndex() {
		return isIndex;
	}
	public void setIsIndex(int isIndex) {
		this.isIndex = isIndex;
	}
	@Column(name="is_top_nav")
	public int getIsTopNav() {
		return isTopNav;
	}
	public void setIsTopNav(int isTopNav) {
		this.isTopNav = isTopNav;
	}
	public int getIsCommend() {
		return isCommend;
	}
	public void setIsCommend(int isCommend) {
		this.isCommend = isCommend;
	}
	public int getIsStatus() {
		return isStatus;
	}
	public void setIsStatus(int isStatus) {
		this.isStatus = isStatus;
	}
	public int getOrders() {
		return orders;
	}
	public void setOrders(int orders) {
		this.orders = orders;
	}
	@ManyToOne
	@JoinColumn(name="pid")
	public Channel getParent() {
		return parent;
	}
	public void setParent(Channel parent) {
		this.parent = parent;
	}
	
	public Channel() {
	}
	
	public Channel(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}
