<%--
  Created by IntelliJ IDEA.
  User: powerman
  Date: 2017/7/31
  Time: 22:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>成功发送邮件</title>
</head>
<body>
        <h4>邮件已发出，请到邮件修改自己的密码.Tips:30分钟后过期。</h4>
</body>
<script type="text/javascript">
    setTimeout(function(){
        location.href="${pageContext.request.contextPath}/gallery.html";
    },5000);
</script>
</html>
