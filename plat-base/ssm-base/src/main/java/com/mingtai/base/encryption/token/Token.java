package com.mingtai.base.encryption.token;

/**
 * Created by mengcy on 2017/3/24.
 */
public interface Token {

    String generateToken(String input, String key);

    boolean checkToken(String input, String key, String token);
}
