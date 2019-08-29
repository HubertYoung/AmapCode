package com.uc.webview.export.utility.download;

import android.content.Context;
import android.util.Pair;
import android.webkit.ValueCallback;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.nebula.filecache.FileCache;
import com.alipay.sdk.util.e;
import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import com.uc.webview.export.internal.setup.UCSetupException;
import com.uc.webview.export.internal.utility.j;
import java.io.File;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

@Api
/* compiled from: ProGuard */
public class UpdateTask {
    public static final String START_FLG_FILE_NAME = "299772b0fd1634653ae3c31f366de3f8";
    public static final String STOP_FLG_FILE_NAME = "2e67cdbeb4ec133dcc8204d930aa7145";
    private static final ConcurrentHashMap<Long, Long> a = new ConcurrentHashMap<>();
    private final String[] b = new String[3];
    /* access modifiers changed from: private */
    public final long[] c = new long[6];
    private final ValueCallback<UpdateTask>[] d = new ValueCallback[12];
    /* access modifiers changed from: private */
    public final Object[] e = new Object[3];
    /* access modifiers changed from: private */
    public int f = 0;
    /* access modifiers changed from: private */
    public int g = 0;
    /* access modifiers changed from: private */
    public String h = "core.jar";
    /* access modifiers changed from: private */
    public ValueCallback<Object[]> i;

    static /* synthetic */ void a(File file, boolean z) throws Exception {
        (z ? new File(file, STOP_FLG_FILE_NAME) : new File(file, START_FLG_FILE_NAME)).createNewFile();
    }

    public UpdateTask(Context context, String str, String str2, String str3, ValueCallback<Object[]> valueCallback, Long l, Long l2) {
        long j;
        l = l == null ? Long.valueOf(60000) : l;
        l2 = l2 == null ? Long.valueOf(FileCache.EXPIRE_TIME) : l2;
        int hashCode = str.hashCode();
        synchronized (a) {
            j = (long) hashCode;
            if (a.containsKey(Long.valueOf(j))) {
                throw new RuntimeException("Duplicate task.");
            }
            a.put(Long.valueOf(j), Long.valueOf(j));
        }
        String valueOf = hashCode >= 0 ? String.valueOf(hashCode) : String.valueOf(hashCode).replace('-', '_');
        WaStat.stat((String) IWaStat.UCM);
        this.i = valueCallback;
        this.c[0] = j;
        this.c[4] = l.longValue();
        this.c[5] = l2.longValue();
        this.b[0] = str;
        this.b[1] = str2;
        this.b[2] = valueOf;
        this.e[0] = context;
        this.e[2] = new DownloadTask(context, str, valueCallback);
        this.h = str3;
    }

