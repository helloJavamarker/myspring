package com.zhang;

import com.spring.bean.MyApplicationContext;
import com.zhang.service.OrderService;
import com.zhang.service.UserService;

public class MyTest {
    public static void main(String[] args) {
        //bean 的生命周期:class(UserService.class)-->实例化-->对象-->属性填充-->初始化afterPropertiesSet -->AOP--->代理对象--->bean对象
        MyApplicationContext context = new MyApplicationContext(AppConfig.class);
//        Object userService = context.getBean("userService");//map  <beanName, bean对象>   解析对象??
//        Object userService2 = context.getBean("userService");//scope是单例时,返回的对象一直都是同一个

        UserService userService = (UserService)context.getBean("userService");
        userService.test();// 这里userService的属性orderService有值

        OrderService orderService = (OrderService) context.getBean("orderService");
        orderService.test();  //这里orderService的属性userService没有值  todo
    }
}
