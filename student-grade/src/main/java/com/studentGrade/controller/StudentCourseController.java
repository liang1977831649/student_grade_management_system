package com.studentGrade.controller;

import com.studentGrade.entity.PageBean;
import com.studentGrade.entity.Result;
import com.studentGrade.entity.StudentCourse;
import com.studentGrade.server.StudentCourseServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/studentCourse")
public class StudentCourseController {

    @Autowired
    private StudentCourseServer studentCourseServer;

    @PostMapping("/add/{no}")
    public Result addStudentCourse(@PathVariable String no){
        if(no==null){
            return Result.error("课程编号不存在");
        }
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourseNo(no);
        studentCourseServer.add(studentCourse);
        return Result.success();
    }

    @GetMapping("/listPersonal")
    public Result listChooseCoursePersonal(Integer pageNum,Integer pageSize,String courseNo){
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=3;
        }
        PageBean<StudentCourse> studentCoursePageBean = studentCourseServer.listPersonal(pageNum, pageSize, courseNo);
        return Result.success(studentCoursePageBean);
    }
    @DeleteMapping("/{courseNo}")
    public Result deleteStudentCourse(@PathVariable String courseNo){
        studentCourseServer.del(courseNo);
        return Result.success();
    }
    @GetMapping("/list")
    public Result listAllChooseCourse(Integer pageNum,Integer pageSize,String courseNo,String studentName){
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=3;
        }
        PageBean<StudentCourse> studentCoursePageBean = studentCourseServer.listAll(pageNum, pageSize, courseNo, studentName);
        return Result.success(studentCoursePageBean);
    }
    @DeleteMapping("/{courseNo}/{studentId}")
    public Result delCourseChooseServerByManager(@PathVariable String courseNo,@PathVariable Integer studentId){
        if(courseNo==null||"".equals( courseNo)){
            return Result.error("无课程编号");
        }
        if(studentId==0){
            return Result.error("无学生学号");
        }
        studentCourseServer.deleteByManager( courseNo, studentId);
        return Result.success();
    }
    @PutMapping("/access")
    public Result updateScore(@RequestBody StudentCourse studentCourse){
        if(studentCourse==null){
            return Result.error("没有改条信息");
        }
        studentCourseServer.updateScoreAndComment(studentCourse);
        return Result.success();
    }

    @PutMapping("/feedback")
    public Result update(@RequestBody StudentCourse studentCourse){
        studentCourseServer.feedback(studentCourse);
        return Result.success();
    }
}
