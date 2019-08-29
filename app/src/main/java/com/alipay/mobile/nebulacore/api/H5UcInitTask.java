package com.alipay.mobile.nebulacore.api;

import android.os.Bundle;
import com.alipay.mobile.h5container.service.UcService;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5WebViewChoose;
import com.alipay.mobile.nebulacore.web.H5HardwarePolicy;

public class H5UcInitTask implements Runnable {
    public static final String TAG = "H5UcInitTask";
    private Bundle bundle;
    private long taskInit = System.currentTimeMillis();
    private boolean urgent;

    public H5UcInitTask(boolean urgent2, Bundle startParam) {
        this.urgent = urgent2;
        this.bundle = startParam;
    }

    public void run() {
        boolean z = false;
        if (H5WebViewChoose.notNeedInitUc(this.bundle)) {
            H5Log.d(TAG, "not need init uc");
            return;
        }
        try {
            long phase0 = System.currentTimeMillis() - this.taskInit;
            H5Log.d(TAG, "init uc task wait phase0 " + phase0 + " urgent " + this.urgent);
            UcService ucService = H5ServiceUtils.getUcService();
            long phase1 = (System.currentTimeMillis() - this.taskInit) - phase0;
            if (ucService == null) {
                H5Log.e((String) TAG, (String) "ucService == null");
                H5WebViewChoose.sendUcReceiver(false);
            } else if (Nebula.disableHWACByUCStyle()) {
                if (!H5HardwarePolicy.disableHardwareAccelerate(this.bundle, null)) {
                    z = true;
                }
                ucService.init(z);
            } else {
                ucService.init(H5HardwarePolicy.isAbove14Level());
            }
            H5Log.d(TAG, "init uc task phase1 " + phase1 + " phase2 " + (((System.currentTimeMillis() - this.taskInit) - phase0) - phase1));
        } catch (Exception e) {
            H5Log.e(TAG, "catch exception ", e);
        }
    }
}
