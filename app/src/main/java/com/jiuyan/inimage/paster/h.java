package com.jiuyan.inimage.paster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.zoloz.R;
import com.jiuyan.inimage.util.DisplayUtil;
import com.jiuyan.inimage.util.q;

/* compiled from: TextObject */
public class h implements d {
    private boolean A;
    private String B;
    private Paint C;
    private int D;
    private int E;
    private int F;
    private Rect G;
    private Rect H;
    private Rect I;
    private Point J;
    private int K;
    private int b;
    private PaintFlagsDrawFilter c;
    private e d;
    private String e;
    private Matrix f;
    private Matrix g;
    private Matrix h;
    private Context i;
    private float j;
    private float k;
    private float l;
    private float m;
    private a n;
    private boolean o;
    private Bitmap p;
    private int q;
    private int r;
    private Paint s;
    private f t;
    private PointF u;
    private PointF v;
    private boolean w;
    private boolean x;
    private String y;
    private boolean z;

    public h(Context context) {
        this.b = 0;
        this.c = new PaintFlagsDrawFilter(0, 3);
        this.d = new e();
        this.f = new Matrix();
        this.g = new Matrix();
        this.h = new Matrix();
        this.j = 0.0f;
        this.k = 0.0f;
        this.l = 1.0f;
        this.m = 0.0f;
        this.o = true;
        this.u = new PointF();
        this.v = new PointF();
        this.w = false;
        this.x = true;
        this.z = true;
        this.A = false;
        this.J = new Point();
        this.f.reset();
        this.g.reset();
        this.h.reset();
        this.i = context;
        this.F = -1;
        this.D = DisplayUtil.dip2px(this.i, 28.0f);
        this.E = DisplayUtil.dip2px(this.i, 8.0f);
        this.C = new Paint();
        this.C.setColor(this.F);
        this.C.setTextSize((float) this.D);
        this.B = this.i.getString(R.string.watermark_default_text);
        this.G = b(this.C, this.B);
        this.H = a(this.C, this.B);
        this.I = new Rect(0, 0, this.H.width() + (this.E * 2), this.H.height() + (this.E * 2));
        this.d.a = new PointF(0.0f, 0.0f);
        this.d.b = new PointF((float) this.I.width(), 0.0f);
        this.d.c = new PointF((float) this.I.width(), (float) this.I.height());
        this.d.d = new PointF(0.0f, (float) this.I.height());
        RectF rectF = new RectF(0.0f, 0.0f, (float) this.I.width(), (float) this.I.height());
        this.d.e = new PointF(rectF.centerX(), rectF.centerY());
        this.p = BitmapFactory.decodeResource(this.i.getResources(), R.drawable.bussiness_edit_rotate);
        this.q = this.p.getWidth();
        this.r = this.p.getHeight();
        this.s = new Paint();
        this.s.setColor(-1);
        this.s.setStrokeWidth((float) DisplayUtil.dip2px(this.i, 1.0f));
        this.K = DisplayUtil.getScreenSize(this.i)[0];
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public h(Context context, i iVar) {
        this(context);
        a(iVar);
    }

    public Rect a() {
        return this.I;
    }

    public void a(Matrix matrix) {
        this.g.set(matrix);
        this.f.set(this.g);
        l();
    }

    public void a(String str) {
        this.B = str;
        this.G = b(this.C, this.B);
        this.H = a(this.C, this.B);
        this.I = new Rect(0, 0, this.H.width() + (this.E * 2), this.H.height() + (this.E * 2));
        l();
        if (this.n != null) {
            this.n.a();
        }
    }

    public String d() {
        return this.B;
    }

    public void a(int i2) {
        this.F = i2;
        this.C.setColor(this.F);
        if (this.n != null) {
            this.n.a();
        }
    }

    public int e() {
        return this.F;
    }

    public void b(int i2) {
        this.D = i2;
        this.C.setTextSize((float) this.D);
        this.G = b(this.C, this.B);
        this.H = a(this.C, this.B);
        this.I = new Rect(0, 0, this.H.width() + (this.E * 2), this.H.height() + (this.E * 2));
        l();
        if (this.n != null) {
            this.n.a();
        }
    }

    public void a(Canvas canvas) {
        if (!TextUtils.isEmpty(this.B)) {
            canvas.save();
            canvas.setDrawFilter(this.c);
            canvas.concat(this.f);
            canvas.drawText(this.B, (float) ((-this.G.left) + this.E), (float) ((-this.G.top) + this.E), this.C);
            canvas.restore();
            if (this.o) {
                canvas.drawLine(this.d.a.x, this.d.a.y, this.d.b.x, this.d.b.y, this.s);
                canvas.drawLine(this.d.b.x, this.d.b.y, this.d.c.x, this.d.c.y, this.s);
                canvas.drawLine(this.d.c.x, this.d.c.y, this.d.d.x, this.d.d.y, this.s);
                canvas.drawLine(this.d.d.x, this.d.d.y, this.d.a.x, this.d.a.y, this.s);
                canvas.drawBitmap(this.p, this.d.c.x - ((float) (this.q / 2)), this.d.c.y - ((float) (this.r / 2)), null);
            }
        }
    }

    public e f() {
        return this.d;
    }

    public boolean a(PointF pointF) {
        if (!this.z) {
            return false;
        }
        if (this.A) {
            return true;
        }
        return b(pointF);
    }

    public void b(String str) {
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
        if (fArr[0] <= 0.0f || fArr[0] >= ((float) this.I.width()) || fArr[1] <= 0.0f || fArr[1] >= ((float) this.I.height())) {
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

    public int a(float f2, float f3) {
        if (a(f2, f3, this.d.a.x, this.d.a.y) < ((float) (this.q / 2))) {
            return 1;
        }
        if (a(f2, f3, this.d.b.x, this.d.b.y) < ((float) (this.q / 2))) {
            return 2;
        }
        if (a(f2, f3, this.d.c.x, this.d.c.y) < ((float) (this.q / 2))) {
            return 3;
        }
        return 0;
    }

    public boolean a(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.v.set(motionEvent.getX(), motionEvent.getY());
                this.j = motionEvent.getX();
                this.k = motionEvent.getY();
                this.h.set(this.f);
                if (a(motionEvent.getX(), motionEvent.getY()) != 1) {
                    if (a(motionEvent.getX(), motionEvent.getY()) != 2) {
                        if (a(motionEvent.getX(), motionEvent.getY()) != 3) {
                            this.b = 1;
                            break;
                        } else {
                            this.b = 3;
                            this.l = a(this.d.e.x, this.d.e.y, motionEvent.getX(), motionEvent.getY());
                            this.m = c(motionEvent);
                            this.u.set(this.d.e.x, this.d.e.y);
                            this.h.set(this.f);
                            break;
                        }
                    } else {
                        q.a("PasterObject", "mirror paster");
                        break;
                    }
                } else {
                    q.a("PasterObject", "delete paster");
                    break;
                }
            case 1:
            case 6:
                if (a(this.v.x, this.v.y, motionEvent.getX(), motionEvent.getY()) < ((float) a(this.i, 4.0f)) && this.x && a(this.v.x, this.v.y) == 1 && this.t != null) {
                    this.t.a(this);
                }
                float g2 = g();
                this.g.set(this.f);
                if (!(this.u.x == 0.0f || this.u.y == 0.0f)) {
                    if (g2 >= -5.0f && g2 <= 5.0f) {
                        this.g.postRotate(-g2, this.u.x, this.u.y);
                    } else if (g2 >= 85.0f && g2 <= 95.0f) {
                        this.g.postRotate(90.0f - g2, this.u.x, this.u.y);
                    } else if (g2 >= 175.0f && g2 < 180.0f) {
                        this.g.postRotate(180.0f - g2, this.u.x, this.u.y);
                    } else if (g2 >= -95.0f && g2 <= -85.0f) {
                        this.g.postRotate(-90.0f - g2, this.u.x, this.u.y);
                    } else if (g2 > -180.0f && g2 <= -175.0f) {
                        this.g.postRotate(-180.0f - g2, this.u.x, this.u.y);
                    }
                    if (a(this.g, this.b)) {
                        this.f.set(this.g);
                        if (this.n != null) {
                            this.n.a();
                        }
                        l();
                    }
                }
                this.v.set(0.0f, 0.0f);
                this.b = 0;
                break;
            case 2:
                if (!this.w) {
                    if (this.t != null) {
                        this.t.a();
                    }
                    this.w = true;
                }
                if (this.b != 2) {
                    if (this.b != 1) {
                        if (this.b == 3) {
                            this.g.set(this.h);
                            float a = a(this.d.e.x, this.d.e.y, motionEvent.getX(), motionEvent.getY()) / this.l;
                            this.g.postScale(a, a, this.u.x, this.u.y);
                            this.g.postRotate(c(motionEvent) - this.m, this.u.x, this.u.y);
                            if (a(this.g, this.b)) {
                                this.f.set(this.g);
                                if (this.n != null) {
                                    this.n.a();
                                }
                                l();
                                break;
                            }
                        }
                    } else {
                        this.g.set(this.h);
                        this.g.postTranslate(motionEvent.getX() - this.j, motionEvent.getY() - this.k);
                        if (a(this.g, this.b)) {
                            this.f.set(this.g);
                            if (this.n != null) {
                                this.n.a();
                            }
                            l();
                            break;
                        }
                    }
                } else {
                    this.g.set(this.h);
                    float a2 = a(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1)) / this.l;
                    this.g.postScale(a2, a2, this.u.x, this.u.y);
                    this.g.postRotate(b(motionEvent) - this.m, this.u.x, this.u.y);
                    if (a(this.g, this.b)) {
                        this.f.set(this.g);
                        if (this.n != null) {
                            this.n.a();
                        }
                        l();
                        break;
                    }
                }
                break;
            case 5:
                this.b = 2;
                this.l = a(motionEvent.getX(0), motionEvent.getY(0), motionEvent.getX(1), motionEvent.getY(1));
                this.m = b(motionEvent);
                this.h.set(this.f);
                this.u.set(this.d.e.x, this.d.e.y);
                break;
        }
        return true;
    }

    public void a(boolean z2) {
        if (!this.z || !this.A) {
            this.o = z2;
        } else {
            this.o = true;
        }
    }

    public String c() {
        return null;
    }

    public void b(boolean z2) {
        this.z = z2;
        if (!z2) {
            this.o = false;
        }
    }

    public void c(boolean z2) {
        this.A = z2;
        if (z2) {
            this.o = true;
        }
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

    private void l() {
        float[] fArr = new float[9];
        this.f.getValues(fArr);
        float f2 = (fArr[0] * 0.0f) + (fArr[1] * 0.0f) + fArr[2];
        float f3 = (fArr[3] * 0.0f) + (fArr[4] * 0.0f) + fArr[5];
        float width = (fArr[0] * ((float) this.I.width())) + (fArr[1] * 0.0f) + fArr[2];
        float width2 = (fArr[3] * ((float) this.I.width())) + (fArr[4] * 0.0f) + fArr[5];
        float width3 = (fArr[0] * ((float) this.I.width())) + (fArr[1] * ((float) this.I.height())) + fArr[2];
        float width4 = (fArr[3] * ((float) this.I.width())) + (fArr[4] * ((float) this.I.height())) + fArr[5];
        float height = (fArr[0] * 0.0f) + (fArr[1] * ((float) this.I.height())) + fArr[2];
        this.d.a.x = f2;
        this.d.a.y = f3;
        this.d.b.x = width;
        this.d.b.y = width2;
        this.d.c.x = width3;
        this.d.c.y = width4;
        this.d.d.x = height;
        this.d.d.y = fArr[5] + (fArr[3] * 0.0f) + (fArr[4] * ((float) this.I.height()));
        RectF rectF = new RectF(0.0f, 0.0f, (float) this.I.width(), (float) this.I.height());
        RectF rectF2 = new RectF();
        this.f.mapRect(rectF2, rectF);
        this.d.e.x = rectF2.centerX();
        this.d.e.y = rectF2.centerY();
    }

    private boolean a(Matrix matrix, int i2) {
        matrix.mapRect(new RectF(), new RectF(0.0f, 0.0f, (float) this.I.width(), (float) this.I.height()));
        if ((i2 == 2 || i2 == 3) && c(matrix) > 5.0f) {
            return false;
        }
        return true;
    }

    private float c(Matrix matrix) {
        return (float) Math.sqrt((double) (((float) Math.pow((double) b(matrix, 0), 2.0d)) + ((float) Math.pow((double) b(matrix, 3), 2.0d))));
    }

    private float b(Matrix matrix, int i2) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        return fArr[i2];
    }

    public void a(a aVar) {
        this.n = aVar;
    }

    public float g() {
        return b(this.f);
    }

    public static float b(Matrix matrix) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        return -((float) Math.round(Math.atan2((double) fArr[1], (double) fArr[0]) * 57.29577951308232d));
    }

