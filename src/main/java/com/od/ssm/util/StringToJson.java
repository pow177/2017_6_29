package com.od.ssm.util;

/**
 * Created by 黄冠莳 on 2017/7/11.
 */

//对String数组数据进行简单的Json格式处理
public class StringToJson {
    private static  final String JSON_H = "{\"";             //    {\"
    private static  final String JSON_R = "\"}";           //     }"

    private static  final String JSON_M = "\":\"";     //   ":"
    private static  final String JSON_D = "\",\"";   //   ","
    public static String listToJson(String[] str){
        String[] keys = new String[str.length];    //存储jeson数据的key
        String jsonMessage = "";                   //用于存储jeson串
        int i=0;                                  //计数器
        for(String str_:str){

            keys[i] = "message"+String.valueOf(i); // 生成字符串message1,message2,.......
            jsonMessage = keys[i]+JSON_M+str_+JSON_D+jsonMessage;
            i++;
        }
        jsonMessage = JSON_H+jsonMessage+JSON_R;
//用stringBuff修饰一下字符串多余的字符
        int index = jsonMessage.indexOf(",\"\"");
        StringBuffer sf = new StringBuffer(jsonMessage);
        sf.replace(index,index+3,"");

        jsonMessage = sf.toString();

        System.out.println("处理后的Json数据-->"+jsonMessage);
        return jsonMessage;
    }

    public static String strToJson(String str){
        String jsonMessage = "";
        jsonMessage=JSON_H+"message"+JSON_M+str+JSON_R;
        System.out.println("处理后的Json数据-->"+jsonMessage);
        return jsonMessage;
    }
}
