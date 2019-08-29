package com.amap.bundle.network.response.exception;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.json.JSONObject;
import proguard.annotation.Keep;

@Keep
public class ServerException extends RuntimeException {
    private static final long serialVersionUID = 1;
    protected int code;
    protected JSONObject rawData;

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ExceptionType {
        Class<? extends ServerException> value();
    }

    public ServerException(int i, String str) {
        super(str);
        this.code = i;
    }

    public ServerException(int i, String str, Throwable th) {
        super(str, th);
        this.code = i;
    }

    public int getCode() {
        return this.code;
    }

    public void setRawData(JSONObject jSONObject) {
        this.rawData = jSONObject;
    }

    public JSONObject getRawData() {
        return this.rawData;
    }
}
