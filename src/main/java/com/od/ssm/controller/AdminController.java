package com.od.ssm.controller;

import com.od.ssm.bean.ResultBean;
import com.od.ssm.mapper.BookTableMapper;
import com.od.ssm.po.Admin;
import com.od.ssm.po.BookTable;
import com.od.ssm.service.AdminService;
import com.od.ssm.service.BookTableService;
import com.od.ssm.util.ReadWriteExcelFile;
import com.od.ssm.util.StringToJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;



/**
 * Created by 黄冠莳 on 2017/7/24.
 */
@RequestMapping("/admin")
@Controller
public class AdminController {
@Autowired
private AdminService adminService;

@Autowired
private BookTableService bookTableService;

    @RequestMapping("/validate")
    public String validate(HttpServletRequest request, @Validated Admin admin, BindingResult result){
       //验证信息是否有问题
        if(result.hasErrors()){
            request.setAttribute("oldAdminMessage",admin);        //信息存入request,用于回显
            request.setAttribute("resultBean",new ResultBean("信息存在错误",false));       //设置提示信息
            return "admin/adminSign_in";
        }
        //查询信息是否存在
       String message  =  adminService.validateMessage(admin);
       //成功
        if(message.equals("success")){
            return "redirect:/page/table1.action";      //登录成功进入管理页面
        }
        //失败
        request.setAttribute("oldAdminMessage",admin);        //信息存入request,用于回显
        request.setAttribute("resultBean",new ResultBean("你的帐号密码错误",false));       //设置提示信息
        return "admin/adminSign_in";
    }

    @RequestMapping("/signOut")
    public String signOut(HttpServletRequest request){
        request.getSession().removeAttribute("admitKey");
        return "redirect:/gallery.html";
    }


    @RequestMapping("/listBookTable")
    @ResponseBody
    //bootstap 获取所有数据
    public String listBookTable(){
        return  bookTableService.listAllBookTables();
    }

    @RequestMapping("/updateBookTable")
    @ResponseBody
    public String updateBookTable(@RequestBody @Validated BookTable bookTable,BindingResult result){
        //验证信息格式
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
            bookTableService.updateBookTable(bookTable);

        return StringToJson.strToJson("success");
    }

    @RequestMapping("/delBookTable")
    @ResponseBody
    public String delBookTable(@RequestBody@Validated BookTable bookTable,BindingResult result,RedirectAttributes redirectAttributes){
        if(result.hasErrors()){

            return StringToJson.strToJson("fail");
        }
        bookTableService.deleteBookTable(bookTable);

        return StringToJson.strToJson("success");
    }

    @RequestMapping("/readExcel")
    public String readExcel(MultipartFile excelFile, RedirectAttributes redirectAttributes) throws Exception{
        System.out.println("MultipartFile:  "+excelFile!=null);
        try {
            if (excelFile != null) {
                InputStream inputStream = excelFile.getInputStream();
                ReadWriteExcelFile rwef = new ReadWriteExcelFile();

                List<BookTable> bookTableList = rwef.readXLSXFile(inputStream);      //方法返回list集合
                bookTableService.insertListBookeTable(bookTableList);                      //调用接口批量插入数据
            }
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("excelMessage",new ResultBean("发生未知错误(文件格式有误)",false));
            return "redirect:/page/table1.action";
        }
        redirectAttributes.addFlashAttribute("excelMessage",new ResultBean("导入成功",true));
        return  "redirect:/page/table1.action";
    }

    @RequestMapping("/writeExcel")

    public String writeExcel(HttpServletResponse response) throws Exception{
       List<BookTable> bookTableList = bookTableService.getAllBookTables();

        ReadWriteExcelFile rwef = new ReadWriteExcelFile();
        //使用封装好的方法完成导出
        rwef.writeXLSXFile(response,bookTableList);

        return StringToJson.strToJson("success");

    }


}
