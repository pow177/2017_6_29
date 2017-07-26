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
                    '<div class="showPic" id="showPic'+data[i].c_id+i+'"></div>' +
                    '<p style="color: black">' + data[i].c_words + '</p>' +
                    ' </div>' +
                    '</div>' +
                    '</li>' +
                    ' <li class="item cl" style="text-align: right"> <button type="button" id="reply'+data[i].c_id+'" class="btn btn-link btn-sm">回复</button></li></div>')
                //判断是否存在图片信息，若有则向上添加图片url.
                if(data[i].pic_urls.length!=0){

                    var imgs = data[i].pic_urls;
                    if(imgs[0]!="") {
                        for (var p = 0; p < imgs.length; p++) {

                            $('#showPic'+data[i].c_id+i).append('<img alt="" src="' + imgs[p] + '" >')
                        }
                    }
                }


                //添加 “回复” 点击事件
                $('#reply'+data[i].c_id).click(data[i],function (event) {
                    //ajax获取回复的信息
                    $.ajax({
                        url: "http://localhost:8080/maven2-ssm/comment/getReplyByPage.action",
                        type: "post",
                        contentType: 'application/json;charset=UTF-8',
                        data:'{"pageNum":1,"c_id":"'+event.data.c_id+'"}',      //默认第一页
                        dataType: 'json',
                        success: function (dd) {
                            //添加 回复的信息
                            //这里用主评论的id 既c_id 去标识这一列的回复内容
                            $('#cc' + dd[0].c_id).after('<div class="myZoom"> ' +
                                '<ul class="commentList" id="huifuList'+dd[0].c_id+'"> </ul> </div>')
                            for(var r=0;r<dd.length;r++){
                                //往上面新添加的节点append回复信息
                                $('#huifuList'+dd[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="././resources/page2/images/about.jpeg" height="50" width="50"></i></a> ' +
                                    '<div class="comment-main"> ' +
                                    '<header class="comment-header"> ' +
                                    '<div class="comment-meta"><a class="comment-author" href="#">'+dd[r].user.name+'</a> 评论于 ' +
                                    '<time title="2014年8月31日 下午3:20" datetime="'+dd[r].r_date+'">'+dd[r].r_date+'</time>' +
                                    '</div>' +
                                    '</header> ' +
                                    '<div class="comment-body"> ' +
                                    '<p style="color: black">回复<a href="#">@'+dd[r].comment.user.name+'</a>'+dd[r].r_words+'</p> ' +
                                    '</div> ' +
                                    '</div>' +
                                    '</li>')
                            }

                            //添加回复内容的分页按钮

                            //添加分页按钮
                            $('#huifuList'+dd[0].c_id).after(' <div class="row" id="rowHuiFu"><ul id="huifuBtnLi'+dd[0].c_id+'"></ul></div>')

                            for(var r2=1;r2<=dd[0].pageBean.pages;r2++) {
                                var pack = {"pageNum":r2,"c_id":dd[0].c_id}; //包装数据
                                $('#huifuBtnLi' + dd[0].c_id).append('<li><button type="button" class="btn btn-lg btn-link btn-block" id="replyFenYeBtn' + dd[0].c_id +r2+ '">' + r2 + '</button></li>')
                                //为分页按钮添加点击事件
                                $('#replyFenYeBtn' + dd[0].c_id+r2).click(pack,function (pack) {

                                    $.ajax({
                                        url: "http://localhost:8080/maven2-ssm/comment/getReplyByPage.action",
                                        type: "post",
                                        contentType: 'application/json;charset=UTF-8',
                                        data: '{"pageNum":'+pack.data.pageNum+',"c_id":"'+pack.data.c_id+'"}',
                                        dataType: 'json',
                                        success: function (d1) {

                                            $('#huifuList'+d1[0].c_id).remove();///移除旧页的节点

                                            //添加 回复的信息
                                            //这里用主评论的id 既c_id 去标识这一列的回复内容
                                            $('#cc' + d1[0].c_id).after('<div class="myZoom"> ' +
                                                '<ul class="commentList" id="huifuList'+d1[0].c_id+'"> </ul> </div>')
                                            for(var r=0;r<d1.length;r++) {
                                                //往上面新添加的节点append回复信息
                                                $('#huifuList' + d1[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="././resources/page2/images/about.jpeg" height="50" width="50"></i></a> ' +
                                                    '<div class="comment-main"> ' +
                                                    '<header class="comment-header"> ' +
                                                    '<div class="comment-meta"><a class="comment-author" href="#">' + d1[r].user.name + '</a> 评论于 ' +
                                                    '<time title="2014年8月31日 下午3:20" datetime="' + d1[r].r_date + '">' + d1[r].r_date + '</time>' +
                                                    '</div>' +
                                                    '</header> ' +
                                                    '<div class="comment-body"> ' +
                                                    '<p style="color: black">回复<a href="#">@' + d1[r].comment.user.name + '</a>' + d1[r].r_words + '</p> ' +
                                                    '</div> ' +
                                                    '</div>' +
                                                    '</li>')
                                            }


                                        }
                                    })

                                })
                                //为分页按钮添加点击事件结束
                            }
                        }


                    })
//ajax获取回复的信息结束
                    //添加节点显示回复内容于 回复评论的输入框 及发表按钮
                    $('#cc' + event.data.c_id).after('<div class="myZoom" id="myZoom' + event.data.c_id + '"><ul id="wenbenUl"><li style="text-align: right"><textarea type="text" align="right" id="wenben' + event.data.c_id + '" style="width: 700px;height: 100px"></textarea></li>' +
                        '<li style="text-align: right"><button type="button" value="发表" id="fabiao' + event.data.c_id + '">发表</button></li></ul></div>')
                    //将 回复 替换成 收起回复
                    $('#reply' + event.data.c_id).replaceWith(' <button type="button" id="hidReply' + event.data.c_id + '" class="btn btn-link btn-sm">收起回复</button>')


                    //为发表添加点击事件
                    $('#fabiao' + event.data.c_id).click(event.data, function (e) {
                        if($('.dropdown-menu').length<=0){
                            alert("你还未登录")
                            $('#modal-529194').click()
                            return false;
                        }
                        var r_words = $('#wenben' + e.data.c_id).val()
                        var c_id = event.data.c_id

                        $.ajax({
                            url: "http://localhost:8080/maven2-ssm/comment/insertReply.action",
                            type: "post",
                            contentType: 'application/json;charset=UTF-8',
                            data: '{"r_words":"' + r_words + '",' +
                            '"c_id":"' + c_id + '"}',
                            dataType: 'json',
                            success: function (data) {           //这里获得的是最后一页的信息

                                $('#huifuList'+data[0].c_id).remove();//移除旧页的节点
                                $('#wenben'+data[0].c_id).val('')
                                alert("回复成功")
                                //提交成功后，添加回复的节点
                                //添加 回复的信息
                                $('#cc' +data[0].c_id).after('<div class="myZoom"> ' +
                                    '<ul class="commentList" id="huifuList'+data[0].c_id+'"> </ul> </div>')
                                for(var r=0;r<data.length;r++){

                                    //往上面新添加的节点append回复信息
                                    $('#huifuList'+data[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="././resources/page2/images/about.jpeg" height="50" width="50"></i></a> ' +
                                        '<div class="comment-main"> ' +
                                        '<header class="comment-header"> ' +
                                        '<div class="comment-meta"><a class="comment-author" href="#">'+data[r].user.name+'</a> 评论于 ' +
                                        '<time title="2014年8月31日 下午3:20" datetime="'+data[r].r_date+'">'+data[r].r_date+'</time>' +
                                        '</div>' +
                                        '</header> ' +
                                        '<div class="comment-body"> ' +
                                        '<p style="color: black">回复<a href="#">@'+data[r].comment.user.name+'</a>'+data[r].r_words+'</p> ' +
                                        '</div> ' +
                                        '</div>' +
                                        '</li>')
                                }
                                //发表完后，如果pages为1的时候
                                if(data[0].pageBean.pages==1){


                                    var p = {"pageNum":1,"c_id":data[0].c_id}
                                    $('#huifuBtnLi'+data[0].c_id).append('<li><button type="button" class="btn btn-lg btn-link btn-block" id="replyFenYeBtn1' +data[0].c_id+ '>' + 1 + '</button></li>') //添加新按钮
                                    //为新的按钮添加点击事件
                                    $('#replyFenYeBtn1'+data[0].c_id).click(p,function (p) {
                                        $.ajax({
                                            url: "http://localhost:8080/maven2-ssm/comment/getReplyByPage.action",
                                            type: "post",
                                            contentType: 'application/json;charset=UTF-8',
                                            data: '{"pageNum":"' + p.data.pageNum + '",' +
                                            '"c_id":"' + p.data.c_id + '"}',
                                            dataType: 'json',
                                            success: function (d3) {
                                                $('#huifuList'+d3[0].c_id).remove();//移除旧页的节点
                                                //添加 回复的信息
                                                //这里用主评论的id 既c_id 去标识这一列的回复内容
                                                $('#cc' + d3[0].c_id).after('<div class="myZoom"> ' +
                                                    '<ul class="commentList" id="huifuList'+d3[0].c_id+'"> </ul> </div>')
                                                for(var r=0;r<d3.length;r++) {
                                                    //往上面新添加的节点append回复信息
                                                    $('#huifuList' + d3[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="././resources/page2/images/about.jpeg" height="50" width="50"></i></a> ' +
                                                        '<div class="comment-main"> ' +
                                                        '<header class="comment-header"> ' +
                                                        '<div class="comment-meta"><a class="comment-author" href="#">' + d3[r].user.name + '</a> 评论于 ' +
                                                        '<time title="2014年8月31日 下午3:20" datetime="' + d3[r].r_date + '">' + d3[r].r_date + '</time>' +
                                                        '</div>' +
                                                        '</header> ' +
                                                        '<div class="comment-body"> ' +
                                                        '<p style="color: black">回复<a href="#">@' + d3[r].comment.user.name + '</a>' + d3[r].r_words + '</p> ' +
                                                        '</div> ' +
                                                        '</div>' +
                                                        '</li>')
                                                }


                                            }
                                        })
                                    })
                                }
//-------------------------------------------  //发表完后，如果pages为1的时候--------------end
                                //判断是否需要添加一个新的分页按钮
                                var newL = data[0].pageBean.pages;
                                var oldL = $('#huifuBtnLi'+data[0].c_id).children().length

                                if(newL>oldL){
                                    var pack = {"pageNum":newL,"c_id":data[0].c_id}
                                    $('#huifuBtnLi'+data[0].c_id).append('<li><button type="button" class="btn btn-lg btn-link btn-block" id="replyFenYeBtn' +data[0].c_id+ newL + '">' + newL + '</button></li>') //添加新按钮
                                    //为新的按钮添加点击事件
                                    $('#replyFenYeBtn'+data[0].c_id+newL).click(pack,function (pack) {
                                        $.ajax({
                                            url: "http://localhost:8080/maven2-ssm/comment/getReplyByPage.action",
                                            type: "post",
                                            contentType: 'application/json;charset=UTF-8',
                                            data: '{"pageNum":"' + pack.data.pageNum + '",' +
                                            '"c_id":"' + pack.data.c_id + '"}',
                                            dataType: 'json',
                                            success: function (d3) {
                                                $('#huifuList'+d3[0].c_id).remove();//移除旧页的节点
                                                //添加 回复的信息
                                                //这里用主评论的id 既c_id 去标识这一列的回复内容
                                                $('#cc' + d3[0].c_id).after('<div class="myZoom"> ' +
                                                    '<ul class="commentList" id="huifuList'+d3[0].c_id+'"> </ul> </div>')
                                                for(var r=0;r<d3.length;r++) {
                                                    //往上面新添加的节点append回复信息
                                                    $('#huifuList' + d3[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="././resources/page2/images/about.jpeg" height="50" width="50"></i></a> ' +
                                                        '<div class="comment-main"> ' +
                                                        '<header class="comment-header"> ' +
                                                        '<div class="comment-meta"><a class="comment-author" href="#">' + d3[r].user.name + '</a> 评论于 ' +
                                                        '<time title="2014年8月31日 下午3:20" datetime="' + d3[r].r_date + '">' + d3[r].r_date + '</time>' +
                                                        '</div>' +
                                                        '</header> ' +
                                                        '<div class="comment-body"> ' +
                                                        '<p style="color: black">回复<a href="#">@' + d3[r].comment.user.name + '</a>' + d3[r].r_words + '</p> ' +
                                                        '</div> ' +
                                                        '</div>' +
                                                        '</li>')
                                                }


                                            }
                                        })
                                    })
                                }
                                //结束------------------------------------------------

                            }


                        })
                    })

                    //为收起回复添加点击事件
                    $('#hidReply' + event.data.c_id).click(event.data, function (event2) {
                        //移除回复内容
                        $('#myZoom' + event2.data.c_id).hide()
                        $('#huifuList'+ event2.data.c_id).hide()

                        $('#huifuBtnLi'+ event2.data.c_id).hide()
                        //将  收起回复 替换成 回复
                        $('#hidReply' + event2.data.c_id).replaceWith(' <button type="button" id="reply' + event2.data.c_id + '" class="btn btn-link btn-sm">回复</button>')


                        // /为回复添加点击事件
                        $('#reply' + event2.data.c_id).click(event.data, function (event3) {
                            var display = $('#myZoom' + event3.data.c_id).css('display');
                            if (display == 'none') {
                                $('#myZoom' + event3.data.c_id).show()
                                $('#huifuList'+ event2.data.c_id).show()
                                $('#huifuBtnLi'+ event3.data.c_id).show()
                                $('#reply' + event3.data.c_id).text('收起回复')
                            } else {
                                $('#reply' + event3.data.c_id).text('回复')
                                $('#huifuBtnLi'+ event3.data.c_id).hide()
                                $('#myZoom' + event3.data.c_id).hide()
                                $('#huifuList'+ event2.data.c_id).hide()
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
                           //获取成功后，先移除当前已显示的数据 .commentList remove 然后显示新的数据
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
                                   '<div class="showPic" id="showPic'+data[i].c_id+i+'"></div>' +
                                   '<p style="color: black">' + data[i].c_words + '</p>' +
                                   ' </div>' +
                                   '</div>' +
                                   '</li>' +
                                   ' <li class="item cl" style="text-align: right"> <button type="button" id="reply'+data[i].c_id+'" class="btn btn-link btn-sm">回复</button></li></div>')
                               //判断是否存在图片信息，若有则向上添加图片url.

                               if(data[i].pic_urls.length!=0){
                                   var imgs = data[i].pic_urls;
                                   if(imgs[0]!=""){

                                       for (var p = 0; p < imgs.length; p++) {
                                           $('#showPic'+data[i].c_id+i).append('<img alt="" src="' + imgs[p] + '" >')
                                       }
                                   }
                               }

                               //添加 “回复” 点击事件
                               $('#reply'+data[i].c_id).click(data[i],function (event) {
                                   //ajax获取回复的信息
                                   $.ajax({
                                       url: "http://localhost:8080/maven2-ssm/comment/getReplyByPage.action",
                                       type: "post",
                                       contentType: 'application/json;charset=UTF-8',
                                       data:'{"pageNum":1,"c_id":"'+event.data.c_id+'"}',      //默认第一页
                                       dataType: 'json',
                                       success: function (dd) {
                                           //添加 回复的信息
                                           //这里用主评论的id 既c_id 去标识这一列的回复内容
                                           $('#cc' + dd[0].c_id).after('<div class="myZoom"> ' +
                                               '<ul class="commentList" id="huifuList'+dd[0].c_id+'"> </ul> </div>')
                                           for(var r=0;r<dd.length;r++){
                                               //往上面新添加的节点append回复信息
                                               $('#huifuList'+dd[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="././resources/page2/images/about.jpeg" height="50" width="50"></i></a> ' +
                                                   '<div class="comment-main"> ' +
                                                   '<header class="comment-header"> ' +
                                                   '<div class="comment-meta"><a class="comment-author" href="#">'+dd[r].user.name+'</a> 评论于 ' +
                                                   '<time title="2014年8月31日 下午3:20" datetime="'+dd[r].r_date+'">'+dd[r].r_date+'</time>' +
                                                   '</div>' +
                                                   '</header> ' +
                                                   '<div class="comment-body"> ' +
                                                   '<p style="color: black">回复<a href="#">@'+dd[r].comment.user.name+'</a>'+dd[r].r_words+'</p> ' +
                                                   '</div> ' +
                                                   '</div>' +
                                                   '</li>')
                                           }

                                           //添加回复内容的分页按钮

                                           //添加分页按钮
                                           $('#huifuList'+dd[0].c_id).after(' <div class="row" id="rowHuiFu"><ul id="huifuBtnLi'+dd[0].c_id+'"></ul></div>')

                                           for(var r2=1;r2<=dd[0].pageBean.pages;r2++) {
                                               var pack = {"pageNum":r2,"c_id":dd[0].c_id}; //包装数据
                                               $('#huifuBtnLi' + dd[0].c_id).append('<li><button type="button" class="btn btn-lg btn-link btn-block" id="replyFenYeBtn' + dd[0].c_id +r2+ '">' + r2 + '</button></li>')
                                               //为分页按钮添加点击事件
                                               $('#replyFenYeBtn' + dd[0].c_id+r2).click(pack,function (pack) {

                                                   $.ajax({
                                                       url: "http://localhost:8080/maven2-ssm/comment/getReplyByPage.action",
                                                       type: "post",
                                                       contentType: 'application/json;charset=UTF-8',
                                                       data: '{"pageNum":'+pack.data.pageNum+',"c_id":"'+pack.data.c_id+'"}',
                                                       dataType: 'json',
                                                       success: function (d1) {

                                                           $('#huifuList'+d1[0].c_id).remove();///移除旧页的节点

                                                           //添加 回复的信息
                                                           //这里用主评论的id 既c_id 去标识这一列的回复内容
                                                           $('#cc' + d1[0].c_id).after('<div class="myZoom"> ' +
                                                               '<ul class="commentList" id="huifuList'+d1[0].c_id+'"> </ul> </div>')
                                                           for(var r=0;r<d1.length;r++) {
                                                               //往上面新添加的节点append回复信息
                                                               $('#huifuList' + d1[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="././resources/page2/images/about.jpeg" height="50" width="50"></i></a> ' +
                                                                   '<div class="comment-main"> ' +
                                                                   '<header class="comment-header"> ' +
                                                                   '<div class="comment-meta"><a class="comment-author" href="#">' + d1[r].user.name + '</a> 评论于 ' +
                                                                   '<time title="2014年8月31日 下午3:20" datetime="' + d1[r].r_date + '">' + d1[r].r_date + '</time>' +
                                                                   '</div>' +
                                                                   '</header> ' +
                                                                   '<div class="comment-body"> ' +
                                                                   '<p style="color: black">回复<a href="#">@' + d1[r].comment.user.name + '</a>' + d1[r].r_words + '</p> ' +
                                                                   '</div> ' +
                                                                   '</div>' +
                                                                   '</li>')
                                                           }


                                                       }
                                                   })

                                               })
                                               //为分页按钮添加点击事件结束
                                           }
                                       }


                                   })
//ajax获取回复的信息结束
                                   //添加节点显示回复内容于 回复评论的输入框 及发表按钮
                                   $('#cc' + event.data.c_id).after('<div class="myZoom" id="myZoom' + event.data.c_id + '"><ul id="wenbenUl"><li style="text-align: right"><textarea type="text" align="right" id="wenben' + event.data.c_id + '" style="width: 700px;height: 100px"></textarea></li>' +
                                       '<li style="text-align: right"><button type="button" value="发表" id="fabiao' + event.data.c_id + '">发表</button></li></ul></div>')
                                   //将 回复 替换成 收起回复
                                   $('#reply' + event.data.c_id).replaceWith(' <button type="button" id="hidReply' + event.data.c_id + '" class="btn btn-link btn-sm">收起回复</button>')


                                   //为发表添加点击事件
                                   $('#fabiao' + event.data.c_id).click(event.data, function (e) {
                                       if($('.dropdown-menu').length<=0){
                                           alert("你还未登录")
                                           $('#modal-529194').click()
                                           return false;
                                       }
                                       var r_words = $('#wenben' + e.data.c_id).val()
                                       var c_id = event.data.c_id

                                       $.ajax({
                                           url: "http://localhost:8080/maven2-ssm/comment/insertReply.action",
                                           type: "post",
                                           contentType: 'application/json;charset=UTF-8',
                                           data: '{"r_words":"' + r_words + '",' +
                                           '"c_id":"' + c_id + '"}',
                                           dataType: 'json',
                                           success: function (data) {           //这里获得的是最后一页的信息

                                               $('#huifuList'+data[0].c_id).remove();//移除旧页的节点
                                               $('#wenben'+data[0].c_id).val('')
                                               alert("回复成功")
                                               //提交成功后，添加回复的节点
                                               //添加 回复的信息
                                               $('#cc' +data[0].c_id).after('<div class="myZoom"> ' +
                                                   '<ul class="commentList" id="huifuList'+data[0].c_id+'"> </ul> </div>')
                                               for(var r=0;r<data.length;r++){

                                                   //往上面新添加的节点append回复信息
                                                   $('#huifuList'+data[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="././resources/page2/images/about.jpeg" height="50" width="50"></i></a> ' +
                                                       '<div class="comment-main"> ' +
                                                       '<header class="comment-header"> ' +
                                                       '<div class="comment-meta"><a class="comment-author" href="#">'+data[r].user.name+'</a> 评论于 ' +
                                                       '<time title="2014年8月31日 下午3:20" datetime="'+data[r].r_date+'">'+data[r].r_date+'</time>' +
                                                       '</div>' +
                                                       '</header> ' +
                                                       '<div class="comment-body"> ' +
                                                       '<p style="color: black">回复<a href="#">@'+data[r].comment.user.name+'</a>'+data[r].r_words+'</p> ' +
                                                       '</div> ' +
                                                       '</div>' +
                                                       '</li>')
                                               }

                                               //发表完后，如果pages为1的时候
                                               if(data[0].pageBean.pages==1){

                                                   var p = {"pageNum":1,"c_id":data[0].c_id}
                                                   $('#huifuList'+data[0].c_id).after(' <div class="row" id="rowHuiFu"><ul id="huifuBtnLi'+data[0].c_id+'"></ul></div>')

                                                   
                                                   $('#huifuBtnLi'+data[0].c_id).append('<li><button type="button" class="btn btn-lg btn-link btn-block" id="replyFenYeBtn1' +data[0].c_id+ '>' + 1 + '</button></li>') //添加新按钮
                                                   //为新的按钮添加点击事件
                                                   $('#replyFenYeBtn1'+data[0].c_id).click(p,function (p) {
                                                       $.ajax({
                                                           url: "http://localhost:8080/maven2-ssm/comment/getReplyByPage.action",
                                                           type: "post",
                                                           contentType: 'application/json;charset=UTF-8',
                                                           data: '{"pageNum":"' + p.data.pageNum + '",' +
                                                           '"c_id":"' + p.data.c_id + '"}',
                                                           dataType: 'json',
                                                           success: function (d3) {
                                                               $('#huifuList'+d3[0].c_id).remove();//移除旧页的节点
                                                               //添加 回复的信息
                                                               //这里用主评论的id 既c_id 去标识这一列的回复内容
                                                               $('#cc' + d3[0].c_id).after('<div class="myZoom"> ' +
                                                                   '<ul class="commentList" id="huifuList'+d3[0].c_id+'"> </ul> </div>')
                                                               for(var r=0;r<d3.length;r++) {
                                                                   //往上面新添加的节点append回复信息
                                                                   $('#huifuList' + d3[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="././resources/page2/images/about.jpeg" height="50" width="50"></i></a> ' +
                                                                       '<div class="comment-main"> ' +
                                                                       '<header class="comment-header"> ' +
                                                                       '<div class="comment-meta"><a class="comment-author" href="#">' + d3[r].user.name + '</a> 评论于 ' +
                                                                       '<time title="2014年8月31日 下午3:20" datetime="' + d3[r].r_date + '">' + d3[r].r_date + '</time>' +
                                                                       '</div>' +
                                                                       '</header> ' +
                                                                       '<div class="comment-body"> ' +
                                                                       '<p style="color: black">回复<a href="#">@' + d3[r].comment.user.name + '</a>' + d3[r].r_words + '</p> ' +
                                                                       '</div> ' +
                                                                       '</div>' +
                                                                       '</li>')
                                                               }


                                                           }
                                                       })
                                                   })
                                               }
//-------------------------------------------  //发表完后，如果pages为1的时候--------------end

                                               //判断是否需要添加一个新的分页按钮
                                               var newL = data[0].pageBean.pages;
                                               var oldL = $('#huifuBtnLi'+data[0].c_id).children().length

                                               if(newL>oldL){
                                                   var pack = {"pageNum":newL,"c_id":data[0].c_id}
                                                   $('#huifuBtnLi'+data[0].c_id).append('<li><button type="button" class="btn btn-lg btn-link btn-block" id="replyFenYeBtn' +data[0].c_id+ newL + '">' + newL + '</button></li>') //添加新按钮
                                                   //为新的按钮添加点击事件
                                                   $('#replyFenYeBtn'+data[0].c_id+newL).click(pack,function (pack) {
                                                       $.ajax({
                                                           url: "http://localhost:8080/maven2-ssm/comment/getReplyByPage.action",
                                                           type: "post",
                                                           contentType: 'application/json;charset=UTF-8',
                                                           data: '{"pageNum":"' + pack.data.pageNum + '",' +
                                                           '"c_id":"' + pack.data.c_id + '"}',
                                                           dataType: 'json',
                                                           success: function (d3) {
                                                               $('#huifuList'+d3[0].c_id).remove();//移除旧页的节点
                                                               //添加 回复的信息
                                                               //这里用主评论的id 既c_id 去标识这一列的回复内容
                                                               $('#cc' + d3[0].c_id).after('<div class="myZoom"> ' +
                                                                   '<ul class="commentList" id="huifuList'+d3[0].c_id+'"> </ul> </div>')
                                                               for(var r=0;r<d3.length;r++) {
                                                                   //往上面新添加的节点append回复信息
                                                                   $('#huifuList' + d3[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="././resources/page2/images/about.jpeg" height="50" width="50"></i></a> ' +
                                                                       '<div class="comment-main"> ' +
                                                                       '<header class="comment-header"> ' +
                                                                       '<div class="comment-meta"><a class="comment-author" href="#">' + d3[r].user.name + '</a> 评论于 ' +
                                                                       '<time title="2014年8月31日 下午3:20" datetime="' + d3[r].r_date + '">' + d3[r].r_date + '</time>' +
                                                                       '</div>' +
                                                                       '</header> ' +
                                                                       '<div class="comment-body"> ' +
                                                                       '<p style="color: black">回复<a href="#">@' + d3[r].comment.user.name + '</a>' + d3[r].r_words + '</p> ' +
                                                                       '</div> ' +
                                                                       '</div>' +
                                                                       '</li>')
                                                               }


                                                           }
                                                       })
                                                   })
                                               }
                                               //结束------------------------------------------------

                                           }


                                       })
                                   })

                                   //为收起回复添加点击事件
                                   $('#hidReply' + event.data.c_id).click(event.data, function (event2) {
                                       //移除回复内容
                                       $('#myZoom' + event2.data.c_id).hide()
                                       $('#huifuList'+ event2.data.c_id).hide()

                                       $('#huifuBtnLi'+ event2.data.c_id).hide()
                                       //将  收起回复 替换成 回复
                                       $('#hidReply' + event2.data.c_id).replaceWith(' <button type="button" id="reply' + event2.data.c_id + '" class="btn btn-link btn-sm">回复</button>')


                                       // /为回复添加点击事件
                                       $('#reply' + event2.data.c_id).click(event.data, function (event3) {
                                           var display = $('#myZoom' + event3.data.c_id).css('display');
                                           if (display == 'none') {
                                               $('#myZoom' + event3.data.c_id).show()
                                               $('#huifuList'+ event2.data.c_id).show()
                                               $('#huifuBtnLi'+ event3.data.c_id).show()
                                               $('#reply' + event3.data.c_id).text('收起回复')
                                           } else {
                                               $('#reply' + event3.data.c_id).text('回复')
                                               $('#huifuBtnLi'+ event3.data.c_id).hide()
                                               $('#myZoom' + event3.data.c_id).hide()
                                               $('#huifuList'+ event2.data.c_id).hide()
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