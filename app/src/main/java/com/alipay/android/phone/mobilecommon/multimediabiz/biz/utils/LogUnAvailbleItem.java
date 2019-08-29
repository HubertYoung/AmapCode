package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import java.util.HashMap;
import java.util.Map.Entry;

public class LogUnAvailbleItem {
    public static final String EXTRA_KEY_EXTEND = "extend";
    public static final String EXTRA_KEY_RESULT = "result";
    public static final String EXTRA_KEY_SIZE = "size";
    public static final String EXTRA_KEY_SUBTYPE = "subtype";
    public static final String EXTRA_KEY_TIME = "time";
    public static final String MTBIZ_MEDIA = "BIZ_MEDIA";
    public static final String SUB_ASHMEM = "11_0";
    public static final String SUB_COLLECT_AR = "0_0";
    public static final String SUB_COLLECT_PIC = "0_1";
    public static final String SUB_COLLECT_VR = "0_2";
    public static final String SUB_CP_AD = "1_2";
    public static final String SUB_CP_PIC = "1_0";
    public static final String SUB_CP_VD = "1_1";
    public static final String SUB_DOWNLOAD = "3_0";
    public static final String SUB_PLAY_AD = "4_0";
    public static final String SUB_PLAY_VD = "4_1";
    public static final String SUB_SO_FAIL = "10_0";
    public static final String SUB_UPLOAD = "2_0";
    public static final String SUCCESS = "0";
    private HashMap<String, String> a;
    public String mCode = "0";
    public int mCount = 0;
    public long mFirstTime = 0;
    public int mMinCount = 20;
    public long mMinTime = 86400000;
    public String mSubName;

    public LogUnAvailbleItem(String subName, String code) {
        this.mSubName = subName;
        this.mCode = code;
    }

    public boolean checkUnAvailble() {
        return this.mCount >= this.mMinCount && Math.abs(System.currentTimeMillis() - this.mFirstTime) >= this.mMinTime;
    }

    public synchronized void putToExtra(String key, String value) {
        if (this.a == null) {
            this.a = new HashMap<>();
        }
        HashMap<String, String> hashMap = this.a;
        if (value == null) {
            value = "";
        }
        hashMap.put(key, value);
    }

    public synchronized HashMap<String, String> getExtra() {
        return this.a;
    }

    public void reset() {
        this.mCount = 0;
        this.mCode = "0";
        this.mFirstTime = 0;
    }

    public void parseConfig(String config) {
        if (!TextUtils.isEmpty(config) && config.contains("_")) {
            try {
                String[] datas = config.split("\\_");
                this.mMinTime = 86400000 * ((long) Integer.valueOf(datas[0]).intValue());
                this.mMinCount = Integer.valueOf(datas[1]).intValue();
            } catch (Exception e) {
            }
        }
    }

    public static boolean isNonSensitive(String subType) {
        return CompareUtils.in(subType, "DownloadImage", "DownloadFile", "DownloadVoice", "DownloadVideo");
    }

    public String convertToJson() {
        JSONObject js = new JSONObject();
        js.put((String) "firstTime", (Object) Long.valueOf(this.mFirstTime));
        js.put((String) NewHtcHomeBadger.COUNT, (Object) Integer.valueOf(this.mCount));
        js.put((String) "subName", (Object) this.mSubName);
        js.put((String) "code", (Object) this.mCode);
        if (getExtra() != null) {
            for (Entry entry : getExtra().entrySet()) {
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();
                if (!TextUtils.isEmpty(key)) {
                    if (val == null) {
                        val = "";
                    }
                    js.put(key, (Object) val);
                }
            }
        }
        return js.toJSONString();
    }

    public static LogUnAvailbleItem convertToItem(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        LogUnAvailbleItem item = null;
        try {
            JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
            LogUnAvailbleItem item2 = new LogUnAvailbleItem(jsonObject.getString("subName"), jsonObject.getString("code"));
            try {
                item2.mCount = jsonObject.getIntValue(NewHtcHomeBadger.COUNT);
                item2.mFirstTime = jsonObject.getLongValue("firstTime");
                item2.putToExtra(EXTRA_KEY_SUBTYPE, jsonObject.getString(EXTRA_KEY_SUBTYPE));
                item2.putToExtra("result", jsonObject.getString("result"));
                item2.putToExtra("size", jsonObject.getString("size"));
                item2.putToExtra("time", jsonObject.getString("time"));
                item2.putToExtra(EXTRA_KEY_EXTEND, jsonObject.getString(EXTRA_KEY_EXTEND));
                return item2;
            } catch (Exception e) {
                e = e;
                item = item2;
                Logger.E((String) "LogUnAvailbleItem", (Throwable) e, (String) "convertToItem exp", new Object[0]);
                return item;
            }
        } catch (Exception e2) {
            e = e2;
            Logger.E((String) "LogUnAvailbleItem", (Throwable) e, (String) "convertToItem exp", new Object[0]);
            return item;
        }
    }

    public String toString() {
        return "LogUnAvailbleInfo=[mFirstTime" + this.mFirstTime + ";mCount=" + this.mCount + ";mSubName=" + this.mSubName + ";mCode=" + this.mCode + ";mMinCount=" + this.mMinCount + ";mMinTime=" + this.mMinTime + "]";
    }
}
