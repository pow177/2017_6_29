package com.od.ssm.service.impl;

import com.od.ssm.mapper.BookTableMapper;
import com.od.ssm.po.BookTable;
import com.od.ssm.service.BookTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * Created by 黄冠莳 on 2017/7/10.
 */
@Service
public class BookTableServiceImpl implements BookTableService{
   @Autowired
   private BookTableMapper BTMapper;

   public void insertBookTableMessage(BookTable bt){
            BTMapper.insert(bt);
    }

    public int selectByPhoneAndTime(BookTable bt){
//进行查询操作
       int count = BTMapper.selectCountByBookNameAndPhone(bt);
       return count;

    }
}
