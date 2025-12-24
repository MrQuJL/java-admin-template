package com.example.javaadmintemplate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.javaadmintemplate.entity.User;

/**
 * 用户Mapper接口
 * 
 * @author example
 */
public interface UserMapper extends BaseMapper<User> {
    // 继承BaseMapper即可获得基本的CRUD操作
    // 如需自定义SQL，可以在此添加方法并在Mapper XML中实现
}
