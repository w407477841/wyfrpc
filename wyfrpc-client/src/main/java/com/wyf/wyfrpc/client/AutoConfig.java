package com.wyf.wyfrpc.client;

import com.wyf.wyfrpc.client.auto.NettyBootstrapClient;
import com.wyf.wyfrpc.client.properties.NettyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AutoConfig {
    @Autowired
    private NettyProperties nettyProperties;
    public static void main(String[] args) {
        SpringApplication.run(AutoConfig.class,args);

    }
    @Bean(initMethod = "start")
    NettyBootstrapClient nettyBootstrapClient(NettyProperties nettyProperties){
        return new NettyBootstrapClient(nettyProperties);
    }


}
