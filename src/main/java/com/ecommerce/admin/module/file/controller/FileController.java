package com.ecommerce.admin.module.file.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件存储控制器
 */
@Api(tags = "文件存储")
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 上传文件
     */
    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public FileInfo upload(MultipartFile file) {
        // 直接上传并保存到默认存储平台
        return fileStorageService.of(file).upload();
    }

    /**
     * 上传图片并生成缩略图
     */
    @ApiOperation("上传图片并生成缩略图")
    @PostMapping("/upload/image")
    public FileInfo uploadImage(MultipartFile file) {
        return fileStorageService.of(file)
                .image(img -> img.size(1000, 1000)) // 将图片大小限制在 1000x1000 以内
                .thumbnail(th -> th.size(200, 200)) // 生成 200x200 的缩略图
                .upload();
    }
}
