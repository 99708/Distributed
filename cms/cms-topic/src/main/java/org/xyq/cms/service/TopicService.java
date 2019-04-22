package org.xyq.cms.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.xyq.basic.model.Pager;
import org.xyq.cms.dao.IAttachmentDao;
import org.xyq.cms.dao.IChannelDao;
import org.xyq.cms.dao.ITopicDao;
import org.xyq.cms.dao.IUserDao;
import org.xyq.cms.model.Attachment;
import org.xyq.cms.model.Channel;
import org.xyq.cms.model.CmsException;
import org.xyq.cms.model.Topic;
import org.xyq.cms.model.User;

@Service("topicService")
public class TopicService implements ITopicService {

	private ITopicDao topicDao;
	private IAttachmentDao attachmentDao;
	private IChannelDao channelDao;
	private IUserDao userDao;
	
	public ITopicDao getTopicDao() {
		return topicDao;
	}
	@Inject
	public void setTopicDao(ITopicDao topicDao) {
		this.topicDao = topicDao;
	}

	public IAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	@Inject
	public void setAttachmentDao(IAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	
	public IChannelDao getChannelDao() {
		return channelDao;
	}
	@Inject
	public void setChannelDao(IChannelDao channelDao) {
		this.channelDao = channelDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}
	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	private void addTopicAtths(Topic topic, Integer[] aids){
		if(aids != null){
			for(Integer aid:aids){
				Attachment attachment = attachmentDao.load(aid);
				if(attachment == null) continue;
				attachment.setTopic(topic);
			}
		}
	}

	@Override
	public void add(Topic topic, int cid, int uid, Integer[] aids) {
		Channel channel = channelDao.load(cid);
		User user = userDao.load(uid);
		if(channel == null || user == null){
			throw new CmsException("要添加的文章必须有用户或栏目");
		}
		topic.setAuthor(user.getNickname());
		topic.setChannel(channel);
		topic.setUser(user);
		topic.setCreateDate(new Date());
		topic.setCname(channel.getName());
		topicDao.add(topic);
		addTopicAtths(topic, aids);
	}

	@Override
	public void add(Topic topic, int cid, int uid) {
		add(topic, cid, uid, null);
	}

	@Override
	public void delete(int id) {
		List<Attachment> atts = attachmentDao.listByTopic(id);
		attachmentDao.deleteByTopic(id);
		topicDao.delete(id);
		for(Attachment a:atts){
			AttachmentService.deleteAttachFiles(a);
		}
	}

	@Override
	public void update(Topic topic, int cid, Integer[] aids) {
		Channel channel = channelDao.load(cid);	
		if(channel == null){
			throw new CmsException("要更新的文章必须有栏目");
		}
		topic.setCname(channel.getName());
		topic.setChannel(channel);
		topicDao.update(topic);
		addTopicAtths(topic, aids);
	}

	@Override
	public void update(Topic topic, int cid) {
		update(topic, cid, null);
	}

	@Override
	public Topic load(int id) {
		return topicDao.load(id);
	}

	@Override
	public Pager<Topic> find(Integer cid, String title, Integer status) {
		return topicDao.find(cid, title, status);
	}

	@Override
	public Pager<Topic> find(Integer uid, Integer cid, String title,
			Integer status) {
		return topicDao.find(uid, cid, title, status);
	}

	@Override
	public Pager<Topic> searchTopicByKeyword(String keyword) {
		return topicDao.searchTopicByKeyword(keyword);
	}

	@Override
	public Pager<Topic> searchTopic(String con) {
		return topicDao.searchTopic(con);
	}

	@Override
	public Pager<Topic> findRecommendTopic(Integer cid) {
		return topicDao.findRecommendTopic(cid);
	}
	
	@Override
	public void updateStatus(int tid) {
		Topic topic = topicDao.load(tid);
		if(topic.getStatus() == 0){
			topic.setStatus(1);
		}else {
			topic.setStatus(0);
		}
		topicDao.update(topic);
	}

}
