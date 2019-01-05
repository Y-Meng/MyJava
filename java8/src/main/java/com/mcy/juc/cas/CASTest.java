package com.mcy.juc.cas;

/**
 * Created by mengchaoyue on 2019/1/5.
 */
public class CASTest {

    static class CompareAndSwap{

        private int value;

        // 获取内存值
        public synchronized int get(){
            return value;
        }

        // 无论更新成功与否,都返回修改之前的内存值
        public synchronized int compareAndSwap(int expectedValue, int newValue){

            // 获取旧值
            int oldValue = value;

            if(oldValue == expectedValue){
                this.value = newValue;
            }

            // 返回修改之前的值
            return oldValue;
        }

        // 判断是否设置成功
        public synchronized boolean compareAndSet(int expectedValue, int newValue){
            return expectedValue == compareAndSwap(expectedValue, newValue);
        }
    }

    public static void main(String[] args){

        final CompareAndSwap cas = new CompareAndSwap();

        for(int i=0; i<1000; i++){
            new Thread(new Runnable(){
                public void run(){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    boolean success = false;
                    while(!success){
                        int expectedValue = cas.get();
                        success = cas.compareAndSet(expectedValue, expectedValue+1);
                        System.out.println("count:" + cas.get() + "--set:" + success);
                    }
                }
            }).start();
        }
    }
}
