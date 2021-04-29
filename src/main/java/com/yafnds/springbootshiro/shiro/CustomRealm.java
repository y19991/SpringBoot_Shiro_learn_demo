package com.yafnds.springbootshiro.shiro;

import com.yafnds.springbootshiro.pojo.User;
import com.yafnds.springbootshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * 包名称： com.yafnds.springbootshiro.config
 * 类名称：CustomRealm
 * 类描述：自定义Reaml类 Realm是实现自定义登录和授权的核心类
 * 创建人：@author y19991
 * 创建时间：2021/4/25 15:57
 */
public class CustomRealm extends AuthorizingRealm {

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
        if (!ObjectUtils.isEmpty(permsList)) {
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

        // 获取身份信息
        String principal = (String) token.getPrincipal();

        /*
            注入UserService
                方法一：自动注入 @Resource
                方法二：通过工具类，从bean工厂中获取
        */

        User user = userService.queryUserByName(principal);
        if (!ObjectUtils.isEmpty(user)) {
            // 密码认证。由shiro实现，我们直接返回结果值
            return new SimpleAuthenticationInfo(user, user.getPwd()
                    , ByteSource.Util.bytes(user.getSalt()), this.getName());
        }

        return null;
    }
}
