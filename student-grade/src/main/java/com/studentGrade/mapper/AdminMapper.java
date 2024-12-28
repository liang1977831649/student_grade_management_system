package com.studentGrade.mapper;

import com.studentGrade.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminMapper {
    @Select("select * from admin where id=#{id}")
    public Admin queryAdminById(Integer id);

    @Update("update admin set password=#{newPwd} where id=#{id}")
    public void updateAdminPassword(String newPwd,Integer id);

    @Select("select * from admin where id=#{id}")
    public Admin queryAdminInfo(Integer id);
}
