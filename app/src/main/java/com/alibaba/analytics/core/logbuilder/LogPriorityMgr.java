package com.alibaba.analytics.core.logbuilder;

import android.text.TextUtils;
import com.alibaba.analytics.core.config.SystemConfigMgr;
import com.alibaba.analytics.core.config.SystemConfigMgr.IKVChangeListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public class LogPriorityMgr implements IKVChangeListener {
    private static final String TAG_KEY = "loglevel";
    private static LogPriorityMgr instance;
    private Map<String, String> mLogLevelMap = Collections.synchronizedMap(new HashMap());

    LogPriorityMgr() {
        SystemConfigMgr.getInstance().register(TAG_KEY, this);
        onChange(TAG_KEY, SystemConfigMgr.getInstance().get(TAG_KEY));
    }

    public static synchronized LogPriorityMgr getInstance() {
        LogPriorityMgr logPriorityMgr;
        synchronized (LogPriorityMgr.class) {
            try {
                if (instance == null) {
                    instance = new LogPriorityMgr();
                }
                logPriorityMgr = instance;
            }
        }
        return logPriorityMgr;
    }

    public String getLogLevel(String str) {
        String configLogLevel = getConfigLogLevel(str);
        return !TextUtils.isEmpty(configLogLevel) ? configLogLevel : "3";
    }

    public String getConfigLogLevel(String str) {
        return this.mLogLevelMap.get(str);
    }

    public void onChange(String str, String str2) {
        this.mLogLevelMap.clear();
        if (!TextUtils.isEmpty(str2)) {
            try {
                JSONObject jSONObject = new JSONObject(str2);
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    String optString = jSONObject.optString(next);
                    if (!TextUtils.isEmpty(optString) && !TextUtils.isEmpty(optString)) {
                        this.mLogLevelMap.put(next, optString);
                    }
                }
            } catch (Throwable unused) {
            }
        }
    }
}
