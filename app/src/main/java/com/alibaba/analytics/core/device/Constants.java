package com.alibaba.analytics.core.device;

import java.io.File;

public class Constants {
    public static final String COMMON_PERSISTENT_CONF_CACHE_KEY = "UTConfCache";
    public static final String COMMON_PERSISTENT_LONGLOGINUSERNICK_KEY = "longLoginUserNick";
    public static final String COMMON_PERSISTENT_TRACE_CONF_CACHE_KEY = "UTTraceConfCache";
    public static final String COMMON_PERSISTENT_TRACE_CONF_HISTORY_VERSION_CACHE_KEY = "UTTraceConfHisVersionCache";
    public static final String CONFIGURATION_URL = "http://adash.m.taobao.com/rest/gc";
    public static final int DEFAULT_TRACE_SIZE = 200;
    public static final String EXCEPTION_REPORT_URL = "http://adash.m.taobao.com/rest/er";
    public static final String FAKE_USERNICK = "BniUaBZgOpIkLWIAoept";
    public static final String GLOBAL_PERSISTENT_CONFIG_DIR;
    public static final String IS_EASY_TRACE = "_EASY_TRACE_";
    public static final int MAX_CONNECTION_TIME_OUT = 10000;
    public static final int MAX_PACKAGE_SIZE = 3072;
    public static final int MAX_PACKAGE_STORE_SIZE = 262144;
    public static final int MAX_READ_CONNECTION_STREAM_TIME_OUT = 30000;
    public static final int MAX_UPLOAD_SIZE = 10240;
    public static final String NULL_TRACE_FIELD = "-";
    public static final String PACKAGE_NAME = "com.taobao.statistic";
    public static final String PACKAGE_NAME2 = "org.usertrack";
    public static final String PERSISTENT_CONFIG_DIR = ".UTSystemConfig";
    public static final int PER_CLEAN_PACKAGE_COUNT = 50;
    public static final String[] REISSUE = {"reissue=true"};
    public static final String RESOURCE_IDENTIFIER = "";
    public static final String SEPARATOR = "||";
    public static final String SUB_SEPARATOR = ",";
    public static final int TRACES_BYTE_BUFFER_SIZE = 3072;
    public static final String TRACE_CONFIGURATION_URL = "http://adash.m.taobao.com/rest/tgc";
    public static final String UNKNOWN = "Unknown";
    public static final String UPLOAD_URL = "http://adash.m.taobao.com/rest/ur";
    public static final String USERTRACK_EXTEND_PAGE_NAME = "Page_Extend";
    public static final String USERTRACK_INIT_PAGE_NAME = "Page_UsertrackInit";
    public static final String USERTRACK_PAGE_NAME = "Page_Usertrack";
    public static final String USERTRACK_UNINIT_PAGE_NAME = "Page_UsertrackUninit";
    public static final String _IET_ = "_iet";

    static {
        StringBuilder sb = new StringBuilder(PERSISTENT_CONFIG_DIR);
        sb.append(File.separator);
        sb.append("Global");
        GLOBAL_PERSISTENT_CONFIG_DIR = sb.toString();
    }
}
