package com.od.ssm.service;

import com.od.ssm.po.BookTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 黄冠莳 on 2017/7/10.
 */

public interface BookTableService {
    public void insertBookTableMessage(BookTable bt);
    public int selectByPhoneAndTime(BookTable bt);
    public String listAllBookTables();
    public void updateBookTable(BookTable bookTable);
    public void deleteBookTable(BookTable bookTable);
    public void insertListBookeTable(List<BookTable> bookTableList);
}
