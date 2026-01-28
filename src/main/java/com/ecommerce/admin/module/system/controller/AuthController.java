package com.ecommerce.admin.module.system.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.ecommerce.admin.common.result.Result;
import com.ecommerce.admin.module.system.entity.SysUser;
import com.ecommerce.admin.module.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api(tags = "认证鉴权管理")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 验证码 Redis 前缀
     */
    private static final String CAPTCHA_KEY_PREFIX = "captcha:";

    @ApiOperation("获取验证码")
    @GetMapping("/captcha")
    public Result<Map<String, String>> getCaptcha() {
        // 1. 生成验证码 (宽200, 高100, 字符数4, 干扰线数20)
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(120, 40, 4, 10);
        String code = lineCaptcha.getCode();
        String imageBase64 = lineCaptcha.getImageBase64Data();
        
        // 2. 生成唯一标识 uuid
        String uuid = UUID.fastUUID().toString();
        
        // 3. 存储到 Redis，设置有效期 2 分钟
        stringRedisTemplate.opsForValue().set(CAPTCHA_KEY_PREFIX + uuid, code, 2, TimeUnit.MINUTES);
        
        // 4. 返回结果
        Map<String, String> result = new HashMap<>();
        result.put("uuid", uuid);
        result.put("img", imageBase64);
        return Result.success(result);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // 0. 校验验证码
        String captchaKey = CAPTCHA_KEY_PREFIX + loginRequest.getUuid();
        String redisCaptcha = stringRedisTemplate.opsForValue().get(captchaKey);
        
        if (StrUtil.isBlank(redisCaptcha)) {
            return Result.fail("验证码已过期");
        }
        
        if (!redisCaptcha.equalsIgnoreCase(loginRequest.getCaptcha())) {
            return Result.fail("验证码错误");
        }
        
        // 校验通过后立即删除验证码，防止重用
        stringRedisTemplate.delete(captchaKey);

        // 1. 查询用户
        SysUser user = sysUserService.getByUsername(loginRequest.getUsername());
        
        // 2. 模拟验证 (兼容演示模式)
        // 如果数据库没查到，且是 admin/123456，则允许登录 (为了演示方便，实际生产环境请删除)
        if (user != null && "admin".equals(loginRequest.getUsername()) && "123456".equals(loginRequest.getPassword())) {
             StpUtil.login(1L); // 默认ID 1
             return Result.success(new LoginResponse(StpUtil.getTokenValue(), "admin"));
        }

        // 3. 校验用户和密码
        if (user == null) {
            return Result.fail("用户名或密码错误");
        }
        
        // 注意：这里假设数据库密码是 BCrypt 加密的。如果你的数据库是明文，请改为 .equals()
        // 为了兼容性，先尝试 BCrypt，如果抛异常（比如格式不对）或者不匹配，再尝试明文（可选）
        boolean passwordMatch = false;
        try {
            passwordMatch = BCrypt.checkpw(loginRequest.getPassword(), user.getPassword());
        } catch (Exception e) {
            // 可能是明文密码，尝试直接比对
            passwordMatch = loginRequest.getPassword().equals(user.getPassword());
        }
        
        if (!passwordMatch) {
            return Result.fail("用户名或密码错误");
        }
        
        // 4. Sa-Token 登录
        StpUtil.login(user.getId());

        // 5. 返回结果
        return Result.success(new LoginResponse(StpUtil.getTokenValue(), user.getUsername()));
    }

    @Data
    @ApiModel(description = "登录请求参数")
    public static class LoginRequest {
        @ApiModelProperty(value = "用户名", required = true, example = "admin")
        private String username;
        @ApiModelProperty(value = "密码", required = true, example = "123456")
        private String password;
        @ApiModelProperty(value = "验证码", required = true, example = "1234")
        private String captcha;
        @ApiModelProperty(value = "验证码UUID", required = true, example = "uuid")
        private String uuid;
    }

    @Data
    @ApiModel(description = "登录响应结果")
    public static class LoginResponse {
        @ApiModelProperty(value = "访问令牌")
        private String token;
        @ApiModelProperty(value = "用户名")
        private String username;

        public LoginResponse(String token, String username) {
            this.token = token;
            this.username = username;
        }
    }
}
