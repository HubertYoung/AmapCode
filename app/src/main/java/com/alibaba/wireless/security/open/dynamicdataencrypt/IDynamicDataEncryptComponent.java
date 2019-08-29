package com.alibaba.wireless.security.open.dynamicdataencrypt;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;

@InterfacePluginInfo(pluginName = "main")
public interface IDynamicDataEncryptComponent extends IComponent {
    @Deprecated
    String dynamicDecrypt(String str) throws SecException;

    @Deprecated
    byte[] dynamicDecryptByteArray(byte[] bArr) throws SecException;

    byte[] dynamicDecryptByteArrayDDp(byte[] bArr) throws SecException;

    String dynamicDecryptDDp(String str) throws SecException;

    @Deprecated
    String dynamicEncrypt(String str) throws SecException;

    @Deprecated
    byte[] dynamicEncryptByteArray(byte[] bArr) throws SecException;

    byte[] dynamicEncryptByteArrayDDp(byte[] bArr) throws SecException;

    String dynamicEncryptDDp(String str) throws SecException;
}
