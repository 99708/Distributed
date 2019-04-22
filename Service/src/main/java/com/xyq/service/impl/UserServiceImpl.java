package com.xyq.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.xyq.service.UserService;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {
	
	private static final long serialVersionUID = 1L;

	public UserServiceImpl() throws RemoteException {
	}

	@Override
	public String sayHello(String name) {
		return "Hello: "+name;
	}

}
