package com.autonavi.minimap.ajx3.util;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {

    /* renamed from: com.autonavi.minimap.ajx3.util.BitmapUtils$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$Config = new int[Config.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                android.graphics.Bitmap$Config[] r0 = android.graphics.Bitmap.Config.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$android$graphics$Bitmap$Config = r0
                int[] r0 = $SwitchMap$android$graphics$Bitmap$Config     // Catch:{ NoSuchFieldError -> 0x0014 }
                android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.ARGB_4444     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$android$graphics$Bitmap$Config     // Catch:{ NoSuchFieldError -> 0x001f }
                android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.RGB_565     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$android$graphics$Bitmap$Config     // Catch:{ NoSuchFieldError -> 0x002a }
                android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.ARGB_8888     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$android$graphics$Bitmap$Config     // Catch:{ NoSuchFieldError -> 0x0035 }
                android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.ALPHA_8     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = $SwitchMap$android$graphics$Bitmap$Config     // Catch:{ NoSuchFieldError -> 0x0040 }
                android.graphics.Bitmap$Config r1 = android.graphics.Bitmap.Config.RGBA_F16     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.util.BitmapUtils.AnonymousClass1.<clinit>():void");
        }
    }

    public static String compressBitmap(Bitmap bitmap, String str) {
        FileOutputStream fileOutputStream;
        String str2;
        File file = new File(str);
        if (!FileUtil.makesureExist(file.getParentFile())) {
            return null;
        }
        try {
            fileOutputStream = new FileOutputStream(file);
            try {
                boolean compress = bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                if (compress) {
                    StringBuilder sb = new StringBuilder("file://");
                    sb.append(file.getAbsolutePath());
                    str2 = sb.toString();
                } else {
                    str2 = null;
                }
                CloseableUtils.close(fileOutputStream);
                return str2;
            } catch (IOException e) {
                e = e;
                try {
                    e.printStackTrace();
                    CloseableUtils.close(fileOutputStream);
                    return null;
                } catch (Throwable th) {
                    th = th;
                    CloseableUtils.close(fileOutputStream);
                    throw th;
                }
            }
        } catch (IOException e2) {
            e = e2;
            fileOutputStream = null;
            e.printStackTrace();
            CloseableUtils.close(fileOutputStream);
            return null;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
            CloseableUtils.close(fileOutputStream);
            throw th;
        }
    }

    public static Bitmap compressBitmap(Context context, Bitmap bitmap, Config config, int i) {
        if (bitmap == null || bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width == 0 || height == 0) {
            return bitmap;
        }
        double sqrt = Math.sqrt((double) ((((((float) width) * 1.0f) * ((float) height)) * ((float) getPixelUnitSize(config))) / ((float) i)));
        int launcherLargeIconSize = getLauncherLargeIconSize(context);
        if (launcherLargeIconSize > 30) {
            sqrt = Math.max(sqrt, (double) ((((float) Math.max(width, height)) * 1.0f) / ((float) launcherLargeIconSize)));
        }
        if (sqrt < 1.0d || Math.abs(sqrt - 1.0d) < 0.009999999776482582d) {
            return bitmap;
        }
        try {
            return Bitmap.createScaledBitmap(bitmap, (int) (((double) width) / sqrt), (int) (((double) height) / sqrt), true);
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    private static int getPixelUnitSize(Config config) {
        switch (AnonymousClass1.$SwitchMap$android$graphics$Bitmap$Config[config.ordinal()]) {
            case 1:
            case 2:
                return 2;
            case 4:
                return 1;
            case 5:
                return 8;
            default:
                return 4;
        }
    }

    public static int getLauncherLargeIconSize(Context context) {
        return ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getLauncherLargeIconSize();
    }
}
