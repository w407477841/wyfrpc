package com.wyf.wyfrpc.client.auto;

import com.wyf.wyfrpc.client.handlers.SimpleHandler;
import com.wyf.wyfrpc.client.properties.NettyProperties;
import com.wyf.wyfrpc.common.Const;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * netty 服务启动类
 *
 * */

@Slf4j
public class NettyBootstrapClient implements BootstrapServer {

    private NioEventLoopGroup bossGroup;

    private NettyProperties nettyProperties;

    private Channel channel;

     /**启动辅助类*/
     private   Bootstrap bootstrap=null ;

    public NettyBootstrapClient(NettyProperties nettyProperties) {
        this.nettyProperties = nettyProperties;
    }

    @Override
    public void shutdown() {

    }



    @Override
    public void start() {
        bootstrap= new Bootstrap();
        // 设置线程数为1，防止占用过多资源
        bossGroup = new NioEventLoopGroup(1);

        bootstrap.group(bossGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.SO_SNDBUF, 10485760)
                .option(ChannelOption.SO_RCVBUF, 10485760)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IdleStateHandler(9,0,0));
                        ch.pipeline().addLast(new SimpleHandler());
                    }
                });
        try {

            this.channel = bootstrap.connect(nettyProperties.getHost(),nettyProperties.getPort()).sync().channel();

            log.info("连接成功[{}]:[{}]", nettyProperties.getHost(),nettyProperties.getPort());

        } catch (Exception e) {
            log.info("connect to channel fail ",e);
        }

    }


}

