package com.wyf.wyfrpc.client.handlers;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.wyf.wyfrpc.common.Const;
import com.wyf.wyfrpc.common.dto.RpcDTO;
import com.wyf.wyfrpc.common.utils.SendUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.SocketAddress;

/**
 * @author : wangyifei
 * Description
 * Date: Created in 15:33 2019/3/29
 * Modified By : wangyifei
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class SimpleHandler extends SimpleChannelInboundHandler<String> {





    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {

        Channel channel =  channelHandlerContext.channel();
        //收到的原始数据串
        log.info("数据[{}]",msg);
        RpcDTO rpcDTO = JSONUtil.toBean(msg,RpcDTO.class);
        String method = rpcDTO.getMethod() ;
        if(Const.METHOD_PING.equals(method)){
            return ;
        }else if(Const.METHOD_REGISTER.equals(method)) {
            Const.CHANNEL_MAP.put(rpcDTO.getFrom(),channel);
            channel.attr(Const.NETTY_CHANNEL_WHO_KEY).set(rpcDTO.getFrom());
        }else if(Const.METHOD_CALL.equals(method)){
             if(1 == rpcDTO.getCode()){
                 // 调用方法，并返回
                 log.info("方法执行[{}]",rpcDTO.toString());
                 rpcDTO.setCode(2);
                 SendUtil.send(channel,rpcDTO);
             }
            else if(3 == rpcDTO.getCode()){
               // 调用成功
               log.info("调用成功 [{}]",rpcDTO.toString());
            }else if(5 == rpcDTO.getCode()){
                log.info("服务不在线 [{}]",rpcDTO.toString());
            }
        }
        }






    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().attr(Const.NETTY_CHANNEL_UUID_KEY).set(RandomUtil.randomString(32));
        SocketAddress socketAddress =  ctx.channel().remoteAddress() ;
        Const.CURRENT_LINKS.increment();
        log.info("[{}] 新建连接:[{}],当前总连接数:[{}]",ctx.channel().attr(Const.NETTY_CHANNEL_UUID_KEY).get(),socketAddress.toString(),Const.CURRENT_LINKS.longValue());



    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel =  ctx. channel();
        SocketAddress socketAddress =  ctx.channel().remoteAddress() ;
        Const.CURRENT_LINKS.decrement();

        String who = channel.attr(Const.NETTY_CHANNEL_WHO_KEY).get();
        if(StrUtil.isNotBlank(who)){
            Const.CHANNEL_MAP.remove(who,channel);
        }
        log.info("[{}] 断开连接:[{}],当前总连接数:[{}]",ctx.channel().attr(Const.NETTY_CHANNEL_UUID_KEY).get(),socketAddress.toString(),Const.CURRENT_LINKS.longValue());
    }

    /**
     * 事件触发后执行
     * 配合 IdleStateHandler使用
     * @param ctx
     * @param evt
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("[{}] 心跳超时:[{}]", ctx.channel().attr(Const.NETTY_CHANNEL_UUID_KEY).get(),ctx.channel().remoteAddress());
                ctx.close();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                /*写超时*/
                // System.out.println("===服务端===(WRITER_IDLE 写超时)");
            } else if (event.state() == IdleState.ALL_IDLE) {
                /*总超时*/
                // System.out.println("===服务端===(ALL_IDLE 总超时)");
            }
        }
    }

    /**
     *  异常处理
     *  打印异常，关闭连接
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("[{}] 发生异常:[{}],信息:[{}] ",ctx.channel().attr(Const.NETTY_CHANNEL_UUID_KEY).get(), ctx.channel().remoteAddress(),cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}
