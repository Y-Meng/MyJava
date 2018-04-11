package com.mcy.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @author zkzc-mcy create at 2018/4/11.
 */
public class PipeExam {


    public static void main(String[] args){
        try {
            Pipe pipe = Pipe.open();

            new ThreadB(pipe).start();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new ThreadA(pipe).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static class ThreadA extends Thread{

        private Pipe pipe;

        public ThreadA(Pipe pipe){
            this.pipe = pipe;
        }

        @Override
        public void run() {
            super.run();

            while (true){
                Pipe.SinkChannel sinkChannel = pipe.sink();

                String data = "写入数据..." + System.currentTimeMillis();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                buffer.clear();
                buffer.put(data.getBytes());
                buffer.flip();
                while (buffer.hasRemaining()){
                    try {
                        sinkChannel.write(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(data);

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class ThreadB extends Thread{

        private Pipe pipe;

        public ThreadB(Pipe pipe){
            this.pipe = pipe;
        }

        @Override
        public void run() {
            super.run();

            while (true){

                Pipe.SourceChannel sourceChannel = pipe.source();
                try {
                    // 设置为非阻塞模式
                    sourceChannel.configureBlocking(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                try {
                    int read = sourceChannel.read(buffer);
                    System.out.println(System.currentTimeMillis() + " read " + read);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
