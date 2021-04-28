package com.yafnds.springbootshiro.service.impl;

import com.yafnds.springbootshiro.mapper.UserMapper;
import com.yafnds.springbootshiro.pojo.User;
import com.yafnds.springbootshiro.service.UserService;
import com.yafnds.springbootshiro.util.EncryptUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.yafnds.springbootshiro.util.EncryptUtil.HASH_ITERATIONS;

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

    /**
     * 新增一个用户
     * @param user 需要插入的用户信息
     */
    @Override
    public void save(User user) {

        // 1. 生成随机盐
        String salt = EncryptUtil.getSalt(8);
        // 2. 将其保存进user
        user.setSalt(salt);
        // 3. 明文密码进行 md5+salt+hash散列
        Md5Hash md5Hash = new Md5Hash(user.getPwd(), salt, HASH_ITERATIONS);
        // 4. 把密码保存到user
        user.setPwd(md5Hash.toHex());

        userMapper.save(user);
    }
}
