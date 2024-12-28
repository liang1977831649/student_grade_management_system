package com.studentGrade.server.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.studentGrade.entity.PageBean;
import com.studentGrade.entity.Teacher;
import com.studentGrade.mapper.TeacherMapper;
import com.studentGrade.server.TeacherServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServerImpl implements TeacherServer {
    @Autowired
    private TeacherMapper teacherMapper;


    @Override
    public PageBean<Teacher> listTeacher(String name,String no,Integer pageNum,Integer pageSize) {
        PageBean<Teacher> teacherPageBean = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);

        List<Teacher> teachers = teacherMapper.listTeacher(name, no);
        Page<Teacher> page=(Page<Teacher>)teachers;

        teacherPageBean.setTotal((int)page.getTotal());
        teacherPageBean.setItems(page.getResult());
        return teacherPageBean;
    }

    @Override
    public void addServer(Teacher teacher) {
        teacherMapper.add(teacher);
    }

    @Override
    public void editServer(Teacher teacher) {
        teacherMapper.update(teacher);
    }

    @Override
    public void deleteServer(Integer id) {
        teacherMapper.delete(id);
    }
}
