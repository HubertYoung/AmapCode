package defpackage;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import com.alipay.mobile.tinyappcommon.utils.H5TinyAppLogUtil;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.mapscreenshot.MapScreenshotPage;
import com.autonavi.minimap.operation.inter.PicPhotoDialog$1$1;
import com.autonavi.widget.ui.AlertView;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.WeakHashMap;

/* renamed from: dtd reason: default package */
/* compiled from: PicPhotoDialog */
public final class dtd extends CompatDialog implements OnClickListener {
    public static String a;
    public static final String b = Build.MODEL;
    public static final String c = Build.DEVICE;
    public static final String d = Build.MANUFACTURER;
    public File e;
    public int f;
    public b g;
    public final int h;
    public final int i;
    public boolean j;
    public boolean k;
    public int l;
    /* access modifiers changed from: private */
    public GeoPoint m;
    /* access modifiers changed from: private */
    public final Activity n;
    /* access modifiers changed from: private */
    public bid o;
    private Context p;
    /* access modifiers changed from: private */
    public HashMap<String, String> q;
    private String r;
    private int s;
    private WeakHashMap<Integer, Bitmap> t;

    /* renamed from: dtd$a */
    /* compiled from: PicPhotoDialog */
    public class a extends Thread {
        public b a = null;
        private Uri c = null;
        private String d = null;
        private String e = null;

        public a(String str) {
            this.d = str;
            this.e = dtd.a(str);
            dtd.this.q.put(this.e, str);
        }

        public a(Uri uri) {
            this.c = uri;
            this.d = ahb.a(dtd.this.n, uri);
            this.e = dtd.a(this.d);
            dtd.this.q.put(this.e, this.d);
        }

