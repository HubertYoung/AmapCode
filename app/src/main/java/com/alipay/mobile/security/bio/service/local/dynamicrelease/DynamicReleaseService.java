package com.alipay.mobile.security.bio.service.local.dynamicrelease;

import android.content.Context;
import com.alipay.mobile.security.bio.service.local.LocalService;
import com.alipay.mobile.security.bio.utils.BioLog;

public abstract class DynamicReleaseService extends LocalService {
    public abstract void trigDynamicRelease(Context context, String str);

    public void monitorCoverage(String str, String str2) {
        BioLog.d("DynamicReleaseService.monitorCoverage(bundleName=" + str + ", bizCode=" + str2 + ")");
    }
}
