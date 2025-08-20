package com.zcf.controller;


import com.zcf.annotation.OperateLog;
import com.zcf.pojo.Result;
import com.zcf.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;//阿里云对象存储

    /**
     * 文件上传到本地服务器
     * @param file
     * @return
     */
//    @PostMapping
//    public Result handleFileUpload(String name, Integer age, MultipartFile  file) throws IOException {
//        log.info("上传文件开始，文件名：{}，文件大小：{}",file.getOriginalFilename(),file.getSize());
//
//        // 获取文件名
//        String originalFilename = file.getOriginalFilename();
//
//        // 获取文件后缀
//        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//        String newFileName = UUID.randomUUID().toString() + extension;
//
//        file.transferTo(new File("D:/develop/" + newFileName));
//
//        return Result.success();
//    }

    /**
     * 文件上传到阿里云
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping
    @OperateLog(value = "文件上传", operateType = "增")
    public Result upload(MultipartFile  file) throws Exception {
        log.info("上传文件开始，文件名：{}，文件大小：{}",file.getOriginalFilename(),file.getSize());
        String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        log.info("上传文件成功，文件地址：{}",url);
        return Result.success(url);
    }


}
