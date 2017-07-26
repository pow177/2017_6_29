package com.od.ssm.po;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by 黄冠莳 on 2017/7/24.
 */
@Table(name="admin")
public class Admin {

    @NotNull
    @Column(name="user_name")
    private String userName;
    @NotNull
    @Column(name="password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
