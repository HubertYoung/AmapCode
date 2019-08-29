package com.jiuyan.inimage.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.view.View.OnLongClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Scroller;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.jiuyan.inimage.util.d;
import com.jiuyan.inimage.util.q;
import com.jiuyan.inimage.util.s;
import com.jiuyan.inimage.util.x;

public class PhotoCropViewFreedom extends ImageView implements OnDoubleTapListener, OnGlobalLayoutListener, x {
    private r A;
    private t B;
    private u C;
    /* access modifiers changed from: private */
    public OnLongClickListener D;
    private s E;
    private int F;
    private int G;
    private int H;
    private int I;
    private p J;
    private boolean K;
    private ScaleType L;
    private Scroller M;
    private int N;
    private int O;
    private q P;
    private int Q;
    private int R;
    private float S;
    private float T;
    private boolean U;
    private PointF V;
    private int W;
    private boolean aa;
    private int ab;
    private int ac;
    RectF b;
    RectF c;
    RectF d;
    /* access modifiers changed from: private */
    public final String e;
    /* access modifiers changed from: private */
    public boolean f;
    private final int g;
    private Paint h;
    private int i;
    private int j;
    private int k;
    private Paint l;
    private int m;
    private Paint n;
    private float o;
    private float p;
    private float q;
    private float r;
    private ViewTreeObserver s;
    private GestureDetector t;
    private s u;
    private final Matrix v;
    private final Matrix w;
    /* access modifiers changed from: private */
    public final Matrix x;
    private final RectF y;
    private final float[] z;

