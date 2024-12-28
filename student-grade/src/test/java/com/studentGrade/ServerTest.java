package com.studentGrade;

import com.studentGrade.entity.*;
import com.studentGrade.server.*;
import com.studentGrade.utils.Md5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ServerTest {
    @Autowired
    private MenuServer menuServer;

    @Autowired
    CourseServer courseServer;

    @Autowired
    TeacherServer teacherServer;
    @Autowired
    StudentServer studentServer;
    @Autowired
    StudentCourseServer studentCourseServer;
    @Test
    public void GetMenuListTest(){
        List<Menu> menuList = menuServer.getMenuList();
        for (Menu menu : menuList) {
            System.out.println(menu);
        }
    }
    @Test
    public void queryCourseTest(){
        PageBean<Course> coursePageBean = courseServer.searchServer("体", 1,3,null,"陈");
        System.out.println(coursePageBean);
    }
    @Test
    public void addTeacher(){
        teacherServer.addServer(new Teacher(null,"cx2201","张小龙","1231234567"));
    }
    @Test
    public void updateTeacher(){
        teacherServer.editServer(new Teacher(4,"114","张小小","12434315623"));
    }

    @Test
    public void deleteTeacher(){
        teacherServer.deleteServer(4);
    }

    @Test
    public void addCourseTest(){
        //courseServer.addCourse(new Course(null,"Java程序设计","cs2011",24,1,"面对对象的",null));
        System.out.println("添加成功");
    }
    @Test
    public void updateCourseTest(){
        //courseServer.editCourse(new Course(7,"Java程序设计@@","cs2011@",11,2,"面对对象的~~","啊哈啊哈"));
    }
    @Test
    public void deleteCourseTest(){
        courseServer.deleteCourse(7);
    }

    @Test
    public void getMenuByRole( ){
        List<Menu> menuListByRole = menuServer.getMenuListByRole(1);
        System.out.println(menuListByRole);
    }
    @Test
    public void addStudent(){
        LocalDateTime now = LocalDateTime.now();
        Student student = new Student();
        student.setId(20211132);
        student.setPassword(Md5Util.getMD5String("123456"));
        student.setName("张三");
        student.setBirthday(now);
        student.setRole(1);
        //studentServer.addStudentManage(student)
        System.out.println(student);
    }

    @Test
    public void updateStudent(){
        Student student = new Student();
        student.setId(2021113789);
        student.setSex(0);
        student.setPassword("123456");
        student.setName("梁政@");
        studentServer.updateStudentById(student);
    }
    public void addStudent2(){
        Student student = new Student();
        student.setId(20211132);
        student.setName("张皇");
        student.setPassword("123456");
        student.setSex(0);
    }
    @Test
    public void getAllStudentCourse(){
        PageBean<StudentCourse> studentCoursePageBean = studentCourseServer.listAll(1, 3, "6055", "张扬");
        System.out.println("总数："+studentCoursePageBean.getTotal());
        for (StudentCourse item : studentCoursePageBean.getItems()) {
            System.out.println(item);
        }
    }

    @Test
    public void menuTest(){
        List<Menu> menuListByRole = menuServer.getMenuListByRole(1);
    }
}
