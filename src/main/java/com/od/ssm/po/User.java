package com.od.ssm.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;


import org.hibernate.validator.constraints.Length;

@Table(name = "user")
public class User {
    @Id
    private int id;

    @Column(name = "phone_number")
    @Pattern(regexp="(^1[3|5|7|8][0-9]{9}$)",message="手机号码格式错误")
    @Length(max = 11)
    private String phoneNumber;

    @Column(name = "password")
    @Length(max = 20,message="密码长度过长")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "modd_message")
    @Length(max = 255,message="留言过长")
    private String moodMessage;

    //get and set
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
