package com.studentGrade.server;

import com.studentGrade.entity.PageBean;
import com.studentGrade.entity.StudentCourse;


public interface StudentCourseServer {
    public void add(StudentCourse studentCourse);

    PageBean<StudentCourse> listPersonal(Integer pageNum,Integer pageSize,String courseNo);

    void del(String courseNo);

    PageBean<StudentCourse> listAll(Integer pageNum, Integer pageSize, String courseNo, String studentName);

    void deleteByManager(String courseNo, Integer studentId);

    void updateScoreAndComment(StudentCourse studentCourse);

    void feedback(StudentCourse studentCourse);
}
