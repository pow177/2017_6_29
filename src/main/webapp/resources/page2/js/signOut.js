/**
 * Created by powerman on 2017/7/14.
 */
function signOut(){
    $.ajax({
        url: "http://localhost:8080/maven2-ssm/signOut.action",
        type: "post",
        contentType: 'application/json;charset=UTF-8',
        data: '{"date":"'+new Date()+'"}',
        dataType: 'json',
        success: function (data) {


            $('#modal-529194').replaceWith('<a id="modal-529194" href="#modal-container-529194" role="button" class="btn btn-primary" data-toggle="modal">评论</a>')
            $('.dropdown.pull-right').html('<a href="#modal-container-529194" class="dropdown-toggle" data-toggle="modal"><span class="glyphicon glyphicon-user"></span> User ')
            alert("注销成功！")
        }
    })
}