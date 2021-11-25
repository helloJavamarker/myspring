package com.zhang.spring.bean;

/**
 * @author by mark
 * @Classname InitializingBean
 * @Description TODO
 * @Date 2021/8/15 9:35 上午
 */
public interface InitializingBean {

    void afterPropertiesSet() throws Exception;
}
