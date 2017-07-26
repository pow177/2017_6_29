package com.od.ssm.bean;

/**
 * Created by 黄冠莳 on 2017/7/24.
 * 封装返回结果的bean
 */
public class ResultBean {
    private String message;
    private boolean stu ;


    public ResultBean(String message,boolean stu){
        this.message = message;
        this.stu = stu;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStu() {
        return stu;
    }

    public void setStu(boolean stu) {
        this.stu = stu;
    }
}
