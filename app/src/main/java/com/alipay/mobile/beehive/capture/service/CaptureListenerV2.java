package com.alipay.mobile.beehive.capture.service;

import android.app.Activity;
import android.view.View;

public interface CaptureListenerV2 extends CaptureListener {
    void onLatestRecordEntryClicked(Activity activity, View view);
}
