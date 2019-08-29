package com.alipay.mobile.common.utils.config.fmk;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NwSharedSwitchUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.http.ParseException;
import org.json.JSONObject;

public class ConfigureOperationImpl implements ConfigureOperation {
    private Map<String, String> a = Collections.emptyMap();

    public int getIntValue(ConfigureItem item) {
        return getIntValue(item, item.getIntValue());
    }

    public int getIntValue(ConfigureItem item, int defaultValue) {
        int val = defaultValue;
        String value = this.a.get(item.getConfigName());
        if (TextUtils.isEmpty(value)) {
            return val;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return val;
        }
    }

    public long getLongValue(ConfigureItem item) {
        return getLongValue(item, item.getLongValue());
    }

    public long getLongValue(ConfigureItem item, long defaultValue) {
        long val = defaultValue;
        String value = this.a.get(item.getConfigName());
        if (TextUtils.isEmpty(value)) {
            return val;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return val;
        }
    }

    public double getDoubleValue(ConfigureItem item) {
        return getDoubleValue(item, item.getDoubleValue());
    }

    public double getDoubleValue(ConfigureItem item, double defaultValue) {
        double val = defaultValue;
        String value = this.a.get(item.getConfigName());
        if (TextUtils.isEmpty(value)) {
            return val;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return val;
        }
    }

    public String getStringValue(ConfigureItem item) {
        return getStringValue(item, item.getStringValue());
    }

    public List<String> getStringValueList(ConfigureItem item, String delimiters) {
        return getStringValueList(item, item.getStringValue(), delimiters);
    }

    public List<String> getStringValueList(ConfigureItem item, String defaultValue, String delimiters) {
        String stringValue = getStringValue(item, defaultValue);
        if (TextUtils.isEmpty(stringValue)) {
            return Collections.emptyList();
        }
        return a(stringValue, delimiters);
    }

    public String getStringValue(ConfigureItem item, String defaultValue) {
        String value = this.a.get(item.getConfigName());
        return !TextUtils.isEmpty(value) ? value : defaultValue;
    }

    public boolean updateFromMapAndSave(Context context, Map<String, String> config, String sharePrefName, String sharedPrefKey) {
        if (config == null) {
            return false;
        }
        try {
            if (config.isEmpty() || !a(config)) {
                return false;
            }
            NwSharedSwitchUtil.refreshSharedSwitch(context, sharePrefName, sharedPrefKey, new JSONObject(this.a).toString());
            return true;
        } catch (Exception e) {
            LogCatUtil.error((String) "Conf_ConfigureOperation", (Throwable) e);
            return false;
        }
    }

    public boolean partialUpdateFromMapAndSave(Context context, Map<String, String> config, String sharePrefName, String sharedPrefKey) {
        if (config == null) {
            return false;
        }
        try {
            if (config.isEmpty()) {
                return false;
            }
            String origConfigStr = NwSharedSwitchUtil.getSharedSwitch(context, sharePrefName, sharedPrefKey);
            LogCatUtil.verbose("Conf_ConfigureOperation", "Before PartialUpdate:" + origConfigStr);
            Map origConfigMap = new HashMap();
            if (!TextUtils.isEmpty(origConfigStr)) {
                origConfigMap = parseObject(origConfigStr);
            }
            origConfigMap.putAll(config);
            if (!a(origConfigMap) || this.a.isEmpty()) {
                return false;
            }
            String finalSwitchStr = new JSONObject(origConfigMap).toString();
            LogCatUtil.verbose("Conf_ConfigureOperation", "After Partial Merge:" + finalSwitchStr);
            NwSharedSwitchUtil.refreshSharedSwitch(context, sharePrefName, sharedPrefKey, finalSwitchStr);
            return true;
        } catch (Exception e) {
            LogCatUtil.error((String) "Conf_ConfigureOperation", (Throwable) e);
            return false;
        }
    }

