package defpackage;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import java.lang.ref.WeakReference;

/* renamed from: eqt reason: default package */
/* compiled from: PhotoViewAttacher */
public final class eqt implements OnTouchListener, OnGlobalLayoutListener, eqs, eqx {
    static final Interpolator a = new AccelerateDecelerateInterpolator();
    /* access modifiers changed from: private */
    public static final boolean o = Log.isLoggable("PhotoViewAttacher", 3);
    private int A = 2;
    public int b = 200;
    public float c = 1.0f;
    public float d = 1.75f;
    public float e = 3.0f;
    public boolean f = true;
    public GestureDetector g;
    public final Matrix h = new Matrix();
    public c i;
    public d j;
    public e k;
    public OnLongClickListener l;
    public boolean m;
    public ScaleType n = ScaleType.FIT_CENTER;
    private WeakReference<ImageView> p;
    private eqy q;
    private final Matrix r = new Matrix();
    private final Matrix s = new Matrix();
    private final RectF t = new RectF();
    private final float[] u = new float[9];
    private int v;
    private int w;
    private int x;
    private int y;
    private b z;

    /* renamed from: eqt$2 reason: invalid class name */
    /* compiled from: PhotoViewAttacher */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a = new int[ScaleType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                android.widget.ImageView$ScaleType[] r0 = android.widget.ImageView.ScaleType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.MATRIX     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.FIT_START     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
                android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.FIT_END     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0035 }
                android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.FIT_CENTER     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0040 }
                android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.FIT_XY     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.eqt.AnonymousClass2.<clinit>():void");
        }
    }

    /* renamed from: eqt$a */
    /* compiled from: PhotoViewAttacher */
    class a implements Runnable {
        private final float b;
        private final float c;
        private final long d = System.currentTimeMillis();
        private final float e;
        private final float f;

        public a(float f2, float f3, float f4, float f5) {
            this.b = f4;
            this.c = f5;
            this.e = f2;
            this.f = f3;
        }

        public final void run() {
            ImageView c2 = eqt.this.c();
            if (c2 != null) {
                float interpolation = eqt.a.getInterpolation(Math.min(1.0f, (((float) (System.currentTimeMillis() - this.d)) * 1.0f) / ((float) eqt.this.b)));
                float d2 = (this.e + ((this.f - this.e) * interpolation)) / eqt.this.d();
                eqt.this.h.postScale(d2, d2, this.b, this.c);
                eqt.this.g();
                if (interpolation < 1.0f) {
                    eqq.a(c2, this);
                }
            }
        }
    }

    /* renamed from: eqt$b */
    /* compiled from: PhotoViewAttacher */
    class b implements Runnable {
        final erc a;
        int b;
        int c;

        public b(Context context) {
            erc erc;
            if (VERSION.SDK_INT < 9) {
                erc = new erb(context);
            } else if (VERSION.SDK_INT < 14) {
                erc = new eqz(context);
            } else {
                erc = new era(context);
            }
            this.a = erc;
        }

        public final void a() {
            eqt.o;
            this.a.b();
        }

        public final void run() {
            if (!this.a.c()) {
                ImageView c2 = eqt.this.c();
                if (c2 != null && this.a.a()) {
                    int d2 = this.a.d();
                    int e = this.a.e();
                    eqt.o;
                    eqt.this.h.postTranslate((float) (this.b - d2), (float) (this.c - e));
                    eqt.this.a(eqt.this.f());
                    this.b = d2;
                    this.c = e;
                    eqq.a(c2, this);
                }
            }
        }
    }

    /* renamed from: eqt$c */
    /* compiled from: PhotoViewAttacher */
    public interface c {
    }

    /* renamed from: eqt$d */
    /* compiled from: PhotoViewAttacher */
    public interface d {
        void a(View view);
    }

    /* renamed from: eqt$e */
    /* compiled from: PhotoViewAttacher */
    public interface e {
        void a();
    }

    public static void a(float f2, float f3, float f4) {
        if (f2 >= f3) {
            throw new IllegalArgumentException("MinZoom has to be less than MidZoom");
        } else if (f3 >= f4) {
            throw new IllegalArgumentException("MidZoom has to be less than MaxZoom");
        }
    }

    public static boolean a(ScaleType scaleType) {
        if (scaleType == null) {
            return false;
        }
        if (AnonymousClass2.a[scaleType.ordinal()] != 1) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(scaleType.name());
        sb.append(" is not supported in PhotoView");
        throw new IllegalArgumentException(sb.toString());
    }

    private static void a(ImageView imageView) {
        if (imageView != null && !(imageView instanceof eqs) && !ScaleType.MATRIX.equals(imageView.getScaleType())) {
            imageView.setScaleType(ScaleType.MATRIX);
        }
    }

    public eqt(ImageView imageView) {
        eqy eqy;
        this.p = new WeakReference<>(imageView);
        imageView.setDrawingCacheEnabled(true);
        imageView.setOnTouchListener(this);
        ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.addOnGlobalLayoutListener(this);
        }
        a(imageView);
        if (!imageView.isInEditMode()) {
            Context context = imageView.getContext();
            int i2 = VERSION.SDK_INT;
            if (i2 < 5) {
                eqy = new equ(context);
            } else if (i2 < 8) {
                eqy = new eqv(context);
            } else {
                eqy = new eqw(context);
            }
            eqy.a(this);
            this.q = eqy;
            this.g = new GestureDetector(imageView.getContext(), new SimpleOnGestureListener() {
                public final void onLongPress(MotionEvent motionEvent) {
                    if (eqt.this.l != null) {
                        eqt.this.l.onLongClick(eqt.this.c());
                    }
                }
            });
            this.g.setOnDoubleTapListener(new eqr(this));
            a(true);
        }
    }

    public final void a() {
        if (this.p != null) {
            ImageView imageView = (ImageView) this.p.get();
            if (imageView != null) {
                ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
                if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
                    viewTreeObserver.removeGlobalOnLayoutListener(this);
                }
                imageView.setOnTouchListener(null);
                j();
            }
            if (this.g != null) {
                this.g.setOnDoubleTapListener(null);
            }
            this.i = null;
            this.j = null;
            this.k = null;
            this.p = null;
        }
    }

    public final RectF b() {
        h();
        return b(f());
    }

    public final void a(float f2) {
        this.h.setRotate(f2 % 360.0f);
        g();
    }

    public final ImageView c() {
        ImageView imageView = this.p != null ? (ImageView) this.p.get() : null;
        if (imageView == null) {
            a();
        }
        return imageView;
    }

    public final float d() {
        return (float) Math.sqrt((double) (((float) Math.pow((double) a(this.h, 0), 2.0d)) + ((float) Math.pow((double) a(this.h, 3), 2.0d))));
    }

    public final void a(float f2, float f3) {
        if (!this.q.a()) {
            ImageView c2 = c();
            if (c2 != null) {
                this.h.postTranslate(f2, f3);
                g();
                ViewParent parent = c2.getParent();
                if (!this.f || this.q.a()) {
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                } else if ((this.A == 2 || ((this.A == 0 && f2 >= 1.0f) || (this.A == 1 && f2 <= -1.0f))) && parent != null) {
                    parent.requestDisallowInterceptTouchEvent(false);
                }
            }
        }
    }

    public final void b(float f2, float f3) {
        int i2;
        int i3;
        int i4;
        int i5;
        ImageView c2 = c();
        if (c2 != null) {
            this.z = new b(c2.getContext());
            b bVar = this.z;
            int b2 = b(c2);
            int c3 = c(c2);
            int i6 = (int) f2;
            int i7 = (int) f3;
            RectF b3 = eqt.this.b();
            if (b3 != null) {
                int round = Math.round(-b3.left);
                float f4 = (float) b2;
                if (f4 < b3.width()) {
                    i2 = Math.round(b3.width() - f4);
                    i3 = 0;
                } else {
                    i3 = round;
                    i2 = i3;
                }
                int round2 = Math.round(-b3.top);
                float f5 = (float) c3;
                if (f5 < b3.height()) {
                    i4 = Math.round(b3.height() - f5);
                    i5 = 0;
                } else {
                    i5 = round2;
                    i4 = i5;
                }
                bVar.b = round;
                bVar.c = round2;
                if (!(round == i2 && round2 == i4)) {
                    bVar.a.a(round, round2, i6, i7, i3, i2, i5, i4);
                }
            }
            c2.post(this.z);
        }
    }

    public final void onGlobalLayout() {
        ImageView c2 = c();
        if (c2 != null) {
            if (this.m) {
                int top = c2.getTop();
                int right = c2.getRight();
                int bottom = c2.getBottom();
                int left = c2.getLeft();
                if (!(top == this.v && bottom == this.x && left == this.y && right == this.w)) {
                    a(c2.getDrawable());
                    this.v = top;
                    this.w = right;
                    this.x = bottom;
                    this.y = left;
                }
                return;
            }
            a(c2.getDrawable());
        }
    }

    public final void b(float f2, float f3, float f4) {
        if (d() < this.e || f2 < 1.0f) {
            this.h.postScale(f2, f2, f3, f4);
            g();
        }
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z2 = false;
        if (!this.m) {
            return false;
        }
        ImageView imageView = (ImageView) view;
        if (!((imageView == null || imageView.getDrawable() == null) ? false : true)) {
            return false;
        }
        ViewParent parent = view.getParent();
        int action = motionEvent.getAction();
        if (action != 3) {
            switch (action) {
                case 0:
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    j();
                    break;
                case 1:
                    break;
            }
        }
        if (d() < this.c) {
            RectF b2 = b();
            if (b2 != null) {
                a aVar = new a(d(), this.c, b2.centerX(), b2.centerY());
                view.post(aVar);
                z2 = true;
            }
        }
        if (this.q != null && this.q.c(motionEvent)) {
            z2 = true;
        }
        if (this.g == null || !this.g.onTouchEvent(motionEvent)) {
            return z2;
        }
        return true;
    }

    public final void a(float f2, boolean z2) {
        ImageView c2 = c();
        if (c2 != null) {
            a(f2, (float) (c2.getRight() / 2), (float) (c2.getBottom() / 2), z2);
        }
    }

    public final void a(float f2, float f3, float f4, boolean z2) {
        ImageView c2 = c();
        if (c2 != null && f2 >= this.c && f2 <= this.e) {
            if (z2) {
                a aVar = new a(d(), f2, f3, f4);
                c2.post(aVar);
                return;
            }
            this.h.setScale(f2, f2, f3, f4);
            g();
        }
    }

    public final void a(boolean z2) {
        this.m = z2;
        e();
    }

    public final void e() {
        ImageView c2 = c();
        if (c2 != null) {
            if (this.m) {
                a(c2);
                a(c2.getDrawable());
                return;
            }
            l();
        }
    }

    public final Matrix f() {
        this.s.set(this.r);
        this.s.postConcat(this.h);
        return this.s;
    }

    private void j() {
        if (this.z != null) {
            this.z.a();
            this.z = null;
        }
    }

    public final void g() {
        if (h()) {
            a(f());
        }
    }

    private void k() {
        ImageView c2 = c();
        if (c2 != null && !(c2 instanceof eqs) && !ScaleType.MATRIX.equals(c2.getScaleType())) {
            throw new IllegalStateException("The ImageView's ScaleType has been changed since attaching a PhotoViewAttacher");
        }
    }

    public final boolean h() {
        float f2;
        float f3;
        ImageView c2 = c();
        if (c2 == null) {
            return false;
        }
        RectF b2 = b(f());
        if (b2 == null) {
            return false;
        }
        float height = b2.height();
        float width = b2.width();
        float c3 = (float) c(c2);
        float f4 = 0.0f;
        if (height <= c3) {
            switch (AnonymousClass2.a[this.n.ordinal()]) {
                case 2:
                    f2 = -b2.top;
                    break;
                case 3:
                    f2 = (c3 - height) - b2.top;
                    break;
                default:
                    f2 = ((c3 - height) / 2.0f) - b2.top;
                    break;
            }
        } else {
            f2 = b2.top > 0.0f ? -b2.top : b2.bottom < c3 ? c3 - b2.bottom : 0.0f;
        }
        float b3 = (float) b(c2);
        if (width <= b3) {
            switch (AnonymousClass2.a[this.n.ordinal()]) {
                case 2:
                    f3 = -b2.left;
                    break;
                case 3:
                    f3 = (b3 - width) - b2.left;
                    break;
                default:
                    f3 = ((b3 - width) / 2.0f) - b2.left;
                    break;
            }
            f4 = f3;
            this.A = 2;
        } else if (b2.left > 0.0f) {
            this.A = 0;
            f4 = -b2.left;
        } else if (b2.right < b3) {
            f4 = b3 - b2.right;
            this.A = 1;
        } else {
            this.A = -1;
        }
        this.h.postTranslate(f4, f2);
        return true;
    }

    private RectF b(Matrix matrix) {
        ImageView c2 = c();
        if (c2 != null) {
            Drawable drawable = c2.getDrawable();
            if (drawable != null) {
                this.t.set(0.0f, 0.0f, (float) drawable.getIntrinsicWidth(), (float) drawable.getIntrinsicHeight());
                matrix.mapRect(this.t);
                return this.t;
            }
        }
        return null;
    }

    private float a(Matrix matrix, int i2) {
        matrix.getValues(this.u);
        return this.u[i2];
    }

    private void l() {
        this.h.reset();
        a(f());
        h();
    }

    public final void a(Matrix matrix) {
        ImageView c2 = c();
        if (c2 != null) {
            k();
            c2.setImageMatrix(matrix);
            if (this.i != null) {
                b(matrix);
            }
        }
    }

    private void a(Drawable drawable) {
        ImageView c2 = c();
        if (c2 != null && drawable != null) {
            float b2 = (float) b(c2);
            float c3 = (float) c(c2);
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            this.r.reset();
            float f2 = (float) intrinsicWidth;
            float f3 = b2 / f2;
            float f4 = (float) intrinsicHeight;
            float f5 = c3 / f4;
            if (this.n != ScaleType.CENTER) {
                if (this.n != ScaleType.CENTER_CROP) {
                    if (this.n != ScaleType.CENTER_INSIDE) {
                        RectF rectF = new RectF(0.0f, 0.0f, f2, f4);
                        RectF rectF2 = new RectF(0.0f, 0.0f, b2, c3);
                        switch (AnonymousClass2.a[this.n.ordinal()]) {
                            case 2:
                                this.r.setRectToRect(rectF, rectF2, ScaleToFit.START);
                                break;
                            case 3:
                                this.r.setRectToRect(rectF, rectF2, ScaleToFit.END);
                                break;
                            case 4:
                                this.r.setRectToRect(rectF, rectF2, ScaleToFit.CENTER);
                                break;
                            case 5:
                                this.r.setRectToRect(rectF, rectF2, ScaleToFit.FILL);
                                break;
                        }
                    } else {
                        float min = Math.min(1.0f, Math.min(f3, f5));
                        this.r.postScale(min, min);
                        this.r.postTranslate((b2 - (f2 * min)) / 2.0f, (c3 - (f4 * min)) / 2.0f);
                    }
                } else {
                    float max = Math.max(f3, f5);
                    this.r.postScale(max, max);
                    this.r.postTranslate((b2 - (f2 * max)) / 2.0f, (c3 - (f4 * max)) / 2.0f);
                }
            } else {
                this.r.postTranslate((b2 - f2) / 2.0f, (c3 - f4) / 2.0f);
            }
            l();
        }
    }

    private static int b(ImageView imageView) {
        if (imageView == null) {
            return 0;
        }
        return (imageView.getWidth() - imageView.getPaddingLeft()) - imageView.getPaddingRight();
    }

    private static int c(ImageView imageView) {
        if (imageView == null) {
            return 0;
        }
        return (imageView.getHeight() - imageView.getPaddingTop()) - imageView.getPaddingBottom();
    }
}
