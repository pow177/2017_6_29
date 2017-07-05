<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery-1.7.2.js"></script>

    <script type="text/javascript">

        $(function () {

            $(":input[name='username']").change(function () {
                var val = $(this).val();
                val = $.trim(val);

                if(val!=""){

                    var url = "${pageContext.request.contextPath}/validateUsername.action";
                    var args = {"username":val,"time":new Date()};
                    $.post(url,args,function(data){
                        alert(val);
                        $("#message").html(data);
                    });
                }
            });
        })

    </script>
    <base href="<%=basePath%>">

    <title>My JSP 'index.jsp' starting page</title>


  </head>

  <body>
<form action="${pageContext.request.contextPath }/test3.action" method="post" >
  <input id="hid_username"type="hidden" name="userName" value="">
    <input id="btn3"type="submit" value="提交">
</form>
<div id="editor">
  <p>欢迎使用 <b>wangEditor</b> 富文本编辑器</p>
</div>
  <button id="btn1">获取html</button>
  <button id="btn2">获取text</button>

  <a href="${pageContext.request.contextPath }/test.action?id=1">getUser id=1</a>


    <input type="text" name="username">
  <div id="message"></div>




    <!-- 注意， 只需要引用 JS，无需引用任何 CSS ！！！-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/wangEditor.js"></script>
    <script type="text/javascript">
        var E = window.wangEditor;
        var editor = new E('#editor');
        // 或者 var editor = new E( document.getElementById('#editor') )
        editor.customConfig.uploadImgServer = '${pageContext.request.contextPath}/FFTT.action';

        editor.create();

        document.getElementById('btn1').addEventListener('click', function () {
            // 读取 html
            alert(editor.txt.html())
        }, false)

        document.getElementById('btn2').addEventListener('click', function () {
            // 读取 text
            alert(editor.txt.text())
        }, false)

        $("#btn3").click(function () {
           $("#hid_username").val(editor.txt.text());


        })
//        document.getElementById('btn3').addEventListener('click', function () {
//            // 读取 text
//            $("#hid_username").val(editor.txt.text());
//            alert(editor.txt.text())
//            return false
//        }, false)
    </script>

  </body>
</html>
