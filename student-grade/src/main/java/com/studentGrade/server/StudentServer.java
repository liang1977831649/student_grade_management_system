package com.studentGrade.server;

import com.studentGrade.entity.Account;
import com.studentGrade.entity.PageBean;
import com.studentGrade.entity.Student;

public interface StudentServer {
    Student queryStudentById(Account account);

    Student getStudentInfo(Integer id);

    void addStudent(Account account);

    PageBean<Student> queryStudent(Integer id, String name,Integer pageNum,Integer pageSize);

    void addStudentManage(Student student);

    void updateStudentById(Student student);

    void deleteStudentById(Integer id);

    boolean updatePassword(String oldPwd,String newPwd);
}
