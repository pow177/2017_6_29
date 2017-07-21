package com.od.ssm.po;

import com.od.ssm.po.Comment;
import com.od.ssm.po.Reply;

import java.util.List;

/**
 * Created by 黄冠莳 on 2017/7/21.
 用来分页的包装类
 */
public class pageBean {

    private int total; //总记录数        --》后台
    private int pageSize;        //每页多少条记录
    private int pageNum; //第几页  -->前端

    private int pages; // 总页数  --》后台


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}

