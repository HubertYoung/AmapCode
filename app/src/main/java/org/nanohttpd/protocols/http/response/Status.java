package org.nanohttpd.protocols.http.response;

import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.common.nbnet.api.NBNetStatus;
import com.alipay.mobile.h5container.api.H5ErrorCode;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.standardar.common.Util;

public enum Status implements IStatus {
    SWITCH_PROTOCOL(101, "Switching Protocols"),
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),
    PARTIAL_CONTENT(206, "Partial Content"),
    MULTI_STATUS(207, "Multi-Status"),
    REDIRECT(301, "Moved Permanently"),
    FOUND(302, "Found"),
    REDIRECT_SEE_OTHER(303, "See Other"),
    NOT_MODIFIED(304, "Not Modified"),
    TEMPORARY_REDIRECT(SecExceptionCode.SEC_ERROR_STA_ILLEGEL_KEY, "Temporary Redirect"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    REQUEST_TIMEOUT(408, "Request Timeout"),
    CONFLICT(H5ErrorCode.HTTP_CONFLICT, "Conflict"),
    GONE(H5ErrorCode.HTTP_GONE, "Gone"),
    LENGTH_REQUIRED(H5ErrorCode.HTTP_LENGTH_REQUIRED, "Length Required"),
    PRECONDITION_FAILED(H5ErrorCode.HTTP_PRECONDITION, "Precondition Failed"),
    PAYLOAD_TOO_LARGE(413, "Payload Too Large"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    RANGE_NOT_SATISFIABLE(NBNetStatus.SC_HTTP_RANGE_NOT_SATISFIABLE, "Requested Range Not Satisfiable"),
    EXPECTATION_FAILED(Util.SLAM_TAG, "Expectation Failed"),
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    INTERNAL_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    UNSUPPORTED_HTTP_VERSION(505, "HTTP Version Not Supported");
    
    private final int a;
    private final String b;

    private Status(int requestStatus, String description) {
        this.a = requestStatus;
        this.b = description;
    }

    public static Status lookup(int requestStatus) {
        Status[] values;
        for (Status status : values()) {
            if (status.getRequestStatus() == requestStatus) {
                return status;
            }
        }
        return null;
    }

    public final String getDescription() {
        return this.a + Token.SEPARATOR + this.b;
    }

    public final int getRequestStatus() {
        return this.a;
    }
}
