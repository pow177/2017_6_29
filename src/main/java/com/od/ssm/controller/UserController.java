package com.od.ssm.controller;

import com.od.ssm.bean.PackagBean_User_str;
import com.od.ssm.po.BookTable;

import com.od.ssm.po.User;
import com.od.ssm.po.phoneAndCode;
import com.od.ssm.service.BookTableService;
import com.od.ssm.service.UserService;
import com.od.ssm.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.Multipart;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.*;

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

        //1.验证是否存在cookieKey
       if(request.getCookies()!=null) {
           Cookie[] cookies = request.getCookies();
           for (int i = 0; i < cookies.length; i++) {
               if (cookies[i].getName().equals("cookieKey")) {
                   String cookieKey = cookies[i].getValue();
                   User temUser = new User();
                   temUser.setCookieKey(cookieKey);
                   User user = userService.getUserByParame(temUser);         //根据cookieKey查找用户信息
                   request.getSession().setAttribute("user", user);    //将user 信息存入session
               }
           }
       }
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
    public String signIn(HttpServletRequest request, HttpServletResponse response, @RequestBody PackagBean_User_str userAndStr){
//验证用户信息是否存在
        User tem = userService.validateAndGetUserSignInMessage(userAndStr.getUser());

        if(tem!=null){
            //checkBox 记住密码 若不为空
            System.out.println("remeber:           "+ userAndStr.getRemember());
            if(!"".equals(userAndStr.getRemember())){
               String phoneNumber =  tem.getPhoneNumber();
                String uuid = UUID.randomUUID().toString();
                String cookieKey = MD5.stringMD5(uuid+"$"+phoneNumber);
                //新建对象用于更新
                User cookieKeyTemUser = new User();
                cookieKeyTemUser.setCookieKey(cookieKey);
                cookieKeyTemUser.setU_id(tem.getU_id());
               //执行更新
                userService.updateUser(cookieKeyTemUser);
                //将md5 加密过的cookieKey放入cookie
                Cookie cookie =new Cookie("cookieKey",cookieKey);
                cookie.setMaxAge(3600);     //一个小时
                response.addCookie(cookie);
            }

            //这里是用户信息strting->json转换并返回
            JsonMapper jm = new JsonMapper();
            String jsonUserMessage =  jm.toJson(tem);

            request.getSession().setAttribute("user",tem);
            return StringToJson.strToJson("success");

        }
        return StringToJson.strToJson("fail");
    }



    @RequestMapping("/signOut.action")
    @ResponseBody
    public String signOut(HttpServletRequest request,HttpServletResponse response){

        //1.验证是否存在cookieKey，将cookie的存在时间设置为0
        if(request.getCookies()!=null) {
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("cookieKey")) {
                    Cookie cookie = cookies[i];
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }

        //移除session
    request.getSession().removeAttribute("user");

    return StringToJson.strToJson("success");

    }

    //非遮罩框页面登录
    @RequestMapping("/idpSign")
    public String idpSign(HttpServletRequest request,HttpServletResponse response,User user,RedirectAttributes redirect,String remember){
        //查询
    User tem = userService.validateAndGetUserSignInMessage(user);
    if(tem!=null){
        //checkBox 记住密码 若不为空
        System.out.println("remeber: "+remember);
        if(!"".equals(remember)&&remember!=null){
            String phoneNumber =  tem.getPhoneNumber();
            String uuid = UUID.randomUUID().toString();
            String cookieKey = MD5.stringMD5(uuid+"$"+phoneNumber);
            //新建对象用于更新
            User cookieKeyTemUser = new User();
            cookieKeyTemUser.setCookieKey(cookieKey);
            cookieKeyTemUser.setU_id(tem.getU_id());
            //执行更新
            userService.updateUser(cookieKeyTemUser);
            //将md5 加密过的cookieKey放入cookie
            Cookie cookie =new Cookie("cookieKey",cookieKey);
            cookie.setMaxAge(3600);     //一个小时
            response.addCookie(cookie);
        }

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
//        System.out.println("Code:      "+idCode);

//            发送验证码短信
        MyPhoneMessage.sendIdCodeMessage(idCode,phoneNumber) ;

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

                //获取完值移除session里 check_phoneNumber  check_idCode的信息
                request.getSession().removeAttribute("check_phoneNumber");
                request.getSession().removeAttribute("check_idCode");
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
                redirect.addFlashAttribute("reShowPassword",user.getEmail());
            return "redirect:/page/sign-up3-2.action";  //返回原来的视图
        }
        //2.插入用户信息
           User tem = userService.addUserAndGetIt(user);
            request.getSession().setAttribute("user",tem);   //用户信息存到session，实现注册完自动登录
        return "redirect:/gallery.html";
    }

