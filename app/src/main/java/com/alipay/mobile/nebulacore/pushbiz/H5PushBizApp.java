package com.alipay.mobile.nebulacore.pushbiz;

import android.os.Bundle;
import com.alipay.mobile.framework.app.ActivityApplication;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Bundle;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebulacore.R;
import com.alipay.mobile.nebulacore.env.H5Environment;

public class H5PushBizApp extends ActivityApplication {
    private Bundle a;

    public String getEntryClassName() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        this.a = bundle;
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        H5Service h5Service = H5ServiceUtils.getH5Service();
        if (h5Service != null) {
            H5Bundle h5Bundle = new H5Bundle();
            h5Bundle.setParams(this.a);
            h5Service.startPage(this, h5Bundle);
        }
    }

    /* access modifiers changed from: protected */
    public void onRestart(Bundle bundle) {
        H5PushBizUtil.setH5BridgeContext(null);
        destroy(null);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        H5BridgeContext h5BridgeContext = H5PushBizUtil.getH5BridgeContext();
        if (h5BridgeContext != null) {
            h5BridgeContext.sendError(11, H5Environment.getResources().getString(R.string.h5_user_cacel_operation));
        }
    }
}
