package defpackage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.amap.bundle.blutils.zip.IBitmapCompressedListener;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.minimap.R;

/* renamed from: bjd reason: default package */
/* compiled from: BrowseDialog */
public final class bjd extends CompatDialog {
    OnClickListener a = new OnClickListener() {
        public final void onClick(View view) {
            int id = view.getId();
            if (id != R.id.browse_back) {
                if (id == R.id.layout_take_photo || id == R.id.take_photo) {
                    bjd.this.i.onClick(view);
                    return;
                } else if (id == R.id.from_files || id == R.id.layout_from_files) {
                    bjd.this.j.onClick(view);
                    return;
                }
            }
            bjd.this.dismiss();
        }
    };
    private ImageView b;
    /* access modifiers changed from: private */
    public ImageView c;
    private ImageButton d;
    private ImageButton e;
    private ImageButton f;
    private View g;
    /* access modifiers changed from: private */
    public ProgressBar h;
    /* access modifiers changed from: private */
    public OnClickListener i = null;
    /* access modifiers changed from: private */
    public OnClickListener j = null;
    /* access modifiers changed from: private */
    public Handler k = new Handler();
    private int l = 1;

    /* renamed from: bjd$a */
    /* compiled from: BrowseDialog */
    class a implements bkf {
        public final void onPrepareLoad(Drawable drawable) {
        }

        private a() {
        }

        /* synthetic */ a(bjd bjd, byte b) {
            this();
        }

        public final void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
            bjd.this.h.setVisibility(8);
        }

