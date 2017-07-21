package com.od.ssm.bean;

import com.od.ssm.po.pageBean;
import com.od.ssm.po.User;

/**
 * Created by 黄冠莳 on 2017/7/21.
 */
public class PackagBean_Comment {

    private Integer c_id;
    private String c_words;
    private Integer u_id;
    private String c_date;
    private String pic_url;
    private User user;

    private pageBean pageBean;

    public PackagBean_Comment(Integer c_id,String c_words,Integer u_id,String c_date,String pic_url,User user,pageBean pageBean){
        this.c_id = c_id;
        this. c_words= c_words;
        this. u_id = u_id;
        this. c_date = c_date;
        this. pic_url = pic_url;
        this.  user=  user;
        this.pageBean = pageBean;
    }

    public PackagBean_Comment(){}

    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }

    public String getC_words() {
        return c_words;
    }

    public void setC_words(String c_words) {
        this.c_words = c_words;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public String getC_date() {
        return c_date;
    }

    public void setC_date(String c_date) {
        this.c_date = c_date;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public pageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(pageBean pageBean) {
        this.pageBean = pageBean;
    }
}
