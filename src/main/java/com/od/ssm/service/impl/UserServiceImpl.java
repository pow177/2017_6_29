package com.od.ssm.service.impl;

import com.od.ssm.util.StringToJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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

	@Cacheable(value ="userCache",key="#user.phoneNumber.concat(#user.password)")
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
		//设置用户的头像图片
		user.setImgUrl("http://47.93.49.124:81/tasty/userImgTem/defaultPic.jpg");
		//插入后会映射对应的主键
		userMapper.insertAndGetKey(user);

		return user;
	}

	@CacheEvict(value = "userCache", allEntries=true) //若更新了资料，就清除缓存，value指定哪个cache,key指定清除的key
	public void updateUser(User user){
		userMapper.updateByPrimaryKeySelective(user);
		System.out.println("清理缓存");
	}

	@CacheEvict(value = "userCache", key = "#user.moodMessage")//若更新了资料，就清除缓存
	public void updateMoodMessage(User user){
		userMapper.updateByPrimaryKeySelective(user);
		System.out.println("清理缓存");
	}

	@Cacheable(value = "userCache", key = "#user.phoneNumber.concat(#user.email)")
	public int validPhoneNumberAndEmailAndGetId(User user){
		User tem = userMapper.selectOne(user);
		if(tem!=null){
			return tem.getU_id();
		}else{
			return 0;
		}
	}

	public void saveValidMessage(User user){
		userMapper.updateByPrimaryKeySelective(user);
	}

	public User getUserByParame(User user){
	   return userMapper.selectOne(user);
	}

}
