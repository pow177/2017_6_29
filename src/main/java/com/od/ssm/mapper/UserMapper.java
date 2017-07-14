package com.od.ssm.mapper;

import com.od.ssm.po.User;
import tk.mybatis.mapper.common.ExampleMapper;
import tk.mybatis.mapper.common.Mapper;


public interface UserMapper extends Mapper<User> {
//根据手机号码跟密码验证用户信息是否存在
    public User validateAndGetUserSignInMessage(User user);

}
