package com.wyf.wyfrpc.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class Const {

    public static String DELIMITER =  "$_$";

    public static ByteBuf  DELIMITER_BYTEBUF = Unpooled.copiedBuffer(DELIMITER.getBytes());

    /** 当前连接数*/
    public static LongAdder CURRENT_LINKS = new LongAdder();


    public static Map<String, Channel> CHANNEL_MAP = new ConcurrentHashMap<>();



    public static AttributeKey<String> NETTY_CHANNEL_UUID_KEY = AttributeKey.valueOf("netty.channel.uuid");


    public static AttributeKey<String> NETTY_CHANNEL_WHO_KEY = AttributeKey.valueOf("netty.channel.who");


    public static String METHOD_PING = "ping";

    public static String METHOD_REGISTER = "register";

    public static String METHOD_CALL = "call";


}
