package defpackage;

import android.app.Application;
import android.os.Environment;
import com.amap.bundle.network.biz.statistic.apm.ApmCacheTaskManager$1;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.offline.model.FilePathHelper;
import java.io.File;

/* renamed from: zk reason: default package */
/* compiled from: ApmCacheTaskManager */
public final class zk {
    private static zk e;
    int a = 43200000;
    public int b = 120000;
    public long c;
    String d;

    private zk() {
        try {
            Application application = AMapAppGlobal.getApplication();
            File filesDir = application.getFilesDir() == null ? application.getDir(AutoJsonUtils.JSON_FILES, 0) : application.getFilesDir();
            if (filesDir == null) {
                StringBuilder sb = new StringBuilder("/data/data/");
                sb.append(application.getPackageName());
                sb.append("/app_files");
                filesDir = new File(sb.toString());
            }
            if (!filesDir.exists()) {
                filesDir.mkdirs();
            }
            this.d = filesDir.getAbsolutePath();
            if (bno.a) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Environment.getExternalStorageDirectory());
                sb2.append(FilePathHelper.APP_FOLDER);
                this.d = sb2.toString();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static synchronized zk a() {
        zk zkVar;
        synchronized (zk.class) {
            try {
                if (e == null) {
                    e = new zk();
                }
                zkVar = e;
            }
        }
        return zkVar;
    }

    /* access modifiers changed from: 0000 */
    public final void a(File file, File file2) {
        if (file != null) {
            zp.a(file, (Callback<Integer>) new ApmCacheTaskManager$1<Integer>(this, file2, file));
        }
    }
}
