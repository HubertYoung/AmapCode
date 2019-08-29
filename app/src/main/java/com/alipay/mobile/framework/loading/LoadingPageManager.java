package com.alipay.mobile.framework.loading;

import android.os.Bundle;
import com.alipay.mobile.framework.loading.LoadingView.Factory;

public interface LoadingPageManager {
    public static final String TAG = LoadingPageManager.class.getSimpleName();

    LoadingView findLoadingView(String str);

    Factory getDefaultLoadingViewFactory();

    void registerLoadingPageHandler(LoadingPageHandler loadingPageHandler);

    void setDefaultLoadingViewFactory(Factory factory);

    boolean startLoading(String str, String str2, Bundle bundle);

    boolean stopLoading(String str);

    void unregisterLoadingPageHandler(LoadingPageHandler loadingPageHandler);
}
