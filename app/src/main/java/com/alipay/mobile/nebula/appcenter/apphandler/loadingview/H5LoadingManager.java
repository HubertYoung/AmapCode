package com.alipay.mobile.nebula.appcenter.apphandler.loadingview;

import android.os.Bundle;
import com.alipay.mobile.nebula.appcenter.apphandler.H5StartAppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;

public interface H5LoadingManager {
    void exit();

    int getPageStatues();

    long getStartLoadingTime();

    boolean isPageDestroy();

    boolean isReady();

    void open(Bundle bundle, String str, String str2, H5StartAppInfo h5StartAppInfo, int i);

    void playExitAnimation();

    void sendToWebFail();

    void setPageStatue(int i);

    void update(AppInfo appInfo);
}
