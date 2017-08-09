<%--
  Created by IntelliJ IDEA.
  User: powerman
  Date: 2017/7/27
  Time: 1:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>Home</title>
    <!-- Custom Theme files -->
    <link href="${pageContext.request.contextPath}/resources/user/css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <!-- Custom Theme files -->
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="Simple Profile Widget Responsive, Login form web template, Sign up Web Templates, Flat Web Templates, Login signup Responsive web template, Smartphone Compatible web template, free web designs for Nokia, Samsung, LG, sony ericsson, Motorola web design" />

    <link href='https://fonts.googleapis.com/css?family=Droid+Sans:400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/page1/css/bootstrap.min.css">

    <script src="${pageContext.request.contextPath}/resources/user/js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/user/js/script.js"></script>
</head>
<body>
<div class="head">
    <div class="head-text">
        <h1>PERSON DATA</h1>
    </div>
    <div class="login">
        <div class="header">
            <div class="text">
                <c:if test="${sessionScope.user.imgUrl!=null}">
               <a href="#modal-container-879633" data-toggle="modal"> <img src="${sessionScope.user.imgUrl}" height="290px" width="360px" class="img-thumbnail" /></a>
                </c:if>
                <h2 id="showName"></h2>
                <p>@Tasty</p>
            </div>
        </div>
        <input type="hidden" id="oldName" value="${sessionScope.user.name}"/>
        <ul class="content">
            <li class="menu">
                <ul>
                    <li class="button">
                        <div class="form-group">
                            <label class="sr-only" for="name">用户名:</label><div class="right"><button type="button" value="修改用户名" id="resetName" onclick="resetName()">修改用户名</button></div>
                                <input class="form-control" type="text" id="name" name="name" value="${sessionScope.user.name}" disabled="disabled" placeholder="请输入用户名"/>
                        </div>
                    </li>

                </ul>
            </li>

            <li class="menu">
                <ul>
                    <li class="button">
                        <div class="form-group">
                            <input type="hidden" name="oldMoodMessage" id="oldMoodMessage" value="${sessionScope.user.moodMessage}"/>
                            <label class="sr-only" for="moodMessage">心情留言:</label><div align="right"><button  type="button" id="resetMoodMessage" onclick="resetMoodMessage()">留言</button></div>
                            <textarea class="form-control"  id="moodMessage" name="moodMessage" style="height: 350px;width: 380px" placeholder="留下你的心情动态吧~" disabled="disabled">${sessionScope.user.moodMessage}</textarea>
                        </div>
                    </li>

                </ul>
            </li>







        </ul>
    </div>

</div>




    <!--拟态框 -->
    <div class="modal fade" id="modal-container-879633" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                    上传图片
                    </h4>
                </div>
                <div><c:if test="${sessionScope.imgUrl!=null}">
                    <img src="${sessionScope.imgUrl}" height="120px" width="120px" id="img">

                </c:if> <p></p> </div>

                <div class="modal-body">
                    <form  id="uplodImgFrom" action="${pageContext.request.contextPath}/sendImg.action" method="post" enctype="multipart/form-data">
                        <input type="file" name="img" id="imgFile" onchange="uploadImg()"/>

                    </form>
                </div>
                <div class="modal-body">
                    <form id="updateUserImg" action="${pageContext.request.contextPath}/updateUserImg.action" method="post">
                        <input type="hidden" value="${sessionScope.user.u_id}" name="u_id"/>
                        <input type="hidden" value="${sessionScope.imgUrl}" id="imgUrl" name="imgUrl"/>
                    </form>

                    <button type="button" name="imgBtn" id="imgBtn" onclick="imgBtn()" disabled="disabled" >确定</button>
                </div>

            </div>

        </div>

    </div>


