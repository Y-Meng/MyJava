package com.mingtai.base.encryption.encrypt;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Created by mengcy on 2017/3/24.
 */
public class AESStrategy implements EncryptStrategy {

    public String encryptData(String data,String password) {

        byte[] bytes = aesEncrypt(data,password);
        if(bytes!=null){
            return new String(Base64.getEncoder().encode(bytes));
        }
        return null;
    }

    public String decryptData(String secretData,String password) {

        byte[] bytes = Base64.getDecoder().decode(secretData);
        return aesDecrypt(bytes, password);
    }

    /**
     * aes解密
     * @param secretData
     * @param password
     * @return
     */
    private String aesDecrypt(byte[] secretData,String password){
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");

            //防止Linux随机码
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(password.getBytes());

            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 加密
            byte[] result = cipher.doFinal(secretData);
            return new String(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * aes 加密
     * @param data
     * @param password
     * @return
     */
    private byte[] aesEncrypt(String data,String password){
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");

            //防止Linux随机码
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(password.getBytes());

            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = data.getBytes("utf-8");
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
