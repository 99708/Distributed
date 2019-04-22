/**
 * 
 */
package org.xyq.basic.dao;

import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.xyq.basic.model.Pager;
import org.xyq.basic.model.SystemContext;

/**
 * @author Dell
 *
 */
@SuppressWarnings("unchecked")
public class BaseDao<T> implements IBasicDao<T> {

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#add(java.lang.Object)
	 */
	private SessionFactory sessionFactory;
	
	/**
	 * 创建一个Class的对象来获取泛型的class
	 */
	private Class<?> clz;
	
	public Class<?> getClz() {
		if(clz==null) {
			//获取泛型的Class对象
			clz = ((Class<?>)
					(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	@Inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	 
	public T add(T t) {
		getSession().save(t);
		return t;
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#update(java.lang.Object)
	 */
	 
	public void update(T t) {
		getSession().update(t);
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#delete(int)
	 */
	 
	public void delete(int id) {
		getSession().delete(this.load(id));
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#load(int)
	 */
	
	 
	public T load(int id) {
		return (T) getSession().load(getClz(), id);
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#list(java.lang.String, java.lang.Object[])
	 */
	public List<T> list(String hql, Object[] args) {
		return this.list(hql, args, null);
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#list(java.lang.String, java.lang.Object)
	 */
	public List<T> list(String hql, Object arg) {
		return this.list(hql, new Object[]{arg});
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#list(java.lang.String)
	 */
	public List<T> list(String hql) {
		return this.list(hql, null);
	}
	
	private String initSort(String hql){
		
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		if (sort!=null && "".equals(sort.trim())){
			hql = hql + "order by " + sort;
			if ("desc".equals(order)){
				hql += " asc";
			}else{
				hql += " desc";
			}
		}
		return hql;
	}
	
	@SuppressWarnings("rawtypes")
	private void setAliasParameter(Query query, Map<String, Object> alias){
		if(alias != null){
			Set<String> keys = alias.keySet();
			for (String key: keys){
				Object val = alias.get(key);
				if(val instanceof Collection){
					//查询条件列表
					query.setParameterList(key, (Collection) val);
				}else{
					query.setParameter(key, val);
				}
			}
		}
	}
	
	private void setParameter(Query query, Object[] args){
		if(args != null && args.length > 0){
			int index = 0;
			for (Object arg: args){
				query.setParameter(index++, arg);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#list(java.lang.String, java.lang.Object[], java.util.Map)
	 */
	
	public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.list();
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#list(java.lang.String, java.util.Map)
	 */
	public List<T> listByAlias(String hql, Map<String, Object> alias) {
		return this.list(hql, null, alias);
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#find(java.lang.String, java.lang.Object[])
	 */
	public Pager<T> find(String hql, Object[] args) {
		return this.find(hql, args, null);
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#find(java.lang.String, java.lang.Object)
	 */
	public Pager<T> find(String hql, Object arg) {
		return this.find(hql, new Object[]{arg});
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#find(java.lang.String)
	 */
	public Pager<T> find(String hql) {
		return this.find(hql, null);
	}
	
	@SuppressWarnings("rawtypes")
	private void setPages(Query query, Pager pages){
		Integer pageSize = SystemContext.getPagersize();
		Integer Pageroffset = SystemContext.getPageroffset();
		if(Pageroffset == null || Pageroffset < 0){
			Pageroffset = 0;
		}
		if(pageSize == null || pageSize < 0){//默认每页显示15条数据
			pageSize = 15;
		}
		pages.setOffset(Pageroffset);
		pages.setSize(pageSize);
		query.setFirstResult(Pageroffset).setMaxResults(pageSize);
	}
	private String getCountHql(String hql, boolean isHql){
		String end = hql.substring(hql.indexOf("from"));//定位到from
		String c = "select count(*) " +end;//拼接成一个sql语句
		//抓取
		if(isHql){
			c.replaceAll("fetch", "");
		}	
		return c;
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#find(java.lang.String, java.lang.Object[], java.util.Map)
	 */
	public Pager<T> find(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		String cq = getCountHql(hql, true);
		Query query = getSession().createQuery(hql);
		Query cquery = getSession().createQuery(cq);
		//设置别名参数
		setAliasParameter(query, alias);
		setAliasParameter(cquery, alias);
		//设置参数
		setParameter(query, args);
		setParameter(cquery, args);
		Pager<T> pages = new Pager<T>();
		setPages(query,pages);
		List<T> datas = query.list();
		pages.setDatas(datas);
		long total = (Long)cquery.uniqueResult();
		pages.setTotal(total);
		return pages;
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#find(java.lang.String, java.util.Map)
	 */
	public Pager<T> findByAlias(String hql, Map<String, Object> alias) {
		return this.find(hql, null, alias);
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#queryObject(java.lang.String, java.lang.Object[])
	 */
	public Object queryObject(String hql, Object[] args) {
		return this.queryObject(hql, args, null);
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#queryObject(java.lang.String, java.lang.Object)
	 */
	public Object queryObject(String hql, Object arg) {
		return this.queryObject(hql, new Object[]{arg});
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#queryObject(java.lang.String)
	 */
	public Object queryObject(String sql) {
		return this.queryObject(sql, null);
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#updateByHql(java.lang.String, java.lang.Object[])
	 */
	public Object queryObject(String hql, Object[] args,Map<String, Object> alias) {
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.uniqueResult();
	}

	public Object queryObjectByAlias(String hql, Map<String, Object> alias) {
		return this.queryObject(hql, null, alias);
	}
	 
	public void updateByHql(String sql, Object[] args) {
		Query query = getSession().createQuery(sql);
		setParameter(query, args);
		query.executeUpdate();
		}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#updateByHql(java.lang.String, java.lang.Object)
	 */
	 
	public void updateByHql(String sql, Object arg) {
		this.updateByHql(sql, new Object[]{arg});
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#updateByHql(java.lang.String)
	 */
	 
	public void updateByHql(String sql) {
		this.updateByHql(sql, null);
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#listBySql(java.lang.String, java.lang.Object[], java.lang.Class, boolean)
	 */
	 
	public <N extends Object>List<N> listBySql(String sql, Object[] args, Class<?> clz,
			boolean hasEntity) {
		return this.listBySql(sql, args, null, clz, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#listBySql(java.lang.String, java.lang.Object, java.lang.Class, boolean)
	 */
	 
	public <N extends Object>List<N> listBySql(String sql, Object arg, Class<?> clz,
			boolean hasEntity) {
		return this.listBySql(sql, new Object[]{arg}, clz, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#listBySql(java.lang.String, java.lang.Class, boolean)
	 */
	 
	public <N extends Object>List<N> listBySql(String sql, Class<?> clz, boolean hasEntity) {
		return this.listBySql(sql, null, clz, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#listBySql(java.lang.String, java.lang.Object[], java.util.Map, java.lang.Class, boolean)
	 */
	 
	public <N extends Object>List<N> listBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		sql = initSort(sql);
		SQLQuery sq = getSession().createSQLQuery(sql);
		setAliasParameter(sq, alias);
		setParameter(sq, args);
		if(hasEntity){
			sq.addEntity(clz);
		}else{
			sq.setResultTransformer(Transformers.aliasToBean(clz));
		}
		return sq.list();
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#listBySql(java.lang.String, java.util.Map, java.lang.Class, boolean)
	 */
	 
	public <N extends Object>List<N> listByAliasSql(String sql, Map<String, Object> alias,
			Class<?> clz, boolean hasEntity) {
		return this.listBySql(sql, null, alias, clz, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#findBySql(java.lang.String, java.lang.Object[], java.lang.Class, boolean)
	 */
	 
	public <N extends Object>Pager<N> findBySql(String sql, Object[] args, Class<?> clz,
			boolean hasEntity) {
		return this.findBySql(sql, args, null, clz, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#findBySql(java.lang.String, java.lang.Object, java.lang.Class, boolean)
	 */
	 
	public <N extends Object>Pager<N> findBySql(String sql, Object arg, Class<?> clz,
			boolean hasEntity) {
		return this.findBySql(sql, new Object[]{arg}, clz, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#findBySql(java.lang.String, java.lang.Class, boolean)
	 */
	 
	public <N extends Object>Pager<N> findBySql(String sql, Class<?> clz, boolean hasEntity) {
		return this.findByAliasSql(sql, null, clz, hasEntity);
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#findBySql(java.lang.String, java.lang.Object[], java.util.Map, java.lang.Class, boolean)
	 */
	 
	public <N extends Object>Pager<N> findBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		String cq = getCountHql(sql, false);
		cq = initSort(cq);
		sql = initSort(sql);
		SQLQuery sq = getSession().createSQLQuery(sql);
		Query cquery = getSession().createSQLQuery(cq);
		setAliasParameter(cquery, alias);
		setParameter(cquery, args);
		setAliasParameter(sq, alias);
		setParameter(sq, args);
		Pager<N> pages = new Pager<N>();
		setPages(sq, pages);
		if(hasEntity){
			 sq.addEntity(clz);
		}else{
			sq.setResultTransformer(Transformers.aliasToBean(clz));
		}
		List<N> datas = sq.list();
		pages.setDatas(datas);
		long total = ((BigInteger) cquery.uniqueResult()).longValue();
		pages.setTotal(total);
		return pages;
	}

	/* (non-Javadoc)
	 * @see org.xyq.basic.dao.IBasicDao#findBySql(java.lang.String, java.util.Map, java.lang.Class, boolean)
	 */
	 
	public <N extends Object>Pager<N> findByAliasSql(String sql, Map<String, Object> alias,
			Class<?> clz, boolean hasEntity) {
		return this.findBySql(sql, null, alias, clz, hasEntity);
	}

}
