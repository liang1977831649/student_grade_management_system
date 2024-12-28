package com.studentGrade.mapper;

import com.studentGrade.entity.StudentCourse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentCourseMapper {

    @Insert("insert into studentcourse(id,course_no,student_id) value (null,#{courseNo},#{studentId})")
    public void insert(StudentCourse studentCourse);

    @Select("select * from studentcourse where course_no=#{courseId} and student_id=#{studentId}")
    public StudentCourse studentCourseBySIdAndCId(String courseId,int studentId);


    @Select("select * from studentcourse where student_id=#{id}")
    public List<StudentCourse> studentCourseByStudentId(int id);

    @Select("update studentcourse set course_no=#{newCourseNo} where course_no=#{courseNo}")
    public void updateStudentCourseByCourseNo(String newCourseNo,String courseNo);

    public List<StudentCourse> selectStudentCoursePersonal(int id,String courseNo);

    void deleteByCourseNoAndStudentId(String courseNo, Integer id);

    List<StudentCourse> selectAllStudentCourse(String courseNo, String studentName);


    void updateScoreAndCommentByStudentIdAndCourseNo(StudentCourse studentCourse);

    void updateFeedBack(StudentCourse studentCourse);
}
