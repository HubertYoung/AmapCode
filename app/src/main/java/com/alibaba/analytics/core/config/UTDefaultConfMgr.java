package com.alibaba.analytics.core.config;

import com.alibaba.analytics.utils.TaskExecutor;

public class UTDefaultConfMgr extends UTBaseConfMgr {
    public void requestOnlineConfig() {
        try {
            TaskExecutor.getInstance().submit(new Runnable() {
                public void run() {
                    UTDefaultConfMgr.this.init();
                    UTDefaultConfMgr.this.dispatchLocalCacheConfigs();
                    UTBaseConfMgr.sendConfigTimeStamp("0");
                }
            });
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
