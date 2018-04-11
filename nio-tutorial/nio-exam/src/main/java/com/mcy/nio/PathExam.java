package com.mcy.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author zkzc-mcy create at 2018/4/11.
 */
public class PathExam {

    public static void main(String[] args){

        Path path = Paths.get("c:/Z_DATA/./test.txt");

        System.out.println("path = " + path);

        path = path.normalize();

        System.out.println("path = " + path);



    }
}
