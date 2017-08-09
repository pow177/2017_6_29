package com.od.ssm.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

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
        org.codehaus.jackson.map.ObjectMapper objm = new org.codehaus.jackson.map.ObjectMapper();

        //项目中的保存路径(本地服务器)
        String basePath =  "http://47.93.49.124:81/tasty/wangEditorUploadImage/";
        String path = "";
        System.out.println(path);
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<?> iter = multiRequest.getFileNames();

            Map<String, Object> map = new HashMap<String, Object>(); //json map
            List<String> data = new ArrayList<String>(); //路径集合
            map.put("errno", 0);
            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if (file != null) {
                    String origname = file.getOriginalFilename();
                    //重新设置文件名
                    String fileName = sdfDate.format(new Date()) + origname.substring(origname.lastIndexOf("."), origname.length());
                    path = request.getRealPath("/wangEditorUploadImage/");

                    File fpath = new File(path);
                    if (!fpath.exists() && !fpath.isDirectory()) {
                        //创建目录
                        fpath.mkdirs();
                    }
                    data.add(basePath + fileName);
                    //上传
                    file.transferTo(new File(path, fileName));
                }
            }
            map.put("data", data);
            String imgUrl = objm.writeValueAsString(map);
            response.setContentType("text/text;charset=utf-8");
            PrintWriter out = response.getWriter();
            System.out.println("图片地址=====================" + imgUrl);

            out.print(imgUrl);  //返回url地址
            out.flush();
            out.close();

        }
    }
    }
