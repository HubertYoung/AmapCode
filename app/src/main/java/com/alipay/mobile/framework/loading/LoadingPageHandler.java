package com.alipay.mobile.framework.loading;

import android.app.Activity;
import android.os.Bundle;
import com.alipay.mobile.framework.loading.LoadingView.Factory;

public interface LoadingPageHandler extends Factory {
    int getPriority();

    boolean needShowLoadingPage(String str, String str2, Bundle bundle);

    void onCancelLoadingPage(Activity activity, String str, String str2, Bundle bundle);

    void onCreateLoadingPage(Activity activity, String str, String str2, Bundle bundle);

    void onFinishLoadingPage(Activity activity, String str, String str2, Bundle bundle);

    void onStopLoadingPage(Activity activity, String str, String str2, Bundle bundle);
}
