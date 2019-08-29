package com.alipay.android.phone.mobilecommon.multimediabiz.biz.pipeline;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.adjuster.SandboxWrapper;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheMonitor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.FRWBroadcastReceiver;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.MemoryMonitor;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.security.SecuritySyncCenter;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.tools.LocalIdTool;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;

public class APMPipelineTask implements Runnable {
    public void run() {
        Logger.D("APMPipelineTask", "APMPipelineTask run.....", new Object[0]);
        try {
            long start = System.currentTimeMillis();
            SecuritySyncCenter.get().init();
            Logger.D("APMPipelineTask", "MonitorPipelineTask init", new Object[0]);
            SandboxWrapper.registerICache();
            CacheMonitor.get().startMonitor();
            Logger.P("APMPipelineTask", "CacheMonitor startMonitor", new Object[0]);
            MemoryMonitor.startMonitor();
            Logger.P("APMPipelineTask", "MemoryMonitor startMonitor", new Object[0]);
            FRWBroadcastReceiver.initOnce();
            Logger.P("APMPipelineTask", "FRWBroadcastReceiver initOnce", new Object[0]);
            LocalIdTool.get();
            Logger.P("APMPipelineTask", "APMPipelineTask run end coastTime=" + (System.currentTimeMillis() - start), new Object[0]);
        } catch (Throwable t) {
            Logger.E((String) "APMPipelineTask", t, (String) "APMPipelineTask safe run error.....", new Object[0]);
        }
    }
}
