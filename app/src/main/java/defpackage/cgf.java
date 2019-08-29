package defpackage;

import android.graphics.Bitmap;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.mine.feedback.fragment.DoorAddressUploadPage;
import org.json.JSONObject;

/* renamed from: cgf reason: default package */
/* compiled from: DoorAddressUploadPresenter */
public final class cgf extends AbstractBasePresenter<DoorAddressUploadPage> {
    Bitmap a = null;
    public String b = "";
    int c = 0;
    public String d = "";
    public int e = 0;
    public int f = 0;
    public double g = -1.0d;
    public b h;
    public POI i = null;
    public String j = "";

    /* renamed from: cgf$a */
    /* compiled from: DoorAddressUploadPresenter */
    static class a extends Thread {
        crz a = null;
        private String b = null;
        private String c = null;

        public a(String str, String str2) {
            this.b = str;
            this.c = str2;
        }

        /* JADX WARNING: Removed duplicated region for block: B:28:0x0077 A[SYNTHETIC, Splitter:B:28:0x0077] */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x0083 A[SYNTHETIC, Splitter:B:35:0x0083] */
        /* JADX WARNING: Removed duplicated region for block: B:41:0x0092  */
        /* JADX WARNING: Removed duplicated region for block: B:44:0x00a0 A[SYNTHETIC, Splitter:B:44:0x00a0] */
        /* JADX WARNING: Unknown top exception splitter block from list: {B:25:0x0072=Splitter:B:25:0x0072, B:32:0x007e=Splitter:B:32:0x007e} */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
                r5 = this;
                android.graphics.BitmapFactory$Options r0 = new android.graphics.BitmapFactory$Options
                r0.<init>()
                r1 = 1
                r0.inJustDecodeBounds = r1
                java.lang.String r2 = r5.b
                android.graphics.BitmapFactory.decodeFile(r2, r0)
                int r2 = r0.outWidth
                r3 = 1024(0x400, float:1.435E-42)
                if (r2 <= r3) goto L_0x0015
                int r2 = r2 / r3
                int r1 = r1 + r2
            L_0x0015:
                int r2 = r0.outWidth
                int r2 = r2 / r1
                int r3 = r0.outHeight
                int r3 = r3 / r1
                int r2 = defpackage.kp.a(r0, r2, r3)
                r0.inSampleSize = r2
                java.lang.String r2 = r5.b
                int r3 = r0.outWidth
                int r3 = r3 / r1
                int r0 = r0.outHeight
                int r0 = r0 / r1
                android.graphics.Bitmap r0 = defpackage.kp.a(r2, r3, r0)
                if (r0 != 0) goto L_0x0030
                return
            L_0x0030:
                java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream
                r1.<init>()
                android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.JPEG
                r3 = 30
                r0.compress(r2, r3, r1)
                byte[] r1 = r1.toByteArray()
                java.io.File r2 = new java.io.File
                java.lang.String r3 = r5.c
                r2.<init>(r3)
                boolean r3 = r2.exists()
                if (r3 != 0) goto L_0x0054
                java.io.File r3 = r2.getParentFile()
                r3.mkdirs()
            L_0x0054:
                r3 = 0
                java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x007d, IOException -> 0x0071 }
                r4.<init>(r2)     // Catch:{ FileNotFoundException -> 0x007d, IOException -> 0x0071 }
                r4.write(r1)     // Catch:{ FileNotFoundException -> 0x006c, IOException -> 0x0069, all -> 0x0066 }
                r4.flush()     // Catch:{ FileNotFoundException -> 0x006c, IOException -> 0x0069, all -> 0x0066 }
                r4.close()     // Catch:{ IOException -> 0x0064 }
                goto L_0x008b
            L_0x0064:
                r1 = move-exception
                goto L_0x0088
            L_0x0066:
                r1 = move-exception
                r3 = r4
                goto L_0x009e
            L_0x0069:
                r1 = move-exception
                r3 = r4
                goto L_0x0072
            L_0x006c:
                r1 = move-exception
                r3 = r4
                goto L_0x007e
            L_0x006f:
                r1 = move-exception
                goto L_0x009e
            L_0x0071:
                r1 = move-exception
            L_0x0072:
                defpackage.kf.a(r1)     // Catch:{ all -> 0x006f }
                if (r3 == 0) goto L_0x008b
                r3.close()     // Catch:{ IOException -> 0x007b }
                goto L_0x008b
            L_0x007b:
                r1 = move-exception
                goto L_0x0088
            L_0x007d:
                r1 = move-exception
            L_0x007e:
                defpackage.kf.a(r1)     // Catch:{ all -> 0x006f }
                if (r3 == 0) goto L_0x008b
                r3.close()     // Catch:{ IOException -> 0x0087 }
                goto L_0x008b
            L_0x0087:
                r1 = move-exception
            L_0x0088:
                defpackage.kf.a(r1)
            L_0x008b:
                r0.recycle()
                crz r0 = r5.a
                if (r0 == 0) goto L_0x009d
                java.lang.String r0 = r5.c
                android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeFile(r0)
                crz r1 = r5.a
                r1.a(r0)
            L_0x009d:
                return
            L_0x009e:
                if (r3 == 0) goto L_0x00a8
                r3.close()     // Catch:{ IOException -> 0x00a4 }
                goto L_0x00a8
            L_0x00a4:
                r2 = move-exception
                defpackage.kf.a(r2)
            L_0x00a8:
                r0.recycle()
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.cgf.a.run():void");
        }
    }

    /* renamed from: cgf$b */
    /* compiled from: DoorAddressUploadPresenter */
    public class b {
        public b() {
        }

        public static boolean a(byte[] bArr) {
            try {
                return new JSONObject(new String(bArr, "UTF-8")).optBoolean("result");
            } catch (Exception e) {
                AMapLog.e("DoorAddressUploadPresenter", e.getLocalizedMessage());
                return false;
            }
        }
    }

    public cgf(DoorAddressUploadPage doorAddressUploadPage) {
        super(doorAddressUploadPage);
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00e0  */
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings({"OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onActivityResult(int r9, int r10, android.content.Intent r11) {
        /*
            r8 = this;
            super.onActivityResult(r9, r10, r11)
            java.lang.String r0 = ""
            r8.d = r0
            r0 = -1
            if (r10 != r0) goto L_0x013b
            android.net.Uri r2 = r11.getData()
            if (r2 == 0) goto L_0x0069
            r10 = 0
            android.app.Activity r0 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getActivity()     // Catch:{ Exception -> 0x004c, all -> 0x004a }
            if (r0 == 0) goto L_0x002e
            android.app.Activity r0 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getActivity()     // Catch:{ Exception -> 0x004c, all -> 0x004a }
            android.content.ContentResolver r1 = r0.getContentResolver()     // Catch:{ Exception -> 0x004c, all -> 0x004a }
            java.lang.String r0 = "_data"
            java.lang.String[] r3 = new java.lang.String[]{r0}     // Catch:{ Exception -> 0x004c, all -> 0x004a }
            r4 = 0
            r5 = 0
            java.lang.String r6 = "bucket_display_name"
            android.database.Cursor r0 = r1.query(r2, r3, r4, r5, r6)     // Catch:{ Exception -> 0x004c, all -> 0x004a }
            goto L_0x002f
        L_0x002e:
            r0 = r10
        L_0x002f:
            if (r0 == 0) goto L_0x0044
            r0.moveToFirst()     // Catch:{ Exception -> 0x003f, all -> 0x003c }
            r1 = 0
            java.lang.String r1 = r0.getString(r1)     // Catch:{ Exception -> 0x003f, all -> 0x003c }
            r8.b = r1     // Catch:{ Exception -> 0x003f, all -> 0x003c }
            goto L_0x0044
        L_0x003c:
            r9 = move-exception
            r10 = r0
            goto L_0x0063
        L_0x003f:
            r1 = move-exception
            r7 = r1
            r1 = r0
            r0 = r7
            goto L_0x004e
        L_0x0044:
            if (r0 == 0) goto L_0x006d
            r0.close()
            goto L_0x006d
        L_0x004a:
            r9 = move-exception
            goto L_0x0063
        L_0x004c:
            r0 = move-exception
            r1 = r10
        L_0x004e:
            defpackage.kf.a(r0)     // Catch:{ all -> 0x0061 }
            android.graphics.Bitmap r0 = r8.a     // Catch:{ all -> 0x0061 }
            if (r0 == 0) goto L_0x005b
            r8.a = r10     // Catch:{ all -> 0x0061 }
            java.lang.String r10 = ""
            r8.b = r10     // Catch:{ all -> 0x0061 }
        L_0x005b:
            if (r1 == 0) goto L_0x006d
            r1.close()
            goto L_0x006d
        L_0x0061:
            r9 = move-exception
            r10 = r1
        L_0x0063:
            if (r10 == 0) goto L_0x0068
            r10.close()
        L_0x0068:
            throw r9
        L_0x0069:
            java.lang.String r10 = ""
            r8.b = r10
        L_0x006d:
            java.lang.String r10 = r8.b
            int r10 = r10.length()
            if (r10 > 0) goto L_0x00c3
            android.os.Bundle r10 = r11.getExtras()
            if (r10 == 0) goto L_0x00c3
            java.lang.String r11 = "data"
            java.lang.Object r10 = r10.get(r11)
            android.graphics.Bitmap r10 = (android.graphics.Bitmap) r10
            r8.a = r10
            com.autonavi.map.fragmentcontainer.page.IPage r10 = r8.mPage     // Catch:{ Exception -> 0x00bf }
            com.autonavi.mine.feedback.fragment.DoorAddressUploadPage r10 = (com.autonavi.mine.feedback.fragment.DoorAddressUploadPage) r10     // Catch:{ Exception -> 0x00bf }
            android.content.Context r10 = r10.getContext()     // Catch:{ Exception -> 0x00bf }
            java.lang.String r10 = com.amap.bundle.blutils.FileUtil.getMapBaseStorage(r10)     // Catch:{ Exception -> 0x00bf }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00bf }
            r11.<init>()     // Catch:{ Exception -> 0x00bf }
            r11.append(r10)     // Catch:{ Exception -> 0x00bf }
            java.lang.String r10 = "/autonavi/Door_report_jpg.jpg"
            r11.append(r10)     // Catch:{ Exception -> 0x00bf }
            java.lang.String r10 = r11.toString()     // Catch:{ Exception -> 0x00bf }
            r8.d = r10     // Catch:{ Exception -> 0x00bf }
            java.io.FileOutputStream r10 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00bf }
            java.lang.String r11 = r8.d     // Catch:{ Exception -> 0x00bf }
            r10.<init>(r11)     // Catch:{ Exception -> 0x00bf }
            android.graphics.Bitmap r11 = r8.a     // Catch:{ Exception -> 0x00bf }
            android.graphics.Bitmap$CompressFormat r0 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ Exception -> 0x00bf }
            r1 = 90
            r11.compress(r0, r1, r10)     // Catch:{ Exception -> 0x00bf }
            r10.flush()     // Catch:{ Exception -> 0x00bf }
            r10.close()     // Catch:{ Exception -> 0x00bf }
            java.lang.String r10 = r8.d     // Catch:{ Exception -> 0x00bf }
            r8.b = r10     // Catch:{ Exception -> 0x00bf }
            goto L_0x00c3
        L_0x00bf:
            r10 = move-exception
            defpackage.kf.a(r10)
        L_0x00c3:
            java.lang.String r10 = r8.b
            int r10 = r10.length()
            if (r10 > 0) goto L_0x00e0
            android.content.Context r9 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
            int r10 = com.autonavi.minimap.R.string.take_pic_failed
            java.lang.String r9 = r9.getString(r10)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r9)
            com.autonavi.map.fragmentcontainer.page.IPage r9 = r8.mPage
            com.autonavi.mine.feedback.fragment.DoorAddressUploadPage r9 = (com.autonavi.mine.feedback.fragment.DoorAddressUploadPage) r9
            r9.a()
            return
        L_0x00e0:
            java.lang.String r10 = r8.b
            com.autonavi.map.fragmentcontainer.page.IPage r11 = r8.mPage
            com.autonavi.mine.feedback.fragment.DoorAddressUploadPage r11 = (com.autonavi.mine.feedback.fragment.DoorAddressUploadPage) r11
            android.content.Context r11 = r11.getContext()
            java.lang.String r11 = com.amap.bundle.blutils.FileUtil.getMapBaseStorage(r11)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r11)
            java.lang.String r11 = "/autonavi/error_report_jpg.jpg"
            r0.append(r11)
            java.lang.String r11 = r0.toString()
            cgf$a r0 = new cgf$a
            r0.<init>(r10, r11)
            cgf$2 r10 = new cgf$2
            r10.<init>(r11)
            r0.a = r10
            r0.start()
            com.autonavi.map.fragmentcontainer.page.IPage r10 = r8.mPage
            com.autonavi.mine.feedback.fragment.DoorAddressUploadPage r10 = (com.autonavi.mine.feedback.fragment.DoorAddressUploadPage) r10
            r10.b()
            r10 = 1001(0x3e9, float:1.403E-42)
            if (r9 != r10) goto L_0x0123
            cgf$1 r9 = new cgf$1
            java.lang.String r10 = "DoorAddressUploadFragmentThread"
            r9.<init>(r10)
            r9.start()
        L_0x0123:
            java.text.SimpleDateFormat r9 = new java.text.SimpleDateFormat
            java.lang.String r10 = "yyyy-MM-dd HH:mm:ss"
            java.util.Locale r11 = java.util.Locale.CHINA
            r9.<init>(r10, r11)
            java.util.Date r10 = new java.util.Date
            long r0 = java.lang.System.currentTimeMillis()
            r10.<init>(r0)
            java.lang.String r9 = r9.format(r10)
            r8.j = r9
        L_0x013b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cgf.onActivityResult(int, int, android.content.Intent):void");
    }

    public final void a(int i2, int i3, double d2) {
        this.e = i2;
        this.f = i3;
        this.g = d2;
    }
}
