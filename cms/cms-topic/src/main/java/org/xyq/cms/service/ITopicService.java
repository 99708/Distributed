package org.xyq.cms.service;

import org.xyq.basic.model.Pager;
import org.xyq.cms.model.Topic;


public interface ITopicService {
	/**
	 * 添加带附件信息的文章
	 * @param topic
	 * @param cid
	 * @param uid
	 * @param sids
	 */
	public void add(Topic topic, int cid, int uid, Integer[] aids);
	/**
	 * 添加不带附件信息的文章
	 * @param topic
	 * @param cid
	 * @param uid
	 */
	public void add(Topic topic, int cid, int uid);
	/**
	 * 删除文章,先删除文章的附件信息,还得删除附件的文件对象
	 * @param id
	 */
	public void delete (int id);
	/**
	 * 更新文章，带附件信息
	 * @param topic
	 * @param cid
	 * @param aids
	 */
	public void update(Topic topic, int cid, Integer[] aids);
	/**
	 * 更新文章信息,不带附件信息
	 * @param topic
	 * @param cid
	 */
	public void update(Topic topic, int cid);
	/**
	 * 更新文章状态
	 * @param tid
	 */
	public void updateStatus(int tid);
	/**
	 * 加载文章
	 * @param id
	 * @return
	 */
	public Topic load(int id);
	/**
	 * 根据栏目和标题和状态进行文章的检索
	 * @param cid
	 * @param title
	 * @return
	 */
	public Pager<Topic> find(Integer cid, String title, Integer status);
	/**
	 * 根据用户，栏目和标题和状态进行检索
	 * @param uid
	 * @param cid
	 * @param title
	 * @return
	 */
	public Pager<Topic> find(Integer uid, Integer cid, String title, Integer status);
	/**
	 * 根据关键字进行文章的检索，仅仅只是检索关键字类似的
	 * @param keyword
	 * @return
	 */
	public Pager<Topic> searchTopicByKeyword(String keyword);
	/**
	 * 通过某个条件来检索，该条件会有标题，内容和摘要检索
	 * @param con
	 * @return
	 */
	public Pager<Topic> searchTopic(String con);
	/**
	 * 检索某个栏目的推荐文章，如果cid为空表示，表示检索全部的文章
	 * @param cid
	 * @return
	 */
	public Pager<Topic> findRecommendTopic(Integer cid);
	
}
