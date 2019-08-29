package com.alipay.mobile.common.utils.config.fmk;

import android.content.Context;
import java.util.List;
import java.util.Map;

public interface ConfigureOperation {
    void clearConfig();

    boolean equalsInt(ConfigureItem configureItem, int i);

    boolean equalsLong(ConfigureItem configureItem, long j);

    boolean equalsString(ConfigureItem configureItem, String str);

    double getDoubleValue(ConfigureItem configureItem);

    double getDoubleValue(ConfigureItem configureItem, double d);

    int getIntValue(ConfigureItem configureItem);

    int getIntValue(ConfigureItem configureItem, int i);

    long getLongValue(ConfigureItem configureItem);

    long getLongValue(ConfigureItem configureItem, long j);

    String getStringValue(ConfigureItem configureItem);

    String getStringValue(ConfigureItem configureItem, String str);

    List<String> getStringValueList(ConfigureItem configureItem, String str);

    List<String> getStringValueList(ConfigureItem configureItem, String str, String str2);

    boolean isLoadedConfig();

    boolean mergeConfig(Map<String, String> map);

    boolean partialUpdateFromMapAndSave(Context context, Map<String, String> map, String str, String str2);

    void setValue(ConfigureItem configureItem, String str);

    boolean updateFromJsonStrAndSave(Context context, String str, String str2, String str3);

    boolean updateFromMapAndSave(Context context, Map<String, String> map, String str, String str2);

    boolean updateFromSharedPref(Context context, String str, String str2);
}
