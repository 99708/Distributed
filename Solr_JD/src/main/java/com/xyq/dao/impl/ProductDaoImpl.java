package com.xyq.dao.impl;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Repository;

import com.xyq.dao.ProductDao;
import com.xyq.entity.Parameter;

@Repository
public class ProductDaoImpl implements ProductDao {
	
	@Resource
	private HttpSolrServer server;

	@Override
	public QueryResponse getProduct(Parameter parameter) {
		
		QueryResponse response = null;
		
		try {
			//1、创建solrquery对象
			SolrQuery solrQuery = new SolrQuery();
			
			//2、封装查询条件
				//分装默认检索字段
				solrQuery.set("df", "pro_name");
				//分装主检索字段
				String pro_name = parameter.getPro_name();
				if(!"".equals(pro_name) && null!=pro_name) {
					solrQuery.setQuery(pro_name);
				}else {
					solrQuery.set("q", "*:*");
				}
				//添加类别筛选
				String pro_catalog_name = parameter.getPro_catalog_name();
				if(!"".equals(pro_catalog_name) && null!=pro_catalog_name) {
					solrQuery.addFilterQuery("pro_catalog_name:"+pro_catalog_name);
				}
				
				//添加价格筛选
				Double min = parameter.getMin();
				Double max = parameter.getMax();
				if(min != null && max != null) {
					if(max == -1) {
						max = Double.MAX_VALUE;
					}
					solrQuery.addFilterQuery("pro_price:["+min+ " TO "+max+"]");
				}
				//添加价格排序
				if(parameter.getSort() == 1) {
					solrQuery.addSort("pro_price", ORDER.asc);
				}else {
					solrQuery.addSort("pro_price", ORDER.desc);
				}
				
				//设置分页
				int pageStart = (parameter.getPageNum()-1)*parameter.getPagesize();
				solrQuery.setStart(pageStart);
				solrQuery.setRows(parameter.getPagesize());
				
				//设置高亮字段
				solrQuery.setHighlight(true);
				solrQuery.addHighlightField("pro_name");
				solrQuery.setHighlightSimplePre("<font color='red'>");
				solrQuery.setHighlightSimplePost("</font>");
			
			//3、执行查询
			response = server.query(solrQuery);
			
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return response;
	}

}
