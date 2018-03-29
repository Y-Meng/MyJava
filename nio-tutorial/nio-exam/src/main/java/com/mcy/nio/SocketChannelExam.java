package com.mcy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author zkzc-mcy create at 2018/3/29.
 */
public class SocketChannelExam {

    public static void read(SocketChannel channel) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuilder builder = new StringBuilder();

        int len = 0;
        while (len != -1){

            len = channel.read(buffer);

            buffer.flip();
            while (buffer.hasRemaining()) {
                builder.append(buffer.get());
            }
            buffer.clear();
        }

        System.out.println("read: " + builder.toString());
    }

    public static void write(SocketChannel channel) throws IOException {

        String newData = "你好，我是SocketChannel..." + System.currentTimeMillis();

        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());

        buf.flip();

        while(buf.hasRemaining()) {
            channel.write(buf);
        }

        System.out.println("write: " + newData);
    }

    public static void main(String[] args){

        try {
            // 1. 打开通道
            SocketChannel socketChannel = SocketChannel.open();

            // 2. 连接到服务端口
            boolean connected = socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));

            if(connected){

                // 3. 写入数据
                write(socketChannel);

                // 4. 读取数据
//                read(socketChannel);
            }

            while (true){
                // 等待
            }

            // 5. 关闭通道
//            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
