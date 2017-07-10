package com.od.ssm.controller;

import com.od.ssm.po.BookTable;
import com.od.ssm.po.User;
import com.od.ssm.service.BookTableService;
import com.od.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 黄冠莳 on 2017/7/8.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookTableService bookTableService;
    @RequestMapping("/insertUser.action")
    public void insertUser(@RequestBody BookTable bt, BindingResult result){
        bookTableService.insertBookTableMessage(bt);
    }
}
