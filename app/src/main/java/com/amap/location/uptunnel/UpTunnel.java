package com.amap.location.uptunnel;

import android.content.Context;
import com.amap.location.uptunnel.a.a;
import com.amap.location.uptunnel.a.b;
import com.amap.location.uptunnel.core.c;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.json.JSONArray;
import org.json.JSONObject;

public class UpTunnel {
    private static final int MAX_DATA_SIZE = 400000;
    private static final String TAG = "UpTunnel";
    private static final ReentrantReadWriteLock mLock = new ReentrantReadWriteLock();
    private static c mUpTunnelManager = null;
    public static boolean sUseTestNet = false;

    private UpTunnel() {
    }

    public static void init(Context context, ConfigContainer configContainer) {
        try {
            mLock.writeLock().lock();
            if (mUpTunnelManager == null) {
                mUpTunnelManager = new c(context.getApplicationContext(), configContainer);
            }
        } finally {
            mLock.writeLock().unlock();
        }
    }

    public static void addCount(int i) {
        try {
            mLock.readLock().lock();
            if (mUpTunnelManager != null) {
                mUpTunnelManager.a(i);
            }
        } finally {
            mLock.readLock().unlock();
        }
    }

    public static void reportEvent(int i, byte[] bArr) {
        if (bArr != null && bArr.length != 0 && bArr.length < MAX_DATA_SIZE) {
            try {
                mLock.readLock().lock();
                if (mUpTunnelManager != null) {
                    mUpTunnelManager.a(i, bArr);
                }
            } finally {
                mLock.readLock().unlock();
            }
        }
    }

    public static void reportBlockData(int i, byte[] bArr) {
        if (bArr != null && bArr.length != 0 && bArr.length < MAX_DATA_SIZE) {
            try {
                mLock.readLock().lock();
                if (mUpTunnelManager != null) {
                    mUpTunnelManager.b(i, bArr);
                }
            } finally {
                mLock.readLock().unlock();
            }
        }
    }

    public static void reportKeyLog(int i, byte[] bArr) {
        if (bArr != null && bArr.length != 0 && bArr.length < MAX_DATA_SIZE) {
            try {
                mLock.readLock().lock();
                if (mUpTunnelManager != null) {
                    mUpTunnelManager.c(i, bArr);
                }
            } finally {
                mLock.readLock().unlock();
            }
        }
    }

    public static void reportLog(int i, String str) {
        if (str != null && str.length() != 0 && str.length() * 2 < MAX_DATA_SIZE) {
            try {
                mLock.readLock().lock();
                if (mUpTunnelManager != null) {
                    mUpTunnelManager.a(i, str);
                }
            } finally {
                mLock.readLock().unlock();
            }
        }
    }

    public static void execCMD(JSONObject jSONObject) {
        if (jSONObject != null && jSONObject.length() != 0) {
            try {
                mLock.readLock().lock();
                if (mUpTunnelManager != null) {
                    a a = b.a(jSONObject);
                    if (a != null) {
                        mUpTunnelManager.a(a);
                    }
                }
            } catch (Exception e) {
                com.amap.location.common.d.a.a((Throwable) e);
            } catch (Throwable th) {
                mLock.readLock().unlock();
                throw th;
            }
            mLock.readLock().unlock();
        }
    }

    public static void execCMD(JSONArray jSONArray) {
        if (jSONArray != null && jSONArray.length() != 0) {
            int length = jSONArray.length();
            int i = 0;
            while (i < length) {
                try {
                    execCMD(jSONArray.optJSONObject(i));
                    i++;
                } catch (Exception e) {
                    com.amap.location.common.d.a.a((Throwable) e);
                    return;
                }
            }
        }
    }

    public static void destroy() {
        try {
            mLock.writeLock().lock();
            if (mUpTunnelManager != null) {
                mUpTunnelManager.a();
                mUpTunnelManager = null;
            }
        } finally {
            mLock.writeLock().unlock();
        }
    }

    public static long getTableSize(int i) {
        try {
            mLock.writeLock().lock();
            if (mUpTunnelManager != null) {
                long b = mUpTunnelManager.b(i);
                return b;
            }
            mLock.writeLock().unlock();
            return -1;
        } finally {
            mLock.writeLock().unlock();
        }
    }
}
