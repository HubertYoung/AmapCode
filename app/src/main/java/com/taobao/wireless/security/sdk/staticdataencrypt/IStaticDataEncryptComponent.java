package com.taobao.wireless.security.sdk.staticdataencrypt;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.taobao.wireless.security.sdk.IComponent;

@InterfacePluginInfo(pluginName = "main")
public interface IStaticDataEncryptComponent extends IComponent {
    public static final int ALGORITHM_MAX_NUMBER = 19;
    public static final int GCRY_CIPHER_AES128 = 16;
    public static final int GCRY_CIPHER_AES192 = 17;
    public static final int GCRY_CIPHER_AES256 = 18;
    public static final int GCRY_CIPHER_ARCFOUR = 3;

    byte[] staticBinarySafeDecrypt(int i, String str, byte[] bArr);

    byte[] staticBinarySafeDecryptNoB64(int i, String str, byte[] bArr);

    byte[] staticBinarySafeEncrypt(int i, String str, byte[] bArr);

    byte[] staticBinarySafeEncryptNoB64(int i, String str, byte[] bArr);

    String staticSafeDecrypt(int i, String str, String str2);

    String staticSafeEncrypt(int i, String str, String str2);
}
