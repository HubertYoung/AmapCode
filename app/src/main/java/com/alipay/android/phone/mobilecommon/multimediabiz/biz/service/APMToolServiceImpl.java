package com.alipay.android.phone.mobilecommon.multimediabiz.biz.service;

import android.os.Bundle;
import com.alipay.android.phone.mobilecommon.multimedia.api.APMToolService;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools.LocalIdTool;

public class APMToolServiceImpl extends APMToolService {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }

    public String encodeToLocalId(String path) {
        return LocalIdTool.get().encodeToLocalId(path);
    }

    public String decodeToPath(String localId) {
        return LocalIdTool.get().decodeToPath(localId);
    }
}
