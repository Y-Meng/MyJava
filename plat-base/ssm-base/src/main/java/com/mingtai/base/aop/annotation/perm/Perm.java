package com.mingtai.base.aop.annotation.perm;

import java.lang.annotation.*;

/**
 * @author zkzc-mcy create at 2018/1/26.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Perm {

    String perm();
    String name() default "";
}
