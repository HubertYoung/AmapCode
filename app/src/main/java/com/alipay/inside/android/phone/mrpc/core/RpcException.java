package com.alipay.inside.android.phone.mrpc.core;

public class RpcException extends RuntimeException {
    public static final int ERROR_SOURCE_CLIENT = 1;
    public static final int ERROR_SOURCE_SERVER = 2;
    private static final long serialVersionUID = -2875437994101380406L;
    private int errorSource;
    private int mCode;
    private String mMsg;
    private String mOperationType;

    public interface ErrorCode {
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
        public static final int CLIENT_SERIALIZER_ERROR = 20;
        public static final int CLIENT_TRANSPORT_UNAVAILABAL_ERROR = 1;
        public static final int CLIENT_UNKNOWN_ERROR = 0;
        public static final int CLIENT_USER_CHANGE_ERROR = 12;
        public static final int OK = 1000;
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
    }

    public RpcException(Integer num, String str) {
        super(format(num, str));
        this.errorSource = -1;
        this.mCode = num.intValue();
        this.mMsg = str;
        setErrorSource();
    }

    public RpcException(Integer num, Throwable th) {
        super(th);
        this.errorSource = -1;
        this.mCode = num.intValue();
        setErrorSource();
    }

    public RpcException(Integer num, String str, Throwable th) {
        super(format(num, str), th);
        this.errorSource = -1;
        this.mCode = num.intValue();
        this.mMsg = str;
        setErrorSource();
    }

    public RpcException(String str) {
        super(str);
        this.errorSource = -1;
        this.mCode = 0;
        this.mMsg = str;
    }

    public String getOperationType() {
        return this.mOperationType;
    }

    public void setOperationType(String str) {
        this.mOperationType = str;
    }

    public int getCode() {
        return this.mCode;
    }

    public String getMsg() {
        return this.mMsg;
    }

    protected static String format(Integer num, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("RPCException: ");
        if (num != null) {
            sb.append("[");
            sb.append(num);
            sb.append("]");
        }
        sb.append(" : ");
        if (str != null) {
            sb.append(str);
        }
        return sb.toString();
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

    public boolean isClientError() {
        return this.errorSource == 1;
    }

    public boolean isServerError() {
        return this.errorSource == 2;
    }
}
