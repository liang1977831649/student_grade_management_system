package com.studentGrade.mapper;

import com.studentGrade.entity.Account;
import com.studentGrade.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("select * from student where id=#{id}")
    Student selectStudentById(Account account);

    @Select("select * from student where id =#{id}")
    Student queryStudentById(Integer id);

    void addStudent(Account account);

    void addStudentByManage(Student student);

    void updateStudent(Student  student);

    List<Student> queryStudentByIdAndName(Integer id, String name);

    void delete(Integer id);

    void updatePassword(String newPwd,Integer id);
}
