/**
 * Created by powerman on 2017/7/21.
 */
function  commentAndReply() {

    $.ajax({
        url: "http://localhost:8080/maven2-ssm/comment/getCommentByPage.action",
        type: "post",
        contentType: 'application/json;charset=UTF-8',
        data:'{"pageNum":1}',      //默认第一页
        dataType: 'json',
        success: function (data) {

            var comment = data;
            for(var i=0;i<comment.length;i++){
                //这里添加评论的信息
                $('.commentList').append('<div id="cc'+data[i].c_id+'"><li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="././resources/page2/images/about.jpeg" height="50" width="50"></i></a> ' +
                    '<div class="comment-main">' +
                    '<header class="comment-header">' +
                    '<div class="comment-meta"><a class="comment-author" href="#">'+data[i].user.name+'</a> 评论于' +
                    '<time title="'+data[i].c_date+'" >'+data[i].c_date+'</time>' +
                    '</div>' +
                    '</header>' +
                    '<div class="comment-body">' +
                    '<p style="color: black">'+data[i].c_words+'</p>' +
                    ' </div>' +
                    '</div>' +
                    '</li>' +
                    ' <li class="item cl" style="text-align: right"> <button type="button" id="reply'+data[i].c_id+'" class="btn btn-link btn-sm">回复</button></li></div>')


                //添加 “回复” 点击事件
                $('#reply'+data[i].c_id).click(data[i],function (event) {
                    //添加节点显示回复内容于 回复评论的输入框 及发表按钮
                    $('#cc' + event.data.c_id).after('<div class="myZoom" id="myZoom' + event.data.c_id + '"><ul><li style="text-align: right"><input type="text" align="right" id="wenben' + event.data.c_id + '"></li>' +
                        '<li style="text-align: right"><button type="button" value="发表" id="fabiao' + event.data.c_id + '">发表</button></li></ul></div>')
                    //将 回复 替换成 收起回复
                    $('#reply' + event.data.c_id).replaceWith(' <button type="button" id="hidReply' + event.data.c_id + '" class="btn btn-link btn-sm">收起回复</button>')


                    //为发表添加点击事件
                    $('#fabiao' + event.data.c_id).click(event.data, function (e) {
                        var r_words = $('#wenben' + e.data.c_id).val()
                        var c_id = event.data.c_id

                        $.ajax({
                            url: "http://localhost:8080/maven2-ssm/comment/getReplyBypage.action",
                            type: "post",
                            contentType: 'application/json;charset=UTF-8',
                            date: '{"r_words":"' + r_words + '",' +
                            '"c_id":"' + c_id + '"}',
                            dataType: 'json',
                            success: function (data) {
                                //提交成功后，添加回复的节点


                            }


                        })
                    })

                    //为收起回复添加点击事件
                    $('#hidReply' + event.data.c_id).click(event.data, function (event2) {
                        //移除回复内容
                        $('#myZoom' + event2.data.c_id).hide()

                        //将  收起回复 替换成 回复
                        $('#hidReply' + event2.data.c_id).replaceWith(' <button type="button" id="reply' + event2.data.c_id + '" class="btn btn-link btn-sm">回复</button>')


                        // /为回复添加点击事件
                        $('#reply' + event2.data.c_id).click(event.data, function (event3) {
                            var display = $('#myZoom' + event3.data.c_id).css('display');
                            if (display == 'none') {
                                $('#myZoom' + event3.data.c_id).show()
                                $('#reply' + event3.data.c_id).text('收起回复')
                            } else {
                                $('#reply' + event3.data.c_id).text('回复')
                                $('#myZoom' + event3.data.c_id).hide()
                            }
                        })

                    })
                })

                //事件添加结束
            }

                //添加分页按钮
            $('.commentList').after(' <div class="row"><ul id="fenye"></ul></div>')

           for(var i2=1;i2<=data[0].pageBean.pages;i2++){

            $('#fenye').append('<li><button type="button" class="btn btn-lg btn-link btn-block" id="fenyeBtn'+i2+'">'+i2+'</button></li>')

               $('#fenyeBtn'+i2).click(i2,function (event) {

                   //获取对应页数的数据
                   $.ajax({
                       url: "http://localhost:8080/maven2-ssm/comment/getCommentByPage.action",
                       type: "post",
                       contentType: 'application/json;charset=UTF-8',
                       data: '{"pageNum":' + event.data + '}',
                       dataType: 'json',
                       success: function (data) {
                           //获取成功后，先移除当前已显示的数据 .commentList remove 然后显示新的数据 然后把当前btn设置成不可点击
                           $('.commentList').children().remove()
                           for(var i=0;i<comment.length;i++){
                               //这里添加评论的信息
                               $('.commentList').append('<div id="cc'+data[i].c_id+'"><li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="././resources/page2/images/about.jpeg" height="50" width="50"></i></a> ' +
                                   '<div class="comment-main">' +
                                   '<header class="comment-header">' +
                                   '<div class="comment-meta"><a class="comment-author" href="#">'+data[i].user.name+'</a> 评论于' +
                                   '<time title="'+data[i].c_date+'" >'+data[i].c_date+'</time>' +
                                   '</div>' +
                                   '</header>' +
                                   '<div class="comment-body">' +
                                   '<p style="color: black">'+data[i].c_words+'</p>' +
                                   ' </div>' +
                                   '</div>' +
                                   '</li>' +
                                   ' <li class="item cl" style="text-align: right"> <button type="button" id="reply'+data[i].c_id+'" class="btn btn-link btn-sm">回复</button></li></div>')


                               //添加 “回复” 点击事件
                               $('#reply'+data[i].c_id).click(data[i],function (event) {
                                   //添加节点显示回复内容于 回复评论的输入框 及发表按钮
                                   $('#cc' + event.data.c_id).after('<div class="myZoom" id="myZoom' + event.data.c_id + '"><ul><li style="text-align: right"><input type="text" align="right" id="wenben' + event.data.c_id + '"></li>' +
                                       '<li style="text-align: right"><button type="button" value="发表" id="fabiao' + event.data.c_id + '">发表</button></li></ul></div>')
                                   //将 回复 替换成 收起回复
                                   $('#reply' + event.data.c_id).replaceWith(' <button type="button" id="hidReply' + event.data.c_id + '" class="btn btn-link btn-sm">收起回复</button>')


                                   //为发表添加点击事件
                                   $('#fabiao' + event.data.c_id).click(event.data, function (e) {
                                       var r_words = $('#wenben' + e.data.c_id).val()
                                       var c_id = event.data.c_id

                                       $.ajax({
                                           url: "http://localhost:8080/maven2-ssm/comment/getReplyBypage.action",
                                           type: "post",
                                           contentType: 'application/json;charset=UTF-8',
                                           date: '{"r_words":"' + r_words + '",' +
                                           '"c_id":"' + c_id + '"}',
                                           dataType: 'json',
                                           success: function (data) {
                                               //提交成功后，添加回复的节点


                                           }


                                       })
                                   })

                                   //为收起回复添加点击事件
                                   $('#hidReply' + event.data.c_id).click(event.data, function (event2) {
                                       //移除回复内容
                                       $('#myZoom' + event2.data.c_id).hide()

                                       //将  收起回复 替换成 回复
                                       $('#hidReply' + event2.data.c_id).replaceWith(' <button type="button" id="reply' + event2.data.c_id + '" class="btn btn-link btn-sm">回复</button>')


                                       // /为回复添加点击事件
                                       $('#reply' + event2.data.c_id).click(event.data, function (event3) {
                                           var display = $('#myZoom' + event3.data.c_id).css('display');
                                           if (display == 'none') {
                                               $('#myZoom' + event3.data.c_id).show()
                                               $('#reply' + event3.data.c_id).text('收起回复')
                                           } else {
                                               $('#reply' + event3.data.c_id).text('回复')
                                               $('#myZoom' + event3.data.c_id).hide()
                                           }
                                       })

                                   })
                               })

                               //事件添加结束
                           }
                       }
                   })
               })


           }



        }
//



    })


}