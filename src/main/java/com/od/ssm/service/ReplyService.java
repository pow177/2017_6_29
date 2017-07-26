package com.od.ssm.service;

import com.od.ssm.bean.PageBean;
import com.od.ssm.po.Reply;

/**
 * Created by 黄冠莳 on 2017/7/21.
 */
public interface ReplyService {
    //插入一条回复,并且获得最后一页回复的内容
    public String insertReply(Reply reply);
    //根据前端传来的pageNum来获取数据
    public String getReplyByPage(PageBean pageBean);
}
