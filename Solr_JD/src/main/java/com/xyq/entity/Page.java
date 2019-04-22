package com.xyq.entity;

import java.util.List;

public class Page {
	private long count;
	private int pageCount;
	private int pageStart;
	private int pageNum;
	private int pagesize=12;
	List<Product> products;
	private Parameter parameter;
	
	public Parameter getParameter() {
		return parameter;
	}
	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageStart() {
		return pageStart;
	}
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public Page(long count, int pageCount, int pageStart, int pageNum, int pagesize, List<Product> products,
			Parameter parameter) {
		super();
		this.count = count;
		this.pageCount = pageCount;
		this.pageStart = pageStart;
		this.pageNum = pageNum;
		this.pagesize = pagesize;
		this.products = products;
		this.parameter = parameter;
	}
	public Page() {
		super();
	}
	@Override
	public String toString() {
		return "Page [count=" + count + ", pageCount=" + pageCount + ", pageStart=" + pageStart + ", pageNum=" + pageNum
				+ ", pagesize=" + pagesize + ", products=" + products + ", parameter=" + parameter + "]";
	}

	
	
	
}
