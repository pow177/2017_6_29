package com.od.ssm.controller;

import com.od.ssm.po.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 黄冠莳 on 2017/7/15.
 * 控制页面转发 的控制器
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
       if(phoneNumber==null){
          return "redirect:/gallery.html";     //若为空则表示存在直接访问该页的行为
       }
           request.setAttribute("hidden_phoneNumber", phoneNumber);
        return "/sign-up3-2";
    }
    //后台登录页面
    @RequestMapping("/admin")
        public String adminSign_in(HttpServletRequest request){
        if(request.getSession().getAttribute("adminTor")!=null){
            return "redirect:/page/table1.action";           //判断是否已经登录过，有的话直接跳过登录页面进入管理页面
        }
        return "/admin/adminSign_in";
    }

    @RequestMapping("/table1")
        public String table1(HttpServletRequest request){
            if(request.getParameter("excelMessage")!=null){
                request.setAttribute("excelMessage",request.getParameter("excelMessage"));
            }
            if(request.getParameter("delMessage")!=null){
                request.setAttribute("delMessage",request.getParameter("delMessage"));
            }
            return "/admin/table1";
        }

    @RequestMapping("/table2")
    public String table2(){
        return "admin/table2";
    }

    @RequestMapping("/table3")
    public String table3(){
        return "admin/table3";
    }

    @RequestMapping("/userZoom")
    public String personZoom(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/gallery.html";
        }
        return "userZoom";
    }
    @RequestMapping("/uploadErrorPage")
    public String uploadErrorPage(){
        return "uploadErrorPage";
    }
    @RequestMapping("/sendEmailSuccess")
    public String sendEmailSuccess(){
        return "sendEmailSuccessPage";
    }
    @RequestMapping("/resetPassword")
    public String resetPassword(){
        return "resetPassword";
    }
    @RequestMapping("/updataPassWordErrorPage")
    public String updataPassWordErrorPage(){
        return "updataPassWordErrorPage";
    }
    @RequestMapping("/updataPassWordSuccessPage")
    public String updataPassWordSuccessPage(){
        return "updataPassWordSuccessPage";
    }
}
