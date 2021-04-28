package com.yafnds.springbootshiro.service.impl;

import com.yafnds.springbootshiro.mapper.UserMapper;
import com.yafnds.springbootshiro.pojo.User;
import com.yafnds.springbootshiro.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 包名称：com.yafnds.springbootshiro.service.impl
 * 类名称：UserServiceImpl
 * 类描述：用户服务层实现类
 * 创建人：@author y19991
 * 创建时间：2021/4/26 15:31
 */

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户名查找用户
     * @param name 用户名
     * @return 一个用户 {@link User}
     */
    @Override
    public User queryUserByName(String name) {
        return userMapper.queryUserByName(name);
    }
}
