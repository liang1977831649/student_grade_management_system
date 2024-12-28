package com.studentGrade.controller;

import com.studentGrade.entity.Result;
import com.studentGrade.utils.DateCreateUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${server.port}")
    private String port;
    private final String contextPath = "localhost";

    //文件上传
    @PostMapping("/upload")
    public Result upload(MultipartFile multipartFile) throws IOException {
        //获取文件原名称
        String name = multipartFile.getOriginalFilename();
        name = UUID.randomUUID().toString().substring(0, 5) + "_" + name;
        //获取静态资源路径
        String path = ResourceUtils.getURL("classpath:static/img").getPath()+"/"+ DateCreateUtil.dateFileName();

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        //上传
        multipartFile.transferTo(new File(file, name));
        String url = "http://" + contextPath + ":" + port + "/file/down?name="+ DateCreateUtil.dateFileName()+"/"+ name;
        return Result.success(url);
    }

    //文件下载
    @GetMapping("/down")
    public void down(@RequestParam String name, HttpServletResponse response) throws IOException {
        //System.out.println("name=" + name);
        String path = ResourceUtils.getURL("classpath:static/img").getPath();
        String fileName = path + "/" + name;
        File file = new File(fileName);
        System.out.println(file.getPath());
        if (!file.exists()) {
            return;
        }

        // 文件转成字节数组
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        //文件名编码，防止中文乱码
        String filename = URLEncoder.encode(file.getName(), "UTF-8");
        // 设置响应头信息
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        // 内容类型为通用类型，表示二进制数据流
        response.setContentType("application/octet-stream");
        //输出文件内容
        try (OutputStream os = response.getOutputStream()) {
            os.write(fileBytes);
        }
        /**
         *
         //文件名编码，防止中文乱码
         String filename = URLEncoder.encode(file.getName(), "UTF-8");

         // 设置响应头信息
         response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
         // 内容类型为通用类型，表示二进制数据流
         response.setContentType("application/octet-stream");

         try {
         //获取输入流
         FileInputStream fileInputStream = new FileInputStream(file);
         //获取输出流
         ServletOutputStream outputStream = response.getOutputStream();
         byte[] bytes = new byte[1024];
         int readLine;
         while ((readLine=fileInputStream.read(bytes))!=0){
         outputStream.write(bytes,0,readLine);
         }
         } catch (IOException e) {
         throw new RuntimeException(e);
         }
         */

    }

}
