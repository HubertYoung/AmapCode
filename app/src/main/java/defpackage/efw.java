package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import com.autonavi.amap.app.AMapAppGlobal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* renamed from: efw reason: default package */
/* compiled from: RunTraceDisplayUtil */
public final class efw {
    public static void a(Bitmap bitmap, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(AMapAppGlobal.getApplication().getFilesDir().getAbsolutePath());
        sb.append(File.separator);
        sb.append("runTrace");
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
        sb.append("runTrace");
        return sb.toString();
    }

    public static Bitmap a(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = (width * 3) / 4;
        int a = agn.a((Context) AMapAppGlobal.getApplication(), 250.0f);
        int a2 = agn.a((Context) AMapAppGlobal.getApplication(), 67.0f);
        int i2 = (((height - a) - a2) - i) / 2;
        if (i2 > 0) {
            a2 += i2;
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, a2, width, i, null, false);
        Bitmap a3 = eaq.a(createBitmap);
        bitmap.recycle();
        createBitmap.recycle();
        return a3;
    }
}
