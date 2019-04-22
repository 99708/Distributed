package org.xyq.cms.dao;

import org.xyq.basic.dao.IBasicDao;
import org.xyq.basic.model.Pager;
import org.xyq.cms.model.Topic;

public interface ITopicDao extends IBasicDao<Topic> {
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
