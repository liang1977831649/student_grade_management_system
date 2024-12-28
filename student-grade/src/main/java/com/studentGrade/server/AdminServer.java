package com.studentGrade.server;

import com.studentGrade.entity.Account;
import com.studentGrade.entity.Admin;

public interface AdminServer {
    public Admin queryAdminById(Account account);
    public void  updatePasswordByAdminId(String oldPwd,String newPwd,Integer id);

    public Admin getAdminInfo(Integer id);
}
