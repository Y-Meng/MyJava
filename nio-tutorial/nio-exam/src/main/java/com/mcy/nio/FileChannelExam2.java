package com.mcy.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author zkzc-mcy create at 2018/3/26.
 */
public class FileChannelExam2 {

    public static void transferFrom() throws IOException {

        String fromPath = FileChannelExam2.class.getResource("/data/nio-data.txt").getPath();
        String toPath = FileChannelExam2.class.getResource("/data/nio-data-to.txt").getPath();

        RandomAccessFile fromFile = new RandomAccessFile(fromPath, "rw");
        FileChannel      fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile(toPath, "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        toChannel.transferFrom(fromChannel, position, count);
    }

    public static void transferTo() throws IOException{

        String fromPath = FileChannelExam2.class.getResource("/data/nio-data.txt").getPath();
        String toPath = FileChannelExam2.class.getResource("/data/nio-data-to.txt").getPath();

        RandomAccessFile fromFile = new RandomAccessFile(fromPath, "rw");
        FileChannel      fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile(toPath, "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        fromChannel.transferTo(position, count, toChannel);
    }

    public static void main(String[] args){
        try {
//            transferFrom();
            transferTo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
