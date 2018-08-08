package com.mingtai.base.util;

import org.springframework.util.Assert;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author zkzc-mcy create at 2018/8/8.
 */
public class GzipUtil {

    /**
     * 压缩数据
     * @param bytes
     * @return
     * @throws IOException
     */
    public static byte[] compress(byte[] bytes) throws IOException{

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             GZIPOutputStream gzip = new GZIPOutputStream(out)){
            gzip.write(bytes);
            gzip.close();
            return out.toByteArray();
        }
    }

    /**
     * gzip压缩字符串
     * @param content
     * @param charset
     * @return
     * @throws IOException
     */
    public static byte[] compress(String content, String charset) throws IOException {

        Assert.notNull(content, "null compress error");
        return compress(content.getBytes(charset));
    }

    /**
     * gizp解压
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] uncompress(InputStream in) throws IOException {

        try(ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPInputStream gin = new GZIPInputStream(in)){
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gin.read(buffer)) >= 0){
                out.write(buffer, 0 ,len);
            }
            return out.toByteArray();
        }
    }

    /**
     * 解压缩为字符串
     * @param in
     * @param charset
     * @return
     * @throws IOException
     */
    public static String uncompress(InputStream in, String charset) throws IOException {

        byte[] bytes = uncompress(in);
        return new String(bytes, charset);
    }
}
