package com.mingtai.base.util;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zkzc-mcy create at 2018/5/25.
 */
public class JWToken {

    private static final String SECRET = "http://aimingtai.com";
    private static final String EXPIRE = "expire";
    private static final String USER = "user";

    /**
     * 生成签名
     * @param user 用户信息
     * @param maxAge 有效期
     * @param <T> 用户类型
     * @return
     */
    public static <T> String sign(T user, long maxAge){
        final JWTSigner signer = new JWTSigner(SECRET);
        final Map<String, Object> data = new HashMap<>(4);
        data.put(USER, JSONObject.toJSONString(user));
        data.put(EXPIRE, System.currentTimeMillis() + maxAge);
        return signer.sign(data);
    }

    /**
     * 解析token
     * @param token token串
     * @param clz 用户实体类类型
     * @param <T> 用户类
     * @return
     */
    public static <T> T verify(String token, Class<T> clz){
        final JWTVerifier verifier = new JWTVerifier(SECRET);
        try {
            final Map<String, Object> data = verifier.verify(token);
            if(data.containsKey(EXPIRE) && data.containsKey(USER)){
                long expire = (Long)data.get(EXPIRE);
                if(expire > System.currentTimeMillis()){
                    String json = (String) data.get(USER);
                    return JSONObject.parseObject(json, clz);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (JWTVerifyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
