package com.od.ssm.service;

import com.od.ssm.po.BookTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 黄冠莳 on 2017/7/10.
 */

public interface BookTableService {
    public void insertBookTableMessage(BookTable bt);
}
