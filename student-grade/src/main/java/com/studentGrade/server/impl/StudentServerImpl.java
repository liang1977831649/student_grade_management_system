package com.studentGrade.server.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.studentGrade.entity.Account;
import com.studentGrade.entity.PageBean;
import com.studentGrade.entity.Student;
import com.studentGrade.mapper.StudentMapper;
import com.studentGrade.server.StudentServer;
import com.studentGrade.utils.Md5Util;
import com.studentGrade.utils.ThreadLocalUtils;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class StudentServerImpl implements StudentServer {
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public Student queryStudentById(Account account) {
        return studentMapper.selectStudentById(account);
    }

    @Override
    public Student getStudentInfo(Integer id) {
        return  studentMapper.queryStudentById(id);

    }

    @Override
    public void addStudent(Account account) {
        //先判断有无这个用户
        Student student = studentMapper.queryStudentById(account.getId());

        if(student!=null){
            throw new RuntimeException("该账号已存在");
        }

        account.setPassword(Md5Util.getMD5String(account.getPassword()));
        account.setRole(1);
        studentMapper.addStudent(account);
    }

    @Override
    public void addStudentManage(Student student) {
        //检查改student是否存在
        Student studentId = studentMapper.queryStudentById(student.getId());
        if(studentId!=null){
            throw new RuntimeException("该账号已存在");
        }
        student.setPassword(Md5Util.getMD5String(student.getPassword()));
        student.setRole(1);
        studentMapper.addStudentByManage(student);
    }

    @Override
    public PageBean<Student> queryStudent(Integer id, String name,Integer pageNum,Integer pageSize) {
        PageBean<Student> studentPageBean = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);

        List<Student> students = studentMapper.queryStudentByIdAndName(id, name);

        Page<Student> page=(Page<Student>)students;

        studentPageBean.setTotal((int)page.getTotal());
        studentPageBean.setItems(page.getResult());
        return studentPageBean;
    }

    @Override
    public void updateStudentById(Student student) {
        if(!StringUtil.isNullOrEmpty(student.getPassword())){
            student.setPassword(Md5Util.getMD5String(student.getPassword()));
        }
        studentMapper.updateStudent(student);
    }

    @Override
    public void deleteStudentById(Integer id) {
        studentMapper.delete(id);
    }

    @Override
    public boolean updatePassword(String oldPwd, String newPwd) {
        //获取当前ID
        HashMap<String,Object> map = ThreadLocalUtils.get();
        Integer id  =(Integer) map.get("id");
        Student studentById = studentMapper.queryStudentById(id);
        if (studentById==null){
            return false;
        }
        //如果原密码错误
        if(!Md5Util.checkPassword(oldPwd,studentById.getPassword())){
            return false;
        }
        //对新密码进行加密
        newPwd=Md5Util.getMD5String(newPwd);
        studentMapper.updatePassword(newPwd,id);
        return true;
    }
}
