package com.od.ssm.service.impl;

import com.od.ssm.mapper.BookTableMapper;
import com.od.ssm.po.BookTable;
import com.od.ssm.service.BookTableService;
import com.od.ssm.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 黄冠莳 on 2017/7/10.
 */
@Service
public class BookTableServiceImpl implements BookTableService{
   @Autowired
   private BookTableMapper bookTableMapper;

   public void insertBookTableMessage(BookTable bt){
       bookTableMapper.insert(bt);
    }

    public int selectByPhoneAndTime(BookTable bt){
//进行查询操作
       int count = bookTableMapper.selectCountByBookNameAndPhone(bt);
       return count;

    }
    public String listAllBookTables(){
        List<BookTable> bookTableList =  bookTableMapper.select(new BookTable());
        JsonMapper jm = new JsonMapper();
        //转成json数据返回
        return  jm.toJson(bookTableList);
    }


    public void updateBookTable(BookTable bookTable){
        bookTableMapper.updateByPrimaryKey(bookTable);
    }

    public void deleteBookTable(BookTable bookTable){
        bookTableMapper.delete(bookTable);

    }
    //批量插入信息
    public void insertListBookeTable(List<BookTable> bookTableList){
        bookTableMapper.insertListBookTables(bookTableList);
    }
}
