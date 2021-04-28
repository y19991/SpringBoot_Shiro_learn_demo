package com.yafnds.springbootshiro.service;

import com.yafnds.springbootshiro.pojo.User;

/**
 * 包名称：com.yafnds.springbootshiro.service
 * 类名称：UserService
 * 类描述：用户服务层
 * 创建人：@author y19991
 * 创建时间：2021/4/26 15:28
 */
public interface UserService {

    /**
     * 查找所有用户
     * @param name 用户名
     * @return
     */
    User queryUserByName(String name);
}
