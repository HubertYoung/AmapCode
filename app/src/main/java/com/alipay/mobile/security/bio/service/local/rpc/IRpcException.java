package com.alipay.mobile.security.bio.service.local.rpc;

public interface IRpcException {

    public interface ErrorCode {
        public static final int BIZ_INTERCEPTOR_HANDLE_ERROR = 21;
        public static final int CLIENT_DESERIALIZER_ERROR = 10;
        public static final int CLIENT_HANDLE_ERROR = 9;
        public static final int CLIENT_INTERUPTED_ERROR = 13;
        public static final int CLIENT_LOGIN_FAIL_ERROR = 11;
        public static final int CLIENT_NETWORK_AUTH_ERROR = 15;
        public static final int CLIENT_NETWORK_CACHE_ERROR = 14;
        public static final int CLIENT_NETWORK_CONNECTION_ERROR = 4;
        public static final int CLIENT_NETWORK_DNS_ERROR = 16;
        public static final int CLIENT_NETWORK_ERROR = 7;
        public static final int CLIENT_NETWORK_SCHEDULE_ERROR = 8;
        public static final int CLIENT_NETWORK_SERVER_ERROR = 6;
        public static final int CLIENT_NETWORK_SOCKET_ERROR = 5;
        public static final int CLIENT_NETWORK_SSL_ERROR = 3;
        public static final int CLIENT_NETWORK_UNAVAILABLE_ERROR = 2;
        public static final int CLIENT_NOTIN_WHITELIST = 17;
        public static final int CLIENT_REQ_OVERSIZE_ERROR = 22;
        public static final int CLIENT_RES_OVERSIZE_ERROR = 23;
        public static final int CLIENT_SERIALIZER_ERROR = 20;
        public static final int CLIENT_TRANSPORT_UNAVAILABAL_ERROR = 1;
        public static final int CLIENT_UNKNOWN_ERROR = 0;
        public static final int CLIENT_USER_CHANGE_ERROR = 12;
        public static final int LOGIN_REFRESH_ERROR = 24;
        public static final int NETWORK_AUTH_WIFI = 19;
        public static final int NETWORK_TRAFIC_BEYOND_LIMIT = 18;
        public static final int OK = 1000;
        public static final int PRC_APIVERSION_EMPTY = 7010;
        public static final int PRC_DID_EMPTY = 7006;
        public static final int PRC_IMEI_EMPTY = 7008;
        public static final int PRC_IMSI_EMPTY = 7009;
        public static final int PRC_NO_AUTHORIZED = 7011;
        public static final int PRC_NO_PUBLISHED = 7012;
        public static final int PRC_OPERATIONTYPE_EMPTY = 7004;
        public static final int PRC_PRODCUTID_EMPTY = 7005;
        public static final int PRC_REQUESTTIME_EMPTY = 7007;
        public static final int PRC_SECRET_EMPTY = 7013;
        public static final int PRC_SID_EMPTY = 7016;
        public static final int PRC_SID_INVALID = 7017;
        public static final int PRC_SIGN_EMPTY = 7014;
        public static final int PRC_SIGN_INVALID = 7015;
        public static final int PRC_TOKEN_ALIPAYUSER_EMPTY = 7019;
        public static final int PRC_TOKEN_INVALID = 7018;
        public static final int PUBLIC_KEY_NOT_FOUND = 7000;
        public static final int RESPONSE_DATA_NOT_MODIFIED = 8001;
        public static final int SERVER_BIZEXCEPTION = 6666;
        public static final int SERVER_CREATEPROXYERROR = 4003;
        public static final int SERVER_ILLEGALACCESS = 6003;
        public static final int SERVER_ILLEGALARGUMENT = 6005;
        public static final int SERVER_INVOKEEXCEEDLIMIT = 1002;
        public static final int SERVER_JSONPARSEREXCEPTION = 6004;
        public static final int SERVER_METHODNOTFOUND = 6001;
        public static final int SERVER_OPERATIONTYPEMISSED = 3000;
        public static final int SERVER_PARAMMISSING = 6002;
        public static final int SERVER_PERMISSIONDENY = 1001;
        public static final int SERVER_REMOTEACCESSEXCEPTION = 4002;
        public static final int SERVER_REQUESTDATAMISSED = 3001;
        public static final int SERVER_REQUESTTIMEOUT = 4001;
        public static final int SERVER_SERVICENOTFOUND = 6000;
        public static final int SERVER_SESSIONSTATUS = 2000;
        public static final int SERVER_UNKNOWERROR = 5000;
        public static final int SERVER_VALUEINVALID = 3002;
        public static final int SIGNA_PARAM_MISSING = 7001;
        public static final int SIGN_TIME_STAMP_ERROR = 7003;
        public static final int SIGN_VERIFY_FAILED = 7002;
    }

    int getCode();

    String getMsg();
}
