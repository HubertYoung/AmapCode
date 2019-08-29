package com.alipay.mobile.common.transportext.biz.diagnose.network;

public class Configuration {
    public static final String CFG_DETECT = "/detect";
    public static final String CFG_MISC = "/misc";
    public static final String CFG_TIME_OUT = "/time-out";
    public static final String CFG_VERSION = "/version";
    public static final int CONNECT_MAX = 15;
    public static final int CONNECT_MIN = 1;
    public static final int DIAGNOSE_BY_AUTO = 3;
    public static final int DIAGNOSE_BY_SYS = 2;
    public static final int DIAGNOSE_BY_USR = 1;
    public static final int HANDSHAKE_MAX = 20;
    public static final int HANDSHAKE_MIN = 1;
    public static final String IDLE = "IDLE";
    public static final int INTERVAL_MAX = 660;
    public static final int INTERVAL_MIN = 10;
    public static final int LINK_ERROR_CLOSEEXP = 5;
    public static final int LINK_ERROR_CONVERT = 9;
    public static final int LINK_ERROR_NONET = -2;
    public static final int LINK_ERROR_OK = 0;
    public static final int LINK_ERROR_OTHER = 10;
    public static final int LINK_ERROR_PROXY = 6;
    public static final int LINK_ERROR_RSP = 2;
    public static final int LINK_ERROR_SSLEXP = 4;
    public static final int LINK_ERROR_SSLPROXYEXP = 8;
    public static final int LINK_ERROR_TCPEXP = 3;
    public static final int LINK_ERROR_TCPPROXYEXP = 7;
    public static final int LINK_ERROR_VAL = 1;
    public static final int LINK_ERROR_WIFI = -1;
    public static final int LOG_DEBUG = 0;
    public static final int LOG_ERROR = 3;
    public static final int LOG_FATAL = 4;
    public static final int LOG_INF = 1;
    public static final String LOG_TYPE_DIAGNOSE = "diagnose";
    public static final String LOG_TYPE_OUT_DIAGO = "out_diago";
    public static final String LOG_TYPE_TRACEROUTE = "traceroute";
    public static final String LOG_TYPE_TRAFFIC = "traffic";
    public static final int LOG_WARN = 2;
    public static final String LOOK_BACK = "127.0.0.1";
    public static final String LVL_DEBUG = "DEBUG";
    public static final String LVL_ERROR = "ERROR";
    public static final String LVL_FATAL = "FATAL";
    public static final String LVL_INF = "INFO";
    public static final String LVL_VERBOSE = "VERBOSE";
    public static final String LVL_WARN = "WARN";
    public static final int PORT_MAX = 65535;
    public static final int PORT_MIN = 0;
    public static final int PROTO_SSL = 1;
    public static final int PROTO_TCP = 0;
    public static final String RUNNING = "RUNNING";
    public static final int TRYING_MAX = 100;
    public static final int TRYING_MIN = 0;
    public static final String VAL_CONNECT = "/connect";
    public static final String VAL_DOMAIN = "/domain";
    public static final String VAL_HANDSHAKE = "/handshake";
    public static final String VAL_INF = "/information";
    public static final String VAL_IP = "/IP";
    public static final String VAL_LOG = "/log";
    public static final String VAL_PORT = "/port";
    public static final String VAL_PROTO = "/protocol";
    public static final String VAL_REQ = "/request";
    public static final String VAL_RSP = "/response";
    public static final String VAL_TRYING = "/trying";
    public static final String VAL_WAITING = "/waiting";
    public static final int WAITING_MAX = 100;
    public static final int WAITING_MIN = 1;
    public Detect detect = null;
    public int version = -1;

    public class Address {
        public String ip = null;
        public int port = -1;
    }

    public class Detect {
        public DetectInf[] inf = null;
        public boolean on = false;
        public int sleep = -1;
    }

    public class DetectInf extends Address {
        public String domain = null;
        public int protocol = -1;
        public String request = null;
        public String response = null;
        public int trying = -1;
        public int waiting = -1;
    }
}
