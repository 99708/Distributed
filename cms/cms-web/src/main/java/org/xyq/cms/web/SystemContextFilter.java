package org.xyq.cms.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.xyq.basic.model.SystemContext;

public class SystemContextFilter implements Filter {
	private Integer pagerSize;
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		Integer offset = 0;
		try {
			Integer.parseInt(req.getParameter("pager.offset"));
		} catch (NumberFormatException e) {}
		try {
			SystemContext.setOrder(req.getParameter("order"));
			SystemContext.setSort(req.getParameter("sort"));
			SystemContext.setPageroffset(offset);
			SystemContext.setPagersize(pagerSize);
			SystemContext.setRealPath(((HttpServletRequest)req)
					.getSession().getServletContext().getRealPath("/"));
			chain.doFilter(req, resp);
		}
		finally{
			SystemContext.removeOrder();
			SystemContext.removePageroffset();
			SystemContext.removePagersize();
			SystemContext.removeSort();
			SystemContext.removeRealPath();
		}
	}

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		try {
			pagerSize = Integer.parseInt(cfg.getInitParameter("pagerSize"));
		} catch (NumberFormatException e) {
			pagerSize = 15;
		}
	}

}
