<%--
  Created by IntelliJ IDEA.
  User: powerman
  Date: 2017/7/24
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Lumino - Tables</title>

    <link href="${pageContext.request.contextPath}/resources/admin/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/admin/css/datepicker3.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/admin/css/bootstrap-table.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/admin/css/styles.css" rel="stylesheet">

    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/resources/admin/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/resources/admin/js/respond.min.js"></script>
    <![endif]-->

</head>

<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><span>Lumino</span>Admin</a>
            <ul class="user-menu">
                <li class="dropdown pull-right">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> User <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#"><span class="glyphicon glyphicon-user"></span> Profile</a></li>
                        <li><a href="#"><span class="glyphicon glyphicon-cog"></span> Settings</a></li>
                        <li><a href="#"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div>

    </div><!-- /.container-fluid -->
</nav>

<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
    <form role="search">
        <div class="form-group">
            <input type="text" class="form-control" placeholder="Search">
        </div>
    </form>
    <ul class="nav menu">
        <li class="active"><a href="${pageContext.request.contextPath}/page/table1.action"><span class="glyphicon glyphicon-list-alt"></span> 订台信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/page/table2.action"><span class="glyphicon glyphicon-list-alt"></span> 用户权限管理</a></li>
        <li><a href="${pageContext.request.contextPath}/page/table3.action"><span class="glyphicon glyphicon-list-alt"></span> 视频管理</a></li>
    </ul>
    <div class="attribution">Template by <a href="http://www.medialoot.com/item/lumino-admin-bootstrap-template/">Medialoot</a></div>
</div><!--/.sidebar-->

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#"><span class="glyphicon glyphicon-home"></span></a></li>
            <li class="active">Controller</li>
        </ol>
    </div><!--/.row-->

    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Controller</h1>
        </div>
    </div><!--/.row-->


    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">订台信息</div>
                <div class="panel-body">
                    <div><form action="${pageContext.request.contextPath}/admin/readExcel.action" method="post"  enctype="multipart/form-data">
                       <input type="file" id="exampleInputFile" name="excelFile"/>
                        <input type="submit" value="导入数据" id="sbm" disabled="disabled">
                    </form></div>
                    <table id="table1" data-toggle="table" data-url="tables/data1.json"  data-show-refresh="true" data-show-toggle="true" data-show-columns="true" data-search="true" data-select-item-name="toolbar1" data-pagination="true" data-sort-name="name" data-sort-order="desc">

                    </table>
                </div>
            </div>
        </div>
    </div><!--/.row-->


</div><!--/.row-->


</div><!--/.main-->

