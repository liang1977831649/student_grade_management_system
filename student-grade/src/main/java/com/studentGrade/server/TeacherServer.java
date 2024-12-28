package com.studentGrade.server;

import com.studentGrade.entity.PageBean;
import com.studentGrade.entity.Teacher;
import org.springframework.stereotype.Service;

@Service
public interface TeacherServer {
    PageBean<Teacher> listTeacher(String name, String no, Integer pageNum, Integer pageSize);
    void addServer(Teacher teacher);

    void editServer(Teacher teacher);

    void deleteServer(Integer id);
}
