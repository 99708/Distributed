package org.xyq.cms.dao;


import java.util.List;

import org.xyq.basic.dao.IBasicDao;
import org.xyq.basic.model.Pager;
import org.xyq.cms.model.Attachment;

public interface IAttachmentDao extends IBasicDao<Attachment> {
	/**
	 * 获取没有被引用的附件
	 * @return
	 */
	public Pager<Attachment> findNoUseAttachment();
	/**
	 * 清空没有被引用的附件
	 */
	public void clearNoUseAttachment();
	/**
	 * 删除某个文章的所有附件
	 * @param tid
	 */
	public void deleteByTopic(Integer tid);
	/**
	 * 获取某个文章的附件
	 * @param tid
	 * @return
	 */
	public List<Attachment> listByTopic(Integer tid);
	/**
	 * 根据一个数量获取首页图片的附件信息
	 * @param num
	 * @return
	 */
	public List<Attachment> listIndexPic(int num);
	/**
	 * 获取某个栏目中的附件图片信息
	 * @param cid
	 * @return
	 */
	public Pager<Attachment> findChannelPic(Integer cid);
	/**
	 * 获取某篇文章的附件
	 * @param tid
	 * @return
	 */
	public List<Attachment> listAttachByTopic(Integer tid);
}
