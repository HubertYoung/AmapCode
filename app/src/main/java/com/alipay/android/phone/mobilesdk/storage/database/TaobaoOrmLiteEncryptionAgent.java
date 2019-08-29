package com.alipay.android.phone.mobilesdk.storage.database;

import android.content.Context;
import android.content.ContextWrapper;
import com.alipay.android.phone.mobilesdk.storage.encryption.TaobaoSecurityEncryptor;
import com.j256.ormlite.field.encrypt.OrmLiteEncryptionAgent;
import java.sql.SQLException;

public class TaobaoOrmLiteEncryptionAgent implements OrmLiteEncryptionAgent {
    private static ContextWrapper contextWrapper = null;
    private static TaobaoOrmLiteEncryptionAgent sInstance = null;

    public static synchronized TaobaoOrmLiteEncryptionAgent getInstance(Context context) {
        TaobaoOrmLiteEncryptionAgent taobaoOrmLiteEncryptionAgent;
        synchronized (TaobaoOrmLiteEncryptionAgent.class) {
            try {
                if (sInstance == null) {
                    sInstance = new TaobaoOrmLiteEncryptionAgent(context);
                }
                taobaoOrmLiteEncryptionAgent = sInstance;
            }
        }
        return taobaoOrmLiteEncryptionAgent;
    }

    private TaobaoOrmLiteEncryptionAgent(Context context) {
        if (context == null) {
            throw new RuntimeException("TaobaoOrmLiteEncryptionAgent init paramContext cannnot be null!");
        }
        contextWrapper = new ContextWrapper(context.getApplicationContext());
    }

    public byte[] encrypt(byte[] input) {
        if (input == null) {
            return null;
        }
        try {
            return TaobaoSecurityEncryptor.encrypt(contextWrapper, input);
        } catch (Exception e) {
            throw new SQLException("OrmLite encrypt byte[] fail");
        }
    }

    public byte[] decrypt(byte[] input) {
        if (input == null) {
            return null;
        }
        try {
            return TaobaoSecurityEncryptor.decrypt(contextWrapper, input);
        } catch (Exception e) {
            throw new SQLException("OrmLite decrypt byte[] fail");
        }
    }

    public String encrypt(String input) {
        if (input == null) {
            return null;
        }
        try {
            return TaobaoSecurityEncryptor.encrypt(contextWrapper, input);
        } catch (Exception e) {
            throw new SQLException("OrmLite encrypt String fail");
        }
    }

    public String decrypt(String input) {
        if (input == null) {
            return null;
        }
        try {
            return TaobaoSecurityEncryptor.decrypt(contextWrapper, input);
        } catch (Exception e) {
            throw new SQLException("OrmLite decrypt String fail");
        }
    }
}
