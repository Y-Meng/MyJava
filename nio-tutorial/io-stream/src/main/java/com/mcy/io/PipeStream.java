package com.mcy.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.*;

/**
 * @author zkzc-mcy create at 2018/3/19.
 */
public class PipeStream {

    public static void main(String[] args){

        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            PipedOutputStream pos = new PipedOutputStream();
            PipedInputStream pis = new PipedInputStream(pos);

            // 创建通信对象
            Sender sender = new Sender(pos);
            Receiver receiver = new Receiver(pis);

            // 运行
            executorService.execute(sender);
            executorService.execute(receiver);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 结束命令
        executorService.shutdown();
        try {
            // 阻塞等待线程全部结束，或超时，或异常
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class Sender extends Thread{

        private PipedOutputStream pos;

        public Sender(PipedOutputStream pos){
            super();
            this.pos = pos;
        }

        @Override
        public void run() {
            try {
                String s = "This is a good day. 今天是个好天气。";
                System.out.println("Sender:" + s);
                byte[] buf = s.getBytes();
                pos.write(buf, 0, buf.length);
                // 若不关闭，读取端无法读到-1，会报错
                pos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static class Receiver extends Thread{

        private PipedInputStream pis;
        public Receiver(PipedInputStream pis){
            this.pis = pis;
        }

        @Override
        public void run() {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = pis.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                byte[] result = baos.toByteArray();
                String s = new String(result, 0, result.length);
                System.out.println("Reciever:" + s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
