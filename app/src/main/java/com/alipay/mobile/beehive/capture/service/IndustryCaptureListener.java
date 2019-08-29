package com.alipay.mobile.beehive.capture.service;

import android.os.Bundle;
import com.alipay.mobile.beehive.capture.modle.MediaInfo;
import java.util.List;
import java.util.Map;

public abstract class IndustryCaptureListener implements CaptureListener {
    public abstract void onAction(boolean z, List<MediaInfo> list, Map<String, Object> map);

    public abstract void onRecorderEvent(String str, Bundle bundle);

    public void onAction(boolean isCanceled, MediaInfo mediaInfo) {
    }
}
