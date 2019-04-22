package org.xyq.cms.dao;


import java.util.List;

import org.xyq.basic.dao.IBasicDao;
import org.xyq.basic.model.Pager;
import org.xyq.cms.model.Channel;
import org.xyq.cms.model.ChannelTree;
import org.xyq.cms.model.Group;
import org.xyq.cms.model.GroupChannel;

public interface IGroupDao extends IBasicDao<Group> {
	public List<Group> listGroup();
	public Pager<Group> findGroup();
	public void deleteGroupUsers(int gid);
	
	/**
	 * 添加GroupChannel对象
	 * @param group
	 * @param channel
	 */
	public void addGroupChannel(Group group, Channel channel);
	/**
	 * 删除GroupChannel对象
	 * @param gid
	 * @param cid
	 */
	public void deleteGroupChannel(int gid, int cid);
	/**
	 * 清空组所管理的栏目
	 * @param gid
	 */
	public void clearGroupChannel(int gid);
	/**
	 * 加载GroupChannel对象
	 * @param gid
	 * @param cid
	 * @return
	 */
	public GroupChannel loadGroupChannel(int gid, int cid);
	/**
	 * 获取某个组的所有栏目id
	 * @param gid
	 * @return
	 */
	public List<Integer> listGroupChannelIds(int gid);
	/**
	 * 获取某个组的栏目树
	 * @param gid
	 * @return
	 */
	public List<ChannelTree> generateGroupChannelTree(int gid);
	/**
	 * 获取某个用户的栏目树
	 * @param gid
	 * @return
	 */
	public List<ChannelTree> generateUserChannelTree(int gid);
}
