package defpackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: drj reason: default package */
/* compiled from: SpotDownloadManager */
public final class drj {
    public static ConcurrentHashMap<String, String> c;
    private static drj h;
    public Context a = AMapAppGlobal.getApplication();
    boolean b = Environment.getExternalStorageState().equals("mounted");
    public long d = 0;
    public long e = 0;
    public String f = "";
    public String g = "";

    /* renamed from: drj$a */
    /* compiled from: SpotDownloadManager */
    public interface a {
    }

    /* renamed from: drj$b */
    /* compiled from: SpotDownloadManager */
    public class b implements bjf {
        private String b = "";
        private String c = "";
        private ProgressDlg d;
        private a e;
        private Context f;

        public final void onStart(long j, Map<String, List<String>> map, int i) {
        }

        public b(Context context, String str, String str2) {
            this.f = context;
            this.b = str;
            this.c = str2;
            this.e = null;
            drj.this.d = 0;
            drj.this.e = 0;
        }

        public final void onProgressUpdate(long j, long j2) {
            double d2 = ((double) j) / ((double) j2);
            drj.this.d = j2;
            drj.this.e = j;
            if (this.e != null) {
                Math.round(d2 * 100.0d);
            }
        }

        public final void onFinish(bpk bpk) {
            File file = new File(this.b);
            if (file.exists()) {
                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(Uri.fromFile(file));
                this.f.sendBroadcast(intent);
                StringBuilder sb = new StringBuilder();
                sb.append(drj.this.g);
                sb.append(this.f.getResources().getText(R.string.travel_image_download_success).toString());
                ToastHelper.showToast(sb.toString());
                drj.a(this.d);
                file.renameTo(new File(this.b));
                if (this.e != null) {
                    file.getPath();
                }
                drj.c.remove(this.c);
                drj.this.d = 100;
                drj.this.e = 100;
            }
        }

        public final void onError(int i, int i2) {
            StringBuilder sb = new StringBuilder();
            sb.append(drj.this.g);
            sb.append(this.f.getResources().getText(R.string.travel_image_download_fail).toString());
            ToastHelper.showToast(sb.toString());
            drj.a(this.d);
            drj.c.remove(this.c);
            drj.this.f = UploadDataResult.FAIL_MSG;
            drj.this.d = 0;
            drj.this.e = 0;
        }
    }

    public drj() {
        c = new ConcurrentHashMap<>();
    }

    private static synchronized void c() {
        synchronized (drj.class) {
            if (h == null) {
                h = new drj();
            }
        }
    }

    public static drj a() {
        if (h == null) {
            c();
        }
        return h;
    }

    public static String a(String str) {
        String substring = str.substring(str.lastIndexOf("/") + 1, str.contains("?") ? str.indexOf("?") : str.length());
        if (!substring.contains(".")) {
            StringBuilder sb = new StringBuilder();
            sb.append(substring);
            sb.append(".jpeg");
            substring = sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory());
        sb2.append("/AutonaviImage/");
        sb2.append(substring);
        return sb2.toString();
    }

    public static void b(String str) {
        String str2 = c.get(str);
        if (!TextUtils.isEmpty(str2)) {
            bjh.a().a(str2);
        }
    }

    public static void a(bid bid, String str) {
        Activity activity = bid.getActivity();
        if (activity != null) {
            File file = new File(str);
            Intent intent = new Intent("android.intent.action.VIEW");
            kh.a(activity, intent, "image/*", file);
            if (bid != null) {
                activity.startActivity(intent);
            }
        }
    }

    public static void a(ProgressDlg progressDlg) {
        if (progressDlg != null) {
            progressDlg.dismiss();
        }
    }
}
