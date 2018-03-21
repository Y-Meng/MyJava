package com.mingtai.base.encryption;

import com.mingtai.base.encryption.encrypt.AESStrategy;
import com.mingtai.base.encryption.encrypt.EncryptStrategy;
import com.mingtai.base.encryption.token.SHA1Token;
import com.mingtai.base.encryption.token.Token;

/**
 * Created by mengcy on 2017/3/24.
 */
public class EncryptionManager {

    private static final String DEFAULT_KEY = "com.mcy.base";

    private static Token defaultToken = new SHA1Token();

    private static EncryptStrategy defaultStrategy = new AESStrategy();

    /**
     * 根据输入内容生成 token ,使用默认加密KEY
     *
     * @return
     */
    public static String generateToken(String input) {
        return generateToken(input, DEFAULT_KEY);
    }

    /**
     * 根据输入内容生成 token ,使用指定KEY
     *
     * @return
     */
    public static String generateToken(String input, String key) {
        return defaultToken.generateToken(input, key);
    }

    /**
     * 根据输入信息校验 token
     *
     * @return
     */
    public static boolean checkToken(String input, String token) {
        return checkToken(input, DEFAULT_KEY, token);
    }

    /**
     * 校验 token
     *
     * @return
     */
    public static boolean checkToken(String input, String key, String token) {
        return defaultToken.checkToken(input, key, token);
    }


    /**
     * 加密数据：使用默认key
     * @param data
     * @return
     */
    public static String encryptData(String data){
        return encryptData(data,DEFAULT_KEY);
    }

    /**
     * 加密数据
     *
     * @param data 明文数据
     * @param key 秘钥
     * @return
     */
    public static String encryptData(String data, String key) {

        return defaultStrategy.encryptData(data, key);
    }

    /**
     * 解密数据：使用默认key
     * @param data
     * @return
     */
    public static String decryptData(String data){
        return defaultStrategy.decryptData(data, DEFAULT_KEY);
    }

    /**
     * 解密数据
     *
     * @param secretData 密文数据
     * @param key 秘钥
     * @return
     */
    public static String decryptData(String secretData, String key) {
        return decryptData(secretData, key);
    }


    //测试
    public static void main(String[] args) {

        String input = "{id:111,name:\"111\",sss,sss,中文测试}";

        // token
        System.out.println("input:" + input);
        String token = generateToken(input);
        System.out.println("token:" + token);
        System.out.println("check:" + checkToken(input, token));

        // encrypt
        System.out.println("input:" + input);
        System.out.println("password:" + DEFAULT_KEY);
        String secret = encryptData(input);
        System.out.println("encrypt:" + secret);
        System.out.println("decrypt:" + decryptData(secret));
    }
}
