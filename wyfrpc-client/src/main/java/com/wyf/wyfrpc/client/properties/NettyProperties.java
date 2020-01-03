package com.wyf.wyfrpc.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "netty")
public class NettyProperties {

    private String host;
    /** 端口 */
    private int  port;




}
