package com.mcy.io;

import java.io.UnsupportedEncodingException;

/**
 * @author zkzc-mcy create at 2018/3/19.
 */
public class ByteAndChar {

    public static void charToBytes() {
        try {
            byte[] buf1 = "一".getBytes("unicode");
            System.out.println("---------unicode---------");
            for (int i = 0; i < buf1.length; i++) {
                System.out.println(Integer.toHexString(buf1[i]));
            }

            System.out.println("---------UTF-8---------");
            byte[] buf2 = "一".getBytes("UTF-8");
            for (int i = 0; i < buf2.length; i++) {
                System.out.println(Integer.toHexString(buf2[i]));
            }

            System.out.println("---------UTF-16---------");
            byte[] buf3 = "一".getBytes("UTF-16");
            for (int i = 0; i < buf3.length; i++) {
                System.out.println(Integer.toHexString(buf3[i]));
            }

            System.out.println("---------gbk---------");
            byte[] buf4 = "一".getBytes("gbk");
            for (int i = 0; i < buf4.length; i++) {
                System.out.println(Integer.toHexString(buf4[i]));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String byteToHexString(byte b) {
        String s = Integer.toHexString(b);
        int len = s.length();
        if (len >= 2) {
            s = s.substring(len - 2);
        }else{
            s = "0" + s;
        }
        return s;
    }

    private static void showNativeEncoding() {
        String enc = System.getProperty("file.encoding");
        System.out.println(enc);
    }


    public static void main(String[] args){
//        charToBytes();
        showNativeEncoding();
    }
}
