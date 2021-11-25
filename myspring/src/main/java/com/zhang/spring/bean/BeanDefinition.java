package com.zhang.spring.bean;

/**
 * @author by mark
 * @Classname BeanDefinition
 * @Description TODO
 * @Date 2021/8/14 10:46 上午
 */
public class BeanDefinition {

    private Class clazz;
    private String scope;

    public BeanDefinition(Class clazz, String scope) {
        this.clazz = clazz;
        this.scope = scope;
    }

    public BeanDefinition() {
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
