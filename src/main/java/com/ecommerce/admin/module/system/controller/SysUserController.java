package com.ecommerce.admin.module.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ecommerce.admin.common.result.Result;
import com.ecommerce.admin.common.util.EasyUtils;
import com.ecommerce.admin.module.system.dto.SysUserDTO;
import com.ecommerce.admin.module.system.service.SysUserService;
import com.ecommerce.admin.module.system.vo.SysUserExcel;
import com.ecommerce.admin.module.system.vo.SysUserVO;
import com.ecommerce.admin.common.validation.AddGroup;
import com.ecommerce.admin.common.validation.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;


/**
 * 系统用户Controller
 */
@Api(tags = "系统用户管理")
@RestController
@RequestMapping("/system/user")
@Validated
@Slf4j
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 分页查询用户列表
     * @param page 当前页码
     * @param size 每页大小
     * @return Result<IPage<SysUserVO>>
     */
    @ApiOperation("分页查询用户列表")
    @GetMapping("/list")
    public Result<IPage<SysUserVO>> list(@ApiParam(value = "当前页码", example = "1")
                                         @RequestParam(defaultValue = "1") Integer page,
                                         @ApiParam(value = "每页大小", example = "10")
                                         @RequestParam(defaultValue = "10") Integer size,
                                         @ApiParam(value = "用户名（模糊查询）", example = "admin")
                                         @RequestParam(required = false) String username) {
        // 调用service层查询
        IPage<SysUserVO> voPage = sysUserService.getUserPage(page, size, username);
        
        return Result.success(voPage);
    }

    /**
     * 根据ID查询用户详情
     * @param id 用户ID
     * @return Result<SysUserVO>
     */
    @ApiOperation("根据ID查询用户详情")
    @GetMapping("/getById")
    public Result<SysUserVO> getById(@ApiParam(value = "用户ID", required = true, example = "1")
                                     @RequestParam
                                     @NotNull(message = "用户ID不能为空")
                                     @Min(value = 1, message = "用户ID必须为正数") Long id) {
        // 调用service层查询
        SysUserVO vo = sysUserService.getUserDetail(id);
        
        return Result.success(vo);
    }

    /**
     * 创建用户
     * @param userDTO 用户DTO
     * @return Result<SysUserVO>
     */
    @ApiOperation("创建用户")
    @PostMapping("/create")
    public Result<SysUserVO> create(@Validated(AddGroup.class) @RequestBody SysUserDTO userDTO) {
        // 调用service层创建用户
        SysUserVO vo = sysUserService.createUser(userDTO);
        
        return Result.success(vo);
    }

    /**
     * 更新用户
     * @param userDTO 用户DTO
     * @return Result<SysUserVO>
     */
    @ApiOperation("更新用户")
    @PostMapping("/update")
    public Result<SysUserVO> update(@Validated(UpdateGroup.class) @RequestBody SysUserDTO userDTO) {
        // 调用service层更新用户
        SysUserVO vo = sysUserService.updateUser(userDTO);
        
        return Result.success(vo);
    }

    /**
     * 删除用户
     * @param id 用户ID
     * @return Result<Void>
     */
    @ApiOperation("删除用户")
    @PostMapping("/delete")
    public Result<Void> delete(@RequestParam
                               @NotNull(message = "用户ID不能为空")
                               @Min(value = 1, message = "用户ID必须为正数") Long id) {
        // 调用service层删除用户
        sysUserService.deleteUser(id);
        
        return Result.success();
    }
    
    /**
     * 导出用户数据
     * @param response HttpServletResponse
     * @throws IOException IO异常
     */
    @ApiOperation("导出用户数据")
    @GetMapping("/export")
    public void exportExcel(HttpServletResponse response) throws IOException {
        // 获取导出数据
        List<SysUserExcel> userExcels = sysUserService.getAllUsersForExport();
        
        // 导出Excel
        EasyUtils.write(userExcels, SysUserExcel.class, "用户数据", "用户数据", response);
    }

    /**
     * 解析Excel数据
     * @param excelFile Excel文件
     * @return Result<List<SysUserVO>>
     * @throws IOException IO异常
     */
    @ApiOperation("解析Excel数据")
    @PostMapping("/parseData")
    public Result<List<SysUserVO>> parseData(@ApiParam(value = "Excel文件", required = true)
                                             @RequestParam("excel") MultipartFile excelFile) throws IOException {
        // 调用Service解析数据
        List<SysUserVO> list = sysUserService.parseUserExcel(excelFile);
        return Result.success(list);
    }
}