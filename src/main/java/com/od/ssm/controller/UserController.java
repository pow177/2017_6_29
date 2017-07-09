package com.od.ssm.controller;

import com.od.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 黄冠莳 on 2017/7/8.
 */
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


}
