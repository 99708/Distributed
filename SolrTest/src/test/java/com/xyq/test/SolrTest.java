package com.xyq.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {
	
	/**
	 * @throws IOException 
	 * @throws SolrServerException 
	 * 更新数据
	 * @param:       
	 * @return: void      
	 * @throws
	 */
	@Test
	public void testUpdate() throws SolrServerException, IOException {
		//1、创建连接
		String url = "http://localhost:8081/solr/solrCore";
		HttpSolrServer httpSolrServer = new HttpSolrServer(url);
		//2、创建SolrInputDocument对象，用来存储要放入solr库中的数据
		SolrInputDocument sDocument = new SolrInputDocument();
		sDocument.addField("id", "5555");
		sDocument.addField("pro_name", "固态硬盘");
		//3、提交数据更新请求
		httpSolrServer.add(sDocument);
		//4、提交事务
		httpSolrServer.commit();
	}
	
	/**
	 * @throws IOException 
	 * @throws SolrServerException 
	 * 删除数据
	 * @param:       
	 * @return: void      
	 * @throws
	 */
	@Test
	public void testDelete() throws SolrServerException, IOException {
		
		//1、创建连接
		String url = "http://localhost:8081/solr/solrCore";
		HttpSolrServer httpSolrServer = new HttpSolrServer(url);
		//2、执行删除操作
		httpSolrServer.deleteById("5555");
		//3、提交
		httpSolrServer.commit();
	}
	
	@Test
	public void testSele() throws SolrServerException {
		
		//1、创建连接
		String url = "http://localhost:8081/solr/solrCore";
		HttpSolrServer httpSolrServer = new HttpSolrServer(url);
		//2、创建SolrQuery对象，用来封装查询参数
		SolrQuery solrQuery = new SolrQuery();
		//封装q查询
		solrQuery.set("q", "pro_name:幸福一家人");
		//封装fq查询
		solrQuery.addFilterQuery("pro_catalog_name:幽默杂货", "pro_price:[0 TO 20]");
		//封装排序
		solrQuery.addSort("pro_price", ORDER.desc);
		//封装分页
		solrQuery.setStart(0);
		solrQuery.setRows(2);
		//封装要查询的字段
		solrQuery.setFields("id", "pro_name");
		//进行索引库查询操作，返回查询结果
		QueryResponse response = httpSolrServer.query(solrQuery);
		//获得查询到的文档对象的集合
		SolrDocumentList results = response.getResults();
		//遍历集合
		for(SolrDocument sd : results) {
			System.out.println(sd.getFieldValue("id"));
			System.out.println(sd.getFieldValue("pro_name"));
			System.out.println(sd.getFieldValue("pro_catalog_name"));
			System.out.println(sd.getFieldValue("pro_price"));
			
			
			System.out.println("===============================================");
		}
		
	}
}