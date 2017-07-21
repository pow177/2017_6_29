package com.od.ssm.util;

import static java.lang.Math.random;

/**
 * Created by 黄冠莳 on 2017/7/16.
 * 用户生成纯数字验证码的工具
 */


public class MyIdCode {
    private  int max;
    public MyIdCode(int max){
        this.max = max;
    }
    public MyIdCode(){
        this.max = 6; //默认长度为6个大小的max
    }

    public String getIdCode(){
       String code="";
        for(int i=0;i<max;i++) {
            int tem = (int) (Math.random() * 10);
            code = code+String.valueOf(tem);
        }
        return code;
    }


}
