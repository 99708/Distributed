package com.xyq.main;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import com.xyq.service.UserService;
import com.xyq.service.impl.UserServiceImpl;

public class Provider {
	public static void main(String[] args) {
		try {
			//注册发布的远程服务端口
			LocateRegistry.createRegistry(8888);
			//设置访问url
			String url = "rmi://localhost:8888/rmi";
			//创建服务对象
			UserService userService = new UserServiceImpl();
			//绑定url
			Naming.bind(url, userService);
			
			System.out.println("====发布rmi远程服务======");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
