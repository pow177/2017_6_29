package com.od.ssm.controller;

import com.od.ssm.po.BookTable;

import com.od.ssm.po.User;
import com.od.ssm.po.phoneAndCode;
import com.od.ssm.service.BookTableService;
import com.od.ssm.service.UserService;
import com.od.ssm.util.JsonMapper;
import com.od.ssm.util.MyIdCode;
import com.od.ssm.util.StringToJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        if(tem.getU_id()>0){
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

    //非遮罩框页面登录
    @RequestMapping("/idpSign")
    public String idpSign(HttpServletRequest request,User user,RedirectAttributes redirect){
        //查询
    User tem = userService.validateAndGetUserSignInMessage(user);
    if(tem!=null){
       request.getSession().setAttribute("user",user);
       return "redirect:/gallery.html";
    }
   //登录失败返回提示错误信息
        redirect.addFlashAttribute("reShowUser",user);
        redirect.addFlashAttribute("erorMessage","账号或者密码错误!");
    return "redirect:/page/sign-in3.action";
}

//验证用户注册是否已经注册了手机号码
@RequestMapping("/checkPhoneNumberIfRe")
@ResponseBody
public String  checkPhoneNumberIfRe(@RequestBody @Validated User user,BindingResult result){
        //1.服务器验证手机格式
    if(result.hasErrors()){
        //这里因为只检验了phoneNumber的格式，所以不再去取result里面的错误信息了,只需判断
        //是否有错误信息
        return StringToJson.strToJson("phoneNumberError");
    }
        int count = userService.ajaxCheackUserIfRe(user);
        if(count>0){
            return StringToJson.strToJson("fail");
        }
           return  StringToJson.strToJson("success");


}

    @RequestMapping("/getIdCode")
    //记得配置全局异常处理器
    @ResponseBody
    public String getIdCode(HttpServletRequest request,@RequestBody@Validated User user,BindingResult result)throws Exception{
    System.out.println("进入了cotroller");
   if (result.hasErrors()){
       return StringToJson.strToJson("phoneError");
   }
        //2.
   MyIdCode code = new MyIdCode(); //实例化工具类idCode对象

        String phoneNumber = user.getPhoneNumber();   //获得手机号码
        String idCode = code.getIdCode();  //获得随机生成的6位验证码
System.out.println("Code:      "+idCode);

            //发送验证码短信
//        MyPhoneMessage.sendIdCodeMessage(Code,phoneNumber) ;

        //将手机号码跟对应的idCode放入session中
        request.getSession().setAttribute("check_phoneNumber",phoneNumber);
        request.getSession().setAttribute("check_idCode",idCode);
        return StringToJson.strToJson("success");
    }

    @RequestMapping("/checkIdCode")
    @ResponseBody
    public String checkIdCode(@RequestBody phoneAndCode pc, HttpServletRequest request){

        //check_phoneNumber跟check_idCode是生成验证码时候存入session的，用来识别手机跟验证码是否一致
        String check_phoneNumber = (String) request.getSession().getAttribute("check_phoneNumber");
        String check_idCode = (String) request.getSession().getAttribute("check_idCode");
        //获取完值移除session里 check_phoneNumber  check_idCode的信息
        request.getSession().removeAttribute("check_phoneNumber");
        request.getSession().removeAttribute("check_idCode");
                //phoneNumber跟idCode是从页面表单传过来的数据。
        String phoneNumber = pc.getPhoneNumber();
        String idCode = pc.getIdCode();

        //问题:这里可能出现用户不点击前端 获取验证码 随便填入的情况，这样session中并没有保存check_phoneNumber跟check_idCode的值
        //解决:这里加多一个判断，来判断用户有没有点击了获取验证码，没有则返回页面显示提示信息。

        if(check_phoneNumber==null&&check_idCode==null){                //为空则证明未通过前台的 获取验证码的方式去获取过验证码。直接返回fail
            return StringToJson.strToJson("fail");
        }else{
            if(phoneNumber.equals(check_phoneNumber)&&         //验证码跟手机号码是否一致
                    idCode.equals(check_idCode)){
                return StringToJson.strToJson("success");
            }
        }
        return StringToJson.strToJson("fail");                              //验证码跟手机号码不一致，返回fail
    }


    @RequestMapping("/sign-up-3Next")
    public String signUp3Next(HttpServletRequest request,RedirectAttributes redirect) {
            String phoneNumber = request.getParameter("phoneNumber");
            //问题：在sign-up3-2视图中，如果刷新了页面重新请求多一次视图的话，会导致request中获取不到phoneNumber的值
        //解决：把phoneNumber放入session中，通过page controller时候每次通过session重新获取一次phoneNumer放入页面的hidden中，注册完移出session
            request.getSession().setAttribute("hidden_phoneNumber",phoneNumber);  //这里把号码存到session

        return "redirect:/page/sign-up3-2.action"; //重定向到注册填写密码的视图
    }

    @RequestMapping("/addUser")
    public String addUser(@Validated User user,BindingResult result,RedirectAttributes redirect
            ,HttpServletRequest request){

        //1.后台检验是否存在格式不正确的情况（这里一般不会发生）
            if(result.hasErrors()){
                redirect.addFlashAttribute("errorMessage","发生了错误");
                redirect.addFlashAttribute("reShowPhoneNumber",user.getPhoneNumber());
                redirect.addFlashAttribute("reShowPassword",user.getPassword());
            return "redirect:/page/sign-up3-2.action";  //返回原来的视图
        }
        //2.插入用户信息
           User tem = userService.addUserAndGetIt(user);
            request.getSession().setAttribute("user",tem);   //用户信息存到session，实现注册完自动登录
        return "redirect:/gallery.html";
    }




}
