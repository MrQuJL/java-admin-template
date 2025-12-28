package com.example.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.admin.entity.Department;

public interface DepartmentService extends IService<Department> {
    Department getDepartmentById(Long id);

    Page<Department> pageDepartment(Integer current, Integer size, Department department);
}
