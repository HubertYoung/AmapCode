package com.alipay.mobile.common.logging.util.config;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.strategy.LogStrategyInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LogConfigUtils {
    public static final String LEISURE = "leisure";

    public static synchronized List<String> getLeisureLogCategory() {
        List leisureList;
        synchronized (LogConfigUtils.class) {
            leisureList = new ArrayList();
            Map infos = LoggerFactory.getLogContext().getLogStrategyInfos();
            if (infos == null) {
                LoggerFactory.getTraceLogger().warn((String) "LogConfigUtils", (String) "getLogStrategyInfos is null");
            } else {
                for (Entry entry : infos.entrySet()) {
                    if (entry != null) {
                        try {
                            if (((LogStrategyInfo) entry.getValue()).getUploadEvents().contains(LEISURE)) {
                                leisureList.add(entry.getKey());
                            }
                        } catch (Throwable e) {
                            LoggerFactory.getTraceLogger().error((String) "LogConfigUtils", e);
                        }
                    }
                }
            }
        }
        return leisureList;
    }

    public static synchronized boolean uploadLeisureLogcategory(boolean isFlush) {
        boolean z = false;
        synchronized (LogConfigUtils.class) {
            List logCategorys = getLeisureLogCategory();
            if (logCategorys == null || logCategorys.isEmpty()) {
                LoggerFactory.getTraceLogger().info("LogConfigUtils", "getLeisureLogCategory is null");
            } else {
                int size = logCategorys.size();
                LoggerFactory.getTraceLogger().info("LogConfigUtils", "logCategorys size = " + size);
                for (int i = 0; i < size; i++) {
                    String logCategory = logCategorys.get(i);
                    if (!TextUtils.isEmpty(logCategory)) {
                        if (isFlush) {
                            try {
                                LoggerFactory.getLogContext().flush(logCategory, false);
                                LoggerFactory.getTraceLogger().info("LogConfigUtils", "isFlush = " + logCategory);
                            } catch (Throwable e) {
                                LoggerFactory.getTraceLogger().error((String) "LogConfigUtils", e);
                            }
                        }
                        LoggerFactory.getLogContext().uploadAfterSync(logCategory);
                        LoggerFactory.getTraceLogger().info("LogConfigUtils", "uploadAfterSync = " + logCategory);
                    }
                }
                z = true;
            }
        }
        return z;
    }
}
