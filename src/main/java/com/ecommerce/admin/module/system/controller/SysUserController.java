package com.ecommerce.admin.module.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecommerce.admin.common.result.Result;
import com.ecommerce.admin.common.util.EasyUtils;
import com.ecommerce.admin.module.system.dto.SysUserDTO;
import com.ecommerce.admin.module.system.entity.SysUser;
import com.ecommerce.admin.module.system.service.SysUserService;
import com.ecommerce.admin.module.system.vo.SysUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


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

    @Autowired
    private ModelMapper modelMapper;

    /**
     * 分页查询用户列表
     * @param page 当前页码
     * @param size 每页大小
     * @return Result<IPage<SysUserVO>>
     */
    @ApiOperation("分页查询用户列表")
    @GetMapping("/list")
    public Result<IPage<SysUserVO>> list(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size) {
        // 创建分页对象
        Page<SysUser> pageParam = new Page<>(page, size);
        
        // 查询用户列表
        IPage<SysUser> userPage = sysUserService.page(pageParam);
        
        // 转换为VO对象
        IPage<SysUserVO> voPage = userPage.convert(user -> modelMapper.map(user, SysUserVO.class));
        
        return Result.success(voPage);
    }

    /**
     * 根据ID查询用户详情
     * @param id 用户ID
     * @return Result<SysUserVO>
     */
    @ApiOperation("根据ID查询用户详情")
    @GetMapping("/getById")
    public Result<SysUserVO> getById(@RequestParam
                                     @NotNull(message = "用户ID不能为空")
                                     @Min(value = 1, message = "用户ID必须为正数") Long id) {
        // 查询用户
        SysUser user = sysUserService.getById(id);
        
        if (user == null) {
            return Result.fail(404, "用户不存在");
        }
        
        // 转换为VO对象
        SysUserVO vo = modelMapper.map(user, SysUserVO.class);
        
        return Result.success(vo);
    }

    /**
     * 创建用户
     * @param userDTO 用户DTO
     * @return Result<SysUserVO>
     */
    @ApiOperation("创建用户")
    @PostMapping("/create")
    public Result<SysUserVO> create(@Validated @RequestBody SysUserDTO userDTO) {
        // 调用service层创建用户
        SysUser user = sysUserService.createUser(userDTO);
        
        // 转换为VO对象
        SysUserVO vo = modelMapper.map(user, SysUserVO.class);
        
        return Result.success(vo);
    }

    /**
     * 更新用户
     * @param id 用户ID
     * @param userDTO 用户DTO
     * @return Result<SysUserVO>
     */
    @ApiOperation("更新用户")
    @PostMapping("/update")
    public Result<SysUserVO> update(@RequestParam
                                    @NotNull(message = "用户ID不能为空")
                                    @Min(value = 1, message = "用户ID必须为正数") Long id,
                                    @Validated
                                    @RequestBody SysUserDTO userDTO) {
        // 调用service层更新用户
        SysUser user = sysUserService.updateUser(id, userDTO);
        
        // 转换为VO对象
        SysUserVO vo = modelMapper.map(user, SysUserVO.class);
        
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
        // 检查用户是否存在
        if (sysUserService.getById(id) == null) {
            return Result.fail(404, "用户不存在");
        }
        
        // 删除用户（逻辑删除）
        sysUserService.removeById(id);
        
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
        // 查询用户列表
        List<SysUser> userList = sysUserService.listAll();
        
        // 转换为VO对象
        List<SysUserVO> voList = userList.stream().map(user -> modelMapper.map(user, SysUserVO.class)).collect(Collectors.toList());
        
        // 导出Excel
        EasyUtils.write(voList, SysUserVO.class, "用户数据", "用户数据", response);
    }

    @PostMapping("/parseData")
    public Result<List<SysUserVO>> parseData(@RequestParam("excel") MultipartFile excelFile) throws IOException {
        List<SysUserVO> list = EasyUtils.read(excelFile.getInputStream(), SysUserVO.class);
        log.info("解析到 {} 条用户数据", list.size());
        log.info("用户数据: {}", list);
        return Result.success(list);
    }
}