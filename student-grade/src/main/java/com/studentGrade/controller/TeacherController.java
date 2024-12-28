package com.studentGrade.controller;

import com.studentGrade.entity.PageBean;
import com.studentGrade.entity.Result;
import com.studentGrade.entity.Teacher;
import com.studentGrade.server.TeacherServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherServer teacherServer;

    @RequestMapping("/list")
    public Result list(String name,String no ,Integer pageNum,Integer pageSize){
        System.out.println("name="+name);
        System.out.println("no="+no);
        System.out.println("pageNum="+pageNum);
        System.out.println("pageSize="+pageSize);
        if(pageNum==null ||pageNum<=0){
            pageNum=1;
        }if(pageSize==null||pageSize<=0){
            pageSize=3;
        }
        PageBean<Teacher> teacherPageBean = teacherServer.listTeacher(name, no, pageNum, pageSize);
        return Result.success(teacherPageBean);
    }
    @PostMapping
    public Result add(@RequestBody Teacher teacher){
        teacherServer.addServer(teacher);
        return Result.success();
    }
    @PutMapping
    public Result edit(@RequestBody Teacher teacher){
        teacherServer.editServer(teacher);
        return Result.success();
    }
    @DeleteMapping
    public Result del(@RequestParam Integer id){
        teacherServer.deleteServer(id);
        return Result.success();
    }

}
