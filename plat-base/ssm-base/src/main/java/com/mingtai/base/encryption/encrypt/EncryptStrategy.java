package com.mingtai.base.encryption.encrypt;

/**
 * Created by mengcy on 2017/3/24.
 */
public interface EncryptStrategy {
    /**
     * 加密数据
     * @param data
     * @return
     */
    String encryptData(String data, String password);

    /**
     * 解码数据
     * @param secretData
     * @return
     */
    String decryptData(String secretData, String password);
}
