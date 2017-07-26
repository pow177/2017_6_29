<%--
  Created by IntelliJ IDEA.
  User: powerman
  Date: 2017/7/24
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Wopop</title>
    <link href="${pageContext.request.contextPath}/resources/admin/Wopop_files/style_log.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resources/admin/Wopop_files/style.css">
    <link href="${pageContext.request.contextPath}/resources/admin/Wopop_files/userpanel.css">
    <link href="${pageContext.request.contextPath}/resources/admin/Wopop_files/jquery.ui.all.css">


</head>

<body class="login" mycollectionplug="bind">
<div class="login_m">
    <div class="login_logo"><img src="${pageContext.request.contextPath}/resources/admin/Wopop_files/logo.png" width="196" height="46"></div>
    <div class="login_boder">

        <div class="login_padding" id="login_model">


           <div id="message">${resultBean.message}</div>

            <form action="${pageContext.request.contextPath}/admin/validate.action" method="post">
                <h2>USERNAME</h2>
                <label>
                <input type="text" name="userName" id="userName" class="txt_input txt_input2" value="${requestScope.oldAdminMessage.password}"/>
            </label>
            <h2>PASSWORD</h2>
            <label>
                <input type="password" name="password"  id="password" class="txt_input" value="${requestScope.oldAdminMessage.password}" />
            </label>

            <div class="rem_sub">
                <label>
                    <input type="submit" class="sub_button" name="button" id="sbtn" value="SIGN-IN" style="opacity: 0.7;">
                </label>
            </div>
            </form>
        </div>



        <!--login_padding  Sign up end-->
    </div><!--login_boder end-->
</div><!--login_m end-->
<br> <br>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery-1.7.2.js"></script>
<script type="text/javascript">

    $(window).load(function(){

        $('#sbtn').click(function(){

            if($('#userName').val().length==0&&$('#password').val()==""){
                alert("请输入账号密码")
                return false
            }
            return true;

        })
    })

</script>


</body></html>
