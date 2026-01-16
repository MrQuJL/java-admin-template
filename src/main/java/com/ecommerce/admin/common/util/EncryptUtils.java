package com.ecommerce.admin.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * 加密工具类
 * 提供密码加密、验证、随机密码生成等功能
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EncryptUtils {

    /**
     * 加密算法：PBKDF2WithHmacSHA256
     */
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    
    /**
     * 盐值长度（字节）
     */
    private static final int SALT_LENGTH = 16;
    
    /**
     * 迭代次数
     */
    private static final int ITERATIONS = 10000;
    
    /**
     * 生成密钥长度（字节）
     */
    private static final int KEY_LENGTH = 32;

    /**
     * 加密密码
     * 使用PBKDF2WithHmacSHA256算法对密码进行加密
     *
     * @param password 原始密码
     * @return 加密后的密码（格式：盐值:密文）
     */
    public static String encryptPassword(String password) {
        if (password == null || password.isEmpty()) {
            return null;
        }
        
        try {
            // 生成随机盐值
            byte[] salt = generateSalt();
            
            // 使用PBKDF2算法生成密钥
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH * 8);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            
            // 将盐值和密文转换为Base64字符串，格式：盐值:密文
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hash);
            
            return saltBase64 + ":" + hashBase64;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }

    /**
     * 验证密码
     * 检查原始密码与加密密码是否匹配
     *
     * @param password     原始密码
     * @param encryptedPwd 加密后的密码（格式：盐值:密文）
     * @return 是否匹配
     */
    public static boolean verifyPassword(String password, String encryptedPwd) {
        if (password == null || encryptedPwd == null) {
            return false;
        }
        
        try {
            // 分割盐值和密文
            String[] parts = encryptedPwd.split(":");
            if (parts.length != 2) {
                return false;
            }
            
            // 解析盐值和密文
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] hash = Base64.getDecoder().decode(parts[1]);
            
            // 使用相同的参数生成密钥
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH * 8);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] testHash = factory.generateSecret(spec).getEncoded();
            
            // 比较生成的密文和存储的密文是否一致
            return slowEquals(hash, testHash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("密码验证失败", e);
        }
    }
    
    /**
     * 生成随机盐值
     *
     * @return 盐值字节数组
     */
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }
    
    /**
     * 安全比较两个字节数组
     * 采用固定时间比较，防止计时攻击
     *
     * @param a 字节数组a
     * @param b 字节数组b
     * @return 是否相等
     */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }

    /**
     * 生成随机密码
     * 生成指定长度的随机密码，包含大小写字母、数字和特殊字符
     *
     * @param length 密码长度
     * @return 随机密码
     */
    public static String generateRandomPassword(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("密码长度必须大于0");
        }
        // 密码字符集：包含大小写字母、数字和常用特殊字符
        String passwordChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;:,.<>?";
        return generateRandomString(passwordChars, length);
    }

    /**
     * 生成随机密码（默认12位）
     *
     * @return 12位随机密码
     */
    public static String generateRandomPassword() {
        return generateRandomPassword(12);
    }

    /**
     * 生成简单随机密码
     * 只包含大小写字母和数字，适合作为临时密码
     *
     * @param length 密码长度
     * @return 简单随机密码
     */
    public static String generateSimpleRandomPassword(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("密码长度必须大于0");
        }
        // 简单密码字符集：只包含大小写字母和数字
        String passwordChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return generateRandomString(passwordChars, length);
    }

    /**
     * 生成简单随机密码（默认8位）
     *
     * @return 8位简单随机密码
     */
    public static String generateSimpleRandomPassword() {
        return generateSimpleRandomPassword(8);
    }
    
    /**
     * 生成随机字符串
     *
     * @param chars  字符集
     * @param length 长度
     * @return 随机字符串
     */
    private static String generateRandomString(String chars, int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 检查密码强度
     * 评估密码的强度等级
     *
     * @param password 密码
     * @return 密码强度等级（1-弱，2-中，3-强）
     */
    public static int checkPasswordStrength(String password) {
        if (password == null || password.length() < 6) {
            return 1; // 弱密码
        }

        int strength = 1; // 默认弱密码
        int charTypeCount = 0;
        
        // 检查是否包含数字
        if (password.matches(".*\\d.*")) {
            charTypeCount++;
        }
        
        // 检查是否包含小写字母
        if (password.matches(".*[a-z].*")) {
            charTypeCount++;
        }
        
        // 检查是否包含大写字母
        if (password.matches(".*[A-Z].*")) {
            charTypeCount++;
        }
        
        // 检查是否包含特殊字符
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{}|;:,.<>?].*")) {
            charTypeCount++;
        }
        
        // 根据字符类型数量评估强度
        if (charTypeCount >= 2 && charTypeCount < 3) {
            strength = 2; // 中等密码：包含两种字符类型
        } else if (charTypeCount >= 3) {
            strength = 3; // 强密码：包含三种或更多字符类型
        }
        
        return strength;
    }
}