package org.example.itheima.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.itheima.reggie.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传和下载
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.path}")
    private String BasePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        log.info(file.toString());

        String originalFilename = file.getOriginalFilename();
        String suffix =originalFilename.substring(originalFilename.lastIndexOf( "."));

        //使用UUID重新生成文件名,避免文件名重复造成覆盖
        String fileName = UUID.randomUUID().toString() + suffix;

        File dir = new File(BasePath);
        if(!dir.exists()){
            dir.mkdirs();
        }

        file.transferTo(new File(BasePath + fileName));
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(BasePath + name));
            //输出流，通过输出流将文件写回浏览器，在浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
            }

            outputStream.close();
            fileInputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
