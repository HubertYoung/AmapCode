package com.alipay.mobile.mrtc.api.service;

import android.os.Bundle;
import com.alipay.mobile.framework.service.ext.ExternalService;
import com.alipay.mobile.mrtc.api.widget.ARTVCView;
import com.alipay.mobile.mrtc.api.wwj.APToyMListener;
import com.alipay.mobile.mrtc.api.wwj.StreamerConfig;
import java.util.Map;

public abstract class APMultimediaToyMRTVCService extends ExternalService {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }

    public void createStreamer(StreamerConfig config) {
    }

    public void registeListener(APToyMListener listener) {
    }

    public ARTVCView createRenderView() {
        return null;
    }

    public void startStream() {
    }

    public void stopStream() {
    }

    public void showStream(String streamId, ARTVCView view) {
    }

    public Map<String, String> getLiveUrls(String machineId, String bizName, String subBiz, String sign) {
        return null;
    }

    public void switchStreamer(String streamId) {
    }
}
