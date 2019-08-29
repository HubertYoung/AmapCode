package com.alipay.mobile.framework.app.monitor;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.quinox.asynctask.AsyncTaskExecutor;
import com.alipay.mobile.quinox.utils.MonitorLogger;
import com.alipay.mobile.quinox.utils.TraceLogger;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MemoryMonitor {
    private static MemoryMonitor a;
    /* access modifiers changed from: private */
    public static int c = -1;
    private boolean b = false;
    private Context d;
    /* access modifiers changed from: private */
    public Set<String> e;
    /* access modifiers changed from: private */
    public List<MemoryFamily> f;

    class MemoryFamily {
        Map<String, String> extParams = new HashMap();
        String familyName;
        List<MemoryUnit> memoryUnits = new ArrayList();

        class MemoryUnit {
            long memInfo = -1;
            String unitName;

            MemoryUnit(String unitName2) {
                this.unitName = unitName2;
            }

            /* access modifiers changed from: 0000 */
            public void recordMemInfo() {
                if (this.memInfo <= -1) {
                    this.memInfo = a();
                    TraceLogger.i((String) "FRAME.MemoryMonitor", "record unitName memInfo: " + this.unitName + " memInfo:" + this.memInfo);
                    return;
                }
                TraceLogger.i((String) "FRAME.MemoryMonitor", (String) "record unitName memInfo already had.");
            }

            private static long a() {
                try {
                    long start2 = SystemClock.elapsedRealtime();
                    long memInfo2 = b();
                    TraceLogger.i((String) "FRAME.MemoryMonitor", "readMemInfoFromProc costs: " + (SystemClock.elapsedRealtime() - start2) + " memInfo:" + memInfo2);
                    return memInfo2;
                } catch (Throwable tr) {
                    TraceLogger.w((String) "FRAME.MemoryMonitor", tr);
                    return -1;
                }
            }

            /* JADX WARNING: Removed duplicated region for block: B:41:0x0097 A[SYNTHETIC, Splitter:B:41:0x0097] */
            /* JADX WARNING: Removed duplicated region for block: B:44:0x009c A[SYNTHETIC, Splitter:B:44:0x009c] */
            /* JADX WARNING: Removed duplicated region for block: B:52:0x00b1 A[SYNTHETIC, Splitter:B:52:0x00b1] */
            /* JADX WARNING: Removed duplicated region for block: B:55:0x00b6 A[SYNTHETIC, Splitter:B:55:0x00b6] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            private static long b() {
                /*
                    r6 = -1
                    int r8 = com.alipay.mobile.framework.app.monitor.MemoryMonitor.c
                    if (r8 >= 0) goto L_0x000f
                    int r8 = android.os.Process.myPid()
                    com.alipay.mobile.framework.app.monitor.MemoryMonitor.c = r8
                L_0x000f:
                    r3 = 0
                    r0 = 0
                    java.io.FileReader r4 = new java.io.FileReader     // Catch:{ Throwable -> 0x008f }
                    java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x008f }
                    java.lang.String r9 = "/proc/"
                    r8.<init>(r9)     // Catch:{ Throwable -> 0x008f }
                    int r9 = com.alipay.mobile.framework.app.monitor.MemoryMonitor.c     // Catch:{ Throwable -> 0x008f }
                    java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x008f }
                    java.lang.String r9 = "/status"
                    java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x008f }
                    java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x008f }
                    r4.<init>(r8)     // Catch:{ Throwable -> 0x008f }
                    java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00cf, all -> 0x00c8 }
                    r1.<init>(r4)     // Catch:{ Throwable -> 0x00cf, all -> 0x00c8 }
                    java.lang.String r5 = r1.readLine()     // Catch:{ Throwable -> 0x00d2, all -> 0x00cb }
                L_0x0038:
                    boolean r8 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x00d2, all -> 0x00cb }
                    if (r8 != 0) goto L_0x0079
                    java.lang.String r8 = "VmRSS:"
                    boolean r8 = r5.startsWith(r8)     // Catch:{ Throwable -> 0x00d2, all -> 0x00cb }
                    if (r8 == 0) goto L_0x0074
                    java.lang.String r8 = "VmRSS:"
                    java.lang.String r9 = ""
                    java.lang.String r8 = r5.replace(r8, r9)     // Catch:{ Throwable -> 0x00d2, all -> 0x00cb }
                    java.lang.String r9 = "kB"
                    java.lang.String r10 = ""
                    java.lang.String r8 = r8.replace(r9, r10)     // Catch:{ Throwable -> 0x00d2, all -> 0x00cb }
                    java.lang.String r8 = r8.trim()     // Catch:{ Throwable -> 0x00d2, all -> 0x00cb }
                    long r6 = java.lang.Long.parseLong(r8)     // Catch:{ Throwable -> 0x00d2, all -> 0x00cb }
                    r4.close()     // Catch:{ Throwable -> 0x0066 }
                L_0x0061:
                    r1.close()     // Catch:{ Throwable -> 0x006d }
                L_0x0064:
                    r0 = r1
                L_0x0065:
                    return r6
                L_0x0066:
                    r2 = move-exception
                    java.lang.String r8 = "FRAME.MemoryMonitor"
                    com.alipay.mobile.quinox.utils.TraceLogger.w(r8, r2)
                    goto L_0x0061
                L_0x006d:
                    r2 = move-exception
                    java.lang.String r8 = "FRAME.MemoryMonitor"
                    com.alipay.mobile.quinox.utils.TraceLogger.w(r8, r2)
                    goto L_0x0064
                L_0x0074:
                    java.lang.String r5 = r1.readLine()     // Catch:{ Throwable -> 0x00d2, all -> 0x00cb }
                    goto L_0x0038
                L_0x0079:
                    r4.close()     // Catch:{ Throwable -> 0x0081 }
                L_0x007c:
                    r1.close()     // Catch:{ Throwable -> 0x0088 }
                L_0x007f:
                    r0 = r1
                    goto L_0x0065
                L_0x0081:
                    r2 = move-exception
                    java.lang.String r8 = "FRAME.MemoryMonitor"
                    com.alipay.mobile.quinox.utils.TraceLogger.w(r8, r2)
                    goto L_0x007c
                L_0x0088:
                    r2 = move-exception
                    java.lang.String r8 = "FRAME.MemoryMonitor"
                    com.alipay.mobile.quinox.utils.TraceLogger.w(r8, r2)
                    goto L_0x007f
                L_0x008f:
                    r2 = move-exception
                L_0x0090:
                    java.lang.String r8 = "FRAME.MemoryMonitor"
                    com.alipay.mobile.quinox.utils.TraceLogger.w(r8, r2)     // Catch:{ all -> 0x00ae }
                    if (r3 == 0) goto L_0x009a
                    r3.close()     // Catch:{ Throwable -> 0x00a7 }
                L_0x009a:
                    if (r0 == 0) goto L_0x0065
                    r0.close()     // Catch:{ Throwable -> 0x00a0 }
                    goto L_0x0065
                L_0x00a0:
                    r2 = move-exception
                    java.lang.String r8 = "FRAME.MemoryMonitor"
                    com.alipay.mobile.quinox.utils.TraceLogger.w(r8, r2)
                    goto L_0x0065
                L_0x00a7:
                    r2 = move-exception
                    java.lang.String r8 = "FRAME.MemoryMonitor"
                    com.alipay.mobile.quinox.utils.TraceLogger.w(r8, r2)
                    goto L_0x009a
                L_0x00ae:
                    r6 = move-exception
                L_0x00af:
                    if (r3 == 0) goto L_0x00b4
                    r3.close()     // Catch:{ Throwable -> 0x00ba }
                L_0x00b4:
                    if (r0 == 0) goto L_0x00b9
                    r0.close()     // Catch:{ Throwable -> 0x00c1 }
                L_0x00b9:
                    throw r6
                L_0x00ba:
                    r2 = move-exception
                    java.lang.String r7 = "FRAME.MemoryMonitor"
                    com.alipay.mobile.quinox.utils.TraceLogger.w(r7, r2)
                    goto L_0x00b4
                L_0x00c1:
                    r2 = move-exception
                    java.lang.String r7 = "FRAME.MemoryMonitor"
                    com.alipay.mobile.quinox.utils.TraceLogger.w(r7, r2)
                    goto L_0x00b9
                L_0x00c8:
                    r6 = move-exception
                    r3 = r4
                    goto L_0x00af
                L_0x00cb:
                    r6 = move-exception
                    r0 = r1
                    r3 = r4
                    goto L_0x00af
                L_0x00cf:
                    r2 = move-exception
                    r3 = r4
                    goto L_0x0090
                L_0x00d2:
                    r2 = move-exception
                    r0 = r1
                    r3 = r4
                    goto L_0x0090
                */
                throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.framework.app.monitor.MemoryMonitor.MemoryFamily.MemoryUnit.b():long");
            }
        }

        MemoryFamily(String familyName2) {
            this.familyName = familyName2;
        }

        /* access modifiers changed from: 0000 */
        public void recordUnit(String unit) {
            MemoryUnit targetMemoryUnit = null;
            Iterator<MemoryUnit> it = this.memoryUnits.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                MemoryUnit memoryUnit = it.next();
                if (unit.equals(memoryUnit.unitName)) {
                    targetMemoryUnit = memoryUnit;
                    break;
                }
            }
            if (targetMemoryUnit == null) {
                targetMemoryUnit = new MemoryUnit(unit);
                this.memoryUnits.add(targetMemoryUnit);
            }
            TraceLogger.i((String) "FRAME.MemoryMonitor", "record family: " + this.familyName + " unit:" + unit);
            targetMemoryUnit.recordMemInfo();
        }

        /* access modifiers changed from: 0000 */
        public void putExtParam(String unit, String key, String value) {
            if (!TextUtils.isEmpty(key)) {
                if (TextUtils.isEmpty(value)) {
                    this.extParams.remove(key);
                } else {
                    this.extParams.put(key, value);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void commit() {
            if (!TextUtils.isEmpty(this.familyName)) {
                MemoryMonitor.this.e.add(this.familyName);
                TraceLogger.i((String) "FRAME.MemoryMonitor", "commit and add reported family: " + this.familyName);
            }
            if (this.memoryUnits.size() <= 0) {
                TraceLogger.i((String) "FRAME.MemoryMonitor", "commit but no memoryUnits: " + this.familyName);
                return;
            }
            TraceLogger.i((String) "FRAME.MemoryMonitor", "commit family:" + this.familyName);
            Map params = new HashMap();
            for (MemoryUnit memoryUnit : this.memoryUnits) {
                if (memoryUnit.memInfo > 0) {
                    params.put(memoryUnit.unitName + "_totalPss", String.valueOf(memoryUnit.memInfo));
                    TraceLogger.i((String) "FRAME.MemoryMonitor", "unit:" + memoryUnit.unitName + " totalPss:" + memoryUnit.memInfo);
                }
            }
            for (Entry entry : this.extParams.entrySet()) {
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    params.put(key, value);
                }
            }
            MonitorLogger.performance(MonitorLogger.MONITORPOINT_PERFORMANCE, MonitorLogger.invokePerformanceBuilder(MonitorLogger.createPerformanceBuilderObject(), "FRAME.MemoryMonitor", "FamilyMemory", this.familyName, null, params));
        }
    }

    public static MemoryMonitor getInstance(Context context) {
        if (a == null) {
            synchronized (MemoryMonitor.class) {
                try {
                    if (a == null) {
                        a = new MemoryMonitor(context);
                    }
                }
            }
        }
        return a;
    }

    private MemoryMonitor(Context context) {
        if (context == null) {
            this.d = LauncherApplicationAgent.getInstance().getApplicationContext();
        } else if (context.getApplicationContext() != null) {
            this.d = context.getApplicationContext();
        } else {
            this.d = context;
        }
        this.e = new HashSet();
        this.f = new ArrayList();
        TraceLogger.i((String) "FRAME.MemoryMonitor", (String) "Create MemoryMonitor instance.");
    }

    public void setEnable(boolean enable) {
        this.b = enable;
        TraceLogger.i((String) "FRAME.MemoryMonitor", "setEnable: " + this.b);
    }

    public void record(final String family, final String unit) {
        if (!this.b) {
            TraceLogger.i((String) "FRAME.MemoryMonitor", "record but not Enable: " + family + Token.SEPARATOR + unit);
        } else if (TextUtils.isEmpty(family) || TextUtils.isEmpty(unit)) {
            TraceLogger.i((String) "FRAME.MemoryMonitor", "record but not valid: " + family + Token.SEPARATOR + unit);
        } else {
            AsyncTaskExecutor.getInstance().executeSerially((String) "FRAME.MemoryMonitor", (Runnable) new Runnable() {
                public void run() {
                    try {
                        if (MemoryMonitor.this.e.contains(family)) {
                            TraceLogger.i((String) "FRAME.MemoryMonitor", "record but already reported: " + family + Token.SEPARATOR + unit);
                            return;
                        }
                        MemoryFamily targetMemoryFamily = null;
                        Iterator it = MemoryMonitor.this.f.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            MemoryFamily memoryFamily = (MemoryFamily) it.next();
                            if (family.equals(memoryFamily.familyName)) {
                                targetMemoryFamily = memoryFamily;
                                break;
                            }
                        }
                        if (targetMemoryFamily == null) {
                            targetMemoryFamily = new MemoryFamily(family);
                            while (MemoryMonitor.this.f.size() >= 10) {
                                MemoryFamily oldestMemoryFamily = (MemoryFamily) MemoryMonitor.this.f.remove(0);
                                if (oldestMemoryFamily != null) {
                                    TraceLogger.i((String) "FRAME.MemoryMonitor", "record exceed limit 10, remove oldest family: " + oldestMemoryFamily.familyName);
                                }
                            }
                            MemoryMonitor.this.f.add(targetMemoryFamily);
                        }
                        TraceLogger.i((String) "FRAME.MemoryMonitor", "record family: " + targetMemoryFamily.familyName);
                        targetMemoryFamily.recordUnit(unit);
                    } catch (Throwable tr) {
                        TraceLogger.w((String) "FRAME.MemoryMonitor", tr);
                    }
                }
            }, (String) "FRAME.MemoryMonitor");
        }
    }

    public void commit(final String family) {
        if (!this.b) {
            TraceLogger.i((String) "FRAME.MemoryMonitor", "commit but not enabled " + family);
        } else if (TextUtils.isEmpty(family)) {
            TraceLogger.i((String) "FRAME.MemoryMonitor", "commit but not valid " + family);
        } else {
            AsyncTaskExecutor.getInstance().executeSerially((String) "FRAME.MemoryMonitor", (Runnable) new Runnable() {
                public void run() {
                    try {
                        if (MemoryMonitor.this.e.contains(family)) {
                            TraceLogger.i((String) "FRAME.MemoryMonitor", "commit but already Reported: " + family);
                            return;
                        }
                        MemoryFamily targetMemoryFamily = null;
                        Iterator it = MemoryMonitor.this.f.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            MemoryFamily memoryFamily = (MemoryFamily) it.next();
                            if (family.equals(memoryFamily.familyName)) {
                                targetMemoryFamily = memoryFamily;
                                break;
                            }
                        }
                        if (targetMemoryFamily != null) {
                            MemoryMonitor.this.f.remove(targetMemoryFamily);
                            targetMemoryFamily.commit();
                            return;
                        }
                        TraceLogger.i((String) "FRAME.MemoryMonitor", "commit family but not found: " + family);
                    } catch (Throwable tr) {
                        TraceLogger.w((String) "FRAME.MemoryMonitor", tr);
                    }
                }
            }, (String) "FRAME.MemoryMonitor");
        }
    }

    public void putExternalParams(String family, String unit, String key, String value) {
        if (!this.b) {
            TraceLogger.i((String) "FRAME.MemoryMonitor", "putExternalParams but not enabled " + family);
        } else if (TextUtils.isEmpty(family) || TextUtils.isEmpty(key)) {
            TraceLogger.i((String) "FRAME.MemoryMonitor", "commit but params not valid " + family);
        } else {
            final String str = family;
            final String str2 = unit;
            final String str3 = key;
            final String str4 = value;
            AsyncTaskExecutor.getInstance().executeSerially((String) "FRAME.MemoryMonitor", (Runnable) new Runnable() {
                public void run() {
                    MemoryFamily targetMemoryFamily = null;
                    try {
                        Iterator it = MemoryMonitor.this.f.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            MemoryFamily memoryFamily = (MemoryFamily) it.next();
                            if (str.equals(memoryFamily.familyName)) {
                                targetMemoryFamily = memoryFamily;
                                break;
                            }
                        }
                        if (targetMemoryFamily != null) {
                            targetMemoryFamily.putExtParam(str2, str3, str4);
                        } else {
                            TraceLogger.i((String) "FRAME.MemoryMonitor", "putExternalParams family but not found: " + str);
                        }
                    } catch (Throwable tr) {
                        TraceLogger.w((String) "FRAME.MemoryMonitor", tr);
                    }
                }
            }, (String) "FRAME.MemoryMonitor");
        }
    }
}
