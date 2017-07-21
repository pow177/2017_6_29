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

    private Integer u_id;                     //外键关联user

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
}
