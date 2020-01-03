package com.wyf.wyfrpc.client.auto;


import com.wyf.wyfrpc.client.properties.NettyProperties;

/**
 * 启动类接口
 *
 * @author lxr
 * @create 2017-11-18 14:05
 **/
public interface BootstrapServer {

    /**
     *  关闭
     */
    void shutdown();


    /**
     *  启动
     */
    void start();


}
