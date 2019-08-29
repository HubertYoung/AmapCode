package com.autonavi.miniapp.plugin.util;

public class ErrorCode {
    public static final int SUCCESS = 1000;
    private static final int UserInfoErrorPrefix = 1000;

    public static class UserInfoError {
        public static final int USER_INFO_IS_NULL = 1002;
        public static final int USER_NOT_LOGIN = 1001;
    }
}
