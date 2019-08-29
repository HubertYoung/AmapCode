package com.taobao.wireless.security.sdk.dynamicdatastore;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.taobao.wireless.security.sdk.IComponent;

@InterfacePluginInfo(pluginName = "main")
public interface IDynamicDataStoreComponent extends IComponent {
    @Deprecated
    boolean getBoolean(String str);

    @Deprecated
    byte[] getByteArray(String str);

    @Deprecated
    byte[] getByteArrayDDp(String str);

    byte[] getByteArrayDDpEx(String str, int i);

    @Deprecated
    float getFloat(String str);

    @Deprecated
    int getInt(String str);

    @Deprecated
    long getLong(String str);

    @Deprecated
    String getString(String str);

    @Deprecated
    String getStringDDp(String str);

    String getStringDDpEx(String str, int i);

    @Deprecated
    int putBoolean(String str, boolean z);

    @Deprecated
    int putByteArray(String str, byte[] bArr);

    @Deprecated
    int putByteArrayDDp(String str, byte[] bArr);

    boolean putByteArrayDDpEx(String str, byte[] bArr, int i);

    @Deprecated
    int putFloat(String str, float f);

    @Deprecated
    int putInt(String str, int i);

    @Deprecated
    int putLong(String str, long j);

    @Deprecated
    int putString(String str, String str2);

    @Deprecated
    int putStringDDp(String str, String str2);

    boolean putStringDDpEx(String str, String str2, int i);

    @Deprecated
    void removeBoolean(String str);

    @Deprecated
    void removeByteArray(String str);

    @Deprecated
    void removeByteArrayDDp(String str);

    void removeByteArrayDDpEx(String str, int i);

    @Deprecated
    void removeFloat(String str);

    @Deprecated
    void removeInt(String str);

    @Deprecated
    void removeLong(String str);

    @Deprecated
    void removeString(String str);

    @Deprecated
    void removeStringDDp(String str);

    void removeStringDDpEx(String str, int i);
}
