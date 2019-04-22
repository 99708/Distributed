package com.xyq.dao;

import org.apache.solr.client.solrj.response.QueryResponse;

import com.xyq.entity.Page;
import com.xyq.entity.Parameter;

public interface ProductDao {
	QueryResponse getProduct(Parameter parameter);
}
