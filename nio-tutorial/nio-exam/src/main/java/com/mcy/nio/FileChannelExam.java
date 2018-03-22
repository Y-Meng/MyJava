package com.mcy.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zkzc-mcy create at 2018/3/22.
 */
public class FileChannelExam {

    public static void main(String[] args){

        try {

            String path = FileChannelExam.class.getResource("/data/nio-data.txt").getPath();

            // 创建一个文件通道
            RandomAccessFile file = new RandomAccessFile(path, "rw");
            FileChannel channel = file.getChannel();

            // 创建一个字节buffer，容量为1024字节
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            // 读取数据到buffer
            int len = channel.read(buffer);

            while (len != -1){
                System.out.println("Read " + len);

                // 将写模式转变为读模式，
                // 将写模式下的buffer内容最后位置设为读模式下的limit位置，作为读越界位，同时将读位置设为0
                // 表示转换后重头开始读，同时消除写模式的mark标记
                buffer.flip();

                // 判断当前读取位置是否到达越界位（position < limit）
                while (buffer.hasRemaining()){
                    // 读取当前position的字节（position++）
                    System.out.println(buffer.get());
                }

                // 清空当前buffer内容
                buffer.clear();
                len = channel.read(buffer);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
