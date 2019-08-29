package com.alipay.mobile.common.transport.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.mobile.common.netsdkextdependapi.appinfo.AppInfoUtil;
import com.alipay.mobile.common.transport.TransportStrategy;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;

public final class MiscUtils {
    public static final String[] BACKGROUND_RPC_APIS = {"alipay.security.vkeyDFP.staticData.report", "alipay.discovery.movie.getPreloadList", "alipay.mobileappcommon.repairinfo"};
    public static String CHANNEL_ID = null;
    public static final int FLAG_CHINA = 1;
    public static final int FLAG_TAIWAN = 2;
    public static final int FLAG_US = 4;
    public static final String KEY_RUNNING = "running";
    public static final String KEY_TOP = "top";
    public static int LANGUAGE_FLAG = 1;
    public static final String[] LOGIN_APIS = {"alipay.user.login", "ali.user.gw.unifyLogin"};
    public static String RELEASE_TYPE = null;
    public static final String[] RPC_LOG_BACKLIST = {"alipay.mobilerelation.friend.getMobileContact"};
    public static final String TAG = "MiscUtils";
    private static Boolean a = null;
    private static Boolean b = null;
    private static Object c = null;
    private static Method d = null;
    private static String e = "";
    private static String f = "";
    private static boolean g = false;
    private static Boolean h = null;
    private static final String[] i = {"com.eg.android.AlipayGphone", "com.antfortune.wealth", "com.alipay.m.portal", "com.taobao.mobile.dipei"};
    private static Boolean j = null;
    private static Boolean k = null;
    private static Boolean l = null;
    public static final String testGwUrlBase64 = "aHR0cHM6Ly9tb2JpbGVndy50ZXN0LmFsaXBheS5uZXQvbWd3Lmh0bQ==";
    public static final String test_1_64_gwHostBase64 = "bW9iaWxlZ3ctMS02NC50ZXN0LmFsaXBheS5uZXQ=";

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.String getConfigFromSdcard(java.lang.String r13) {
        /*
            r9 = 0
            java.lang.String r4 = android.os.Environment.getExternalStorageState()     // Catch:{ Exception -> 0x006c }
            java.lang.String r10 = "mounted"
            boolean r10 = android.text.TextUtils.equals(r10, r4)     // Catch:{ Exception -> 0x006c }
            if (r10 != 0) goto L_0x0017
            java.lang.String r10 = "mounted_ro"
            boolean r10 = android.text.TextUtils.equals(r10, r4)     // Catch:{ Exception -> 0x006c }
            if (r10 != 0) goto L_0x0017
            r7 = r9
        L_0x0016:
            return r7
        L_0x0017:
            java.io.File r3 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Exception -> 0x006c }
            boolean r10 = r3.exists()     // Catch:{ Exception -> 0x006c }
            if (r10 == 0) goto L_0x002d
            boolean r10 = r3.isDirectory()     // Catch:{ Exception -> 0x006c }
            if (r10 == 0) goto L_0x002d
            boolean r10 = r3.canRead()     // Catch:{ Exception -> 0x006c }
            if (r10 != 0) goto L_0x002f
        L_0x002d:
            r7 = r9
            goto L_0x0016
        L_0x002f:
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x006c }
            r5.<init>(r3, r13)     // Catch:{ Exception -> 0x006c }
            boolean r10 = r5.exists()     // Catch:{ Exception -> 0x006c }
            if (r10 == 0) goto L_0x0046
            boolean r10 = r5.isFile()     // Catch:{ Exception -> 0x006c }
            if (r10 == 0) goto L_0x0046
            boolean r10 = r5.canRead()     // Catch:{ Exception -> 0x006c }
            if (r10 != 0) goto L_0x0048
        L_0x0046:
            r7 = r9
            goto L_0x0016
        L_0x0048:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x006c }
            r0.<init>()     // Catch:{ Exception -> 0x006c }
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ Exception -> 0x006c }
            r6.<init>(r5)     // Catch:{ Exception -> 0x006c }
            r10 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r10]     // Catch:{ all -> 0x0067 }
        L_0x0056:
            r10 = -1
            int r8 = r6.read(r1)     // Catch:{ all -> 0x0067 }
            if (r10 == r8) goto L_0x0074
            java.lang.String r10 = new java.lang.String     // Catch:{ all -> 0x0067 }
            r11 = 0
            r10.<init>(r1, r11, r8)     // Catch:{ all -> 0x0067 }
            r0.append(r10)     // Catch:{ all -> 0x0067 }
            goto L_0x0056
        L_0x0067:
            r10 = move-exception
            r6.close()     // Catch:{ Exception -> 0x006c }
            throw r10     // Catch:{ Exception -> 0x006c }
        L_0x006c:
            r2 = move-exception
            java.lang.String r10 = "MiscUtils"
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r10, r2)
            r7 = r9
            goto L_0x0016
        L_0x0074:
            java.lang.String r7 = r0.toString()     // Catch:{ all -> 0x0067 }
            java.lang.String r10 = "TransportStrategy"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x0067 }
            java.lang.String r12 = "MiscUtils#getConfigFromSdcard().  加载本地配置："
            r11.<init>(r12)     // Catch:{ all -> 0x0067 }
            java.lang.StringBuilder r11 = r11.append(r7)     // Catch:{ all -> 0x0067 }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x0067 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r10, r11)     // Catch:{ all -> 0x0067 }
            r6.close()     // Catch:{ Exception -> 0x006c }
            goto L_0x0016
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.utils.MiscUtils.getConfigFromSdcard(java.lang.String):java.lang.String");
    }

    public static final boolean isDebugger(Context context) {
        boolean z;
        if (a != null) {
            return a.booleanValue();
        }
        if (context == null) {
            return false;
        }
        try {
            if ((context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).flags & 2) != 0) {
                z = true;
            } else {
                z = false;
            }
            Boolean valueOf = Boolean.valueOf(z);
            a = valueOf;
            return valueOf.booleanValue();
        } catch (Throwable e2) {
            LogCatUtil.error("MiscUtils", "isDebugger error, default return false.", e2);
            return false;
        }
    }

    public static boolean isRCVersion(Context context) {
        return AppInfoUtil.isReleaseTypeRC();
    }

    public static String getReleaseType(Context context) {
        if (!TextUtils.isEmpty(RELEASE_TYPE)) {
            return RELEASE_TYPE;
        }
        synchronized (MiscUtils.class) {
            if (!TextUtils.isEmpty(RELEASE_TYPE)) {
                String str = RELEASE_TYPE;
                return str;
            }
            try {
                Properties properties = new Properties();
                properties.load(context.getAssets().open("channel.config"));
                for (Entry configEntity : properties.entrySet()) {
                    if (TextUtils.equals(String.valueOf(configEntity.getKey()), "release_type")) {
                        Object valueObj = configEntity.getValue();
                        if (valueObj == null) {
                            RELEASE_TYPE = "unkown";
                            return "unkown";
                        }
                        String valueOf = String.valueOf(valueObj);
                        RELEASE_TYPE = valueOf;
                        return valueOf;
                    }
                }
            } catch (Exception e2) {
                LogCatUtil.warn((String) "MiscUtils", "getReleaseType error occurr " + e2.toString());
            }
            RELEASE_TYPE = "unkown";
            return "unkown";
        }
    }

    public static String getReleaseChannelId(Context context) {
        if (!TextUtils.isEmpty(CHANNEL_ID)) {
            return CHANNEL_ID;
        }
        synchronized (MiscUtils.class) {
            if (!TextUtils.isEmpty(CHANNEL_ID)) {
                String str = CHANNEL_ID;
                return str;
            }
            try {
                Properties properties = new Properties();
                properties.load(context.getAssets().open("channel.config"));
                for (Entry configEntity : properties.entrySet()) {
                    if (TextUtils.equals(String.valueOf(configEntity.getKey()), "channel_id")) {
                        Object valueObj = configEntity.getValue();
                        if (valueObj == null) {
                            CHANNEL_ID = "unkown";
                            return "unkown";
                        }
                        String valueOf = String.valueOf(valueObj);
                        CHANNEL_ID = valueOf;
                        return valueOf;
                    }
                }
            } catch (Exception e2) {
                LogCatUtil.warn((String) "MiscUtils", "getReleaseChannelId error " + e2.toString());
            }
            CHANNEL_ID = "unkown";
            return "unkown";
        }
    }

    public static boolean isSandboxChannel() {
        try {
            String channelId = getReleaseChannelId(TransportEnvUtil.getContext());
            if (TextUtils.isEmpty(channelId)) {
                return false;
            }
            if (channelId.contains("sandbox") || channelId.contains("special_channel")) {
                return true;
            }
            return false;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "MiscUtils", "isSandboxChannel ex:" + ex.toString());
            return false;
        }
    }

    public static boolean isShowUserTip(Context context) {
        try {
            if (isNeedShowUserTip(context) && context.getSharedPreferences("usertip", 0).getBoolean("showTip", true)) {
                return true;
            }
            return false;
        } catch (Exception e2) {
            LogCatUtil.warn((String) "MiscUtils", "isShowUserTip exception : " + e2.toString());
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0061  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isNeedShowUserTip(android.content.Context r7) {
        /*
            r0 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Exception -> 0x003f }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x003f }
            android.content.res.AssetManager r5 = r7.getAssets()     // Catch:{ Exception -> 0x003f }
            java.lang.String r6 = "channel.config"
            java.io.InputStream r5 = r5.open(r6)     // Catch:{ Exception -> 0x003f }
            r4.<init>(r5)     // Catch:{ Exception -> 0x003f }
            r1.<init>(r4)     // Catch:{ Exception -> 0x003f }
        L_0x0015:
            java.lang.String r3 = r1.readLine()     // Catch:{ Exception -> 0x0068, all -> 0x0065 }
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x0068, all -> 0x0065 }
            if (r4 != 0) goto L_0x0039
            java.lang.String r3 = r3.trim()     // Catch:{ Exception -> 0x0068, all -> 0x0065 }
            java.lang.String r4 = "isShowUserTip"
            boolean r4 = r3.startsWith(r4)     // Catch:{ Exception -> 0x0068, all -> 0x0065 }
            if (r4 == 0) goto L_0x0015
            java.lang.String r4 = "true"
            boolean r4 = r3.endsWith(r4)     // Catch:{ Exception -> 0x0068, all -> 0x0065 }
            if (r4 == 0) goto L_0x0015
            r1.close()
            r4 = 1
            r0 = r1
        L_0x0038:
            return r4
        L_0x0039:
            r1.close()
            r0 = r1
        L_0x003d:
            r4 = 0
            goto L_0x0038
        L_0x003f:
            r2 = move-exception
        L_0x0040:
            java.lang.String r4 = "MiscUtils"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x005e }
            java.lang.String r6 = "isNeedShowUserTip exception : "
            r5.<init>(r6)     // Catch:{ all -> 0x005e }
            java.lang.String r6 = r2.toString()     // Catch:{ all -> 0x005e }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x005e }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x005e }
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r4, r5)     // Catch:{ all -> 0x005e }
            if (r0 == 0) goto L_0x003d
            r0.close()
            goto L_0x003d
        L_0x005e:
            r4 = move-exception
        L_0x005f:
            if (r0 == 0) goto L_0x0064
            r0.close()
        L_0x0064:
            throw r4
        L_0x0065:
            r4 = move-exception
            r0 = r1
            goto L_0x005f
        L_0x0068:
            r2 = move-exception
            r0 = r1
            goto L_0x0040
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.utils.MiscUtils.isNeedShowUserTip(android.content.Context):boolean");
    }

    public static final boolean isOtherProcess(Context context) {
        return isPushProcess(context);
    }

    @Deprecated
    public static final boolean isPushProcess(Context context) {
        if (b != null) {
            return b.booleanValue();
        }
        String curProcessName = getCurProcessName(context);
        LogCatUtil.info("MiscUtils", "curProcessName:" + curProcessName);
        if (!TextUtils.equals(context.getPackageName(), curProcessName)) {
            LogCatUtil.verbose("MiscUtils", "Other Process");
            b = Boolean.valueOf(true);
        } else {
            LogCatUtil.verbose("MiscUtils", "Main Process");
            b = Boolean.valueOf(false);
        }
        return b.booleanValue();
    }

    public static final boolean isToolProcess(Context context) {
        if (j != null) {
            return j.booleanValue();
        }
        try {
            Boolean valueOf = Boolean.valueOf((context.getPackageName() + ":tools").equals(getCurProcessName(context)));
            j = valueOf;
            return valueOf.booleanValue();
        } catch (Throwable e2) {
            LogCatUtil.warn((String) "MiscUtils", "isToolProcess exception " + e2.toString());
            return false;
        }
    }

    public static final boolean isRealPushProcess(Context context) {
        if (k != null) {
            return k.booleanValue();
        }
        try {
            Boolean valueOf = Boolean.valueOf((context.getPackageName() + ":push").equals(getCurProcessName(context)));
            k = valueOf;
            return valueOf.booleanValue();
        } catch (Throwable e2) {
            LogCatUtil.warn((String) "MiscUtils", "isToolProcess exception " + e2.toString());
            return false;
        }
    }

    public static final String getCurProcessName(Context context) {
        String processName = "";
        try {
            Class clazz = Class.forName("android.ddm.DdmHandleAppName");
            processName = (String) clazz.getDeclaredMethod("getAppName", new Class[0]).invoke(clazz, new Object[0]);
        } catch (Exception e2) {
            LogCatUtil.warn((String) "MiscUtils", (Throwable) e2);
        }
        if (!TextUtils.isEmpty(processName)) {
            return processName;
        }
        int pid = Process.myPid();
        Iterator<RunningAppProcessInfo> it = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            RunningAppProcessInfo appProcess = it.next();
            if (appProcess.pid == pid) {
                processName = appProcess.processName;
                break;
            }
        }
        return processName;
    }

    public static final String getCurShortProcessName(Context context) {
        String procName = "main";
        try {
            String curProcessName = getCurProcessName(context);
            if (isEmpty(curProcessName)) {
                return procName;
            }
            String[] procNameSplit = curProcessName.split(":");
            if (procNameSplit.length >= 2) {
                procName = procNameSplit[1];
            }
            return procName;
        } catch (Throwable e2) {
            LogCatUtil.error("MiscUtils", "getCurShortProcessName fail", e2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0058 A[SYNTHETIC, Splitter:B:22:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0061 A[SYNTHETIC, Splitter:B:27:0x0061] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.String getCpuModel() {
        /*
            r0 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Exception -> 0x003d }
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ Exception -> 0x003d }
            java.lang.String r5 = "/proc/cpuinfo"
            r4.<init>(r5)     // Catch:{ Exception -> 0x003d }
            r1.<init>(r4)     // Catch:{ Exception -> 0x003d }
        L_0x000d:
            java.lang.String r3 = r1.readLine()     // Catch:{ Exception -> 0x006c, all -> 0x0069 }
            if (r3 == 0) goto L_0x0033
            java.lang.String r4 = r3.trim()     // Catch:{ Exception -> 0x006c, all -> 0x0069 }
            java.lang.String r5 = "Hardware"
            boolean r4 = r4.startsWith(r5)     // Catch:{ Exception -> 0x006c, all -> 0x0069 }
            if (r4 == 0) goto L_0x000d
            java.lang.String r4 = ":"
            int r4 = r3.indexOf(r4)     // Catch:{ Exception -> 0x006c, all -> 0x0069 }
            int r4 = r4 + 1
            java.lang.String r4 = r3.substring(r4)     // Catch:{ Exception -> 0x006c, all -> 0x0069 }
            java.lang.String r4 = r4.trim()     // Catch:{ Exception -> 0x006c, all -> 0x0069 }
            r1.close()     // Catch:{ Exception -> 0x0065 }
        L_0x0032:
            return r4
        L_0x0033:
            r1.close()     // Catch:{ Exception -> 0x003a }
            r0 = r1
        L_0x0037:
            java.lang.String r4 = ""
            goto L_0x0032
        L_0x003a:
            r4 = move-exception
            r0 = r1
            goto L_0x0037
        L_0x003d:
            r2 = move-exception
        L_0x003e:
            java.lang.String r4 = "MiscUtils"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x005e }
            java.lang.String r6 = "getCpuModel Exception: "
            r5.<init>(r6)     // Catch:{ all -> 0x005e }
            java.lang.String r6 = r2.getMessage()     // Catch:{ all -> 0x005e }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x005e }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x005e }
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r4, r5)     // Catch:{ all -> 0x005e }
            if (r0 == 0) goto L_0x0037
            r0.close()     // Catch:{ Exception -> 0x005c }
            goto L_0x0037
        L_0x005c:
            r4 = move-exception
            goto L_0x0037
        L_0x005e:
            r4 = move-exception
        L_0x005f:
            if (r0 == 0) goto L_0x0064
            r0.close()     // Catch:{ Exception -> 0x0067 }
        L_0x0064:
            throw r4
        L_0x0065:
            r5 = move-exception
            goto L_0x0032
        L_0x0067:
            r5 = move-exception
            goto L_0x0064
        L_0x0069:
            r4 = move-exception
            r0 = r1
            goto L_0x005f
        L_0x006c:
            r2 = move-exception
            r0 = r1
            goto L_0x003e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.utils.MiscUtils.getCpuModel():java.lang.String");
    }

    public static final Throwable getRootCause(Throwable e2) {
        Throwable cause = null;
        try {
            for (Throwable tmpCause = e2.getCause(); tmpCause != null; tmpCause = tmpCause.getCause()) {
                cause = tmpCause;
            }
            if (cause != null) {
                return cause;
            }
            return e2;
        } catch (Exception e3) {
            LogCatUtil.warn((String) "MiscUtils", "getRootCause exception : " + e2.toString());
            return e2;
        }
    }

    public static final String getInvokeStartTimeKey(String operationType, String id) {
        return "rpc#" + operationType + MetaRecord.LOG_SEPARATOR + id;
    }

    public static final boolean grayscaleUtdidForABTest(ConfigureItem configureItem) {
        try {
            return ABTestHelper.isSwitchEnableInner(configureItem);
        } catch (Throwable ex) {
            LogCatUtil.error((String) "MiscUtils", ex);
            return false;
        }
    }

    public static final boolean grayscaleUtdid(String utdid, String grayValuesStr) {
        if (TextUtils.equals(grayValuesStr, "0") || TextUtils.equals(grayValuesStr, "-1")) {
            return false;
        }
        try {
            String[] split = grayValuesStr.split(",");
            int[] grayValues = new int[split.length];
            for (int i2 = 0; i2 < grayValues.length; i2++) {
                grayValues[i2] = Integer.parseInt(split[i2]);
            }
            return grayscaleUtdid(utdid, grayValues);
        } catch (Throwable ex) {
            LogCatUtil.error("MiscUtils", "grayscaleUtdid exception", ex);
            return false;
        }
    }

    public static final boolean grayscaleUtdid(String utdid, int[] grayValues) {
        if (utdid == null || utdid.length() < 2 || grayValues == null || grayValues.length == 0 || grayValues[grayValues.length - 1] == 0) {
            return false;
        }
        int grayBitLen = grayValues.length;
        if (grayValues.length > utdid.length()) {
            grayBitLen = utdid.length();
        }
        int i2 = 0;
        int j2 = 1;
        while (i2 < grayBitLen) {
            if ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+/".indexOf(utdid.charAt(utdid.length() - j2)) > grayValues[grayBitLen - j2]) {
                return false;
            }
            i2++;
            j2++;
        }
        return true;
    }

    public static final boolean grayscale(String numRange) {
        String[] split = numRange.split("-");
        if (split.length != 2) {
            return false;
        }
        int grayNum = Integer.parseInt(split[0]);
        if (grayNum > 0) {
            return grayscale(Integer.parseInt(split[1]), grayNum);
        }
        return false;
    }

    public static final boolean grayscale(int maxNum, int grayNum) {
        try {
            if (new Random().nextInt(maxNum / grayNum) == 0) {
                return true;
            }
        } catch (Exception e2) {
            LogCatUtil.warn((String) "MiscUtils", (Throwable) e2);
        }
        return false;
    }

    public static final boolean isBgRpc(String operationType) {
        for (String equals : BACKGROUND_RPC_APIS) {
            if (TextUtils.equals(equals, operationType)) {
                return true;
            }
        }
        return false;
    }

    public static Object invokeMethod(Object obj, String methodName, Class[] paramClasses, Object[] methodParam) {
        return obj.getClass().getDeclaredMethod(methodName, paramClasses).invoke(obj, methodParam);
    }

    public static boolean isGreatEqualAndroid5() {
        if (VERSION.SDK_INT >= 21) {
            return true;
        }
        return false;
    }

    public static boolean isOnlineUrl(URL url) {
        try {
            return TextUtils.equals(url.getHost(), "mobilegw.alipay.com");
        } catch (Throwable e2) {
            LogCatUtil.error((String) "MiscUtils", e2);
            return false;
        }
    }

    public static boolean isPreUrl(URL url) {
        try {
            return TextUtils.equals(url.getHost(), "mobilegwpre.alipay.com");
        } catch (Throwable e2) {
            LogCatUtil.error((String) "MiscUtils", e2);
            return false;
        }
    }

    public static boolean isTestUrl(URL url) {
        try {
            return TextUtils.equals(url.getHost(), getTest_1_64_gwHost());
        } catch (Throwable e2) {
            LogCatUtil.error((String) "MiscUtils", e2);
            return false;
        }
    }

    public static boolean isSandboxUrl(URL url) {
        try {
            return TextUtils.equals(url.getHost(), "mobilegw.alipaydev.com");
        } catch (Throwable e2) {
            LogCatUtil.error((String) "MiscUtils", e2);
            return false;
        }
    }

    public static String getTestGwUrl() {
        try {
            if (TextUtils.isEmpty(e)) {
                e = new String(Base64.decode((String) testGwUrlBase64, 0), "UTF-8");
            }
            LogCatUtil.debug("MiscUtils", "testGwUrl= " + e);
            return e;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "MiscUtils", ex);
            return "";
        }
    }

    public static String getTest_1_64_gwHost() {
        try {
            if (TextUtils.isEmpty(f)) {
                f = new String(Base64.decode((String) test_1_64_gwHostBase64, 0), "UTF-8");
            }
            LogCatUtil.debug("MiscUtils", "test_1_64_gwHost= " + f);
            return f;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "MiscUtils", ex);
            return "";
        }
    }

    public static final boolean isInLogBackList(String operationType) {
        for (String equals : RPC_LOG_BACKLIST) {
            if (TextUtils.equals(equals, operationType)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isLoginRpc(String operationType) {
        if (TextUtils.isEmpty(operationType)) {
            return false;
        }
        for (String loginApi : LOGIN_APIS) {
            if (operationType.startsWith(loginApi)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isAtFrontDesk(Context context) {
        try {
            ComponentName topActivity = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningTasks(1).get(0).topActivity;
            if (topActivity != null) {
                LogCatUtil.info("MiscUtils", "isAtFrontDesk.  topActivity ShortClassName=[" + topActivity.getShortClassName() + "]");
            }
            if (topActivity == null || !context.getPackageName().equalsIgnoreCase(topActivity.getPackageName()) || topActivity.getShortClassName().contains("FlyBirdWindowActivity")) {
                LogCatUtil.info("MiscUtils", "topActivity return false.");
                return false;
            }
            LogCatUtil.info("MiscUtils", "topActivity return true.");
            return true;
        } catch (Exception e2) {
            LogCatUtil.warn((String) "MiscUtils", "isAtFrontDesk Exception: " + e2.toString());
            return false;
        }
    }

    public static final Map<String, String> getWalletProcState(Context context) {
        Map outParam = new HashMap(2);
        outParam.put("top", "false");
        outParam.put(KEY_RUNNING, "false");
        try {
            if (isAtFrontDesk(context)) {
                outParam.put("top", "true");
                outParam.put(KEY_RUNNING, "true");
            } else {
                outParam.put("top", "false");
                List runningTasks = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningTasks(100);
                if (runningTasks != null && !runningTasks.isEmpty()) {
                    Iterator<RunningTaskInfo> it = runningTasks.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        ComponentName baseActivity = it.next().baseActivity;
                        String packageName = context.getPackageName();
                        if (baseActivity != null && packageName.equalsIgnoreCase(baseActivity.getPackageName())) {
                            outParam.put(KEY_RUNNING, "true");
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LogCatUtil.error((String) "MiscUtils", (Throwable) ex);
        }
        return outParam;
    }

    public static final boolean isScreenOn(Context context) {
        try {
            boolean screenState = ((Boolean) PowerManager.class.getMethod("isScreenOn", new Class[0]).invoke((PowerManager) context.getSystemService("power"), new Object[0])).booleanValue();
            LogCatUtil.info("MiscUtils", "isScreenOn=" + screenState);
            return screenState;
        } catch (Throwable e2) {
            LogCatUtil.info("MiscUtils", "API < 7," + e2);
            return false;
        }
    }

    public static final boolean isEnableExtTransport(Context pContext) {
        if (isInAlipayClient(pContext)) {
            return true;
        }
        return false;
    }

    public static final boolean isInAlipayClient(Context context) {
        if (h != null) {
            return h.booleanValue();
        }
        String currPackageName = context.getPackageName();
        if (TextUtils.isEmpty(currPackageName)) {
            return false;
        }
        for (String alipayPackageName : i) {
            if (currPackageName.startsWith(alipayPackageName)) {
                Boolean bool = Boolean.TRUE;
                h = bool;
                return bool.booleanValue();
            }
        }
        Boolean bool2 = Boolean.FALSE;
        h = bool2;
        return bool2.booleanValue();
    }

    public static final String getCurrentLocale(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        StringBuilder stringBuilder = new StringBuilder(11);
        stringBuilder.append(locale.getLanguage());
        String countryCode = locale.getCountry();
        if (!TextUtils.isEmpty(countryCode)) {
            stringBuilder.append("-").append(countryCode);
        }
        return stringBuilder.toString();
    }

    public static final String getAlipayLocaleDes() {
        try {
            return (String) a().invoke(getLocaleHelper(), new Object[0]);
        } catch (Exception e2) {
            return "";
        }
    }

    public static final Object getLocaleHelper() {
        if (c != null) {
            return c;
        }
        try {
            Class localeHelperClazz = Class.forName("com.alipay.mobile.framework.locale.LocaleHelper");
            Object invoke = localeHelperClazz.getDeclaredMethod("getInstance", new Class[0]).invoke(localeHelperClazz, new Object[0]);
            c = invoke;
            return invoke;
        } catch (Exception e2) {
            LogCatUtil.warn((String) "MiscUtils", "[getLocaleHelper] Exception: " + e2.toString());
            return null;
        }
    }

    private static final Method a() {
        if (d != null) {
            return d;
        }
        try {
            Method declaredMethod = getLocaleHelper().getClass().getDeclaredMethod("getAlipayLocaleDes", new Class[0]);
            d = declaredMethod;
            return declaredMethod;
        } catch (Throwable e2) {
            LogCatUtil.error((String) "MiscUtils", "[getAlipayLocaleDesMethod] Exception: " + e2.toString());
            return null;
        }
    }

    public static final boolean isTFSHost(String host) {
        return TextUtils.equals(host, "tfsimg.alipay.com");
    }

    public static final String replaceTFS2CDN(URI tfsURL) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(AjxHttpLoader.DOMAIN_HTTP);
        stringBuilder.append("tfs.alipayobjects.com");
        stringBuilder.append(tfsURL.getPath());
        return stringBuilder.toString();
    }

    public static final int getEffectivePort(String scheme, int specifiedPort) {
        return specifiedPort != -1 ? specifiedPort : getDefaultPort(scheme);
    }

    public static int getDefaultPort(String scheme) {
        if ("http".equalsIgnoreCase(scheme)) {
            return 80;
        }
        if ("https".equalsIgnoreCase(scheme)) {
            return 443;
        }
        return -1;
    }

    public static boolean isAlipayLocalPackage(Context context) {
        if (l != null) {
            return l.booleanValue();
        }
        if (CertUtils.isDevSignPackage(context)) {
            LogCatUtil.info("MiscUtils", " isDevSignPackage is true.");
            Boolean valueOf = Boolean.valueOf(true);
            l = valueOf;
            return valueOf.booleanValue();
        }
        try {
            LogCatUtil.info("MiscUtils", " getReleaseType:" + AppInfoUtil.getReleaseType());
            if (AppInfoUtil.isReleaseTypeDev()) {
                l = Boolean.TRUE;
            } else {
                l = Boolean.FALSE;
            }
            return l.booleanValue();
        } catch (Throwable e2) {
            LogCatUtil.warn((String) "MiscUtils", "getReleaseType exception : " + e2.toString());
            return true;
        }
    }

    public static final String getSharedPrefsDir(Context context) {
        try {
            return context.getFilesDir().getParent() + "/shared_prefs/";
        } catch (Throwable e2) {
            LogCatUtil.warn((String) "MiscUtils", "getSharedPrefsDir exception" + e2.toString());
            return "";
        }
    }

    public static boolean isPushProcessRuning(Context context) {
        try {
            List<RunningAppProcessInfo> runningApps = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses();
            String pname = context.getPackageName() + ":push";
            for (RunningAppProcessInfo info : runningApps) {
                if (pname.equals(info.processName)) {
                    LogCatUtil.info("MiscUtils", "isPushProcessRuning is true");
                    return true;
                }
            }
        } catch (Throwable e2) {
            LogCatUtil.error((String) "MiscUtils", e2);
        }
        return false;
    }

    public static boolean isMainProcessRuning(Context context) {
        try {
            List<RunningAppProcessInfo> runningApps = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses();
            String pname = context.getPackageName();
            for (RunningAppProcessInfo info : runningApps) {
                if (pname.equals(info.processName)) {
                    LogCatUtil.info("MiscUtils", "isMainProcessRuning is true");
                    return true;
                }
            }
        } catch (Throwable e2) {
            LogCatUtil.error((String) "MiscUtils", e2);
        }
        return false;
    }

    public static boolean isExistMultiMainProcess(Context context) {
        try {
            List<RunningAppProcessInfo> runningApps = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses();
            String pname = context.getPackageName();
            int procCount = 0;
            for (RunningAppProcessInfo info : runningApps) {
                if (pname.equals(info.processName)) {
                    procCount++;
                }
            }
            if (procCount > 1) {
                return true;
            }
        } catch (Throwable e2) {
            LogCatUtil.error((String) "MiscUtils", e2);
        }
        return false;
    }

    public static boolean isAlipayGW(String gwUrl) {
        try {
            return TransportStrategy.isAlipayUrl(gwUrl);
        } catch (Throwable ex) {
            LogCatUtil.error((String) "MiscUtils", ex);
            return false;
        }
    }

    public static boolean isOversea() {
        return g;
    }

    public static void setOversea(boolean oversea) {
        g = oversea;
    }

    public static final boolean isEmpty(String text) {
        if (text == null || text.trim().length() <= 0) {
            return true;
        }
        return false;
    }

    public static final String getMetaData(Context context, String metaDatakey) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString(metaDatakey);
        } catch (Throwable e2) {
            LogCatUtil.error("MiscUtils", " getMetaData exception. ", e2);
            return "";
        }
    }

    public static final Object getMetaDataVO(Context context, String metaDatakey) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.get(metaDatakey);
        } catch (Throwable e2) {
            LogCatUtil.error("MiscUtils", " getMetaData exception. ", e2);
            return null;
        }
    }

    public static final boolean getBooleanFromMetaData(Context context, String metaDatakey) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            String resultStr = applicationInfo.metaData.getString(metaDatakey);
            if (!TextUtils.isEmpty(resultStr)) {
                return Boolean.valueOf(resultStr).booleanValue();
            }
            return applicationInfo.metaData.getBoolean(metaDatakey);
        } catch (Throwable e2) {
            LogCatUtil.error("MiscUtils", " getMetaData exception. ", e2);
            return false;
        }
    }

    public static String getAmnetServerAddressFromMetaData() {
        try {
            Context context = TransportEnvUtil.getContext();
            String amnetServerAddress = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("sandbox.amnet.server");
            if (!TextUtils.isEmpty(amnetServerAddress)) {
                return amnetServerAddress;
            }
        } catch (Throwable e2) {
            LogCatUtil.warn((String) "MiscUtils", e2);
        }
        return null;
    }

    public static boolean isMdapApi(String operationType) {
        try {
            if (!isInAlipayClient(TransportEnvUtil.getContext())) {
                return false;
            }
            String mdapApis = TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.MDAP_APIS);
            if (TextUtils.isEmpty(mdapApis) || TextUtils.isEmpty(operationType)) {
                return false;
            }
            for (String equals : mdapApis.split(",")) {
                if (TextUtils.equals(equals, operationType)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "MiscUtils", "isMdapApi ex:" + ex.toString());
            return false;
        }
    }

    public static String generateRandomStr(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i2 = 0; i2 < length; i2++) {
            sb.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt(62)));
        }
        return sb.toString();
    }

    public static String getTopDomain(String url) {
        try {
            URI uri = new URI(url);
            return uri.getHost().substring(uri.getHost().indexOf("."));
        } catch (Throwable ex) {
            LogCatUtil.error((String) "MiscUtils", "getTopDomain ex:" + ex.toString());
            return url;
        }
    }
}
