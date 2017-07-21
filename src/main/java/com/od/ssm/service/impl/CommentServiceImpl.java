package com.od.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.od.ssm.po.pageBean;
import com.od.ssm.bean.PackagBean_Comment;
import com.od.ssm.mapper.CommentMapper;
import com.od.ssm.po.Comment;
import com.od.ssm.service.CommentService;
import com.od.ssm.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黄冠莳 on 2017/7/18.
 */
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentMapper commentMapper;

    public String insertComment(Comment comment){

        commentMapper.insertComment(comment);
        return "success";
    }

    public String getCommentByPage(pageBean pb){
      //1.获得数据

        //分页插件
        PageHelper.startPage(pb.getPageNum(), 5);        //第几页，几条
        List<Comment> commentList =  commentMapper.getCommentAndUserByPage();

        int count = commentMapper.selectCount(new Comment());    //查询所有评论的记录数
        //自定义的一个page
            pageBean temPb = new pageBean();
            temPb.setTotal(count);                //设置总记录数
            temPb.setPageSize(6);                     //每页显示的记录条数

            if(count%5==0){
                temPb.setPages(count/5);
            }else {
                temPb.setPages(count/5+1);
            }

        //用来包装组合page跟Comment,方便json数据放回
        PackagBean_Comment pbc = new PackagBean_Comment();
      //用于存储组合数据的新list
       List<PackagBean_Comment> comments = new ArrayList<PackagBean_Comment>();
       //(Integer c_id,String c_words,Integer u_id,String c_date,String pic_url,User user,pageBean pageBean)
       for(Comment com:commentList){
           //将commentList转成packageBean_Comment的组合数据类型
           comments.add(new PackagBean_Comment(com.getC_id(),com.getC_words(),com.getU_id(),com.getC_date(),com.getPic_url(),com.getUser(),temPb));
        }

       //str ->Json
        JsonMapper jm = new JsonMapper();
        String message  = jm.toJson(comments);
        System.out.println(comments);
        return message;
    }

}
