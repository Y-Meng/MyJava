package com.mingtai.base.aop.annotation.valid;

import java.lang.annotation.*;

/**
 * @author zkzc-mcy create at 2018/1/9.
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Valid {

    Rule rule() default Rule.Field;
    String message() default "";
    int min() default 0;
    int max() default -1;

    enum Rule {

        NotNull, Value, Length, Field
    }
}