        /* JADX WARNING: Removed duplicated region for block: B:39:0x0083 A[SYNTHETIC, Splitter:B:39:0x0083] */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x0092 A[SYNTHETIC, Splitter:B:48:0x0092] */
        /* JADX WARNING: Removed duplicated region for block: B:54:0x009d A[SYNTHETIC, Splitter:B:54:0x009d] */
        /* JADX WARNING: Removed duplicated region for block: B:63:0x00ad A[SYNTHETIC, Splitter:B:63:0x00ad] */
        /* JADX WARNING: Unknown top exception splitter block from list: {B:45:0x008d=Splitter:B:45:0x008d, B:36:0x007e=Splitter:B:36:0x007e} */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
                r6 = this;
                dtd$b r0 = r6.a     // Catch:{ Exception -> 0x00d9 }
                if (r0 == 0) goto L_0x0009
                dtd$b r0 = r6.a     // Catch:{ Exception -> 0x00d9 }
                r0.a()     // Catch:{ Exception -> 0x00d9 }
            L_0x0009:
                java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x00d9 }
                java.lang.String r1 = r6.e     // Catch:{ Exception -> 0x00d9 }
                r0.<init>(r1)     // Catch:{ Exception -> 0x00d9 }
                boolean r1 = r0.exists()     // Catch:{ Exception -> 0x00d9 }
                if (r1 != 0) goto L_0x00a9
                java.io.File r1 = r0.getParentFile()     // Catch:{ Exception -> 0x00d9 }
                r1.mkdirs()     // Catch:{ Exception -> 0x00d9 }
                int r1 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x00d9 }
                r2 = 29
                r3 = 0
                if (r1 < r2) goto L_0x002d
                dtd r1 = defpackage.dtd.this     // Catch:{ Exception -> 0x00d9 }
                android.net.Uri r2 = r6.c     // Catch:{ Exception -> 0x00d9 }
                android.graphics.Bitmap r1 = r1.a(r2)     // Catch:{ Exception -> 0x00d9 }
                goto L_0x002e
            L_0x002d:
                r1 = r3
            L_0x002e:
                if (r1 != 0) goto L_0x0038
                dtd r1 = defpackage.dtd.this     // Catch:{ Exception -> 0x00d9 }
                java.lang.String r2 = r6.d     // Catch:{ Exception -> 0x00d9 }
                android.graphics.Bitmap r1 = r1.b(r2)     // Catch:{ Exception -> 0x00d9 }
            L_0x0038:
                if (r1 != 0) goto L_0x004b
                dtd$b r0 = r6.a     // Catch:{ Exception -> 0x00d9 }
                if (r0 == 0) goto L_0x004a
                dtd$b r0 = r6.a     // Catch:{ Exception -> 0x00d9 }
                java.lang.Exception r1 = new java.lang.Exception     // Catch:{ Exception -> 0x00d9 }
                java.lang.String r2 = "bit null"
                r1.<init>(r2)     // Catch:{ Exception -> 0x00d9 }
                r0.b()     // Catch:{ Exception -> 0x00d9 }
            L_0x004a:
                return
            L_0x004b:
                java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x00d9 }
                r2.<init>()     // Catch:{ Exception -> 0x00d9 }
                android.graphics.Bitmap$CompressFormat r4 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Exception -> 0x00d9 }
                r5 = 50
                r1.compress(r4, r5, r2)     // Catch:{ Exception -> 0x00d9 }
                byte[] r2 = r2.toByteArray()     // Catch:{ Exception -> 0x00d9 }
                java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x008c, IOException -> 0x007d }
                r4.<init>(r0)     // Catch:{ FileNotFoundException -> 0x008c, IOException -> 0x007d }
                r4.write(r2)     // Catch:{ FileNotFoundException -> 0x0078, IOException -> 0x0075, all -> 0x0072 }
                r4.flush()     // Catch:{ FileNotFoundException -> 0x0078, IOException -> 0x0075, all -> 0x0072 }
                r4.close()     // Catch:{ IOException -> 0x006a }
                goto L_0x006e
            L_0x006a:
                r0 = move-exception
                defpackage.kf.a(r0)     // Catch:{ Exception -> 0x00d9 }
            L_0x006e:
                r1.recycle()     // Catch:{ Exception -> 0x00d9 }
                goto L_0x00a9
            L_0x0072:
                r0 = move-exception
                r3 = r4
                goto L_0x009b
            L_0x0075:
                r0 = move-exception
                r3 = r4
                goto L_0x007e
            L_0x0078:
                r0 = move-exception
                r3 = r4
                goto L_0x008d
            L_0x007b:
                r0 = move-exception
                goto L_0x009b
            L_0x007d:
                r0 = move-exception
            L_0x007e:
                defpackage.kf.a(r0)     // Catch:{ all -> 0x007b }
                if (r3 == 0) goto L_0x006e
                r3.close()     // Catch:{ IOException -> 0x0087 }
                goto L_0x006e
            L_0x0087:
                r0 = move-exception
                defpackage.kf.a(r0)     // Catch:{ Exception -> 0x00d9 }
                goto L_0x006e
            L_0x008c:
                r0 = move-exception
            L_0x008d:
                defpackage.kf.a(r0)     // Catch:{ all -> 0x007b }
                if (r3 == 0) goto L_0x006e
                r3.close()     // Catch:{ IOException -> 0x0096 }
                goto L_0x006e
            L_0x0096:
                r0 = move-exception
                defpackage.kf.a(r0)     // Catch:{ Exception -> 0x00d9 }
                goto L_0x006e
            L_0x009b:
                if (r3 == 0) goto L_0x00a5
                r3.close()     // Catch:{ IOException -> 0x00a1 }
                goto L_0x00a5
            L_0x00a1:
                r2 = move-exception
                defpackage.kf.a(r2)     // Catch:{ Exception -> 0x00d9 }
            L_0x00a5:
                r1.recycle()     // Catch:{ Exception -> 0x00d9 }
                throw r0     // Catch:{ Exception -> 0x00d9 }
            L_0x00a9:
                dtd$b r0 = r6.a     // Catch:{ Exception -> 0x00d9 }
                if (r0 == 0) goto L_0x00d8
                java.lang.String r0 = r6.e     // Catch:{ OutOfMemoryError -> 0x00c5 }
                android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeFile(r0)     // Catch:{ OutOfMemoryError -> 0x00c5 }
                dtd$b r1 = r6.a     // Catch:{ OutOfMemoryError -> 0x00c5 }
                java.lang.String r2 = r6.e     // Catch:{ OutOfMemoryError -> 0x00c5 }
                dtd r3 = defpackage.dtd.this     // Catch:{ OutOfMemoryError -> 0x00c5 }
                r3.f     // Catch:{ OutOfMemoryError -> 0x00c5 }
                dtd r3 = defpackage.dtd.this     // Catch:{ OutOfMemoryError -> 0x00c5 }
                r3.m     // Catch:{ OutOfMemoryError -> 0x00c5 }
                r1.a(r0, r2)     // Catch:{ OutOfMemoryError -> 0x00c5 }
                return
            L_0x00c5:
                r0 = move-exception
                dtd$b r1 = r6.a     // Catch:{ Exception -> 0x00d9 }
                java.lang.Exception r2 = new java.lang.Exception     // Catch:{ Exception -> 0x00d9 }
                java.lang.String r3 = "Out of Memory"
                r2.<init>(r3)     // Catch:{ Exception -> 0x00d9 }
                r1.b()     // Catch:{ Exception -> 0x00d9 }
                defpackage.dtd.b()     // Catch:{ Exception -> 0x00d9 }
                r0.printStackTrace()     // Catch:{ Exception -> 0x00d9 }
            L_0x00d8:
                return
            L_0x00d9:
                r0 = move-exception
                defpackage.kf.a(r0)
                dtd$b r0 = r6.a
                r0.b()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.dtd.a.run():void");
        }
    }

    /* renamed from: dtd$b */
    /* compiled from: PicPhotoDialog */
    public interface b {
        void a();

        void a(Bitmap bitmap, String str);

        void b();
    }

    static /* synthetic */ void b() {
    }

    public static String a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append("/");
        sb.append(str.hashCode());
        sb.append(".jpg");
        return sb.toString();
    }

    public dtd(bid bid, Context context) {
        this(bid, context, 0);
    }

    private dtd(bid bid, Context context, byte b2) {
        super(bid.getActivity(), R.style.TrafficDialog);
        this.g = null;
        int i2 = 0;
        this.k = false;
        this.q = new HashMap<>();
        this.s = 7;
        Application application = AMapAppGlobal.getApplication();
        if (application != null) {
            if (VERSION.SDK_INT >= 29) {
                StringBuilder sb = new StringBuilder();
                sb.append(application.getExternalCacheDir().getAbsolutePath());
                sb.append(File.separator);
                sb.append("autonavi/errorpic");
                a = sb.toString();
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Environment.getExternalStorageDirectory());
                sb2.append("/autonavi/errorpic");
                a = sb2.toString();
            }
        }
        this.t = new WeakHashMap<>();
        this.o = bid;
        this.n = this.o.getActivity();
        this.j = true;
        this.h = 12288;
        this.i = 12289;
        this.l = 0;
        this.r = null;
        this.p = context;
        setContentView(R.layout.pick_photo_dialog);
        Window window = getWindow();
        window.setGravity(80);
        window.setLayout(-1, -2);
        window.findViewById(R.id.ibCamera).setOnClickListener(this);
        window.findViewById(R.id.ibScreenshot).setOnClickListener(this);
        window.findViewById(R.id.ibAlbum).setOnClickListener(this);
        window.findViewById(R.id.btnCancel).setOnClickListener(this);
        window.findViewById(R.id.llScreenshot).setVisibility((this.l <= 0 || (this.s & 4) <= 0) ? 8 : i2);
    }

    public final void a() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            c();
            return;
        }
        ToastHelper.showToast(AMapPageUtil.getAppContext().getResources().getString(R.string.publish_sd_notexist));
        dismiss();
    }

    public final void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ibCamera) {
            a();
        } else if (id == R.id.ibScreenshot) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("title", this.r);
                pageContext.startPageForResult(MapScreenshotPage.class, pageBundle, this.l);
            }
            dismiss();
        } else if (id == R.id.ibAlbum) {
            try {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                this.n.startActivityForResult(intent, this.i);
            } catch (ActivityNotFoundException unused) {
            }
            dismiss();
        } else {
            if (id == R.id.btnCancel) {
                dismiss();
            }
        }
    }

    private void c() {
        if (this.k || agq.b(getContext())) {
            if (this.j) {
                d();
            } else {
                e();
            }
            dismiss();
            return;
        }
        f();
    }

    private void d() {
        kj.a(this.n, new String[]{"android.permission.CAMERA"}, (defpackage.kj.b) new defpackage.kj.b() {
            public final void run() {
                try {
                    adu.a("autonavi/errorpic_take", dtd.this.n, dtd.this.h, new PicPhotoDialog$1$1(this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void e() {
        kj.a(this.n, new String[]{"android.permission.CAMERA"}, (defpackage.kj.b) new defpackage.kj.b() {
            public final void run() {
                try {
                    if (!FileUtil.PHOTO_DIR.exists()) {
                        FileUtil.PHOTO_DIR.mkdirs();
                    }
                    dtd.this.e = new File(FileUtil.PHOTO_DIR, ahb.a());
                    dtd.this.n.startActivityForResult(dtd.a((Context) dtd.this.n, dtd.this.e), dtd.this.h);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Intent a(Context context, File file) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (VERSION.SDK_INT >= 24) {
            intent.setFlags(1);
            intent.putExtra(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, FileProvider.getUriForFile(context, FileUtil.FILE_PROVIDER, file));
        } else {
            intent.putExtra(H5TinyAppLogUtil.TINY_APP_STANDARD_OUTPUT, Uri.fromFile(file));
        }
        return intent;
    }

    private void f() {
        com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(this.n);
        aVar.a(R.string.open_gps_title);
        aVar.a(17039370, (defpackage.ern.a) new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                dtd.this.o.dismissViewLayer(alertView);
            }
        });
        aVar.b(R.string.open_gps_left, (defpackage.ern.a) new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                dtd.this.o.dismissViewLayer(alertView);
                dtd.this.k = true;
                if (dtd.this.o instanceof dtb) {
                    dtd.this.o;
                }
            }
        });
        aVar.a(R.string.open_gps_right, (defpackage.ern.a) new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
                dtd.this.o.dismissViewLayer(alertView);
                Intent intent = new Intent();
                intent.setAction("android.settings.LOCATION_SOURCE_SETTINGS");
                intent.setFlags(268435456);
                try {
                    dtd.this.getContext().startActivity(intent);
                } catch (ActivityNotFoundException unused) {
                    intent.setAction("android.settings.SETTINGS");
                    try {
                        dtd.this.getContext().startActivity(intent);
                    } catch (Exception unused2) {
                    }
                }
            }
        });
        aVar.b = new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
            }
        };
        aVar.c = new defpackage.ern.a() {
            public final void onClick(AlertView alertView, int i) {
            }
        };
        aVar.a(false);
        AlertView a2 = aVar.a();
        this.o.showViewLayer(a2);
        a2.startAnimation();
    }

    private static int a(Options options) {
        int i2 = options.outHeight;
        int i3 = options.outWidth;
        if (i2 >= 1500 && i3 >= 2300) {
            return 4;
        }
        int i4 = 1;
        if (i2 > 480 || i3 > 320) {
            int i5 = i2 / 2;
            int i6 = i3 / 2;
            while (i5 / i4 > 480 && i6 / i4 > 320) {
                i4 *= 2;
            }
        }
        return i4;
    }

    public final Bitmap b(String str) {
        if (str == null || !new File(str).exists()) {
            return null;
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inSampleSize = a(options);
        options.inJustDecodeBounds = false;
        try {
            Bitmap decodeFile = BitmapFactory.decodeFile(str, options);
            MemoryInfo memoryInfo = new MemoryInfo();
            ((ActivityManager) this.n.getSystemService(WidgetType.ACTIVITY)).getMemoryInfo(memoryInfo);
            if (((int) (memoryInfo.availMem / 1048576)) < 150) {
                return decodeFile;
            }
            int c2 = c(str);
            if (c2 == 0) {
                return decodeFile;
            }
            Matrix matrix = new Matrix();
            int width = decodeFile.getWidth();
            int height = decodeFile.getHeight();
            matrix.setRotate((float) c2);
            try {
                Bitmap createBitmap = Bitmap.createBitmap(decodeFile, 0, 0, width, height, matrix, true);
                decodeFile.recycle();
                return createBitmap;
            } catch (OutOfMemoryError e2) {
                e2.printStackTrace();
                return null;
            } catch (Exception e3) {
                e3.printStackTrace();
                return null;
            }
        } catch (OutOfMemoryError e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public final Bitmap a(Uri uri) {
        if (uri == null) {
            return null;
        }
        Application application = AMapAppGlobal.getApplication();
        if (application == null) {
            return null;
        }
        ContentResolver contentResolver = application.getContentResolver();
        if (contentResolver == null) {
            return null;
        }
        try {
            InputStream openInputStream = contentResolver.openInputStream(uri);
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(openInputStream, null, options);
            openInputStream.close();
            if (options.outWidth != -1) {
                if (options.outHeight != -1) {
                    options.inSampleSize = a(options);
                    options.inJustDecodeBounds = false;
                    InputStream openInputStream2 = contentResolver.openInputStream(uri);
                    Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream2, null, options);
                    openInputStream2.close();
                    MemoryInfo memoryInfo = new MemoryInfo();
                    ((ActivityManager) this.n.getSystemService(WidgetType.ACTIVITY)).getMemoryInfo(memoryInfo);
                    return ((int) (memoryInfo.availMem / 1048576)) < 150 ? decodeStream : decodeStream;
                }
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static int c(String str) {
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt("Orientation", -1);
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt == 6) {
                return 90;
            }
            if (attributeInt != 8) {
                return 0;
            }
            return 270;
        } catch (IOException e2) {
            kf.a((Throwable) e2);
            return 0;
        }
    }
}