<script src="${pageContext.request.contextPath}/resources/page1/js/bootstrap.min.js" type="text/javascript"></script>
<script type="text/javascript">

    //验证文件格式
  function uploadImg() {
      arr = $("#imgFile").val().split(".")
      tem = "jpg|jpeg|png|".split("|")
      if(arr[1]==tem[0]||arr[1]==tem[1]||arr[1]==tem[2]){
                $('#uplodImgFrom').submit()

      }else {
          alert("格式不正确")
      }

//      $('#uplodImgFrom').submit()
  }
    //重新选择
    function reChose(){
        alert("123")

    }

  <c:if test="${sessionScope.imgUrl!=null}"> //这里是选择完图片后回显了
  $(".img-thumbnail").click()
$("#imgBtn").attr("disabled",false)
    $("#imgFile").hide()
    $("#imgFile").after('<p id="hadChose">√已选择</p><button id="reChose" type="button" onclick="reChose()" >重新选择</button> ')
  </c:if>

$("#reChose").click(function(){
    $("#hadChose").hide()
    $("#reChose").hide()
    $("#imgFile").show()
    $("#imgBtn").attr("disabled",true)

})

  function imgBtn(){
      $("#updateUserImg").submit()
  }



</script>
<script type="text/javascript">
    function resetName() {
        $('#name').attr("disabled",false);
        $("#resetName").replaceWith(' <button type="button" value="" id="submit" onclick="submit()">提交修改</button>')
        $("#submit").after('<button type="button" value="" id="cle" onclick="cle()">取消</button>')


    }
    function submit(){
        var a = confirm("确定要修改用户名吗?")

        if(a==true){
            if($("#name").val()==""){
                alert("用户名不能为空")
                return false
            }
            $.ajax({
            url: "http://47.93.49.124:81/tasty/updataUserName.action",
            type: "post",
            contentType: 'application/json;charset=UTF-8',
            data: '{"u_id":${sessionScope.user.u_id},"name":"'+$("#name").val()+'"}',
            dataType: 'json',
            success: function (data){
               if(data.message="success") {
                   alert("更新成功")
                   $("#name").attr("disabled",true)
                   $("#submit").replaceWith('  <button type="button" value="修改用户名" id="resetName" onclick="resetName()">修改</button>')
                   $("#cle").remove();
                   $("#oldName").val($("#name").val());
                   $("#showName").text($("#name").val());
               }
            }
        })
        }
    }
    function cle(){
        $("#submit").replaceWith('  <button type="button" value="修改用户名" id="resetName" onclick="resetName()">修改</button>')
       var nameVal = $("#name").val();
        $("#name").attr("disabled",true)
        $("#name").val($("#oldName").val())
        $("#cle").remove();
    }

</script>

<script type="text/javascript">
   function resetMoodMessage(){
       $("#moodMessage").attr("disabled",false)
       $("#resetMoodMessage").replaceWith('<button  type="button" id="sendMoodMessage" onclick="updateMoodMessage()">发表</button>')



   }

   function updateMoodMessage(){
           var a = confirm("确定更新或者发表留言吗?")
           if(a==true){
               $.ajax({
                   url: "http://47.93.49.124:81/tasty/updateMoodMessage.action",
                   type: "post",
                   contentType: 'application/json;charset=UTF-8',
                   data: '{"u_id":${sessionScope.user.u_id},"moodMessage":"'+$("#moodMessage").val()+'"}',
                   dataType: 'json',
                   success: function (data){
                       if(data=="fail"){
                           alert("留言字数不能超过255")
                       }else {
                           alert("留言成功")
                           $("#oldMoodMessage").val($("#moodMessage").val())
                           $("#moodMessage").attr("disabled",true);
                           $("#sendMoodMessage").replaceWith('<button type="button" id="resetMoodMessage" onclick="resetMoodMessage()">留言</button>')
                           $("#showName").text($("#oldName").val())

                       }

                   }

               })
           }
           if(a==false){
               $("#sendMoodMessage").replaceWith('<button type="button" id="resetMoodMessage" onclick="resetMoodMessage()">留言</button>')

               $("#moodMessage").attr("disabled",true);
               $("#moodMessage").val($("#oldMoodMessage").val())
           }

   }



</script>

<script type="text/javascript">
    $("#showName").text($("#oldName").val())
</script>


</body>
</html>
