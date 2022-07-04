package com.myssm.listeners;

import com.myssm.ioc.BeanFactory;
import com.myssm.ioc.ClassPathXmlApplicationContext;
import com.myssm.utils.ConnUtils;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//监听上下文启动，在上下文启动的时候去创建IOC容器，
//然后将其保存到application作用域中，后面中央控制器再从application作用域去获取IOC容器
@WebListener
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String path = servletContext.getInitParameter("contextConfigLocation");
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(path);
        servletContext.setAttribute("beanFactory",beanFactory);
        ConnUtils.initDataSource();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnUtils.destoryDataSource();
    }
}
