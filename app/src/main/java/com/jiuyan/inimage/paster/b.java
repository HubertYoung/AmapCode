package com.jiuyan.inimage.paster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;
import com.jiuyan.inimage.util.q;

/* compiled from: CustomPasterObject */
public class b implements d {
    private static final String b = b.class.getSimpleName();
    private PointF A = new PointF();
    private boolean B = false;
    private boolean C = false;
    private String D;
    private boolean E = true;
    private boolean F;
    private boolean G;
    private int c = 0;
    private PaintFlagsDrawFilter d = new PaintFlagsDrawFilter(0, 3);
    private e e = new e();
    private String f;
    private Matrix g = new Matrix();
    private Matrix h = new Matrix();
    private Matrix i = new Matrix();
    private Context j;
    private Bitmap k;
    private float l = 0.0f;
    private float m = 0.0f;
    private float n = 1.0f;
    private float o = 0.0f;
    private a p;
    private boolean q = false;
    private Bitmap r;
    private Bitmap s;
    private Bitmap t;
    private int u;
    private int v;
    private Paint w;
    private f x;
    private c y;
    private PointF z = new PointF();

    public b(Context context, Bitmap bitmap) {
        this.g.reset();
        this.h.reset();
        this.i.reset();
        this.j = context;
        this.k = bitmap;
        this.e.a = new PointF(0.0f, 0.0f);
        this.e.b = new PointF((float) this.k.getWidth(), 0.0f);
        this.e.c = new PointF((float) this.k.getWidth(), (float) this.k.getHeight());
        this.e.d = new PointF(0.0f, (float) this.k.getHeight());
        RectF rectF = new RectF(0.0f, 0.0f, (float) this.k.getWidth(), (float) this.k.getHeight());
        this.e.e = new PointF(rectF.centerX(), rectF.centerY());
        this.r = BitmapFactory.decodeResource(this.j.getResources(), R.drawable.in_alipay_bussiness_edit_del);
        this.s = BitmapFactory.decodeResource(this.j.getResources(), R.drawable.in_alipay_bussiness_edit_symmetry);
        this.t = BitmapFactory.decodeResource(this.j.getResources(), R.drawable.in_alipay_bussiness_edit_control);
        this.u = this.r.getWidth();
        this.v = this.r.getHeight();
        this.w = new Paint();
        this.w.setColor(-1);
        this.w.setStrokeWidth(2.0f);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(f fVar) {
        this.x = fVar;
    }

    public void a(Matrix matrix, boolean z2) {
        if (z2) {
            Bitmap a = a(this.k);
            if (a != null) {
                this.k = a;
            }
        }
        this.C = z2;
        this.h.set(matrix);
        this.g.set(this.h);
        i();
    }

    public void a(Canvas canvas) {
        canvas.save();
        canvas.setDrawFilter(this.d);
        canvas.drawBitmap(this.k, this.g, null);
        if (this.q) {
            canvas.drawLine(this.e.a.x, this.e.a.y, this.e.b.x, this.e.b.y, this.w);
            canvas.drawLine(this.e.b.x, this.e.b.y, this.e.c.x, this.e.c.y, this.w);
            canvas.drawLine(this.e.c.x, this.e.c.y, this.e.d.x, this.e.d.y, this.w);
            canvas.drawLine(this.e.d.x, this.e.d.y, this.e.a.x, this.e.a.y, this.w);
            canvas.drawBitmap(this.r, this.e.a.x - ((float) (this.u / 2)), this.e.a.y - ((float) (this.v / 2)), null);
            canvas.drawBitmap(this.s, this.e.b.x - ((float) (this.u / 2)), this.e.b.y - ((float) (this.v / 2)), null);
            canvas.drawBitmap(this.t, this.e.c.x - ((float) (this.u / 2)), this.e.c.y - ((float) (this.v / 2)), null);
        }
        canvas.restore();
    }

    public e a() {
        return this.e;
    }

    public boolean a(PointF pointF) {
        return b(pointF);
    }

    public void a(String str) {
        this.f = str;
    }

    public String b() {
        return this.f;
    }

    public boolean b(PointF pointF) {
        boolean z2;
        boolean z3;
        q.a(b, "isContainPoint: " + pointF.toString());
        float[] fArr = new float[2];
        Matrix matrix = new Matrix();
        matrix.set(this.g);
        if (!matrix.invert(matrix)) {
            q.a(b, "can not revert matrix");
            return false;
        }
        matrix.mapPoints(fArr, new float[]{pointF.x, pointF.y});
        q.a(b, "after map: " + fArr.toString());
        if (fArr[0] <= 0.0f || fArr[0] >= ((float) this.k.getWidth()) || fArr[1] <= 0.0f || fArr[1] >= ((float) this.k.getHeight())) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (a(pointF.x, pointF.y) != 0) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z2 || z3) {
            return true;
        }
        return false;
    }

