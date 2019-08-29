package com.uc.webview.export.internal.interfaces;

import com.uc.webview.export.annotations.Reflection;
import java.util.List;
import java.util.Map;

@Reflection
/* compiled from: ProGuard */
public interface IResponseData {
    Map<String, String> getHeaders();

    Map<String, List<String>> getHeadersV2();

    int getStatus();

    String getUrl();

    void setHeaders(Map<String, String> map);

    void setHeadersV2(Map<String, List<String>> map);

    void setStatus(int i);

    void setUrl(String str);
}
