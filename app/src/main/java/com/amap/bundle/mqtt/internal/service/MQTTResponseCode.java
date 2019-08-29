package com.amap.bundle.mqtt.internal.service;

import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;

public enum MQTTResponseCode {
    SUCCEED("成功", 0),
    UNKNOWN("未知错误", -1),
    NETWORK(UserTrackerConstants.EM_NETWORK_ERROR, -2),
    NO_CONNECTION("会话未连接", -3),
    CONNECTION_CLOSED("长连接关闭", -4),
    ARGUMENT("业务方输入参数错误", -5),
    SERVER_BUSY("服务器忙", -6),
    TIMEOUT("发送超时", -7),
    INTERNAL("长连接内部错误", -8);
    
    private String name;
    private int value;

    private MQTTResponseCode(String str, int i) {
        this.value = 0;
        this.name = "";
        this.name = str;
        this.value = i;
    }

    public final int value() {
        return this.value;
    }

    public static String getDesc(int i) {
        String name2 = UNKNOWN.name();
        if (i == SUCCEED.value()) {
            return SUCCEED.name();
        }
        if (i == UNKNOWN.value()) {
            return UNKNOWN.name();
        }
        if (i == NETWORK.value()) {
            return NETWORK.name();
        }
        if (i == NO_CONNECTION.value()) {
            return NO_CONNECTION.name();
        }
        if (i == CONNECTION_CLOSED.value()) {
            return CONNECTION_CLOSED.name();
        }
        if (i == ARGUMENT.value()) {
            return ARGUMENT.name();
        }
        if (i == SERVER_BUSY.value()) {
            return SERVER_BUSY.name();
        }
        if (i == TIMEOUT.value()) {
            return TIMEOUT.name();
        }
        return i == INTERNAL.value() ? INTERNAL.name() : name2;
    }
}
