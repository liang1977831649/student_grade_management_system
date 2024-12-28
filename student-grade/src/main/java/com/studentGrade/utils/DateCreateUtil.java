package com.studentGrade.utils;

import java.time.LocalDate;

public class DateCreateUtil {
    public  static String dateFileName(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(LocalDate.now().getYear());
        stringBuffer.append("/");
        stringBuffer.append(LocalDate.now().getMonthValue());
        stringBuffer.append("/");
        stringBuffer.append(LocalDate.now().getDayOfMonth());
        return stringBuffer.toString();
    }
}
