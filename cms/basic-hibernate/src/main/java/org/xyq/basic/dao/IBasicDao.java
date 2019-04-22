package org.xyq.basic.dao;


/**
 * 公共Dao处理对象，这个对象中包含了hibernate的所有基本操作
 * @author Dell
 *
 * @param <T>
 */
public interface IBasicDao<T> {
	/**
	 * 添加对象
	 * @param t
	 * @return
	 */
	public T add(T t);
	/**
	 * 更新对象
	 * @param t
	 * @return
	 */
	public void update(T t);
	/**
	 * 根据id删除对象
	 * @param id
	 * @return
	 */
	public void delete(int id);
	/**
	 * 根据id加载对象
	 * @param id
	 * @return
	 */
	public T load(int id);
	
}
