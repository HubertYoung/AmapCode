package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import com.autonavi.minimap.R;
import java.io.File;
import java.io.IOException;
import proguard.annotation.KeepClassMembers;

@KeepClassMembers
/* renamed from: djy reason: default package */
/* compiled from: ShareBitmapUtil */
public final class djy {
    public static final String PIC_FILE_NAME = "traffic_top_board.png";
    public static final String PIC_THUMBNAIL_FILE_NAME = "TrafficThumbnail.png";

    public static String getSharePicPath(String str) {
        return getShareFilePath(str);
    }

    private static String getShareFilePath(String str) {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return null;
        }
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        StringBuilder sb = new StringBuilder("autonavi");
        sb.append(File.separator);
        sb.append("navishare");
        File file = new File(externalStorageDirectory, sb.toString());
        if (!file.exists()) {
            file.mkdir();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(file.getAbsolutePath());
        sb2.append("/");
        sb2.append(str);
        return sb2.toString();
    }

    public static synchronized void setShareBitmap(Bitmap bitmap, String str) {
        synchronized (djy.class) {
            if (bitmap != null) {
                try {
                    saveBitmap(bitmap, getShareFilePath(str));
                } catch (IOException e) {
                    kf.a((Throwable) e);
                }
                bitmap.recycle();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0062 A[SYNTHETIC, Splitter:B:29:0x0062] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x006b A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x006e A[SYNTHETIC, Splitter:B:37:0x006e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void saveBitmap(android.graphics.Bitmap r3, java.lang.String r4) throws java.io.IOException {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            java.io.File r0 = new java.io.File
            r0.<init>(r4)
            boolean r1 = r0.exists()
            if (r1 == 0) goto L_0x0015
            r0.delete()
        L_0x0015:
            r0.createNewFile()
            r1 = 0
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x005c }
            r2.<init>(r4)     // Catch:{ FileNotFoundException -> 0x005c }
            java.lang.String r4 = r0.getParent()     // Catch:{ FileNotFoundException -> 0x0056, all -> 0x0054 }
            java.io.File r0 = new java.io.File     // Catch:{ FileNotFoundException -> 0x0056, all -> 0x0054 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0056, all -> 0x0054 }
            r1.<init>()     // Catch:{ FileNotFoundException -> 0x0056, all -> 0x0054 }
            r1.append(r4)     // Catch:{ FileNotFoundException -> 0x0056, all -> 0x0054 }
            java.lang.String r4 = "/.nomedia"
            r1.append(r4)     // Catch:{ FileNotFoundException -> 0x0056, all -> 0x0054 }
            java.lang.String r4 = r1.toString()     // Catch:{ FileNotFoundException -> 0x0056, all -> 0x0054 }
            r0.<init>(r4)     // Catch:{ FileNotFoundException -> 0x0056, all -> 0x0054 }
            boolean r4 = r0.exists()     // Catch:{ FileNotFoundException -> 0x0056, all -> 0x0054 }
            if (r4 != 0) goto L_0x0041
            r0.createNewFile()     // Catch:{ FileNotFoundException -> 0x0056, all -> 0x0054 }
        L_0x0041:
            android.graphics.Bitmap$CompressFormat r4 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ FileNotFoundException -> 0x0056, all -> 0x0054 }
            r0 = 100
            r3.compress(r4, r0, r2)     // Catch:{ FileNotFoundException -> 0x0056, all -> 0x0054 }
            r2.flush()     // Catch:{ FileNotFoundException -> 0x0056, all -> 0x0054 }
            r2.close()     // Catch:{ Exception -> 0x004f }
            return
        L_0x004f:
            r3 = move-exception
            defpackage.kf.a(r3)
            return
        L_0x0054:
            r3 = move-exception
            goto L_0x006c
        L_0x0056:
            r3 = move-exception
            r1 = r2
            goto L_0x005d
        L_0x0059:
            r3 = move-exception
            r2 = r1
            goto L_0x006c
        L_0x005c:
            r3 = move-exception
        L_0x005d:
            defpackage.kf.a(r3)     // Catch:{ all -> 0x0059 }
            if (r1 == 0) goto L_0x006b
            r1.close()     // Catch:{ Exception -> 0x0066 }
            return
        L_0x0066:
            r3 = move-exception
            defpackage.kf.a(r3)
            return
        L_0x006b:
            return
        L_0x006c:
            if (r2 == 0) goto L_0x0076
            r2.close()     // Catch:{ Exception -> 0x0072 }
            goto L_0x0076
        L_0x0072:
            r4 = move-exception
            defpackage.kf.a(r4)
        L_0x0076:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.djy.saveBitmap(android.graphics.Bitmap, java.lang.String):void");
    }

    public static Bitmap getThumbnailsBitmap(String str) {
        return BitmapFactory.decodeFile(str);
    }

    public static Bitmap newBitmap(Bitmap bitmap, Bitmap bitmap2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight() + bitmap2.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
        canvas.drawBitmap(bitmap2, 0.0f, (float) bitmap.getHeight(), null);
        bitmap.recycle();
        bitmap2.recycle();
        return createBitmap;
    }

    public static Bitmap convertViewToBitmap(ListView listView, Adapter adapter) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(listView.getContext()).inflate(R.layout.traffic_jam_top_for_screenshots, null);
        int width = ags.a(listView.getContext()).width();
        int count = adapter.getCount();
        int i = 0;
        for (int i2 = 0; i2 < count; i2++) {
            View view = adapter.getView(i2, null, listView);
            if (view != null) {
                view.measure(0, 0);
                int measuredHeight = view.getMeasuredHeight() + listView.getDividerHeight();
                LayoutParams layoutParams = new LayoutParams(width, measuredHeight);
                layoutParams.topMargin = i;
                i += measuredHeight;
                linearLayout.addView(view, layoutParams);
            }
        }
        linearLayout.measure(0, 0);
        linearLayout.layout(0, 0, width, linearLayout.getMeasuredHeight());
        Bitmap createBitmap = Bitmap.createBitmap(width, linearLayout.getMeasuredHeight(), Config.ARGB_8888);
        linearLayout.draw(new Canvas(createBitmap));
        return createBitmap;
    }
}
