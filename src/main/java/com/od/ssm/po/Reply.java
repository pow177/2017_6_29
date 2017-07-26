package com.od.ssm.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by 黄冠莳 on 2017/7/18.
 */
@Table(name="reply")
public class Reply {
    @Id
    private Integer r_id;

    @Column(name="r_words")                        //评论
    private String r_words;

    private User user;

    @Column(name="r_u_id")
    private Integer r_u_id;                     //外键关联user
    @Column(name="r_c_id")
    private Integer c_id; //外键关联 comment

    private Comment comment;

    private String r_date;

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


    public Integer getR_u_id() {
        return r_u_id;
    }

    public void setR_u_id(Integer r_u_id) {
        this.r_u_id = r_u_id;
    }
}
