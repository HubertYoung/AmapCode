package com.alipay.mobile.security.bio.service.local.automation;

import android.content.Context;
import com.alipay.mobile.security.bio.service.local.LocalService;
import com.alipay.mobile.security.faceauth.api.YUVFrame;

public abstract class AutomationService extends LocalService {
    public abstract boolean enable();

    public abstract boolean processFrame(Context context, String str, YUVFrame yUVFrame);
}
