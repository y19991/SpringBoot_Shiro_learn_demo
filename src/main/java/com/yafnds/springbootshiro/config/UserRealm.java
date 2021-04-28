package com.yafnds.springbootshiro.config;

import com.yafnds.springbootshiro.pojo.User;
import com.yafnds.springbootshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;

/**
 * 包名称： com.yafnds.springbootshiro.config
 * 类名称：UserRealm
 * 类描述：自定义Reaml类 Realm是实现自定义登录和授权的核心类
 * 创建人：@author y19991
 * 创建时间：2021/4/25 15:57
 */
public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    /** 授权 */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 拿到当前登录的对象
        Subject subject = SecurityUtils.getSubject();
        // 拿到用户对象
        User currentUser = (User) subject.getPrincipal();

        // 为当前用户授权
        String[] permsList = currentUser.getPerms().split(",");
        if (null != permsList) {
            for (String s : permsList) {
                info.addStringPermission("user:" + s);
            }
        }
        /*else {
            // 获取未授权页面请求的方法，留作备用
            //String unauthorizedUrl = shiroFilterFactoryBean.getUnauthorizedUrl();
        }*/

        return info;
    }

    /** 认证 */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthenticationInfo");

        // 用户名认证
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        User user = userService.queryUserByName(userToken.getUsername());
        if (null == user) {
            // 返回null会抛出 UnknownAccountException
            return null;
        }

        // 密码认证。由shiro实现，我们直接返回结果值
        return new SimpleAuthenticationInfo(user, user.getPwd(), "");
    }
}
