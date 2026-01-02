package com.company.project.controller;

import com.company.project.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@Api(tags = "示例接口")
public class DemoController {

    @GetMapping("/hello")
    @ApiOperation("示例接口")
    public Result<String> hello() {
        return Result.success("Hello World");
    }

}
