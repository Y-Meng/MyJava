package com.mcy.juc.cas;

/**
 * Created by mengchaoyue on 2019/1/5.
 */
public class VolatileTest {

    static volatile int count = 1;

    public static void main(String[] args){
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("count:" + (count ++));
                }
            }).start();
        }
    }
}
