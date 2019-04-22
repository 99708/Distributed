package org.xyq.cms.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.xyq.basic.model.SystemContext;
import org.xyq.cms.dao.IChannelDao;
import org.xyq.cms.model.Channel;
import org.xyq.cms.model.ChannelTree;
import org.xyq.cms.model.CmsException;

@Service("channelService")
public class ChannelService implements IChannelService{
	private IChannelDao channelDao;
	
	public IChannelDao getChannelDao() {
		return channelDao;
	}
	@Inject
	public void setChannelDao(IChannelDao channelDao) {
		this.channelDao = channelDao;
	}

	@Override
	public void add(Channel channel, Integer pid) {
		Integer orders = channelDao.getMaxOrdelByParent(pid);
		if(pid != null && pid >0){
			Channel pc = channelDao.load(pid);
			if(pc == null ){
				throw new CmsException("要添加栏目的父类对象不正确");
			}
			channel.setParent(pc);
		}
		channel.setOrders(orders+1);
		channelDao.add(channel);
	}

	@Override
	public void update(Channel channel) {
		channelDao.update(channel);
	}

	@Override
	public void delete(int id) {
		//TODO 1、判断要删除的栏目是否还有子栏目
		List<Channel> cs = channelDao.listByParent(id);
		if(cs != null && cs.size()>0){
			throw new CmsException("要删除的栏目还有子栏目，不能删除");
		}
		//TODO 2、判断要删除的栏目是否存在文章
		//TODO 3、删除和组的关联关系
		channelDao.delete(id);
	}

	@Override
	public void clearTopic(int id) {
		// TODO 与文章有关
		
	}

	@Override
	public Channel load(int id) {
		return channelDao.load(id);
	}

	@Override
	public List<Channel> listByParent(Integer pid) {
		String sort = SystemContext.getSort();
		if(sort == null || "".equals(sort)){
			SystemContext.setSort("orders");
			SystemContext.setOrder("asc");
		}
		return channelDao.listByParent(pid);
	}
	@Override
	public List<ChannelTree> generateTree() {
		return channelDao.generateTree();
	}
	@Override
	public List<ChannelTree> generateTreeByParent(Integer pid) {
		return channelDao.generateTreeByParent(pid);
	}
	@Override
	public void updateSort(Integer[] ids) {
		channelDao.updateSort(ids);
	}
	@Override
	public List<Channel> listPublishChannels() {
		return channelDao.listPublishChannel();
	}

}
