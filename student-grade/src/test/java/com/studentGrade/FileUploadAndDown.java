package com.studentGrade;


import com.studentGrade.utils.DateCreateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class FileUploadAndDown {
    @Test
    public void test() throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:static/img").getPath();
        System.out.println(path);
        String realPath=path+"/"+DateCreateUtil.dateFileName();
        System.out.println(realPath);
        File file = new File(realPath);
    }
}
