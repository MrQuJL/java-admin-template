package com.ecommerce.admin.module.system.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.ecommerce.admin.module.system.enums.business.UserStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户数据导出模型
 * 用于Easy Excel导出用户数据
 */
@Data
@HeadStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
public class SysUserExcel {
    
    @ColumnWidth(10)
    @ExcelProperty(value = "用户ID", index = 0)
    private Long id;
    
    @ColumnWidth(20)
    @ExcelProperty(value = "用户名", index = 1)
    private String username;
    
    @ColumnWidth(15)
    @ExcelProperty(value = "真实姓名", index = 2)
    private String realName;
    
    @ColumnWidth(35)
    @ExcelProperty(value = "邮箱", index = 3)
    private String email;
    
    @ColumnWidth(15)
    @ExcelProperty(value = "手机号", index = 4)
    private String phone;
    
    @ExcelIgnore
    private Integer isActive;

    @ColumnWidth(10)
    @ExcelProperty(value = "状态", index = 5)
    private String statusName;
    
    @ColumnWidth(20)
    @ExcelProperty(value = "创建时间", index = 6)
    private LocalDateTime createdAt;
    
    /**
     * 用于ModelMapper设置原始值，同时设置导出显示值
     * @param isActive 状态值
     */
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
        UserStatusEnum status = UserStatusEnum.getByCode(isActive);
        this.statusName = status != null ? status.getDesc() : UserStatusEnum.DISABLED.getDesc();
    }
}