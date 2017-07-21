package com.od.ssm.mapper;

import com.github.abel533.mapper.Mapper;
import com.od.ssm.po.Comment;

import java.util.List;

/**
 * Created by 黄冠莳 on 2017/7/17.
 */
public interface CommentMapper extends Mapper<Comment> {
    public List<Comment> getCommentAndUserByPage();
    public void insertComment(Comment comment);
}
