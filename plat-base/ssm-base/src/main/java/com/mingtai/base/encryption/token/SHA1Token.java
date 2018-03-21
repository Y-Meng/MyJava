package com.mingtai.base.encryption.token;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mengcy on 2017/3/24.
 */
public class SHA1Token implements Token {

    /**
     * 生成 token
     *
     * @param input 输入内容
     * @param key   token 秘钥
     * @return
     */
    public String generateToken(String input, String key) {

        String data = null;
        if (key != null) {
            data = input + ":" + key;
        } else {
            data = input;
        }

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");

            digest.update(data.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证 token
     *
     * @param input 输入内容
     * @param key   token 秘钥
     * @param token
     * @return
     */
    public boolean checkToken(String input, String key, String token) {
        if(input!=null && key != null && token!=null){
            return token.equals(generateToken(input,key));
        }

        return false;
    }
}
