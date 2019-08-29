package com.ali.user.mobile.accountbiz.sp;

import android.content.Context;
import android.content.ContextWrapper;
import com.ali.user.mobile.encryption.DataEncryptor;
import com.ali.user.mobile.log.AliUserLog;
import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SecurityShareStore {
    static ReadWriteLock a = new ReentrantReadWriteLock();
    private static HashMap<String, String> b = new HashMap<>();

    public static String a(Context context, String str) {
        String str2 = null;
        try {
            a.readLock().lock();
            String str3 = b.get(str);
            if (str3 == null || "".equals(str3)) {
                ContextWrapper contextWrapper = new ContextWrapper(context);
                AUSharedPreferences a2 = SharedPreferencesManager.a(context, "secuitySharedDataStore", 0);
                try {
                    a.readLock().unlock();
                    a.writeLock().lock();
                    String a3 = a(a2, contextWrapper, str);
                    b.put(str, a3);
                    try {
                        a.writeLock().unlock();
                        a.readLock().lock();
                        str2 = a3;
                    } catch (Exception e) {
                        e = e;
                        str2 = a3;
                        try {
                            AliUserLog.a((String) "StackTrace", (Throwable) e);
                            a.readLock().unlock();
                            return str2;
                        } catch (Throwable th) {
                            a.readLock().unlock();
                            throw th;
                        }
                    }
                } catch (Exception e2) {
                    AliUserLog.a((String) "StackTrace", (Throwable) e2);
                    try {
                        String a4 = a(a2, contextWrapper, str);
                        b.put(str, a4);
                        str2 = a4;
                    } catch (Exception e3) {
                        AliUserLog.a((String) "StackTrace", (Throwable) e3);
                    }
                    a.writeLock().unlock();
                    a.readLock().lock();
                }
                a.readLock().unlock();
                return str2;
            }
            str2 = str3;
            a.readLock().unlock();
            return str2;
        } catch (Exception e4) {
            e = e4;
            AliUserLog.a((String) "StackTrace", (Throwable) e);
            a.readLock().unlock();
            return str2;
        } catch (Throwable th2) {
            a.writeLock().unlock();
            a.readLock().lock();
            throw th2;
        }
    }

    public static boolean a(Context context, String str, String str2) {
        boolean z = true;
        try {
            a.writeLock().lock();
            ContextWrapper contextWrapper = new ContextWrapper(context);
            AUSharedPreferences a2 = SharedPreferencesManager.a(context, "secuitySharedDataStore", 0);
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("_encrypt");
                a2.b(sb.toString(), DataEncryptor.a(contextWrapper, str2));
                a2.b();
                b.put(str, str2);
            } catch (Exception e) {
                AliUserLog.a((String) "StackTrace", (Throwable) e);
                try {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append("_encrypt");
                    a2.b(sb2.toString(), DataEncryptor.a(contextWrapper, str2));
                    a2.b();
                    b.put(str, str2);
                } catch (Exception e2) {
                    AliUserLog.a((String) "StackTrace", (Throwable) e2);
                    z = false;
                }
                if (z) {
                    if (a2.a(str)) {
                        a2.b(str);
                    }
                }
            }
            try {
                if (a2.a(str)) {
                    a2.b(str);
                    a2.b();
                }
            } catch (Exception e3) {
                e = e3;
                try {
                    AliUserLog.a((String) "StackTrace", (Throwable) e);
                    a.writeLock().unlock();
                    return z;
                } catch (Throwable th) {
                    a.writeLock().unlock();
                    throw th;
                }
            }
        } catch (Exception e4) {
            e = e4;
            z = false;
        }
        a.writeLock().unlock();
        return z;
    }

    private static String a(AUSharedPreferences aUSharedPreferences, ContextWrapper contextWrapper, String str) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("_encrypt");
        String a2 = aUSharedPreferences.a(sb.toString(), null);
        if (a2 != null) {
            return DataEncryptor.b(contextWrapper, a2);
        }
        String a3 = aUSharedPreferences.a(str, null);
        if (a3 == null) {
            return a3;
        }
        a((Context) contextWrapper, str, a3);
        return a3;
    }
}