//更新用户名
    @RequestMapping("/updataUserName")
@ResponseBody
    public String updataUserName(@RequestBody@Validated User user,BindingResult result,HttpServletRequest request){
        if(result.hasErrors()){
            return StringToJson.strToJson("fail");
        }
        userService.updateUser(user);

        //考虑减少数据库操作，直接从session拿值并对齐进行修改实现更新数据
    User tem = (User)request.getSession().getAttribute("user");
    tem.setName(user.getName());
    request.getSession().setAttribute("user",tem);
    //更新session完成
    return StringToJson.strToJson("success");
    }

    //更新或插入心情留言
    @RequestMapping("/updateMoodMessage")
    @ResponseBody
    public String updateOrinsertMoodMessage(@Validated@RequestBody User user,BindingResult result,HttpServletRequest request){
        if(result.hasErrors()){
            return StringToJson.strToJson("fail");
        }
      userService.updateMoodMessage(user);

     User tem =    (User)request.getSession().getAttribute("user");
     tem.setMoodMessage(user.getMoodMessage());
     request.getSession().setAttribute("user",tem);
     //更新session user信息
        return StringToJson.strToJson("success");

    }

    //获得用户上传的图片
    @RequestMapping("/sendImg")
    public String sendImg(MultipartFile img,HttpServletRequest request) throws Exception{
        String cStr = img.getOriginalFilename();
       String regEx = "jpg$|jpeg$|png$";
        Pattern p= Pattern.compile(regEx);
        Matcher m=p.matcher(cStr);
        boolean result=m.find();
        System.out.println("result: "+result);
        if(!result){ //格式错误重定向到提示错误的页面
            return "redirect:/page/uploadErrorPage.action";
        }
        //后台验证文件是否为空
        if(img==null){
            return "redirect:/page/uploadErrorPage.action";
        }
        //存图片的路径
//       String imgSaveUrl = "D:\\idea_WordSpace\\maven2-ssm\\target\\pow-1.0-SNAPSHOT\\resources\\userImgTem\\";// "D:\\idea_WordSpace\\maven2-ssm\\src\\main\\webapp\\resources\\userImgTem\\";
        String imgSaveUrl  = request.getRealPath("/userImgTem");        //这样获取会少一个resources目录，不知道为什么

        System.out.println("地址:  "+imgSaveUrl);
        String orgFileName = img.getOriginalFilename();
        //原始图片名称
      //新图片名称
        String newFileName = UUID.randomUUID()+orgFileName.substring(orgFileName.lastIndexOf(".")); //uuid随机数+后缀名

        File  newFile = new File(imgSaveUrl+"/"+newFileName);
        //内存的数据=>磁盘中
        img.transferTo(newFile);


        //这里直接存入session,下一步若用户选择更新的时候，更新完再移除session
        request.getSession().setAttribute("imgUrl","http://47.93.49.124:81/tasty/userImgTem/"+newFileName);
        //返回
        return "redirect:/page/userZoom.action";

    }

    @RequestMapping("updateUserImg")
    public String updateUserImg(@Validated User user,BindingResult result,HttpServletRequest request){
      //验证图片的格式上个步骤完成了
        userService.updateUser(user);
       //更新完后更新session user的信息
        User tem = (User)request.getSession().getAttribute("user");
        tem.setImgUrl(user.getImgUrl());
        //移除session imgUrl的值
        request.getSession().removeAttribute("imgUrl");
        return "redirect:/page/userZoom.action";

    }


    //忘记密码找回(验证手机号跟邮箱是否一致）
    @RequestMapping("/validPhoneNumberAndEmail")
    @ResponseBody
    public String validPhoneNumberAndPassWord(@RequestBody User user){
       int keyId =  userService.validPhoneNumberAndEmailAndGetId(user);
       if(keyId>0){
           String[] str = {"success",String.valueOf(keyId)};         // success   keyId
           return StringToJson.listToJson(str);
       }else{
           String[] str = {"fail",String.valueOf(keyId)};        //fail      0
           return StringToJson.listToJson(str);
       }

    }

    //(将找回密码的链接发送到邮箱内)
    @RequestMapping("/getPassWordByEmail")
    public String getPassWordByEmail(User user,HttpServletRequest request) throws Exception{
        //生成组成数字信息的各部分信息
        Timestamp outDate = new Timestamp(System.currentTimeMillis()+30*60*1000);//30分钟后过期
        String secretKey= UUID.randomUUID().toString();         //uuid key
        String phoneNumber = user.getPhoneNumber();              // 手机号
        long date = TimeIgnoreMilles.ingnoreMilles(outDate.getTime());                         //date,忽略毫秒
       //将信息保存到数据库
        user.setValidataCode(secretKey);
        user.setRegisteDate(outDate);
        userService.saveValidMessage(user);

        String sid = secretKey+"$"+phoneNumber;       //uuid生成的key +phoneNumber构成数字签名
        System.out.println("sid 刚生成的:"+sid);
        String message = MD5.stringMD5(sid);                 //md5 加密

        System.out.println("sid: 刚生成的"+message);

        //发送邮件
//        String path = request.getContextPath();
//        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
        String resetPassHref =  "http://47.93.49.124:81/tasty/resetPassword.action?sid="+message+"&&phoneNumber="+user.getPhoneNumber();
        String emailContent = "请勿回复本邮件.点击下面的链接,重设密码<br/><a href=\""+resetPassHref +"\" target=\"_BLANK\">点击我重新设置密码</a>" +
                "<br/>tips:本邮件超过30分钟,链接将会失效";
        System.out.println(emailContent);
        MyEmail.sendMail(emailContent,user.getEmail());


        return "redirect:/page/sendEmailSuccess.action";
    }

    @RequestMapping("/resetPassword")
    public String resetPassword(String sid,String phoneNumber,HttpServletRequest request){
     System.out.println("sid:  "+sid);
     System.out.println("phoneNumber  "+phoneNumber);
      if(sid.equals("")||sid==null){
          System.out.println("链接不完整");
          return "resetPasswordFailPage";   //链接参数不完整
      }
        if(phoneNumber.equals("")||phoneNumber==null){
            return "resetPasswordFailPage";   //链接参数不完整
        }
        //用于查查询的tem User对象
        User cheTem = new User();
        cheTem.setPhoneNumber(phoneNumber);

        User tem1 = userService.getUserByParame(cheTem); //根据手机号码查询信息

        if(tem1==null){
            return "resetPasswordFailPage";   //链接失效，用户不存在
        }
        String key = tem1.getValidataCode();  // uuid code
        Timestamp outTime = tem1.getRegisteDate();
        long date =TimeIgnoreMilles.ingnoreMilles(outTime.getTime()) ;              //date
        String phone_number = tem1.getPhoneNumber();       //phoneNumber
        String sidTem = key+"$"+phone_number;

        System.out.println("sidTem:   "+sidTem);
        System.out.println("nd5Tem: "+MD5.stringMD5(sidTem));
        //看看当前时间是否已经超过三十分钟
        if(date<=System.currentTimeMillis()){
            System.out.println("链接过期");
            return "resetPasswordFailPage";//链接过期
        }

        //验证数字信息
        if(!MD5.stringMD5(sidTem).equals(sid)){
            System.out.println("数字信息有误");
            return "resetPasswordFailPage";//链接失效，用户数据库信息丢失，
        }
        request.getSession().setAttribute("resetUser_Id",tem1.getU_id());
        request.getSession().setAttribute("resetUser_phoneNumber",tem1.getPhoneNumber());
        request.getSession().setAttribute("resetPassword_active","true");
         return "redirect:/page/resetPassword.action";
    }

    @RequestMapping("/updataPassWord")
    public String updataPassWord(@Validated User user,BindingResult result,String check_phoneNumber,HttpServletRequest request){
      if(result.hasErrors()){//后台验证手机格式是否错误
          request.getSession().setAttribute("updataPassWordErrorMessage","你的手机号码或者密码输入格式有误");
          return "redirect:/page/updataPassWordErrorPage.action";
      }
      if(!user.getPhoneNumber().equals(check_phoneNumber)){ //验证修改密码的手机号码是否一致
          request.getSession().setAttribute("updataPassWordErrorMessage","你填写的手机号码不得修改");
          return "redirect:/page/updataPassWordErrorPage.action";
      }
        String str = (String)request.getSession().getAttribute("resetPassword_active");
        if(str==null){//验证是否可修改状态
            request.getSession().setAttribute("updataPassWordErrorMessage","你填写的手机号码不得修改");
            return "redirect:/page/updataPassWordErrorPage.action";
        }
        //更新密码的操作
        //将key清空，防止同封邮件进行多次修改密码的行为
        user.setValidataCode("");
        //移除该session，标识当前修改密码的页面不得修改密码
        request.getSession().removeAttribute("resetPassword_active");
        userService.updateUser(user);
      return "redirect:/page/updataPassWordSuccessPage.action";
    }


}
