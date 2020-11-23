package com.fuck.gateway.annotation;

import org.springframework.core.annotation.AliasFor;
import java.lang.annotation.*;

/**
 * NettyHandler
 * @author duansg
 * @version 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NettyHandler {

    @AliasFor("path")
    String[] value() default {};

    String name() default "";

    @AliasFor("value")
    String[] path() default {};
}
