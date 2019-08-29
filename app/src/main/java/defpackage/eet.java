package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import com.autonavi.amap.app.AMapAppGlobal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* renamed from: eet reason: default package */
/* compiled from: RideTraceDisplayUtil */
public final class eet {
    public static void a(Bitmap bitmap, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(AMapAppGlobal.getApplication().getFilesDir().getAbsolutePath());
        sb.append(File.separator);
        sb.append("rideTrace");
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(file);
        sb2.append(File.separator);
        sb2.append(str);
        File file2 = new File(sb2.toString());
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bitmap != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                bitmap.compress(CompressFormat.PNG, 90, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(AMapAppGlobal.getApplication().getFilesDir().getAbsolutePath());
        sb.append(File.separator);
        sb.append("rideTrace");
        return sb.toString();
    }
}
