package com.alibaba.analytics.core;

public class Constants {
    public static String G_TRANSFER_URL = "https://h-adashx.ut.taobao.com/upload";
    public static final String SDK_TYPE = "mini";

    public static class Database {
        public static final String DATABASE_NAME = "ut.db";
    }

    public static class LogContentKeys {
        public static final String PRIORITY = "_priority";
    }

    public static class LogTransferLevel {
        public static final String HIGH = "8";
        public static final String L1 = "1";
        public static final String L2 = "2";
        public static final String L4 = "4";
        public static final String L5 = "5";
        public static final String L6 = "6";
        public static final String L7 = "7";
        public static final String LOW = "0";
        public static final String NROMAL = "3";
        public static final String URGENT = "9";
    }

    public final class PrivateLogFields {
        public static final String HOST_APPKEY = "_hak";
        public static final String HOST_APPVERSION = "_hav";
        public static final String SEND_LOG_SYNC = "_sls";

        public PrivateLogFields() {
        }
    }

    public static class RealTimeDebug {
        public static final String DEBUG_API_URL = "debug_api_url";
        public static final String DEBUG_KEY = "debug_key";
        public static final String DEBUG_SAMPLING_OPTION = "debug_sampling_option";
        public static final String DEBUG_STORE = "debug_store";
    }

    public static class UT {
        public static final String TAG_SP_HTTP_TRANSFER_HOST = "http_host";
    }

    public static String getSdkType() {
        return SDK_TYPE;
    }
}
