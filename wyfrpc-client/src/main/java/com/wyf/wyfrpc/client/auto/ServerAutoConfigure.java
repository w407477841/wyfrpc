package com.wyf.wyfrpc.client.auto;

import com.wyf.wyfrpc.client.properties.NettyProperties;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动化配置初始化服务
 *
 * @author wangyf
 * @create 2017-11-29 19:52
 **/
@Configuration
@ConditionalOnClass
@EnableConfigurationProperties({NettyProperties.class})
public class ServerAutoConfigure implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("进入");
        if(ServerAutoConfigure.applicationContext == null) {
            System.out.println("设置spring上下文");
            ServerAutoConfigure.applicationContext = applicationContext;
        }
    }

    /**
     * 获取applicationContext
     */

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**通过name获取 Bean.*/
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**通过class获取Bean.*/
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**通过name,以及Clazz返回指定的Bean*/
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    private static  final  int _BLACKLOG =   1024;

    private static final  int  CPU =Runtime.getRuntime().availableProcessors();

    private static final  int  SEDU_DAY =10;

    private static final  int TIMEOUT =120;

    private static final  int BUF_SIZE=10*1024*1024;


    public ServerAutoConfigure(){

    }




}
