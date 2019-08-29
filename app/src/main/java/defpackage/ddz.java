package defpackage;

import com.alipay.sdk.util.e;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.sdk.log.util.LogConstant;
import java.io.File;
import java.util.List;
import java.util.Map;

/* renamed from: ddz reason: default package */
/* compiled from: Callback */
public class ddz implements bjf {
    private chi a = null;
    private String b = null;
    private File c = null;

    public void onProgressUpdate(long j, long j2) {
    }

    public void onStart(long j, Map<String, List<String>> map, int i) {
    }

    public ddz(chi chi, String str, File file) {
        this.a = chi;
        this.b = str;
        this.c = file;
    }

    public void onFinish(bpk bpk) {
        if (!this.c.exists()) {
            AMapLog.error("basemap.splashscreen", LogConstant.SPLASH_SCREEN_DOWNLOADED, " tmp material file download finished,but not exists");
            return;
        }
        String absolutePath = this.c.getAbsolutePath();
        if (absolutePath.endsWith(FilePathHelper.SUFFIX_DOT_TMP)) {
            File file = new File(absolutePath.substring(0, absolutePath.lastIndexOf(".")));
            this.c.renameTo(file);
            if (file.exists()) {
                String a2 = chi.a();
                StringBuilder sb = new StringBuilder("afp");
                sb.append(this.a.e);
                emd.a(a2, sb.toString(), (String) LogConstant.SPLASH_SCREEN_DOWNLOADED, (String) null, (String) null, (String) null);
                emd.a(this.a, this.b, "successed");
                return;
            }
            AMapLog.error("basemap.splashscreen", LogConstant.SPLASH_SCREEN_DOWNLOADED, " tmp material file has renamed to final material file,but not exists");
        }
    }

    public void onError(int i, int i2) {
        if (-2 != i) {
            if (this.c.exists()) {
                this.c.delete();
            }
            emd.a(this.a, this.b, e.b);
        }
    }
}
