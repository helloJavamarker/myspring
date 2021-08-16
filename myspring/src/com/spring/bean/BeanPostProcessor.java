package com.spring.bean;

/**
 * @author by mark
 * @Classname BeanPostProcessor
 * @Description TODO
 * @Date 2021/8/15 9:40 上午
 */
public interface BeanPostProcessor {

    default Object postProcessBeforeInitialization(Object bean, String beanName)  throws Exception {
//        System.out.println("初始化前");
        return bean;
    }

    default Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
//        System.out.println("初始化后");

        return bean;
    }
}