    public static final File getUCPlayerRoot(Context context) throws UCSetupException {
        if (SDKFactory.x != null) {
            return new File(SDKFactory.x);
        }
        return (File) UCMPackageInfo.invoke(10001, context, "ucplayer");
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            synchronized (a) {
                a.remove(Long.valueOf(this.c[0]));
            }
        } catch (Throwable unused) {
        }
    }

    public UpdateTask onEvent(String str, ValueCallback<UpdateTask> valueCallback) {
        if (str.equals("success")) {
            this.d[0] = valueCallback;
        } else if (str.equals(e.b)) {
            this.d[1] = valueCallback;
        } else if (str.equals("recovered")) {
            this.d[2] = valueCallback;
        } else if (str.equals("progress")) {
            this.d[3] = valueCallback;
        } else if (str.equals(LogCategory.CATEGORY_EXCEPTION)) {
            this.d[4] = valueCallback;
        } else if (str.equals("check")) {
            this.d[5] = valueCallback;
        } else if (str.equals("exists")) {
            this.d[6] = valueCallback;
        } else if (str.equals("beginDownload")) {
            this.d[7] = valueCallback;
        } else if (str.equals("beginUnZip")) {
            this.d[8] = valueCallback;
        } else if (str.equals("unzipSuccess")) {
            this.d[9] = valueCallback;
        } else if (str.equals("downloadException")) {
            this.d[10] = valueCallback;
        } else if (str.equals("deleteDownFile")) {
            this.d[11] = valueCallback;
        } else {
            StringBuilder sb = new StringBuilder("The given event:");
            sb.append(str);
            sb.append(" is unknown.");
            throw new RuntimeException(sb.toString());
        }
        return this;
    }

    public final File getUpdateDir() throws UCSetupException {
        if (this.c[1] <= 0) {
            Pair<Long, Long> a2 = j.a(this.b[0], (URL) null);
            this.c[1] = ((Long) a2.first).longValue();
            this.c[2] = ((Long) a2.second).longValue();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(this.c[1]));
        sb.append("_");
        sb.append(this.c[2]);
        String sb2 = sb.toString();
        String str = this.b[2];
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.b[1]);
        sb3.append("/");
        sb3.append(str);
        sb3.append("/");
        sb3.append(sb2);
        return new File(sb3.toString());
    }

    public Throwable getException() {
        return (Throwable) this.e[1];
    }

    public UpdateTask start() {
        ValueCallback<UpdateTask> valueCallback = this.d[0];
        ValueCallback<UpdateTask> valueCallback2 = this.d[1];
        ValueCallback<UpdateTask> valueCallback3 = this.d[2];
        ValueCallback<UpdateTask> valueCallback4 = this.d[3];
        ValueCallback<UpdateTask> valueCallback5 = this.d[4];
        ValueCallback<UpdateTask> valueCallback6 = this.d[5];
        ValueCallback<UpdateTask> valueCallback7 = this.d[6];
        ValueCallback<UpdateTask> valueCallback8 = this.d[7];
        ValueCallback<UpdateTask> valueCallback9 = this.d[8];
        ValueCallback<UpdateTask> valueCallback10 = this.d[9];
        ValueCallback<UpdateTask> valueCallback11 = this.d[10];
        ValueCallback<UpdateTask> valueCallback12 = this.d[11];
        String str = this.b[0];
        this.f = 0;
        ValueCallback<UpdateTask> valueCallback13 = valueCallback3;
        d dVar = r0;
        DownloadTask downloadTask = (DownloadTask) this.e[2];
        ValueCallback<UpdateTask> valueCallback14 = valueCallback4;
        ValueCallback<UpdateTask> valueCallback15 = valueCallback4;
        d dVar2 = new d(this, valueCallback14, str, downloadTask, valueCallback2, valueCallback9, valueCallback10, valueCallback, valueCallback5);
        DownloadTask downloadTask2 = downloadTask;
        ValueCallback<UpdateTask> valueCallback16 = valueCallback15;
        DownloadTask onEvent = downloadTask2.onEvent("check", new e(this, valueCallback6)).onEvent("success", new o(this, dVar)).onEvent("exists", new n(this, dVar)).onEvent("delete", new m(this, valueCallback12)).onEvent(e.b, new l(this, valueCallback2)).onEvent("progress", new k(this, valueCallback16)).onEvent(LogCategory.CATEGORY_EXCEPTION, new i(this, valueCallback11, valueCallback5));
        h hVar = new h(this, valueCallback7, valueCallback13, valueCallback16, valueCallback5);
        onEvent.onEvent(Performance.KEY_LOG_HEADER, hVar).onEvent("beginDownload", new g(this, valueCallback8)).start();
        return this;
    }

    public UpdateTask stop() {
        ((DownloadTask) this.e[2]).stop();
        return this;
    }

    public UpdateTask startDownload() {
        ((DownloadTask) this.e[2]).start();
        return this;
    }

    public UpdateTask delete() {
        ((DownloadTask) this.e[2]).stopWith(new f(this));
        return this;
    }

    public static boolean isFinished(File file, String str) {
        return file.exists() && new File(file, str).exists() && ((!new File(file, START_FLG_FILE_NAME).exists() && !new File(file, "c34d62af061f389f7e4c9f0e835f7a54").exists()) || new File(file, STOP_FLG_FILE_NAME).exists() || new File(file, "95b70b3ec9f6407a92becf890996088d").exists());
    }

    public static File getUpdateRoot(Context context) throws Exception {
        return (File) UCMPackageInfo.invoke(10002, context);
    }

    public int getPercent() {
        return this.g;
    }

    public String getFilePath() {
        return ((DownloadTask) this.e[2]).getFilePath();
    }
}
