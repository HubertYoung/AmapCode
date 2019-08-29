package defpackage;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.support.v4.os.AsyncTaskCompat;

/* renamed from: eol reason: default package */
/* compiled from: ShotUtil */
public final class eol {
    /* access modifiers changed from: private */
    public static Context b;
    private static ImageReader c;
    private static MediaProjection d;
    private static eol g;
    /* access modifiers changed from: 0000 */
    public String a = "default";
    /* access modifiers changed from: private */
    public VirtualDisplay e;
    /* access modifiers changed from: private */
    public a f;

    /* renamed from: eol$a */
    /* compiled from: ShotUtil */
    public interface a {
        void a();
    }

    /* renamed from: eol$b */
    /* compiled from: ShotUtil */
    public class b extends AsyncTask<Image, Void, Bitmap> {
        public b() {
        }

        /* access modifiers changed from: protected */
        @TargetApi(19)
        public final /* synthetic */ void onPostExecute(Object obj) {
            Bitmap bitmap = (Bitmap) obj;
            super.onPostExecute(bitmap);
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            if (eol.this.e != null) {
                eol.this.e.release();
            }
            if (eol.this.f != null) {
                eol.this.f.a();
            }
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Removed duplicated region for block: B:19:0x00c4 A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x00c5 A[RETURN] */
        @android.annotation.TargetApi(19)
        /* renamed from: a */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.graphics.Bitmap doInBackground(android.media.Image... r9) {
            /*
                r8 = this;
                r0 = 0
                if (r9 == 0) goto L_0x00c6
                int r1 = r9.length
                if (r1 <= 0) goto L_0x00c6
                r1 = 0
                r2 = r9[r1]
                if (r2 != 0) goto L_0x000d
                goto L_0x00c6
            L_0x000d:
                r9 = r9[r1]
                int r2 = r9.getWidth()
                int r3 = r9.getHeight()
                android.media.Image$Plane[] r4 = r9.getPlanes()
                r5 = r4[r1]
                java.nio.ByteBuffer r5 = r5.getBuffer()
                r6 = r4[r1]
                int r6 = r6.getPixelStride()
                r4 = r4[r1]
                int r4 = r4.getRowStride()
                int r7 = r6 * r2
                int r4 = r4 - r7
                int r4 = r4 / r6
                int r4 = r4 + r2
                android.graphics.Bitmap$Config r6 = android.graphics.Bitmap.Config.ARGB_8888
                android.graphics.Bitmap r4 = android.graphics.Bitmap.createBitmap(r4, r3, r6)
                r4.copyPixelsFromBuffer(r5)
                android.graphics.Bitmap r1 = android.graphics.Bitmap.createBitmap(r4, r1, r1, r2, r3)
                r9.close()
                if (r1 == 0) goto L_0x00c1
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                r9.<init>()     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                android.content.Context r2 = defpackage.eol.b     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                java.io.File r2 = r2.getFilesDir()     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                java.lang.String r2 = r2.getAbsolutePath()     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                r9.append(r2)     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                java.lang.String r2 = java.io.File.separator     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                r9.append(r2)     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                java.lang.String r2 = "QAScreenShot"
                r9.append(r2)     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                java.lang.String r2 = java.io.File.separator     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                r9.append(r2)     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                java.lang.String r9 = r9.toString()     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                eol r2 = defpackage.eol.this     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                r3.<init>()     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                r3.append(r9)     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                eol r9 = defpackage.eol.this     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                java.lang.String r9 = r9.a     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                r3.append(r9)     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                java.lang.String r9 = ".png"
                r3.append(r9)     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                java.lang.String r9 = r3.toString()     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                r2.a = r9     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                java.io.File r9 = new java.io.File     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                eol r2 = defpackage.eol.this     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                java.lang.String r2 = r2.a     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                r9.<init>(r2)     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                boolean r2 = r9.exists()     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                if (r2 != 0) goto L_0x00a5
                java.io.File r2 = r9.getParentFile()     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                r2.mkdirs()     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                r9.createNewFile()     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
            L_0x00a5:
                java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                r2.<init>(r9)     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                android.graphics.Bitmap$CompressFormat r3 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                r4 = 90
                r1.compress(r3, r4, r2)     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                r2.flush()     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                r2.close()     // Catch:{ FileNotFoundException -> 0x00bd, IOException -> 0x00b8 }
                goto L_0x00c2
            L_0x00b8:
                r9 = move-exception
                r9.printStackTrace()
                goto L_0x00c1
            L_0x00bd:
                r9 = move-exception
                r9.printStackTrace()
            L_0x00c1:
                r9 = r0
            L_0x00c2:
                if (r9 == 0) goto L_0x00c5
                return r1
            L_0x00c5:
                return r0
            L_0x00c6:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.eol.b.doInBackground(android.media.Image[]):android.graphics.Bitmap");
        }
    }

    public static eol a(Context context, int i, Intent intent) {
        if (g == null) {
            g = new eol();
            b = context;
            if (VERSION.SDK_INT >= 21) {
                if (d == null) {
                    d = ((MediaProjectionManager) b.getSystemService("media_projection")).getMediaProjection(i, intent);
                }
                c = ImageReader.newInstance(c(), d(), 1, 1);
            }
        }
        return g;
    }

    @TargetApi(21)
    private void b() {
        this.e = d.createVirtualDisplay("screen-mirror", c(), d(), Resources.getSystem().getDisplayMetrics().densityDpi, 16, c.getSurface(), null, null);
    }

    @TargetApi(19)
    public final void a(a aVar) {
        this.f = aVar;
        if (VERSION.SDK_INT >= 21) {
            b();
            Image acquireLatestImage = c.acquireLatestImage();
            AsyncTaskCompat.executeParallel(new b(), acquireLatestImage);
        }
    }

    private static int c() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private static int d() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
