package com.ecommerce.admin.common.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 加密工具类测试
 * 测试EncryptUtils的各项功能
 */
class EncryptUtilsTest {

    /**
     * 测试密码加密和验证功能
     */
    @Test
    void testEncryptAndVerifyPassword() {
        // 原始密码
        String password = "Test@123456";
        
        // 加密密码
        String encryptedPassword = EncryptUtils.encryptPassword(password);
        assertNotNull(encryptedPassword, "加密密码不应为null");
        assertTrue(encryptedPassword.contains(":"), "加密密码应包含盐值和密文分隔符");
        
        // 验证密码
        boolean isMatch = EncryptUtils.verifyPassword(password, encryptedPassword);
        assertTrue(isMatch, "密码验证应返回true");
    }

    /**
     * 测试密码不匹配的情况
     */
    @Test
    void testVerifyPasswordMismatch() {
        // 原始密码
        String password = "Test@123456";
        String wrongPassword = "Wrong@Password";
        
        // 加密密码
        String encryptedPassword = EncryptUtils.encryptPassword(password);
        assertNotNull(encryptedPassword, "加密密码不应为null");
        
        // 使用错误密码验证
        boolean isMatch = EncryptUtils.verifyPassword(wrongPassword, encryptedPassword);
        assertFalse(isMatch, "错误密码验证应返回false");
    }

    /**
     * 测试空密码处理
     */
    @Test
    void testEmptyPassword() {
        // 测试空密码加密
        String encryptedPassword1 = EncryptUtils.encryptPassword(null);
        assertNull(encryptedPassword1, "null密码加密应返回null");
        
        String encryptedPassword2 = EncryptUtils.encryptPassword("");
        assertNull(encryptedPassword2, "空字符串密码加密应返回null");
        
        // 测试空密码验证
        boolean isMatch1 = EncryptUtils.verifyPassword(null, "test:encrypted");
        assertFalse(isMatch1, "null密码验证应返回false");
        
        boolean isMatch2 = EncryptUtils.verifyPassword("test", null);
        assertFalse(isMatch2, "null加密密码验证应返回false");
    }

    /**
     * 测试随机密码生成功能
     */
    @Test
    void testGenerateRandomPassword() {
        // 测试默认长度随机密码
        String randomPassword1 = EncryptUtils.generateRandomPassword();
        assertNotNull(randomPassword1, "随机密码不应为null");
        assertEquals(12, randomPassword1.length(), "默认随机密码长度应为12");
        
        // 测试指定长度随机密码
        int customLength = 16;
        String randomPassword2 = EncryptUtils.generateRandomPassword(customLength);
        assertNotNull(randomPassword2, "随机密码不应为null");
        assertEquals(customLength, randomPassword2.length(), "指定长度随机密码长度应匹配");
        
        // 测试不同调用生成不同密码
        String randomPassword3 = EncryptUtils.generateRandomPassword();
        assertNotEquals(randomPassword1, randomPassword3, "不同调用生成的随机密码应不同");
    }

    /**
     * 测试简单随机密码生成功能
     */
    @Test
    void testGenerateSimpleRandomPassword() {
        // 测试默认长度简单随机密码
        String simplePassword1 = EncryptUtils.generateSimpleRandomPassword();
        assertNotNull(simplePassword1, "简单随机密码不应为null");
        assertEquals(8, simplePassword1.length(), "默认简单随机密码长度应为8");
        assertTrue(simplePassword1.matches("[a-zA-Z0-9]++"), "简单随机密码应只包含字母和数字");
        
        // 测试指定长度简单随机密码
        int customLength = 10;
        String simplePassword2 = EncryptUtils.generateSimpleRandomPassword(customLength);
        assertNotNull(simplePassword2, "简单随机密码不应为null");
        assertEquals(customLength, simplePassword2.length(), "指定长度简单随机密码长度应匹配");
        assertTrue(simplePassword2.matches("[a-zA-Z0-9]++"), "简单随机密码应只包含字母和数字");
    }

    /**
     * 测试密码强度检查功能
     */
    @Test
    void testCheckPasswordStrength() {
        // 测试弱密码
        String weakPassword1 = "123456";
        assertEquals(1, EncryptUtils.checkPasswordStrength(weakPassword1), "弱密码强度应为1");
        
        String weakPassword2 = "abcdef";
        assertEquals(1, EncryptUtils.checkPasswordStrength(weakPassword2), "弱密码强度应为1");
        
        // 测试中等密码
        String mediumPassword1 = "abc123";
        assertEquals(2, EncryptUtils.checkPasswordStrength(mediumPassword1), "中等密码强度应为2");
        
        String mediumPassword2 = "Abcdef";
        assertEquals(2, EncryptUtils.checkPasswordStrength(mediumPassword2), "中等密码强度应为2");
        
        // 测试强密码
        String strongPassword1 = "Abc123!";
        assertEquals(3, EncryptUtils.checkPasswordStrength(strongPassword1), "强密码强度应为3");
        
        String strongPassword2 = "Test@123456";
        assertEquals(3, EncryptUtils.checkPasswordStrength(strongPassword2), "强密码强度应为3");
        
        // 测试超长强密码
        String veryStrongPassword = "Test@1234567890abcdefghijklmnopqrstuvwxyz";
        assertEquals(3, EncryptUtils.checkPasswordStrength(veryStrongPassword), "超长强密码强度应为3");
    }

    /**
     * 测试不同密码的加密结果应不同
     */
    @Test
    void testDifferentPasswordsHaveDifferentHashes() {
        // 两个不同的密码
        String password1 = "Password1@123";
        String password2 = "Password2@456";
        
        // 加密密码
        String encrypted1 = EncryptUtils.encryptPassword(password1);
        String encrypted2 = EncryptUtils.encryptPassword(password2);
        
        // 验证加密结果不同
        assertNotEquals(encrypted1, encrypted2, "不同密码的加密结果应不同");
    }

    /**
     * 测试相同密码的加密结果应不同（因为使用了随机盐值）
     */
    @Test
    void testSamePasswordHasDifferentHashes() {
        // 相同的密码
        String password = "Same@Password123";
        
        // 两次加密相同密码
        String encrypted1 = EncryptUtils.encryptPassword(password);
        String encrypted2 = EncryptUtils.encryptPassword(password);
        
        // 验证加密结果不同（因为使用了随机盐值）
        assertNotEquals(encrypted1, encrypted2, "相同密码的加密结果应不同，因为使用了随机盐值");
        
        // 但两次加密结果都应能通过验证
        assertTrue(EncryptUtils.verifyPassword(password, encrypted1), "第一次加密结果应能通过验证");
        assertTrue(EncryptUtils.verifyPassword(password, encrypted2), "第二次加密结果应能通过验证");
    }
}