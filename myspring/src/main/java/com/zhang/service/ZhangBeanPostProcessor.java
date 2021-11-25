package com.zhang.service;

import com.zhang.spring.bean.BeanPostProcessor;
import com.zhang.spring.bean.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author by mark
 * @Classname ZhangBeanPostProcessor
 * @Description TODO
 * @Date 2021/8/15 9:46 上午
 */
@Component("zhangBeanPostProcessor")
public class ZhangBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        if ("userService".equals(beanName)){
            ((UserServiceImpl)bean).setName("zhangAF");
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
        //return new User();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        ////jdk 动态代理产生代理对象     使用cglib是靠继承,继承UserService后,可以重写目标方法
        if ("userService".equals(beanName)) {
            Object proxyInstance = Proxy.newProxyInstance(ZhangBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("代理逻辑");
                    return method.invoke(bean, args);//反射执行业务逻辑
                }
            });
            return proxyInstance;
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
