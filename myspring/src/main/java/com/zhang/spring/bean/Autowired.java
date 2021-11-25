package com.zhang.spring.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author by mark
 * @Classname Autowored
 * @Description TODO
 * @Date 2021/8/14 12:57 下午
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD})
public @interface Autowired {
}
