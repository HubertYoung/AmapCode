package com.alipay.mobile.common.logging.api.http;

import android.content.Context;
import java.util.Map;
import org.apache.http.HttpResponse;

public abstract class BaseHttpClient {
    public abstract void closeStreamForNextExecute();

    public abstract long getRequestLength();

    public abstract int getResponseCode();

    public abstract String getResponseContent();

    public abstract long getResponseLength();

    public abstract void setContext(Context context);

    public abstract void setUrl(String str);

    public abstract HttpResponse synchronousRequestByGET(Map<String, String> map);

    public abstract HttpResponse synchronousRequestByPOST(byte[] bArr, Map<String, String> map);

    public BaseHttpClient(String url, Context context) {
    }

    public BaseHttpClient() {
    }
}
