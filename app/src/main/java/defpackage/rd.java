package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/* renamed from: rd reason: default package */
/* compiled from: DriveEyrieRouteShareUtil */
public final class rd {
    public ph a = null;

    public rd(ph phVar) {
        this.a = phVar;
    }

    public final String a() {
        if (this.a == null) {
            return null;
        }
        pg a2 = this.a.a();
        if (a2 == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(a(R.string.autonavi_route_car_result_full_string));
        sb.append(cfe.a(a2.c));
        sb.append("(约");
        sb.append(lf.a(a2.g));
        sb.append(")\n");
        if (a2.f > 0) {
            sb.append(String.format(a(R.string.autonavi_car_share_taxi_format), new Object[]{Integer.valueOf(a2.f)}));
        }
        return sb.toString();
    }

    public final String a(String str) {
        if (this.a == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(AMapAppGlobal.getApplication().getString(R.string.autonavi_car_share_from_text));
        sb2.append(this.a.g.getName());
        sb.append(sb2.toString());
        List<POI> list = this.a.i;
        if (list != null && list.size() > 0) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a(R.string.autonavi_car_result_share_pass));
            sb3.append(list.get(0).getName());
            sb.append(sb3);
        }
        StringBuilder sb4 = new StringBuilder();
        sb4.append(AMapAppGlobal.getApplication().getString(R.string.autonavi_car_share_text_to_string));
        sb4.append(this.a.h.getName());
        sb.append(sb4.toString());
        return sb.toString();
    }

    public final String b(String str) {
        if (this.a == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(AMapAppGlobal.getApplication().getString(R.string.autonavi_car_share_from_text));
        sb.append(this.a.g.getName());
        List<POI> list = this.a.i;
        if (list != null && list.size() > 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a(R.string.autonavi_car_result_share_pass));
            sb2.append(list.get(0).getName());
            sb.append(sb2);
        }
        sb.append(a(R.string.autonavi_car_share_text_to_string));
        sb.append(this.a.h.getName());
        pg a2 = this.a.a();
        if (a2 != null) {
            sb.append(",");
            sb.append(a(R.string.autonavi_route_car_result_full_string));
            sb.append(cfe.a(a2.c));
            sb.append(",");
            sb.append(a(R.string.autonavi_car_result_share_about_need));
            sb.append(lf.a(a2.g));
            if (a2.f > 0) {
                sb.append(",");
                sb.append(String.format(a(R.string.autonavi_car_share_taxi_format), new Object[]{Integer.valueOf(a2.f)}));
            }
        }
        return sb.toString();
    }

    private static String a(int i) {
        return AMapAppGlobal.getApplication().getResources().getString(i);
    }

    public static String b() {
        String str = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            StringBuilder sb = new StringBuilder("autonavi");
            sb.append(File.separator);
            sb.append("naviendshare");
            File file = new File(externalStorageDirectory, sb.toString());
            if (!file.exists() && !file.mkdir()) {
                return null;
            }
            str = file.getAbsolutePath();
        }
        return str;
    }

    public static Bitmap c(String str) {
        FileOutputStream fileOutputStream;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Bitmap decodeFile = BitmapFactory.decodeFile(str);
        if (decodeFile == null) {
            return null;
        }
        Bitmap a2 = ahc.a(decodeFile, decodeFile.getWidth() >> 3, decodeFile.getHeight() >> 3);
        StringBuilder sb = new StringBuilder();
        sb.append(b());
        sb.append("navi_end_thumbnail.png");
        String sb2 = sb.toString();
        try {
            File file = new File(sb2);
            if (file.exists() && !file.delete()) {
                ahe.a((Closeable) null);
                return null;
            } else if (!file.createNewFile()) {
                ahe.a((Closeable) null);
                return null;
            } else {
                fileOutputStream = new FileOutputStream(sb2);
                try {
                    String parent = file.getParent();
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(parent);
                    sb3.append("/.nomedia");
                    File file2 = new File(sb3.toString());
                    if (file2.exists() || file2.createNewFile()) {
                        a2.compress(CompressFormat.PNG, 100, fileOutputStream);
                        a2.recycle();
                        ahe.a((Closeable) fileOutputStream);
                        return a2;
                    }
                    ahe.a((Closeable) fileOutputStream);
                    return null;
                } catch (Exception e) {
                    e = e;
                    try {
                        e.printStackTrace();
                        ahe.a((Closeable) fileOutputStream);
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        ahe.a((Closeable) fileOutputStream);
                        throw th;
                    }
                }
            }
        } catch (Exception e2) {
            e = e2;
            fileOutputStream = null;
            e.printStackTrace();
            ahe.a((Closeable) fileOutputStream);
            return null;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
            ahe.a((Closeable) fileOutputStream);
            throw th;
        }
    }
}
