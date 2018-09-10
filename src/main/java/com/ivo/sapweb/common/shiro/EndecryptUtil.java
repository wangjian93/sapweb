package com.ivo.sapweb.common.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 加密解密工具类 (依赖shiro的加密)
 * @author wangjian
 * @date 2018/9/1
 */
public class EndecryptUtil {

    /**
     * md5加密(没有盐)
     *
     * @param password 要加密的字符串
     */
    public static String encrytMd5(String password) {
        return new Md5Hash(password).toHex();
    }

    /**
     * 指定盐salt进行md5加密
     *
     * @param password       要加密的字符串
     * @param salt           盐
     * @param hashIterations 散列次数
     * @return
     */
    public static String encrytMd5(String password, String salt, int hashIterations) {
        String password_cipherText = new Md5Hash(password, salt, hashIterations).toHex();
        return password_cipherText;
    }
}
