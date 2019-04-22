package org.xyq.cms.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xyq.cms.auth.AuthClass;
import org.xyq.cms.auth.AuthMethod;
import org.xyq.cms.web.CmsSessionContext;

@Controller
@AuthClass("login")
public class AdminController {
	@RequestMapping("/admin")
	@AuthMethod
	public String index(){
		return "admin/index";
	}
	
	@AuthMethod
	@RequestMapping("/admin/logout")
	public String logout(HttpSession session){
		CmsSessionContext.removeSession(session);
		System.out.println("移除了session"+session.getId());
		session.invalidate();
		return "redirect:/login";
	}
}
