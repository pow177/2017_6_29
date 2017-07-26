package com.od.ssm.bean;

import com.od.ssm.po.User;

import java.util.List;

/**
 * Created by 黄冠莳 on 2017/7/21.
 */
public class PackagBean_Comment {

    private Integer c_id;
    private String c_words;
    private Integer u_id;
    private String c_date;
    private List<String> pic_urls;
    private User user;

    private PageBean PageBean;

    public PackagBean_Comment(Integer c_id,String c_words,Integer u_id,String c_date,List<String> pic_urls,User user,PageBean PageBean){
        this.c_id = c_id;
        this. c_words= c_words;
        this. u_id = u_id;
        this. c_date = c_date;
        this. pic_urls = pic_urls;
        this.  user=  user;
        this.PageBean = PageBean;
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



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PageBean getPageBean() {
        return PageBean;
    }

    public void setPageBean(PageBean PageBean) {
        this.PageBean = PageBean;
    }

    public List<String> getPic_urls() {
        return pic_urls;
    }

    public void setPic_urls(List<String> pic_urls) {
        this.pic_urls = pic_urls;
    }
}
