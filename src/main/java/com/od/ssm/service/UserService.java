package com.od.ssm.service;

import com.od.ssm.po.User;

public interface UserService {
	public void insertUser(User user);
    public User validateAndGetUserSignInMessage(User user);
}
