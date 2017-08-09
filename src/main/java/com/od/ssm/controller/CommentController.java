package com.od.ssm.controller;

import com.od.ssm.bean.PageBean;
import com.od.ssm.po.Comment;
import com.od.ssm.po.Reply;
import com.od.ssm.po.User;
import com.od.ssm.service.CommentService;
import com.od.ssm.service.ReplyService;
import com.od.ssm.util.StringToJson;
import com.od.ssm.util.WangEditor_Pic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
import org.codehaus.jackson.map.ObjectMapper;
/**
 * Created by 黄冠莳 on 2017/7/18.
 */
@RequestMapping("/comment")
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ReplyService replyService;
    //异步插入评论,并返回更新的评论
    @RequestMapping("/insertComment.action")
    @ResponseBody
    public String insertComment(HttpServletRequest request, @RequestBody @Validated Comment comment, BindingResult result){
       //1.校验评论的数据格式
        if(result.hasErrors()){
            return StringToJson.strToJson("fail");
        }

        //从session获取user的信息
        User user =  (User) request.getSession().getAttribute("user");

        comment.setU_id(user.getU_id());       //将用户id设置成comment的外键值


        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        comment.setC_date(date);   //设置成当前时间

        String message = commentService.insertCommentAndGetNewComment(comment);      //插入数据，并且返回最后一页的数据过来

        return message;

    }


    @RequestMapping("/addPic.action")
    public void addPic(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        WangEditor_Pic.addPic(request,response);

    }


    @RequestMapping("/getCommentByPage.action")
    @ResponseBody
    public String getCommentByPage(@RequestBody PageBean PageBean){

       String commentMessageList =  commentService.getCommentByPage(PageBean);
        return commentMessageList;
    }


    //插入一条回复
    @RequestMapping("/insertReply")
    @ResponseBody
    public String insertReply(HttpServletRequest request, @RequestBody Reply reply) {
        //获取已登录的用户的信息
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return StringToJson.strToJson("fail");
        }
        //将用户的主键设置到reply u_id中
        reply.setR_u_id(user.getU_id());
        //当前评论时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
       //设置当前评论时间
        reply.setR_date(date);
        //插入一条新的评论
        String message = replyService.insertReply(reply);
        return message;   //返回最后一页的数据

    }


    //根据pageNum,c_id获得回复的数据
    @RequestMapping("/getReplyByPage.action")
    @ResponseBody
    public String getReplyByPage(@RequestBody PageBean pageBean){
       String message =  replyService.getReplyByPage(pageBean);
       return message;
    }

    @RequestMapping(value="/push",produces="text/event-stream")
    @ResponseBody
    public String push(){
        try {
            Thread.sleep(3000);       //三秒一次
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "data:"+commentService.getAllCounts()+"\n\n";
    }
    @RequestMapping("/getAllCounts")
    @ResponseBody
    public String getAllCounts(){
        int counts = commentService.getAllCounts();
        return StringToJson.strToJson(String.valueOf(counts));
    }



}
