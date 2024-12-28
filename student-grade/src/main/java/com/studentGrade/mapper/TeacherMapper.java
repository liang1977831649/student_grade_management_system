package com.studentGrade.mapper;

import com.studentGrade.entity.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper {
    List<Teacher> listTeacher(String name,String no);

    void add(Teacher teacher);

    void update(Teacher teacher);

    @Delete("delete  from teacher where id=#{id}")
    void delete(Integer id);


}
