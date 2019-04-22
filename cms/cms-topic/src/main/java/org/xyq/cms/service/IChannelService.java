package org.xyq.cms.service;

import java.util.List;

import org.xyq.cms.model.Channel;
import org.xyq.cms.model.ChannelTree;


public interface IChannelService {
	/**
	 * 添加栏目
	 * @param channel
	 * @param pid
	 */
	public void add(Channel channel, Integer pid);
	/**
	 * 更新栏目
	 * @param channel
	 */
	public void update(Channel channel);
	/**
	 * 删除栏目
	 * @param id
	 */
	public void delete(int id);
	/**
	 * 清空栏目中的文章
	 * @param id
	 */
	public void clearTopic(int id);
	/**
	 * 根据id加载栏目
	 * @param id
	 * @return
	 */
	public Channel load(int id);
	/**
	 * 根据父id加载栏目，该方法首先检查SystemContext是否存在排序，如果不存在把orders加入
	 * @param pid
	 * @return
	 */
	public List<Channel> listByParent(Integer pid);
	/**
	 * 获取所有可以发布文章的栏目，栏目的状态必须为启用状态
	 * @return
	 */
	public List<Channel> listPublishChannels ();
	/**
	 * 把所有的栏目获取并生成一颗完整的树
	 * @return
	 */
	public List<ChannelTree> generateTree();
	/**
	 * 根据父类对象获取子类栏目并且生成树
	 * @param pid
	 * @return
	 */
	public List<ChannelTree> generateTreeByParent(Integer pid);
	
	public void updateSort(Integer[] ids);
	
	
}