        public final void onBitmapFailed(Drawable drawable) {
            bjd.this.h.setVisibility(8);
            ToastHelper.showToast(AMapAppGlobal.getApplication().getResources().getString(R.string.request_image_fail));
        }
    }

    /* renamed from: bjd$b */
    /* compiled from: BrowseDialog */
    public static class b extends Thread {
        IBitmapCompressedListener a = null;
        private String b = null;
        private String c = null;

        public b(String str, String str2) {
            this.b = str;
            this.c = str2;
        }

        /* JADX WARNING: Removed duplicated region for block: B:32:0x0062 A[Catch:{ all -> 0x005b }] */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x006c A[SYNTHETIC, Splitter:B:35:0x006c] */
        /* JADX WARNING: Removed duplicated region for block: B:47:0x0083 A[Catch:{ all -> 0x005b }] */
        /* JADX WARNING: Removed duplicated region for block: B:50:0x008d A[SYNTHETIC, Splitter:B:50:0x008d] */
        /* JADX WARNING: Removed duplicated region for block: B:60:0x00a3 A[Catch:{ Exception -> 0x00c8 }] */
        /* JADX WARNING: Removed duplicated region for block: B:63:0x00b3 A[SYNTHETIC, Splitter:B:63:0x00b3] */
        /* JADX WARNING: Unknown top exception splitter block from list: {B:29:0x005e=Splitter:B:29:0x005e, B:44:0x007f=Splitter:B:44:0x007f} */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
                r5 = this;
                java.lang.String r0 = r5.b     // Catch:{ Exception -> 0x00c8 }
                r1 = 320(0x140, float:4.48E-43)
                r2 = 480(0x1e0, float:6.73E-43)
                android.graphics.Bitmap r0 = defpackage.kp.a(r0, r1, r2)     // Catch:{ Exception -> 0x00c8 }
                if (r0 != 0) goto L_0x000d
                return
            L_0x000d:
                java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x00c8 }
                r1.<init>()     // Catch:{ Exception -> 0x00c8 }
                android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Exception -> 0x00c8 }
                r3 = 50
                r0.compress(r2, r3, r1)     // Catch:{ Exception -> 0x00c8 }
                byte[] r1 = r1.toByteArray()     // Catch:{ Exception -> 0x00c8 }
                java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00c8 }
                java.lang.String r3 = r5.c     // Catch:{ Exception -> 0x00c8 }
                r2.<init>(r3)     // Catch:{ Exception -> 0x00c8 }
                boolean r3 = r2.exists()     // Catch:{ Exception -> 0x00c8 }
                if (r3 != 0) goto L_0x0031
                java.io.File r3 = r2.getParentFile()     // Catch:{ Exception -> 0x00c8 }
                r3.mkdirs()     // Catch:{ Exception -> 0x00c8 }
            L_0x0031:
                r3 = 0
                java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x007e, IOException -> 0x005d }
                r4.<init>(r2)     // Catch:{ FileNotFoundException -> 0x007e, IOException -> 0x005d }
                r4.write(r1)     // Catch:{ FileNotFoundException -> 0x0058, IOException -> 0x0055, all -> 0x0052 }
                r4.flush()     // Catch:{ FileNotFoundException -> 0x0058, IOException -> 0x0055, all -> 0x0052 }
                r4.close()     // Catch:{ IOException -> 0x0041 }
                goto L_0x004e
            L_0x0041:
                r1 = move-exception
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r2 = r5.a     // Catch:{ Exception -> 0x00c8 }
                if (r2 == 0) goto L_0x004b
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r2 = r5.a     // Catch:{ Exception -> 0x00c8 }
                r2.onException(r1)     // Catch:{ Exception -> 0x00c8 }
            L_0x004b:
                r1.printStackTrace()     // Catch:{ Exception -> 0x00c8 }
            L_0x004e:
                r0.recycle()     // Catch:{ Exception -> 0x00c8 }
                goto L_0x009f
            L_0x0052:
                r1 = move-exception
                r3 = r4
                goto L_0x00b1
            L_0x0055:
                r1 = move-exception
                r3 = r4
                goto L_0x005e
            L_0x0058:
                r1 = move-exception
                r3 = r4
                goto L_0x007f
            L_0x005b:
                r1 = move-exception
                goto L_0x00b1
            L_0x005d:
                r1 = move-exception
            L_0x005e:
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r2 = r5.a     // Catch:{ all -> 0x005b }
                if (r2 == 0) goto L_0x0067
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r2 = r5.a     // Catch:{ all -> 0x005b }
                r2.onException(r1)     // Catch:{ all -> 0x005b }
            L_0x0067:
                r1.printStackTrace()     // Catch:{ all -> 0x005b }
                if (r3 == 0) goto L_0x004e
                r3.close()     // Catch:{ IOException -> 0x0070 }
                goto L_0x004e
            L_0x0070:
                r1 = move-exception
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r2 = r5.a     // Catch:{ Exception -> 0x00c8 }
                if (r2 == 0) goto L_0x007a
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r2 = r5.a     // Catch:{ Exception -> 0x00c8 }
                r2.onException(r1)     // Catch:{ Exception -> 0x00c8 }
            L_0x007a:
                r1.printStackTrace()     // Catch:{ Exception -> 0x00c8 }
                goto L_0x004e
            L_0x007e:
                r1 = move-exception
            L_0x007f:
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r2 = r5.a     // Catch:{ all -> 0x005b }
                if (r2 == 0) goto L_0x0088
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r2 = r5.a     // Catch:{ all -> 0x005b }
                r2.onException(r1)     // Catch:{ all -> 0x005b }
            L_0x0088:
                r1.printStackTrace()     // Catch:{ all -> 0x005b }
                if (r3 == 0) goto L_0x004e
                r3.close()     // Catch:{ IOException -> 0x0091 }
                goto L_0x004e
            L_0x0091:
                r1 = move-exception
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r2 = r5.a     // Catch:{ Exception -> 0x00c8 }
                if (r2 == 0) goto L_0x009b
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r2 = r5.a     // Catch:{ Exception -> 0x00c8 }
                r2.onException(r1)     // Catch:{ Exception -> 0x00c8 }
            L_0x009b:
                r1.printStackTrace()     // Catch:{ Exception -> 0x00c8 }
                goto L_0x004e
            L_0x009f:
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r0 = r5.a     // Catch:{ Exception -> 0x00c8 }
                if (r0 == 0) goto L_0x00b0
                java.lang.String r0 = r5.c     // Catch:{ Exception -> 0x00c8 }
                android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeFile(r0)     // Catch:{ Exception -> 0x00c8 }
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r1 = r5.a     // Catch:{ Exception -> 0x00c8 }
                java.lang.String r2 = r5.b     // Catch:{ Exception -> 0x00c8 }
                r1.onCompress(r0, r2)     // Catch:{ Exception -> 0x00c8 }
            L_0x00b0:
                return
            L_0x00b1:
                if (r3 == 0) goto L_0x00c4
                r3.close()     // Catch:{ IOException -> 0x00b7 }
                goto L_0x00c4
            L_0x00b7:
                r2 = move-exception
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r3 = r5.a     // Catch:{ Exception -> 0x00c8 }
                if (r3 == 0) goto L_0x00c1
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r3 = r5.a     // Catch:{ Exception -> 0x00c8 }
                r3.onException(r2)     // Catch:{ Exception -> 0x00c8 }
            L_0x00c1:
                r2.printStackTrace()     // Catch:{ Exception -> 0x00c8 }
            L_0x00c4:
                r0.recycle()     // Catch:{ Exception -> 0x00c8 }
                throw r1     // Catch:{ Exception -> 0x00c8 }
            L_0x00c8:
                r0 = move-exception
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r1 = r5.a
                if (r1 == 0) goto L_0x00d2
                com.amap.bundle.blutils.zip.IBitmapCompressedListener r1 = r5.a
                r1.onException(r0)
            L_0x00d2:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.bjd.b.run():void");
        }
    }

    public bjd(Activity activity) {
        super(activity, R.style.TrafficDialog);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.browse_layout);
        this.g = findViewById(R.id.browse_bottom);
        this.g.setOnClickListener(this.a);
        this.c = (ImageView) findViewById(R.id.img_browse);
        this.b = (ImageView) findViewById(R.id.img_net_browse);
        this.d = (ImageButton) findViewById(R.id.browse_back);
        this.d.setOnClickListener(this.a);
        this.e = (ImageButton) findViewById(R.id.take_photo);
        this.e.setOnClickListener(this.a);
        findViewById(R.id.layout_take_photo).setOnClickListener(this.a);
        this.f = (ImageButton) findViewById(R.id.from_files);
        this.f.setOnClickListener(this.a);
        findViewById(R.id.layout_from_files).setOnClickListener(this.a);
        getWindow().setLayout(-1, -1);
        getWindow().setBackgroundDrawable(new ColorDrawable(AMapAppGlobal.getApplication().getResources().getColor(R.color.browse_dialog_background)));
        this.h = (ProgressBar) findViewById(R.id.progress_bar);
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        motionEvent.getAction();
        dismiss();
        return true;
    }

    public final void a(String str) {
        if (this.l == 2) {
            this.c.setVisibility(8);
            this.b.setVisibility(0);
            ko.a(this.b, str, null, R.drawable.tmc_tipdetail, new a(this, 0));
            return;
        }
        if (this.l == 1) {
            this.c.setVisibility(0);
            this.b.setVisibility(8);
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory());
            sb.append("/autonavi/out.jpg");
            b bVar = new b(str, sb.toString());
            bVar.a = new IBitmapCompressedListener() {
                public final void onException(Exception exc) {
                }

                public final void onCompress(final Bitmap bitmap, String str) {
                    bjd.this.k.post(new Runnable() {
                        public final void run() {
                            bjd.this.c.setImageBitmap(bitmap);
                        }
                    });
                }
            };
            bVar.start();
        }
    }

    public final void a() {
        this.l = 2;
        this.g.setVisibility(4);
        this.d.setVisibility(4);
    }
}
