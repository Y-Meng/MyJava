package com.mingtai.base.aop.annotation.log;

import java.lang.annotation.*;

/**
 * @author zkzc-mcy create at 2018/3/21.
 *
 * @Target 声明作用域
 * @Retention 声明声明周期，
 *  1.RetentionPolicy.SOURCE —— 这种类型的Annotations只在源代码级别保留,编译时就会被忽略
 *  2.RetentionPolicy.CLASS —— 这种类型的Annotations编译时被保留,在class文件中存在,但JVM将会忽略
 *  3.RetentionPolicy.RUNTIME —— 这种类型的Annotations将被JVM保留,所以他们能在运行时被JVM或其他使用反射
 * @Documentd 声明这个注解被javadoc工具类记录，默认javadoc是不包括注解的
 * @Inherited 声明被注解的类会自动继承该注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Log {

    String type();
    String opt();
    String log();

}
