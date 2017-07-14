<%--
  Created by IntelliJ IDEA.
  User: powerman
  Date: 2017/6/10
  Time: 0:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--<a href="${pageContext.request.contextPath}/makeExcel.action">MakeExcel 123</a>--%>
<%--<a href="${pageContext.request.contextPath}/putInExcel.action">putInExcel</a>--%>

<div class="text">
<p>文本</p>
</div>
<input type="button" id="b1" value="b1">  <input type="button" id="b2" value="b2">
<script type="text/javascript" src="././resources/script/jquery-1.7.2.js">
</script>

<script type="text/javascript">
    $(document).ready(function (){
        $('#b1').click(function(){
            $('.text').html('<p style="color: red">改变了文本1</p>')
        })

        $('#b2').click(function(){
            $('.text').html('<p>改变了文本2</p>')
        })
    })
</script>
</body>
</html>
