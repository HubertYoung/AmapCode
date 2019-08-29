package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.utils;

import com.alipay.android.phone.mobilecommon.multimedia.api.data.OnPermissionResultHandler;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.PermissionResult;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UCLogUtil;

public class AudioRecordOnPermissionResultHandler implements OnPermissionResultHandler {
    private static long b = 0;
    private final OnPermissionResultHandler a;

    public AudioRecordOnPermissionResultHandler(OnPermissionResultHandler handler) {
        this.a = handler;
    }

    public void onRequestPermission(PermissionResult permissionResult) {
        Logger.D("AudioRecord", "onRequestPermission result: " + permissionResult, new Object[0]);
        if (System.currentTimeMillis() - b > 300000 && permissionResult != null && !permissionResult.granted) {
            b = System.currentTimeMillis();
            UCLogUtil.UC_MM_C11(108, "user refused to record");
        }
        if (this.a != null) {
            this.a.onRequestPermission(permissionResult);
        }
    }
}
