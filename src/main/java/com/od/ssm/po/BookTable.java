package com.od.ssm.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by 黄冠莳 on 2017/7/10.
 */
@Table(name="bookTable")
public class BookTable {
    @Id
    private Integer id;
    @Column(name="book_name")
   //因为考虑到中文英文名称，所以正则只规范了三个字符以上的非数字字符串
    @Pattern(regexp="\\D{3}",message="名字有误")
    private String bookName;

    @Pattern(regexp="(^1[3|5|7|8][0-9]{9}$)",message="手机号码格式错误")
    @Column(name="book_phone_number")
    private String bookPhoneNumber;

    @Pattern(regexp="(0[1-9]|1[0-2])/(0[1-9]|1[1-9]|2[1-9]|3[0-1])/(2017|2018)\\s([0-9]|11):([0-5][0-9]|60)\\s(AM|PM)",message="定台时间格式有误（注：只接受2年内的预定）")
    @Column(name="book_time")
    private String bookTime;         //(0[1-9]|1[0-2])/(0[1-9]|1[1-9]|2[1-9]|3[0-1])/(2017|2018)\s(0[0-9]|1[0-2]):([0-5][0-9]|60)\s(AM|PM)

    @Column(name="book_people_number")
    private  String bookPeopleNumber;

    public BookTable(String bookName,String bookPhoneNumber,String bookPeopleNumber,String bookTime){
            this.bookName = bookName;
            this.bookPhoneNumber = bookPhoneNumber;
        this.bookTime = bookTime;
        this.bookPeopleNumber = bookPeopleNumber;
    }

    public  BookTable(){}



    public String getBookPeopleNumber() {
        return bookPeopleNumber;
    }

    public void setBookPeopleNumber(String bookPeopleNumber) {
        this.bookPeopleNumber = bookPeopleNumber;
    }



    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPhoneNumber() {
        return bookPhoneNumber;
    }

    public void setBookPhoneNumber(String bookPhoneNumber) {
        this.bookPhoneNumber = bookPhoneNumber;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
