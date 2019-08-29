package com.alibaba.analytics.core;

import android.text.TextUtils;
import com.alibaba.analytics.core.Constants.LogContentKeys;
import com.alibaba.analytics.core.Constants.PrivateLogFields;
import com.alibaba.analytics.core.config.UTRealtimeConfBiz;
import com.alibaba.analytics.core.logbuilder.LogPriorityMgr;
import com.alibaba.analytics.core.model.Log;
import com.alibaba.analytics.core.model.LogField;
import com.alibaba.analytics.core.store.LogStoreMgr;
import com.alibaba.analytics.core.sync.UploadLogFromCache;
import com.alibaba.analytics.utils.Logger;
import java.util.Map;

public class LogProcessor {
    public static void process(Map<String, String> map) {
        boolean z;
        Logger.d();
        if (map != null) {
            String str = map.get(LogField.EVENTID.toString());
            if (!map.containsKey(LogContentKeys.PRIORITY)) {
                if ("2201".equalsIgnoreCase(str)) {
                    map.put(LogContentKeys.PRIORITY, "4");
                }
                if ("2202".equalsIgnoreCase(str)) {
                    map.put(LogContentKeys.PRIORITY, "6");
                }
            }
            String str2 = "3";
            if (map.containsKey(LogContentKeys.PRIORITY)) {
                str2 = map.remove(LogContentKeys.PRIORITY);
            }
            String configLogLevel = LogPriorityMgr.getInstance().getConfigLogLevel(str);
            if (!TextUtils.isEmpty(configLogLevel)) {
                str2 = configLogLevel;
            }
            if (map.containsKey(PrivateLogFields.SEND_LOG_SYNC)) {
                map.remove(PrivateLogFields.SEND_LOG_SYNC);
                z = true;
            } else {
                z = false;
            }
            int topicId = UTRealtimeConfBiz.getInstance().isRealtimeLogSampled() ? UTRealtimeConfBiz.getInstance().getTopicId(map) : 0;
            Log log = new Log(str2, null, str, map);
            if (topicId > 0) {
                Logger.d((String) "", "topicId", Integer.valueOf(topicId));
                log.setTopicId(topicId);
                UploadLogFromCache.getInstance().addLog(log);
            }
            if (z) {
                LogStoreMgr.getInstance().addLogAndSave(log);
                return;
            }
            LogStoreMgr.getInstance().add(log);
        }
    }
}
