package com.xyq.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xyq.entity.User;
import com.xyq.service.UserService;

@Controller
public class UserController {

	@Resource
	private UserService userServiceImpl;
	
	@RequestMapping("login")
	public String login(String uname, String pwd) {
		User user = userServiceImpl.selUserInfo(uname, pwd);
		if(user != null) {
			return "redirect:/main.jsp";
		}else {
			return "redirect:/login.jsp";
		}
	}
}
