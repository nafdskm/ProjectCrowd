package com.skm.crowd.util;

import com.skm.crowd.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 通用工具方法类
 */
public class CrowdUtil {

    /**
     * 判断当前请求是否是ajax请求
     *
     * @param request
     * @return true    当前请求是ajax请求
     * false   当前请求不是ajax请求
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        // 获取消息头
        String acceptHeader = request.getHeader("Accept");
        String xRequestedHeader = request.getHeader("X-Requested-With");

        // 判断
        return (acceptHeader != null && acceptHeader.contains("application/json"))
                || (xRequestedHeader != null && xRequestedHeader.equals("XMLHttpRequest"));
    }

    /**
     * 对明文字符串进行md5加密
     * @param source    要加密字符串
     * @return  加密结果
     */
    public static String md5(String source) {
        if (source == null || source.length() == 0) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }


        try {
            String algorithm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            // 获取原字符串的字节数组
            byte[] input = source.getBytes();
            // 执行加密
            byte[] output = messageDigest.digest(input);

            // 加密输出结果 转换为 正数BigInteger
            int sigNum = 1;
            BigInteger bigInteger = new BigInteger(sigNum, output);
            // 按照16进制转为String
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();

            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
