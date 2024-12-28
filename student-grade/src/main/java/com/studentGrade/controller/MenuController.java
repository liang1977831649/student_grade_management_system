package com.studentGrade.controller;

import com.studentGrade.entity.Menu;
import com.studentGrade.entity.Result;
import com.studentGrade.server.MenuServer;
import com.studentGrade.utils.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class MenuController {
    @Autowired
    private MenuServer menuServer;
    @RequestMapping("/menuList")
    public Result getMenuList(){
        //List<Menu> menuList = menuServer.getMenuList();
        //return Result.success(menuList);
        HashMap<String,Object> hashmap = ThreadLocalUtils.get();
        Integer role =(Integer) hashmap.get("role");
        List<Menu> menuListByRole = menuServer.getMenuListByRole(role);
        return Result.success(menuListByRole);
    }
}
