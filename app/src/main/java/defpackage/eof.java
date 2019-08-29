package defpackage;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.HttpFileUpMMTask;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.request.param.NetworkParam;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* renamed from: eof reason: default package */
/* compiled from: QAInfoCollectManager */
public final class eof {
    public static BlockingQueue a = new ArrayBlockingQueue(20000);
    public static ConcurrentHashMap<String, Boolean> b = new ConcurrentHashMap<>(10000);
    public static String c = "RecordState";
    public static String d = "RecordCase";
    private static volatile HandlerThread e = null;
    /* access modifiers changed from: private */
    public static a f = null;
    private static Context g = null;
    private static eof h = null;
    /* access modifiers changed from: private */
    public static String i = "QAInfoCollectManager";
    /* access modifiers changed from: private */
    public static boolean j = true;
    private static long k = 300000;
    private static final ScheduledExecutorService l = Executors.newScheduledThreadPool(5);
    /* access modifiers changed from: private */
    public static boolean m = false;
    private static ReentrantReadWriteLock n = null;
    private static Lock o = null;
    private static Lock p = n.readLock();
    private static TimerTask q = new TimerTask() {
        public final void run() {
            new eoi();
            boolean a = eoi.a();
            if (eof.j && a) {
                Message message = new Message();
                message.what = 1;
                eof.f.sendMessage(message);
                eof.i;
            }
        }
    };
    private static TimerTask r = new TimerTask() {
        public final void run() {
            new eoi();
            if (eoi.a()) {
                Message message = new Message();
                message.what = 3;
                eof.f.sendMessage(message);
                eof.i;
            }
        }
    };
    /* access modifiers changed from: private */
    public static String s = "";
    /* access modifiers changed from: private */
    public static long t = 0;
    /* access modifiers changed from: private */
    public static boolean u = false;
    /* access modifiers changed from: private */
    public static Runnable v = new Runnable() {
        public final void run() {
            if (!eof.u) {
                AMapLog.d(eof.i, "upload begin");
                File file = new File(eof.u());
                if (!file.exists() || !file.isDirectory()) {
                    AMapLog.d(eof.i, "无case录制文件夹");
                } else {
                    File[] listFiles = file.listFiles();
                    if (listFiles != null) {
                        for (File file2 : listFiles) {
                            if (eof.s == null || eof.s.equals("") || !file2.getAbsolutePath().contains(eof.s)) {
                                if (!eof.g()) {
                                    AMapLog.d(eof.i, "upload no wifi ");
                                } else if (eoe.a(file2, (String) "record")) {
                                    eof.a(file2.getAbsolutePath());
                                    String j = eof.i;
                                    StringBuilder sb = new StringBuilder("upload success: ");
                                    sb.append(file2.getName());
                                    AMapLog.d(j, sb.toString());
                                } else {
                                    String j2 = eof.i;
                                    StringBuilder sb2 = new StringBuilder("upload fail: ");
                                    sb2.append(file2.getName());
                                    AMapLog.d(j2, sb2.toString());
                                }
                            }
                        }
                    } else {
                        AMapLog.d(eof.i, "文件夹无记录文件");
                    }
                }
                eof.u = false;
            }
        }
    };

    /* renamed from: eof$a */
    /* compiled from: QAInfoCollectManager */
    public static final class a extends Handler {
        a(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            if (message.what == 1) {
                if (!eof.m) {
                    StringBuffer stringBuffer = new StringBuffer();
                    try {
                        ArrayList<String> arrayList = new ArrayList<>();
                        eof.a.drainTo(arrayList);
                        eof.i;
                        new StringBuilder("list：").append(arrayList.size());
                        for (String str : arrayList) {
                            if (eof.j) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(str);
                                sb.append("\n");
                                stringBuffer.append(sb.toString());
                            }
                        }
                        if (!eof.j || !eof.e()) {
                            eof.i;
                            return;
                        }
                        eof.i;
                        long currentTimeMillis = System.currentTimeMillis();
                        if (eof.t == 0) {
                            eof.t = currentTimeMillis;
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(eof.u());
                        sb2.append(File.separator);
                        sb2.append("record_");
                        sb2.append(eof.t());
                        sb2.append("_");
                        sb2.append(eof.f());
                        sb2.append("_");
                        sb2.append(eof.t);
                        String sb3 = sb2.toString();
                        if (new File(sb3).length() > HttpFileUpMMTask.BIG_FILE_SIZE_THRESHOLD) {
                            eof.t = System.currentTimeMillis();
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(eof.u());
                            sb4.append(File.separator);
                            sb4.append("record_");
                            sb4.append(eof.t());
                            sb4.append("_");
                            sb4.append(eof.f());
                            sb4.append("_");
                            sb4.append(eof.t);
                            sb3 = sb4.toString();
                        }
                        eof.s = eof.f();
                        eom.a(new File(sb3), stringBuffer.toString());
                        eof.i;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    eof.i;
                    eof.a.drainTo(new ArrayList());
                }
            } else if (message.what == 2) {
                eof.i;
                eof.o();
            } else {
                if (message.what == 3) {
                    eof.i;
                    Executors.newCachedThreadPool().execute(eof.v);
                }
            }
        }
    }

