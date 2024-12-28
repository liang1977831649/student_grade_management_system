package com.studentGrade.controller;

import com.studentGrade.entity.Account;
import com.studentGrade.entity.Result;
import com.studentGrade.server.AdminServer;
import com.studentGrade.server.StudentServer;
import com.studentGrade.utils.JwtUtil;
import com.studentGrade.utils.Md5Util;
import com.studentGrade.utils.ThreadLocalUtils;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
public class WebController {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private AdminServer adminServer;
    @Autowired
    private StudentServer studentServer;


    @PostMapping("/login")
    public Result login(Account account){
        if(account==null){
            return Result.error("数据异常");
        }
        if(StringUtil.isNullOrEmpty(account.getPassword())||account.getId()==null||account.getRole()==null){
            return Result.error("没有账号或密码");
        }

        Account accountById=null;
        if(account.getRole()==0){
            accountById = adminServer.queryAdminById(account);
        }else if(account.getRole()==1){
            accountById = studentServer.queryStudentById(account);
        }

        account.setPassword(Md5Util.getMD5String(account.getPassword()));
        if(accountById==null){
            return Result.error("账号或密码错误，请重试");
        }
        if(!account.getPassword().equals(accountById.getPassword())){
            return Result.error("密码错误");
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id",accountById.getId());
        hashMap.put("role",accountById.getRole());
        //对hashmap进行JWToken的设置
        String token = JwtUtil.getToken(hashMap);
        //缓存在redis中
        redisTemplate.opsForValue().set("token"+accountById.getId(),token,1, TimeUnit.HOURS);

        return Result.success(token);
    }
    @GetMapping("/info")
    public Result getInfo(){
        HashMap<String,Object> hashMap = ThreadLocalUtils.get();
        Integer role =(Integer) hashMap.get("role");
        Integer id = (Integer) hashMap.get("id");
        Account account=null;
        if(role==0){
            //想要获取管理员的身份信息
            account = adminServer.getAdminInfo(id);
        }else if(role==1){
            //想要获取学生的身份信息
           account = studentServer.getStudentInfo(id);
        }
        return Result.success(account);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Account account){
        if(account==null){
            return  Result.error("请输入完整用户信息");
        }
        if(account.getId()==null||StringUtil.isNullOrEmpty(account.getPassword())){
            return Result.error("账号或者密码为空");
        }
        studentServer.addStudent(account);
        return Result.success();
    }
}
