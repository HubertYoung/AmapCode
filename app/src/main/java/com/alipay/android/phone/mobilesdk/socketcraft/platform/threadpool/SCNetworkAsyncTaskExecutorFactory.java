package com.alipay.android.phone.mobilesdk.socketcraft.platform.threadpool;

import com.alipay.android.phone.mobilesdk.socketcraft.platform.PlatformUtil;
import com.alipay.android.phone.mobilesdk.socketcraft.platform.logcat.SCLogCatUtil;

public class SCNetworkAsyncTaskExecutorFactory {
    private static final String MPAAS_THREAD_POOL_IMPL = "com.alipay.android.phone.mobilesdk.socketcraft.integrated.threadpool.MPaaSNetworkAsyncTaskExecutor";
    private static SCNetworkAsyncTaskExecutor scNetworkAsyncTaskExecutor;

    public static final SCNetworkAsyncTaskExecutor getInstance() {
        if (scNetworkAsyncTaskExecutor != null) {
            return scNetworkAsyncTaskExecutor;
        }
        synchronized (SCNetworkAsyncTaskExecutorFactory.class) {
            if (scNetworkAsyncTaskExecutor != null) {
                SCNetworkAsyncTaskExecutor sCNetworkAsyncTaskExecutor = scNetworkAsyncTaskExecutor;
                return sCNetworkAsyncTaskExecutor;
            }
            if (PlatformUtil.isAndroidMPaaSPlatform()) {
                try {
                    SCNetworkAsyncTaskExecutor sCNetworkAsyncTaskExecutor2 = (SCNetworkAsyncTaskExecutor) Class.forName(MPAAS_THREAD_POOL_IMPL).newInstance();
                    scNetworkAsyncTaskExecutor = sCNetworkAsyncTaskExecutor2;
                    return sCNetworkAsyncTaskExecutor2;
                } catch (Throwable e) {
                    SCLogCatUtil.error("SCNetworkAsyncTaskExecutorFactory", String.format("Instance class: %s error", new Object[]{MPAAS_THREAD_POOL_IMPL}), e);
                }
            }
            if (scNetworkAsyncTaskExecutor == null) {
                scNetworkAsyncTaskExecutor = new DefaultSCNetworkAsyncTaskExecutor();
            }
            SCNetworkAsyncTaskExecutor sCNetworkAsyncTaskExecutor3 = scNetworkAsyncTaskExecutor;
            return sCNetworkAsyncTaskExecutor3;
        }
    }
}
