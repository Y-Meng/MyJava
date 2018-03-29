package com.mcy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author zkzc-mcy create at 2018/3/29.
 */
public class ServerSocketNonBlockingExam {

    public static void main(String[] args){
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(9999));
            serverSocketChannel.configureBlocking(false);
            while (true){
                SocketChannel socketChannel = serverSocketChannel.accept();
                if(socketChannel != null){
                    // 执行操作
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
