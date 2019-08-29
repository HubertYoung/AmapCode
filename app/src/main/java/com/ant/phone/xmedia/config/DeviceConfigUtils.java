package com.ant.phone.xmedia.config;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.alipaylogger.Log;
import com.alipay.android.phone.falcon.ar.render.cloudconfig.DeviceConfig;
import com.alipay.android.phone.falcon.util.log.LogUtil;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class DeviceConfigUtils {
    public static final String a = Build.MANUFACTURER.toLowerCase();
    public static final String b = Build.MODEL.toLowerCase();
    public static final String c = VERSION.RELEASE;
    private static ConcurrentHashMap<String, DeviceConfig> d = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, String> e = new ConcurrentHashMap<>();

    public static DeviceConfig a(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        DeviceConfig config = d.get(key);
        if (config == null || config.needUpdate()) {
            String json = b(key, "");
            Log.i("DeviceConfigUtils", "get config from remote, json:" + json);
            DeviceConfig config2 = c(key, json);
            if (config2 == null) {
                return config2;
            }
            d.put(key, config2);
            return config2;
        }
        LogUtil.logInfo("DeviceConfigUtils", "getDeviceConfig from memory");
        return config;
    }

    private static String b(String name, String defaultValue) {
        String val = defaultValue;
        if (name == null) {
            return val;
        }
        try {
            if (e.containsKey(name)) {
                return e.get(name);
            }
            return val;
        } catch (Throwable th) {
            return defaultValue;
        }
    }

    public static void a(String name, String val) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(val)) {
            try {
                if (e != null) {
                    e.put(name, val);
                }
            } catch (Exception e2) {
            }
        }
    }

    public static void b(String key) {
        DeviceConfig config = d.get(key);
        if (config != null) {
            config.setNeedUpdate();
        }
    }

    private static DeviceConfig c(String configKey, String jsonContent) {
        if (TextUtils.isEmpty(jsonContent)) {
            return null;
        }
        String destKey = "";
        HashMap decodeConfigMap = f(jsonContent);
        if (decodeConfigMap == null || decodeConfigMap.isEmpty()) {
            return null;
        }
        List keyList = new ArrayList(decodeConfigMap.entrySet());
        if (keyList.size() > 1) {
            Collections.sort(keyList, new c());
        }
        String defaultVal = "";
        String defaultKey = "";
        int i = 0;
        while (true) {
            if (i >= keyList.size()) {
                break;
            }
            Entry entry = (Entry) keyList.get(i);
            if (entry != null) {
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();
                if (!(key == null || val == null)) {
                    boolean ret = false;
                    if (key.startsWith("0")) {
                        defaultVal = val;
                        defaultKey = key;
                    } else {
                        if (key.startsWith("1")) {
                            String jsonModel = d(val, a);
                            if (!TextUtils.isEmpty(jsonModel)) {
                                String[] items = jsonModel.split("\\|");
                                if (a(items, false)) {
                                    destKey = key;
                                    break;
                                } else if (a(items, true)) {
                                    destKey = key;
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(destKey)) {
                            break;
                        }
                        if (key.startsWith("2")) {
                            ret = c(val);
                        } else if (key.startsWith("3")) {
                            ret = e(val);
                        } else if (key.startsWith("4")) {
                            ret = true;
                        } else if (key.startsWith("5")) {
                            ret = d(val);
                        }
                        if (ret) {
                            destKey = key;
                            break;
                        }
                    }
                }
            }
            i++;
        }
        if (TextUtils.isEmpty(destKey) && !TextUtils.isEmpty(defaultKey)) {
            destKey = defaultKey;
        }
        return a(destKey, defaultVal, configKey);
    }

    private static boolean a(String str1, String str2, boolean bFuzzy) {
        return bFuzzy ? str1.startsWith(str2) : str1.equalsIgnoreCase(str2);
    }

    private static boolean a(String[] items, boolean bFuzzy) {
        for (String item : items) {
            if (item.contains(MetaRecord.LOG_SEPARATOR)) {
                String[] vers = item.split(MetaRecord.LOG_SEPARATOR);
                if (a(b, vers[0], bFuzzy)) {
                    for (String ver : vers) {
                        if (c.equals(ver)) {
                            return true;
                        }
                    }
                    continue;
                } else {
                    continue;
                }
            } else if (a(b, item, bFuzzy)) {
                return true;
            }
        }
        return false;
    }

    private static boolean c(String contentJson) {
        String jsonMode = d(contentJson, a);
        if (TextUtils.isEmpty(jsonMode) || !jsonMode.contains(c)) {
            return false;
        }
        for (String item : jsonMode.split("\\|")) {
            if (c.equals(item)) {
                return true;
            }
        }
        return false;
    }

    private static boolean d(String contentJson) {
        if (TextUtils.isEmpty(contentJson)) {
            return false;
        }
        for (String item : contentJson.split("\\|")) {
            if (a.equalsIgnoreCase(item)) {
                return true;
            }
        }
        return false;
    }

    private static boolean e(String contentJson) {
        if (TextUtils.isEmpty(contentJson) || !contentJson.contains(c)) {
            return false;
        }
        for (String item : contentJson.split("\\|")) {
            if (c.equals(item)) {
                return true;
            }
        }
        return false;
    }

    private static DeviceConfig a(String content, String defaultVal, String configKey) {
        DeviceConfig config = null;
        if (!TextUtils.isEmpty(content)) {
            config = new DeviceConfig(configKey);
            config.content = content;
            if (content.length() > 0) {
                config.level = String.valueOf(content.charAt(0));
            }
            config.defaultVal = defaultVal;
            config.updateTime();
        }
        return config;
    }

    private static HashMap<String, String> f(String json) {
        HashMap<String, String> hashMap = null;
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
            if (jsonObject != null) {
                HashMap<String, String> hashMap2 = new HashMap<>();
                try {
                    for (String key : jsonObject.keySet()) {
                        if (key != null) {
                            Object value = jsonObject.get(key);
                            if (value != null) {
                                hashMap2.put(key, value.toString());
                            }
                        }
                    }
                    hashMap = hashMap2;
                } catch (Throwable th) {
                    hashMap = hashMap2;
                }
            }
        } catch (Throwable th2) {
        }
        return hashMap;
    }

    private static String d(String json, String subKey) {
        if (TextUtils.isEmpty(json) || TextUtils.isEmpty(subKey)) {
            return null;
        }
        try {
            JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
            if (jsonObject == null) {
                return "";
            }
            for (String key : jsonObject.keySet()) {
                if (subKey.equalsIgnoreCase(key)) {
                    return jsonObject.getString(key);
                }
            }
            return "";
        } catch (Throwable th) {
            return "";
        }
    }
}
