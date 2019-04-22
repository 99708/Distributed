package com.xyq.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xyq.entity.User;
import com.xyq.mapper.UserMapper;
import com.xyq.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	
	@Override
	public User selUserInfo(String uname, String pwd) {
		return userMapper.getUser(uname, pwd);
	}

}
