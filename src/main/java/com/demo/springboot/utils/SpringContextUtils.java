package com.demo.springboot.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextUtils.context = context;
    }


    public static ApplicationContext getContext() {
        return context;
    }

    /**
     *   
     * 获取对象     
     * @param name  
     * @return Object 一个以所给名字注册的bean的实例  
     * @throws BeansException  
     */
    public static Object getBean(String name) throws BeansException {
        return context.getBean(name);
    }

}