package com.taobao.wireless.security.sdk.safetoken;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.taobao.wireless.security.sdk.IComponent;

@InterfacePluginInfo(pluginName = "main")
public interface ISafeTokenComponent extends IComponent {
    byte[] decryptWithToken(String str, byte[] bArr, int i);

    byte[] encryptWithToken(String str, byte[] bArr, int i);

    byte[] getOtp(String str, int i, String[] strArr, byte[][] bArr);

    boolean isTokenExisted(String str);

    void removeToken(String str);

    boolean saveToken(String str, String str2, String str3, int i);

    String signWithToken(String str, byte[] bArr, int i);
}
