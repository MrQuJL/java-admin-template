package com.ecommerce.admin.module.system.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户数据导出模型
 * 用于Easy Excel导出用户数据
 */
@Data
public class SysUserExcel {
    
    @ExcelProperty(value = "用户ID", index = 0)
    private Long id;
    
    @ExcelProperty(value = "用户名", index = 1)
    private String username;
    
    @ExcelProperty(value = "真实姓名", index = 2)
    private String realName;
    
    @ExcelProperty(value = "邮箱", index = 3)
    private String email;
    
    @ExcelProperty(value = "手机号", index = 4)
    private String phone;
    
    @ExcelProperty(value = "状态", index = 5)
    private Integer isActive;
    
    @ExcelProperty(value = "创建时间", index = 6)
    private LocalDateTime createdAt;
    
    /**
     * 设置Excel导出时的状态显示
     * @return 格式化后的状态文本
     */
    public String getIsActive() {
        return this.isActive == 1 ? "启用" : "禁用";
    }
    
    /**
     * 用于ModelMapper设置原始值
     * @param isActive 状态值
     */
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}