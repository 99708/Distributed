package org.xyq.cms.web;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.xyq.cms.auth.AuthUtil;

public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static WebApplicationContext wac;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//初始化Spring的工厂
		wac = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		//初始化权限信息
		Map<String, Set<String>> auths = AuthUtil.initAuth("org.xyq.cms.controller");
		this.getServletContext().setAttribute("allAuths", auths);
		System.out.println("-----------系统初始化成功："+auths+"---------");
	}
	public static WebApplicationContext getwac(){
		return wac;
	}

}
