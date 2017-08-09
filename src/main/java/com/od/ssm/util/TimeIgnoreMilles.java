package com.od.ssm.util;

/**
 * Created by 黄冠莳 on 2017/8/3.
 * 时间类型毫秒忽略处理工具
 */
public class TimeIgnoreMilles {
    public static long ingnoreMilles(long tem){
        char[] charTemArrays = String.valueOf(tem).toCharArray();
        for(int i=0;i<3;i++){
            charTemArrays[charTemArrays.length-3+i]="0".charAt(0);
        }
        String strTem = String.valueOf(charTemArrays);
        return  Long.parseLong(strTem);
    }
}
