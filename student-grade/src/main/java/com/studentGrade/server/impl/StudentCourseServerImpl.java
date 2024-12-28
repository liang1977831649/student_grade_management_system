package com.studentGrade.server.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.studentGrade.entity.Course;
import com.studentGrade.entity.PageBean;
import com.studentGrade.entity.StudentCourse;
import com.studentGrade.mapper.CourseMapper;
import com.studentGrade.mapper.StudentCourseMapper;
import com.studentGrade.server.StudentCourseServer;
import com.studentGrade.utils.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class StudentCourseServerImpl implements StudentCourseServer {
    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public void add(StudentCourse studentCourse) {
        HashMap<String,Object> map = ThreadLocalUtils.get();
        Integer id =(Integer) map.get("id");
        studentCourse.setStudentId(id);
        //查询是否有该课程，或者该课程是否已被选定
        Course course = courseMapper.queryCourseByNo(studentCourse.getCourseNo());
        if(course==null){
            throw  new RuntimeException("该课程不存在");
        }
        StudentCourse studentCourseBySIdAndCId = studentCourseMapper.studentCourseBySIdAndCId(studentCourse.getCourseNo(), studentCourse.getStudentId());
        if(studentCourseBySIdAndCId!=null){
            throw new RuntimeException("该课程已被选定");
        }

        studentCourseMapper.insert(studentCourse);
    }

    @Override
    public PageBean<StudentCourse> listPersonal(Integer pageNum,Integer pageSize,String courseNo) {
        PageBean<StudentCourse> studentCoursePageBean = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        //获取个人学号
        Integer id = (Integer) ((HashMap<String, Object>) ThreadLocalUtils.get()).get("id");
        List<StudentCourse> studentCourses = studentCourseMapper.selectStudentCoursePersonal(id,courseNo);
        Page<StudentCourse> page =(Page<StudentCourse>) studentCourses;

        studentCoursePageBean.setTotal((int)((page.getTotal())));
        studentCoursePageBean.setItems(page.getResult());

        return studentCoursePageBean;
    }

    @Override
    public void del(String courseNo) {
        Integer id = (Integer) ((HashMap<String, Object>) ThreadLocalUtils.get()).get("id");
        StudentCourse studentCourseBySIdAndCId = studentCourseMapper.studentCourseBySIdAndCId(courseNo, id);
        if(studentCourseBySIdAndCId.getScore()!=null){
            throw new RuntimeException("该课程已被评分，不可删除");
        }
        studentCourseMapper.deleteByCourseNoAndStudentId(courseNo,id);
    }

    @Override
    public PageBean<StudentCourse> listAll(Integer pageNum, Integer pageSize, String courseNo, String studentName) {
        PageBean<StudentCourse> pageBean=new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);

        List<StudentCourse> studentCourses = studentCourseMapper.selectAllStudentCourse(courseNo, studentName);

        Page<StudentCourse> page =(Page<StudentCourse>) studentCourses;
        pageBean.setItems(page.getResult());
        pageBean.setTotal((int)((page.getTotal())));
        return pageBean;
    }

    @Override
    public void deleteByManager(String courseNo, Integer studentId) {
        //如果该课程已被评分，则不可以删除
        StudentCourse studentCourseBySIdAndCId = studentCourseMapper.studentCourseBySIdAndCId(courseNo, studentId);
        if(studentCourseBySIdAndCId.getScore()!=null){
            throw new RuntimeException("该课程已被评分，不可取消");
        }
        studentCourseMapper.deleteByCourseNoAndStudentId(courseNo,studentId);
    }

    @Override
    public void updateScoreAndComment(StudentCourse studentCourse) {
        //查找有没有这一条记录
        StudentCourse studentCourseBySIdAndCId = studentCourseMapper.studentCourseBySIdAndCId(studentCourse.getCourseNo(), studentCourse.getStudentId());
        if(studentCourseBySIdAndCId==null){
            throw new RuntimeException("没有该选课信息");
        }
        //已打分的成绩不能被修改
        if(studentCourseBySIdAndCId.getScore()!=null){
            throw new RuntimeException("成绩已评价，不可被修改！");
        }
        if(studentCourse.getComment()==null){
            studentCourse.setComment(" ");
        }
        studentCourseMapper.updateScoreAndCommentByStudentIdAndCourseNo(studentCourse);
    }

    @Override
    public void feedback(StudentCourse studentCourse) {
        StudentCourse studentCourseBySIdAndCId = studentCourseMapper.studentCourseBySIdAndCId(studentCourse.getCourseNo(), studentCourse.getStudentId());
        if(studentCourseBySIdAndCId.getFeedback()!=null){
            throw new RuntimeException("已反馈，不能再反馈");
        }
        if(studentCourseBySIdAndCId.getScore()==null){
            throw new RuntimeException("老师未评分，不能反馈");
        }
        studentCourseMapper.updateFeedBack(studentCourse);
    }


}
