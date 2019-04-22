package org.xyq.cms.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.xyq.basic.model.Pager;
import org.xyq.cms.model.Attachment;

public interface IAttachmentService {
	/**
	 * 增加附件信息
	 * @param id
	 */
	public void add(Attachment attachment, InputStream is) throws IOException;
	/**
	 * 删除附件信息
	 * @param id
	 */
	public void delete(int id);
	/**
	 * 检索附件信息
	 * @param id
	 */
	public Attachment load(int id);
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
	/**
	 * 更新是否是主页图片
	 * @param aid
	 */
	public void updateIndexPic(int aid);
	/**
	 * 更新是否是附件信息
	 * @param aid
	 */
	public void updateAttachInfo(int aid);
}
