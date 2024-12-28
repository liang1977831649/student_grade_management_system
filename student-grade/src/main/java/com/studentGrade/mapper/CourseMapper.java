package com.studentGrade.mapper;

import com.studentGrade.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {
    List<Course> listCourse(String searchName,String courseNo,String teacherName);
    void insert(Course course);
    void update(Course course);
    void delete(Integer id);

    @Select("select * from course where no=#{no}")
    Course queryCourseByNo(String no);

    @Select("select * from course where id=#{id}")
    Course queryCourseById(int id);

}
