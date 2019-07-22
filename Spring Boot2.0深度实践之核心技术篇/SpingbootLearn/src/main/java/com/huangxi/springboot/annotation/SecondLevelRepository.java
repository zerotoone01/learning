package com.huangxi.springboot.annotation;

import org.springframework.stereotype.Repository;

import java.lang.annotation.*;

/**
 * 二级依赖注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FirstLevelRepository
public @interface SecondLevelRepository {
    String value() default "";
}
