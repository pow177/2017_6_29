package com.od.ssm.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 黄冠莳 on 2017/7/24.
 */
public class AdminInterceptor implements HandlerInterceptor {
    private  String passKey ;
    /**
     * 在调用controller具体方法前拦截
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
       String key = request.getParameter("key");
        String sessionkey = (String)request.getSession().getAttribute("admitKey");
        System.out.println(key);
       if(key==null&&sessionkey==null){
           System.out.println("未通过拦截器");
           request.getRequestDispatcher("/WEB-INF/page/admin/adminFail.jsp").forward(request, response);
           return false;
       }else if(passKey.equals(key)){
           System.out.println("通过拦截器");
           request.getSession().setAttribute("admitKey",passKey);        //通过之后将信息存入session,下次进入直接通过拦截
           return true;
       }else if(sessionkey!=null){
           System.out.println("通过拦截器");
           return true;
       }
       else{
           System.out.println("未通过拦截器");
           request.getRequestDispatcher("/WEB-INF/page/admin/adminFail.jsp").forward(request, response);
           return false;
       }


    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


    public void setPassKey(String passKey) {
        this.passKey = passKey;
    }
}

