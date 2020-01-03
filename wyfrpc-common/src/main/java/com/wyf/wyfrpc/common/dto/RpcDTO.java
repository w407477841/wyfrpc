package com.wyf.wyfrpc.common.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author wangyifei
 */
@Data
public class RpcDTO {

        private String uuid;
        /** 类型  call / ping / register*/
        private String method;
        /** 调用者  */
        private String from;
        /** 被调用者 */
        private String to;
        /** 通信状态
         *  0 调用(发包到rpc服务)
         *  1 服务器转发（发包到对应服务）
         *  2 服务返回（发包到rpc服务）
         *  3 服务器转发（发包到调用方）
         *
         *  4 服务不可达
         * */
        private Integer code;


        private Map<String,String> data;


}
