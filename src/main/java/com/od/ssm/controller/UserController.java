package com.od.ssm.controller;

import com.od.ssm.po.BookTable;
import com.od.ssm.po.User;
import com.od.ssm.service.BookTableService;
import com.od.ssm.service.UserService;
import com.od.ssm.util.JsonMapper;
import com.od.ssm.util.StringToJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
    @ResponseBody
    public String insertUser(@RequestBody @Validated BookTable bt, BindingResult result) {

//1.
        if (result.hasErrors()) {          //判断是否存在错误信息
            List<ObjectError> results = result.getAllErrors();     //获得所有错误信息
            String[] messages = new String[results.size()];            //定义一个String数组存储错误信息
            int i = 0;//计数器
            for (ObjectError oje : results) {
                messages[i++] = oje.getDefaultMessage(); //将错误信息存储到String集合中
            }
            String jesonMessages = StringToJson.listToJson(messages);

            return jesonMessages;
        }
       //2.判断该手机号码，跟预定日期是否重复。是否已存在
        int count = bookTableService.selectByPhoneAndTime(bt);
        if(count>0){
            return StringToJson.strToJson("你已经用该号码预约了该时间用餐！");
        }
        //3.插入心的预定信息
        bookTableService.insertBookTableMessage(bt);
        return StringToJson.strToJson("预定座位成功！");
    }

    @RequestMapping("/checkUserIfLogin.action")
   @ResponseBody
    public  String checkUserIfLogin(HttpServletRequest request){
//        request.getSession().setAttribute("user",new User());
        User user = (User)request.getSession().getAttribute("user");
            if(user!=null){
                JsonMapper jm = new JsonMapper();
                String jsonMessage =  jm.toJson(user);
                return jsonMessage;
            }
    return StringToJson.strToJson("noLogin");
    }

    @RequestMapping("/sign-in.action")
   @ResponseBody
    public String signIn(HttpServletRequest request,@RequestBody @Validated User user,BindingResult result){

        //1.
        if (result.hasErrors()) {          //判断是否存在错误信息
            List<ObjectError> results = result.getAllErrors();     //获得所有错误信息
            String[] messages = new String[results.size()];            //定义一个String数组存储错误信息
            int i = 0;//计数器
            for (ObjectError oje : results) {
                messages[i++] = oje.getDefaultMessage(); //将错误信息存储到String集合中
            }
            String jesonMessages = StringToJson.listToJson(messages);

            return jesonMessages;
        }

//验证用户信息是否存在
        User tem = userService.validateAndGetUserSignInMessage(user);
        if(tem.getId()>0){
            JsonMapper jm = new JsonMapper();
            tem.setName(tem.getPhoneNumber());
            String jsonUserMessage =  jm.toJson(tem);

            request.getSession().setAttribute("user",tem);
            return StringToJson.strToJson("success");

        }
        return StringToJson.strToJson("fail");
    }

    //个人页面
@RequestMapping("/goToPersonZoo")
    public String goToPersonZoo(){
        return "PersonZoo";
}

    @RequestMapping("/signOut.action")
    @ResponseBody
    public String signOut(HttpServletRequest request){

    request.getSession().removeAttribute("user");

    return StringToJson.strToJson("success");

    }


}
