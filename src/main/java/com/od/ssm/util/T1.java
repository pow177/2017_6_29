package com.od.ssm.util;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by 黄冠莳 on 2017/6/10.
 */
public class T1 {
    public static void main(String[] args) {
//        try {
//            MyEmail.sendMail("12093812498124", "120289256@qq.com");
//        }catch(Exception e){
//            System.out.print("错误");
//        }
//        String str = "";
//        String[] tem = str.split(",");
//        System.out.println(tem[0]);
//        Timestamp temDate = new Timestamp(System.currentTimeMillis()+30*60*1000);
//        System.out.println(temDate);


        Timestamp temDate = null;
        long tem2 = new Timestamp(System.currentTimeMillis()).getTime();
        char[] charTemArrays = String.valueOf(tem2).toCharArray();
        for(int i=0;i<3;i++){
            charTemArrays[charTemArrays.length-3+i]="0".charAt(0);
        }
        String strTem = String.valueOf(charTemArrays);
      long t =  Long.parseLong(strTem);
      Date d1 = new Date(tem2);
      Date d2 = new Date(t);
        System.out.println("d1: "+d1);
        System.out.println("d2: "+d2);


    }
}