    static {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        n = reentrantReadWriteLock;
        o = reentrantReadWriteLock.writeLock();
    }

    public static synchronized eof a(Context context) {
        eof eof;
        synchronized (eof.class) {
            try {
                if (h == null) {
                    h = new eof();
                    g = context;
                    HandlerThread handlerThread = new HandlerThread("QAInfoCollectManager_thread");
                    e = handlerThread;
                    handlerThread.start();
                    f = new a(e.getLooper());
                    l.scheduleAtFixedRate(q, 10, 1000, TimeUnit.MILLISECONDS);
                    l.scheduleAtFixedRate(r, 2000, k, TimeUnit.MILLISECONDS);
                }
                eof = h;
            } catch (Throwable th) {
                throw th;
            }
        }
        return eof;
    }

    private eof() {
    }

    public static void a() {
        m = true;
        try {
            o.lock();
            b.clear();
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            o.unlock();
            throw th;
        }
        o.unlock();
    }

    public static List<String> b() {
        m = false;
        long currentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        try {
            o.lock();
            Set<String> keySet = b.keySet();
            if (keySet != null) {
                StringBuilder sb = new StringBuilder("");
                String uuid = UUID.randomUUID().toString();
                sb.append(uuid);
                for (String next : keySet) {
                    if (sb.length() > 10000) {
                        arrayList.add(sb.toString());
                        sb.setLength(0);
                        sb.append(uuid);
                    }
                    sb.append(AUScreenAdaptTool.PREFIX_ID);
                    sb.append(next);
                }
                arrayList.add(sb.toString());
            }
            b.clear();
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            o.unlock();
            throw th;
        }
        o.unlock();
        new StringBuilder("获取:时间：").append(System.currentTimeMillis() - currentTimeMillis);
        return arrayList;
    }

    /* access modifiers changed from: private */
    public static String t() {
        try {
            return g.getPackageManager().getPackageInfo(g.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e2) {
            e2.printStackTrace();
            r0 = "";
            return "";
        }
    }

    /* access modifiers changed from: private */
    public static String u() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append("QACaseInfo");
        return sb.toString();
    }

    public static void c() {
        Message message = new Message();
        message.what = 2;
        f.sendMessage(message);
    }

    public static void a(String str) {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        }
    }

    static String d() {
        StringBuilder sb = new StringBuilder();
        sb.append(u());
        sb.append(File.separator);
        sb.append("AJX_");
        sb.append(t());
        sb.append("_");
        sb.append(f());
        sb.append("_");
        sb.append(NetworkParam.getDiu());
        return sb.toString();
    }

    public static void a(boolean z) {
        try {
            Editor edit = new MapSharePreference(SharePreferenceName.SharedPreferences).edit();
            edit.putBoolean(c, z);
            edit.commit();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void b(String str) {
        if (str != null) {
            try {
                if (str.length() > 0) {
                    Editor edit = new MapSharePreference(SharePreferenceName.SharedPreferences).edit();
                    edit.putString(d, str);
                    edit.commit();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static boolean e() {
        try {
            MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
            if (mapSharePreference.contains(c)) {
                return mapSharePreference.getBooleanValue(c, false);
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static String f() {
        try {
            MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
            if (mapSharePreference.contains(d)) {
                return mapSharePreference.getStringValue(d, "");
            }
            return "";
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static boolean g() {
        return ((ConnectivityManager) g.getSystemService("connectivity")).getNetworkInfo(1).isConnected();
    }

    static /* synthetic */ void o() {
        File file = new File(u());
        try {
            if (!file.exists() || !file.isDirectory()) {
                AMapLog.d(i, "无case录制文件夹");
                return;
            }
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    String f2 = f();
                    if (f2 != null && !f2.equals("") && file2.getName().contains(f2)) {
                        a(file2.getAbsolutePath());
                        AMapLog.d(i, "删除文件：".concat(String.valueOf(file2)));
                    }
                }
                return;
            }
            AMapLog.d(i, "文件夹无记录文件");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
