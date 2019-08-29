package com.alipay.mobile.common.nbnet.biz.util;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import java.nio.ByteBuffer;
import java.util.UUID;

public final class NBNetEnvUtils {
    public static byte a = 2;
    public static Context b;
    private static String c;
    private static int d;
    private static int e;

    public static final Context a() {
        if (b != null) {
            return b;
        }
        if (VERSION.SDK_INT >= 14) {
            try {
                Context context = (Context) Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]).invoke(null, new Object[0]);
                b = context;
                if (context != null) {
                    return b;
                }
                NBNetLogCat.d("NBNetEnvUtils", "context from ActivityThread is null");
                Context context2 = (Context) Class.forName("com.alipay.mobile.quinox.LauncherApplication").getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
                b = context2;
                if (context2 == null) {
                    NBNetLogCat.d("NBNetEnvUtils", "context from LauncherApplication is null");
                }
                return b;
            } catch (Exception e2) {
                NBNetLogCat.b("NBNetEnvUtils", "", e2);
            }
        } else {
            throw new UnsupportedOperationException("does not support API level < 14");
        }
    }

    public static boolean b() {
        try {
            if ((a().getPackageManager().getApplicationInfo(b.getPackageName(), 128).flags & 2) != 0) {
                return true;
            }
            return false;
        } catch (Exception e2) {
            NBNetLogCat.b("NBNetEnvUtils", "", e2);
            return false;
        }
    }

    public static final String c() {
        return "aliwallet";
    }

    public static final String d() {
        if (j()) {
            return LoggerFactory.getLogContext().getUserId();
        }
        return "2088012008657728";
    }

    public static final String e() {
        if (j()) {
            return LoggerFactory.getLogContext().getDeviceId();
        }
        return "Vkr4I4tXff4DAEYfqB1HfTKH";
    }

    public static final String f() {
        if (j()) {
            return LoggerFactory.getLogContext().getProductVersion();
        }
        return "1.0-test";
    }

    public static final boolean g() {
        return a == 3;
    }

    private static boolean j() {
        return a == 2;
    }

    public static final String h() {
        return "arup";
    }

    public static String i() {
        ByteBuffer byteBuffer = null;
        try {
            byte[] uuidBytes = ByteUtil.a(UUID.randomUUID());
            byte[] timeStampBytes = ByteUtil.a(System.currentTimeMillis());
            byte[] channelBytes = k();
            byte[] appIdBytes = ByteUtil.a();
            byteBuffer = ByteBuffer.allocate(uuidBytes.length + 0 + timeStampBytes.length + 4 + appIdBytes.length + 1);
            byteBuffer.put(uuidBytes);
            byteBuffer.put(timeStampBytes);
            byteBuffer.put(channelBytes);
            byteBuffer.put(appIdBytes);
            byteBuffer.put(new byte[]{1});
            byteBuffer.flip();
            String base64String = Base64.encodeToString(byteBuffer.array(), 11);
            NBNetLogCat.a((String) "NBNetEnvUtils", "traceId : " + base64String);
            if (byteBuffer == null) {
                return base64String;
            }
            byteBuffer.clear();
            return base64String;
        } catch (Exception e2) {
            NBNetLogCat.b("NBNetEnvUtils", "getTraceId exception", e2);
            if (byteBuffer != null) {
                byteBuffer.clear();
            }
            return "";
        } catch (Throwable th) {
            if (byteBuffer != null) {
                byteBuffer.clear();
            }
            throw th;
        }
    }

    private static byte[] k() {
        return new byte[]{(byte) a(NetworkUtils.getNetworkType(a())), (byte) c(a()), (byte) b(a()), 2};
    }

    private static int b(Context context) {
        if (e >= 0) {
            return e;
        }
        int ver = 0;
        try {
            String version = d(context);
            if (version.indexOf(46) > 0) {
                version = version.substring(0, version.indexOf(46));
            }
            ver = Integer.parseInt(version);
        } catch (Exception e2) {
            e = 0;
        }
        e = ver;
        NBNetLogCat.a((String) "NBNetEnvUtils", "getMainVersion2: " + e);
        return e;
    }

    private static int c(Context context) {
        if (d >= 0) {
            return d;
        }
        int ver = 0;
        try {
            String version = d(context);
            if (version.indexOf(46) > 0) {
                String version2 = version.substring(version.indexOf(46) + 1);
                if (version2.indexOf(46) > 0) {
                    version2 = version2.substring(0, version2.indexOf(46));
                }
                ver = Integer.parseInt(version2);
            }
        } catch (Exception e2) {
            d = 0;
        }
        d = ver;
        NBNetLogCat.a((String) "NBNetEnvUtils", "getMinorVersion2: " + d);
        return d;
    }

    private static String d(Context context) {
        String version;
        if (!TextUtils.isEmpty(c)) {
            return c;
        }
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e2) {
            version = null;
        }
        c = version;
        return version;
    }

    private static int a(int type) {
        switch (type) {
            case 0:
                return 127;
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 2;
            case 4:
                return 5;
            default:
                return 0;
        }
    }

    public static final boolean a(Context appContext) {
        try {
            Class activityHelperClass = Class.forName("com.alipay.mobile.framework.app.ui.ActivityHelper");
            Boolean isBackground = (Boolean) activityHelperClass.getMethod("isBackgroundRunning", new Class[0]).invoke(activityHelperClass, new Object[0]);
            if (isBackground != null) {
                if (!isBackground.booleanValue()) {
                    return true;
                }
                return false;
            }
        } catch (Throwable e2) {
            NBNetLogCat.d("NBNetEnvUtils", String.format("isAtFrontDesk error: %s", new Object[]{e2.getMessage()}));
        }
        return MiscUtils.isAtFrontDesk(appContext);
    }
}
