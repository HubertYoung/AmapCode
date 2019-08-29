package com.alipay.android.phone.mobilecommon.multimedia.api.cache;

import java.util.Map;

public interface APCacheQueryCallback {
    void onQueryError(String str);

    void onQueryFinish(Map<String, APCacheResult> map);

    void onQueryProgress(int i);

    void onStartQuery();
}
