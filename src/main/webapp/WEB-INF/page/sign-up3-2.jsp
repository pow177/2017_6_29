<%--
  Created by IntelliJ IDEA.
  User: powerman
  Date: 2017/7/15
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Minimal and Clean Sign up / Login and Forgot Form by FreeHTML5.co</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Free HTML5 Template by FreeHTML5.co" />
    <meta name="keywords" content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive" />




    <!-- Facebook and Twitter integration -->
    <meta property="og:title" content=""/>
    <meta property="og:image" content=""/>
    <meta property="og:url" content=""/>
    <meta property="og:site_name" content=""/>
    <meta property="og:description" content=""/>
    <meta name="twitter:title" content="" />
    <meta name="twitter:image" content="" />
    <meta name="twitter:url" content="" />
    <meta name="twitter:card" content="" />

    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <link rel="shortcut icon" href="favicon.ico">

    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,700,300' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/page1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/page1/css/animate.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/page1/css/style.css">

    <!-- Modernizr JS -->
    <script src="${pageContext.request.contextPath}/resources/page1/js/modernizr-2.6.2.min.js"></script>
    <!-- FOR IE9 below -->
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/resources/page1/js/respond.min.js"></script>
    <![endif]-->
    <!-- 用于判断用户是否登录，若登录则自动重定向到其他主页 -->
    <c:if test="${sessionScope.user!=null}">
        <script language="JavaScript">
            window.location.replace("${pageContext.request.contextPath}/gallery.html");
        </script>
    </c:if>
</head>
<body class="style-3">

<div class="container">
    <div class="row">
        <div class="col-md-12 text-center">
            <ul class="menu">
                <li></li>
                <li></li>
                <li></li>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-md-4 col-md-push-8">


            <!-- Start Sign In Form -->
            <form action="${pageContext.request.contextPath}/addUser.action" class="fh5co-form animate-box" data-animate-effect="fadeInRight">
                <h2>注册Step2</h2>
                <input type="hidden" name="phoneNumber" value="${requestScope.hidden_phoneNumber}">
                <div class="form-group">
                    <label for="password" class="sr-only">登录密码</label>
                    <input type="password" class="form-control" name="password"  id="password" placeholder="登录密码" autocomplete="off">
                </div>

                <div class="form-group">
                    <label for="re_PassWord" class="sr-only">确认密码</label>
                    <input type="password" class="form-control" name="re_password" id="re_password" placeholder="确认密码" autocomplete="off">
                    <div id="message"></div>
                </div>

                <div class="form-group">
                    <input type="submit" value="确认注册" class="btn btn-primary" disabled="disabled">
                </div>
            </form>
            <!-- END Sign In Form -->


        </div>
    </div>

</div>

<!-- jQuery -->
<script src="${pageContext.request.contextPath}/resources/page1/js/jquery.min.js"></script>
<!-- Bootstrap -->
<script src="${pageContext.request.contextPath}/resources/page1/js/bootstrap.min.js"></script>
<!-- Placeholder -->
<script src="${pageContext.request.contextPath}/resources/page1/js/jquery.placeholder.min.js"></script>
<!-- Waypoints -->
<script src="${pageContext.request.contextPath}/resources/page1/js/jquery.waypoints.min.js"></script>
<!-- Main JS -->
<script src="${pageContext.request.contextPath}/resources/page1/js/main.js"></script>


<!-- 验证插件 -->
<script src="${pageContext.request.contextPath}/resources/page2/js/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath}/resources/page2/js/validate-methods.js"></script>


<!-- 前台验证 -->
<script type="text/javascript">
    $(window).load(function(){
        $('.fh5co-form.animate-box').validate({
            errorElement: 'span',

            rules: {
                password: {
                    required: true,
                    minlength: 6,
                    maxlength: 20,
                    number: true
                },
                re_password: {
                    required: true,
                    maxlength: 20,
                    minlength: 6,
                    number: true
                }
            },
            messages: {
                password: {
                    required: "*请再次输入相同的密码",
                    maxlength: "*密码长度长于20",
                    minlength: "*密码不能小于6位",
                    number: true

                },
                re_password: {
                    required: "*请再次输入相同的密码",
                    maxlength: "*密码长度长于20",
                    minlength: "*密码不能小于6位",
                    number: true
            },

            submitHandler: function(form) {//验证通过之后回调
                form.submit



            },
            invalidHandler: function(form, validator) {  //验证不通过之后回调
                return false;

            }
            }
            });

    })
</script>

<script type="text/javascript" >
   $('#password').change(function () {
       var passwordVal = $('#password').val()
       var re_passwordVal = $('#re_password').val()
       if(re_passwordVal!=null){
           if(passwordVal!=re_passwordVal){
               $('#message').html('<p style="color: red">两次输入的密码不一致</p>')
               $('.btn.btn-primary').attr("disabled",true)
           }
           if(passwordVal==re_passwordVal){
               $('#message').html('<p></p>')
               $('.btn.btn-primary').removeAttr("disabled")
           }
       }
   })

    $('#re_password').change(function(){

        var passwordVal = $('#password').val()
        var re_passwordVal = $('#re_password').val()

        if(re_passwordVal!=null){
            if(passwordVal!=re_passwordVal){
                $('#message').html('<p style="color: red">两次输入的密码不一致</p>')
                $('.btn.btn-primary').attr("disabled",true)
            }
            if(passwordVal==re_passwordVal){

                $('#message').html('<p></p>')
                $('.btn.btn-primary').removeAttr("disabled")
            }
        }
    })
</script>

<c:if test="${requestScope.errorMessage!=null}">
    <script type="text/javascript">
        $('#password').attr("value",requestScope.reShowPhoneNumber)     //回显
        $('#re_password').attr("value",requestScope.reShowPhoneNumber)  //回显
        alert("发生未知错误，请联系管理人员");

    </script>
</c:if>
</body>
</html>

