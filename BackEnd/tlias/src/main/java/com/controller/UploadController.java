package com.controller;

import com.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {

    @PostMapping("/upload")
    public Result upload(String username, Integer age, MultipartFile image) throws IOException {
        log.info("Upload file with username: " + username + ", age: " + age + ", image: " + image.getOriginalFilename());
        String originalFilename = image.getOriginalFilename();
        // uuid + 文件后缀名 来防止文件名重复
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String newFileName = UUID.randomUUID() + suffix;
        // 存储到本地
        image.transferTo(new File("/Users/songtengxiao/Desktop/Playground/JavaWeb/day11-SpringBootWeb案例/代码/" + newFileName));
        return Result.success();
    }
}
