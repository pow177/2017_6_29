package com.od.ssm.service;

import com.od.ssm.bean.PageBean;
import com.od.ssm.po.Comment;

/**
 * Created by 黄冠莳 on 2017/7/18.
 */
public interface CommentService {
    public String insertCommentAndGetNewComment(Comment comment);
    public String getCommentByPage(PageBean pb);
    public int getAllCounts();
}