    public PhotoCropViewFreedom(Context context) {
        this(context, null);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public PhotoCropViewFreedom(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PhotoCropViewFreedom(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.e = PhotoCropViewFreedom.class.getSimpleName();
        this.f = true;
        this.g = 2;
        this.m = 872349696;
        this.o = 1.0f;
        this.p = 1.75f;
        this.q = 3.0f;
        this.r = 0.5f;
        this.v = new Matrix();
        this.w = new Matrix();
        this.x = new Matrix();
        this.y = new RectF();
        this.z = new float[9];
        this.L = ScaleType.FIT_CENTER;
        this.b = new RectF();
        this.c = new RectF();
        this.d = new RectF();
        this.U = false;
        this.V = new PointF();
        this.W = 0;
        this.aa = true;
        super.setScaleType(ScaleType.MATRIX);
        this.i = 640;
        this.j = 640;
        this.k = 2;
        this.h = new Paint();
        this.h.setAntiAlias(true);
        this.h.setStyle(Style.STROKE);
        this.h.setStrokeWidth((float) this.k);
        this.h.setColor(this.m);
        this.l = new Paint();
        this.l.setAntiAlias(true);
        this.l.setStyle(Style.STROKE);
        this.l.setStrokeWidth(20.0f);
        this.l.setColor(Color.parseColor("#00000000"));
        this.n = new Paint();
        this.n.setAntiAlias(true);
        this.n.setStyle(Style.FILL);
        this.n.setColor(-1);
        c();
    }

    /* access modifiers changed from: 0000 */
    public void setFrameBackgroundColor(int i2) {
        this.n.setColor(i2);
    }

    public void a(int i2, int i3, int i4, String str) {
        q.a(this.e, "setFrameSize: " + i2 + Token.SEPARATOR + i3 + "  strokeWidth: " + i4);
        this.i = i2;
        this.j = i3;
        this.k = i4;
        this.m = Color.parseColor(str);
        b();
        invalidate();
    }

    public Matrix getDisplayMatrix() {
        this.w.set(this.v);
        this.w.postConcat(this.x);
        return this.w;
    }

    public Matrix getSuppMatrix() {
        return this.x;
    }

    public Matrix getTmpMatrix() {
        Matrix matrix = new Matrix();
        matrix.setRectToRect(getDisplayRect(), new RectF(getFrameRect()), ScaleToFit.CENTER);
        return matrix;
    }

    public PointF getCenterPoint() {
        return new PointF((float) (getWidth() / 2), (float) (getHeight() / 2));
    }

    public int getFrameWidth() {
        return this.i == 0 ? getWidth() : this.i;
    }

    public int getFrameHeight() {
        return this.j == 0 ? getHeight() : this.j;
    }

    public Rect getFrameRect() {
        Rect rect = new Rect();
        rect.left = getLeftRightGap();
        rect.top = getTopBottomGap();
        rect.right = rect.left + this.i;
        rect.bottom = rect.top + this.j;
        return rect;
    }

    public RectF getCropperRectForDrawable() {
        RectF displayRect = getDisplayRect();
        RectF rectF = new RectF();
        rectF.left = ((float) getLeftRightGap()) - displayRect.left;
        rectF.top = ((float) getTopBottomGap()) - displayRect.top;
        rectF.right = rectF.left + ((float) this.i);
        rectF.bottom = rectF.top + ((float) this.j);
        return rectF;
    }

    public float getDisplayScale() {
        return a(getDisplayMatrix(), 0);
    }

    public void setOnDoActionListener(q qVar) {
        this.P = qVar;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i2;
        int i3;
        int i4;
        int i5 = 2;
        this.O = canvas.getMaximumBitmapWidth();
        int width = getWidth();
        int height = getHeight();
        if (width != 0 && height != 0) {
            int i6 = width - this.i;
            if (a(i6)) {
                i6--;
            }
            int i7 = height - this.j;
            if (a(i7)) {
                i7--;
            }
            int i8 = i6 / 2;
            int i9 = i7 / 2;
            this.h.setStrokeWidth((float) this.k);
            this.h.setColor(this.m);
            int i10 = i8 + ((a(this.k) ? this.k + 1 : this.k) / 2);
            int i11 = i9 + ((a(this.k) ? this.k + 1 : this.k) / 2);
            int i12 = (this.i + i8) - ((a(this.k) ? this.k + 1 : this.k) / 2);
            int i13 = (this.j + i9) - ((a(this.k) ? this.k + 1 : this.k) / 2);
            q.a(this.e, "mingtian : " + i10 + "  " + i11 + "  " + i12 + "  " + i13);
            this.b.set((float) i10, (float) i11, (float) i12, (float) i13);
            canvas.drawRect(this.b, this.n);
            super.onDraw(canvas);
            this.c.set((float) i10, (float) i11, (float) i12, (float) i13);
            canvas.drawRect(this.c, this.h);
            int i14 = i8 > i9 ? i8 : i9;
            Paint paint = this.l;
            if (!a(this.k)) {
                i5 = 1;
            }
            paint.setStrokeWidth((float) (i5 + i14));
            if (a(i14)) {
                i2 = i14 + 1;
            } else {
                i2 = i14;
            }
            int i15 = i8 - (i2 / 2);
            if (a(i14)) {
                i3 = i14 + 1;
            } else {
                i3 = i14;
            }
            int i16 = i9 - (i3 / 2);
            int i17 = this.i;
            if (a(i14)) {
                i4 = i14 + 1;
            } else {
                i4 = i14;
            }
            int i18 = i4 + i17 + i15;
            int i19 = this.j;
            if (a(i14)) {
                i14++;
            }
            this.d.set((float) i15, (float) i16, (float) i18, (float) (i14 + i19 + i16));
            canvas.drawRect(this.d, this.l);
        }
    }

    private boolean a(int i2) {
        return i2 % 2 != 0;
    }

    private void c() {
        this.s = getViewTreeObserver();
        this.s.addOnGlobalLayoutListener(this);
        if (!isInEditMode()) {
            this.u = s.a(getContext(), this);
            this.t = new GestureDetector(getContext(), new m(this));
            this.t.setOnDoubleTapListener(this);
            setZoomable(true);
        }
        this.M = new Scroller(getContext(), new DecelerateInterpolator());
        this.N = d.a(getContext(), 10.0f);
        q.a(this.e, "default touch slop: " + this.N);
    }

    public void a() {
        this.o = 1.0f;
        this.p = 1.75f;
        this.q = 3.0f;
        this.v.reset();
        this.w.reset();
        this.x.reset();
        this.y.left = 0.0f;
        this.y.top = 0.0f;
        this.y.right = 0.0f;
        this.y.bottom = 0.0f;
    }

    private void d() {
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
        this.s = null;
        this.A = null;
        this.B = null;
        this.C = null;
        h();
    }

    public void onGlobalLayout() {
        if (this.K) {
            int top = getTop();
            int right = getRight();
            int bottom = getBottom();
            int left = getLeft();
            q.a(this.e, "onGlobalLayout: " + left + Token.SEPARATOR + top + Token.SEPARATOR + right + Token.SEPARATOR + bottom);
            if ((top != this.F || bottom != this.H || left != this.I || right != this.G) && getDrawable() != null) {
                a(getDrawable());
                setFirstShowCenterCropMode(getDrawable());
                this.F = top;
                this.G = right;
                this.H = bottom;
                this.I = left;
            }
        }
    }

    public boolean onDoubleTap(MotionEvent motionEvent) {
        try {
            float scale = getScale();
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            if (scale < this.p) {
                b(this.p, x2, y2);
            } else if (scale < this.p || scale >= this.q) {
                b(this.o, x2, y2);
            } else {
                b(this.q, x2, y2);
            }
            if (this.E != null) {
                this.E.h();
            }
        } catch (ArrayIndexOutOfBoundsException e2) {
        }
        return true;
    }

    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        if (this.B != null) {
            RectF displayRect = getDisplayRect();
            if (displayRect != null) {
                float x2 = motionEvent.getX();
                float y2 = motionEvent.getY();
                if (displayRect.contains(x2, y2)) {
                    this.B.a(this, (x2 - displayRect.left) / displayRect.width(), (y2 - displayRect.top) / displayRect.height());
                    return true;
                }
            }
        }
        if (this.C != null) {
            this.C.a(this, motionEvent.getX(), motionEvent.getY());
        }
        return false;
    }

    public void a(float f2, float f3) {
        q.a(this.e, String.format("onDrag: dx: %.2f. dy: %.2f", new Object[]{Float.valueOf(f2), Float.valueOf(f3)}));
        if (this.E != null) {
            this.E.a(f2, f3);
        }
        if (this.W == 1 && getDrawable() != null) {
            if (this.P != null) {
                this.P.a();
            }
            this.x.postTranslate(f2, f3);
            setImageViewMatrix(getDisplayMatrix());
        }
    }

    public void a(float f2, float f3, float f4, float f5) {
    }

    private float[] a(Matrix matrix) {
        Matrix matrix2 = new Matrix();
        matrix2.set(this.v);
        matrix2.postConcat(matrix);
        RectF b2 = b(matrix2);
        RectF rectF = new RectF();
        rectF.left = ((float) getLeftRightGap()) - b2.left;
        rectF.top = ((float) getTopBottomGap()) - b2.top;
        rectF.right = rectF.left + ((float) this.i);
        rectF.bottom = rectF.top + ((float) this.j);
        float a = a(matrix2, 0);
        rectF.left /= a;
        rectF.top /= a;
        rectF.right /= a;
        rectF.bottom /= a;
        return new float[]{rectF.width(), rectF.height()};
    }

    public void a(float f2, float f3, float f4) {
        q.a(this.e, String.format("onScale: scale: %.2f. fX: %.2f. fY: %.2f", new Object[]{Float.valueOf(f2), Float.valueOf(f3), Float.valueOf(f4)}));
        if (this.E != null) {
            this.E.a(f2, f3, f4);
        }
        if (this.W == 2 && getDrawable() != null) {
            if (getScale() > this.q && f2 > 1.0f) {
                return;
            }
            if (getScale() >= this.r || f2 >= 1.0f) {
                Matrix matrix = new Matrix(this.x);
                matrix.postScale(f2, f2, this.V.x, this.V.y);
                float[] a = a(matrix);
                q.a(this.e, "pre cropper width: " + a[0] + "  height: " + a[1]);
                int i2 = this.O;
                q.a(this.e, "max texture from canvas: " + this.O);
                if (i2 <= 0) {
                    i2 = d.b();
                    q.a(this.e, "max texture form opengl: " + i2);
                }
                if (i2 <= 0) {
                    i2 = 4800;
                }
                if ((a[0] <= ((float) i2) && a[1] <= ((float) i2)) || f2 >= 1.0f) {
                    this.x.postScale(f2, f2, this.V.x, this.V.y);
                    setImageViewMatrix(getDisplayMatrix());
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void setTouchEnabled(boolean z2) {
        this.aa = z2;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z2 = false;
        if (!this.aa) {
            return super.onTouchEvent(motionEvent);
        }
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.W = 1;
                RectF b2 = b(getDisplayMatrix());
                this.V.set(b2.centerX(), b2.centerY());
                getParent().requestDisallowInterceptTouchEvent(true);
                this.U = false;
                this.Q = (int) motionEvent.getX();
                this.R = (int) motionEvent.getY();
                break;
            case 1:
            case 3:
                break;
            case 2:
                int abs = (int) Math.abs(((float) this.Q) - motionEvent.getX());
                int abs2 = (int) Math.abs(((float) this.R) - motionEvent.getY());
                float x2 = motionEvent.getX() - this.S;
                float y2 = motionEvent.getY() - this.T;
                this.S = motionEvent.getX();
                this.T = motionEvent.getY();
                if (Math.abs(abs) > Math.abs(abs2) && Math.abs(abs) > this.N) {
                    if (x2 > 0.0f) {
                        if (!g() || !b(true)) {
                            this.U = true;
                        }
                    } else if (!g() || !b(false)) {
                        this.U = true;
                    }
                }
                if (Math.abs(abs2) > Math.abs(abs) && Math.abs(abs2) > this.N) {
                    if (y2 <= 0.0f) {
                        if (!f() || !a(false)) {
                            this.U = true;
                            break;
                        }
                    } else if (!f() || !a(true)) {
                        this.U = true;
                        break;
                    }
                }
            case 5:
                q.a(this.e, "@@@@@@@@@@ MotionEvent.ACTION_POINTER_DOWN");
                RectF b3 = b(getDisplayMatrix());
                this.V.set(b3.centerX(), b3.centerY());
                this.W = 2;
                break;
            case 6:
                q.a(this.e, "################ MotionEvent.ACTION_POINTER_UP");
                break;
        }
        this.W = 0;
        e();
        if (this.t != null && this.t.onTouchEvent(motionEvent)) {
            z2 = true;
        }
        if (this.u == null || !this.u.a(motionEvent)) {
            return z2;
        }
        return true;
    }

    private Rect getDisplayRectInt() {
        Rect rect = new Rect();
        RectF b2 = b(getDisplayMatrix());
        rect.set((int) b2.left, (int) b2.top, (int) b2.right, (int) b2.bottom);
        return rect;
    }

    private void e() {
        Rect frameRect = getFrameRect();
        Rect displayRectInt = getDisplayRectInt();
        int a = d.a(getContext(), 8.0f);
        if (d.a(frameRect.left, displayRectInt.left, a)) {
            if (d.a(frameRect.right, displayRectInt.right, a)) {
                this.x.postTranslate((float) ((frameRect.left - ((displayRectInt.width() - frameRect.width()) / 2)) - displayRectInt.left), 0.0f);
                RectF b2 = b(getDisplayMatrix());
                float width = ((float) frameRect.width()) / ((float) displayRectInt.width());
                this.x.postScale(width, width, b2.centerX(), b2.centerY());
            } else {
                this.x.postTranslate((float) (frameRect.left - displayRectInt.left), 0.0f);
            }
        } else if (d.a(frameRect.right, displayRectInt.right, a)) {
            this.x.postTranslate((float) (frameRect.right - displayRectInt.right), 0.0f);
        }
        Rect displayRectInt2 = getDisplayRectInt();
        if (d.a(frameRect.top, displayRectInt2.top, a)) {
            if (d.a(frameRect.bottom, displayRectInt2.bottom, a)) {
                this.x.postTranslate(0.0f, (float) ((frameRect.top - ((displayRectInt2.height() - frameRect.height()) / 2)) - displayRectInt2.top));
                RectF b3 = b(getDisplayMatrix());
                float height = ((float) frameRect.height()) / ((float) displayRectInt2.height());
                this.x.postScale(height, height, b3.centerX(), b3.centerY());
            } else {
                this.x.postTranslate(0.0f, (float) (frameRect.top - displayRectInt2.top));
            }
        } else if (d.a(frameRect.bottom, displayRectInt2.bottom, a)) {
            this.x.postTranslate(0.0f, (float) (frameRect.bottom - displayRectInt2.bottom));
        }
        setImageViewMatrix(getDisplayMatrix());
    }

    public void computeScroll() {
        if (this.M.computeScrollOffset()) {
            int currX = this.M.getCurrX();
            int currY = this.M.getCurrY();
            this.x.postTranslate((float) (currX - this.ab), (float) (currY - this.ac));
            setImageViewMatrix(getDisplayMatrix());
            this.ab = currX;
            this.ac = currY;
            postInvalidate();
        }
    }

    private boolean f() {
        RectF b2 = b(getDisplayMatrix());
        if (b2 != null && b2.height() > ((float) this.j)) {
            return true;
        }
        return false;
    }

    private boolean g() {
        RectF b2 = b(getDisplayMatrix());
        if (b2 != null && b2.width() > ((float) this.i)) {
            return true;
        }
        return false;
    }

    private boolean a(boolean z2) {
        RectF b2 = b(getDisplayMatrix());
        if (b2 == null) {
            return false;
        }
        if (z2) {
            if (b2.top >= ((float) getTopBottomGap())) {
                return false;
            }
            return true;
        } else if (b2.bottom <= ((float) (this.j + getTopBottomGap()))) {
            return false;
        } else {
            return true;
        }
    }

    private boolean b(boolean z2) {
        RectF b2 = b(getDisplayMatrix());
        if (b2 == null) {
            return false;
        }
        if (z2) {
            if (b2.left >= ((float) getLeftRightGap())) {
                return false;
            }
            return true;
        } else if (b2.right <= ((float) (this.i + getLeftRightGap()))) {
            return false;
        } else {
            return true;
        }
    }

    public final void b() {
        if (getDrawable() != null) {
            if (this.K) {
                a(getDrawable());
            } else {
                j();
            }
        }
    }

    private void h() {
        if (this.J != null) {
            this.J.a();
            this.J = null;
        }
    }

    /* access modifiers changed from: private */
    public void i() {
        float f2;
        float f3 = 0.0f;
        RectF b2 = b(getDisplayMatrix());
        q.a(this.e, "checkMatrixBounds cur rect: " + b2.toString() + "  width: " + b2.width() + "  height: " + b2.height());
        if (b2 != null && getDrawable() != null) {
            float height = b2.height();
            float width = b2.width();
            int i2 = this.j;
            if (height <= ((float) i2)) {
                q.a(this.e, "+1111111111111+: height<=cropperHeight");
                switch (n.a[this.L.ordinal()]) {
                    case 1:
                        f2 = -b2.top;
                        break;
                    case 2:
                        f2 = (((float) i2) - height) - b2.top;
                        break;
                    default:
                        f2 = (((((float) i2) - height) / 2.0f) - b2.top) + ((float) getTopBottomGap());
                        break;
                }
            } else if (b2.top > ((float) getTopBottomGap())) {
                f2 = (-b2.top) + ((float) getTopBottomGap());
                q.a(this.e, "+2222222222222+: " + b2.top + "    " + getTopBottomGap());
            } else if (b2.bottom < ((float) (getTopBottomGap() + i2))) {
                f2 = ((float) (getTopBottomGap() + i2)) - b2.bottom;
                q.a(this.e, "+333333333333333+: " + i2 + "    " + getTopBottomGap() + "  " + b2.bottom);
            } else {
                f2 = 0.0f;
            }
            int i3 = this.i;
            if (width <= ((float) i3)) {
                switch (n.a[this.L.ordinal()]) {
                    case 1:
                        f3 = -b2.left;
                        break;
                    case 2:
                        f3 = (((float) i3) - width) - b2.left;
                        break;
                    default:
                        f3 = (((((float) i3) - width) / 2.0f) - b2.left) + ((float) getLeftRightGap());
                        break;
                }
            } else if (b2.left > ((float) getLeftRightGap())) {
                f3 = (-b2.left) + ((float) getLeftRightGap());
            } else if (b2.right < ((float) (getLeftRightGap() + i3))) {
                f3 = ((float) (getLeftRightGap() + i3)) - b2.right;
            }
            q.a(this.e, "correct: " + f3 + Token.SEPARATOR + f2);
            this.x.postTranslate(f3, f2);
        }
    }

    private RectF b(Matrix matrix) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return this.y;
        }
        this.y.set(0.0f, 0.0f, (float) drawable.getIntrinsicWidth(), (float) drawable.getIntrinsicHeight());
        matrix.mapRect(this.y);
        return this.y;
    }

    private float a(Matrix matrix, int i2) {
        matrix.getValues(this.z);
        return this.z[i2];
    }

    private void j() {
        q.a(this.e, "resetMatrix");
        this.x.reset();
        i();
        setImageViewMatrix(getDisplayMatrix());
    }

    /* access modifiers changed from: protected */
    public void setImageViewMatrix(Matrix matrix) {
        setImageMatrix(matrix);
        if (this.A != null) {
            RectF b2 = b(matrix);
            if (b2 != null) {
                this.A.a(b2);
            }
        }
    }

    public int getLeftRightGap() {
        if (this.i == 0) {
            return 0;
        }
        return (getWidth() - this.i) / 2;
    }

    public int getTopBottomGap() {
        if (this.j == 0) {
            return 0;
        }
        return (getHeight() - this.j) / 2;
    }

    private void setFirstShowCenterCropMode(Drawable drawable) {
        q.a(this.e, "setFirstShowCenterCropMode");
        float f2 = (float) this.i;
        float f3 = (float) this.j;
        RectF b2 = b(getDisplayMatrix());
        if (b2 != null && getDrawable() != null) {
            q.a(this.e, "***** display rect: " + b2);
            float width = b2.width();
            float height = b2.height();
            q.a(this.e, "***** drawable width height: " + width + "  " + height);
            float max = Math.max(f2 / width, f3 / height);
            q.a(this.e, "***** scale: " + max);
            this.x.postScale(max, max, (float) (getWidth() / 2), (float) (getHeight() / 2));
            float f4 = width * max;
            float width2 = (((float) getWidth()) - f4) / 2.0f;
            float height2 = (((float) getHeight()) - (max * height)) / 2.0f;
            i();
            setImageViewMatrix(getDisplayMatrix());
        }
    }

    private void a(Drawable drawable) {
        float f2 = (float) this.i;
        float f3 = (float) this.j;
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        q.a(this.e, "cropper: " + f2 + Token.SEPARATOR + f3);
        q.a(this.e, "drawble: " + intrinsicWidth + Token.SEPARATOR + intrinsicHeight);
        this.v.reset();
        float f4 = f2 / ((float) intrinsicWidth);
        float f5 = f3 / ((float) intrinsicHeight);
        if (this.L != ScaleType.CENTER) {
            if (this.L != ScaleType.CENTER_CROP) {
                if (this.L != ScaleType.CENTER_INSIDE) {
                    RectF rectF = new RectF(0.0f, 0.0f, (float) intrinsicWidth, (float) intrinsicHeight);
                    RectF rectF2 = new RectF(0.0f, 0.0f, f2, f3);
                    switch (n.a[this.L.ordinal()]) {
                        case 1:
                            this.v.setRectToRect(rectF, rectF2, ScaleToFit.START);
                            break;
                        case 2:
                            this.v.setRectToRect(rectF, rectF2, ScaleToFit.END);
                            break;
                        case 3:
                            this.v.setRectToRect(rectF, rectF2, ScaleToFit.CENTER);
                            k();
                            break;
                        case 4:
                            this.v.setRectToRect(rectF, rectF2, ScaleToFit.FILL);
                            break;
                    }
                } else {
                    float min = Math.min(1.0f, Math.min(f4, f5));
                    this.v.postScale(min, min);
                    this.v.postTranslate((f2 - (((float) intrinsicWidth) * min)) / 2.0f, (f3 - (((float) intrinsicHeight) * min)) / 2.0f);
                }
            } else {
                float max = Math.max(f4, f5);
                this.v.postScale(max, max);
                Matrix matrix = this.v;
                matrix.postTranslate((((float) getWidth()) - (((float) intrinsicWidth) * max)) / 2.0f, (((float) getHeight()) - (max * ((float) intrinsicHeight))) / 2.0f);
            }
        } else {
            this.v.postTranslate((f2 - ((float) intrinsicWidth)) / 2.0f, (f3 - ((float) intrinsicHeight)) / 2.0f);
        }
        j();
    }

    private void k() {
        float f2;
        float f3 = 0.0f;
        RectF b2 = b(getDisplayMatrix());
        q.a(this.e, "adjustBaseMatrixBounds rect: " + b2.toString());
        if (b2 != null && getDrawable() != null) {
            float height = b2.height();
            float width = b2.width();
            int i2 = this.j;
            if (height <= ((float) i2)) {
                switch (n.a[this.L.ordinal()]) {
                    case 1:
                        f2 = -b2.top;
                        break;
                    case 2:
                        f2 = (((float) i2) - height) - b2.top;
                        break;
                    default:
                        f2 = (((((float) i2) - height) / 2.0f) - b2.top) + ((float) getTopBottomGap());
                        break;
                }
            } else if (b2.top > ((float) getTopBottomGap())) {
                f2 = (-b2.top) + ((float) getTopBottomGap());
            } else if (b2.bottom < ((float) (getTopBottomGap() + i2))) {
                f2 = ((float) (getTopBottomGap() + i2)) - b2.bottom;
            } else {
                f2 = 0.0f;
            }
            int i3 = this.i;
            if (width <= ((float) i3)) {
                switch (n.a[this.L.ordinal()]) {
                    case 1:
                        f3 = -b2.left;
                        break;
                    case 2:
                        f3 = (((float) i3) - width) - b2.left;
                        break;
                    default:
                        f3 = (((((float) i3) - width) / 2.0f) - b2.left) + ((float) getLeftRightGap());
                        break;
                }
            } else if (b2.left > ((float) getLeftRightGap())) {
                f3 = (-b2.left) + ((float) getLeftRightGap());
            } else if (b2.right < ((float) (getLeftRightGap() + i3))) {
                f3 = ((float) (getLeftRightGap() + i3)) - b2.right;
            }
            q.a(this.e, "adjust base matrix: " + f3 + Token.SEPARATOR + f2);
            this.v.postTranslate(f3, f2);
        }
    }

    public RectF getDisplayRect() {
        return b(getDisplayMatrix());
    }

    public float getScale() {
        return a(this.x, 0);
    }

    public void setMinScale(float f2) {
        c(f2, this.p, this.q);
        this.o = f2;
    }

    public void setMidScale(float f2) {
        c(this.o, f2, this.q);
        this.p = f2;
    }

    public void setMaxScale(float f2) {
        c(this.o, this.p, f2);
        this.q = f2;
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        b();
    }

    public void setImageResource(int i2) {
        super.setImageResource(i2);
        b();
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        b();
    }

    public void setOnMatrixChangeListener(r rVar) {
        this.A = rVar;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.D = onLongClickListener;
    }

    public void setOnPhotoTapListener(t tVar) {
        this.B = tVar;
    }

    public void setOnViewTapListener(u uVar) {
        this.C = uVar;
    }

    public void setOnOperateListener(s sVar) {
        this.E = sVar;
    }

    public void setScaleType(ScaleType scaleType) {
        if (scaleType == null || ScaleType.MATRIX == scaleType) {
            throw new IllegalArgumentException(scaleType.name() + " is not supported in PhotoCropView");
        } else if (scaleType != this.L) {
            this.L = scaleType;
            b();
        }
    }

    public ScaleType getScaleType() {
        return this.L;
    }

    public void setZoomable(boolean z2) {
        this.K = z2;
        b();
    }

    public void b(float f2, float f3, float f4) {
        post(new o(this, getScale(), f2, f3, f4));
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        d();
        this.f = false;
        super.onDetachedFromWindow();
    }

    private void c(float f2, float f3, float f4) {
        if (f2 >= f3) {
            throw new IllegalArgumentException("MinZoom should be less than MidZoom");
        } else if (f3 >= f4) {
            throw new IllegalArgumentException("MidZoom should be less than MaxZoom");
        }
    }
}
