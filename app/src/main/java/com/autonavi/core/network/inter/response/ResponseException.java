package com.autonavi.core.network.inter.response;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;

public class ResponseException extends Exception {
    public static final int CACHE_NO_RESULT_ERROR = 2;
    public static final int CALLBACK_ERROR = 10;
    public static final int CONNECT_TIMEOUT = 12;
    public static final int IO_EXCEPTION = 13;
    public static final int MALFORMED_URL = 14;
    public static final int NETWORK_ERROR = 1;
    public static final int REQUEST_ERROR = 3;
    public static final int SOCKET_TIMEOUT = 11;
    public int errorCode = 1;
    public Exception exception;
    public boolean isCallbackError;
    public bpk response;

    public ResponseException(String str) {
        super(str);
    }

    public static ResponseException exception2ResponseException(Exception exc) {
        if (exc instanceof ResponseException) {
            return (ResponseException) exc;
        }
        ResponseException responseException = new ResponseException(exc == null ? "unknown exception" : exc.getLocalizedMessage());
        responseException.exception = exc;
        responseException.errorCode = getErrorCodeByException(exc);
        return responseException;
    }

    private static int getErrorCodeByException(Exception exc) {
        if (exc instanceof SocketTimeoutException) {
            return 11;
        }
        if (exc instanceof ConnectTimeoutException) {
            return 12;
        }
        if (exc instanceof MalformedURLException) {
            return 14;
        }
        return exc instanceof IOException ? 13 : 1;
    }
}
