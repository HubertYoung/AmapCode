package com.alipay.mobile.beehive.capture.service;

import com.alipay.mobile.beehive.capture.modle.MediaInfo;

public interface CaptureListener {
    void onAction(boolean z, MediaInfo mediaInfo);
}
