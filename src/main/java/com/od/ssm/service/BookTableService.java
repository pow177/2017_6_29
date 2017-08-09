package com.od.ssm.service;

import com.od.ssm.po.BookTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 黄冠莳 on 2017/7/10.
 */

public interface BookTableService {
    //插入
    public void insertBookTableMessage(BookTable bt);
    //根据手机跟时间查
    public int selectByPhoneAndTime(BookTable bt);
    //JSON格式返回所有数据
    public String listAllBookTables();
    //更新
    public void updateBookTable(BookTable bookTable);
    //删除
    public void deleteBookTable(BookTable bookTable);
    //批量插入
    public void insertListBookeTable(List<BookTable> bookTableList);
    //查询所有信息并且返回List
    public List<BookTable> getAllBookTables();


}