    public Matrix h() {
        return this.f;
    }

    public void c(String str) {
        this.y = str;
    }

    public String i() {
        return this.y;
    }

    private Rect a(Paint paint, String str) {
        if (TextUtils.isEmpty(str)) {
            return new Rect();
        }
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return new Rect(0, 0, rect.width(), rect.height());
    }

    private Rect b(Paint paint, String str) {
        if (TextUtils.isEmpty(str)) {
            return new Rect();
        }
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect;
    }

    public i j() {
        i iVar = new i(this);
        iVar.a = this.B;
        iVar.b = this.F;
        iVar.c = this.D;
        iVar.d = this.o;
        float[] fArr = new float[9];
        this.f.getValues(fArr);
        iVar.e = fArr;
        iVar.f = this.e;
        iVar.g = this.y;
        iVar.h = this.z;
        iVar.i = this.A;
        return iVar;
    }

    public void a(i iVar) {
        a(iVar.a);
        a(iVar.b);
        b(iVar.c);
        a(iVar.d);
        b(iVar.h);
        c(iVar.i);
        b(iVar.f);
        c(iVar.g);
        if (iVar.e != null) {
            Matrix matrix = new Matrix();
            matrix.setValues(iVar.e);
            a(matrix);
        }
    }

    public h k() {
        return new h(this.i, j());
    }
}
