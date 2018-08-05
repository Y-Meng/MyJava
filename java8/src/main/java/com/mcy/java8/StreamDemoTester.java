package com.mcy.java8;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by mengchaoyue on 2018/8/5.
 */
public class StreamDemoTester {

    static void forEach(){

        System.out.println("forEach:-----------------");
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
    }

    static void map(){

        System.out.println("map:-----------------");

        List<Integer> numbers = Arrays.asList(2,3,2,5,2,7);
        List<Integer> squares = numbers.stream()
                .map( i -> i*i)
                .distinct()
                .collect(Collectors.toList());

        numbers.forEach(System.out::println);
        squares.forEach(System.out::println);
    }

    static void filter(){

        System.out.println("filter:---------------");
        List<String>strings = Arrays.asList("efg", "", "abc", "bc", "ghij","", "lmn");
        //get count of empty string
        long count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println(count);
    }

    static void parallelFilter(){

        System.out.println("parallel filter:---------------");
        List<String>strings = Arrays.asList("efg", "", "abc", "bc", "ghij","", "lmn");
        //get count of empty string
        long count = strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println(count);
    }

    static void limit(){

        System.out.println("limit:---------------");
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
    }

    static void sorted(){

        System.out.println("sorted:---------------");
        Random random = new Random();
        random.ints().limit(10).sorted().forEach(System.out::println);
    }

    public static void main(String[] args){

        // forEach 迭代操作
        forEach();

        // map 映射操作
        map();

        // filter 操作
        filter();

        // limit 操作，限制流的大小
        limit();

        // sorted 操作
        sorted();

        // 并行处理
        parallelFilter();

    }
}
