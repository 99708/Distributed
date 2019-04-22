package org.xyq.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.xyq.basic.dao.BaseDao;
import org.xyq.basic.model.Pager;
import org.xyq.cms.model.Attachment;

@Repository("attachmentDao")
public class AttachmentDao extends BaseDao<Attachment> implements IAttachmentDao {

	private String getAttachmentSelect() {
		/*int id, String newName, String oldName, String type,
		String suffix, long size, int isIndexPic, int isImg, int isAttach, int tid*/
		return "select new Attachment(a.id, a.newName, a.oldName, a.type," +
				"a.suffix, a.size, a.isIndexPic, a.isImg, a.isAttach, a.topic.id)";
	}
	@Override
	public Pager<Attachment> findNoUseAttachment() {
		String hql = "select a from Attachment a where a.topic is null";
		return this.find(hql);
	}

	@Override
	public void clearNoUseAttachment() {
		String sql = "delete Attachment a where a.topic is null";
		this.updateByHql(sql);
	}

	@Override
	public void deleteByTopic(Integer tid) {
		String hql = "delete Attachment a where a.topic.id=?";
		this.updateByHql(hql, tid);
	}

	@Override
	public List<Attachment> listByTopic(Integer tid) {
		String hql = getAttachmentSelect()+" from Attachment a where a.topic.id=?";
		return this.list(hql, tid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Attachment> listIndexPic(int num) {
		String hql = getAttachmentSelect()+" from Attachment a " +
				"where a.isIndexPic=? and a.topic.status=1";
		return this.getSession().createQuery(hql).setParameter(0, 1).
				setFirstResult(0).setMaxResults(num).list();
	}

	@Override
	public Pager<Attachment> findChannelPic(Integer cid) {
		String hql = getAttachmentSelect()+" from Attachment a " +
				"where a.topic.status=1 and a.topic.channel.id=? and a.id=a.topic.channelPicId";
		return this.find(hql, cid);
	}

	@Override
	public List<Attachment> listAttachByTopic(Integer tid) {
		String hql = getAttachmentSelect()+" from Attachment a " +
				"where a.topic.status=1 and a.topic.id=? and a.isAttach=1";
		return this.list(hql,tid);
	}

}
