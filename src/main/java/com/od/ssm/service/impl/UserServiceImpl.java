package com.od.ssm.service.impl;

import com.od.ssm.util.StringToJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.support.SimpleCacheManager;
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

//	@Cacheable(value ="userCache",key="#user.phoneNumber.concat(#user.password)")
	public User validateAndGetUserSignInMessage(User user){
		User tem = userMapper.validateAndGetUserSignInMessage(user);
	    System.out.println("调用了数据库");
		return tem;
	}


	public int ajaxCheackUserIfRe(User user){

		int count = userMapper.selectCount(user);
		return count;

	}

	public User addUserAndGetIt(User user){
		//这里将用户的用户名设置成跟手机号码一样
		user.setName(user.getPhoneNumber());
		userMapper.insert(user);
		return user;
	}
}
