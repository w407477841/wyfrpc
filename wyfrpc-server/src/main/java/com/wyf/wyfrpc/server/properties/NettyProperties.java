package com.wyf.wyfrpc.server.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "netty")
public class NettyProperties {

    /**复用*/
    private  boolean  reuseaddr;
    /**堆积*/
    private int backlog;
    /**接收区缓存*/
    private int revbuf;
    /**发送区缓存*/
    private int sndbuf;
    /** 立即发送*/
    private boolean tcpNodelay;
    /** 保持 */
    private boolean keepalive;
    /** 端口 */
    private int  port;
    /** 心跳*/
    private int heart;


    /**  */
    private int bossThread;
    /**  */
    private int workThread;

}
