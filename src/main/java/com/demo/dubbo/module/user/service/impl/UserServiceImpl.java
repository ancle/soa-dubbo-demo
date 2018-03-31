package com.demo.dubbo.module.user.service.impl;

import com.demo.dubbo.module.user.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public String say() {
		return "say hello";
	}

}
