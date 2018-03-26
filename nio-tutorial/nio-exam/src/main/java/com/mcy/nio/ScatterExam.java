package com.mcy.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zkzc-mcy create at 2018/3/26.
 */
public class ScatterExam {

    public static void main(String[] args){
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body = ByteBuffer.allocate(1024);

        ByteBuffer[] byteBuffers = {header, body};

        String path = FileChannelExam.class.getResource("/data/nio-data.txt").getPath();

        // 创建一个文件通道
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(path, "rw");
            FileChannel channel = file.getChannel();

            channel.read(byteBuffers);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
