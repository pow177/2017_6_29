<%--
  Created by IntelliJ IDEA.
  User: powerman
  Date: 2017/8/2
  Time: 0:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>修改密码成功</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery-1.7.2.js"></script>
</head>
<body>
    <h4>修改密码成功！三秒后自动跳转，如无跳转<a href="${pageContext.request.contextPath}/page/sign-in3.action">点我返回登录页面</a></h4>
</body>

<script type="text/javascript">
    setTimeout(function(){
        location.href="${pageContext.request.contextPath}/page/sign-in3.action";
    },3000);
</script>

</html>
