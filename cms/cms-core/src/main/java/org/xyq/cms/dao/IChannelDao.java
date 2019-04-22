package org.xyq.cms.dao;

import java.util.List;

import org.xyq.basic.dao.IBasicDao;
import org.xyq.cms.model.Channel;
import org.xyq.cms.model.ChannelTree;

public interface IChannelDao extends IBasicDao<Channel> {
	/**
	 * 根据父id获取所有的子栏目
	 */
	public List<Channel> listByParent(Integer pid);
	/**
	 * 获取子栏目的最大排序号
	 * @param pid
	 * @return
	 */
	public int getMaxOrdelByParent(Integer pid);
	/**
	 * 把所有的栏目获取并生成一颗完整的树
	 * @return
	 */
	public List<ChannelTree> generateTree();
	/**
	 * 获取所有可以发布文章的栏目，栏目的状态必须为启用状态
	 * @return
	 */
	public List<Channel> listPublishChannel ();
	/**
	 * 根据父类对象获取子类栏目并且生成树
	 * @param pid
	 * @return
	 */
	public List<ChannelTree> generateTreeByParent(Integer pid);
	/**
	 * 通过一个数组完成排序
	 * @param ids
	 */
	public void updateSort(Integer[] ids);
}
