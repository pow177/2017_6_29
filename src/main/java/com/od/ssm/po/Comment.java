package com.od.ssm.po;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Created by 黄冠莳 on 2017/7/18.
 */
@Table(name="comment")
public class Comment {
    @Id
    private Integer c_id;              //主评论主键

    @Column(name = "c_words")
    @Length(max=255,message="评论过长")
    private String c_words;            //主评论


    @Column(name = "c_u_id")
    private Integer u_id;              //外键关联用户

    @Column(name="c_date")
    private String c_date;              //评论日期

    @Column(name="pic_url")         //评论上传照片的地址
    private String pic_url;

    private User user;


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




    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
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
}
