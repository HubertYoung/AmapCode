package com.alibaba.wireless.security.open.safetoken;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;

@InterfacePluginInfo(pluginName = "main")
public interface ISafeTokenComponent extends IComponent {
    byte[] decryptWithToken(String str, byte[] bArr, int i) throws SecException;

    byte[] encryptWithToken(String str, byte[] bArr, int i) throws SecException;

    byte[] getOtp(String str, int i, String[] strArr, byte[][] bArr) throws SecException;

    byte[] getOtp(String str, int i, String[] strArr, byte[][] bArr, String str2) throws SecException;

    boolean isTokenExisted(String str) throws SecException;

    void removeToken(String str) throws SecException;

    boolean saveToken(String str, String str2, String str3, int i) throws SecException;

    String signWithToken(String str, byte[] bArr, int i) throws SecException;
}
