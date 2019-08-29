package com.sina.weibo.sdk.network;

import android.content.Context;
import android.os.Bundle;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public interface IRequestParam {

    public enum RequestType {
        POST,
        GET,
        DELETE,
        PATCH
    }

    public static class ValuePart<T> {
        public String mimeType;
        public T value;
    }

    void addInterceptResult(String str, Object obj);

    Map<String, byte[]> byteArrays();

    Map<String, ValuePart<File>> files();

    Context getContext();

    Bundle getExtraBundle();

    Bundle getGetBundle();

    Bundle getHeader();

    ArrayList<IRequestIntercept> getIntercept();

    Object getInterceptResult(String str);

    RequestType getMethod();

    Bundle getPostBundle();

    int getRequestTimeout();

    int getResponseTimeout();

    String getUrl();

    boolean needGzip();

    boolean needIntercept();

    void setUrl(String str);

    boolean useDefaultHost();
}
