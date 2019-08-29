package com.autonavi.minimap.offline.service;

import android.support.annotation.Nullable;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;

public abstract class IAutoOfflineJsCallback3 implements JsFunctionCallback, IAutoOfflineJsCallback {
    public void call(@Nullable String str) {
        callback(str);
    }
}
