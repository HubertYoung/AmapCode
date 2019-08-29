package com.ali.auth.third.core.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.exception.SecRuntimeException;
import com.ali.auth.third.core.model.Constants;
import com.ali.auth.third.core.model.HistoryAccount;
import com.ali.auth.third.core.service.StorageService;
import com.ali.auth.third.core.storage.a.a;
import com.ali.auth.third.core.storage.a.b;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.TreeMap;

public class CommonStorageServiceImpl implements StorageService {
    private SharedPreferences a = this.b.getSharedPreferences(Constants.TB_SESSION, 0);
    private Context b = KernelContext.getApplicationContext();
    private String c;

    public HistoryAccount findHistoryAccount(String str) {
        return null;
    }

    public String getAppKey() {
        try {
            ApplicationInfo applicationInfo = this.b.getPackageManager().getApplicationInfo(this.b.getPackageName(), 128);
            if (applicationInfo.metaData == null) {
                return null;
            }
            Object obj = applicationInfo.metaData.get("com.alibaba.app.appkey");
            if (obj == null) {
                return null;
            }
            return obj.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    public byte[] getByteArray(String str) {
        return new byte[0];
    }

    public List<HistoryAccount> getHistoryAccounts() {
        return null;
    }

    public String getUmid() {
        return this.c;
    }

    public String getValue(String str, boolean z) {
        String string = this.a.getString(str, "");
        if (TextUtils.isEmpty(string)) {
            return string;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(KernelContext.timestamp);
        return symDecrypt(string, b.a(sb.toString()));
    }

    public HistoryAccount matchHistoryAccount(String str) {
        return null;
    }

    public void putLoginHistory(HistoryAccount historyAccount, String str) {
    }

    @SuppressLint({"NewApi"})
    public void putValue(String str, String str2, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(KernelContext.timestamp);
        String symEncrypt = symEncrypt(str2, b.a(sb.toString()));
        if (VERSION.SDK_INT >= 9) {
            this.a.edit().putString(str, symEncrypt).apply();
        } else {
            this.a.edit().putString(str, symEncrypt).commit();
        }
    }

    public void removeSafeToken(String str) {
    }

    public void removeValue(String str, boolean z) {
        this.a.edit().remove(str);
    }

    public void savePublicKey(byte[] bArr) {
    }

    public void setUmid(String str) {
        this.c = str;
    }

    public String signMap(String str, TreeMap<String, String> treeMap) {
        return null;
    }

    public String symDecrypt(String str, String str2) {
        try {
            return a.b(str2, str);
        } catch (GeneralSecurityException e) {
            throw new SecRuntimeException(-2, e);
        }
    }

    public String symEncrypt(String str, String str2) {
        try {
            return a.a(str2, str);
        } catch (GeneralSecurityException e) {
            throw new SecRuntimeException(-1, e);
        }
    }
}
