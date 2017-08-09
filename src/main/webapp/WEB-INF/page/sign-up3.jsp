<%--
  Created by IntelliJ IDEA.
  User: powerman
  Date: 2017/7/15
  Time: 10:38
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
            <form action="${pageContext.request.contextPath}/sign-up-3Next.action" method="post" class="fh5co-form animate-box" data-animate-effect="fadeInRight">
                <h2>注册Step1</h2>

                <div class="form-group">
                    <label for="phoneNumber" class="sr-only">手机号码</label>
                    <input type="text" class="form-control" name="phoneNumber" id="phoneNumber" placeholder="手机号码" autocomplete="off">
                    <div id="message"></div>
                </div>

                <div class="form-group">
                    <label for="idCode" class="sr-only">验证码</label>
                    <input type="text" class="form-control" name="idCode" id="idCode" placeholder="验证码" autocomplete="off">
                    <div id="idCodeMessage"></div>
                </div>
                <div class="form-group">
                    <p>已经注册了？ <a href="sign-in3.action">登录</a></p>
                    <p> <input type="button"  id="btn" value="获取验证码" ></p>
                </div>
                <div class="form-group">
                    <input type="submit" value="下一步" class="btn btn-primary">
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

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/page1/js/phoneAjax.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/page1/js/makeIdCodeBtn.js"></script>
<script type="text/javascript">

    $(function(){
        phoneAjax()
        idCode()

        $('#Code').change(function(){
            $('#idCodeMessage').html('')
        })
    })
</script>
<script type="text/javascript">

</script>
<!-- 前台验证 -->
<script type="text/javascript">
    $(window).load(function(){
    $('.fh5co-form.animate-box').validate({
        errorElement: 'span',

        rules: {
            idCode: {
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
            idCode: {
                required:"*请填写验证码",

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

            $.ajax({
                url: "http://47.93.49.124:81/tasty/checkIdCode.action",
                type: "post",
                contentType: 'application/json;charset=UTF-8',
                data: '{"phoneNumber":"'+$('#phoneNumber').val()+'","idCode":"'+$('#idCode').val()+'"}',
                dataType: 'json',
                success: function (data) {
                    if(data.message=='success'){

                            form.submit()//提交表单
                    }
                   if(data.message=='fail'){
                       $('#idCodeMessage').html('<p style="color: red">验证码错误</p>')
                   }
                }
            })


        },
        invalidHandler: function(form, validator) {  //验证不通过之后回调
            return false;
        }
    });
    })
</script>


</body>
</html>

