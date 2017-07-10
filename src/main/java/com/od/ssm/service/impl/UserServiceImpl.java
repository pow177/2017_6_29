package com.od.ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.od.ssm.mapper.UserMapper;
import com.od.ssm.po.User;
import com.od.ssm.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	public void insertUser(User user){
		userMapper.insert(user);
	}
	
}
