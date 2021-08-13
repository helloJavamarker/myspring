package com.zhang;

import com.spring.MyApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        MyApplicationContext context = new MyApplicationContext(AppConfig.class);
        Object userService = context.getBean("userService");
    }
}