<script src="${pageContext.request.contextPath}/resources/admin/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/admin/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/admin/js/chart.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/admin/js/chart-data.js"></script>
<script src="${pageContext.request.contextPath}/resources/admin/js/easypiechart.js"></script>
<script src="${pageContext.request.contextPath}/resources/admin/js/easypiechart-data.js"></script>
<script src="${pageContext.request.contextPath}/resources/admin/js/bootstrap-datepicker.js"></script>
<script src="${pageContext.request.contextPath}/resources/admin/js/bootstrap-table.js"></script>
<script>
    !function ($) {
        $(document).on("click","ul.nav li.parent > a > span.icon", function(){
            $(this).find('em:first').toggleClass("glyphicon-minus");
        });
        $(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
    }(window.jQuery);

    $(window).on('resize', function () {
        if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
    })
    $(window).on('resize', function () {
        if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
    })
</script>





<script type="text/javascript">



    var $table = $('#table1');
    $table.bootstrapTable({
        url: "${pageContext.request.contextPath}/admin/listBookTable.action",
        dataType: "json",
        pagination: true, //分页
        singleSelect: true,



        columns:[
            {
                title: '名字',
                field: 'bookName',
                align: 'center',

            },
            {
                title: '登记手机',
                field: 'bookPhoneNumber',
                align: 'center',

            },
            {
                title: '用餐人数',
                field: 'bookPeopleNumber',
                align: 'center'
            },
            {
                title: '用餐时间',
                field: 'bookTime',
                align: 'center'
            },
            {
                title: '编辑/删除',
                field: 'id',
                align: 'center',
                formatter: function (value, row, index) {
                    var e = '<a href="#"  id="F' + row.id + '\" onclick="edit(\'' + row.id + '\',\'' + row.bookName + '\',\'' + row.bookPhoneNumber + '\',\'' + row.bookPeopleNumber + '\',\'' + row.bookTime + '\')">编辑</a> ';
                    var d = '<a href="#"  id="S' + row.id + '\" onclick="del(\'' + row.id + '\',\'' + row.bookName + '\')">删除</a> ';

                    return e + d;
                }
            }
        ]
    });


    function edit(id,bookName,bookPhoneNumber,bookPeopleNumber,bookTime){
        var a = confirm("确定编辑吗？")

        if(a==true){

            var nodes = $('#F'+id).parent().siblings() //获取 兄弟节点
            $(nodes[0]).html('<input type="text"  name="bookName" value="'+bookName+'">')
            $(nodes[1]).html('<input type="text"  name="bookPhoneNumber" value="'+bookPhoneNumber+'">');
            $(nodes[2]).html($('<input type="text"  name="bookPeopleNumber" value="'+bookPeopleNumber+'">'));
            $(nodes[3]).html($('<input type="text"  name="bookTime" value="'+bookTime+'">'));

            $('#F'+id).replaceWith('<a href="#"  id="RF' + id + '\" onclick="update(\'' + id + '\')">确定</a>');
            $('#S'+id).replaceWith('<a href="#"  id="RS' + id + '\" onclick="cle(\'' + id + '\',\'' + bookName + '\',\'' + bookPhoneNumber + '\',\'' + bookPeopleNumber + '\',\'' + bookTime + '\')">取消</a>');

        }

    }

    function cle(id,bookName,bookPhoneNumber,bookPeopleNumber,bookTime){

        var n = $('#RF'+id).parent().siblings() //获取 兄弟节点
        //text -> p
        $(n[0]).html(bookName)
        $(n[1]).html(bookPhoneNumber)
        $(n[2]).html(bookPeopleNumber)
        $(n[3]).html(bookTime)

        $('#RF'+id).replaceWith('<a href="#"  id="F' + id + '\" onclick="edit(\'' + id+ '\',\'' + bookName + '\',\'' + bookPhoneNumber + '\',\'' + bookPeopleNumber + '\',\'' + bookTime + '\')">编辑</a> ')
        $('#RS'+id).replaceWith('<a href="#"  id="S' + id + '\" onclick="del(\'' + id + '\',\'' + bookName + '\')">删除</a> ')
    }

    function update(id){
        var nodes = $('#RF'+id).parent().siblings() //获取 兄弟节点

        var  bookName  =   $(nodes[0]).children()
        var  bookPhoneNumber  =   $(nodes[1]).children()
        var  bookPeopleNumber  =   $(nodes[2]).children()
        var  bookTime  =   $(nodes[3]).children()
        var m = confirm("确定修改"+$(bookName).val()+"的订台信息吗？")
        if(m==true){
            var bookNameVal = $(bookName).val().trim()
            var bookPhoneNumberVal = $(bookPhoneNumber).val().trim()
            var bookPeopleNumberVal = $(bookPeopleNumber).val().trim()
            var bookTimeVal = $(bookTime).val().trim()

            //ajax更新信息
            var args =
            $.ajax({
                url:"${pageContext.request.contextPath}/admin/updateBookTable.action",
                type:"post",
                data:'{"id":'+id+',"bookName":"'+bookNameVal+'","bookPeopleNumber":"'+bookPeopleNumberVal+'","bookPhoneNumber":"'+bookPhoneNumberVal+'","bookTime":"'+bookTimeVal+'"}',
                contentType: 'application/json;charset=UTF-8',
                dataType: 'json',
                success:function(data){
                   if(data.length>1) {
                       for (var i in data) {
                           alert(data[i]);
                       }
                   }
                    if(data.message=="success"){
                        alert("修改成功")
                        $(bookName).replaceWith($(bookName).val())
                        $(bookPhoneNumber).replaceWith($(bookPhoneNumber).val())
                        $(bookPeopleNumber).replaceWith($(bookPeopleNumber).val())
                        $(bookTime).replaceWith($(bookTime).val())

                        $('#RF'+id).replaceWith('<a href="#"  id="F' + id + '\" onclick="edit(\'' + id+ '\',\'' + $(bookName).val() + '\',\'' + $(bookPhoneNumber).val() + '\',\'' + $(bookPeopleNumber).val() + '\',\'' + $(bookTime).val() + '\')">编辑</a> ')
                        $('#RS'+id).replaceWith('<a href="#"  id="S' + id + '\" onclick="del(\'' + id + '\',\'' + $(bookName).val() + '\')">删除</a> ')


                    }
                }
            })
        }
    }

    function del(id,bookName) {
        var d = confirm("确定要删除"+bookName+"的信息吗？")
        if(d==true){
            $.ajax({
                url:"${pageContext.request.contextPath}/admin/delBookTable.action",
                type:"POST",
                data:'{"id":'+id+'}',
                contentType: 'application/json;charset=UTF-8',
                dataType: "json",
                success:function(data){
                        if(data.message=="success"){
                            $('#F'+id).parent().parent().remove()
                        }
                }
            })
        }
    }
</script>



<script type="text/javascript">
    $('#exampleInputFile').click(function () {
            $('#sbm').attr("disabled",false)
    })
    $('#sbm').click(function () {
        if($('#exampleInputFile').val()==""){
            alert("请选择导入的文件")
            return false
        }


    })
</script>







</body>
</html>
