package com.yafnds.springbootshiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.yafnds.springbootshiro.shiro.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.yafnds.springbootshiro.util.EncryptUtil.HASH_ITERATIONS;

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

        // 系统公共资源
        filterMap.put("/user/toLogin", "anon");
        filterMap.put("/user/toRegister", "anon");
        filterMap.put("/user/login", "anon");
        filterMap.put("/user/register", "anon");

        // 系统受限资源
        filterMap.put("/user/add", "perms[user:add]");
        filterMap.put("/user/update", "perms[user:update]");
        filterMap.put("/user/*", "authc");

        bean.setFilterChainDefinitionMap(filterMap);

        // 设置登录请求
        bean.setLoginUrl("/user/toLogin");

        // 设置未授权请求
        bean.setUnauthorizedUrl("/user/noauth");

        return bean;
    }

    /**
     * 第二步：默认的安全管理器
     * @param customRealm
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("customRealm") CustomRealm customRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联自定义Realm
        securityManager.setRealm(customRealm);
        return securityManager;
    }

    /**
     * 第三步：配置自定义Realm
     * @return 自定义Realm类实例
     */
    @Bean
    public CustomRealm customRealm() {

        CustomRealm customRealm = new CustomRealm();

        // 修改凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 设置加密算法为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        // 设置散列次数
        credentialsMatcher.setHashIterations(HASH_ITERATIONS);

        customRealm.setCredentialsMatcher(credentialsMatcher);

        return customRealm;
    }

    /**
     * Thymeleaf前端页面 shiro标签支持
     */
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }

}
