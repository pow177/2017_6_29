function addLogo(){
 $.ajax({
                url: "http://localhost:8080/maven2-ssm/checkUserIfLogin.action",
                type: "post",
                contentType: 'application/json;charset=UTF-8',
                data: '{"date":"'+new Date()+'"}',
                dataType: 'json',
                success: function (data) {
                    for (var prop in data)
                    {
                        var message= data[prop]
                    }
                    if(message=='noLogin'){//用户未登录
                        $('#modal-529194').replaceWith('<a id="modal-529194" href="#modal-container-529194" role="button" class="btn btn-primary" data-toggle="modal">评论</a>')
						$('.dropdown.pull-right').html('<a href="#modal-container-529194" class="dropdown-toggle" data-toggle="modal"><span class="glyphicon glyphicon-user"></span> User<span class="caret"></span></a>')

                    }else{//用户已登录
                       var id = data.id
                        $('#modal-529194').replaceWith('<a id="modal-529194" href="#" role="button" class="btn btn-primary">评论</a>')
                       alert("123")
                        $('.dropdown.pull-right').html('<a href="#" class="dropdown-toggle" data-toggle="dropdown">' +
                            '<span class="glyphicon glyphicon-user"></span> User' +
                            '<span class="caret"></span></a> ' +
                            '<ul class="dropdown-menu" role="menu"> ' +
                            '<li><a href="#" id="signOut"><span class="glyphicon glyphicon-log-out" style="color: black"></span>' +
                            '<p style="color: black">注销</p></a></li> ' +
                            '<li><a href="http://localhost:8080/maven2-ssm/goToPersonZoo.action?id='+id+'" id="personZoo"><span class="glyphicon glyphicon-log-out" style="color: black"></span><p style="color: black">个人空间</p></a></li> </ul>')
                        alert("123")
                        //为注销添加点击事件
                        $('.dropdown-menu li a').click(function () {
                            alert("123")
                            signOut()

                        })
                    }
                }
            })
}