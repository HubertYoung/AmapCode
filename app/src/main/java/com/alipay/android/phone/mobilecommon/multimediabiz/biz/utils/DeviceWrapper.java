package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.DeviceConfig;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class DeviceWrapper {
    public static final String[] DEVICE_INFO = {manufacturer, model, version, String.valueOf(c)};
    public static final String TAG = "DeviceWrapper";
    private static Boolean a = null;
    private static Boolean b = null;
    private static final int c = VERSION.SDK_INT;
    public static final String manufacturer = Build.MANUFACTURER.toLowerCase();
    public static final String model = Build.MODEL.toLowerCase();
    public static final String version = VERSION.RELEASE;

    public static boolean isSamSung() {
        if (a == null) {
            a = Boolean.valueOf("samsung".equalsIgnoreCase(Build.MANUFACTURER));
        }
        return a.booleanValue();
    }

    public static boolean hasBitmapReuseablity() {
        return !isSamSung() || c != 13;
    }

    public static DeviceConfig parseDeviceConfig(String configKey, String jsonContent) {
        if (TextUtils.isEmpty(jsonContent)) {
            return null;
        }
        String destKey = "";
        Logger.P(TAG, "getDeviceSubKey jsonContent=" + jsonContent, new Object[0]);
        HashMap decodeConfigMap = CommonUtils.jsonToHashMap(jsonContent);
        if (decodeConfigMap == null || decodeConfigMap.isEmpty()) {
            return null;
        }
        List keyList = new ArrayList(decodeConfigMap.entrySet());
        if (keyList.size() > 1) {
            Collections.sort(keyList, new Comparator<Entry<String, String>>() {
                public final int compare(Entry<String, String> firstMapEntry, Entry<String, String> secondMapEntry) {
                    return firstMapEntry.getKey().compareTo(secondMapEntry.getKey());
                }
            });
        }
        String defaultVal = "";
        String defaultKey = "";
        int i = 0;
        while (true) {
            if (i >= keyList.size()) {
                break;
            }
            Entry entry = (Entry) keyList.get(i);
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            boolean ret = false;
            if (key.startsWith("0")) {
                defaultVal = val;
                defaultKey = key;
            } else {
                if (key.startsWith("1")) {
                    String jsonModel = CommonUtils.getValFromjson(val, manufacturer);
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
                    ret = parseLevelManuVer(val);
                } else if (key.startsWith("3")) {
                    ret = parseLevelVer(val);
                } else if (key.startsWith("4")) {
                    ret = parseLevelUid();
                }
                if (ret) {
                    destKey = key;
                    break;
                }
            }
            i++;
        }
        if (TextUtils.isEmpty(destKey) && !TextUtils.isEmpty(defaultKey)) {
            destKey = defaultKey;
        }
        Logger.P(TAG, "getDeviceSubKey destKey=" + destKey + ";defaultVal=" + defaultVal, new Object[0]);
        return a(destKey, defaultVal, configKey);
    }

    private static DeviceConfig a(String content, String defaultVal, String configKey) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        DeviceConfig config = new DeviceConfig(configKey);
        config.content = content;
        config.level = String.valueOf(content.charAt(0));
        config.defaultVal = defaultVal;
        config.updateTime();
        return config;
    }

    private static boolean a(String[] items, boolean bFuzzy) {
        for (String item : items) {
            if (item.contains(MetaRecord.LOG_SEPARATOR)) {
                String[] vers = item.split(MetaRecord.LOG_SEPARATOR);
                if (a(model, vers[0], bFuzzy)) {
                    for (String ver : vers) {
                        if (version.equals(ver)) {
                            return true;
                        }
                    }
                    continue;
                } else {
                    continue;
                }
            } else if (a(model, item, bFuzzy)) {
                return true;
            }
        }
        return false;
    }

    private static boolean a(String str1, String str2, boolean bFuzzy) {
        return bFuzzy ? str1.startsWith(str2) : str1.equals(str2);
    }

    public static boolean parseLevelManuVer(String contentJson) {
        String jsonMode = CommonUtils.getValFromjson(contentJson, manufacturer);
        if (TextUtils.isEmpty(jsonMode) || !jsonMode.contains(version)) {
            return false;
        }
        for (String item : jsonMode.split("\\|")) {
            if (version.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean parseLevelVer(String contentJson) {
        if (TextUtils.isEmpty(contentJson) || !contentJson.contains(version)) {
            return false;
        }
        for (String item : contentJson.split("\\|")) {
            if (version.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean parseLevelUid() {
        return true;
    }

    public static boolean isMatchDevice(String config) {
        boolean match = false;
        if (!TextUtils.isEmpty(config)) {
            config = config.toLowerCase();
            String[] split = config.split(";;");
            int length = split.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                String[] patterns = split[i].split(",");
                if (patterns.length <= 4) {
                    boolean flag = true;
                    for (int i2 = 0; i2 < patterns.length && flag; i2++) {
                        if (!TextUtils.isEmpty(patterns[i2])) {
                            flag = Pattern.compile(patterns[i2]).matcher(DEVICE_INFO[i2]).matches();
                        }
                    }
                    if (flag) {
                        match = true;
                        break;
                    }
                }
                i++;
            }
        }
        Logger.D(TAG, "isMatchDevice config: " + config + ", deviceInfo: " + Arrays.toString(DEVICE_INFO) + ", match: " + match, new Object[0]);
        return match;
    }

    public static boolean isDetectOrientationBlackList() {
        boolean z;
        if (b == null) {
            if (!"xiaomi".equalsIgnoreCase(manufacturer) || !"mi pad 3".equalsIgnoreCase(model)) {
                z = false;
            } else {
                z = true;
            }
            b = new Boolean(z);
        }
        if (b.booleanValue() || ConfigManager.getInstance().getOrientationDetectConfig().isInPadBlackList()) {
            return true;
        }
        return false;
    }
}
