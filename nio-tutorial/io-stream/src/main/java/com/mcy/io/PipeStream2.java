package com.mcy.io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author zkzc-mcy create at 2018/3/19.
 * 使用管道流 + 信号量多次传递数据
 */
public class PipeStream2 {

    /** 读取信号量，初始状态0 */
    public static Semaphore READ_SIGNAL = new Semaphore(0, true);
    /** 写入信号量，初始状态1，表示允许一次写入 */
    public static Semaphore WRITE_SIGNAL = new Semaphore(1, true);

    public static void main(String[] args){
        ExecutorService executorService = new ThreadPoolExecutor(8, 8,
                1, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        try {
            PipedOutputStream pos = new PipedOutputStream();
            PipedInputStream pis = new PipedInputStream(pos);

            Sender sender = new Sender(pos);
            Receiver receiver = new Receiver(pis);

            executorService.execute(sender);
            executorService.execute(receiver);

        } catch (IOException e) {
            e.printStackTrace();
        }
        executorService.shutdown();

    }

    static class Sender extends Thread {
        private PipedOutputStream pos;

        public Sender(PipedOutputStream pos) {
            super();
            this.pos = pos;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(new Random().nextInt(1000));
                    //获取写入信号量
                    WRITE_SIGNAL.acquire();
                    String content = "today is a good day. 今天是个好天气："+i;
                    System.out.println("Sender:" + content);
                    pos.write(content.getBytes("utf-8"));
                    //释放读取信号量
                    READ_SIGNAL.release();
                }
                pos.close();
                READ_SIGNAL.release();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Receiver extends Thread {
        private PipedInputStream pis;
        public Receiver(PipedInputStream pis) {
            super();
            this.pis = pis;
        }

        @Override
        public void run() {
            try {
                byte[] buf = new byte[1024];
                int len = 0;
                String s;
                while(true) {
                    //获取读取信号量
                    READ_SIGNAL.acquire();
                    len = pis.read(buf);

                    if(len == -1) {break;}

                    s = new String(buf, 0, len, "utf-8");
                    System.out.println("Receive:" + s);
                    //释放写入信号量
                    WRITE_SIGNAL.release();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
