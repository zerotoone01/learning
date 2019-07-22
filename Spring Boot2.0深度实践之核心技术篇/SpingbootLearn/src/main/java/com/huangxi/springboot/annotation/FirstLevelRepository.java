package com.huangxi.springboot.annotation;


import org.springframework.stereotype.Repository;

import java.lang.annotation.*;

/**
 * 一级依赖注解 -- 派生系 （@Repository 派生）
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repository
public @interface FirstLevelRepository {

    String value() default "";
}
