package com.alipay.edge.impl;

import com.autonavi.indoor.constant.MessageCode;

public class ErrorCode {
    public static int a(int i) {
        if (i < 0) {
            return i | Integer.MIN_VALUE;
        }
        if (i == 0) {
            return 0;
        }
        return (((i & MessageCode.MSG_BLE_NOT_SUPPORT) << 12) | 0 | 0) & Integer.MAX_VALUE;
    }

    public static boolean b(int i) {
        return i != 0 && ((i >> 12) & MessageCode.MSG_BLE_NOT_SUPPORT) == 401;
    }

    public static String c(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append((i >> 12) & MessageCode.MSG_BLE_NOT_SUPPORT);
        sb.append("_");
        sb.append(i & 4095);
        return sb.toString();
    }
}
