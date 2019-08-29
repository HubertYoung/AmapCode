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

/* compiled from: PasterObject */
public class g implements d {
    private boolean A = false;
    private boolean B = true;
    private String C;
    private String D;
    private int b = 0;
    private PaintFlagsDrawFilter c = new PaintFlagsDrawFilter(0, 3);
    private e d = new e();
    private String e;
    private Matrix f = new Matrix();
    private Matrix g = new Matrix();
    private Matrix h = new Matrix();
    private Context i;
    private Bitmap j;
    private float k = 0.0f;
    private float l = 0.0f;
    private float m = 1.0f;
    private float n = 0.0f;
    private a o;
    private boolean p = false;
    private Bitmap q;
    private Bitmap r;
    private Bitmap s;
    private int t;
    private int u;
    private Paint v;
    private f w;
    private PointF x = new PointF();
    private PointF y = new PointF();
    private boolean z = false;

    public g(Context context, Bitmap bitmap) {
        this.f.reset();
        this.g.reset();
        this.h.reset();
        this.i = context;
        this.j = bitmap;
        this.d.a = new PointF(0.0f, 0.0f);
        this.d.b = new PointF((float) this.j.getWidth(), 0.0f);
        this.d.c = new PointF((float) this.j.getWidth(), (float) this.j.getHeight());
        this.d.d = new PointF(0.0f, (float) this.j.getHeight());
        RectF rectF = new RectF(0.0f, 0.0f, (float) this.j.getWidth(), (float) this.j.getHeight());
        this.d.e = new PointF(rectF.centerX(), rectF.centerY());
        this.q = BitmapFactory.decodeResource(this.i.getResources(), R.drawable.in_alipay_bussiness_edit_del);
        this.r = BitmapFactory.decodeResource(this.i.getResources(), R.drawable.in_alipay_bussiness_edit_symmetry);
        this.s = BitmapFactory.decodeResource(this.i.getResources(), R.drawable.in_alipay_bussiness_edit_control);
        this.t = this.q.getWidth();
        this.u = this.q.getHeight();
        this.v = new Paint();
        this.v.setColor(-1);
        this.v.setStrokeWidth(2.0f);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void a(f fVar) {
        this.w = fVar;
    }

    public void a(Matrix matrix, boolean z2) {
        if (z2) {
            Bitmap a = a(this.j);
            if (a != null) {
                this.j = a;
            }
        }
        this.A = z2;
        this.g.set(matrix);
        this.f.set(this.g);
        i();
    }

    public void a(Canvas canvas) {
        canvas.save();
        canvas.setDrawFilter(this.c);
        canvas.drawBitmap(this.j, this.f, null);
        if (this.p) {
            canvas.drawLine(this.d.a.x, this.d.a.y, this.d.b.x, this.d.b.y, this.v);
            canvas.drawLine(this.d.b.x, this.d.b.y, this.d.c.x, this.d.c.y, this.v);
            canvas.drawLine(this.d.c.x, this.d.c.y, this.d.d.x, this.d.d.y, this.v);
            canvas.drawLine(this.d.d.x, this.d.d.y, this.d.a.x, this.d.a.y, this.v);
            if (this.B) {
                canvas.drawBitmap(this.q, this.d.a.x - ((float) (this.t / 2)), this.d.a.y - ((float) (this.u / 2)), null);
            }
            canvas.drawBitmap(this.r, this.d.b.x - ((float) (this.t / 2)), this.d.b.y - ((float) (this.u / 2)), null);
            canvas.drawBitmap(this.s, this.d.c.x - ((float) (this.t / 2)), this.d.c.y - ((float) (this.u / 2)), null);
        }
        canvas.restore();
    }

    public e a() {
        return this.d;
    }

    public boolean a(PointF pointF) {
        return b(pointF);
    }

    public void a(String str) {
        this.e = str;
    }

    public String b() {
        return this.e;
    }

    public boolean b(PointF pointF) {
        boolean z2;
        boolean z3;
        q.a("PasterObject", "isContainPoint: " + pointF.toString());
        float[] fArr = new float[2];
        Matrix matrix = new Matrix();
        matrix.set(this.f);
        if (!matrix.invert(matrix)) {
            q.a("PasterObject", "can not revert matrix");
            return false;
        }
        matrix.mapPoints(fArr, new float[]{pointF.x, pointF.y});
        q.a("PasterObject", "after map: " + fArr.toString());
        if (fArr[0] <= 0.0f || fArr[0] >= ((float) this.j.getWidth()) || fArr[1] <= 0.0f || fArr[1] >= ((float) this.j.getHeight())) {
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
            q.a("PasterObject", "createYMirrorBitmap occur exception: " + e2.getMessage());
            bitmap2 = null;
        }
        System.gc();
        return bitmap2;
    }

    public int a(float f2, float f3) {
        if (a(f2, f3, this.d.a.x, this.d.a.y) < ((float) (this.t / 2))) {
            return 1;
        }
        if (a(f2, f3, this.d.b.x, this.d.b.y) < ((float) (this.t / 2))) {
            return 2;
        }
        if (a(f2, f3, this.d.c.x, this.d.c.y) < ((float) (this.t / 2))) {
            return 3;
        }
        return 0;
    }

    public boolean a(MotionEvent motionEvent) {
        boolean z2 = false;
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.y.set(motionEvent.getX(), motionEvent.getY());
                this.k = motionEvent.getX();
                this.l = motionEvent.getY();
                this.h.set(this.f);
                if (a(motionEvent.getX(), motionEvent.getY()) != 1) {
                    if (a(motionEvent.getX(), motionEvent.getY()) != 2) {
                        if (a(motionEvent.getX(), motionEvent.getY()) != 3) {
                            this.b = 1;
                            break;
                        } else {
                            this.b = 3;
                            this.m = a(this.d.e.x, this.d.e.y, motionEvent.getX(), motionEvent.getY());
                            this.n = c(motionEvent);
                            this.x.set(this.d.e.x, this.d.e.y);
                            this.h.set(this.f);
                            break;
                        }
                    } else {
                        q.a("PasterObject", "mirror paster");
                        if (!this.A) {
                            z2 = true;
                        }
                        this.A = z2;
                        this.j = a(this.j);
                        this.o.a();
                        break;
                    }
                } else {
                    q.a("PasterObject", "delete paster");
                    break;
                }
            case 1:
            case 6:
                if (a(this.y.x, this.y.y, motionEvent.getX(), motionEvent.getY()) < ((float) a(this.i, 4.0f)) && this.B && a(this.y.x, this.y.y) == 1 && this.w != null) {
                    this.w.a(this);
                }
                float d2 = d();
                this.g.set(this.f);
                if (!(this.x.x == 0.0f || this.x.y == 0.0f)) {
                    if (d2 >= -5.0f && d2 <= 5.0f) {
                        this.g.postRotate(-d2, this.x.x, this.x.y);
                    } else if (d2 >= 85.0f && d2 <= 95.0f) {
                        this.g.postRotate(90.0f - d2, this.x.x, this.x.y);
                    } else if (d2 >= 175.0f && d2 < 180.0f) {
                        this.g.postRotate(180.0f - d2, this.x.x, this.x.y);
                    } else if (d2 >= -95.0f && d2 <= -85.0f) {
                        this.g.postRotate(-90.0f - d2, this.x.x, this.x.y);
                    } else if (d2 > -180.0f && d2 <= -175.0f) {
                        this.g.postRotate(-180.0f - d2, this.x.x, this.x.y);
                    }
                    if (a(this.g, this.b)) {
                        this.f.set(this.g);
                        this.o.a();
                        i();
                    }
                }
                this.y.set(0.0f, 0.0f);
                this.b = 0;
                break;
            case 2:
                if (!this.z) {
                    if (this.w != null) {
                        this.w.a();
                    }
                    this.z = true;
                }
                if (this.b != 2) {
                    if (this.b != 1) {
                        if (this.b == 3) {
                            this.g.set(this.h);
                            float a = a(this.d.e.x, this.d.e.y, motionEvent.getX(), motionEvent.getY()) / this.m;
                            this.g.postScale(a, a, this.x.x, this.x.y);
                            this.g.postRotate(c(motionEvent) - this.n, this.x.x, this.x.y);
                            if (a(this.g, this.b)) {
                                this.f.set(this.g);
                                this.o.a();
                                i();
                                break;
                            }
                        }
                    } else {
                        this.g.set(this.h);
                        this.g.postTranslate(motionEvent.getX() - this.k, motionEvent.getY() - this.l);
                        if (a(this.g, this.b)) {
                            this.f.set(this.g);
                            this.o.a();
                            i();
                            break;
                        }
                    }
                } else {
                    this.g.set(this.h);
                    float a2 = a(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1)) / this.m;
                    this.g.postScale(a2, a2, this.x.x, this.x.y);
                    this.g.postRotate(b(motionEvent) - this.n, this.x.x, this.x.y);
                    if (a(this.g, this.b)) {
                        this.f.set(this.g);
                        this.o.a();
                        i();
                        break;
                    }
                }
                break;
            case 5:
                this.b = 2;
                this.m = a(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1));
                this.n = b(motionEvent);
                this.h.set(this.f);
                this.x.set(this.d.e.x, this.d.e.y);
                break;
        }
        return true;
    }

    public void a(boolean z2) {
        this.p = z2;
    }

    public void b(String str) {
        this.D = str;
    }

    public String c() {
        return this.D;
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
        return (float) Math.toDegrees(Math.atan2((double) (motionEvent.getY() - this.d.e.y), (double) (motionEvent.getX() - this.d.e.x)));
    }

    private int a(Context context, float f2) {
        return (int) ((context.getResources().getDisplayMetrics().density * f2) + 0.5f);
    }

    private void i() {
        float[] fArr = new float[9];
        this.f.getValues(fArr);
        float f2 = (fArr[0] * 0.0f) + (fArr[1] * 0.0f) + fArr[2];
        float f3 = (fArr[3] * 0.0f) + (fArr[4] * 0.0f) + fArr[5];
        float width = (fArr[0] * ((float) this.j.getWidth())) + (fArr[1] * 0.0f) + fArr[2];
        float width2 = (fArr[3] * ((float) this.j.getWidth())) + (fArr[4] * 0.0f) + fArr[5];
        float width3 = (fArr[0] * ((float) this.j.getWidth())) + (fArr[1] * ((float) this.j.getHeight())) + fArr[2];
        float width4 = (fArr[3] * ((float) this.j.getWidth())) + (fArr[4] * ((float) this.j.getHeight())) + fArr[5];
        float height = (fArr[0] * 0.0f) + (fArr[1] * ((float) this.j.getHeight())) + fArr[2];
        this.d.a.x = f2;
        this.d.a.y = f3;
        this.d.b.x = width;
        this.d.b.y = width2;
        this.d.c.x = width3;
        this.d.c.y = width4;
        this.d.d.x = height;
        this.d.d.y = fArr[5] + (fArr[3] * 0.0f) + (fArr[4] * ((float) this.j.getHeight()));
        RectF rectF = new RectF(0.0f, 0.0f, (float) this.j.getWidth(), (float) this.j.getHeight());
        RectF rectF2 = new RectF();
        this.f.mapRect(rectF2, rectF);
        this.d.e.x = rectF2.centerX();
        this.d.e.y = rectF2.centerY();
    }

    private boolean a(Matrix matrix, int i2) {
        matrix.mapRect(new RectF(), new RectF(0.0f, 0.0f, (float) this.j.getWidth(), (float) this.j.getHeight()));
        return true;
    }

    public void a(a aVar) {
        this.o = aVar;
    }

    public static float a(Matrix matrix) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        float f2 = fArr[0];
        float f3 = fArr[3];
        return (float) Math.sqrt((double) ((f3 * f3) + (f2 * f2)));
    }

    public float d() {
        return b(this.f);
    }

    public static float b(Matrix matrix) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        return -((float) Math.round(Math.atan2((double) fArr[1], (double) fArr[0]) * 57.29577951308232d));
    }

    public Matrix e() {
        return this.f;
    }

    public boolean f() {
        return this.A;
    }

    public Bitmap g() {
        return this.j;
    }

    public void c(String str) {
        this.C = str;
    }

    public String h() {
        return this.C;
    }
}
