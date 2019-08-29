package com.jiuyan.inimage.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.opengl.GLES10;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.Toast;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.jiuyan.inimage.util.b;
import com.jiuyan.inimage.util.d;
import com.jiuyan.inimage.util.q;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class CropperView extends ComponentView implements OnClickListener, s {
    private static int d = 2048;
    private static int e = 3072;
    private static int f = 4096;
    private int A;
    private int B = -1;
    private int C = -1;
    /* access modifiers changed from: private */
    public boolean D = true;
    private View E;
    private int F = 0;
    /* access modifiers changed from: private */
    public boolean G;
    private OnPreDrawListener H = new d(this);
    private boolean I = false;
    private final String c = "CropperActivity";
    private int[] g = new int[2];
    private int[] h = new int[2];
    private int[] i = new int[2];
    private int[] j = new int[2];
    private int[] k = new int[2];
    private PhotoCropViewFreedom l;
    private Uri m;
    private String n;
    /* access modifiers changed from: private */
    public Bitmap o;
    private View p;
    private View q;
    private View r;
    private View s;
    private View t;
    private View u;
    private View v;
    private int w;
    private int x;
    private final String y = "#ff1E82D2";
    private ViewGroup z;

    public CropperView(Context context) {
        super(context);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public CropperView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CropperView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }

    /* access modifiers changed from: 0000 */
    public void a(String str) {
        if (getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).toast(str, 0);
        } else if (getContext() != null) {
            Toast.makeText(getContext(), str, 0).show();
        }
    }

    private Intent getIntent() {
        return ((Activity) getContext()).getIntent();
    }

    public void a() {
        this.z = (ViewGroup) findViewById(R.id.rl_crop);
        this.m = (Uri) getIntent().getParcelableExtra("image_uri");
        this.n = getIntent().getStringExtra("image_save_path");
        this.B = getIntent().getIntExtra("indic_ratio", -1);
        this.C = getIntent().getIntExtra("indic_compress_size", -1);
        int a = d.a(getContext(), 3.0f);
        if (a % 2 != 0) {
            a++;
        }
        this.A = a;
        this.l = (PhotoCropViewFreedom) findViewById(R.id.img);
        this.l.setOnDoActionListener(new a(this));
        this.E = findViewById(R.id.inalipay_cropper_bottom);
        this.E.setOnClickListener(this);
        this.F = d.a(getContext(), 54.0f);
        this.E.setTranslationY((float) this.F);
        this.r = findViewById(R.id.ratio_11);
        this.p = findViewById(R.id.ratio_34);
        this.q = findViewById(R.id.ratio_43);
        this.s = findViewById(R.id.ratio_916);
        this.t = findViewById(R.id.ratio_169);
        this.p.setOnClickListener(this);
        this.q.setOnClickListener(this);
        this.r.setOnClickListener(this);
        this.s.setOnClickListener(this);
        this.t.setOnClickListener(this);
        this.u = findViewById(R.id.btn_back);
        this.v = findViewById(R.id.btn_next);
        this.u.setOnClickListener(this);
        this.v.setOnClickListener(this);
        this.z.getViewTreeObserver().addOnPreDrawListener(this.H);
    }

    public void setBitmapPreviously(Bitmap bitmap) {
        this.o = bitmap;
        if (this.l != null) {
            this.l.setImageBitmap(bitmap);
        }
        this.E.setTranslationY(0.0f);
    }

    public void c() {
        q.a((String) "start crop");
        f();
        setVisibility(4);
        post(new b(this));
    }

    /* access modifiers changed from: private */
    public void i() {
        a(this.E, true, null);
    }

    public void d() {
        q.a((String) "stop crop");
        if (b()) {
            a(this.E, false, new c(this));
        }
    }

    public void e() {
        this.B = -1;
        this.C = -1;
        this.D = true;
        this.o = null;
        this.l.a();
        this.I = false;
    }

    /* access modifiers changed from: protected */
    public void f() {
        if (b.a != null) {
            this.o = b.a;
        } else if (this.m != null && this.n != null) {
            this.o = a(this.m);
            if (this.o == null) {
                q.a("CropperActivity", "Can not found image from uri: " + this.m.toString());
                return;
            }
            int a = d.a(d.a(getContext().getContentResolver(), this.m));
            if (a != 0) {
                this.o = d.a(this.o, (float) a);
            }
        } else {
            return;
        }
        this.l.setImageBitmap(this.o);
        this.l.setOnOperateListener(this);
        j();
    }

    /* access modifiers changed from: private */
    public void j() {
        int[] iArr;
        float f2 = (0.5625f + 0.75f) / 2.0f;
        float f3 = (0.75f + 1.0f) / 2.0f;
        float f4 = (1.0f + 1.3333334f) / 2.0f;
        float f5 = (1.3333334f + 1.7777778f) / 2.0f;
        float width = (float) this.o.getWidth();
        float height = (float) this.o.getHeight();
        float f6 = width / height;
        this.j = a(4);
        this.h = a(2);
        this.g = a(1);
        this.i = a(3);
        this.k = a(5);
        if (f6 <= f2) {
            iArr = this.j;
            setButtonStatus(this.s);
        } else if (f6 > f2 && width / height <= f3) {
            iArr = this.h;
            setButtonStatus(this.p);
        } else if (f6 > f3 && f6 <= f4) {
            iArr = this.g;
            setButtonStatus(this.r);
        } else if (f6 <= f4 || f6 > f5) {
            iArr = this.k;
            setButtonStatus(this.t);
        } else {
            iArr = this.i;
            setButtonStatus(this.q);
        }
        if (this.B == -1) {
            this.w = iArr[0];
            this.x = iArr[1];
            this.l.a(this.w, this.x, this.A, (String) "#ff1E82D2");
            return;
        }
        int[] a = a(this.B);
        this.w = a[0];
        this.x = a[1];
        this.l.a(this.w, this.x, this.A, (String) "#ff1E82D2");
    }

    private Bitmap k() {
        try {
            float displayScale = this.l.getDisplayScale();
            q.a("CropperActivity", "display scale: " + displayScale);
            RectF cropperRectForDrawable = this.l.getCropperRectForDrawable();
            q.a("CropperActivity", "frame rect: " + cropperRectForDrawable);
            cropperRectForDrawable.left = (float) ((int) (cropperRectForDrawable.left / displayScale));
            cropperRectForDrawable.top = (float) ((int) (cropperRectForDrawable.top / displayScale));
            cropperRectForDrawable.right = (float) ((int) (cropperRectForDrawable.right / displayScale));
            cropperRectForDrawable.bottom = (float) ((int) (cropperRectForDrawable.bottom / displayScale));
            q.a("CropperActivity", "after scale restore: " + cropperRectForDrawable);
            Rect rect = new Rect((int) cropperRectForDrawable.left, (int) cropperRectForDrawable.top, (int) cropperRectForDrawable.right, (int) cropperRectForDrawable.bottom);
            Drawable drawable = this.l.getDrawable();
            if (drawable == null) {
                q.a((String) "crop create, but origin bitmap is null");
                return null;
            }
            Bitmap a = a(((BitmapDrawable) drawable).getBitmap(), rect, rect.width(), rect.height(), rect.width(), rect.height());
            q.a("CropperActivity", "rect width: " + rect.width() + "  height: " + rect.height());
            return a;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        } catch (OutOfMemoryError e3) {
            e3.printStackTrace();
            return null;
        }
    }

    private Bitmap a(Bitmap bitmap, Rect rect, int i2, int i3, int i4, int i5) {
        System.gc();
        float measuredWidth = ((float) this.l.getMeasuredWidth()) * 1.5f;
        float measuredHeight = ((float) this.l.getMeasuredHeight()) * 1.5f;
        float f2 = ((float) i4) / measuredWidth;
        float f3 = ((float) i5) / measuredHeight;
        if (f2 > 1.0f || f3 > 1.0f) {
            if (f2 == Math.max(f2, f3)) {
                i4 = (int) measuredWidth;
                i5 = (int) (((float) i5) / f2);
            } else {
                i5 = (int) measuredHeight;
                i4 = (int) (((float) i4) / f3);
            }
        }
        try {
            Bitmap createBitmap = Bitmap.createBitmap(i4, i5, Config.ARGB_8888);
            if (createBitmap == null) {
                q.a("CropperActivity", "Bitmap.createBitmap failed.");
                return null;
            }
            Canvas canvas = new Canvas(createBitmap);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
            RectF rectF = new RectF(0.0f, 0.0f, (float) i4, (float) i5);
            Matrix matrix = new Matrix();
            matrix.setRectToRect(new RectF(rect), rectF, ScaleToFit.FILL);
            canvas.drawColor(-1);
            canvas.drawBitmap(bitmap, matrix, new Paint(1));
            System.gc();
            return createBitmap;
        } catch (OutOfMemoryError e2) {
            q.a("CropperActivity", "OOM cropping image: " + e2.getMessage());
            q.a("CropperActivity", "OOM cropping image: " + e2.getMessage());
            return null;
        } finally {
            System.gc();
        }
    }

    public void a(View view) {
        Bitmap k2 = k();
        if (k2 == null) {
            a(getContext().getResources().getString(R.string.in_sdk_output_failed));
            return;
        }
        q.a("CropperActivity", "after crop bitmap size: " + k2.getWidth() + Token.SEPARATOR + k2.getHeight());
        b.a = k2;
        if (this.b != null) {
            this.b.onComponentDone(Integer.valueOf(1), Integer.valueOf(0), null);
        }
    }

    private Bitmap a(Uri uri) {
        InputStream inputStream;
        Bitmap bitmap = null;
        int i2 = 1;
        try {
            inputStream = b(uri);
        } catch (IOException e2) {
            try {
                e2.printStackTrace();
                inputStream = null;
            } catch (Exception e3) {
                e = e3;
                inputStream = null;
                try {
                    q.a("CropperActivity", e.toString());
                    d.a((Closeable) inputStream);
                    return bitmap;
                } catch (Throwable th) {
                    th = th;
                    d.a((Closeable) inputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                inputStream = null;
                th = th2;
                d.a((Closeable) inputStream);
                throw th;
            }
        }
        if (inputStream == null) {
            try {
                q.a("CropperActivity", "inputStream is null !");
            } catch (Exception e4) {
                e = e4;
                q.a("CropperActivity", e.toString());
                d.a((Closeable) inputStream);
                return bitmap;
            }
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        q.a("CropperActivity", "raw size: " + options.outWidth + "  " + options.outHeight);
        l();
        int i3 = this.C == -1 ? getMaxImageSize() : this.C;
        q.a("CropperActivity", "max image size: " + i3);
        while (true) {
            if (options.outHeight / i2 <= i3 && options.outWidth / i2 <= i3) {
                break;
            }
            i2 <<= 1;
        }
        q.a("CropperActivity", "sample size: " + i2);
        d.a((Closeable) inputStream);
        InputStream b = b(uri);
        Options options2 = new Options();
        options2.inSampleSize = i2;
        bitmap = BitmapFactory.decodeStream(b, null, options2);
        d.a((Closeable) b);
        return bitmap;
    }

    private void l() {
        long[] a = d.a();
        if (a != null) {
            long j2 = a[0];
            long j3 = a[1];
            if (j2 != 0) {
                q.a("CropperActivity", "memory total: " + j2 + "MB  free: " + j3 + "MB");
                q.a("CropperActivity", "memory total: " + j2 + "MB  free: " + j3 + "MB");
                if (j2 < 512) {
                    d = 720;
                    e = 1024;
                    f = 1536;
                } else if (j2 < 1024) {
                    d = 1024;
                    e = 2048;
                    f = 2560;
                } else if (j2 < 2048) {
                    d = 2048;
                    e = 2560;
                    f = 3072;
                } else {
                    d = 2048;
                    e = 3072;
                    f = 4096;
                }
            }
        }
    }

    private int getMaxImageSize() {
        int maxTextureSize = getMaxTextureSize();
        q.a("CropperActivity", "max texture size: " + maxTextureSize);
        if (VERSION.SDK_INT >= 21) {
            return f;
        }
        if (maxTextureSize <= 0) {
            return e;
        }
        return Math.min(maxTextureSize, f);
    }

    private int getMaxTextureSize() {
        int[] iArr = new int[1];
        GLES10.glGetIntegerv(3379, iArr, 0);
        return iArr[0];
    }

    private InputStream b(Uri uri) {
        try {
            if (uri.getScheme().equals("file")) {
                return new FileInputStream(uri.getPath());
            }
            return getContext().getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e2) {
            return null;
        }
    }

    public void b(View view) {
        int i2 = this.g[0];
        int i3 = this.g[1];
        if (this.w != i2 || this.x != i3) {
            PhotoCropViewFreedom photoCropViewFreedom = this.l;
            photoCropViewFreedom.a();
            photoCropViewFreedom.a(i2, i3, this.A, (String) "#ff1E82D2");
            this.w = i2;
            this.x = i3;
        }
    }

    public void c(View view) {
        int i2 = this.h[0];
        int i3 = this.h[1];
        if (this.w != i2 || this.x != i3) {
            q.a("CropperActivity", "frame: " + i2 + Token.SEPARATOR + i3);
            PhotoCropViewFreedom photoCropViewFreedom = this.l;
            photoCropViewFreedom.a();
            photoCropViewFreedom.a(i2, i3, this.A, (String) "#ff1E82D2");
            this.w = i2;
            this.x = i3;
        }
    }

    public void d(View view) {
        int i2 = this.i[0];
        int i3 = this.i[1];
        if (this.w != i2 || this.x != i3) {
            q.a("CropperActivity", "frame: " + i2 + Token.SEPARATOR + i3);
            PhotoCropViewFreedom photoCropViewFreedom = this.l;
            photoCropViewFreedom.a();
            photoCropViewFreedom.a(i2, i3, this.A, (String) "#ff1E82D2");
            this.w = i2;
            this.x = i3;
        }
    }

    public void e(View view) {
        int i2 = this.j[0];
        int i3 = this.j[1];
        if (this.w != i2 || this.x != i3) {
            q.a("CropperActivity", "frame: " + i2 + Token.SEPARATOR + i3);
            PhotoCropViewFreedom photoCropViewFreedom = this.l;
            photoCropViewFreedom.a();
            photoCropViewFreedom.a(i2, i3, this.A, (String) "#ff1E82D2");
            this.w = i2;
            this.x = i3;
        }
    }

    public void f(View view) {
        int i2 = this.k[0];
        int i3 = this.k[1];
        if (this.w != i2 || this.x != i3) {
            q.a("CropperActivity", "frame: " + i2 + Token.SEPARATOR + i3);
            PhotoCropViewFreedom photoCropViewFreedom = this.l;
            photoCropViewFreedom.a();
            photoCropViewFreedom.a(i2, i3, this.A, (String) "#ff1E82D2");
            this.w = i2;
            this.x = i3;
        }
    }

    private int[] a(int i2) {
        int[] iArr = new int[2];
        switch (i2) {
            case 1:
                iArr[0] = d.a(getContext());
                iArr[1] = d.a(getContext());
                break;
            case 2:
                iArr[0] = d.a(getContext());
                iArr[1] = (int) (((double) d.a(getContext())) * 1.3333333333333333d);
                if (iArr[1] > this.z.getHeight()) {
                    iArr[1] = this.z.getHeight();
                    iArr[0] = (int) (((double) iArr[1]) * 0.75d);
                    break;
                }
                break;
            case 3:
                iArr[0] = d.a(getContext());
                iArr[1] = (int) (((double) d.a(getContext())) * 0.75d);
                break;
            case 4:
                iArr[0] = (int) (((double) this.z.getHeight()) * 0.5625d);
                if (iArr[0] <= d.a(getContext())) {
                    iArr[1] = this.z.getHeight();
                    break;
                } else {
                    iArr[0] = d.a(getContext());
                    iArr[1] = (int) (((double) iArr[0]) * 1.7777777777777777d);
                    break;
                }
            case 5:
                iArr[0] = d.a(getContext());
                iArr[1] = (int) (((double) iArr[0]) * 0.5625d);
                break;
            default:
                iArr[0] = d.a(getContext());
                iArr[1] = d.a(getContext());
                break;
        }
        return iArr;
    }

    public boolean g() {
        return this.I;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_next) {
            a(view);
        } else if (id == R.id.btn_back) {
            if (this.b != null) {
                this.b.onComponentBack(Integer.valueOf(1), Integer.valueOf(0), null);
            }
        } else if (id == R.id.ratio_34) {
            this.I = true;
            c(view);
            setButtonStatus(view);
        } else if (id == R.id.ratio_43) {
            this.I = true;
            d(view);
            setButtonStatus(view);
        } else if (id == R.id.ratio_11) {
            this.I = true;
            b(view);
            setButtonStatus(view);
        } else if (id == R.id.ratio_916) {
            this.I = true;
            e(view);
            setButtonStatus(view);
        } else if (id == R.id.ratio_169) {
            this.I = true;
            f(view);
            setButtonStatus(view);
        }
    }

    private void setButtonStatus(View view) {
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5 = true;
        this.r.setSelected(this.r == view);
        View view2 = this.p;
        if (this.p == view) {
            z2 = true;
        } else {
            z2 = false;
        }
        view2.setSelected(z2);
        View view3 = this.q;
        if (this.q == view) {
            z3 = true;
        } else {
            z3 = false;
        }
        view3.setSelected(z3);
        View view4 = this.s;
        if (this.s == view) {
            z4 = true;
        } else {
            z4 = false;
        }
        view4.setSelected(z4);
        View view5 = this.t;
        if (this.t != view) {
            z5 = false;
        }
        view5.setSelected(z5);
    }

    public void a(float f2, float f3) {
        this.I = true;
    }

    public void a(float f2, float f3, float f4) {
        this.I = true;
    }

    public void h() {
        this.I = true;
    }
}
