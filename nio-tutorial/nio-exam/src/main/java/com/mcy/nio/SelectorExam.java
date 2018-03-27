package com.mcy.nio;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * @author zkzc-mcy create at 2018/3/27.
 */
public class SelectorExam {

    public static void main(String[] args){
        try {
            Selector selector = Selector.open();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
