package org.xyq.cms.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;
import org.xyq.cms.model.Topic;

public class TopicDto {
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private int id;
	/**
	 * 文章的标题
	 */
	private String title;
	/**
	 * 关键字，通过|来分割不同的关键字
	 */
	private String keyword;
	/**
	 * 文章的状态，默认为0表示未发表，为1表示已发表
	 */
	private int status;
	/**
	 * 是否是推荐文章
	 */
	private int recommend;
	/**
	 * 文章的内容
	 */
	private String content;
	/**
	 * 文章的摘要
	 */
	private String summary;
	/**
	 * 栏目图片id，如果该栏目是图片类型的栏目，就会显示这个id的图片
	 */
	private int channelPicId;
	/**
	 * 文章的发布时间，用来进行排序
	 */
	private String publishDate;
	/**
	 * 文章的栏目id
	 */
	private int cid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@NotEmpty(message="文章标题不能为空")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getChannelPicId() {
		return channelPicId;
	}
	public void setChannelPicId(int channelPicId) {
		this.channelPicId = channelPicId;
	}
	
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	@Min(value=1, message="必须选择一个栏目id")
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	
	public Topic getTopic(){
		Topic topic = new Topic();
		topic.setChannelPicId(this.getChannelPicId());
		topic.setContent(this.getContent());
		topic.setId(this.getId());
		topic.setKeyword(this.getKeyword());
		try {
			topic.setPublishDate(sdf.parse(this.getPublishDate()));
		} catch (ParseException e) {
			topic.setPublishDate(new Date());
		}
		topic.setRecommend(this.getRecommend());
		topic.setStatus(this.getStatus());
		topic.setSummary(this.getSummary());
		topic.setTitle(this.getTitle());
		return topic;
	} 
	
	public TopicDto() {}
	
	public TopicDto(Topic topic) {
		this.setChannelPicId(topic.getChannelPicId());
		this.setContent(topic.getContent());
		this.setId(topic.getId());
		this.setKeyword(topic.getKeyword());
		this.setPublishDate(sdf.format(topic.getPublishDate()));
		this.setRecommend(topic.getRecommend());
		this.setStatus(topic.getStatus());
		this.setSummary(topic.getSummary());
		this.setTitle(topic.getTitle());
	}
	public TopicDto(Topic topic, int cid) {
		this.setChannelPicId(topic.getChannelPicId());
		this.setContent(topic.getContent());
		this.setId(topic.getId());
		this.setCid(cid);
		this.setKeyword(topic.getKeyword());
		this.setPublishDate(sdf.format(topic.getPublishDate()));
		this.setRecommend(topic.getRecommend());
		this.setStatus(topic.getStatus());
		this.setSummary(topic.getSummary());
		this.setTitle(topic.getTitle());
	}


}
