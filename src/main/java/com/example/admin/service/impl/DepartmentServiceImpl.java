package com.example.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.admin.common.BusinessException;
import com.example.admin.entity.Department;
import com.example.admin.mapper.DepartmentMapper;
import com.example.admin.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Override
    public Department getDepartmentById(Long id) {
        if (id == null) {
            throw new BusinessException("部门ID不能为空");
        }
        return this.lambdaQuery()
                .eq(Department::getId, id)
                .one();
    }

    @Override
    public Page<Department> pageDepartment(Integer current, Integer size, Department department) {
        Page<Department> page = new Page<>(current, size);
        return this.lambdaQuery()
                .eq(department.getId() != null, Department::getId, department.getId())
                .like(StringUtils.isNotBlank(department.getName()), Department::getName, department.getName())
                .like(StringUtils.isNotBlank(department.getCode()), Department::getCode, department.getCode())
                .eq(department.getParentId() != null, Department::getParentId, department.getParentId())
                .eq(department.getStatus() != null, Department::getStatus, department.getStatus())
                .orderByAsc(Department::getSort)
                .orderByDesc(Department::getCreateTime)
                .page(page);
    }
}
