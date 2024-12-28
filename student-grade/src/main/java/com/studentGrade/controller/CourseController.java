package com.studentGrade.controller;

import com.studentGrade.entity.Course;
import com.studentGrade.entity.PageBean;
import com.studentGrade.entity.Result;
import com.studentGrade.server.CourseServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseServer courseServer;
    @GetMapping("/list")
    public Result search(String searchName,Integer pageNum,Integer pageSize,String courseNo,String teacherName){
        System.out.println("searchName="+searchName);
        System.out.println("pageNum="+pageNum);
        System.out.println("pageSize="+pageSize);
        if(pageNum==null){
            pageNum=1;
        }if(pageSize==null){
            pageSize=3;
        }
        PageBean<Course> coursePageBean = courseServer.searchServer(searchName, pageNum, pageSize,courseNo,teacherName);
        return Result.success(coursePageBean);
    }

    @GetMapping("/listForStudent")
    public Result searchForStudent(String searchName,Integer pageNum,Integer pageSize,String courseNo,String teacherName){
        System.out.println("searchName="+searchName);
        System.out.println("pageNum="+pageNum);
        System.out.println("pageSize="+pageSize);
        if(pageNum==null){
            pageNum=1;
        }if(pageSize==null){
            pageSize=3;
        }
        PageBean<Course> coursePageBean = courseServer.searchServerForStudent(searchName, pageNum, pageSize,courseNo,teacherName);
        return Result.success(coursePageBean);
    }

    @PostMapping
    public Result add(@RequestBody Course course){
        courseServer.addCourse(course);
        return Result.success();
    }
    @PutMapping
    public Result update(@RequestBody Course course){
        if(course.getId()==null){
            throw new RuntimeException("改课程没有ID");
        }
        courseServer.editCourse(course);
        return Result.success();
    }
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
         courseServer.deleteCourse(id);
         return Result.success();
    }
}
