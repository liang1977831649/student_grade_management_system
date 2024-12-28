package com.studentGrade.controller;

import com.studentGrade.entity.PageBean;
import com.studentGrade.entity.Result;
import com.studentGrade.entity.Student;
import com.studentGrade.server.StudentServer;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentServer studentServer;

    @GetMapping("/list")
    public Result StudentList(@RequestParam(required = false) String name,
                              @RequestParam(required = false) Integer id,
                              @RequestParam(required = false) Integer pageNum,
                              @RequestParam(required = false) Integer pageSize){
        if(pageNum==null){
            pageNum=1;
        }
        if(pageSize==null){
            pageSize=3;
        }
        PageBean<Student> studentPageBean = studentServer.queryStudent(id, name, pageNum, pageSize);
        return Result.success(studentPageBean);
    }

    @PostMapping
    public Result add(@RequestBody @Validated Student student){
        studentServer.addStudentManage(student);
        return Result.success();
    }
    @PutMapping
    public Result update(@RequestBody @Validated Student student){
        System.out.println("student"+student);
        studentServer.updateStudentById(student);
        return Result.success();
    }
    @DeleteMapping
    public Result delete(@RequestParam Integer id){
        assert id!=null;
        studentServer.deleteStudentById(id);
        return Result.success();
    }
    @PostMapping("/password")
    public Result updatePassword(String oldPwd,String newPwd,String rePwd){
        //获取到当前用户的id
        if(StringUtil.isNullOrEmpty(oldPwd)||StringUtil.isNullOrEmpty(newPwd)||StringUtil.isNullOrEmpty(rePwd)){
            return Result.error("没有输入完整密码");
        }
        if(!newPwd.equals(rePwd)){
            return Result.error("新密码和旧密码不一致");
        }
        boolean b = studentServer.updatePassword(oldPwd, newPwd);
        if(!b){
            return Result.error("修改密码失败");
        }
        return Result.success("成功！");
    }
}
