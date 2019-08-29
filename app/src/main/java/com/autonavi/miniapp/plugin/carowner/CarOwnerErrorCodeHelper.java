package com.autonavi.miniapp.plugin.carowner;

import android.util.Pair;

public class CarOwnerErrorCodeHelper {
    private static final int MINIAPP_ERROR_CODE_ALREADY_EXIST = 12;
    private static int MINIAPP_ERROR_CODE_MAX_EXCEEDED = 13;
    private static int MINIAPP_ERROR_CODE_UNKNOWN = 99;
    private static final String MINIAPP_ERROR_MSG_ALREADY_EXIST = "车辆已存在";
    private static final String MINIAPP_ERROR_MSG_MAX_EXCEEDED = "已达车辆上限，添加失败";

    public static Pair<Integer, String> translateErrorCode(int i) {
        switch (i) {
            case 1:
                return new Pair<>(Integer.valueOf(12), MINIAPP_ERROR_MSG_ALREADY_EXIST);
            case 2:
                return new Pair<>(Integer.valueOf(MINIAPP_ERROR_CODE_MAX_EXCEEDED), MINIAPP_ERROR_MSG_MAX_EXCEEDED);
            default:
                return new Pair<>(Integer.valueOf(MINIAPP_ERROR_CODE_UNKNOWN), "未知错误，raw_error: ".concat(String.valueOf(i)));
        }
    }
}
