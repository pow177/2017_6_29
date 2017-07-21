package com.od.ssm.mapper;

import com.github.abel533.mapper.Mapper;
import com.od.ssm.po.User;




public interface UserMapper extends Mapper<User> {
//根据手机号码跟密码验证用户信息是否存在
    public User validateAndGetUserSignInMessage(User user);
    public User getUserById(Integer id);

}
