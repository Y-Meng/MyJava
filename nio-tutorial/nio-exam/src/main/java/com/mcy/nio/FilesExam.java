package com.mcy.nio;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author zkzc-mcy create at 2018/4/11.
 */
public class FilesExam {

    public static void main(String[] args){

        Path newDir = Paths.get("c:/Z_DATA/newDir");
        try {
            if(!Files.exists(newDir)) {
                Files.createDirectory(newDir);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 复制文件
        String classPath = FilesExam.class.getResource("/data").getPath().substring(1);
        Path sourcePath = Paths.get(classPath,"nio-data.txt");
        Path targetPath = Paths.get(classPath,"nio-data-copy.txt");
        try {
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 移动文件
        Path movePath = Paths.get(classPath,"nio-data-move.txt");
        try {
            Files.move(targetPath, movePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 删除文件
        try {
            Files.delete(movePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 遍历文件
        Path start = Paths.get(classPath);
        try {
            Files.walkFileTree(start, new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

                    System.out.println("pre visit dir:" + dir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                    System.out.println("visit file:" + file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {

                    System.out.println("visit file failed:" + file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

                    System.out.println("post visit dir:" + dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 遍历查找文件
        try {
            Files.walkFileTree(start, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                    String filePath = file.toAbsolutePath().toString();

                    if(filePath.endsWith("nio-data.txt")){
                        System.out.println("file found at path:" + filePath);
                        return FileVisitResult.TERMINATE;
                    }

                    return super.visitFile(file, attrs);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 递归删除目录
        try {
            Files.walkFileTree(start, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println("delete file:" + file);
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    System.out.println("delete dir:" + dir);
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
