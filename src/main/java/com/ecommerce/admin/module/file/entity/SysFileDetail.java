package com.ecommerce.admin.module.file.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 文件详情实体类
 * 用于存储文件上传后的元数据信息
 */
@Data
@TableName("sys_file_detail")
public class SysFileDetail {

    /**
     * 文件ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 文件访问地址
     */
    private String url;

    /**
     * 文件大小，单位字节
     */
    private Long size;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 原始文件名
     */
    private String originalFilename;

    /**
     * 基础存储路径
     */
    private String basePath;

    /**
     * 存储路径
     */
    private String path;

    /**
     * 文件扩展名
     */
    private String ext;

    /**
     * MIME类型
     */
    private String contentType;

    /**
     * 存储平台
     */
    private String platform;

    /**
     * 缩略图访问路径
     */
    private String thUrl;

    /**
     * 缩略图名称
     */
    private String thFilename;

    /**
     * 缩略图大小，单位字节
     */
    private Long thSize;

    /**
     * 缩略图MIME类型
     */
    private String thContentType;

    /**
     * 文件所属对象id
     */
    private String objectId;

    /**
     * 文件所属对象类型
     */
    private String objectType;

    /**
     * 额外属性
     */
    private String attr;

    /**
     * 文件ACL
     */
    private String fileAcl;

    /**
     * 缩略图ACL
     */
    private String thFileAcl;

    /**
     * 创建时间
     */
    private Date createTime;
}
