package com.od.ssm.util;
import java.io.UnsupportedEncodingException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
/**
 * Created by 黄冠莳 on 2017/6/10.
 */
public class MyPhoneMessage {
    public static void sendIdCodeMessage(String code,String phoneNumber)throws Exception
    {

        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
        NameValuePair[] data ={ new NameValuePair("Uid", "pow17765602448"),new NameValuePair("Key", "5b5d6105577923d0bc3b"),new NameValuePair("smsMob",phoneNumber),new NameValuePair("smsText","Tasty咖啡厅提示：您的注册帐号验证码为"+code)};
        post.setRequestBody(data);

        client.executeMethod(post);
        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:"+statusCode);
        for(Header h : headers)
        {
                System.out.println(h.toString());
                }
                String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
                System.out.println(result);


                post.releaseConnection();

                }
                }
