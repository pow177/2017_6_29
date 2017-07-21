package com.od.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 黄冠莳 on 2017/7/15.
 * 用于纯属访问WEB-INF下的页面的控制器
 */
@RequestMapping("/page")
@Controller
public class PageController {
    @RequestMapping("/sign-in3.action")
    public String signIn3(){
        return "sign-in3";
    }

    @RequestMapping("/sign-up3.action")
    public String signUp3(){
        return "sign-up3";
    }

    @RequestMapping("/forgot3.action")
    public String forgot3(){
        return "forgot3";
    }

    @RequestMapping("/sign-up3-2")
    public String signUp3_2(HttpServletRequest request){
       String phoneNumber = (String)request.getSession().getAttribute("hidden_phoneNumber");
       if(phoneNumber!=null) {
           request.setAttribute("hidden_phoneNumber", phoneNumber);
       }
        return "/sign-up3-2";
    }

}
