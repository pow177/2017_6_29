package com.od.ssm.util;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by 黄冠莳 on 2017/7/31.
 */
public class MD5 {
        /***
         * MD5加码 生成32位md5码
         */
        public static String stringMD5(String inStr){
            MessageDigest md5 = null;
            try{
                md5 = MessageDigest.getInstance("MD5");
            }catch (Exception e){
                System.out.println(e.toString());
                e.printStackTrace();
                return "";
            }
            char[] charArray = inStr.toCharArray();
            byte[] byteArray = new byte[charArray.length];

            for (int i = 0; i < charArray.length; i++)
                byteArray[i] = (byte) charArray[i];
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++){
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();

        }

        public static void main(String[] args){
            String uuid = UUID.randomUUID().toString();
            String key1 = uuid+"$"+"17765602449"+"$"+"15678";
            String n1 = stringMD5(key1);
            String n2 = stringMD5(new String(uuid+"$"+"17765602449"+"$"+"15678"));
            System.out.println(n1.equals(n2));

        }
    }
