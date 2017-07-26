package com.od.ssm.mapper;

import com.github.abel533.mapper.Mapper;
import com.od.ssm.po.Reply;

import java.util.List;

/**
 * Created by 黄冠莳 on 2017/7/21.
 */
public interface ReplyMapper extends Mapper<Reply> {
    public List<Reply> selectReplyAndUser(int c_id);
    public void insertReply(Reply reply);
}
