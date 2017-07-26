package com.od.ssm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 黄冠莳 on 2017/7/19.
 */
public class WangEditor_Pic {

    public static void addPic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
        ObjectMapper objm = new ObjectMapper();
        //项目中的保存路径(本地服务器)
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/wangEditorUploadImage/";
        String path = request.getRealPath("/wangEditorUploadImage/");
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        String fileName = "";// 文件名称
        /**上传文件处理内容**/
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setHeaderEncoding("UTF-8"); // 处理中文问题
        sfu.setSizeMax(1024 * 1024); // 限制文件大小
        Map<String , Object> map = new HashMap<String , Object>(); //json map
        try {
            List<String> data = new ArrayList<String>(); //路径集合
            List<FileItem> fileItems = sfu.parseRequest(request); // 解码请求
            map.put("errno", 0);
            for (FileItem fi : fileItems) {
                fileName = sdfDate.format(new Date())+fi.getName().substring(fi.getName().lastIndexOf("."),fi.getName().length());
                fi.write(new File(path, fileName));
                //copyFile(fi.getInputStream(),path,fileName);
                data.add(basePath+fileName);
            }
            map.put("data", data);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("errno", 1);
            map.put("data", new ArrayList());
        }
        //获取图片url地址
        String imgUrl = objm.writeValueAsString(map);
        response.setContentType("text/text;charset=utf-8");
        PrintWriter out = response.getWriter();
        System.out.println(imgUrl);
        out.print(imgUrl);  //返回url地址
        out.flush();
        out.close();
    }
}
