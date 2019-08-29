package com.amap.bundle.statistics.log;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteBlobTooBigException;
import android.text.TextUtils;
import android.util.Log;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.db.LogContentDao.Properties;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import java.io.DataOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadPoolExecutor;

public enum LogRecorder {
    INSTANCE;
    
    private static final String LOG_TYPE_HISTORY = "history";
    private static final String LOG_TYPE_NORMAL = "normal";
    private static final String LOG_TYPE_WAKEUP = "wakeup";
    private static final int LOG_UPDATE_LIMIT_COUNT = 500;
    private static final String TAG = "LogRecorder";
    private static long beginTime;
    private final int LIM_MAX_COUNT_IN_OTHER;
    private final int LIM_MAX_COUNT_IN_WIFI;
    private final long MAX_LOG_FILE_SIZE;
    /* access modifiers changed from: private */
    public List<afg> cacheLogContent;
    private Context mContext;
    private String mDebugLogName;
    private afl mHead;
    private String mLogPath;
    /* access modifiers changed from: private */
    public boolean mLogTestModeSwitch;
    private SharedPreferences mPreference;
    private String mSeparator;
    private String mStartInitTimeLogName;
    /* access modifiers changed from: private */
    public ThreadPoolExecutor mTaskExecutor;
    private final int msgType;

    final class a implements Runnable {
        afg a;

        a(afg afg) {
            this.a = afg;
        }

        private void a(String str) {
            if (NetworkReachability.b()) {
                List d = LogRecorder.this.cacheLogContent;
                LogRecorder.this.cacheLogContent = new ArrayList();
                LogRecorder.this.uploadLog(d, str);
            }
        }

