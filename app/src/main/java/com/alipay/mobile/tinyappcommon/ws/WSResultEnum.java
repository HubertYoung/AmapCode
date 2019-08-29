package com.alipay.mobile.tinyappcommon.ws;

import com.alipay.android.phone.mobilesdk.socketcraft.util.WsMessageConstants;
import java.util.HashMap;
import java.util.Map;

public enum WSResultEnum {
    UNKNOW_ERROR(1, "未知错误."),
    WEBSOCKET_IS_CONNECTED_MSG(2, "当前存在一个已有的连接."),
    URL_IS_NULL_MSG(3, "URL 为空."),
    URL_NOT_WELL_FORMAT(4, "URL 格式不合法."),
    URL_NOT_WS_OR_WSS(5, "URL 地址不是 ws 或者 wss."),
    CONNECTION_TIMEOUT(6, "建连超时."),
    SSL_HANDSHAKE_ERROR(7, "SSL 握手失败."),
    INVALID_SEC_WS_ACCEPT_RESP(8, "Invalid Sec-WebSocket-Accept response."),
    SERVER_SPEC_SEC_WS_PROTO_NOT_REQ(9, "Server specified Sec-WebSocket-Protocol that wasn't requested."),
    CANNOT_SEND_UNTIL_CONNECTION_IS_OPEN(10, "没有建连成功之前无法发送消息."),
    ERROR_WRITING_TO_STREAM(11, "Error writing to stream."),
    UNABLE_ALLOC_MEM_TO_READ(12, "不能分配内存给当前的 Websocket 连接使用."),
    LAST_ITEM(100000, "$v");
    
    private static final Map<String, WSResultEnum> MSG_TO_RESULT_MAP = null;
    private int errCode;
    private String errMsg;

    static {
        HashMap hashMap = new HashMap();
        MSG_TO_RESULT_MAP = hashMap;
        hashMap.put(WsMessageConstants.MSG_PAYLOAD_SIZE_BIG, UNABLE_ALLOC_MEM_TO_READ);
        MSG_TO_RESULT_MAP.put(WsMessageConstants.MSG_PENDING_FRAME_EXPLODED, UNABLE_ALLOC_MEM_TO_READ);
        MSG_TO_RESULT_MAP.put(WsMessageConstants.MSG_CONNECTION_TIME_OUT, CONNECTION_TIMEOUT);
        MSG_TO_RESULT_MAP.put(WsMessageConstants.MSG_WEBSOCKET_NOT_CONNECTED, CANNOT_SEND_UNTIL_CONNECTION_IS_OPEN);
    }

    private WSResultEnum(int errCode2, String errMsg2) {
        this.errCode = errCode2;
        this.errMsg = errMsg2;
    }

    public final int getErrCode() {
        return this.errCode;
    }

    public final String getErrMsg() {
        return this.errMsg;
    }

    public static final WSResultEnum getResultEnumByWsMsg(String msg) {
        WSResultEnum wsResultEnum = MSG_TO_RESULT_MAP.get(msg);
        return wsResultEnum == null ? UNKNOW_ERROR : wsResultEnum;
    }
}
