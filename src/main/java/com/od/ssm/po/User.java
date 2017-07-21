package com.od.ssm.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;


import org.hibernate.validator.constraints.Length;



@Table(name = "user")
public class User {
    @Id
    private Integer u_id;

    @Column(name = "phone_number")
    @Pattern(regexp="(^1[3|5|7|8][0-9]{9}$)",message="手机号码格式错误")
    @Length(max = 11)
    private String phoneNumber;

    @Column(name = "password")
    @Length(min = 6,max=20,message="密码不符合要求")
    private String password;

    @Column(name = "name")
    @Length(max=12,message="名字过长")
    private String name;

    @Column(name = "mood_message")
    @Length(max = 255,message="留言过长")
    private String moodMessage;

    //get and set


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoodMessage(String moodMessage){
        this.moodMessage = moodMessage;
    }
    public  String getMoodMessage(){
        return moodMessage;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }
}
