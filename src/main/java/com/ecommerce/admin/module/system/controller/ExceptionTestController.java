package com.ecommerce.admin.module.system.controller;

import com.ecommerce.admin.common.exception.BusinessException;
import com.ecommerce.admin.common.enums.response.ResponseEnum;
import com.ecommerce.admin.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 异常测试控制器
 * 用于测试 GlobalExceptionHandler 中的各个异常处理器
 */
@RestController
@RequestMapping("/api/exception-test")
@Api(tags = "异常测试接口")
@Validated  // 用于验证 @RequestParam 参数
@Slf4j
public class ExceptionTestController {

    /**
     * 测试业务异常
     * 触发 BusinessException 处理器
     */
    @GetMapping("/business-exception")
    @ApiOperation("测试业务异常")
    public Result<?> testBusinessException() {
        // 主动抛出业务异常，使用已有的 BUSINESS_ERROR 枚举值
        throw new BusinessException(ResponseEnum.BUSINESS_ERROR);
    }

    /**
     * 测试请求体参数校验异常
     * 触发 MethodArgumentNotValidException 处理器
     */
    @PostMapping("/body-validation")
    @ApiOperation("测试请求体参数校验异常")
    public Result<?> testBodyValidation(@Valid @RequestBody TestDto testDto) {
        return Result.success("参数校验通过: " + testDto);
    }

    /**
     * 测试请求参数缺失异常
     * 触发 MissingServletRequestParameterException 处理器
     */
    @GetMapping("/missing-param")
    @ApiOperation("测试请求参数缺失异常")
    public Result<?> testMissingParam(@RequestParam(required = true) String requiredParam) {
        return Result.success("获取到参数: " + requiredParam);
    }

    /**
     * 测试请求参数约束违反异常
     * 触发 ConstraintViolationException 处理器
     */
    @GetMapping("/param-validation")
    @ApiOperation("测试请求参数约束违反异常")
    public Result<?> testParamValidation(
            @RequestParam @Min(value = 1, message = "ID必须大于0") Integer id,
            @RequestParam @NotBlank(message = "名称不能为空") String name) {
        return Result.success("参数校验通过: id=" + id + ", name=" + name);
    }

    /**
     * 测试模型属性绑定异常
     * 触发 BindException 处理器
     */
    @PostMapping("/bind-exception")
    @ApiOperation("测试模型属性绑定异常")
    public Result<?> testBindException(@Valid TestDto testDto) {
        return Result.success("模型绑定通过: " + testDto);
    }

    /**
     * 测试运行时异常
     * 触发 RuntimeException 处理器
     */
    @GetMapping("/runtime-exception")
    @ApiOperation("测试运行时异常")
    public Result<?> testRuntimeException() {
        // 除0异常
        int zeroDivisor = 0;
        int result = 10 / zeroDivisor;
        return Result.success("结果: " + result);
    }

    /**
     * 测试系统异常
     * 触发 Exception 处理器（兜底）
     */
    @GetMapping("/system-exception")
    @ApiOperation("测试系统异常")
    public Result<?> testSystemException() throws Exception {
        // 主动抛出 Exception
        throw new Exception("测试系统异常");
    }

    /**
     * 测试 DTO 类
     * 用于参数校验测试
     */
    public static class TestDto {
        @NotBlank(message = "名称不能为空")
        private String name;

        @Min(value = 18, message = "年龄必须大于等于18")
        private Integer age;

        // getter 和 setter
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "TestDto{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}