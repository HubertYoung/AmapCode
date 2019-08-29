package com.alipay.mobile.core.loading.impl;

import android.os.Bundle;
import com.alipay.mobile.framework.loading.LoadingPageHandler;
import com.alipay.mobile.framework.loading.LoadingView;
import java.lang.ref.WeakReference;

public class LoadingRecord {
    public boolean isStopped = false;
    public WeakReference<LoadingPage> loadingPage;
    public LoadingPageHandler loadingPageHandler;
    public LoadingView loadingView;
    public Bundle params;
    public String sourceId;
    public String targetAppId;
    public int token;

    public LoadingRecord(int token2, String sourceId2, String targetAppId2, Bundle params2, LoadingPageHandler loadingPageHandler2, LoadingView loadingView2) {
        this.token = token2;
        this.sourceId = sourceId2;
        this.targetAppId = targetAppId2;
        this.params = params2;
        this.loadingView = loadingView2;
        this.loadingPageHandler = loadingPageHandler2;
    }

    public String toString() {
        return "LoadingRecord{targetAppId='" + this.targetAppId + '\'' + ", token=" + this.token + ", isStopped=" + this.isStopped + ", sourceId='" + this.sourceId + '\'' + '}';
    }
}
