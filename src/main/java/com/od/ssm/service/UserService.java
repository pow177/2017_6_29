package com.od.ssm.service;

import com.od.ssm.po.User;

public interface UserService {
	public void insertUser(User user);
    public User validateAndGetUserSignInMessage(User user);
    public int ajaxCheackUserIfRe(User user);
    public User addUserAndGetIt(User user);
    public void updateUser(User user);
    public void updateMoodMessage(User user);
    public int validPhoneNumberAndEmailAndGetId(User user);
    public void saveValidMessage(User user);
    public User getUserByParame(User user);
}
