package com.example.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.common.Result;
import com.example.admin.entity.Department;
import com.example.admin.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/get/{id}")
    public Result<Department> get(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        return Result.success(department);
    }

    @GetMapping("/list")
    public Result<Page<Department>> page(@RequestParam(defaultValue = "1") Integer current,
                                           @RequestParam(defaultValue = "10") Integer size,
                                           Department department) {
        Page<Department> page = departmentService.pageDepartment(current, size, department);
        return Result.success(page);
    }

    @PostMapping("/add")
    public Result<String> add(@Valid @RequestBody Department department) {
        boolean success = departmentService.save(department);
        return success ? Result.success("新增成功") : Result.error("新增失败");
    }

    @PostMapping("/update")
    public Result<String> update(@Valid @RequestBody Department department) {
        if (department.getId() == null) {
            return Result.error("部门ID不能为空");
        }
        boolean success = departmentService.updateById(department);
        return success ? Result.success("修改成功") : Result.error("修改失败");
    }

    @PostMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        boolean success = departmentService.removeById(id);
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }
}
