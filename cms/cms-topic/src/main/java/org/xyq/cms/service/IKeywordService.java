package org.xyq.cms.service;

import java.util.List;

import org.xyq.basic.model.Pager;
import org.xyq.cms.model.Keyword;

public interface IKeywordService {
	/**
	 * 添加或更改关键字
	 * 如果这个关键字不存在就添加，如果存在就让引用次数加一
	 * @param name
	 */
	public void addOrUpdate(String name);
	/**
	 * 获取引用次数大于等于某个数的关键字
	 * @param num
	 * @return
	 */
	public List<Keyword> getKeywordByTimes(int num);
	/**
	 * 获取引用次数最大的几个关键字
	 * @param num
	 * @return
	 */
	public List<Keyword> getMaxTimesKeyword(int num);
	/**
	 * 查找没有使用的关键字
	 * @return
	 */
	public Pager<Keyword> findNoUseKeyword();
	/**
	 * 清空没有使用的关键字
	 */
	public void clearNoUseKeyword();
	/**
	 * 查找正在被引用的关键字
	 * @return
	 */
	public List<Keyword> findUseKeyword();
	/**
	 * 根据某个条件从keyword表中查询关键字
	 * @param con
	 * @return
	 */
	public List<Keyword> listKeywordByCon(String con);
	/**
	 * 根据某个条件从keyword表中查询关键字，返回字符串
	 * @param con
	 * @return
	 */
	public List<String>  listStringByCon(String con);
}
