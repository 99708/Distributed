package org.xyq.basic.model;


/**
 * 用来传递列表对象的ThreadLocal
 * @author Dell
 *
 */
public class SystemContext {
	/**
	 * 分页的大小
	 */
	private static ThreadLocal<Integer> pagersize = new ThreadLocal<Integer>();
	/**
	 * 分页的起始页
	 */
	private static ThreadLocal<Integer> pageroffset = new ThreadLocal<Integer>();
	/**
	 * 列表的排序字段
	 */
	private static ThreadLocal<String> sort = new ThreadLocal<String>();
	/**
	 * 列表的排序方式
	 */
	private static ThreadLocal<String> order = new ThreadLocal<String>();
	private static ThreadLocal<String> realPath = new ThreadLocal<String>();
	
	
	
	public static String getRealPath() {
		return realPath.get();
	}
	public static void setRealPath(String _realPath) {
		SystemContext.realPath.set(_realPath);
	}
	
	public static Integer getPagersize() {
		return pagersize.get();
	}
	public static void setPagersize(Integer _pagersize) {
		pagersize.set(_pagersize);
	}
	public static Integer getPageroffset() {
		return pageroffset.get();
	}
	public static void setPageroffset(Integer _pageroffset) {
		pageroffset.set(_pageroffset);
	}
	public static String getSort() {
		return sort.get();
	}
	public static void setSort(String _sort) {
		sort.set(_sort);
	}
	public static String getOrder() {
		return order.get();
	}
	public static void setOrder(String _order) {
		order.set(_order);
	}
	
	public static void removePagersize(){
		pagersize.remove();
	}
	
	public static void removePageroffset(){
		pageroffset.remove();
	}
	
	public static void removeSort(){
		sort.remove();
	}
	
	public static void removeOrder(){
		order.remove();
	}
	
	public static void removeRealPath(){
		realPath.remove();
	} 
}
