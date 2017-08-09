<%--
  Created by IntelliJ IDEA.
  User: powerman
  Date: 2017/8/1
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

    <title>Title</title>
    <!-- Bootstrap  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/page2/css/bootstrap.css">
    <!-- Bootstrap -->
    <script src="${pageContext.request.contextPath}/resources/page1/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery-1.7.2.js"></script>
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-4 column">
        </div>
        <div class="col-md-4 column">
            <form  id="form" role="form"  action="${pageContext.request.contextPath}/updataPassWord.action" method="post">
               <input type="hidden" name="u_id" value="${sessionScope.resetUser_Id}"/>
                <input type="hidden" name="check_phoneNumber" value="${sessionScope.resetUser_phoneNumber}"/>
                <div class="form-group">
                    <label for="phoneNumber">手机号码:</label><input type="text" class="form-control" name="phoneNumber" id="phoneNumber" />
                </div>
                <div class="form-group">
                    <label for="password">新密码:</label><input type="password" class="form-control" name="password"  id="password" />
                </div>
                <button type="submit" class="btn btn-default">提交</button>
            </form>
        </div>
        <div class="col-md-4 column">
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
        </div>
    </div>
</div>

<!-- 登录前台验证 -->
<!-- 验证插件 -->
<script src="${pageContext.request.contextPath}/resources/page2/js/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath}/resources/page2/js/validate-methods.js"></script>
<script type="text/javascript">

        $('#form').validate({
            errorElement: 'span',

            rules: {
                password: {
                    required: true,
                    minlength:6,
                    maxlength:20

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
                    minlength:"*密码不得小于6位",
                    maxlength:"*密码不得大于20位",

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
                form.submit()
            },
            invalidHandler: function(form, validator) {  //验证不通过之后回调
                return false;
            }
        });


</script>
</body>
</html>
