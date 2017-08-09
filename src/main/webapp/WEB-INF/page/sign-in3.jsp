<%--
  Created by IntelliJ IDEA.
  User: powerman
  Date: 2017/7/15
  Time: 10:34
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


    <style>
        span.error {
            color:aquamarine;
        }
    </style>

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
            <form action="${pageContext.request.contextPath}/idpSign.action" method="post" class="fh5co-form animate-box" data-animate-effect="fadeInRight">
                <h2>登录页面</h2>
                <c:if test="${requestScope.erorMessage!=null}">
                <div id="message" style="color: red">${requestScope.erorMessage}</div>
                </c:if>
                    <div class="form-group">
                    <label for="phoneNumber" class="sr-only">手机号码</label>
                    <input type="text" class="form-control" name="phoneNumber" id="phoneNumber" placeholder="手机号码" autocomplete="off" >
                </div>
                <div class="form-group">
                    <label for="password" class="sr-only">密码</label>
                    <input type="password" class="form-control" name="password" id="password" placeholder="密码" autocomplete="off" >
                </div>
                <div class="form-group">
                    <label for="remember"><input type="checkbox" id="remember" name="remember" value="remember">记住密码</label>
                </div>
                <div class="form-group">
                    <p>还没注册？<a href="${pageContext.request.contextPath}/page/sign-up3.action">注册</a> | <a href="${pageContext.request.contextPath}/page/forgot3.action">忘记密码</a></p>
                </div>
                <div class="form-group">
                    <input type="submit" value="登录" class="btn btn-primary">
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


<script src="${pageContext.request.contextPath}/resources/page2/js/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath}/resources/page2/js/validate-methods.js"></script>
<script src="${pageContext.request.contextPath}/resources/page2/js/sign-inAjax.js"></script>
<script type="text/javascript">
    $(window).load(function() {
        //<!-- JQ validation 验证表单信息 -->
        $('.fh5co-form.animate-box').validate({
            errorElement: 'span',

            rules: {
                password: {
                    required: true,

                },
                phoneNumber: {
                    required: true,
                    isPhone: true,
                    maxlength: 11,
                    minlength: 11,
                    number: true
                }
            },
            messages: {
                password: {
                    required:"*请填写你的密码",

                },
                phoneNumber: {
                    required: "*请填写你的手机号码",
                    isPhone: "*请输入正确的手机号码",
                    maxlength:"*请输入正确的手机号码",
                    minlength:"*请输入正确的手机号码",
                    number: "*请输入正确的手机号码"
                }
            },

            submitHandler: function(form) {//验证通过之后回调
                $('.fh5co-form.animate-box').submit();
            },
            invalidHandler: function(form, validator) {  //验证不通过之后回调
                return false;
            }
        });
    })
</script>
<!-- end -->
<!-- 回显表单 -->
<c:if test="${requestScope.erorMessage!=null&&requestScope.reShowUser!=null}">
<script type="text/javascript">
    $('#phoneNumber').val(${reShowUser.phoneNumber});
    $('#password').val(${reShowUser.password});
</script>
</c:if>
</body>
</html>
