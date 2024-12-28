package com.studentGrade.server.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.studentGrade.entity.Course;
import com.studentGrade.entity.PageBean;
import com.studentGrade.entity.StudentCourse;
import com.studentGrade.mapper.CourseMapper;
import com.studentGrade.mapper.StudentCourseMapper;
import com.studentGrade.mapper.TeacherMapper;
import com.studentGrade.server.CourseServer;
import com.studentGrade.utils.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class CourseServerImpl implements CourseServer {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    StudentCourseMapper studentCourseMapper;

    @Override
    public PageBean<Course> searchServer(String searchName,Integer pageNum,Integer pageSize,String courseNo,String teacherName) {
        PageBean<Course> coursePageBean = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);

        List<Course> courses = courseMapper.listCourse(searchName,courseNo,teacherName);
        Page<Course> page=(Page<Course>) courses;



        coursePageBean.setItems(page.getResult());
        coursePageBean.setTotal((int)page.getTotal());

        return coursePageBean;
    }

    @Override
    public PageBean<Course> searchServerForStudent(String searchName,Integer pageNum,Integer pageSize,String courseNo,String teacherName) {
        PageBean<Course> coursePageBean = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);

        List<Course> courses = courseMapper.listCourse(searchName,courseNo,teacherName);
        Page<Course> page=(Page<Course>) courses;

        //设计标记,如果某些课程被学生选过，那就标记为1
        Integer id = (Integer) ((HashMap<String, Object>) ThreadLocalUtils.get()).get("id");
        List<StudentCourse> studentCourses = studentCourseMapper.studentCourseByStudentId(id);
        //两层for循环，将被选择过的课程标记为1
        for (StudentCourse studentCourse : studentCourses) {
            for (Course course : courses) {
                if(Objects.equals(course.getNo(), studentCourse.getCourseNo())){
                    //开始标记
                    course.setStudentChoose(1);
                }
            }
        }

        coursePageBean.setItems(page.getResult());
        coursePageBean.setTotal((int)page.getTotal());
        return coursePageBean;
    }

    @Override
    public void addCourse(Course course) {
        courseMapper.insert(course);
    }

    @Override
    public void editCourse(Course course) {
        Course courseById = courseMapper.queryCourseById(course.getId());
        //改变本张表的course的信息
        courseMapper.update(course);
        //因为是要关联到学生选课表，因此可能需要改变学生选课表的courseNo
        //先获取到当前的课表,查看courseNo是否发生变化，如果变化了，那就改变StudentCourse表的ID
        if(!courseById.getNo().equals(course.getNo())){
            studentCourseMapper.updateStudentCourseByCourseNo(course.getNo(),courseById.getNo());
        }
    }

    @Override
    public void deleteCourse(Integer id) {
        courseMapper.delete(id);
    }
}
