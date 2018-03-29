package com.mcy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author zkzc-mcy create at 2018/3/29.
 */
public class ServerSocketExam {

    public static void main(String[] args){

        ServerSocketChannel serverSocketChannel = null;
        try {
            // 1. 打开一个ServerSocketChannel
            serverSocketChannel = ServerSocketChannel.open();

            // 2. 绑定到需要监听的端口
            serverSocketChannel.socket().bind(new InetSocketAddress(9999));

            boolean listening = true;
            while(listening){

                System.out.println("listening...");

                // 3. 监听请求（阻塞，直到接收到请求）
                SocketChannel socketChannel = serverSocketChannel.accept();
                System.out.println("accept request");

                ByteBuffer buffer = ByteBuffer.allocate(1024);
                StringBuilder builder = new StringBuilder("read:");

                // 4. 读取通道中请求数据
                int len = socketChannel.read(buffer);
                while (len != -1){
                    buffer.flip();
                    while (buffer.hasRemaining()){
                        builder.append(buffer.get());
                    }
                    buffer.clear();
                    len = socketChannel.read(buffer);
                }
                System.out.println(builder.toString());

                // 5. 写入返回数据
                String returnData = "你好，我是Server SocketChannel..." + System.currentTimeMillis();

                buffer.clear();
                buffer.put(returnData.getBytes());

                buffer.flip();

                while(buffer.hasRemaining()) {
                    socketChannel.write(buffer);
                }
                System.out.println("write:" + returnData);

            }

            // 6. 关闭通道
            serverSocketChannel.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
