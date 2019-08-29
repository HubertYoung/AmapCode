package com.alibaba.analytics.core.config.timestamp;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.db.Entity;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.TaskExecutor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

public class ConfigTimeStampMgr {
    private static ConfigTimeStampMgr instance;
    /* access modifiers changed from: private */
    public static Map<String, String> mKvMap = Collections.synchronizedMap(new HashMap());
    private ScheduledFuture future;
    private Runnable storeTask = new Runnable() {
        public void run() {
            Context context = Variables.getInstance().getContext();
            if (context != null) {
                ArrayList arrayList = new ArrayList(ConfigTimeStampMgr.mKvMap.size());
                for (String str : ConfigTimeStampMgr.mKvMap.keySet()) {
                    arrayList.add(new TimeStampEntity(str, (String) ConfigTimeStampMgr.mKvMap.get(str)));
                }
                Variables.getInstance().getDbMgr().clear(TimeStampEntity.class);
                Variables.getInstance().getDbMgr().insert((List<? extends Entity>) arrayList);
                return;
            }
            Logger.w((String) "storeTask.run()", "context", context);
        }
    };

    private ConfigTimeStampMgr() {
        if (Variables.getInstance().getContext() != null) {
            List<? extends Entity> find = Variables.getInstance().getDbMgr().find(TimeStampEntity.class, null, null, -1);
            if (find != null) {
                for (int i = 0; i < find.size(); i++) {
                    mKvMap.put(((TimeStampEntity) find.get(i)).namespace, ((TimeStampEntity) find.get(i)).timestamp);
                }
            }
        }
    }

    public static synchronized ConfigTimeStampMgr getInstance() {
        ConfigTimeStampMgr configTimeStampMgr;
        synchronized (ConfigTimeStampMgr.class) {
            try {
                if (instance == null) {
                    instance = new ConfigTimeStampMgr();
                }
                configTimeStampMgr = instance;
            }
        }
        return configTimeStampMgr;
    }

    public void put(String str, String str2) {
        mKvMap.put(str, str2);
        this.future = TaskExecutor.getInstance().schedule(this.future, this.storeTask, 10000);
    }

    public String get(String str) {
        String str2 = mKvMap.get(str);
        return TextUtils.isEmpty(str2) ? "0" : str2;
    }
}
