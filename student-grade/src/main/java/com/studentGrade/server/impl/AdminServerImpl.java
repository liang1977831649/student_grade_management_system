package com.studentGrade.server.impl;

import com.studentGrade.entity.Account;
import com.studentGrade.entity.Admin;
import com.studentGrade.mapper.AdminMapper;
import com.studentGrade.server.AdminServer;
import com.studentGrade.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServerImpl implements AdminServer {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin queryAdminById(Account account) {
        return adminMapper.queryAdminById(account.getId());
    }

    @Override
    public void updatePasswordByAdminId(String oldPwd,String newPwd,Integer id ) {
        //查看原密码是否正确
        Admin adminById = adminMapper.queryAdminById(id);
        if(adminById==null){
            throw new RuntimeException("该账号不存在");
        }
        //加密原密码
        oldPwd=Md5Util.getMD5String(oldPwd);
        if(!oldPwd.equals(adminById.getPassword())){
            throw new RuntimeException("原密码错误");
        }
        newPwd=Md5Util.getMD5String(newPwd);
        adminMapper.updateAdminPassword(newPwd, id);
    }

    @Override
    public Admin getAdminInfo(Integer id) {
        return adminMapper.queryAdminInfo(id);
    }
}
