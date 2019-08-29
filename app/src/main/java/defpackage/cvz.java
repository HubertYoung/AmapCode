package defpackage;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.HttpFileUpMMTask;
import com.alipay.mobile.nebula.filecache.FileCache;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.blutils.FileUtil;
import com.autonavi.minimap.bundle.apm.base.plugin.Plugin;
import com.autonavi.minimap.bundle.apm.internal.report.ReportManager;
import com.autonavi.widget.ui.BalloonLayout;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* renamed from: cvz reason: default package */
/* compiled from: UploadPlugin */
public class cvz extends Plugin {
    private Application c;
    /* access modifiers changed from: private */
    public a d;
    private SharedPreferences e;
    private long f = 0;
    /* access modifiers changed from: private */
    public volatile boolean g = true;
    /* access modifiers changed from: private */
    public volatile boolean h = false;
    private int i = 30000;
    private long j = HttpFileUpMMTask.BIG_FILE_SIZE_THRESHOLD;
    private long k = FileCache.EXPIRE_TIME;
    /* access modifiers changed from: private */
    public long l = 300000;
    private long m = 10000;
    private long n = BalloonLayout.DEFAULT_DISPLAY_DURATION;
    private long o = 256000;
    private long p = 52428800;

    /* renamed from: cvz$a */
    /* compiled from: UploadPlugin */
    static class a extends Handler {
        private cvz a;

