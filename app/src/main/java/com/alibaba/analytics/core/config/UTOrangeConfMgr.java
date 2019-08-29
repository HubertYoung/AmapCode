package com.alibaba.analytics.core.config;

import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.selfmonitor.SelfMonitorEventDispather;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.TaskExecutor;
import com.taobao.orange.OrangeConfig;
import com.taobao.orange.OrangeConfigListenerV1;
import java.util.Map;

public class UTOrangeConfMgr extends UTBaseConfMgr {
    /* access modifiers changed from: private */
    public static final String[] ORANGE_CONFIGS = {"ut_sample", "ut_stream", "ut_bussiness", "utap_system", "ap_alarm", "ap_counter", "ap_stat", "ut_realtime"};
    public static final SelfMonitorEventDispather mMonitor = new SelfMonitorEventDispather();

    final class OrangeGetConfigsRunnable implements Runnable {
        private final int[] mSleepIntervalList = {1, 1, 2, 2, 4, 4, 8, 8};

        OrangeGetConfigsRunnable() {
        }

        public final void run() {
            String[] access$100 = UTOrangeConfMgr.ORANGE_CONFIGS;
            UTOrangeConfMgr.super.init();
            UTOrangeConfMgr.super.dispatchLocalCacheConfigs();
            UTBaseConfMgr.sendConfigTimeStamp("0");
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = i;
                for (int i4 = 0; i4 < access$100.length; i4++) {
                    if (!(access$100[i4] == null || OrangeConfig.getInstance().getConfigs(UTOrangeConfMgr.ORANGE_CONFIGS[i4]) == null)) {
                        access$100[i4] = null;
                        i3++;
                    }
                }
                if (i3 == access$100.length) {
                    break;
                }
                try {
                    Thread.sleep((long) (this.mSleepIntervalList[i2] * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i2++;
                if (i2 > this.mSleepIntervalList.length) {
                    break;
                }
                i = i3;
            }
            for (String str : access$100) {
                if (str != null) {
                    UTOrangeConfMgr.super.deleteDBConfigEntity(str);
                }
            }
        }
    }

    public void requestOnlineConfig() {
        try {
            OrangeConfig.getInstance().init(Variables.getInstance().getContext());
            TaskExecutor.getInstance().submit(new OrangeGetConfigsRunnable());
            OrangeConfig.getInstance().registerListener(ORANGE_CONFIGS, new OrangeConfigListenerV1() {
                public void onConfigUpdate(String str, boolean z) {
                    Logger.d((String) null, "aGroupname", str, "aIsCached", Boolean.valueOf(z));
                    Map configs = OrangeConfig.getInstance().getConfigs(str);
                    if (configs != null) {
                        UTOrangeConfMgr.super.updateAndDispatch(str, configs);
                        UTBaseConfMgr.sendConfigTimeStamp("2");
                    }
                }
            });
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
