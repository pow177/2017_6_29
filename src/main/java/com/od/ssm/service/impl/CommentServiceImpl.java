package com.od.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.od.ssm.bean.PageBean;
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

    public String insertCommentAndGetNewComment(Comment comment){
        //插入新的评论
        commentMapper.insertComment(comment);
        //获得记录总数
        int count = commentMapper.selectCount(new Comment());
        int pageNum = count/5;

        if(count%5!=0){
            pageNum++;
        }

        //分页插件
        PageHelper.startPage(pageNum, 5);        //查询最后一页的数据
        PageHelper.orderBy("c_id");
        List<Comment> commentList =  commentMapper.getCommentAndUserByPage();

        //自定义的一个page
        PageBean temPb = new PageBean();
        temPb.setTotal(count);                //设置总记录数
        temPb.setPageSize(5);                     //每页显示的记录条数
        temPb.setPages(pageNum);
        //自定义包装类
        PackagBean_Comment pc = new PackagBean_Comment();
        pc.setPageBean(temPb);


        //用于存储组合数据的新list
        List<PackagBean_Comment> comments = new ArrayList<PackagBean_Comment>();
        //(Integer c_id,String c_words,Integer u_id,String c_date,String pic_url,User user,PageBean PageBean)
        //将commentList转成packageBean_Comment的组合数据类型
        for(Comment com:commentList){

            //将数据库取出来的img url 字符串 拆分 - >List<String>再存入包装类中
            List<String> temUrls  = new ArrayList<String>();
           String [] temS = com.getPic_url().split(",");
            if(temS.length>1&&temS[0].equals("")){ //两个以上将去掉分割后的第一个空集
                for(int i=1;i<temS.length;i++){
                    temUrls.add(temS[i]);
                }
            }else {
                for (int i = 0; i < temS.length; i++) {
                    temUrls.add(temS[i]);
                }
            }
           //拆分取出完成
            //装入包装类
            comments.add(new PackagBean_Comment(com.getC_id(),com.getC_words(),com.getU_id(),com.getC_date(),temUrls,com.getUser(),temPb));
        }

        //str ->Json
        JsonMapper jm = new JsonMapper();
        String message  = jm.toJson(comments);
        System.out.println(comments);
        return message;
    }





    public String getCommentByPage(PageBean pb){
      //1.获得数据

        //分页插件
        PageHelper.startPage(pb.getPageNum(), 5);        //第几页，几条
        PageHelper.orderBy("c_id");
        List<Comment> commentList =  commentMapper.getCommentAndUserByPage();

        int count = commentMapper.selectCount(new Comment());    //查询所有评论的记录数
        //自定义的一个page
            PageBean temPb = new PageBean();
            temPb.setTotal(count);                //设置总记录数
            temPb.setPageSize(5);                     //每页显示的记录条数

            if(count%5==0){
                temPb.setPages(count/5);
            }else {
                temPb.setPages(count/5+1);
            }

        //用来包装组合page跟Comment,方便json数据放回
        PackagBean_Comment pbc = new PackagBean_Comment();
      //用于存储组合数据的新list
       List<PackagBean_Comment> comments = new ArrayList<PackagBean_Comment>();
       //(Integer c_id,String c_words,Integer u_id,String c_date,String pic_url,User user,PageBean PageBean)
       for(Comment com:commentList){
           //将数据库取出来的img url 字符串 拆分 - >List<String>再存入包装类中
           List<String> temUrls  = new ArrayList<String>();
           String [] temS = com.getPic_url().split(",");
           if(temS.length>1&&temS[0].equals("")){ //两个以上将去掉分割后的第一个空集
               for(int i=1;i<temS.length;i++){
                   temUrls.add(temS[i]);
               }
           }else {
               for (int i = 0; i < temS.length; i++) {
                   temUrls.add(temS[i]);
               }
           }
           //拆分取出完成
           //装入包装类
           comments.add(new PackagBean_Comment(com.getC_id(),com.getC_words(),com.getU_id(),com.getC_date(),temUrls,com.getUser(),temPb));
        }

       //str ->Json
        JsonMapper jm = new JsonMapper();
        String message  = jm.toJson(comments);
        System.out.println(comments);
        return message;
    }

    public int getAllCounts(){
      return  commentMapper.selectCount(new Comment());
    }
}
