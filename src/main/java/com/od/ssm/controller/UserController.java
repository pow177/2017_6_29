package com.od.ssm.controller;


import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;




import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.od.ssm.po.User;
import com.od.ssm.service.UserService;
import com.od.ssm.service.impl.UserServiceImpl;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	//	public void setUserService(@Qualifier("com.od.ssm.service.impl.UserServiceimpl")
//	UserService userServiceimpl){
//		this.userService = userServiceimpl;
//	}
	@RequestMapping("/test")
	public ModelAndView test(User user){
//		mo.addObject("user",userService.getUser(id));
		return test2(user);
	}
	@RequestMapping("/test2")
	public ModelAndView test2(User user){
		ModelAndView mo = new ModelAndView();
		System.out.println(user.getId());
		mo.addObject("user",userService.getUser(user.getId()));

		mo.setViewName("success2");
		return mo;
	}
	@RequestMapping("/validateUsername")
	public  void validateUsername(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		ModelAndView mo = new ModelAndView();
		Integer tem = Integer.parseInt(request.getParameter("username"));
		User userTem = userService.getUser(tem);
		System.out.print("测试数据"+"" +tem+" " + userTem);
		response.setContentType("text/html");
		String result = "";
		if (userTem != null) {
			result = "<font color= 'red'>该用户已存在</font>";
			response.getWriter().print(result);


		} if (userTem == null){
			result = "<font color=‘green’>该用户名可用</font>";
			response.getWriter().print(result);
		}


	}
	@RequestMapping("/test3")
	public ModelAndView test3(User user){
		System.out.print("---------------------------------"+user.getUserName());
		ModelAndView mo = new ModelAndView();
		mo.setViewName("success2");

		mo.addObject("user",user);
		return mo;
	}


	@RequestMapping("/makeExcel")
	public void makeExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		OutputStream os = response.getOutputStream();// 取得输出流
		response.reset();// 清空输出流
		response.setHeader("Content-disposition", "attachment; filename=testRed.xls");// 设定输出文件头
		response.setContentType("application/msexcel");// 定义输出类型

		WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件
		String tmptitle = "测试数据"; // 标题
		WritableSheet wsheet = wbook.createSheet(tmptitle, 0); // sheet名称

		// 设置excel标题
		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		WritableCellFormat wcfFC = new WritableCellFormat(wfont);
		wcfFC.setBackground(Colour.AQUA);
		wsheet.addCell(new Label(1, 0, tmptitle, wcfFC));
		wfont = new jxl.write.WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false,
				UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
		wcfFC = new WritableCellFormat(wfont);

		// 开始生成主体内容
		wsheet.addCell(new Label(0, 2, "姓名"));
		wsheet.addCell(new Label(1, 2, "邮箱"));
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Map<String, String> map = new HashMap<String, String>();
		map.put("Red1", "it_red@sina.com");
		map.put("Red2", "it_red@sohu.com");
		map.put("Red3", "it_red@163.com");
		int count = 0;
		for (String key : map.keySet()) {
			wsheet.addCell(new Label(0, count + 3, key));
			wsheet.addCell(new Label(1, count + 3, map.get(key)));
			count++;
		}

		// 主体内容生成结束
		wbook.write(); // 写入文件
		wbook.close();
		os.close(); // 关闭流
	}


	@RequestMapping("/file")
	public void file(HttpServletRequest request,HttpServletResponse response) throws Exception {
		System.out.print(request.getParameter("---------------------------"));
		System.out.print(request.getParameter("filed"));

	}

	@RequestMapping("/datetable")
	@ResponseBody
	public String datetable(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> m = new HashMap<String, Object>();
//		m.put("id","1");
//		m.put("firstName","ming");
//		m.put("lastName","mi");
//		m.put("Operation","c");

		m.put("id","1");
		m.put("firstName","ming");
		m.put("lastName","mi");
		m.put("Operation","c");





		String json = new Gson().toJson(m);
		System.out.println("------------------------>"+json);

		json = "{\"data\":["+json+"]}";

		System.out.println("------------------------>"+json);

		return json;
//
// "{\"draw\":2," +
//				"\"recordsTotal\":11," +
//				"\"recordsFiltered\":11," +
//				"\"data\":[{\"id\":1,\"firstName\":\"MZ1\",\"lastName\":\"MZ2\",\"Operation\":\"OP\"}]}";
	}
}
