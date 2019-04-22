package org.xyq.cms.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.xyq.basic.model.Pager;
import org.xyq.cms.dao.IChannelDao;
import org.xyq.cms.dao.IGroupDao;
import org.xyq.cms.dao.IUserDao;
import org.xyq.cms.model.Channel;
import org.xyq.cms.model.ChannelTree;
import org.xyq.cms.model.CmsException;
import org.xyq.cms.model.Group;
import org.xyq.cms.model.GroupChannel;
import org.xyq.cms.model.User;

@Service("groupService")
public class GroupService implements IGroupService {
	
	private IGroupDao groupDao;
	private IUserDao userDao;
	private IChannelDao channelDao;
	
	public IChannelDao getChannelDao() {
		return channelDao;
	}
	@Inject
	public void setChannelDao(IChannelDao channelDao) {
		this.channelDao = channelDao;
	}

	public IGroupDao getGroupDao() {
		return groupDao;
	}
	
	@Inject
	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void add(Group group) {
		groupDao.add(group);
	}

	@Override
	public void delete(int id) {
		List<User> users = userDao.listGroupUsers(id);
		if(users!=null && users.size()>0){
			throw new CmsException("你要删除的组中还有用户，请检查你的操作");
		}else{
			groupDao.delete(id);
		}
	}

	@Override
	public void update(Group group) {
		groupDao.update(group);
	}

	@Override
	public Group load(int id) {
		return groupDao.load(id);
	}

	@Override
	public List<Group> listGroup() {
		return groupDao.listGroup();
	}

	@Override
	public Pager<Group> findGroup() {
		return groupDao.findGroup();
	}

	@Override
	public void deleteGroupUser(int gid) {
		groupDao.deleteGroupUsers(gid);
	}

	@Override
	public void addGroupChannel(int gid, int cid) {
		Group group = groupDao.load(gid);
		Channel channel = channelDao.load(cid);
		if(group == null || channel == null){
			throw new CmsException("要添加的频道关联对象不存在");
		}
		groupDao.addGroupChannel(group, channel);
	}

	@Override
	public void deleteGroupChannel(int gid, int cid) {
		groupDao.deleteGroupChannel(gid, cid);
	}

	@Override
	public void clearGroupChannel(int gid) {
		groupDao.clearGroupChannel(gid);
	}

	@Override
	public GroupChannel loadGroupChannel(int gid, int cid) {
		return groupDao.loadGroupChannel(gid, cid);
	}

	@Override
	public List<Integer> listGroupChannelIds(int gid) {
		return groupDao.listGroupChannelIds(gid);
	}

	@Override
	public List<ChannelTree> generateGroupChannelTree(int gid) {
		return groupDao.generateGroupChannelTree(gid);
	}

	@Override
	public List<ChannelTree> generateUserChannelTree(int uid) {
		return groupDao.generateUserChannelTree(uid);
	}

	

}
