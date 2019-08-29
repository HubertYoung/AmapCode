package com.alipay.mobile.tinyappcommon.utils.io;

import android.os.Build.VERSION;
import android.system.Os;
import android.system.StructStat;
import java.lang.reflect.Method;

public final class SafeLibCore {
    private static volatile Object sLibCoreOs;
    private static volatile Method sLibCoreOsStat;

    private static Object getLibCoreOs() {
        if (sLibCoreOs == null) {
            synchronized (SafeLibCore.class) {
                if (sLibCoreOs == null) {
                    try {
                        sLibCoreOs = Class.forName("libcore.io.Libcore").getField("os").get(null);
                    } catch (Exception e) {
                    }
                }
            }
        }
        return sLibCoreOs;
    }

    private static Method getLibCoreOsStat() {
        if (sLibCoreOsStat == null) {
            synchronized (SafeStructStat.class) {
                if (sLibCoreOsStat == null) {
                    Object os = getLibCoreOs();
                    if (os != null) {
                        try {
                            sLibCoreOsStat = os.getClass().getMethod("stat", new Class[]{String.class});
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        return sLibCoreOsStat;
    }

    public static SafeStructStat stat(String path) {
        if (VERSION.SDK_INT >= 21) {
            try {
                StructStat stat = Os.stat(path);
                return new SafeStructStat(stat.st_dev, stat.st_ino, stat.st_mode, stat.st_nlink, stat.st_uid, stat.st_gid, stat.st_rdev, stat.st_size, stat.st_atime, stat.st_mtime, stat.st_ctime, stat.st_blksize, stat.st_blocks);
            } catch (Exception e) {
                return null;
            }
        } else {
            Object os = getLibCoreOs();
            if (os == null) {
                return null;
            }
            Method osStat = getLibCoreOsStat();
            if (osStat == null) {
                return null;
            }
            try {
                Object object = osStat.invoke(os, new Object[]{path});
                if (object != null) {
                    return new SafeStructStat(object);
                }
                return null;
            } catch (Exception e2) {
                return null;
            }
        }
    }
}
