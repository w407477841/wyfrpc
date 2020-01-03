package com.wyf.wyfrpc.common.utils;

import cn.hutool.json.JSONUtil;
import com.wyf.wyfrpc.common.Const;
import com.wyf.wyfrpc.common.dto.RpcDTO;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

public class SendUtil {


    public static void send(Channel channel, RpcDTO rpcDTO){

        String json  = JSONUtil.toJsonStr(rpcDTO) + Const.DELIMITER;

        channel.writeAndFlush(Unpooled.copiedBuffer(json.getBytes()));
    }

}
