package com.yafnds.springbootshiro.mapper;

import com.yafnds.springbootshiro.pojo.User;
import org.springframework.stereotype.Repository;

/**
 * 包名称：com.yafnds.springbootshiro.mapper
 * 类名称：UserMapper
 * 类描述：
 * 创建人：@author y19991
 * 创建时间：2021/4/26 10:05
 */

@Repository
public interface UserMapper {

    /**
     * 根据用户名查找用户
     * @param name 用户名
     * @return 一个用户 {@link User}
     */
    User queryUserByName(String name);

}
