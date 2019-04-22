package org.xyq.cms.dao;

import org.springframework.stereotype.Repository;
import org.xyq.basic.dao.BaseDao;
import org.xyq.basic.model.Pager;
import org.xyq.cms.model.Topic;

@Repository("topicDao")
public class TopicDao extends BaseDao<Topic> implements ITopicDao {

	@Override
	public Pager<Topic> find(Integer cid, String title, Integer status) {
		
		return find(null, cid, title, status);
	}
	
	private String getTopicSelect(){
		return "select new Topic(t.id,t.title,t.keyword,t.status,t.recommend,t.publishDate,t.author,t.cname)";
	}

	@Override
	public Pager<Topic> find(Integer uid, Integer cid, String title, Integer status) {
		String hql = getTopicSelect()+" from Topic t where 1=1";
		if(status != null){
			hql += " and t.status="+status;
		}
		if(title != null && !title.equals("")){
			hql += " and t.title like '%"+title+"%'";
		}
		if(uid != null && uid > 0){
			hql += " and t.user.id="+uid;
		}
		if(cid != null && cid > 0){
			hql += " and t.channel.id="+cid;
		}
		return this.find(hql);
	}

	@Override
	public Pager<Topic> searchTopicByKeyword(String keyword) {
		String hql = getTopicSelect()+" from Topic t where t.status=1 and t.keyword like '%"+keyword+"%'";
		return this.find(hql);
	}

	@Override
	public Pager<Topic> searchTopic(String con) {
		String hql = getTopicSelect()+" from Topic t where t.status=1 and " +
				"(title like '%"+con+"%' or content like '%"+con+"%' or summary like '%"+con+"%')";
		return this.find(hql);
	}

	@Override
	public Pager<Topic> findRecommendTopic(Integer cid) {
		String hql = getTopicSelect()+" from Topic t where t.status=1 and t.recommend=1";
		if(cid == null || cid == 0){
			return this.find(hql);
		}else{
			hql += " and t.channel.id=?";
			return this.find(hql,cid);
		}
	}

}