    public boolean updateFromJsonStrAndSave(Context context, String json, String sharePrefName, String sharedPrefKey) {
        try {
            if (TextUtils.isEmpty(json)) {
                LogCatUtil.warn((String) "Conf_ConfigureOperation", (String) "json value is empty!!");
                return false;
            }
            Map targetMap = NwSharedSwitchUtil.mergeMapsharedSwitch(context, sharePrefName, sharedPrefKey, parseObject(json));
            if (!a(targetMap) || this.a.isEmpty()) {
                return false;
            }
            NwSharedSwitchUtil.refreshSharedSwitch(context, sharePrefName, sharedPrefKey, new JSONObject(targetMap).toString());
            return true;
        } catch (Throwable e) {
            LogCatUtil.error((String) "Conf_ConfigureOperation", e);
            return false;
        }
    }

    public boolean updateFromSharedPref(Context context, String sharedPrefName, String sharedPrefKey) {
        try {
            String configContent = NwSharedSwitchUtil.getSharedSwitch(context, sharedPrefName, sharedPrefKey);
            if (!TextUtils.isEmpty(configContent)) {
                return a(parseObject(configContent));
            }
            LogCatUtil.info("Conf_ConfigureOperation", "No config at sharedPref. sharedPrefName=[" + sharedPrefName + "] sharedPerf=[" + sharedPrefKey + "] !");
            return false;
        } catch (Exception e) {
            LogCatUtil.error((String) "Conf_ConfigureOperation", (Throwable) e);
            return false;
        }
    }

    private boolean a(Map<String, String> configMap) {
        return mergeConfig(configMap);
    }

    public boolean isLoadedConfig() {
        return !this.a.isEmpty();
    }

    public boolean equalsString(ConfigureItem item, String value) {
        return TextUtils.equals(getStringValue(item), value);
    }

    public boolean equalsLong(ConfigureItem item, long value) {
        return getLongValue(item) == value;
    }

    public boolean equalsInt(ConfigureItem item, int value) {
        return getIntValue(item) == value;
    }

    public void setValue(ConfigureItem item, String value) {
        item.setValue(value);
        if (this.a == Collections.emptyMap()) {
            this.a = new HashMap();
        }
        this.a.remove(item.getConfigName());
        this.a.put(item.getConfigName(), value);
    }

    public boolean mergeConfig(Map<String, String> pTmpMap) {
        if (pTmpMap != null) {
            try {
                if (!pTmpMap.isEmpty()) {
                    if (this.a == Collections.emptyMap()) {
                        this.a = new HashMap(2);
                    }
                    this.a.putAll(pTmpMap);
                    return true;
                }
            } catch (Throwable e) {
                LogCatUtil.error("Conf_ConfigureOperation", "update. Parse spdy config exception.  pTmpMap=" + pTmpMap, e);
                this.a = Collections.emptyMap();
                return false;
            }
        }
        LogCatUtil.info("Conf_ConfigureOperation", "configMap is empty!");
        return false;
    }

    public void clearConfig() {
        try {
            this.a.clear();
        } catch (Throwable e) {
            LogCatUtil.warn((String) "Conf_ConfigureOperation", "mConfigMap.clear() exception : " + e.toString());
        }
    }

    private static List<String> a(String stringValue, String delimiters) {
        try {
            List operationTypeList = new ArrayList();
            if (TextUtils.isEmpty(stringValue)) {
                return operationTypeList;
            }
            StringTokenizer tokenizer = new StringTokenizer(stringValue, delimiters);
            while (tokenizer.hasMoreElements()) {
                operationTypeList.add(String.valueOf(tokenizer.nextElement()));
            }
            return operationTypeList;
        } catch (Throwable e) {
            LogCatUtil.warn((String) "Conf_ConfigureOperation", "string2List exception : " + e.toString());
            return Collections.emptyList();
        }
    }

    public Map<String, String> parseObject(String configJson) {
        try {
            JSONObject jsonObject = new JSONObject(configJson);
            Map mapConfig = new HashMap(jsonObject.length());
            Iterator keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                mapConfig.put(key, jsonObject.optString(key));
            }
            return mapConfig;
        } catch (Throwable e) {
            ParseException parseException = new ParseException(configJson);
            parseException.initCause(e);
            throw parseException;
        }
    }

    public String getStringValue(String key) {
        String value = this.a.get(key);
        return !TextUtils.isEmpty(value) ? value : "";
    }

    public Map<String, String> getAllConfig() {
        return Collections.unmodifiableMap(this.a);
    }
}
