package com.studentGrade.server;


import com.studentGrade.entity.Course;
import com.studentGrade.entity.PageBean;

public interface CourseServer {
    PageBean<Course> searchServer(String searchName, Integer pageNum, Integer pageSize,String courseName,String teacherName);
    void addCourse(Course course);
    void editCourse(Course course);
    void deleteCourse(Integer id);
    PageBean<Course> searchServerForStudent(String searchName,Integer pageNum,Integer pageSize,String courseNo,String teacherName);
}
