package com.uc.webview.export.internal.interfaces;

import com.uc.webview.export.annotations.Reflection;
import java.util.Map;

@Reflection
/* compiled from: ProGuard */
public interface INetwork {
    public static final int ALINETWORK = 1;
    public static final int THIRDNETWORK = 2;
    public static final int UCNETWORK = 0;

    void cancelPrefetchLoad();

    void clearUserSslPrefTable();

    IRequest formatRequest(EventHandler eventHandler, String str, String str2, boolean z, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, Map<String, byte[]> map4, long j, int i, int i2);

    int getNetworkType();

    String getVersion();

    boolean requestURL(EventHandler eventHandler, String str, String str2, boolean z, Map<String, String> map, Map<String, String> map2, Map<String, String> map3, Map<String, byte[]> map4, long j, int i, int i2);

    boolean sendRequest(IRequest iRequest);
}
