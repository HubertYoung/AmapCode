package com.alipay.android.phone.mobilecommon.multimedia.api.cache;

import android.os.Bundle;

public interface APCacheDeleteCallback {
    void onError(String str, Bundle bundle);

    void onFinish(int i, int i2, long j, long j2);

    void onProgress(int i, int i2, long j, long j2);

    void onStart(int i, long j);
}