        public a(Looper looper, cvz cvz) {
            super(looper);
            this.a = cvz;
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                cvz.a(this.a);
            }
        }
    }

    public final void a(Application application, cuu cuu, JSONObject jSONObject) {
        JSONObject jSONObject2 = jSONObject;
        super.a(application, cuu, jSONObject);
        this.c = application;
        if (jSONObject2 == null) {
            this.e = cwk.a().a(this.c, "com.autonavi.minimap.bundle.apm");
            long j2 = this.e.getLong("date", 0);
            this.f = this.e.getLong("size", 0);
            long currentTimeMillis = System.currentTimeMillis() / 86400000;
            if (currentTimeMillis != j2) {
                this.e.edit().putLong("date", currentTimeMillis).putLong("size", 0).apply();
                this.f = 0;
            }
            this.i = 30000;
            this.j = 1048576;
            this.k = FileCache.EXPIRE_TIME;
            this.l = 300000;
            this.m = 10000;
            this.n = BalloonLayout.DEFAULT_DISPLAY_DURATION;
            this.o = 256000;
            this.p = 52428800;
        } else {
            this.e = cwk.a().a(this.c, "com.autonavi.minimap.bundle.apm");
            long j3 = this.e.getLong("date", 0);
            this.f = this.e.getLong("size", 0);
            long currentTimeMillis2 = System.currentTimeMillis() / 86400000;
            if (currentTimeMillis2 != j3) {
                this.e.edit().putLong("date", currentTimeMillis2).putLong("size", 0).apply();
                this.f = 0;
            }
            this.i = jSONObject2.optInt("boot_delay_check", 30000);
            this.j = jSONObject2.optLong("max_mobile_traffic", 1048576);
            this.k = jSONObject2.optLong("max_cache_day", FileCache.EXPIRE_TIME);
            this.l = jSONObject2.optLong("max_check_interval", 300000);
            this.m = jSONObject2.optLong("bg_to_fg_check_delay", 10000);
            this.n = jSONObject2.optLong("fg_to_bg_check_delay", BalloonLayout.DEFAULT_DISPLAY_DURATION);
            this.o = jSONObject2.optLong("max_load_file_size", 256000);
            this.p = jSONObject2.optLong("max_cache_size", 52428800);
        }
        this.d = new a(b.a, this);
        cuu.a(2, this.a);
        this.d.sendEmptyMessageDelayed(1, (long) this.i);
    }

    public final void a(int i2, cur cur) {
        super.a(i2, cur);
        if (!this.h && i2 == 2) {
            cuq cuq = (cuq) cur;
            if (cuq.a == 1) {
                this.g = false;
                this.d.removeMessages(1);
                this.d.sendEmptyMessageDelayed(1, this.n);
            } else if (cuq.a == 2) {
                this.g = true;
                if (!this.d.hasMessages(1)) {
                    this.d.sendEmptyMessageDelayed(1, this.m);
                }
            }
        }
    }

    private boolean a(File file) {
        long j2 = 0;
        if (file.length() == 0) {
            return true;
        }
        try {
            boolean a2 = a();
            if (a2) {
                j2 = this.f + ((long) (((double) file.length()) * 0.4d));
                if (j2 >= this.j) {
                    StringBuilder sb = new StringBuilder("upload size larger than MAX_MOBILE_TRAFFIC! ");
                    sb.append(file.getAbsolutePath());
                    sb.append(Token.SEPARATOR);
                    sb.append(file.length());
                    sb.append(Token.SEPARATOR);
                    sb.append(j2);
                    cwl.d("UploadPlugin", sb.toString());
                    return false;
                }
            }
            byte[] a3 = cwe.a(file);
            if (a3 == null) {
                StringBuilder sb2 = new StringBuilder("read file failed! ");
                sb2.append(file.getAbsolutePath());
                sb2.append(Token.SEPARATOR);
                sb2.append(file.length());
                cwl.e("UploadPlugin", sb2.toString());
                return true;
            }
            byte[] encode = Base64.encode(a3, 0);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(file.getAbsolutePath());
            sb3.append(".b");
            String sb4 = sb3.toString();
            FileUtil.writeDatasToFile(sb4, encode);
            File a4 = zp.a(new File(sb4), false);
            if (bno.a) {
                new StringBuilder("uploadFile: ").append(a4);
            }
            boolean a5 = cwc.a(a4);
            if (bno.a) {
                StringBuilder sb5 = new StringBuilder("uploadFile: ");
                sb5.append(a4);
                sb5.append(" result: ");
                sb5.append(a5);
            }
            if (a5 && a2) {
                this.f = j2;
                this.e.edit().putLong("size", this.f).apply();
            }
            return a5;
        } catch (OutOfMemoryError unused) {
            file.delete();
            StringBuilder sb6 = new StringBuilder("read file oom! ");
            sb6.append(file.getAbsolutePath());
            sb6.append(Token.SEPARATOR);
            sb6.append(file.length());
            cwl.e("UploadPlugin", sb6.toString());
            return false;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    private void a(List<File> list, long j2) {
        File[] fileArr;
        long j3 = j2;
        int size = list.size();
        long j4 = j3 - this.p;
        long currentTimeMillis = System.currentTimeMillis();
        int i2 = 1;
        int i3 = size - 1;
        while (i3 >= 0) {
            File file = list.get(i3);
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles(new FileFilter() {
                    public final boolean accept(File file) {
                        return file.getName().endsWith(".trace");
                    }
                });
                if (listFiles != null) {
                    int length = listFiles.length;
                    long j5 = j4;
                    int i4 = 0;
                    while (i4 < length) {
                        File file2 = listFiles[i4];
                        if (file2.isFile()) {
                            if (j5 > 0) {
                                String[] strArr = new String[i2];
                                StringBuilder sb = new StringBuilder("total size large than MAX_CACHE_SIZE! ");
                                sb.append(j3);
                                sb.append(" remove=");
                                sb.append(file2.getAbsolutePath());
                                sb.append("  ");
                                sb.append(file2.length());
                                sb.append(" outSize=");
                                sb.append(j5);
                                strArr[0] = sb.toString();
                                cwl.b("UploadPlugin", strArr);
                                j5 -= file2.length();
                                file2.delete();
                            } else {
                                if (file2.length() >= this.o) {
                                    StringBuilder sb2 = new StringBuilder("file size is to large! ");
                                    sb2.append(file2.getAbsolutePath());
                                    sb2.append(Token.SEPARATOR);
                                    fileArr = listFiles;
                                    sb2.append(file2.length());
                                    cwl.e("UploadPlugin", sb2.toString());
                                    file2.delete();
                                } else {
                                    fileArr = listFiles;
                                    long b = b(file2, ".trace");
                                    if (b > 0 && currentTimeMillis - b > this.k) {
                                        StringBuilder sb3 = new StringBuilder("file date is expired! ");
                                        sb3.append(file2.getAbsolutePath());
                                        cwl.b("UploadPlugin", sb3.toString());
                                        file2.delete();
                                        i4++;
                                        listFiles = fileArr;
                                        j3 = j2;
                                        i2 = 1;
                                        List<File> list2 = list;
                                    }
                                }
                                i4++;
                                listFiles = fileArr;
                                j3 = j2;
                                i2 = 1;
                                List<File> list22 = list;
                            }
                        }
                        fileArr = listFiles;
                        i4++;
                        listFiles = fileArr;
                        j3 = j2;
                        i2 = 1;
                        List<File> list222 = list;
                    }
                    j4 = j5;
                    i3--;
                    j3 = j2;
                    i2 = 1;
                }
            }
            i3--;
            j3 = j2;
            i2 = 1;
        }
    }

    private long a(List<File> list) {
        long j2 = 0;
        for (File next : list) {
            if (next.isDirectory()) {
                File[] listFiles = next.listFiles(new FileFilter() {
                    public final boolean accept(File file) {
                        return file.getName().endsWith(".trace");
                    }
                });
                if (listFiles != null) {
                    for (File file : listFiles) {
                        if (file.isFile()) {
                            j2 += file.length();
                        }
                    }
                }
            }
        }
        return j2;
    }

    /* access modifiers changed from: private */
    public static long b(File file, String str) {
        String name = file.getName();
        if (TextUtils.isEmpty(str)) {
            return b(name);
        }
        int indexOf = name.indexOf(str);
        if (indexOf > 0) {
            return b(name.substring(0, indexOf));
        }
        return -1;
    }

    private boolean a() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.c.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.getType() == 0) {
                return activeNetworkInfo.isAvailable();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static long b(String str) {
        try {
            return Long.parseLong(str);
        } catch (Throwable th) {
            th.printStackTrace();
            return -1;
        }
    }

    static /* synthetic */ void a(cvz cvz) {
        final ArrayList arrayList;
        if (!cvz.h) {
            cvz.h = true;
            String pathCachPrefix = ReportManager.getPathCachPrefix(cvz.c);
            String pathPrefix = ReportManager.getPathPrefix(cvz.c);
            File file = new File(pathCachPrefix);
            if (file.exists()) {
                File[] listFiles = file.listFiles(new FileFilter() {
                    public final boolean accept(File file) {
                        if (!file.isDirectory() || cvz.b(file.getName()) <= 0 || file.getName().equals(String.valueOf(ReportManager.session))) {
                            return false;
                        }
                        return true;
                    }
                });
                if (listFiles != null && listFiles.length > 0) {
                    for (File file2 : listFiles) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(file2.getAbsolutePath());
                        sb.append(File.separator);
                        sb.append("hotdata");
                        String sb2 = sb.toString();
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(pathPrefix);
                        sb3.append(File.separator);
                        sb3.append(file2.getName());
                        String sb4 = sb3.toString();
                        if (new File(sb2).exists()) {
                            ReportManager.getInstance().trimHotdataBeforeUpload(file2.getAbsolutePath(), sb4);
                        }
                        cwe.b(file2);
                    }
                }
            }
            File file3 = new File(ReportManager.getPathPrefix(cvz.c));
            if (file3.exists()) {
                File[] listFiles2 = file3.listFiles(new FileFilter() {
                    public final boolean accept(File file) {
                        if (!file.isDirectory() || cvz.b(file.getName()) <= 0) {
                            return false;
                        }
                        return true;
                    }
                });
                if (listFiles2 != null && listFiles2.length > 1) {
                    List asList = Arrays.asList(listFiles2);
                    if (asList.size() > 1) {
                        Collections.sort(asList, new Comparator<File>() {
                            public final /* synthetic */ int compare(Object obj, Object obj2) {
                                int i = ((cvz.b(((File) obj2).getName()) - cvz.b(((File) obj).getName())) > 0 ? 1 : ((cvz.b(((File) obj2).getName()) - cvz.b(((File) obj).getName())) == 0 ? 0 : -1));
                                if (i == 0) {
                                    return 0;
                                }
                                return i > 0 ? 1 : -1;
                            }
                        });
                    }
                    arrayList = new ArrayList(asList);
                    arrayList.remove(0);
                    if (arrayList != null || arrayList.size() <= 0) {
                        cwl.b("UploadPlugin", "upload dir is empty !");
                        cvz.h = false;
                    } else {
                        cvz.a((List<File>) arrayList, cvz.a((List<File>) arrayList));
                        new Thread("telescope upload") {
                            public final void run() {
                                super.run();
                                cwl.c("start upload", new Object[0]);
                                if (!cvz.a(cvz.this, arrayList) && cvz.this.g) {
                                    cvz.this.d.sendEmptyMessageDelayed(1, cvz.this.l);
                                }
                                cvz.this.h = false;
                                cwl.c("finish upload", new Object[0]);
                            }
                        }.start();
                        return;
                    }
                }
            }
            arrayList = null;
            if (arrayList != null) {
            }
            cwl.b("UploadPlugin", "upload dir is empty !");
            cvz.h = false;
        }
    }

    static /* synthetic */ boolean a(cvz cvz, List<File> list) {
        for (File file : list) {
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles(new FileFilter() {
                    public final boolean accept(File file) {
                        if (!file.isFile() || cvz.b(file, ".trace") <= 0) {
                            return false;
                        }
                        return true;
                    }
                });
                if (listFiles == null || listFiles.length <= 0) {
                    StringBuilder sb = new StringBuilder("upload dir is empty=");
                    sb.append(file.getAbsolutePath());
                    cwl.b("UploadPlugin", sb.toString());
                    cwe.b(file);
                } else {
                    List asList = Arrays.asList(listFiles);
                    if (asList.size() > 1) {
                        Collections.sort(asList, new Comparator<File>() {
                            public final /* synthetic */ int compare(Object obj, Object obj2) {
                                int i = ((cvz.b((File) obj, ".trace") - cvz.b((File) obj2, ".trace")) > 0 ? 1 : ((cvz.b((File) obj, ".trace") - cvz.b((File) obj2, ".trace")) == 0 ? 0 : -1));
                                if (i == 0) {
                                    return 0;
                                }
                                return i > 0 ? 1 : -1;
                            }
                        });
                    }
                    Iterator it = asList.iterator();
                    boolean z = false;
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        File file2 = (File) it.next();
                        boolean a2 = cvz.a(file2);
                        StringBuilder sb2 = new StringBuilder("upload file=");
                        sb2.append(file2.getAbsolutePath());
                        sb2.append(Token.SEPARATOR);
                        sb2.append(a2);
                        sb2.append(Token.SEPARATOR);
                        sb2.append(file2.length());
                        cwl.b("UploadPlugin", sb2.toString());
                        if (!a2) {
                            z = a2;
                            break;
                        }
                        file2.delete();
                        z = a2;
                    }
                    if (!z) {
                        return false;
                    }
                    cwe.b(file);
                }
            }
        }
        return true;
    }
}
