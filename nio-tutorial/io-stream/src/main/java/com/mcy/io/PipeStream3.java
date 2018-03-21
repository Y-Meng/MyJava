package com.mcy.io;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zkzc-mcy create at 2018/3/19.
 * 通过管道保证数据读取性能
 * 一个线程持续读取数据，通过管道发送到另一个线程进行处理
 */
public class PipeStream3 {

    public static void main(String[] args) {
        try {

            PipedInputStream pis = new PipedInputStream();
            PipedOutputStream pos = new PipedOutputStream(pis);

            Sender sender = new Sender(pos);
            Receiver receiver = new Receiver(pis);

            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(sender);
            executorService.execute(receiver);

            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Sender extends Thread {

        private PipedOutputStream pos = null;

        public Sender(PipedOutputStream pos) {
            this.pos = pos;
        }

        @Override
        public void run() {
            try {
                FileInputStream fis = new FileInputStream("c:\\Z_DATA\\input.txt");
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = fis.read(buf)) != -1) {
                    pos.write(buf, 0, len);
                }
                pos.flush();
                pos.close();
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Receiver extends Thread {
        private PipedInputStream pis = null;

        public Receiver(PipedInputStream pis) {
            this.pis = pis;
        }

        @Override
        public void run() {
            try {
                FileOutputStream fos = new FileOutputStream("c:\\Z_DATA\\output.txt");
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = pis.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                fos.flush();
                fos.close();
                pis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
