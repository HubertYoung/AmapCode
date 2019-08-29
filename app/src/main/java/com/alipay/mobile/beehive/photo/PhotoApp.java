package com.alipay.mobile.beehive.photo;

import android.os.Bundle;
import com.alipay.mobile.beehive.photo.ui.RemotePhotoGridActivity;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.util.JumpUtil;
import com.alipay.mobile.framework.app.ActivityApplication;

public class PhotoApp extends ActivityApplication {
    public static final String TAG = "BizsdkApplication";

    public String getEntryClassName() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        PhotoLogger.debug(TAG, "onCreate " + getAppId());
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        PhotoLogger.debug(TAG, "onDestroy " + getAppId());
    }

    /* access modifiers changed from: protected */
    public void onRestart(Bundle bundle) {
        JumpUtil.startActivity(bundle, RemotePhotoGridActivity.class);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        JumpUtil.startActivity(null, RemotePhotoGridActivity.class);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        PhotoLogger.debug(TAG, "onStop " + getAppId());
    }
}
