package com.alipay.sdk.app;

import com.alipay.android.phone.mobilesdk.socketcraft.api.DefaultWebSocketClient;

public enum j {
    SUCCEEDED(9000, "处理成功"),
    FAILED(DefaultWebSocketClient.MIN_CONNECTION_TIMEOUT, "系统繁忙，请稍后再试"),
    CANCELED(6001, "用户取消"),
    NETWORK_ERROR(6002, "网络连接异常"),
    PARAMS_ERROR(4001, "参数错误"),
    DOUBLE_REQUEST(5000, "重复请求"),
    PAY_WAITTING(8000, "支付结果确认中");
    
    public int h;
    public String i;

    private j(int i2, String str) {
        this.h = i2;
        this.i = str;
    }

    private void b(int i2) {
        this.h = i2;
    }

    private int a() {
        return this.h;
    }

    private void a(String str) {
        this.i = str;
    }

    private String b() {
        return this.i;
    }

    public static j a(int i2) {
        if (i2 == 4001) {
            return PARAMS_ERROR;
        }
        if (i2 == 5000) {
            return DOUBLE_REQUEST;
        }
        if (i2 == 8000) {
            return PAY_WAITTING;
        }
        if (i2 == 9000) {
            return SUCCEEDED;
        }
        switch (i2) {
            case 6001:
                return CANCELED;
            case 6002:
                return NETWORK_ERROR;
            default:
                return FAILED;
        }
    }
}
