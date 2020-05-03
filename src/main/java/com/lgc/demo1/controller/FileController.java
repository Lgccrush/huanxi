package com.lgc.demo1.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.lgc.demo1.dto.FileDTO;
import com.lgc.demo1.exception.CustomizeErrorCode;
import com.lgc.demo1.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

/**
 * Created by codedrinker on 2019/6/26.
 */
@Controller
public class FileController {
    @Value("${AccessKeyID}")
    private String accessKeyId;
    @Value("${AccessKeySecret}")
    private String accessKeySecret;
    @Value("${EndPoint}")
    private String endpoint;
    @Value("${Bucket}")
    private String bucketName;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String fileName = file.getOriginalFilename();
//            String filePath = "b.jpg";
        try {
            ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(file.getBytes()));
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
            // 生成URL
            URL url = ossClient.generatePresignedUrl(bucketName, fileName, expiration);
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(url.toString());
            return fileDTO;
        } catch (IOException e) {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
    }
}