    public static Bitmap a(Bitmap bitmap) {
        Bitmap bitmap2;
        Matrix matrix = new Matrix();
        matrix.setValues(new float[]{-1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f});
        try {
            bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Exception e2) {
            e2.printStackTrace();
            q.a(b, "createYMirrorBitmap occur exception: " + e2.getMessage());
            bitmap2 = null;
        }
        System.gc();
        return bitmap2;
    }

    public int a(float f2, float f3) {
        if (a(f2, f3, this.e.a.x, this.e.a.y) < ((float) (this.u / 2))) {
            return 1;
        }
        if (a(f2, f3, this.e.b.x, this.e.b.y) < ((float) (this.u / 2))) {
            return 2;
        }
        if (a(f2, f3, this.e.c.x, this.e.c.y) < ((float) (this.u / 2))) {
            return 3;
        }
        return 0;
    }

    public boolean a(MotionEvent motionEvent) {
        boolean z2 = false;
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.A.set(motionEvent.getX(), motionEvent.getY());
                this.l = motionEvent.getX();
                this.m = motionEvent.getY();
                this.i.set(this.g);
                if (a(motionEvent.getX(), motionEvent.getY()) != 1) {
                    if (a(motionEvent.getX(), motionEvent.getY()) != 2) {
                        if (a(motionEvent.getX(), motionEvent.getY()) != 3) {
                            this.c = 1;
                            break;
                        } else {
                            this.c = 3;
                            this.n = a(this.e.e.x, this.e.e.y, motionEvent.getX(), motionEvent.getY());
                            this.o = c(motionEvent);
                            this.z.set(this.e.e.x, this.e.e.y);
                            this.i.set(this.g);
                            break;
                        }
                    } else {
                        q.a(b, "mirror paster");
                        if (!this.C) {
                            z2 = true;
                        }
                        this.C = z2;
                        this.k = a(this.k);
                        this.p.a();
                        break;
                    }
                } else {
                    q.a(b, "delete paster");
                    break;
                }
            case 1:
            case 6:
                if (a(this.A.x, this.A.y, motionEvent.getX(), motionEvent.getY()) < ((float) a(this.j, 4.0f)) && a(this.A.x, this.A.y) == 1) {
                    if (this.E) {
                        if (this.x != null) {
                            this.x.a(this);
                        }
                    } else if (this.F) {
                        if (this.y != null) {
                            this.y.a(this);
                        }
                    } else if (this.G && this.y != null) {
                        this.y.b(this);
                    }
                }
                float d2 = d();
                this.h.set(this.g);
                if (d2 >= -5.0f && d2 <= 5.0f) {
                    this.h.postRotate(-d2, this.z.x, this.z.y);
                } else if (d2 >= 85.0f && d2 <= 95.0f) {
                    this.h.postRotate(90.0f - d2, this.z.x, this.z.y);
                } else if (d2 >= 175.0f && d2 < 180.0f) {
                    this.h.postRotate(180.0f - d2, this.z.x, this.z.y);
                } else if (d2 >= -95.0f && d2 <= -85.0f) {
                    this.h.postRotate(-90.0f - d2, this.z.x, this.z.y);
                } else if (d2 > -180.0f && d2 <= -175.0f) {
                    this.h.postRotate(-180.0f - d2, this.z.x, this.z.y);
                }
                if (a(this.h, this.c)) {
                    this.g.set(this.h);
                    this.p.a();
                    i();
                }
                this.A.set(0.0f, 0.0f);
                this.c = 0;
                break;
            case 2:
                if (!this.B) {
                    if (this.x != null) {
                        this.x.a();
                    }
                    this.B = true;
                }
                if (this.c != 2) {
                    if (this.c != 1) {
                        if (this.c == 3) {
                            this.h.set(this.i);
                            float a = a(this.e.e.x, this.e.e.y, motionEvent.getX(), motionEvent.getY()) / this.n;
                            this.h.postScale(a, a, this.z.x, this.z.y);
                            this.h.postRotate(c(motionEvent) - this.o, this.z.x, this.z.y);
                            if (a(this.h, this.c)) {
                                this.g.set(this.h);
                                this.p.a();
                                i();
                                break;
                            }
                        }
                    } else {
                        this.h.set(this.i);
                        this.h.postTranslate(motionEvent.getX() - this.l, motionEvent.getY() - this.m);
                        if (a(this.h, this.c)) {
                            this.g.set(this.h);
                            this.p.a();
                            i();
                            break;
                        }
                    }
                } else {
                    this.h.set(this.i);
                    float a2 = a(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1)) / this.n;
                    this.h.postScale(a2, a2, this.z.x, this.z.y);
                    this.h.postRotate(b(motionEvent) - this.o, this.z.x, this.z.y);
                    if (a(this.h, this.c)) {
                        this.g.set(this.h);
                        this.p.a();
                        i();
                        break;
                    }
                }
                break;
            case 5:
                this.c = 2;
                this.n = a(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1));
                this.o = b(motionEvent);
                this.i.set(this.g);
                this.z.set(this.e.e.x, this.e.e.y);
                break;
        }
        return true;
    }

    public void a(boolean z2) {
        this.q = z2;
    }

    public String c() {
        return null;
    }

    private float a(float f2, float f3, float f4, float f5) {
        float f6 = f2 - f4;
        float f7 = f3 - f5;
        return (float) Math.sqrt((double) ((f6 * f6) + (f7 * f7)));
    }

    private float b(MotionEvent motionEvent) {
        return (float) Math.toDegrees(Math.atan2((double) (motionEvent.getY(0) - motionEvent.getY(1)), (double) (motionEvent.getX(0) - motionEvent.getX(1))));
    }

    private float c(MotionEvent motionEvent) {
        return (float) Math.toDegrees(Math.atan2((double) (motionEvent.getY() - this.e.e.y), (double) (motionEvent.getX() - this.e.e.x)));
    }

    private int a(Context context, float f2) {
        return (int) ((context.getResources().getDisplayMetrics().density * f2) + 0.5f);
    }

    private void i() {
        float[] fArr = new float[9];
        this.g.getValues(fArr);
        float f2 = (fArr[0] * 0.0f) + (fArr[1] * 0.0f) + fArr[2];
        float f3 = (fArr[3] * 0.0f) + (fArr[4] * 0.0f) + fArr[5];
        float width = (fArr[0] * ((float) this.k.getWidth())) + (fArr[1] * 0.0f) + fArr[2];
        float width2 = (fArr[3] * ((float) this.k.getWidth())) + (fArr[4] * 0.0f) + fArr[5];
        float width3 = (fArr[0] * ((float) this.k.getWidth())) + (fArr[1] * ((float) this.k.getHeight())) + fArr[2];
        float width4 = (fArr[3] * ((float) this.k.getWidth())) + (fArr[4] * ((float) this.k.getHeight())) + fArr[5];
        float height = (fArr[0] * 0.0f) + (fArr[1] * ((float) this.k.getHeight())) + fArr[2];
        this.e.a.x = f2;
        this.e.a.y = f3;
        this.e.b.x = width;
        this.e.b.y = width2;
        this.e.c.x = width3;
        this.e.c.y = width4;
        this.e.d.x = height;
        this.e.d.y = fArr[5] + (fArr[3] * 0.0f) + (fArr[4] * ((float) this.k.getHeight()));
        RectF rectF = new RectF(0.0f, 0.0f, (float) this.k.getWidth(), (float) this.k.getHeight());
        RectF rectF2 = new RectF();
        this.g.mapRect(rectF2, rectF);
        this.e.e.x = rectF2.centerX();
        this.e.e.y = rectF2.centerY();
    }

    private boolean a(Matrix matrix, int i2) {
        matrix.mapRect(new RectF(), new RectF(0.0f, 0.0f, (float) this.k.getWidth(), (float) this.k.getHeight()));
        return true;
    }

    public void a(a aVar) {
        this.p = aVar;
    }

    public float d() {
        return a(this.g);
    }

    public static float a(Matrix matrix) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        return -((float) Math.round(Math.atan2((double) fArr[1], (double) fArr[0]) * 57.29577951308232d));
    }

    public Matrix e() {
        return this.g;
    }

    public boolean f() {
        return this.C;
    }

    public Bitmap g() {
        return this.k;
    }

    public void b(String str) {
        this.D = str;
    }

    public String h() {
        return this.D;
    }
}
