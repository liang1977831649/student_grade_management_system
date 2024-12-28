package com.studentGrade;

import com.studentGrade.utils.JwtUtil;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class JWTTest {
    @Test
    public void jwt(){
        String str="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsicm9sZSI6MCwiaWQiOjEyMzQ1Nn0sImV4cCI6MTcyOTk1MjU2Mn0.xhR3koGyF5174jiiAkRNm1FbhbSaW6GF0O17-svEdvE";
        Map<String, Object> stringObjectMap = JwtUtil.parseToken(str);
        System.out.println(stringObjectMap);
    }
}
