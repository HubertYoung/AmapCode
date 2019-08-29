package com.jiuyan.inimage.util;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PaintFlagsDrawFilter;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/* compiled from: LocalImageLoader */
public class h {
    private float a = 1.2f;
    private Context b;
    private ActivityManager c;
    private int d;
    private int e;
    private DisplayMetrics f;

    public h(Context context) {
        this.b = context;
        this.c = (ActivityManager) this.b.getApplicationContext().getSystemService(WidgetType.ACTIVITY);
        int[] screenSize = DisplayUtil.getScreenSize(this.b);
        this.d = screenSize[0];
        this.e = screenSize[1];
        this.f = this.b.getResources().getDisplayMetrics();
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    private Uri b(Uri uri) {
        try {
            String uri2 = uri.toString();
            String substring = uri2.substring(uri2.lastIndexOf("/") + 1, uri2.length());
            String substring2 = uri2.substring(0, uri2.lastIndexOf("/"));
            String a2 = r.a(substring);
            q.a("filterUri", "oldString: " + uri2);
            q.a("filterUri", "filename: " + substring);
            q.a("filterUri", "new: " + substring2 + "/" + a2);
            return Uri.parse(substring2 + "/" + a2);
        } catch (Exception e2) {
            return uri;
        }
    }

    public i a(Uri uri) {
        InputStream inputStream;
        int i;
        Bitmap decodeStream;
        i iVar = new i();
        InputStream inputStream2 = null;
        try {
            Uri b2 = b(uri);
            Options c2 = c(b2);
            if (c2 == null) {
                iVar.b = 1;
                a((Closeable) null);
                return iVar;
            }
            int i2 = c2.outWidth;
            int i3 = c2.outHeight;
            if (((float) i2) < ((float) this.d) * 2.5f || ((float) i3) < ((float) this.e) * 2.5f) {
                if (((float) i2) >= ((float) this.d) * 2.0f || ((float) i3) >= ((float) this.e) * 2.0f) {
                    i = 2;
                } else {
                    i = 1;
                }
            } else {
                i = 3;
            }
            inputStream = a(this.b, b2);
            try {
                c2.inSampleSize = i;
                c2.inJustDecodeBounds = false;
                c2.inPreferredConfig = Config.ARGB_8888;
                long currentTimeMillis = System.currentTimeMillis();
                try {
                    decodeStream = BitmapFactory.decodeStream(inputStream, null, c2);
                } catch (OutOfMemoryError e2) {
                    q.a("LocalImageLoader", "decodeBitmap OutOfMemoryError 123");
                    System.gc();
                    Thread.sleep(200);
                    Options options = new Options();
                    options.inSampleSize = 4;
                    options.inPreferredConfig = Config.ARGB_8888;
                    decodeStream = BitmapFactory.decodeStream(inputStream, null, options);
                }
                q.a("LocalImageLoader", "inSampleSize: " + i + " width: " + i2 + " height: " + i3 + " interval: " + (System.currentTimeMillis() - currentTimeMillis));
                int a2 = d.a(d.a(this.b.getContentResolver(), b2));
                Bitmap a3 = d.a(decodeStream, (float) a2);
                int[] iArr = {a3.getWidth(), a3.getHeight()};
                int[] a4 = a(iArr);
                if (a4 != null) {
                    float f2 = ((float) iArr[0]) / ((float) iArr[1]);
                    float f3 = ((float) a4[0]) / ((float) iArr[0]);
                    q.a("LocalImageLoader", "exif rotation: " + a2 + " scale: " + f3 + "  maxSize[0]: " + a4[0] + "  maxSize[1]: " + a4[1]);
                    if (f3 != 1.0f) {
                        int i4 = (int) (((float) iArr[1]) * f3);
                        int i5 = (int) (((float) iArr[0]) * f3);
                        if (i4 > a4[1]) {
                            i4 = a4[1];
                            i5 = (int) (((float) i4) * f2);
                            f3 = ((float) i5) / ((float) iArr[0]);
                        }
                        q.a("LocalImageLoader", "targetHeight: " + i4 + " targetWidth: " + i5);
                        if (i5 % 2 != 0) {
                            i5--;
                        }
                        if (i4 % 2 != 0) {
                            i4--;
                        }
                        Bitmap a5 = a(a3, i5, i4, f3, true);
                        int width = a5.getWidth();
                        int height = a5.getHeight();
                        if (!(iArr[0] == width && iArr[1] == height)) {
                            a3.recycle();
                        }
                        iVar.a = a5;
                    } else {
                        iVar.a = a3;
                    }
                    iVar.b = 2;
                    a((Closeable) inputStream);
                    return iVar;
                }
                iVar.b = 3;
                iVar.a = a3;
                a((Closeable) inputStream);
                return iVar;
            } catch (Exception e3) {
                e = e3;
                inputStream2 = inputStream;
                try {
                    q.a("LocalImageLoader", e.toString());
                    q.a("LocalImageLoader", "uploadPublish\n" + e.toString());
                    a((Closeable) inputStream2);
                    q.a("LocalImageLoader", "uploadPublish STATUS_UNKNOWN_ERROR");
                    iVar.b = 4;
                    return iVar;
                } catch (Throwable th) {
                    th = th;
                    inputStream = inputStream2;
                    a((Closeable) inputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                a((Closeable) inputStream);
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            q.a("LocalImageLoader", e.toString());
            q.a("LocalImageLoader", "uploadPublish\n" + e.toString());
            a((Closeable) inputStream2);
            q.a("LocalImageLoader", "uploadPublish STATUS_UNKNOWN_ERROR");
            iVar.b = 4;
            return iVar;
        } catch (Throwable th3) {
            th = th3;
            inputStream = null;
            a((Closeable) inputStream);
            throw th;
        }
    }

    public Bitmap a(Bitmap bitmap, int i, int i2, float f2, boolean z) {
        Bitmap bitmap2;
        if (!z) {
            return Bitmap.createScaledBitmap(bitmap, i, i2, false);
        }
        PaintFlagsDrawFilter paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
        try {
            bitmap2 = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        } catch (OutOfMemoryError e2) {
            try {
                System.gc();
                Thread.sleep(500);
                bitmap2 = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
            } catch (InterruptedException e3) {
                e3.printStackTrace();
                bitmap2 = null;
            }
        }
        Canvas canvas = new Canvas(bitmap2);
        canvas.setDrawFilter(paintFlagsDrawFilter);
        Matrix matrix = new Matrix();
        matrix.setScale(f2, f2);
        canvas.drawBitmap(bitmap, matrix, null);
        return bitmap2;
    }

    public int[] a(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        if (this.f.densityDpi < 480) {
            this.a = 1.5f;
        }
        int i = iArr[0];
        int i2 = iArr[1];
        int[] iArr2 = new int[2];
        if (i < this.d && i2 < this.e) {
            iArr2[0] = this.d;
            iArr2[1] = this.e;
            return iArr2;
        } else if (((float) i) < ((float) this.d) * this.a && ((float) i2) < ((float) this.e) * this.a) {
            iArr2[0] = i;
            iArr2[1] = i2;
            return iArr2;
        } else if (a()) {
            iArr2[0] = this.d;
            iArr2[1] = this.e;
            return iArr2;
        } else {
            iArr2[0] = (int) (((float) this.d) * this.a);
            iArr2[1] = (int) (((float) this.e) * this.a);
            return iArr2;
        }
    }

    private Options c(Uri uri) {
        try {
            InputStream a2 = a(this.b, uri);
            if (a2 == null) {
                String str = "";
                if (uri != null) {
                    str = uri.toString();
                }
                q.a("LocalImageLoader", "inputStream is null ! uriString: " + str);
                return null;
            }
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(a2, null, options);
            a((Closeable) a2);
            return options;
        } catch (Exception e2) {
            if (uri == null) {
                return null;
            }
            q.a("LocalImageLoader", "uri: " + uri.toString());
            return null;
        }
    }

    private boolean a() {
        MemoryInfo memoryInfo = new MemoryInfo();
        this.c.getMemoryInfo(memoryInfo);
        return memoryInfo.lowMemory;
    }

    public static InputStream a(Context context, Uri uri) {
        try {
            if (uri.getScheme().equals("file")) {
                return new FileInputStream(uri.getPath());
            }
            return context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e2) {
            return null;
        }
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
            }
        }
    }
}
