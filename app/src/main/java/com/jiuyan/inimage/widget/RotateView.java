package com.jiuyan.inimage.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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

public class RotateView extends ComponentView implements OnClickListener {
    private final String c = "CropperActivity";
    /* access modifiers changed from: private */
    public PhotoCropViewFreedom d;
    private Uri e;
    private String f;
    /* access modifiers changed from: private */
    public Bitmap g;
    private View h;
    private View i;
    private View j;
    private View k;
    private View l;
    private View m;
    private ViewGroup n;
    /* access modifiers changed from: private */
    public boolean o = false;
    private int p = -1;
    private int q = -1;
    private View r;
    private OnPreDrawListener s = new ab(this);
    private boolean t = false;

    public RotateView(Context context) {
        super(context);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public RotateView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RotateView(Context context, AttributeSet attributeSet, int i2) {
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
        this.n = (ViewGroup) findViewById(R.id.rotate_rl_crop);
        this.e = (Uri) getIntent().getParcelableExtra("image_uri");
        this.f = getIntent().getStringExtra("image_save_path");
        this.d = (PhotoCropViewFreedom) findViewById(R.id.rotate_img);
        this.d.setFrameBackgroundColor(-14540254);
        this.d.setTouchEnabled(false);
        this.r = findViewById(R.id.inalipay_rotate_bottom);
        this.r.setOnClickListener(this);
        this.h = findViewById(R.id.rotate_left);
        this.i = findViewById(R.id.rotate_right);
        this.j = findViewById(R.id.mirror_hor);
        this.k = findViewById(R.id.mirror_ver);
        this.h.setOnClickListener(this);
        this.i.setOnClickListener(this);
        this.j.setOnClickListener(this);
        this.k.setOnClickListener(this);
        this.l = findViewById(R.id.mirror_back);
        this.m = findViewById(R.id.mirror_next);
        this.l.setOnClickListener(this);
        this.m.setOnClickListener(this);
        this.n.getViewTreeObserver().addOnPreDrawListener(this.s);
    }

    public void setBitmapPreviously(Bitmap bitmap) {
        this.g = bitmap;
        if (this.d != null) {
            this.d.setImageBitmap(bitmap);
        }
    }

    public void c() {
        q.a((String) "start rotate");
        f();
        setVisibility(4);
        post(new z(this));
    }

    /* access modifiers changed from: private */
    public void h() {
        q.a((String) "stop rotate");
        a(this.r, true, null);
    }

    public void d() {
        if (b()) {
            a(this.r, false, new aa(this));
        }
    }

    public void e() {
        this.p = -1;
        this.q = -1;
        this.d.a();
        this.t = false;
    }

    /* access modifiers changed from: protected */
    public void f() {
        if (b.a != null) {
            this.g = b.a;
        } else if (this.e != null && this.f != null) {
            if (this.g == null) {
                q.a("CropperActivity", "Can not found image from uri: " + this.e.toString());
                return;
            }
            q.a("CropperActivity", "src uri: " + this.e.toString() + "  des uri: " + this.f);
            int a = d.a(d.a(getContext().getContentResolver(), this.e));
            q.a("CropperActivity", "exif rotation: " + a);
            if (a != 0) {
                this.g = d.a(this.g, (float) a);
            }
            q.a("CropperActivity", "after rotation size: " + this.g.getWidth() + Token.SEPARATOR + this.g.getHeight());
        } else {
            return;
        }
        this.d.setImageBitmap(this.g);
    }

    public void a(View view) {
        PointF centerPoint = this.d.getCenterPoint();
        this.d.getSuppMatrix().postRotate(-90.0f, centerPoint.x, centerPoint.y);
        this.d.getSuppMatrix().postConcat(this.d.getTmpMatrix());
        this.d.setImageMatrix(this.d.getDisplayMatrix());
    }

    public void b(View view) {
        PointF centerPoint = this.d.getCenterPoint();
        this.d.getSuppMatrix().postRotate(90.0f, centerPoint.x, centerPoint.y);
        this.d.getSuppMatrix().postConcat(this.d.getTmpMatrix());
        this.d.setImageMatrix(this.d.getDisplayMatrix());
    }

    public void c(View view) {
        PointF centerPoint = this.d.getCenterPoint();
        this.d.getSuppMatrix().postScale(-1.0f, 1.0f, centerPoint.x, centerPoint.y);
        this.d.setImageMatrix(this.d.getDisplayMatrix());
    }

    public void d(View view) {
        PointF centerPoint = this.d.getCenterPoint();
        this.d.getSuppMatrix().postScale(1.0f, -1.0f, centerPoint.x, centerPoint.y);
        this.d.setImageMatrix(this.d.getDisplayMatrix());
    }

    public void e(View view) {
        Bitmap i2 = i();
        if (i2 == null) {
            a(getContext().getResources().getString(R.string.in_sdk_output_failed));
            return;
        }
        q.a("CropperActivity", "after crop bitmap size: " + i2.getWidth() + Token.SEPARATOR + i2.getHeight());
        b.a = i2;
        if (this.b != null) {
            this.b.onComponentDone(Integer.valueOf(2), Integer.valueOf(0), null);
        }
    }

    public static float a(Matrix matrix) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        return -((float) Math.round(Math.atan2((double) fArr[1], (double) fArr[0]) * 57.29577951308232d));
    }

    private Bitmap i() {
        float a = a(this.d.getDisplayMatrix());
        int height = a % 180.0f == 0.0f ? this.g.getWidth() : this.g.getHeight();
        int width = a % 180.0f == 0.0f ? this.g.getHeight() : this.g.getWidth();
        try {
            Bitmap bitmap = ((BitmapDrawable) this.d.getDrawable()).getBitmap();
            Bitmap createBitmap = Bitmap.createBitmap(height, width, Config.ARGB_8888);
            if (createBitmap == null) {
                q.a("CropperActivity", "Bitmap.createBitmap failed.");
                return null;
            }
            Canvas canvas = new Canvas(createBitmap);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
            RectF rectF = new RectF(0.0f, 0.0f, (float) height, (float) width);
            Matrix displayMatrix = this.d.getDisplayMatrix();
            Matrix matrix = new Matrix();
            matrix.setRectToRect(this.d.getDisplayRect(), rectF, ScaleToFit.FILL);
            displayMatrix.postConcat(matrix);
            canvas.drawBitmap(bitmap, displayMatrix, new Paint(1));
            System.gc();
            return createBitmap;
        } catch (OutOfMemoryError e2) {
            q.a("CropperActivity", "OOM cropping image: " + e2.getMessage());
            return null;
        } finally {
            System.gc();
        }
    }

    public boolean g() {
        return this.t;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.mirror_back) {
            if (this.b != null) {
                this.b.onComponentBack(Integer.valueOf(2), Integer.valueOf(0), null);
            }
        } else if (id == R.id.mirror_next) {
            e(view);
        } else if (id == R.id.mirror_hor) {
            this.t = true;
            c(view);
        } else if (id == R.id.mirror_ver) {
            this.t = true;
            d(view);
        } else if (id == R.id.rotate_left) {
            this.t = true;
            a(view);
        } else if (id == R.id.rotate_right) {
            this.t = true;
            b(view);
        }
    }
}
