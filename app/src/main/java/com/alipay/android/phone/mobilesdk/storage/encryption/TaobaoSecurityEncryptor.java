package com.alipay.android.phone.mobilesdk.storage.encryption;

import android.content.ContextWrapper;

public class TaobaoSecurityEncryptor {
    public static String encrypt(ContextWrapper context, String source) {
        return new UtilWX(context).Put(source, getDataContext(context));
    }

    public static String decrypt(ContextWrapper context, String encrypted) {
        return new UtilWX(context).Get(encrypted, getDataContext(context));
    }

    public static byte[] encrypt(ContextWrapper context, byte[] source) {
        return new UtilWX(context).Put(source, getDataContext(context));
    }

    public static byte[] decrypt(ContextWrapper context, byte[] encrypted) {
        return new UtilWX(context).Get(encrypted, getDataContext(context));
    }

    private static DataContext getDataContext(ContextWrapper context) {
        SsoLoginUtils.init(context);
        DataContext ctx = new DataContext();
        ctx.extData = null;
        return ctx;
    }

    public static String dynamicEncrypt(ContextWrapper context, String source) {
        SsoLoginUtils.init(context);
        return new UtilWX(context).Put(source);
    }

    public static String dynamicDecrypt(ContextWrapper context, String encrypted) {
        SsoLoginUtils.init(context);
        return new UtilWX(context).Get(encrypted);
    }

    public static byte[] dynamicEncrypt(ContextWrapper context, byte[] source) {
        SsoLoginUtils.init(context);
        return new UtilWX(context).Put(source);
    }

    public static byte[] dynamicDecrypt(ContextWrapper context, byte[] encrypted) {
        SsoLoginUtils.init(context);
        return new UtilWX(context).Get(encrypted);
    }

    public static String encrypt(ContextWrapper context, String source, String type) {
        SsoLoginUtils.init(context);
        return new UtilWX(context).EncryptData(source, type);
    }

    public static String decrypt(ContextWrapper context, String encrypted, String type) {
        SsoLoginUtils.init(context);
        return new UtilWX(context).DecryptData(encrypted, type);
    }

    public static byte[] encrypt(ContextWrapper context, byte[] source, String type) {
        SsoLoginUtils.init(context);
        return new UtilWX(context).EncryptData(source, type.getBytes());
    }

    public static byte[] decrypt(ContextWrapper context, byte[] encrypted, String type) {
        SsoLoginUtils.init(context);
        return new UtilWX(context).DecryptData(encrypted, type.getBytes());
    }
}
