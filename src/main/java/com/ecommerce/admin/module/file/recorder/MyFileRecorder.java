package com.ecommerce.admin.module.file.recorder;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ecommerce.admin.module.file.entity.SysFileDetail;
import com.ecommerce.admin.module.file.mapper.SysFileDetailMapper;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.dromara.x.file.storage.core.upload.FilePartInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义文件记录器实现
 * 将文件信息保存到数据库，实现文件溯源和管理
 */
@Component
public class MyFileRecorder implements FileRecorder {

    @Autowired
    private SysFileDetailMapper sysFileDetailMapper;

    /**
     * 保存文件信息到数据库
     */
    @Override
    public boolean save(FileInfo fileInfo) {
        SysFileDetail detail = BeanUtil.copyProperties(fileInfo, SysFileDetail.class);
        if (fileInfo.getAttr() != null) {
            detail.setAttr(fileInfo.getAttr().toString());
        }
        return sysFileDetailMapper.insert(detail) > 0;
    }

    /**
     * 更新文件信息
     */
    @Override
    public void update(FileInfo fileInfo) {
        SysFileDetail detail = BeanUtil.copyProperties(fileInfo, SysFileDetail.class);
        if (fileInfo.getAttr() != null) {
            detail.setAttr(fileInfo.getAttr().toString());
        }
        sysFileDetailMapper.updateById(detail);
    }

    /**
     * 根据 URL 获取文件信息
     */
    @Override
    public FileInfo getByUrl(String url) {
        SysFileDetail detail = sysFileDetailMapper.selectOne(
                new LambdaQueryWrapper<SysFileDetail>().eq(SysFileDetail::getUrl, url)
        );
        return detail == null ? null : BeanUtil.copyProperties(detail, FileInfo.class);
    }

    /**
     * 根据 URL 删除文件记录
     */
    @Override
    public boolean delete(String url) {
        return sysFileDetailMapper.delete(
                new LambdaQueryWrapper<SysFileDetail>().eq(SysFileDetail::getUrl, url)
        ) > 0;
    }

    /**
     * 保存文件分片信息 (用于断点续传)
     */
    @Override
    public void saveFilePart(FilePartInfo filePartInfo) {
        // 如果不使用断点续传功能，这里可以暂时留空
    }

    /**
     * 根据 uploadId 删除文件分片记录 (用于断点续传)
     */
    @Override
    public void deleteFilePartByUploadId(String uploadId) {
        // 如果不使用断点续传功能，这里可以暂时留空
    }
}
