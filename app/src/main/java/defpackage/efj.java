package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.MeasureSpec;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;

/* renamed from: efj reason: default package */
/* compiled from: ScreenShotHelper */
public final class efj {
    public static void a(Bitmap bitmap, String str) {
        if (!TextUtils.isEmpty(str) && bitmap != null) {
            FileOutputStream fileOutputStream = null;
            try {
                File file = new File(str);
                if (file.exists() && !file.delete()) {
                    ahe.a((Closeable) null);
                } else if (!file.createNewFile()) {
                    ahe.a((Closeable) null);
                } else {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(str);
                    try {
                        String parent = file.getParent();
                        StringBuilder sb = new StringBuilder();
                        sb.append(parent);
                        sb.append("/.nomedia");
                        File file2 = new File(sb.toString());
                        if (file2.exists() || file2.createNewFile()) {
                            bitmap.compress(CompressFormat.PNG, 100, fileOutputStream2);
                            bitmap.recycle();
                            ahe.a((Closeable) fileOutputStream2);
                            return;
                        }
                        ahe.a((Closeable) fileOutputStream2);
                    } catch (Exception e) {
                        e = e;
                        fileOutputStream = fileOutputStream2;
                        try {
                            e.printStackTrace();
                            ahe.a((Closeable) fileOutputStream);
                        } catch (Throwable th) {
                            th = th;
                            fileOutputStream2 = fileOutputStream;
                            ahe.a((Closeable) fileOutputStream2);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        ahe.a((Closeable) fileOutputStream2);
                        throw th;
                    }
                }
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                ahe.a((Closeable) fileOutputStream);
            }
        }
    }

    public static String a(String str) {
        String str2 = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            StringBuilder sb = new StringBuilder("autonavi");
            sb.append(File.separator);
            sb.append("navishare");
            File file = new File(externalStorageDirectory, sb.toString());
            if (!file.exists() && !file.mkdir()) {
                return null;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(file.getAbsolutePath());
            sb2.append("/");
            sb2.append(str);
            str2 = sb2.toString();
        }
        return str2;
    }

    public static Bitmap a(Context context, Bitmap bitmap, View view) {
        Bitmap bitmap2;
        if (bitmap == null || context == null) {
            return null;
        }
        int width = ags.a(context).width();
        int height = ags.a(context).height() - 20;
        try {
            bitmap2 = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            try {
                Canvas canvas = new Canvas(bitmap2);
                view.measure(MeasureSpec.makeMeasureSpec(width, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(height, UCCore.VERIFY_POLICY_QUICK));
                int measuredWidth = view.getMeasuredWidth();
                int measuredHeight = view.getMeasuredHeight();
                int i = height - measuredHeight;
                view.layout(0, i, measuredWidth, measuredHeight + i);
                view.draw(canvas);
                return bitmap2;
            } catch (OutOfMemoryError e) {
                e = e;
                ToastHelper.showToast(context.getString(R.string.route_screenshots_error));
                kf.a((Error) e);
                return bitmap2;
            }
        } catch (OutOfMemoryError e2) {
            e = e2;
            bitmap2 = null;
            ToastHelper.showToast(context.getString(R.string.route_screenshots_error));
            kf.a((Error) e);
            return bitmap2;
        }
    }
}
