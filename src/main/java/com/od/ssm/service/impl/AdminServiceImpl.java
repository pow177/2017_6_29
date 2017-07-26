package com.od.ssm.service.impl;

import com.od.ssm.bean.ResultBean;
import com.od.ssm.mapper.AdminMapper;
import com.od.ssm.po.Admin;
import com.od.ssm.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 黄冠莳 on 2017/7/24.
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public String validateMessage(Admin admin) {
        int count = adminMapper.selectCount(admin);
        if(count>0){
            return "success"
            ;
        }
        return "fail";
    }
}
