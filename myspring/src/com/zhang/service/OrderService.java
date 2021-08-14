package com.zhang.service;

import com.spring.Autowired;
import com.spring.Component;

/**
 * @author by mark
 * @Classname OrderService
 * @Description TODO
 * @Date 2021/8/14 12:58 下午
 */
@Component("orderService")
public class OrderService {
    @Autowired
    private UserService userService;
    public void test() {
        System.out.println("orderService.userService:" + userService);
    }
}
