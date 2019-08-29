package com.alipay.mobile.monitor.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Pair;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.tianyan.mobilesdk.TianyanLoggingStatus;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.link.protocol.http.MultipartUtility;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TimeZone;

public class MonitorUtils {
    private static final String[] SU_PATHS = {"/system/bin/su", "/system/xbin/su", "/system/sbin/su", "/sbin/su", "/vendor/bin/su"};
    private static final String TAG = "MonitorUtils";
    private static Random sCommonRandom = null;
    private static int sDebuggableFlag = -1;
    private static int sDeviceEmulatorFlag = -1;
    private static int sDeviceRootedFlag = -1;

    public interface FillBufferHandler {
        Object handleKey(Object obj);

        Object handleValue(Object obj);
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0053 A[SYNTHETIC, Splitter:B:25:0x0053] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0059 A[SYNTHETIC, Splitter:B:30:0x0059] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int getCpuTemperature() {
        /*
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "/sys/class/thermal/thermal_zone0/temp"
            r0.<init>(r1)
            boolean r1 = r0.exists()
            r2 = -1
            if (r1 == 0) goto L_0x005d
            boolean r1 = r0.isFile()
            if (r1 != 0) goto L_0x0015
            goto L_0x005d
        L_0x0015:
            r1 = 0
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x003d }
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ Throwable -> 0x003d }
            r4.<init>(r0)     // Catch:{ Throwable -> 0x003d }
            r3.<init>(r4)     // Catch:{ Throwable -> 0x003d }
        L_0x0020:
            java.lang.String r0 = r3.readLine()     // Catch:{ Throwable -> 0x0038, all -> 0x0035 }
            if (r0 == 0) goto L_0x0031
            int r1 = r0.length()     // Catch:{ Throwable -> 0x0038, all -> 0x0035 }
            if (r1 == 0) goto L_0x0020
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Throwable -> 0x0038, all -> 0x0035 }
            r2 = r0
        L_0x0031:
            r3.close()     // Catch:{ Throwable -> 0x0056 }
            goto L_0x0056
        L_0x0035:
            r0 = move-exception
            r1 = r3
            goto L_0x0057
        L_0x0038:
            r0 = move-exception
            r1 = r3
            goto L_0x003e
        L_0x003b:
            r0 = move-exception
            goto L_0x0057
        L_0x003d:
            r0 = move-exception
        L_0x003e:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x003b }
            java.lang.String r4 = "MonitorUtils"
            java.lang.String r5 = "getCpuTemperature: "
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x003b }
            java.lang.String r0 = r5.concat(r0)     // Catch:{ all -> 0x003b }
            r3.error(r4, r0)     // Catch:{ all -> 0x003b }
            if (r1 == 0) goto L_0x0056
            r1.close()     // Catch:{ Throwable -> 0x0056 }
        L_0x0056:
            return r2
        L_0x0057:
            if (r1 == 0) goto L_0x005c
            r1.close()     // Catch:{ Throwable -> 0x005c }
        L_0x005c:
            throw r0
        L_0x005d:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r0 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r1 = "MonitorUtils"
            java.lang.String r3 = "getCpuTemperature: no target file"
            r0.error(r1, r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.monitor.util.MonitorUtils.getCpuTemperature():int");
    }

    public static String getBackgroundStatus(String str, String str2, String str3) {
        String str4 = TianyanLoggingStatus.isMonitorBackground() ? str : str2;
        String str5 = TianyanLoggingStatus.isStrictBackground() ? str : str2;
        if (!TianyanLoggingStatus.isRelaxedBackground()) {
            str = str2;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str4);
        sb.append(str3);
        sb.append(str5);
        sb.append(str3);
        sb.append(str);
        return sb.toString();
    }

    public static String serializeMap(Map<String, String> map, String str, String str2) {
        if (map == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (Entry next : map.entrySet()) {
            String str3 = (String) next.getKey();
            String str4 = (String) next.getValue();
            if (z) {
                z = false;
            } else {
                sb.append(str);
            }
            sb.append(str3);
            sb.append(str2);
            sb.append(str4);
        }
        return sb.toString();
    }

    public static String getSystemProperty(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        try {
            return (String) Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class, String.class}).invoke(null, new Object[]{str, str2});
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error(TAG, "getSystemProperty", th);
            return str2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x004e A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x004f A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isDeviceRooted() {
        /*
            int r0 = sDeviceRootedFlag
            r1 = 0
            r2 = 1
            if (r0 >= 0) goto L_0x004a
            java.lang.String r0 = "ro.secure"
            r3 = 0
            java.lang.String r0 = getSystemProperty(r0, r3)     // Catch:{ Throwable -> 0x0038 }
            java.lang.String r3 = "0"
            boolean r0 = r3.equals(r0)     // Catch:{ Throwable -> 0x0038 }
            if (r0 != 0) goto L_0x0043
            r3 = 0
        L_0x0017:
            java.lang.String[] r4 = SU_PATHS     // Catch:{ Throwable -> 0x0036 }
            int r4 = r4.length     // Catch:{ Throwable -> 0x0036 }
            if (r3 >= r4) goto L_0x0043
            java.lang.String[] r4 = SU_PATHS     // Catch:{ Throwable -> 0x0036 }
            r4 = r4[r3]     // Catch:{ Throwable -> 0x0036 }
            java.io.File r5 = new java.io.File     // Catch:{ Throwable -> 0x0036 }
            r5.<init>(r4)     // Catch:{ Throwable -> 0x0036 }
            boolean r4 = r5.exists()     // Catch:{ Throwable -> 0x0036 }
            if (r4 == 0) goto L_0x0033
            boolean r4 = r5.isFile()     // Catch:{ Throwable -> 0x0036 }
            if (r4 == 0) goto L_0x0033
            r0 = 1
            goto L_0x0043
        L_0x0033:
            int r3 = r3 + 1
            goto L_0x0017
        L_0x0036:
            r3 = move-exception
            goto L_0x003a
        L_0x0038:
            r3 = move-exception
            r0 = 0
        L_0x003a:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r5 = "MonitorUtils"
            r4.error(r5, r3)
        L_0x0043:
            if (r0 == 0) goto L_0x0047
            r0 = 1
            goto L_0x0048
        L_0x0047:
            r0 = 0
        L_0x0048:
            sDeviceRootedFlag = r0
        L_0x004a:
            int r0 = sDeviceRootedFlag
            if (r0 != r2) goto L_0x004f
            return r2
        L_0x004f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.monitor.util.MonitorUtils.isDeviceRooted():boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0056, code lost:
        if (new java.math.BigDecimal(r6).compareTo(java.math.BigDecimal.ZERO) == 0) goto L_0x0058;
     */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0040 A[Catch:{ Throwable -> 0x005a, Throwable -> 0x0065 }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0078 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0079 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isDeviceEmulator(android.content.Context r6) {
        /*
            r5 = this;
            int r0 = sDeviceEmulatorFlag
            r1 = 0
            r2 = 1
            if (r0 >= 0) goto L_0x0074
            java.lang.String r0 = android.os.Build.HARDWARE     // Catch:{ Throwable -> 0x0027 }
            java.lang.String r3 = "goldfish"
            boolean r0 = r0.contains(r3)     // Catch:{ Throwable -> 0x0027 }
            if (r0 != 0) goto L_0x0025
            java.lang.String r0 = android.os.Build.PRODUCT     // Catch:{ Throwable -> 0x0027 }
            java.lang.String r3 = "sdk"
            boolean r0 = r0.contains(r3)     // Catch:{ Throwable -> 0x0027 }
            if (r0 != 0) goto L_0x0025
            java.lang.String r0 = android.os.Build.FINGERPRINT     // Catch:{ Throwable -> 0x0027 }
            java.lang.String r3 = "generic"
            boolean r0 = r0.contains(r3)     // Catch:{ Throwable -> 0x0027 }
            if (r0 == 0) goto L_0x0031
        L_0x0025:
            r0 = 1
            goto L_0x0032
        L_0x0027:
            r0 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0067 }
            java.lang.String r4 = "MonitorUtils"
            r3.error(r4, r0)     // Catch:{ Throwable -> 0x0067 }
        L_0x0031:
            r0 = 0
        L_0x0032:
            if (r0 != 0) goto L_0x0072
            if (r6 == 0) goto L_0x0072
            java.lang.String r3 = "phone"
            java.lang.Object r6 = r6.getSystemService(r3)     // Catch:{ Throwable -> 0x0065 }
            android.telephony.TelephonyManager r6 = (android.telephony.TelephonyManager) r6     // Catch:{ Throwable -> 0x0065 }
            if (r6 == 0) goto L_0x0072
            java.lang.String r6 = r6.getDeviceId()     // Catch:{ Throwable -> 0x0065 }
            boolean r3 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Throwable -> 0x0065 }
            if (r3 == 0) goto L_0x004b
            goto L_0x0058
        L_0x004b:
            java.math.BigDecimal r3 = new java.math.BigDecimal     // Catch:{ Throwable -> 0x005a }
            r3.<init>(r6)     // Catch:{ Throwable -> 0x005a }
            java.math.BigDecimal r6 = java.math.BigDecimal.ZERO     // Catch:{ Throwable -> 0x005a }
            int r6 = r3.compareTo(r6)     // Catch:{ Throwable -> 0x005a }
            if (r6 != 0) goto L_0x0072
        L_0x0058:
            r0 = 1
            goto L_0x0072
        L_0x005a:
            r6 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0065 }
            java.lang.String r4 = "MonitorUtils"
            r3.error(r4, r6)     // Catch:{ Throwable -> 0x0065 }
            goto L_0x0072
        L_0x0065:
            r6 = move-exception
            goto L_0x0069
        L_0x0067:
            r6 = move-exception
            r0 = 0
        L_0x0069:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r4 = "MonitorUtils"
            r3.error(r4, r6)
        L_0x0072:
            sDeviceEmulatorFlag = r0
        L_0x0074:
            int r6 = sDeviceEmulatorFlag
            if (r6 != r2) goto L_0x0079
            return r2
        L_0x0079:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.monitor.util.MonitorUtils.isDeviceEmulator(android.content.Context):boolean");
    }

    public static String getConfigValueByKeyOnBrandOrSDK(String str, String str2) {
        String[] split;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("_BrandOrSDK");
        String configValueByKey = TianyanLoggingStatus.getConfigValueByKey(sb.toString(), "");
        boolean z = true;
        if (!TextUtils.isEmpty(configValueByKey) && !"*".equals(configValueByKey)) {
            HashSet hashSet = new HashSet();
            for (String str3 : configValueByKey.split(",")) {
                if (!TextUtils.isEmpty(str3)) {
                    hashSet.add(str3);
                }
            }
            String manufacturer = LoggerFactory.getDeviceProperty().getManufacturer();
            String brandName = LoggerFactory.getDeviceProperty().getBrandName();
            int i = VERSION.SDK_INT;
            if (!hashSet.contains(manufacturer)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(manufacturer);
                sb2.append("_");
                sb2.append(i);
                if (!hashSet.contains(sb2.toString()) && !hashSet.contains(brandName)) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(brandName);
                    sb3.append("_");
                    sb3.append(i);
                    if (!hashSet.contains(sb3.toString()) && !hashSet.contains("SDK".concat(String.valueOf(i)))) {
                        z = false;
                    }
                }
            }
        }
        if (!z) {
            return str2;
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str);
        sb4.append("_Value");
        return TianyanLoggingStatus.getConfigValueByKey(sb4.toString(), str2);
    }

    public static int randomInteger(int i, int i2) {
        return i + ((int) (getsCommonRandom().nextFloat() * ((float) (i2 - i))));
    }

    public static Random getsCommonRandom() {
        if (sCommonRandom == null) {
            sCommonRandom = new Random();
        }
        return sCommonRandom;
    }

    public static String formatTimespanToHHmmssSSS(long j) {
        if (j < 0) {
            return "";
        }
        long j2 = j % 1000;
        long j3 = j / 1000;
        long j4 = j3 / 60;
        StringBuilder sb = new StringBuilder();
        sb.append(j4 / 60);
        sb.append("h:");
        sb.append(j4 % 60);
        sb.append("m:");
        sb.append(j3 % 60);
        sb.append("s:");
        sb.append(j2);
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0013 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void fillBufferWithParams(java.lang.StringBuilder r7, java.util.Map<?, ?> r8, com.alipay.mobile.monitor.util.MonitorUtils.FillBufferHandler r9) {
        /*
            if (r7 == 0) goto L_0x0067
            if (r8 == 0) goto L_0x0067
            int r0 = r8.size()
            if (r0 != 0) goto L_0x000b
            goto L_0x0067
        L_0x000b:
            java.util.Set r8 = r8.entrySet()     // Catch:{ Throwable -> 0x005a }
            java.util.Iterator r8 = r8.iterator()     // Catch:{ Throwable -> 0x005a }
        L_0x0013:
            boolean r0 = r8.hasNext()     // Catch:{ Throwable -> 0x005a }
            if (r0 == 0) goto L_0x0059
            java.lang.Object r0 = r8.next()     // Catch:{ Throwable -> 0x005a }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ Throwable -> 0x005a }
            java.lang.Object r1 = r0.getKey()     // Catch:{ Throwable -> 0x005a }
            java.lang.Object r0 = r0.getValue()     // Catch:{ Throwable -> 0x005a }
            if (r9 == 0) goto L_0x0048
            java.lang.Object r2 = r9.handleKey(r1)     // Catch:{ Throwable -> 0x0036 }
            java.lang.Object r1 = r9.handleKey(r0)     // Catch:{ Throwable -> 0x0034 }
            r0 = r1
        L_0x0032:
            r1 = r2
            goto L_0x0046
        L_0x0034:
            r1 = move-exception
            goto L_0x003a
        L_0x0036:
            r2 = move-exception
            r6 = r2
            r2 = r1
            r1 = r6
        L_0x003a:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x005a }
            java.lang.String r4 = "MonitorUtils"
            java.lang.String r5 = "fillBufferWithParams.inner"
            r3.error(r4, r5, r1)     // Catch:{ Throwable -> 0x005a }
            goto L_0x0032
        L_0x0046:
            if (r1 == 0) goto L_0x0013
        L_0x0048:
            java.lang.String r2 = ", "
            r7.append(r2)     // Catch:{ Throwable -> 0x005a }
            r7.append(r1)     // Catch:{ Throwable -> 0x005a }
            java.lang.String r1 = ": "
            r7.append(r1)     // Catch:{ Throwable -> 0x005a }
            r7.append(r0)     // Catch:{ Throwable -> 0x005a }
            goto L_0x0013
        L_0x0059:
            return
        L_0x005a:
            r7 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r8 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r9 = "MonitorUtils"
            java.lang.String r0 = "fillBufferWithParams.outer"
            r8.error(r9, r0, r7)
            return
        L_0x0067:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.monitor.util.MonitorUtils.fillBufferWithParams(java.lang.StringBuilder, java.util.Map, com.alipay.mobile.monitor.util.MonitorUtils$FillBufferHandler):void");
    }

    /* JADX WARNING: type inference failed for: r1v2, types: [java.lang.Object, java.lang.String] */
    /* JADX WARNING: type inference failed for: r1v3, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r3v5, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r3v1
      assigns: []
      uses: []
      mth insns count: 41
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0013 A[SYNTHETIC] */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void fillBufferWithParams(java.lang.StringBuilder r8, android.os.Bundle r9, com.alipay.mobile.monitor.util.MonitorUtils.FillBufferHandler r10) {
        /*
            if (r8 == 0) goto L_0x0063
            if (r9 == 0) goto L_0x0063
            int r0 = r9.size()
            if (r0 != 0) goto L_0x000b
            goto L_0x0063
        L_0x000b:
            java.util.Set r0 = r9.keySet()     // Catch:{ Throwable -> 0x0056 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ Throwable -> 0x0056 }
        L_0x0013:
            boolean r1 = r0.hasNext()     // Catch:{ Throwable -> 0x0056 }
            if (r1 == 0) goto L_0x0055
            java.lang.Object r1 = r0.next()     // Catch:{ Throwable -> 0x0056 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Throwable -> 0x0056 }
            java.lang.Object r2 = r9.get(r1)     // Catch:{ Throwable -> 0x0056 }
            if (r10 == 0) goto L_0x0044
            java.lang.Object r3 = r10.handleKey(r1)     // Catch:{ Throwable -> 0x0032 }
            java.lang.Object r1 = r10.handleKey(r2)     // Catch:{ Throwable -> 0x0030 }
            r2 = r1
        L_0x002e:
            r1 = r3
            goto L_0x0042
        L_0x0030:
            r1 = move-exception
            goto L_0x0036
        L_0x0032:
            r3 = move-exception
            r7 = r3
            r3 = r1
            r1 = r7
        L_0x0036:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0056 }
            java.lang.String r5 = "MonitorUtils"
            java.lang.String r6 = "fillBufferWithParams.inner"
            r4.error(r5, r6, r1)     // Catch:{ Throwable -> 0x0056 }
            goto L_0x002e
        L_0x0042:
            if (r1 == 0) goto L_0x0013
        L_0x0044:
            java.lang.String r3 = ", "
            r8.append(r3)     // Catch:{ Throwable -> 0x0056 }
            r8.append(r1)     // Catch:{ Throwable -> 0x0056 }
            java.lang.String r1 = ": "
            r8.append(r1)     // Catch:{ Throwable -> 0x0056 }
            r8.append(r2)     // Catch:{ Throwable -> 0x0056 }
            goto L_0x0013
        L_0x0055:
            return
        L_0x0056:
            r8 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r9 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r10 = "MonitorUtils"
            java.lang.String r0 = "fillBufferWithParams.outer"
            r9.error(r10, r0, r8)
            return
        L_0x0063:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.monitor.util.MonitorUtils.fillBufferWithParams(java.lang.StringBuilder, android.os.Bundle, com.alipay.mobile.monitor.util.MonitorUtils$FillBufferHandler):void");
    }

    public static String fliterChar(String str) {
        return !TextUtils.isEmpty(str) ? str.replace(MultipartUtility.LINE_FEED, "###").replace("\n", "###").replace("\r", "###") : str;
    }

    public static boolean isProcessStartByClickLauncherIcon() {
        if (!LoggerFactory.getProcessInfo().isMainProcess()) {
            return false;
        }
        Map<String, String> startupReason = LoggerFactory.getProcessInfo().getStartupReason();
        if (startupReason == null) {
            return false;
        }
        return "com.eg.android.AlipayGphone.AlipayLogin".equals(startupReason.get(ProcessInfo.SR_COMPONENT_NAME));
    }

    public static boolean loadLibrary(Context context, String str) {
        String str2;
        String absolutePath;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            System.loadLibrary(str);
            LoggerFactory.getTraceLogger().info(TAG, "success to System.loadLibrary : ".concat(String.valueOf(str)));
            return true;
        } catch (Throwable th) {
            th = th;
            str2 = absolutePath;
            LoggerFactory.getTraceLogger().error(TAG, "failed to System.load : ".concat(String.valueOf(str2)), th);
            return false;
        }
    }

    public static String getTopActivity() {
        String str = null;
        try {
            List<RunningTaskInfo> runningTasks = ((ActivityManager) LoggerFactory.getLogContext().getApplicationContext().getSystemService(WidgetType.ACTIVITY)).getRunningTasks(3);
            if (runningTasks != null && runningTasks.size() > 0) {
                String str2 = null;
                for (RunningTaskInfo next : runningTasks) {
                    if (next != null && next.topActivity.getPackageName().equals(LoggerFactory.getProcessInfo().getPackageName())) {
                        str2 = next.topActivity.getClassName();
                    }
                }
                str = str2;
            }
            return str;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static Pair<String, String> backTrackInvoker() {
        return backTrackInvoker(2);
    }

    public static Pair<String, String> backTrackInvoker(int i) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace == null) {
            return Pair.create("", "");
        }
        if (i <= 0) {
            i = 1;
        }
        int i2 = i + 3;
        if (i2 >= stackTrace.length) {
            i2 = stackTrace.length - 1;
        }
        StackTraceElement stackTraceElement = stackTrace[i2];
        if (stackTraceElement == null) {
            return Pair.create("", "");
        }
        return Pair.create(stackTraceElement.getClassName(), stackTraceElement.getMethodName());
    }

    public static String[] obtainThreadsStackTrace() {
        return getThreadsStackTrace(true);
    }

    public static String acquireThreadsStackTrace() {
        return getThreadsStackTrace(false)[0];
    }

    public static String[] getThreadsStackTrace(boolean z) {
        StringBuilder sb = new StringBuilder(40000);
        StringBuilder sb2 = z ? new StringBuilder() : null;
        try {
            boolean z2 = true;
            for (Entry next : Thread.getAllStackTraces().entrySet()) {
                try {
                    StackTraceElement[] stackTraceElementArr = (StackTraceElement[]) next.getValue();
                    String name = ((Thread) next.getKey()).getName();
                    sb.append(10);
                    sb.append("ThreadName=");
                    sb.append(name);
                    sb.append("\n");
                    if (sb2 != null) {
                        if (z2) {
                            z2 = false;
                        } else {
                            sb2.append(MergeUtil.SEPARATOR_KV);
                        }
                        sb2.append(name);
                    }
                    for (StackTraceElement valueOf : stackTraceElementArr) {
                        sb.append(String.valueOf(valueOf));
                        sb.append("\n");
                    }
                    sb.append("\n");
                } catch (Throwable unused) {
                }
            }
        } catch (Throwable unused2) {
        }
        return new String[]{sb.toString(), String.valueOf(sb2)};
    }

    public static String getZhizhiSetting(Context context, String str, String str2) {
        if (context == null || TextUtils.isEmpty(str)) {
            return str2;
        }
        Cursor cursor = null;
        try {
            Cursor query = context.getContentResolver().query(Uri.parse(str), null, null, null, null);
            if (query != null) {
                try {
                    if (query.getCount() > 0) {
                        query.moveToFirst();
                        str2 = query.getString(0);
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = query;
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (query != null && !query.isClosed()) {
                try {
                    query.close();
                } catch (Throwable unused) {
                }
            }
        } catch (Throwable th2) {
            th = th2;
            LoggerFactory.getTraceLogger().error(TAG, "getZhizhiSetting", th);
            cursor.close();
            TraceLogger traceLogger = LoggerFactory.getTraceLogger();
            StringBuilder sb = new StringBuilder("getZhizhiSetting, uri: ");
            sb.append(str);
            sb.append(", value: ");
            sb.append(str2);
            traceLogger.info(TAG, sb.toString());
            return str2;
        }
        TraceLogger traceLogger2 = LoggerFactory.getTraceLogger();
        StringBuilder sb2 = new StringBuilder("getZhizhiSetting, uri: ");
        sb2.append(str);
        sb2.append(", value: ");
        sb2.append(str2);
        traceLogger2.info(TAG, sb2.toString());
        return str2;
    }

    public static String concatArray(String str, Object... objArr) {
        if (objArr == null || objArr.length == 0) {
            return "";
        }
        if (objArr.length == 1) {
            return String.valueOf(objArr[0]);
        }
        if (objArr.length == 2) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(objArr[0]));
            sb.append(str);
            sb.append(String.valueOf(objArr[1]));
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        boolean z = true;
        for (Object obj : objArr) {
            if (z) {
                z = false;
            } else {
                sb2.append(str);
            }
            sb2.append(obj);
        }
        return sb2.toString();
    }

    public static boolean isScreenOn(Context context) {
        try {
            PowerManager powerManager = (PowerManager) context.getSystemService("power");
            if (powerManager != null && !powerManager.isScreenOn()) {
                return false;
            }
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error(TAG, "isScreenOn error", th);
        }
        return true;
    }

    public static String stackTraceToString(String str) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("-StackTrace:");
        for (StackTraceElement append : stackTrace) {
            sb.append("###\tat ");
            sb.append(append);
        }
        sb.append("###");
        return sb.toString();
    }

    public static String getNowTime() {
        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
        StringBuilder sb = new StringBuilder();
        int i = instance.get(1);
        int i2 = instance.get(2) + 1;
        int i3 = instance.get(5);
        int i4 = instance.get(11);
        int i5 = instance.get(12);
        int i6 = instance.get(13);
        int i7 = instance.get(14);
        sb.append(i);
        sb.append('-');
        if (i2 < 10) {
            sb.append('0');
        }
        sb.append(i2);
        sb.append('-');
        if (i3 < 10) {
            sb.append('0');
        }
        sb.append(i3);
        sb.append(' ');
        if (i4 < 10) {
            sb.append('0');
        }
        sb.append(i4);
        sb.append(':');
        if (i5 < 10) {
            sb.append('0');
        }
        sb.append(i5);
        sb.append(':');
        if (i6 < 10) {
            sb.append('0');
        }
        sb.append(i6);
        sb.append(':');
        if (i7 < 100) {
            sb.append('0');
        }
        if (i7 < 10) {
            sb.append('0');
        }
        sb.append(i7);
        return sb.toString();
    }

    public static boolean isDebuggable() {
        if (sDebuggableFlag < 0) {
            Context applicationContext = LoggerFactory.getLogContext().getApplicationContext();
            if (applicationContext == null) {
                return false;
            }
            try {
                sDebuggableFlag = (applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), 16384).flags & 2) == 2 ? 1 : 0;
            } catch (Throwable unused) {
                sDebuggableFlag = 0;
            }
        }
        return sDebuggableFlag == 1;
    }
}
