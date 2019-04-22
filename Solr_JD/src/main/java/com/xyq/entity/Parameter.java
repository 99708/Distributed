package com.xyq.entity;

/**
 * 分装查询条件实体类
 * @author: 997 
 * @date:   2018年12月4日 下午7:45:56   
 *
 */
public class Parameter {
	private Product product;
	private String pro_name;
	private String pro_catalog_name;
	private Integer sort = 1;
	private Double max;
	private Double min;
	private int pageNum=1;
	private int pagesize=12;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getPro_catalog_name() {
		return pro_catalog_name;
	}
	public void setPro_catalog_name(String pro_catalog_name) {
		this.pro_catalog_name = pro_catalog_name;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Double getMax() {
		return max;
	}
	public void setMax(Double max) {
		this.max = max;
	}
	public Double getMin() {
		return min;
	}
	public void setMin(Double min) {
		this.min = min;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public Parameter(Product product, String pro_name, String pro_catalog_name, Integer sort, Double max, Double min,
			int pageNum, int pagesize) {
		super();
		this.product = product;
		this.pro_name = pro_name;
		this.pro_catalog_name = pro_catalog_name;
		this.sort = sort;
		this.max = max;
		this.min = min;
		this.pageNum = pageNum;
		this.pagesize = pagesize;
	}
	public Parameter() {
		super();
	}
	@Override
	public String toString() {
		return "Parameter [product=" + product + ", pro_name=" + pro_name + ", pro_catalog_name=" + pro_catalog_name
				+ ", sort=" + sort + ", max=" + max + ", min=" + min + ", pageNum=" + pageNum + ", pagesize=" + pagesize
				+ "]";
	}
	
	
	
	
	
}
