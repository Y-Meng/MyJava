package com.mcy.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/**
 * @author zkzc-mcy create at 2018/4/11.
 */
public class AsynchronousFileChannelExam {

    public static void main(String[] args){
        String classPath = FilesExam.class.getResource("/data").getPath().substring(1);
        Path filePath = Paths.get(classPath,"nio-data.txt");

        try {
            AsynchronousFileChannel asyncFileChannel = AsynchronousFileChannel.open(filePath, StandardOpenOption.READ);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            long position = 0;

            // 读方法一
            Future<Integer> operation = asyncFileChannel.read(buffer, position);
            while (!operation.isDone()){}
            buffer.flip();
            byte[] data = new byte[buffer.limit()];
            buffer.get(data);
            System.out.println(new String(data));
            buffer.clear();

            // 读方法二
            asyncFileChannel.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {

                    System.out.println(result);
                    attachment.flip();
                    byte[] data = new byte[attachment.limit()];
                    attachment.get(data);
                    System.out.println(new String(data));
                    attachment.clear();
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {

                }
            });
            asyncFileChannel.close();


            AsynchronousFileChannel writeFileChannel = AsynchronousFileChannel.open(filePath, StandardOpenOption.WRITE);
            // 写方法一
            position = 12;
            buffer.put("test data".getBytes());
            buffer.flip();
            Future<Integer> writeOperation = writeFileChannel.write(buffer, position);
            buffer.clear();
            while (!writeOperation.isDone()){}
            System.out.println("write done");


            // 写方法二
            writeFileChannel.write(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    System.out.println("bytes written:" + result);
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.out.println("bytes written failed");
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
