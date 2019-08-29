package com.alipay.mobile.mrtc.api.wwj;

public enum StreamerErrorCode {
    STREAMER_INFO_CREATE_ROOM_SUCCESS(1, "create room success"),
    STREAMER_INFO_PUBLISH_SUCCESS(2, "publish success"),
    STREAMER_INFO_CREATE_OFFER_SUCCESS(3, "create offer success"),
    STREAMER_INFO_CREATE_ICE_SUCCESS(4, "create ice success"),
    STREAMER_INFO_SEND_OFFER_SUCCESS(5, "send offer success"),
    STREAMER_INFO_SEND_ICE_SUCCESS(6, "send ice success"),
    STREAMER_INFO_RECV_ANSWER_SUCCESS(7, "recv answer success"),
    STREAMER_INFO_RECV_ICE_SUCCESS(8, "recv ice success"),
    STREAMER_INFO_SET_ANSWER_SUCCESS(9, "set answer success"),
    STREAMER_INFO_PUSH_SUCCESS(10, "stream push success"),
    STREAMER_INFO_ON_ADD_STREAM(11, "stream added"),
    STREAMER_INFO_ON_RMV_STREAM(11, "stream removed"),
    STREAMER_INFO_GET_STAT_SUCCESS(21, "get stat success"),
    STREAMER_INFO_FRAME_SEND_SLOW(22, "low fps send"),
    STREAMER_INFO_EST_BW_LESS_THAN_200(23, "send bandwidth less than 200kbps"),
    STREAMER_INFO_EST_BW_RAISE(24, "send bandwidth raise"),
    STREAMER_INFO_EST_BW_DROP(25, "send bandwidth drop"),
    STREAMER_ERROR_CREATE_ROOM(-1, "create room fail"),
    STREAMER_ERROR_ICE_DISCONNECTED(-2, "ice disconnected"),
    STREAMER_ERROR_ICE_REMOVED(-3, "ice removed"),
    STREAMER_ERROR_ICE_ERROR(-4, "ice error");
    
    public final int nCode;
    public final String nDesc;

    private StreamerErrorCode(int code, String description) {
        this.nCode = code;
        this.nDesc = description;
    }

    public final String toString() {
        return String.valueOf(this.nCode) + ":" + this.nDesc;
    }
}
