package com.od.ssm.controller;

import com.od.ssm.po.pageBean;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    //异步插入评论
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
        String message = commentService.insertComment(comment);      //插入数据，成功则返回success

        return StringToJson.strToJson(message);
    }


    @RequestMapping("/addPic.action")
    public void addPic(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        WangEditor_Pic.addPic(request,response);

    }

    @RequestMapping("/getCommentByPage.action")
    @ResponseBody
    public String getCommentByPage(@RequestBody pageBean pageBean){

       String commentMessageList =  commentService.getCommentByPage(pageBean);
        return commentMessageList;
    }


    //插入一条回复
    @RequestMapping("/insertReply")
    @ResponseBody
    public String insertReply(HttpServletRequest request, @RequestBody Reply reply) {
        //获取已登录的用户的信息
        User user = (User) request.getSession().getAttribute("user");
        //将用户的主键设置到reply u_id中
        reply.setU_id(user.getU_id());
        //插入一条新的评论
    String message = replyService.insertReply(reply);

        return message;   //返回新插入的回复的json数据

    }
}
