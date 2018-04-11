package com.mcy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author zkzc-mcy create at 2018/4/11.
 */
public class DatagramChannelExam {


    public static void main(String[] args){

        DatagramChannel channel = null;
        try {
            // 打开channel
            channel = DatagramChannel.open();
            // 绑定UDP端口
            channel.bind(new InetSocketAddress(9999));
            // 申请buffer
            ByteBuffer buffer = ByteBuffer.allocate(48);
            buffer.clear();
            // 接收数据
            channel.receive(buffer);

            // 发送数据
            String data = "发送数据..." + System.currentTimeMillis();
            buffer.clear();
            buffer.put(data.getBytes());
            buffer.flip();
            int sent = channel.send(buffer, new InetSocketAddress("localhost", 80));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
