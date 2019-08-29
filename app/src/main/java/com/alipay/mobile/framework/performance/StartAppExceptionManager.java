package com.alipay.mobile.framework.performance;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class StartAppExceptionManager {
    private final Set<IStartAppExceptionListener> a = Collections.synchronizedSet(new HashSet());

    public void addListener(IStartAppExceptionListener listener) {
        this.a.add(listener);
    }

    public void onStartAppFail(String sourceAppId, String targetAppId, String errorCode) {
        for (IStartAppExceptionListener onStartAppFail : this.a) {
            onStartAppFail.onStartAppFail(sourceAppId, targetAppId, errorCode);
        }
    }

    public void onStartAppReject(String sourceAppId, String targetAppId, String errorCode) {
        for (IStartAppExceptionListener onStartAppReject : this.a) {
            onStartAppReject.onStartAppReject(sourceAppId, targetAppId, errorCode);
        }
    }
}
