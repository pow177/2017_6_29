<%--
  Created by IntelliJ IDEA.
  User: powerman
  Date: 2017/6/24
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery.dataTables.js"></script>



    <title>Title123</title>
</head>
<body>
<table id="datatable" width="100%" border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Operation</th>
    </tr>
    <thead>

    <script type="text/javascript">

        $(document).ready(function() {

            $("#datatable").dataTable({
                "processing": true,
               "serverSide": true,
                "ajax" : "${pageContext.request.contextPath}/datetable.action",
                "columns": [
                    {"data": "id", "bSortable": false},
                    {"data": "firstName"},
                    {"data": "lastName"},
                    {"data": "Operation"}
                ]
            });

        });    </script>
        </table>
        </body>
        </html>
