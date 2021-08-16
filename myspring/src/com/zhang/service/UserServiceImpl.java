package com.zhang.service;

import com.spring.bean.Autowired;
import com.spring.bean.BeanNameAware;
import com.spring.bean.Component;
import com.spring.bean.InitializingBean;

@Component("userService")
//@Scope("prototype")
public class UserServiceImpl implements UserService, BeanNameAware, InitializingBean {
    @Autowired
    private OrderService orderService;

    @Autowired
    private String beanName;

    private String name;

    @Override
    public void test() {
        System.out.println(orderService);
        System.out.println(beanName);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化");
    }

    public void setName(String name) {
        this.name = name;
    }
}