        public final void run() {
            try {
                if (this.a != null) {
                    if (this.a.b.equals("LogConstant")) {
                        boolean a2 = NetworkReachability.a();
                        boolean z = LogRecorder.this.getIntradayLogSize() < 143360;
                        if (LogRecorder.this.cacheLogContent.size() > 0 && (a2 || z)) {
                            a(LogRecorder.LOG_TYPE_WAKEUP);
                        }
                        return;
                    }
                    AMapAppGlobal.getApplication();
                    aff.a().a.insert(this.a);
                    if (NetworkReachability.b()) {
                        LogRecorder.this.cacheLogContent.add(this.a);
                        if (NetworkReachability.a()) {
                            if (LogRecorder.this.cacheLogContent.size() >= 10) {
                                a("normal");
                            }
                        } else if (LogRecorder.this.getIntradayLogSize() <= 143360 && LogRecorder.this.cacheLogContent.size() >= 40) {
                            a("normal");
                        }
                    } else if (LogRecorder.this.cacheLogContent.size() < 40) {
                        LogRecorder.this.cacheLogContent.add(this.a);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class b implements Runnable {
        private List<afg> b;
        private boolean c;

        b(List<afg> list, boolean z) {
            this.c = false;
            this.b = list;
            this.c = z;
        }

        public final void run() {
            try {
                AMapAppGlobal.getApplication();
                aff a2 = aff.a();
                if (this.b != null && this.b.size() > 0) {
                    a2.a(this.b);
                }
                if (this.c && NetworkReachability.a() && a2.a.count() > 500) {
                    LogRecorder.this.startUploadHistoryLogTask();
                }
            } catch (Throwable unused) {
            }
        }
    }

    class c implements Runnable {
        private c() {
        }

        /* synthetic */ c(LogRecorder logRecorder, byte b) {
            this();
        }

        public final void run() {
            try {
                LogRecorder.this.initNetworkParams();
                AMapAppGlobal.getApplication();
                if (aff.a().a.count() > 0) {
                    LogRecorder.this.startUploadHistoryLogTask();
                }
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("init error:");
                sb.append(e.getMessage());
                AMapLog.error("paas.statistics", LogRecorder.TAG, sb.toString());
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    class d implements Runnable {
        private d() {
        }

        /* synthetic */ d(LogRecorder logRecorder, byte b) {
            this();
        }

        public final void run() {
            List<afg> list;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            AMapAppGlobal.getApplication();
            aff a2 = aff.a();
            try {
                list = a2.a.queryBuilder().orderAsc(Properties.e).limit(500).build().list();
            } catch (SQLiteBlobTooBigException e) {
                AMapLog.error("paas.statistics", LogRecorder.TAG, Log.getStackTraceString(e));
                list = null;
            }
            if (list != null || list.size() > 0) {
                for (afg afg : list) {
                    if (afg.a == null) {
                        a2.a.deleteAll();
                        return;
                    } else if (afg.e == null || LogRecorder.isOutOfTenDay(afg.e.longValue())) {
                        arrayList.add(afg);
                    } else {
                        arrayList2.add(afg);
                    }
                }
            }
            if (arrayList.size() > 0) {
                a2.a(arrayList);
            }
            if (arrayList2.size() > 0) {
                if (NetworkReachability.a()) {
                    LogRecorder.this.uploadLog(arrayList2, LogRecorder.LOG_TYPE_HISTORY);
                    return;
                }
                if (bno.a) {
                    StringBuilder sb = new StringBuilder("IntradayLogSize:");
                    sb.append(LogRecorder.this.getIntradayLogSize());
                    sb.append(",totalSize:143360");
                    AMapLog.d(LogRecorder.TAG, sb.toString());
                }
                if (LogRecorder.this.getIntradayLogSize() < 143360) {
                    if (arrayList2.size() <= 40) {
                        LogRecorder.this.uploadLog(arrayList2, LogRecorder.LOG_TYPE_HISTORY);
                        return;
                    }
                    ArrayList arrayList3 = new ArrayList();
                    for (int i = 0; i < 40; i++) {
                        arrayList3.add(arrayList2.get(i));
                    }
                    LogRecorder.this.uploadLog(arrayList3, LogRecorder.LOG_TYPE_HISTORY);
                }
            }
        }
    }

    public static LogRecorder getInstance() {
        return INSTANCE;
    }

    public final void startInitLogTask(Context context) {
        this.mContext = context.getApplicationContext();
        this.mTaskExecutor.execute(new c(this, 0));
    }

    public final void addActionLog(afg afg) {
        try {
            afg.e = Long.valueOf(getTime());
            afg.f = Long.valueOf(NetworkParam.getSession());
            if (afg.d == null || afg.d.longValue() == 0) {
                afg.d = Long.valueOf(NetworkParam.genStepId());
            }
            if ((afg.g == null || afg.g.intValue() == 0) && (afg.h == null || afg.h.intValue() == 0)) {
                GeoPoint b2 = afk.a().b();
                if (b2 != null) {
                    afg.g = Integer.valueOf(b2.x);
                    afg.h = Integer.valueOf(b2.y);
                }
            }
            if (this.mLogTestModeSwitch) {
                if (!afg.b.equals("LogConstant")) {
                    AMapLog.d(TAG, afg.toString());
                }
                recordStartInitTimeLogToFile(afg);
            }
            this.mTaskExecutor.execute(new a(afg));
        } catch (Throwable unused) {
        }
    }

    /* access modifiers changed from: private */
    public void startUploadHistoryLogTask() {
        this.mTaskExecutor.execute(new d(this, 0));
    }

    /* access modifiers changed from: private */
    public static boolean isOutOfTenDay(long j) {
        return Math.abs(System.currentTimeMillis() - (j + getBeginTime())) / 86400000 > 10;
    }

    /* access modifiers changed from: private */
    public void initNetworkParams() {
        try {
            afl afl = this.mHead;
            String diu = NetworkParam.getDiu();
            String mac = NetworkParam.getMac();
            String isn = NetworkParam.getIsn();
            String dic = NetworkParam.getDic();
            afl.b = (byte) diu.getBytes().length;
            afl.c = diu;
            if (mac != null) {
                afl.d = (byte) mac.getBytes().length;
            }
            afl.e = mac;
            if (isn != null) {
                afl.f = (byte) isn.getBytes().length;
            }
            afl.g = isn;
            if (dic != null) {
                afl.h = (byte) dic.getBytes().length;
            }
            afl.i = dic;
            afl afl2 = this.mHead;
            String div = NetworkParam.getDiv();
            String dibv = NetworkParam.getDibv();
            afl2.k = div;
            afl2.j = (byte) div.getBytes().length;
            afl2.m = dibv;
            afl2.l = (byte) dibv.getBytes().length;
            afl afl3 = this.mHead;
            String adiu = NetworkParam.getAdiu();
            if (TextUtils.isEmpty(adiu)) {
                afl3.p = 0;
            } else {
                afl3.p = (byte) adiu.getBytes().length;
            }
            afl3.q = adiu;
            String b2 = agm.b(AMapAppGlobal.getApplication());
            afl afl4 = this.mHead;
            if (TextUtils.isEmpty(b2)) {
                afl4.n = 0;
            } else {
                afl4.n = (byte) b2.getBytes().length;
            }
            afl4.o = b2;
            afk.b();
            afl afl5 = this.mHead;
            String a2 = afk.b().a();
            if (!TextUtils.isEmpty(a2)) {
                afl5.a = a2;
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("initNetworkParams error:");
            sb.append(e.getMessage());
            AMapLog.error("paas.statistics", TAG, sb.toString());
        }
    }

    /* access modifiers changed from: private */
    public String getPrintStackTraceString(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r2v0, types: [java.io.PrintWriter] */
    /* JADX WARNING: type inference failed for: r1v3, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r6v3, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r1v4, types: [java.io.PrintWriter] */
    /* JADX WARNING: type inference failed for: r6v4 */
    /* JADX WARNING: type inference failed for: r2v8, types: [java.io.PrintWriter] */
    /* JADX WARNING: type inference failed for: r6v5 */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r6v6 */
    /* JADX WARNING: type inference failed for: r6v7, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r1v17 */
    /* JADX WARNING: type inference failed for: r1v18 */
    /* JADX WARNING: type inference failed for: r1v19 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r6v8 */
    /* JADX WARNING: type inference failed for: r6v9 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r6v5
      assigns: []
      uses: []
      mth insns count: 78
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
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00d0 A[SYNTHETIC, Splitter:B:35:0x00d0] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00d5 A[Catch:{ IOException -> 0x00d8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00dc A[SYNTHETIC, Splitter:B:45:0x00dc] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00e1 A[Catch:{ IOException -> 0x00e5 }] */
    /* JADX WARNING: Unknown variable types count: 8 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void upload2TestPlatform(java.util.List<defpackage.afg> r6) {
        /*
            r5 = this;
            com.alibaba.fastjson.JSONArray r0 = new com.alibaba.fastjson.JSONArray
            r0.<init>()
            java.util.Iterator r6 = r6.iterator()
        L_0x0009:
            boolean r1 = r6.hasNext()
            if (r1 == 0) goto L_0x0059
            java.lang.Object r1 = r6.next()
            afg r1 = (defpackage.afg) r1
            if (r1 == 0) goto L_0x0009
            com.alibaba.fastjson.JSONObject r2 = new com.alibaba.fastjson.JSONObject
            r2.<init>()
            java.lang.String r3 = "pageId"
            java.lang.String r4 = r1.b
            r2.put(r3, r4)
            java.lang.String r3 = "btnId"
            java.lang.String r4 = r1.c
            r2.put(r3, r4)
            java.lang.String r3 = "param"
            java.lang.String r1 = r1.i
            r2.put(r3, r1)
            java.lang.String r1 = "imei"
            java.lang.String r3 = com.amap.bundle.network.request.param.NetworkParam.getDiu()
            r2.put(r1, r3)
            android.app.Application r1 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.pm.PackageInfo r1 = defpackage.ahk.b(r1)
            java.lang.String r3 = "pointVersion"
            if (r1 != 0) goto L_0x0049
            java.lang.String r1 = ""
            goto L_0x004b
        L_0x0049:
            java.lang.String r1 = r1.versionName
        L_0x004b:
            r2.put(r3, r1)
            java.lang.String r1 = "platform"
            java.lang.String r3 = "Android"
            r2.put(r1, r3)
            r0.add(r2)
            goto L_0x0009
        L_0x0059:
            java.lang.String r6 = r0.toJSONString()
            java.lang.String r0 = "track_upload_url"
            java.lang.String r0 = defpackage.aaf.b(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L_0x006b
            return
        L_0x006b:
            r1 = 0
            java.net.URL r2 = new java.net.URL     // Catch:{ Exception -> 0x00d9, all -> 0x00cc }
            r2.<init>(r0)     // Catch:{ Exception -> 0x00d9, all -> 0x00cc }
            java.net.URLConnection r0 = r2.openConnection()     // Catch:{ Exception -> 0x00d9, all -> 0x00cc }
            java.lang.String r2 = "accept"
            java.lang.String r3 = "*/*"
            r0.setRequestProperty(r2, r3)     // Catch:{ Exception -> 0x00d9, all -> 0x00cc }
            java.lang.String r2 = "connection"
            java.lang.String r3 = "Keep-Alive"
            r0.setRequestProperty(r2, r3)     // Catch:{ Exception -> 0x00d9, all -> 0x00cc }
            java.lang.String r2 = "user-agent"
            java.lang.String r3 = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"
            r0.setRequestProperty(r2, r3)     // Catch:{ Exception -> 0x00d9, all -> 0x00cc }
            java.lang.String r2 = "Content-Type"
            java.lang.String r3 = "application/x-www-form-urlencoded;charset=UTF-8"
            r0.setRequestProperty(r2, r3)     // Catch:{ Exception -> 0x00d9, all -> 0x00cc }
            r2 = 1
            r0.setDoOutput(r2)     // Catch:{ Exception -> 0x00d9, all -> 0x00cc }
            r0.setDoInput(r2)     // Catch:{ Exception -> 0x00d9, all -> 0x00cc }
            java.io.PrintWriter r2 = new java.io.PrintWriter     // Catch:{ Exception -> 0x00d9, all -> 0x00cc }
            java.io.OutputStream r3 = r0.getOutputStream()     // Catch:{ Exception -> 0x00d9, all -> 0x00cc }
            r2.<init>(r3)     // Catch:{ Exception -> 0x00d9, all -> 0x00cc }
            r2.print(r6)     // Catch:{ Exception -> 0x00da, all -> 0x00c8 }
            r2.flush()     // Catch:{ Exception -> 0x00da, all -> 0x00c8 }
            java.io.BufferedReader r6 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00da, all -> 0x00c8 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00da, all -> 0x00c8 }
            java.io.InputStream r0 = r0.getInputStream()     // Catch:{ Exception -> 0x00da, all -> 0x00c8 }
            r3.<init>(r0)     // Catch:{ Exception -> 0x00da, all -> 0x00c8 }
            r6.<init>(r3)     // Catch:{ Exception -> 0x00da, all -> 0x00c8 }
        L_0x00b6:
            java.lang.String r0 = r6.readLine()     // Catch:{ Exception -> 0x00c6, all -> 0x00c4 }
            if (r0 != 0) goto L_0x00b6
            r2.close()     // Catch:{ IOException -> 0x00c3 }
            r6.close()     // Catch:{ IOException -> 0x00c3 }
            return
        L_0x00c3:
            return
        L_0x00c4:
            r0 = move-exception
            goto L_0x00ca
        L_0x00c6:
            r1 = r6
            goto L_0x00da
        L_0x00c8:
            r0 = move-exception
            r6 = r1
        L_0x00ca:
            r1 = r2
            goto L_0x00ce
        L_0x00cc:
            r0 = move-exception
            r6 = r1
        L_0x00ce:
            if (r1 == 0) goto L_0x00d3
            r1.close()     // Catch:{ IOException -> 0x00d8 }
        L_0x00d3:
            if (r6 == 0) goto L_0x00d8
            r6.close()     // Catch:{ IOException -> 0x00d8 }
        L_0x00d8:
            throw r0
        L_0x00d9:
            r2 = r1
        L_0x00da:
            if (r2 == 0) goto L_0x00df
            r2.close()     // Catch:{ IOException -> 0x00e5 }
        L_0x00df:
            if (r1 == 0) goto L_0x00e6
            r1.close()     // Catch:{ IOException -> 0x00e5 }
            goto L_0x00e6
        L_0x00e5:
            return
        L_0x00e6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.statistics.log.LogRecorder.upload2TestPlatform(java.util.List):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x01f2 A[Catch:{ IOException -> 0x020d }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void uploadLog(java.util.List<defpackage.afg> r11, java.lang.String r12) {
        /*
            r10 = this;
            java.lang.String r0 = r10.mLogPath
            if (r0 != 0) goto L_0x0012
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            java.io.File r0 = defpackage.ahk.a(r0)
            java.lang.String r0 = r0.getAbsolutePath()
            r10.mLogPath = r0
        L_0x0012:
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r10.mLogPath
            r1.append(r2)
            java.lang.String r2 = "/"
            r1.append(r2)
            java.util.UUID r2 = java.util.UUID.randomUUID()
            java.lang.String r2 = r2.toString()
            r1.append(r2)
            java.lang.String r2 = ".logtmp"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            java.io.File r1 = r0.getParentFile()
            boolean r2 = defpackage.bno.a
            if (r2 == 0) goto L_0x0045
            r10.upload2TestPlatform(r11)
        L_0x0045:
            boolean r2 = r1.exists()
            if (r2 != 0) goto L_0x004e
            r1.mkdirs()
        L_0x004e:
            boolean r1 = r0.exists()
            if (r1 == 0) goto L_0x0057
            r0.delete()
        L_0x0057:
            afl r1 = r10.mHead     // Catch:{ IOException -> 0x020d }
            java.lang.String r1 = r1.k     // Catch:{ IOException -> 0x020d }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ IOException -> 0x020d }
            if (r1 == 0) goto L_0x0064
            r10.initNetworkParams()     // Catch:{ IOException -> 0x020d }
        L_0x0064:
            boolean r1 = defpackage.bno.a     // Catch:{ IOException -> 0x020d }
            if (r1 == 0) goto L_0x0075
            java.lang.String r1 = "paas.statistics"
            java.lang.String r2 = "LogRecorder"
            afl r3 = r10.mHead     // Catch:{ IOException -> 0x020d }
            java.lang.String r3 = r3.toString()     // Catch:{ IOException -> 0x020d }
            com.amap.bundle.logs.AMapLog.debug(r1, r2, r3)     // Catch:{ IOException -> 0x020d }
        L_0x0075:
            r0.createNewFile()     // Catch:{ IOException -> 0x020d }
            afl r1 = r10.mHead     // Catch:{ IOException -> 0x020d }
            r2 = 0
            boolean r3 = r0.exists()     // Catch:{ Exception -> 0x01dc }
            if (r3 != 0) goto L_0x0084
            r0.createNewFile()     // Catch:{ Exception -> 0x01dc }
        L_0x0084:
            java.io.DataOutputStream r3 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x01dc }
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x01dc }
            r4.<init>(r0)     // Catch:{ Exception -> 0x01dc }
            r3.<init>(r4)     // Catch:{ Exception -> 0x01dc }
            r2 = 8
            r3.writeByte(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            java.lang.String r2 = "1.0.13.8"
            r3.writeBytes(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            java.lang.String r2 = r1.c     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r4 = 0
            if (r2 != 0) goto L_0x00af
            java.lang.String r2 = r1.c     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            byte[] r2 = defpackage.ahj.a(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            int r5 = r2.length     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeByte(r5)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.write(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            goto L_0x00b2
        L_0x00af:
            r3.writeByte(r4)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
        L_0x00b2:
            byte r2 = r1.d     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 == 0) goto L_0x00c8
            java.lang.String r2 = r1.e     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 == 0) goto L_0x00c8
            java.lang.String r2 = r1.e     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            byte[] r2 = defpackage.ahj.a(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            int r5 = r2.length     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeByte(r5)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.write(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            goto L_0x00cb
        L_0x00c8:
            r3.writeByte(r4)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
        L_0x00cb:
            byte r2 = r1.f     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 == 0) goto L_0x00e1
            java.lang.String r2 = r1.g     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 == 0) goto L_0x00e1
            java.lang.String r2 = r1.g     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            byte[] r2 = defpackage.ahj.a(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            int r5 = r2.length     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeByte(r5)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.write(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            goto L_0x00e4
        L_0x00e1:
            r3.writeByte(r4)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
        L_0x00e4:
            byte r2 = r1.j     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 == 0) goto L_0x00f7
            java.lang.String r2 = r1.k     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 == 0) goto L_0x00f7
            byte r2 = r1.j     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeByte(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            java.lang.String r2 = r1.k     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeBytes(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            goto L_0x00fa
        L_0x00f7:
            r3.writeByte(r4)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
        L_0x00fa:
            byte r2 = r1.l     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 == 0) goto L_0x010d
            java.lang.String r2 = r1.m     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 == 0) goto L_0x010d
            byte r2 = r1.l     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeByte(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            java.lang.String r2 = r1.m     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeBytes(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            goto L_0x0110
        L_0x010d:
            r3.writeByte(r4)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
        L_0x0110:
            byte r2 = r1.h     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 == 0) goto L_0x0123
            java.lang.String r2 = r1.i     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 == 0) goto L_0x0123
            byte r2 = r1.h     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeByte(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            java.lang.String r2 = r1.i     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeBytes(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            goto L_0x0126
        L_0x0123:
            r3.writeByte(r4)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
        L_0x0126:
            java.lang.String r2 = android.os.Build.MODEL     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 == 0) goto L_0x0141
            java.lang.String r2 = android.os.Build.MODEL     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            int r2 = r2.length()     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 <= 0) goto L_0x0141
            java.lang.String r2 = android.os.Build.MODEL     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            int r2 = r2.length()     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeByte(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            java.lang.String r2 = android.os.Build.MODEL     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeBytes(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            goto L_0x0144
        L_0x0141:
            r3.writeByte(r4)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
        L_0x0144:
            java.lang.String r2 = android.os.Build.DEVICE     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 == 0) goto L_0x015f
            java.lang.String r2 = android.os.Build.DEVICE     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            int r2 = r2.length()     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 <= 0) goto L_0x015f
            java.lang.String r2 = android.os.Build.DEVICE     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            int r2 = r2.length()     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeByte(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            java.lang.String r2 = android.os.Build.DEVICE     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeBytes(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            goto L_0x0162
        L_0x015f:
            r3.writeByte(r4)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
        L_0x0162:
            java.lang.String r2 = android.os.Build.MANUFACTURER     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 == 0) goto L_0x017d
            java.lang.String r2 = android.os.Build.MANUFACTURER     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            int r2 = r2.length()     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 <= 0) goto L_0x017d
            java.lang.String r2 = android.os.Build.MANUFACTURER     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            int r2 = r2.length()     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeByte(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            java.lang.String r2 = android.os.Build.MANUFACTURER     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeBytes(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            goto L_0x0180
        L_0x017d:
            r3.writeByte(r4)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
        L_0x0180:
            byte r2 = r1.n     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 <= 0) goto L_0x0198
            java.lang.String r2 = r1.o     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 == 0) goto L_0x0198
            java.lang.String r2 = r1.o     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            byte[] r2 = defpackage.ahj.a(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            int r2 = r2.length     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeByte(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            java.lang.String r2 = r1.o     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeBytes(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            goto L_0x019b
        L_0x0198:
            r3.writeByte(r4)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
        L_0x019b:
            java.lang.String r2 = r1.a     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 != 0) goto L_0x01b2
            java.lang.String r2 = r1.a     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            int r2 = r2.length()     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeByte(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            java.lang.String r2 = r1.a     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeBytes(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            goto L_0x01b5
        L_0x01b2:
            r3.writeByte(r4)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
        L_0x01b5:
            byte r2 = r1.p     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 <= 0) goto L_0x01cd
            java.lang.String r2 = r1.q     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            if (r2 == 0) goto L_0x01cd
            java.lang.String r2 = r1.q     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            byte[] r2 = defpackage.ahj.a(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            int r2 = r2.length     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeByte(r2)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            java.lang.String r1 = r1.q     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            r3.writeBytes(r1)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
            goto L_0x01d0
        L_0x01cd:
            r3.writeByte(r4)     // Catch:{ Exception -> 0x01d6, all -> 0x01d4 }
        L_0x01d0:
            defpackage.ahe.a(r3)     // Catch:{ IOException -> 0x020d }
            goto L_0x01e3
        L_0x01d4:
            r11 = move-exception
            goto L_0x0209
        L_0x01d6:
            r1 = move-exception
            r2 = r3
            goto L_0x01dd
        L_0x01d9:
            r11 = move-exception
            r3 = r2
            goto L_0x0209
        L_0x01dc:
            r1 = move-exception
        L_0x01dd:
            r1.printStackTrace()     // Catch:{ all -> 0x01d9 }
            defpackage.ahe.a(r2)     // Catch:{ IOException -> 0x020d }
        L_0x01e3:
            android.content.Context r1 = r10.mContext     // Catch:{ IOException -> 0x020d }
            r10.writeBodyToFile(r11, r0, r1)     // Catch:{ IOException -> 0x020d }
            afn r1 = defpackage.afn.a()     // Catch:{ IOException -> 0x020d }
            java.io.File r0 = r1.b(r0)     // Catch:{ IOException -> 0x020d }
            if (r0 == 0) goto L_0x0208
            boolean r5 = com.amap.bundle.network.util.NetworkReachability.a()     // Catch:{ IOException -> 0x020d }
            afn r1 = defpackage.afn.a()     // Catch:{ IOException -> 0x020d }
            r8 = 1
            com.amap.bundle.statistics.log.LogRecorder$1 r9 = new com.amap.bundle.statistics.log.LogRecorder$1     // Catch:{ IOException -> 0x020d }
            r2 = r9
            r3 = r10
            r4 = r11
            r6 = r0
            r7 = r12
            r2.<init>(r4, r5, r6, r7)     // Catch:{ IOException -> 0x020d }
            r1.a(r0, r8, r9)     // Catch:{ IOException -> 0x020d }
        L_0x0208:
            return
        L_0x0209:
            defpackage.ahe.a(r3)     // Catch:{ IOException -> 0x020d }
            throw r11     // Catch:{ IOException -> 0x020d }
        L_0x020d:
            r11 = move-exception
            r11.printStackTrace()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.statistics.log.LogRecorder.uploadLog(java.util.List, java.lang.String):void");
    }

    private void recordStartInitTimeLogToFile(afg afg) {
        if (afg.b.trim().equals("P00000") && afg.c.trim().equals("B000")) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(this.mSeparator);
            StringBuilder sb = new StringBuilder("pageid = ");
            sb.append(afg.b);
            sb.append("/ buttonid = ");
            sb.append(afg.c);
            sb.append("/ param = ");
            sb.append(afg.i);
            stringBuffer.append(sb.toString());
            FileUtil.writeLogToFile(stringBuffer.toString(), this.mStartInitTimeLogName);
        }
    }

    /* access modifiers changed from: private */
    public void readTestLogToLocal(List<afg> list, String str, boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.mSeparator);
        StringBuilder sb = new StringBuilder();
        sb.append(getIntradayTime());
        sb.append("\n");
        stringBuffer.append(sb.toString());
        int size = list.size();
        StringBuilder sb2 = new StringBuilder("count :");
        sb2.append(size);
        sb2.append("\n");
        stringBuffer.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder("type :");
        sb3.append(str);
        sb3.append("\n");
        stringBuffer.append(sb3.toString());
        StringBuilder sb4 = new StringBuilder("IntradayLogSize :");
        sb4.append(getIntradayLogSize());
        sb4.append("\n");
        stringBuffer.append(sb4.toString());
        for (int i = 0; i < size; i++) {
            afg afg = list.get(i);
            StringBuilder sb5 = new StringBuilder();
            sb5.append(i);
            sb5.append(".time:");
            sb5.append(afg.e);
            sb5.append("\n");
            stringBuffer.append(sb5.toString());
            StringBuilder sb6 = new StringBuilder("pageid:");
            sb6.append(afg.b);
            sb6.append("\n");
            stringBuffer.append(sb6.toString());
            StringBuilder sb7 = new StringBuilder("buttonid:");
            sb7.append(afg.c);
            sb7.append("\n");
            stringBuffer.append(sb7.toString());
        }
        stringBuffer.append(this.mSeparator);
        StringBuffer stringBuffer2 = new StringBuffer();
        for (afg afg2 : list) {
            stringBuffer2.append(afg2.toString());
        }
        if (!z) {
            String stringBuffer3 = stringBuffer2.toString();
            StringBuilder sb8 = new StringBuilder();
            sb8.append(getNow());
            sb8.append(".txt");
            FileUtil.writeLogToFile(stringBuffer3, sb8.toString());
        }
        FileUtil.writeLogToFile(stringBuffer.toString(), this.mDebugLogName);
    }

    public static String getNow() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    private static long getTime() {
        return new Date().getTime() - getBeginTime();
    }

    private static long getBeginTime() {
        if (beginTime == 0) {
            try {
                beginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault()).parse("2011-01-01 00:00:00:000").getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return beginTime;
    }

    /* access modifiers changed from: private */
    public synchronized void saveIntradayLogSize(long j) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getIntradayTime());
        stringBuffer.append(",");
        stringBuffer.append(j);
        if (this.mPreference != null) {
            Editor edit = this.mPreference.edit();
            edit.putString("log_size_today", stringBuffer.toString());
            edit.apply();
        }
    }

    /* access modifiers changed from: private */
    public synchronized long getIntradayLogSize() {
        String string = this.mPreference != null ? this.mPreference.getString("log_size_today", "") : "";
        if (TextUtils.isEmpty(string)) {
            return 0;
        }
        String[] split = string.split(",");
        if (split.length <= 0) {
            return 0;
        }
        String str = split[0];
        long parseLong = Long.parseLong(split[1]);
        if (str.equals(getIntradayTime())) {
            return parseLong;
        }
        return 0;
    }

    private String getIntradayTime() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
    }

    private void writeBodyToFile(List<afg> list, File file, Context context) {
        if (file != null && file.exists() && list != null && list.size() != 0) {
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(context.openFileOutput(file.getName(), 32768));
                for (int i = 0; i < list.size(); i++) {
                    afg afg = list.get(i);
                    if (TextUtils.isEmpty(afg.b)) {
                        dataOutputStream.writeByte(0);
                    } else {
                        String str = afg.b;
                        dataOutputStream.writeByte(str.length());
                        dataOutputStream.writeBytes(str);
                    }
                    if (TextUtils.isEmpty(afg.c)) {
                        dataOutputStream.writeByte(0);
                    } else {
                        String str2 = afg.c;
                        dataOutputStream.writeByte(str2.length());
                        dataOutputStream.writeBytes(str2);
                    }
                    long j = 0;
                    dataOutputStream.writeLong(afg.e == null ? 0 : afg.e.longValue());
                    dataOutputStream.writeLong(afg.f == null ? 0 : afg.f.longValue());
                    if (afg.d != null) {
                        j = afg.d.longValue();
                    }
                    dataOutputStream.writeLong(j);
                    GeoPoint a2 = afk.a().a();
                    Integer valueOf = Integer.valueOf(a2.x);
                    afg.g = valueOf;
                    dataOutputStream.writeInt(valueOf.intValue());
                    Integer valueOf2 = Integer.valueOf(a2.y);
                    afg.h = valueOf2;
                    dataOutputStream.writeInt(valueOf2.intValue());
                    byte[] a3 = ahj.a(afg.i == null ? "" : afg.i);
                    if (a3 != null) {
                        dataOutputStream.writeShort(a3.length);
                        dataOutputStream.write(a3);
                    } else {
                        dataOutputStream.writeShort(0);
                    }
                }
                dataOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
