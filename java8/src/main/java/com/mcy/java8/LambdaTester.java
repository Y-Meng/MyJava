package com.mcy.java8;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengchaoyue on 2018/8/5.
 */
public class LambdaTester {

    public static void main(String[] args){

        // 带有类型声明的表达式
        MathOperation addition = (int a, int b) -> a + b;

        // 不带类型声明的表达式
        MathOperation substraction = (a, b) -> a - b;

        // 带有大括号，带有返回语句的表达式
        MathOperation multiplication = (int a, int b) -> {return a * b;};

        // 没有大括号和返回语句的表达式
        MathOperation division = (a, b) -> a / b;


        // 输出结果
        LambdaTester tester = new LambdaTester();
        System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
        System.out.println("10 - 5 = " + tester.operate(10, 5, substraction));
        System.out.println("10 * 5 = " + tester.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + tester.operate(10, 5, division));

        // 单个参数不使用括号的表达式
        GreetingService greeting1 = message -> System.out.println(message);
        greeting1.say("hello");

        // 带括号的表达式
        GreetingService greeting2 = (message) -> System.out.println(message);
        greeting2.say("你好");

        List<String> names = new ArrayList<>();
        names.add("张三");
        names.add("李四");
        names.add("王五");

        names.forEach(System.out::println);
    }

    // 定义一些接口
    interface MathOperation{
        int operate(int a, int b);
    }

    interface GreetingService{
        void say(String message);
    }

    private int operate(int a, int b, MathOperation operation){
        return operation.operate(a, b);
    }
}
