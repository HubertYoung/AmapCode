package com.uc.webview.export.internal.interfaces;

import com.uc.webview.export.annotations.Interface;

@Interface
/* compiled from: ProGuard */
public interface IGlobalSettings {
    public static final int SETTINGS_TYPE_BOOL = 2;
    public static final int SETTINGS_TYPE_FLOAT = 3;
    public static final int SETTINGS_TYPE_INT = 1;
    public static final int SETTINGS_TYPE_STRING = 4;

    boolean getBoolValue(String str);

    float getFloatValue(String str);

    int getIntValue(String str);

    String getStringValue(String str);

    void setBoolValue(String str, boolean z);

    void setFloatValue(String str, float f);

    void setIntValue(String str, int i);

    void setStringValue(String str, String str2);
}
