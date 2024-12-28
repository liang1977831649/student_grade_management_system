package com.studentGrade;

import com.studentGrade.entity.Admin;
import com.studentGrade.entity.Course;
import com.studentGrade.entity.Menu;
import com.studentGrade.entity.StudentCourse;
import com.studentGrade.mapper.*;
import com.studentGrade.utils.Md5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SpringBootTest
public class MapperTest {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @Test
    public void queryStudentById() {
        Admin student = new Admin();
        student.setId(123456);
        //Admin student1 = adminMapper.queryStudentById(student);
        //System.out.println(student1);
    }

    @Test
    public void updatePassword() {
        Admin student = new Admin();
        student.setId(123456);
        String pwd = Md5Util.getMD5String("123456");
        student.setPassword(pwd);
        //adminMapper.updateStudentPassword(student);
    }

    @Test
    public void queryMenuByType() {
        List<Menu> menuList = menuMapper.getMenuList();
        recursionMenu(menuList,0);
        cleanHasFatherMenu(menuList);
    }

    @Test
    public void getMenu(){

    }

    public void recursionMenu(List<Menu> menuList, int index) {
        if (index+1 > menuList.size()) {
            return;
        }
        Menu menu = menuList.get(index);
        if (menu.getFather() != 0) {
            Menu menuFather = menuList.get(getFatherIndex(menu.getFather(),menuList));
            List<Menu> menuListSub = menuFather.getMenuList();
            if(menuListSub==null){
                menuListSub=new ArrayList<>();
            }
            menuListSub.add(menu);
            menuFather.setMenuList(menuListSub);
        }
        recursionMenu(menuList,++index);
    }
    public void cleanHasFatherMenu(List<Menu> menuList){
        Iterator<Menu> iterator = menuList.iterator();
        while (iterator.hasNext()){
            Menu next = iterator.next();
            if(next.getFather()!=0){
                iterator.remove();
            }
        }
    }
    public int getFatherIndex(int fatherId,List<Menu> list){
        for(int i=0;i<=list.size();i++){
            Menu menu = list.get(i);
            if(menu.getId()==fatherId){
                return i;
            }
        }
        return -1;
    }
    @Test
    public void TestCourseQuery(){
        List<Course> courses = courseMapper.listCourse("育",null,null);
        System.out.println(courses);
    }

    @Test
    public void getMenuByRoleTest(){
        List<Menu> menuByRole = menuMapper.getMenuByRole(1);
        System.out.println(menuByRole);
    }

    @Test
    public void updatePwd(){
        studentMapper.updatePassword("654321",20211136);
    }

    @Test
    public void addStudentCourse(){
        StudentCourse studentCourse = new StudentCourse();
        //studentCourse.setCourseId(1);
        studentCourse.setStudentId(20211136);
        studentCourseMapper.insert(studentCourse);
    }
    @Test
    public void queryStudentCourseList(){

    }
}
