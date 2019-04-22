package com.xyq.entity;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 产品实体类
 * @author: 997 
 * @date:   2018年12月4日 下午7:46:29   
 *
 */
public class Product {
	@Field
	private String id;
	@Field
	private String pro_name;
	@Field
	private Integer pro_catalog;
	@Field
	private String pro_catalog_name;
	@Field
	private Double pro_price;
	@Field
	private Integer pro_number;
	@Field
	private String pro_description;
	@Field
	private String pro_picture;
	@Field
	private String pro_release_time;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public Integer getPro_catalog() {
		return pro_catalog;
	}
	public void setPro_catalog(Integer pro_catalog) {
		this.pro_catalog = pro_catalog;
	}
	public String getPro_catalog_name() {
		return pro_catalog_name;
	}
	public void setPro_catalog_name(String pro_catalog_name) {
		this.pro_catalog_name = pro_catalog_name;
	}
	public Double getPro_price() {
		return pro_price;
	}
	public void setPro_price(Double pro_price) {
		this.pro_price = pro_price;
	}
	public Integer getPro_number() {
		return pro_number;
	}
	public void setPro_number(Integer pro_number) {
		this.pro_number = pro_number;
	}
	public String getPro_description() {
		return pro_description;
	}
	public void setPro_description(String pro_description) {
		this.pro_description = pro_description;
	}
	public String getPro_picture() {
		return pro_picture;
	}
	public void setPro_picture(String pro_picture) {
		this.pro_picture = pro_picture;
	}
	public String getPro_release_time() {
		return pro_release_time;
	}
	public void setPro_release_time(String pro_release_time) {
		this.pro_release_time = pro_release_time;
	}
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Product(String id, String pro_name, Integer pro_catalog, String pro_catalog_name, Double pro_price,
			Integer pro_number, String pro_description, String pro_picture, String pro_release_time) {
		super();
		this.id = id;
		this.pro_name = pro_name;
		this.pro_catalog = pro_catalog;
		this.pro_catalog_name = pro_catalog_name;
		this.pro_price = pro_price;
		this.pro_number = pro_number;
		this.pro_description = pro_description;
		this.pro_picture = pro_picture;
		this.pro_release_time = pro_release_time;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", pro_name=" + pro_name + ", pro_catalog=" + pro_catalog + ", pro_catalog_name="
				+ pro_catalog_name + ", pro_price=" + pro_price + ", pro_number=" + pro_number + ", pro_description="
				+ pro_description + ", pro_picture=" + pro_picture + ", pro_release_time=" + pro_release_time + "]";
	}
	
	
}
