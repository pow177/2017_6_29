package com.od.ssm.bean;

import com.od.ssm.po.Comment;
import com.od.ssm.po.User;

/**
 * Created by 黄冠莳 on 2017/7/22.
 */
public class PackagBean_Reply {

    private Integer r_id;
    private String r_words;
    private User user;
    private Integer u_id;
    private Comment comment;
    private Integer c_id;
    private String r_date;
    private PageBean PageBean;

    public PackagBean_Reply(Integer r_id,String r_words,User user,Integer u_id,PageBean PageBean,Comment comment,Integer c_id,String r_date){
        this.r_id = r_id;
        this.r_words  =r_words;
        this.user =user;
        this.u_id = u_id;
        this.PageBean = PageBean;
        this.comment = comment;
        this.c_id = c_id;
        this.r_date = r_date;

    }
    public PackagBean_Reply(){}

    public Integer getR_id() {
        return r_id;
    }

    public void setR_id(Integer r_id) {
        this.r_id = r_id;
    }

    public String getR_words() {
        return r_words;
    }

    public void setR_words(String r_words) {
        this.r_words = r_words;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public PageBean getPageBean() {
        return PageBean;
    }

    public void setPageBean(PageBean PageBean) {
        this.PageBean = PageBean;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }



    public String getR_date() {
        return r_date;
    }

    public void setR_date(String r_date) {
        this.r_date = r_date;
    }

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }
}
