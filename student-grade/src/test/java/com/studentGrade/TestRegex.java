package com.studentGrade;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class TestRegex {
    @Test
    public void testNumber(){
        String str="89";
        //(\.\d{1,2})?
        String regex="\\d{1,2}(\\.\\d?)?";
        if(Pattern.matches(regex,str)){
            System.out.println("成功");
        }
    }
}
