package com.yafnds.springbootshiro;

import com.yafnds.springbootshiro.pojo.User;
import com.yafnds.springbootshiro.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SpringBootShiroApplicationTests {

    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
        User currentUser = userService.queryUserByName("root");
        String[] split = currentUser.getPerms().split(",");
        for (String s : split) {
            System.out.println(s);
        }
    }

}
