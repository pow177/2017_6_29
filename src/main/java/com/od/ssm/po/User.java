package com.od.ssm.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;


import com.od.ssm.controller.validation.ValidGroup1;
import com.od.ssm.controller.validation.ValidGroup2;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;


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

    @Column(name="img_url")
    @Pattern( regexp="(jpg$|jpeg$|png$)",message="图片格式有误")  //"jpg|jpeg|png|"   $结尾 = 类似*jpg的格式    如果只写 jpg  那么jpg也能匹配到
    private String imgUrl;

    @Column(name="email")
    @Pattern(regexp = "\\w*@\\w*\\.\\D*",message = "邮箱格式有误")
    private String email;

    @Column(name="validata_code")
    private String validataCode;

    @Column(name="registe_date")
    private Timestamp registeDate;

    @Column(name="cookie_key")
    private String cookieKey;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getValidataCode() {
        return validataCode;
    }

    public void setValidataCode(String validataCode) {
        this.validataCode = validataCode;
    }


    public Timestamp getRegisteDate() {
        return registeDate;
    }

    public void setRegisteDate(Timestamp registeDate) {
        this.registeDate = registeDate;
    }

    public String getCookieKey() {
        return cookieKey;
    }

    public void setCookieKey(String cookieKey) {
        this.cookieKey = cookieKey;
    }
}
