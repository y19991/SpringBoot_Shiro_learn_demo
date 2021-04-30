package com.yafnds.springbootshiro.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 包名称：com.yafnds.springbootshiro.util
 * 类名称：ApplicationContextUtil
 * 类描述：bean工厂类
 * 创建人：@author y19991
 * 创建时间：2021/4/30 14:46
 */

@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    /**
     *根据bean名字获取工厂中指定的bean对象
     * @param beanName 要取出的bean的名称
     * @return bean对象
     */
    public static Object getBean(String beanName) { return context.getBean(beanName); }
}
