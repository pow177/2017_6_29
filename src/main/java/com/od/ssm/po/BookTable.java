package com.od.ssm.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by 黄冠莳 on 2017/7/10.
 */
@Table(name="BookTable")
public class BookTable {
    @Id
    private int id;
    @Column(name="book_name")
    private String bookName;
    @Column(name="book_phone_number")
    private String bookPhoneNumber;
    @Column(name="book_time")
    private String bookTime;
    @Column(name="book_people_number")
    private  String bookPeopleNumber;

    public String getBookPeopleNumber() {
        return bookPeopleNumber;
    }

    public void setBookPeopleNumber(String bookPeopleNumber) {
        this.bookPeopleNumber = bookPeopleNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
