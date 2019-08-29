package com.alipay.android.phone.mobilecommon.multimedia.api;

import com.alipay.android.phone.mobilecommon.multimedia.voice.data.APTTSReq;
import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class MultimediaVoiceService extends ExternalService {
    public abstract void stopCurrentTTSTask();

    public abstract void submitTTSReq(APTTSReq aPTTSReq);
}
