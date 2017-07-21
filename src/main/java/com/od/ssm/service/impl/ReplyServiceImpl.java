package com.od.ssm.service.impl;

import com.od.ssm.mapper.ReplyMapper;
import com.od.ssm.po.Reply;
import com.od.ssm.service.ReplyService;
import com.od.ssm.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 黄冠莳 on 2017/7/21.
 */
@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyMapper replyMapper;
    //插入一条回复
    public String insertReply(Reply reply){



        replyMapper.insert(reply);
        JsonMapper jm = new JsonMapper();
       //此时reply应该有主键
        return jm.toJson(reply);
    }
}
