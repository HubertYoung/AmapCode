package com.alipay.mobile.common.rpc;

public class RpcException extends RuntimeException {
    public static final int ERROR_SOURCE_CLIENT = 1;
    public static final int ERROR_SOURCE_SERVER = 2;
    public static final int INIT_ALERT = -100;
    public static final int NO_ALERT = 0;
    public static final int TOAST_ALERT = 1;
    private static final long serialVersionUID = -2875437994101380406L;
    protected int alert;
    protected int errorSource;
    protected boolean isBgRpc;
    protected int mCode;
    protected String mControl;
    protected boolean mIsControlOwn;
    protected String mMsg;
    protected String mOperationType;

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
        public static final int SERVER_APP_NO_PERMISSION_TO_ACCESS = 1005;
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

    public int getAlert() {
        return this.alert;
    }

    public void setAlert(int alert2) {
        this.alert = alert2;
    }

    public boolean isToast() {
        return this.alert == 1;
    }

    public boolean isDialog() {
        return (this.alert == 0 || this.alert == 1) ? false : true;
    }

    public boolean isSilent() {
        return this.alert == 0;
    }

    public boolean isBgRpc() {
        return this.isBgRpc;
    }

    public void setBgRpc(boolean isBgRpc2) {
        this.isBgRpc = isBgRpc2;
    }

    public RpcException(Integer code, String msg) {
        super(format(code, msg));
        this.mIsControlOwn = true;
        this.errorSource = -1;
        this.isBgRpc = false;
        this.alert = -100;
        this.mCode = code.intValue();
        this.mMsg = msg;
        setErrorSource();
    }

    public RpcException(Integer code, Throwable cause) {
        super(cause);
        this.mIsControlOwn = true;
        this.errorSource = -1;
        this.isBgRpc = false;
        this.alert = -100;
        this.mCode = code.intValue();
        setErrorSource();
    }

    public RpcException(Integer code, String msg, Throwable cause) {
        super(cause);
        this.mIsControlOwn = true;
        this.errorSource = -1;
        this.isBgRpc = false;
        this.alert = -100;
        this.mCode = code.intValue();
        this.mMsg = msg;
        setErrorSource();
    }

    public RpcException(String msg) {
        super(msg);
        this.mIsControlOwn = true;
        this.errorSource = -1;
        this.isBgRpc = false;
        this.alert = -100;
        this.mCode = 0;
        this.mMsg = msg;
    }

    public String getOperationType() {
        return this.mOperationType;
    }

    public void setOperationType(String string) {
        this.mOperationType = string;
    }

    public String getControl() {
        return this.mControl;
    }

    public void setControl(String control) {
        this.mControl = control;
    }

    public boolean isControlOwn() {
        return this.mIsControlOwn;
    }

    public void setControlOwn(boolean isControlOwn) {
        this.mIsControlOwn = isControlOwn;
    }

    public int getCode() {
        return this.mCode;
    }

    public String getMsg() {
        return this.mMsg;
    }

    public int getErrorSource() {
        return this.errorSource;
    }

    protected static String format(Integer code, String message) {
        StringBuilder str = new StringBuilder();
        str.append("RPCException: ");
        if (code != null) {
            str.append("[").append(code).append("]");
        }
        str.append(" : ");
        if (message != null) {
            str.append(message);
        }
        return str.toString();
    }

    public boolean isClientError() {
        return this.errorSource == 1;
    }

    public boolean isServerError() {
        return this.errorSource == 2;
    }

    public void setErrorSource(int errorSource2) {
        this.errorSource = errorSource2;
    }

    public boolean isNetworkUnavailableError() {
        return this.mCode == 2;
    }

    public boolean maybeNetworkUnavailableError() {
        return this.mCode > 0 && this.mCode < 1000;
    }

    public void setErrorSource() {
        if (this.mCode < 1000) {
            this.errorSource = 1;
        } else if (this.mCode > 1000) {
            this.errorSource = 2;
        } else {
            this.errorSource = -1;
        }
    }

    public void setMsg(String msg) {
        this.mMsg = msg;
    }
}
