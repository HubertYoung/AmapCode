package com.alibaba.analytics.core.config;

import android.text.TextUtils;
import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.core.model.LogField;
import com.alibaba.analytics.utils.Logger;
import com.alibaba.analytics.utils.StringUtils;
import com.ta.audid.Constants;
import com.ut.device.UTDevice;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public class UTRealtimeConfBiz extends UTOrangeConfBiz {
    private static final int DefaultEffectiveTime = 10;
    private static UTRealtimeConfBiz s_instance;
    private int mEffectiveTime = 10;
    private int mHashcode = -1;
    private int mSample = 0;
    private Map<String, UTTopicItem> mTopicItemMap = new HashMap();

    static class UTTopicItem {
        private static final String KEY_ARG1 = "arg1";
        private static final String KEY_PG = "pg";
        private static final String KEY_TOPICID = "tp";
        private Map<String, String> mArg1TopicMap = new HashMap();
        private int mDefaultTopicId = 0;
        private Map<String, String> mPageTopicMap = new HashMap();

        private UTTopicItem() {
        }

        public int getTopicId(String str, String str2) {
            if (!StringUtils.isEmpty(str)) {
                String topicName = getTopicName(this.mPageTopicMap, str);
                if (topicName != null) {
                    return UTRealtimeConfBiz.convertStringToInt(topicName);
                }
            }
            if (!StringUtils.isEmpty(str2)) {
                String topicName2 = getTopicName(this.mArg1TopicMap, str2);
                if (topicName2 != null) {
                    return UTRealtimeConfBiz.convertStringToInt(topicName2);
                }
            }
            return this.mDefaultTopicId;
        }

        private String getTopicName(Map<String, String> map, String str) {
            if (str != null) {
                for (String next : map.keySet()) {
                    if (!next.startsWith("%") || !next.endsWith("%")) {
                        if (str.equals(next)) {
                            return map.get(next);
                        }
                    } else if (str.contains(next.substring(1, next.length() - 1))) {
                        return map.get(next);
                    }
                }
            }
            return null;
        }

        public static UTTopicItem parseJson(String str) {
            try {
                UTTopicItem uTTopicItem = new UTTopicItem();
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("tp")) {
                    uTTopicItem.mDefaultTopicId = UTRealtimeConfBiz.convertStringToInt(jSONObject.optString("tp"));
                }
                if (jSONObject.has(KEY_PG)) {
                    HashMap hashMap = new HashMap();
                    JSONObject optJSONObject = jSONObject.optJSONObject(KEY_PG);
                    if (optJSONObject != null) {
                        Iterator<String> keys = optJSONObject.keys();
                        while (keys.hasNext()) {
                            String next = keys.next();
                            hashMap.put(next, optJSONObject.optString(next));
                        }
                    }
                    uTTopicItem.mPageTopicMap = hashMap;
                }
                if (jSONObject.has(KEY_ARG1)) {
                    HashMap hashMap2 = new HashMap();
                    JSONObject optJSONObject2 = jSONObject.optJSONObject(KEY_ARG1);
                    if (optJSONObject2 != null) {
                        Iterator<String> keys2 = optJSONObject2.keys();
                        while (keys2.hasNext()) {
                            String next2 = keys2.next();
                            hashMap2.put(next2, optJSONObject2.optString(next2));
                        }
                    }
                    uTTopicItem.mArg1TopicMap = hashMap2;
                }
                return uTTopicItem;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static UTRealtimeConfBiz getInstance() {
        if (s_instance == null) {
            s_instance = new UTRealtimeConfBiz();
        }
        return s_instance;
    }

    private UTRealtimeConfBiz() {
    }

    public boolean isRealtimeClosed() {
        return Variables.getInstance().isRealtimeServiceClosed() || Variables.getInstance().isHttpService() || Variables.getInstance().isAllServiceClosed();
    }

    public boolean isRealtimeLogSampled() {
        if (isRealtimeClosed()) {
            return false;
        }
        if (Variables.getInstance().getDebugSamplingOption()) {
            return true;
        }
        if (this.mHashcode == -1) {
            String utdid = UTDevice.getUtdid(Variables.getInstance().getContext());
            if (utdid == null || utdid.equals(Constants.UTDID_INVALID)) {
                return false;
            }
            this.mHashcode = Math.abs(StringUtils.hashCode(utdid));
        }
        Logger.d((String) "", "hashcode", Integer.valueOf(this.mHashcode), "sample", Integer.valueOf(this.mSample));
        if (this.mHashcode % 10000 < this.mSample) {
            return true;
        }
        return false;
    }

    public int getEffectiveTime() {
        return this.mEffectiveTime;
    }

    public void resetRealtimeConf() {
        this.mTopicItemMap.clear();
        this.mEffectiveTime = 10;
        this.mSample = 0;
    }

    public synchronized int getTopicId(Map<String, String> map) {
        String str;
        String str2;
        String str3;
        str = "";
        try {
            if (map.containsKey(LogField.EVENTID.toString())) {
                str = map.get(LogField.EVENTID.toString());
            }
            str2 = null;
            str3 = map.containsKey(LogField.PAGE.toString()) ? map.get(LogField.PAGE.toString()) : null;
            if (map.containsKey(LogField.ARG1.toString())) {
                str2 = map.get(LogField.ARG1.toString());
            }
        }
        return getTopicId(str, str3, str2);
    }

    private int getTopicId(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str) && this.mTopicItemMap.containsKey(str)) {
            UTTopicItem uTTopicItem = this.mTopicItemMap.get(str);
            if (uTTopicItem == null) {
                return 0;
            }
            if (!TextUtils.isEmpty(str2) || !TextUtils.isEmpty(str3)) {
                return uTTopicItem.getTopicId(str2, str3);
            }
        }
        return 0;
    }

    public void onNonOrangeConfigurationArrive(String str) {
        super.onNonOrangeConfigurationArrive(str);
    }

    public synchronized void onOrangeConfigurationArrive(String str, Map<String, String> map) {
        Logger.d((String) "", "aGroupname", str, "aConfContent", map);
        resetRealtimeConf();
        for (String next : map.keySet()) {
            String str2 = map.get(next);
            if (!TextUtils.isEmpty(next) && !TextUtils.isEmpty(str2)) {
                if (next.equals("time")) {
                    int convertStringToInt = convertStringToInt(str2);
                    if (convertStringToInt >= 3 && convertStringToInt <= 20) {
                        this.mEffectiveTime = convertStringToInt;
                    }
                } else if (next.equals("sample")) {
                    int convertStringToInt2 = convertStringToInt(str2);
                    if (convertStringToInt2 >= 0 && convertStringToInt2 <= 10000) {
                        this.mSample = convertStringToInt2;
                    }
                } else {
                    UTTopicItem parseJson = UTTopicItem.parseJson(str2);
                    if (parseJson != null) {
                        this.mTopicItemMap.put(next, parseJson);
                    }
                }
            }
        }
    }

    public String[] getOrangeGroupnames() {
        return new String[]{"ut_realtime"};
    }

    /* access modifiers changed from: private */
    public static int convertStringToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            Logger.d((String) "", e);
            return 0;
        }
    }
}
