package com.uc.webview.export.internal.interfaces;

import android.webkit.ValueCallback;
import com.uc.webview.export.annotations.Interface;
import java.util.Map;

@Interface
/* compiled from: ProGuard */
public interface IWebStorage {
    void deleteAllData();

    void deleteOrigin(String str);

    void getOrigins(ValueCallback<Map> valueCallback);

    void getQuotaForOrigin(String str, ValueCallback<Long> valueCallback);

    void getUsageForOrigin(String str, ValueCallback<Long> valueCallback);

    @Deprecated
    void setQuotaForOrigin(String str, long j);
}
