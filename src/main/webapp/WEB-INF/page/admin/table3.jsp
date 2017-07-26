<%--
  Created by IntelliJ IDEA.
  User: powerman
  Date: 2017/7/24
  Time: 17:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <li><a href="${pageContext.request.contextPath}/page/table1.action"><span class="glyphicon glyphicon-list-alt"></span> 订台信息管理</a></li>
        <li><a href="${pageContext.request.contextPath}/page/table2.action"><span class="glyphicon glyphicon-list-alt"></span> 用户权限管理</a></li>
        <li  class="active"><a href="${pageContext.request.contextPath}/page/table3.action"><span class="glyphicon glyphicon-list-alt"></span> 视频管理</a></li>


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
                <div class="panel-heading">视频管理</div>
                <div class="panel-body">
                    <table data-toggle="table" data-url="tables/data1.json"  data-show-refresh="true" data-show-toggle="true" data-show-columns="true" data-search="true" data-select-item-name="toolbar1" data-pagination="true" data-sort-name="name" data-sort-order="desc">
                        <thead>
                        <tr>
                            <th data-field="state" data-checkbox="true" >Item ID</th>
                            <th data-field="id" data-sortable="true">Item ID</th>
                            <th data-field="name"  data-sortable="true">Item Name</th>
                            <th data-field="price" data-sortable="true">Item Price</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
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
</body>

</html>
