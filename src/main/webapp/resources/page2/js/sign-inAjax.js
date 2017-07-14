function signInAjax(){
    $.ajax({
        url: "http://localhost:8080/maven2-ssm/sign-in.action",
        type: "post",
        contentType: 'application/json;charset=UTF-8',
        data: '{"phoneNumber":"'+$('#phoneNumber').val()+'","password":"'+$('#password').val()+'"}',
        dataType: 'json',
        success: function (data) {
            var message= data.message;


            if(data!=null){        //登录成功

                var id= data.id;
                $('.dropdown.pull-right').html('<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> User<span class="caret"></span></a> <ul class="dropdown-menu" role="menu"> <li><a href="#" id="signOut"><span class="glyphicon glyphicon-log-out" style="color: black"></span><p style="color: black">注销</p></a></li> <li><a href="http://localhost:8080/maven2-ssm/goToPersonZoo.action?id='+id+'" id="personZoo"><span class="glyphicon glyphicon-log-out" style="color: black"></span><p style="color: black">个人空间</p></a></li> </ul>')
                $('.close').click()
                $('#modal-529194').replaceWith('<a id="modal-529194" href="#" role="button" class="btn btn-primary">评论</a>')
                //为注销添加点击事件
                $('.dropdown-menu li a').click(function () {
                    signOut()
                    return false;
                })
                alert("登录成功！")
            }else{
                alert("手机号码或者密码有误！")
            }
        }
    })
    $('.fh5co-form.animate-box')[0].reset();  //清空表单
}