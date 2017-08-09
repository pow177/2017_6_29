function upTop(){
    $(".js-gotop").click()
    return true

}
function addLogo(editor){


 $.ajax({
                url: "http://47.93.49.124:81/tasty/checkUserIfLogin.action",
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
                        $('#modal-529194').replaceWith('<a id="modal-529194" href="#modal-container-529194" role="button" class="btn btn-primary" data-toggle="modal" onclick="upTop()">评论</a>')
						$('.dropdown.pull-right').html('<a href="#modal-container-529194" class="dropdown-toggle" data-toggle="modal" onclick="upTop()"><span class="glyphicon glyphicon-user"></span> User<span class="caret"></span></a>')

                    }else{//用户已登录
                        editor.$textElem.attr('contenteditable', true);
                        var id = data.id

                        $('#modal-529194').replaceWith('<span class="comment"><input type="button" id="modal-529194" value="评论" class="btn btn-primary"/></span>')

                        $('.dropdown.pull-right').html('<a href="#" class="dropdown-toggle" data-toggle="dropdown">' +
                            '<span class="glyphicon glyphicon-user"></span> User' +
                            '<span class="caret"></span></a> ' +
                            '<ul class="dropdown-menu" role="menu"> ' +
                            '<li id="signOut"><a href="#"><span class="glyphicon glyphicon-log-out" style="color: black"></span>' +
                            '<p style="color: black">注销</p></a></li> ' +
                            '<li><a href="http://47.93.49.124:81/tasty/page/userZoom.action" id="personZoo"><span class="glyphicon glyphicon-log-out" style="color: black"></span><p style="color: black">个人空间</p></a></li> </ul>')


                        //为注销添加点击事件
                        $('#signOut a').click(function () {
                            signOut()
                        })

                        //为评论添加点击事件

                        $('.comment input').click(function () {

                            if(editor.txt.text().length<=0){
                                alert("请填写评论信息")
                                return false
                            }
                            if(editor.txt.text().length>0&&editor.txt.text().length<6){
                                alert("评论不得少于6个字")
                                return false
                            } if(editor.txt.text().length>250){
                                alert("评论不得长于250个字")
                                return false
                            }

                        //将富文本的图片的url拼起来
                            var picStr = "";
                            var picList1 =  editor.$textElem.children()
                            for(var im1=0;im1<picList1.length;im1++){
                                if(picList1[im1].src!=null){
                                picStr=picStr+","+picList1[im1].src;
                                }
                            }
                            var picList2 = editor.$textElem.children().children()
                            for(var im2=0;im2<picList2.length;im2++){
                                if(picList2[im2].src!=null){
                                    picStr=picStr+","+picList2[im2].src;
                                }
                            }
                            //拼接结束

                            $.ajax({
                                url: "http://47.93.49.124:81/tasty/comment/insertComment.action",
                                type: "post",
                                contentType: 'application/json;charset=UTF-8',
                                data: '{"c_words":"' + editor.txt.text() + '","pic_url":"'+picStr+'"}',
                                dataType: 'json',

                                success: function (data) {

                                    //将icomment的值设置为1
                                    $("#icomment").val("1");

                                    alert("评论成功")
                                    editor.txt.clear()         //清楚富文本内的文字
                                    editor.txt.html('<p style="color: black"></p>')


                                    $('.commentList').children().remove()
                                    for (var i = 0; i < data.length; i++) {
                                        //这里添加评论的信息
                                        $('.commentList').append('<div id="cc' + data[i].c_id + '"><li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="'+data[i].user.imgUrl+'" height="50" width="50"></i></a> ' +
                                            '<div class="comment-main">' +
                                            '<header class="comment-header">' +
                                            '<div class="comment-meta"><a class="comment-author" href="#">' + data[i].user.name + '</a> 评论于' +
                                            '<time title="' + data[i].c_date + '" >' + data[i].c_date + '</time>' +
                                            '</div>' +
                                            '</header>' +
                                            '<div class="comment-body">' +
                                            '<div class="showPic" id="showPic'+data[i].c_id+i+'"></div>' +
                                            '<p style="color: black">' + data[i].c_words + '</p>' +
                                            ' </div>' +
                                            '</div>' +
                                            '</li>' +
                                            ' <li class="item cl" style="text-align: right"> <button type="button" id="reply' + data[i].c_id + '" class="btn btn-link btn-sm">回复</button></li></div>')

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
                                                url: "http://47.93.49.124:81/tasty/comment/getReplyByPage.action",
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
                                                        $('#huifuList'+dd[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="'+dd[r].user.imgUrl+'" height="50" width="50"></i></a> ' +
                                                            '<div class="comment-main"> ' +
                                                            '<header class="comment-header"> ' +
                                                            '<div class="comment-meta"><a class="comment-author" href="#">'+dd[r].user.name+'</a> 评论于 ' +
                                                            '<time title="2014年8月31日 下午3:20" datetime="'+dd[r].r_date+'">'+dd[r].r_date+'</time>' +
                                                            '</div>' +
                                                            '</header> ' +
                                                            '<div class="comment-body"> ' +
                                                            '<p style="color: black">回复<a href="#">@'+dd[r].comment.user.name+'</a>:'+dd[r].r_words+'</p> ' +
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
                                                                url: "http://47.93.49.124:81/tasty/comment/getReplyByPage.action",
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
                                                                        $('#huifuList' + d1[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="'+d1[r].user.imgUrl+'" height="50" width="50"></i></a> ' +
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
                                                    url: "http://47.93.49.124:81/tasty/comment/insertReply.action",
                                                    type: "post",
                                                    contentType: 'application/json;charset=UTF-8',
                                                    data: '{"r_words":"' + r_words + '",' +
                                                    '"c_id":"' + c_id + '"}',
                                                    dataType: 'json',
                                                    success: function (data) {           //这里获得的是最后一页的信息

                                                        $('#huifuList'+data[0].c_id).remove();//移除旧页的节点
                                                        alert("回复成功")
                                                        //提交成功后，添加回复的节点
                                                        //添加 回复的信息
                                                        $('#cc' +data[0].c_id).after('<div class="myZoom"> ' +
                                                            '<ul class="commentList" id="huifuList'+data[0].c_id+'"> </ul> </div>')
                                                        for(var r=0;r<data.length;r++){

                                                            //往上面新添加的节点append回复信息
                                                            $('#huifuList'+data[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="'+data[r].user.imgUrl+'" height="50" width="50"></i></a> ' +
                                                                '<div class="comment-main"> ' +
                                                                '<header class="comment-header"> ' +
                                                                '<div class="comment-meta"><a class="comment-author" href="#">'+data[r].user.name+'</a> 评论于 ' +
                                                                '<time title="'+data[r].r_date+'" datetime="'+data[r].r_date+'">'+data[r].r_date+'</time>' +
                                                                '</div>' +
                                                                '</header> ' +
                                                                '<div class="comment-body"> ' +
                                                                '<p style="color: black">回复<a href="#">@'+data[r].comment.user.name+'</a>'+data[r].r_words+'</p> ' +
                                                                '</div> ' +
                                                                '</div>' +
                                                                '</li>')
                                                        }


                                                        //发表完后，如果pages为1的时候
                                                        if(data[0].pageBean.pages==1&&$('#huifuBtnLi'+data[0].c_id).children().length==0){
                                                            $('#huifuList'+data[0].c_id).after(' <div class="row" id="rowHuiFu"><ul id="huifuBtnLi'+data[0].c_id+'"></ul></div>');
                                                            $('#huifuBtnLi'+data[0].c_id).append('<li><button type="button" class="btn btn-lg btn-link btn-block" id="replyFenYeBtn1' +data[0].c_id+ '">' + 1 + '</button></li>') //添加新按钮
                                                            //为新的按钮添加点击事件
                                                            $('#replyFenYeBtn1'+data[0].c_id).click(data[0].c_id,function (p) {

                                                                $.ajax({
                                                                    url: "http://47.93.49.124:81/tasty/comment/getReplyByPage.action",
                                                                    type: "post",
                                                                    contentType: 'application/json;charset=UTF-8',
                                                                    data: '{"pageNum":"' + 1 + '",' +
                                                                    '"c_id":"' + p.data + '"}',
                                                                    dataType: 'json',
                                                                    success: function (d3) {
                                                                        $('#huifuList'+d3[0].c_id).remove();//移除旧页的节点
                                                                        //添加 回复的信息
                                                                        //这里用主评论的id 既c_id 去标识这一列的回复内容
                                                                        $('#cc' + d3[0].c_id).after('<div class="myZoom"> ' +
                                                                            '<ul class="commentList" id="huifuList'+d3[0].c_id+'"> </ul> </div>')
                                                                        for(var r=0;r<d3.length;r++) {
                                                                            //往上面新添加的节点append回复信息
                                                                            $('#huifuList' + d3[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="'+d3[r].user.imgUrl+'" height="50" width="50"></i></a> ' +
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
                                                                    url: "http://47.93.49.124:81/tasty/comment/getReplyByPage.action",
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
                                                                            $('#huifuList' + d3[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="'+d3[r].user.imgUrl+'" height="50" width="50"></i></a> ' +
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


                                    }
                        //------------------/事件添加结束


                       //---------------- 判断更新完成后是否需要添加多一个新的分页页面,这里是主评论的
                                    var cL = $('#fenye').children().length

                                    if(data[0].pageBean.pages>cL){

                                        $('#fenye').append('<li><button type="button" class="btn btn-lg btn-link btn-block" id="fenyeBtn'+data[0].pageBean.pages+'">'+data[0].pageBean.pages+'</button></li>')

                                        //为新的分页按钮添加点击事件
                                        $('#fenyeBtn'+data[0].pageBean.pages).click(data[0].pageBean.pages,function (event) {

                                            //获取对应页数的数据
                                            $.ajax({
                                                url: "http://47.93.49.124:81/tasty/comment/getCommentByPage.action",
                                                type: "post",
                                                contentType: 'application/json;charset=UTF-8',
                                                data: '{"pageNum":' + event.data + '}',
                                                dataType: 'json',
                                                success: function (data) {
                                                    //获取成功后，先移除当前已显示的数据 .commentList remove 然后显示新的数据 然后把当前btn设置成不可点击
                                                    $('.commentList').children().remove()
                                                    for(var i=0;i<data.length;i++){
                                                        //这里添加评论的信息
                                                        $('.commentList').append('<div id="cc'+data[i].c_id+'"><li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="'+data[i].user.imgUrl+'" height="50" width="50"></i></a> ' +
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

                                                        //判断是否有图片
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
                                                                url: "http://47.93.49.124:81/tasty/comment/getReplyByPage.action",
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
                                                                        $('#huifuList'+dd[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="'+dd[r].user.imgUrl+'" height="50" width="50"></i></a> ' +
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
                                                                                url: "http://47.93.49.124:81/tasty/comment/getReplyByPage.action",
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
                                                                                        $('#huifuList' + d1[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="' + d1[r].user.imgUrl + '" height="50" width="50"></i></a> ' +
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
                                                                    url: "http://47.93.49.124:81/tasty/comment/insertReply.action",
                                                                    type: "post",
                                                                    contentType: 'application/json;charset=UTF-8',
                                                                    data: '{"r_words":"' + r_words + '",' +
                                                                    '"c_id":"' + c_id + '"}',
                                                                    dataType: 'json',
                                                                    success: function (data) {           //这里获得的是最后一页的信息

                                                                        $('#huifuList'+data[0].c_id).remove();//移除旧页的节点
                                                                        alert("回复成功")
                                                                        //提交成功后，添加回复的节点
                                                                        //添加 回复的信息
                                                                        $('#cc' +data[0].c_id).after('<div class="myZoom"> ' +
                                                                            '<ul class="commentList" id="huifuList'+data[0].c_id+'"> </ul> </div>')
                                                                        for(var r=0;r<data.length;r++){

                                                                            //往上面新添加的节点append回复信息
                                                                            $('#huifuList'+data[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="'+data[r].user.imgUrl+'" height="50" width="50"></i></a> ' +
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
                                                                        if(data[0].pageBean.pages==1&&$('#huifuBtnLi'+data[0].c_id).children().length==0){
                                                                            $('#huifuList'+data[0].c_id).after(' <div class="row" id="rowHuiFu"><ul id="huifuBtnLi'+data[0].c_id+'"></ul></div>')
                                                                            $('#huifuBtnLi'+data[0].c_id).append('<li><button type="button" class="btn btn-lg btn-link btn-block" id="replyFenYeBtn1' +data[0].c_id+ '">' + 1 + '</button></li>') //添加新按钮

                                                                            //为新的按钮添加点击事件
                                                                            $('#replyFenYeBtn1'+data[0].c_id).click(data[0].c_id,function (p) {
                                                                                $.ajax({
                                                                                    url: "http://47.93.49.124:81/tasty/comment/getReplyByPage.action",
                                                                                    type: "post",
                                                                                    contentType: 'application/json;charset=UTF-8',
                                                                                    data: '{"pageNum":"' + 1 + '",' +
                                                                                    '"c_id":"' + p.data+ '"}',
                                                                                    dataType: 'json',
                                                                                    success: function (d3) {
                                                                                        $('#huifuList'+d3[0].c_id).remove();//移除旧页的节点
                                                                                        //添加 回复的信息
                                                                                        //这里用主评论的id 既c_id 去标识这一列的回复内容
                                                                                        $('#cc' + d3[0].c_id).after('<div class="myZoom"> ' +
                                                                                            '<ul class="commentList" id="huifuList'+d3[0].c_id+'"> </ul> </div>')
                                                                                        for(var r=0;r<d3.length;r++) {
                                                                                            //往上面新添加的节点append回复信息
                                                                                            $('#huifuList' + d3[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="' + d3[r].user.imgUrl + '" height="50" width="50"></i></a> ' +
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
                                                                                    url: "http://47.93.49.124:81/tasty/comment/getReplyByPage.action",
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
                                                                                            $('#huifuList' + d3[0].c_id).append(' <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="'+d3[r].user.imgUrl+'" height="50" width="50"></i></a> ' +
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



                                        //添加新消息提示点击事件
                                        //移除旧的
                                        $("#newCommentTips").remove()
                                        //重新添加
                                        $(".list-group").before('<button class="btn btn-large btn-block" type="button" id="newCommentTips" style="color: black;">有新消息 </button>')
                                        $("#newCommentTips").hide()
                                        $("#newCommentTips").click(function(){

                                            $('#fenyeBtn'+data[0].pageBean.pages).click()
                                            $("#newCommentTips").hide()
                                        })
                                        //添加新消息提示点击事件结束




                                    }
              //-----------------------评论完添加新的分页按钮结束
                                }
                                })



                        })
                    //事件添加结束

                       }

                    }

            })
}