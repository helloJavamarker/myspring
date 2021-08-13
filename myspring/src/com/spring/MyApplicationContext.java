package com.spring;

public class MyApplicationContext {
    private Class configClass;

    public MyApplicationContext(Class configClass) {
        this.configClass = configClass;
        //解析配置
        //componentScan---扫描路径---扫描
    }
    public Object getBean(String beanName){
        return null;
    }
}
