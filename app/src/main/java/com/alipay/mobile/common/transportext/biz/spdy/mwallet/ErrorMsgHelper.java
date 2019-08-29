package com.alipay.mobile.common.transportext.biz.spdy.mwallet;

import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;

public final class ErrorMsgHelper {
    public static final String[] ATLS_HAND_SHAKE_FAIL = {"003", "ATLS Hand shake fail!"};
    public static final String[] BYTES_REMAIN_NO_EQ_COUNT = {"006", "expected %s bytes but received %s"};
    public static final String[] CHUNK_SIZE_IS_NOT_NUM = {"005", "Expected a hex chunk size but was %s"};
    public static final String[] CONTENT_LENGTH_NO_EQ_LIMIT = {"017", "content-length promised %s bytes, but received %s"};
    public static final String[] DOWNGRADE_TLS_RECONN_FAIL = {"002", "Downgrade TLS， reconnect fail!"};
    public static final String[] EXCEEDED_CONTENT_LENGTH_LIMIT = {"018", "exceeded content-length limit of %s bytes"};
    public static final String[] HTTP_PROXY_AUTH_407 = {"012", "Received HTTP_PROXY_AUTH (407) code while not using proxy"};
    public static final String[] METHOD_NO_SUPPORT_REQUEST_BODY = {"009", "method does not support a request body: %s"};
    public static final String[] NOT_SUPPORT_HTTP_METHOD = {"011", "%s does not support writing."};
    public static final String[] NO_RESPONSE_BODY_EXISTS = {"008", "No response body exists; responseCode=%s"};
    public static final String[] PROTOCOL_NOT_SUPPORT_INPUT = {"007", "protocol does not support input"};
    public static final String[] SOCKET_CONNECTION_FAIL = {"001", "ATLS Socket Connection Fail!"};
    public static final String[] SPDY_VERSION_ERROR = {"019", "version != 3: %s"};
    public static final String[] STATUS_HEADER_NOT_PRESENT = {"014", "Expected ':status' header not present"};
    private static final String STRING_TEMPLETE = "ErrorCode=[%s], ErrorMsg=[%s]";
    public static final String[] TLS_HAND_SHAKE_FAIL = {"020", "TLS Hand shake fail!"};
    public static final String[] TOO_MANY_REDIRECTS = {"013", "Too many redirects: %s"};
    public static final String[] UNEXPECTED_END_STREAM = {"004", "unexpected end of stream"};
    public static final String[] UNEXPECTED_STATUS_LINE = {"016", "Unexpected status line: %s"};
    public static final String[] VERSION_HEADER_NOT_PRESENT = {"015", "Expected ':version' header not present"};
    public static final String[] WRITE_REQ_BODY_AFTER_RESP = {"010", "cannot write request body after response has been read"};

    public static final String getMessage(String[] msgItem) {
        try {
            return String.format(STRING_TEMPLETE, new Object[]{msgItem[0], msgItem[1]});
        } catch (Exception e) {
            LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, (Throwable) e);
            return "";
        }
    }
}
