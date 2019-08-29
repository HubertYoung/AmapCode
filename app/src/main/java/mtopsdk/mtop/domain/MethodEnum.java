package mtopsdk.mtop.domain;

import com.alipay.mobile.common.transport.http.RequestMethodConstants;

public enum MethodEnum {
    GET("GET"),
    POST("POST"),
    HEAD(RequestMethodConstants.HEAD_METHOD),
    PATCH("PATCH");
    
    private String method;

    public final String getMethod() {
        return this.method;
    }

    private MethodEnum(String str) {
        this.method = str;
    }
}
