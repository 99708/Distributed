package org.xyq.cms.web;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.xyq.cms.model.CmsException;
import org.xyq.cms.model.User;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		/**
		 * 如果使用uploadify进行文件的上传，由于flash的bug问题，会产生一个新的session，此时验证失败
		 * 所以必须在此处获取一个原有的session，然后重置session信息
		 */
		String sid = request.getParameter("sid");
		if(sid != null && !"".equals(sid.trim())){
			//当sid有值时，就表示是通过uploadify上传文件，此时应该获取一个原有的session
			session = CmsSessionContext.getSession(sid);
		}
		HandlerMethod hm = (HandlerMethod) handler;
		User user = (User) session.getAttribute("loginUser");
		System.out.println(user);
		if(user == null){
			response.sendRedirect(request.getContextPath()+"/login");
		}else{
			boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
			System.out.println(isAdmin);
			if(!isAdmin){
				Set<String> actions = (Set<String>) session.getAttribute("allActions");
				String aname = hm.getBean().getClass().getName()+"."+hm.getMethod().getName();
				if(!actions.contains(aname)){
					throw new CmsException("没有权限访问该功能");
				}
	 		}
		}
		return super.preHandle(request, response, handler);
	}
}
