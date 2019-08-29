package com.alipay.mobile.mrtc.api.service;

import android.os.Bundle;
import com.alipay.mobile.framework.service.ext.ExternalService;
import com.alipay.mobile.mrtc.api.APCallListener;
import com.alipay.mobile.mrtc.api.APCalleeInfo;
import com.alipay.mobile.mrtc.api.APCallerInfo;
import com.alipay.mobile.mrtc.api.widget.ARTVCView;

public abstract class APMultimediaARTVCService extends ExternalService {
    public abstract ARTVCView getLocalView();

    public abstract ARTVCView getRemoteView();

    public abstract void hangup();

    public abstract void joinCall(APCalleeInfo aPCalleeInfo, APCallListener aPCallListener, Bundle bundle);

    public abstract void makeCall(APCallerInfo aPCallerInfo, APCallListener aPCallListener, Bundle bundle);

    public abstract void pause();

    public abstract void resume();

    public abstract void switchCamera();

    public abstract void switchSpeaker(boolean z);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }
}
