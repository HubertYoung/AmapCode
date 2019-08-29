package com.alibaba.wireless.security.open.statickeyencrypt;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;

@InterfacePluginInfo(pluginName = "main")
public interface IStaticKeyEncryptComponent extends IComponent {
    public static final int OPEN_ALGORITHM_MAX_NUMBER = 20;
    public static final int OPEN_CIPHER_AES128 = 16;
    public static final int OPEN_CIPHER_AES192 = 17;
    public static final int OPEN_CIPHER_AES256 = 18;
    public static final int OPEN_NO_SUCH_ITEM = 2;
    public static final int OPEN_OVERRIDE_SUCCESS = 2;
    public static final int OPEN_REMOVE_FAILED = 0;
    public static final int OPEN_REMOVE_SUCCESS = 1;
    public static final int OPEN_SAVE_FAILED = 0;
    public static final int OPEN_SAVE_SUCCESS = 1;

    byte[] decrypt(int i, String str, byte[] bArr) throws SecException;

    byte[] encrypt(int i, String str, byte[] bArr) throws SecException;

    byte[] encryptSecretData(int i, String str, String str2) throws SecException;

    boolean isSecretExist(String str) throws SecException;

    int removeSecret(String str) throws SecException;

    int saveSecret(String str, byte[] bArr) throws SecException;
}
