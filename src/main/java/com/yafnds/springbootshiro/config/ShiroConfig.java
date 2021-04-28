package com.yafnds.springbootshiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 包名称： com.yafnds.springbootshiro.config
 * 类名称：ShiroConfig
 * 类描述：shiro配置类
 * 创建人：@author y19991
 * 创建时间：2021/4/25 16:09
 */

@Configuration
public class ShiroConfig {

    /**
     * 第一步：创建 shiroFilter
     * 负责拦截所有请求
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {

        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 给filter设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        // 添加shiro的内置过滤器
        /*
            anon： 无需认证就可以访问
            authc： 必须认证了才能访问
            user： 必须拥有记住我功能才能用
            perms： 拥有对某个资源的权限才能访问
            role： 拥有某个角色权限
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        
        filterMap.put("/user/add", "perms[user:add]");
        filterMap.put("/user/update", "perms[user:update]");
        filterMap.put("/user/*", "authc");

        bean.setFilterChainDefinitionMap(filterMap);

        // 设置登录请求
        bean.setLoginUrl("/toLogin");

        // 设置未授权请求
        bean.setUnauthorizedUrl("/noauth");

        return bean;
    }

    /**
     * 第二步：默认的安全管理器
     * @param userRealm
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联自定义Realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 第三步：配置自定义Realm
     * @return 自定义Realm类实例
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }

    /**
     * Thymeleaf前端页面 shiro标签支持
     */
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }

}
