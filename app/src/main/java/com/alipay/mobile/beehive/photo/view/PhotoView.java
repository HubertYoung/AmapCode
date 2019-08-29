package com.alipay.mobile.beehive.photo.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.alipay.mobile.beehive.photo.ui.PhotoBrowseView;
import com.alipay.mobile.beehive.photo.util.CompactScroller;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

@TargetApi(9)
public class PhotoView extends ImageView {
    private static final boolean DEFAULT_FIT_SPACE = true;
    private static final float FIT_FACTOR = 1.0f;
    private static final float MAX_FACTOR = 3.2f;
    private static final float MIN_FACTOR = 0.6f;
    private static final int MIN_SAFE_TEXURE_SIZE = 2048;
    private static final int SKIA_DEFAULT_MAX_TEXURE_SIZE = 32766;
    public static final String TAG = "PhotoView";
    private static final float ZOOM_FACTOR = 2.5f;
    private static boolean isCapableTakeNewMethod;
    private static boolean isMaxTexureInited = false;
    private static int maxTextureSize = 2048;
    /* access modifiers changed from: private */
    public boolean canScale;
    /* access modifiers changed from: private */
    public boolean cropSquare;
    private int currentDrawableHashCode;
    public int defaultDrawableHashCode;
    private int displayType;
    private a dragDetector;
    private int drawableHeight;
    private int drawableWidth;
    private boolean enableCrop;
    private boolean enableScale;
    public int failDrawableHashCode;
    private boolean fitSpace;
    /* access modifiers changed from: private */
    public c flingRunnable;
    private GestureDetector gestureDetector;
    private e gridDetector;
    /* access modifiers changed from: private */
    public boolean isInitToMaxSquare;
    private Matrix matrix;
    private float[] matrixValues;
    private float maxFactor;
    /* access modifiers changed from: private */
    public float minFactor;
    /* access modifiers changed from: private */
    public float normalFactor;
    /* access modifiers changed from: private */
    public OnClickListener onClickListener;
    /* access modifiers changed from: private */
    public OnLongClickListener onLongClickListener;
    /* access modifiers changed from: private */
    public RectF photoRect;
    private boolean photoValid;
    private ScaleGestureDetector scaleDetector;
    /* access modifiers changed from: private */
    public float scaleFactor;
    /* access modifiers changed from: private */
    public i state;
    public int thumbDrawableHashCode;
    /* access modifiers changed from: private */
    public int viewHeight;
    /* access modifiers changed from: private */
    public int viewWidth;
    /* access modifiers changed from: private */
    public float zoomFactor;

    public interface SimpleDragListener {
        void onDrag(float f, float f2);

        void onDragBegin();

        void onDragEnd();
    }

    private class a {
        private SimpleDragListener b;
        private float c;
        private float d;
        private boolean e = true;

        public a(SimpleDragListener listener) {
            this.b = listener;
        }

        public final boolean a(MotionEvent event) {
            if (PhotoView.this.state != i.NONE && PhotoView.this.state != i.DRAG) {
                this.c = event.getX();
                this.d = event.getY();
                return false;
            } else if (this.b == null || !this.e) {
                return false;
            } else {
                int action = event.getAction();
                if (action == 0) {
                    this.c = event.getX();
                    this.d = event.getY();
                    this.b.onDragBegin();
                } else if (action == 5) {
                    this.e = false;
                } else if (action == 2) {
                    float deltaX = event.getX() - this.c;
                    float deltaY = event.getY() - this.d;
                    this.c = event.getX();
                    this.d = event.getY();
                    if (deltaX == 0.0f && deltaY == 0.0f) {
                        return false;
                    }
                    this.b.onDrag(deltaX, deltaY);
                } else if (action == 1) {
                    this.b.onDragEnd();
                    this.c = 0.0f;
                    this.d = 0.0f;
                    this.e = true;
                }
                return true;
            }
        }
    }

    private class b implements SimpleDragListener {
        private b() {
        }

        /* synthetic */ b(PhotoView x0, byte b) {
            this();
        }

        public final void onDragBegin() {
            PhotoView.this.setState(i.DRAG);
        }

        public final void onDrag(float deltaX, float deltaY) {
            if (PhotoView.this.state == i.DRAG) {
                float left = PhotoView.this.photoRect.left;
                float top = PhotoView.this.photoRect.top;
                float bottom = PhotoView.this.photoRect.bottom;
                PhotoView.this.postTranslate(PhotoView.this.checkTranslage(deltaX, (float) PhotoView.this.viewWidth, left, PhotoView.this.photoRect.right), PhotoView.this.checkTranslage(deltaY, (float) PhotoView.this.viewHeight, top, bottom));
            }
        }

        public final void onDragEnd() {
            if (PhotoView.this.state == i.DRAG) {
                PhotoView.this.setState(i.NONE);
            }
        }
    }

    private class c implements Runnable {
        private CompactScroller b;
        private int c;
        private int d;

        @SuppressLint({"NewApi"})
        c(int velX, int velY) {
            int maxX;
            int minX;
            int maxY;
            int minY;
            PhotoView.this.setState(i.FLING);
            this.b = new CompactScroller(PhotoView.this.getContext());
            int startX = (int) PhotoView.this.photoRect.left;
            int startY = (int) PhotoView.this.photoRect.top;
            float photoWidth = PhotoView.this.photoRect.width();
            float photoHeight = PhotoView.this.photoRect.height();
            if (photoWidth > ((float) PhotoView.this.viewWidth)) {
                maxX = 0;
                minX = PhotoView.this.viewWidth - ((int) photoWidth);
            } else {
                maxX = startX;
                minX = startX;
            }
            if (photoHeight > ((float) PhotoView.this.viewHeight)) {
                maxY = 0;
                minY = PhotoView.this.viewHeight - ((int) photoHeight);
            } else {
                maxY = startY;
                minY = startY;
            }
            this.b.fling(startX, startY, velX, velY, minX, maxX, minY, maxY, 1, 1);
            this.c = startX;
            this.d = startY;
        }

        public final void a() {
            if (this.b != null) {
                PhotoView.this.setState(i.NONE);
                this.b.forceFinished(true);
            }
        }

        public final void run() {
            if (this.b == null || this.b.isFinished() || !this.b.computeScrollOffset()) {
                if (PhotoView.this.state == i.FLING) {
                    PhotoView.this.setState(i.NONE);
                }
                this.b = null;
                return;
            }
            int newX = this.b.getCurrX();
            int newY = this.b.getCurrY();
            this.c = newX;
            this.d = newY;
            PhotoView.this.postTranslate((float) (newX - this.c), (float) (newY - this.d));
            PhotoView.this.postAnimation(this);
        }
    }

    private class d extends SimpleOnGestureListener {
        private d() {
        }

        /* synthetic */ d(PhotoView x0, byte b) {
            this();
        }

        public final boolean onSingleTapConfirmed(MotionEvent e) {
            PhotoLogger.debug(PhotoView.TAG, "onSingleTapConfirmed");
            if (PhotoView.this.onClickListener != null) {
                PhotoView.this.onClickListener.onClick(PhotoView.this);
            }
            return true;
        }

        public final void onLongPress(MotionEvent e) {
            if (PhotoView.this.onLongClickListener != null) {
                PhotoView.this.onLongClickListener.onLongClick(PhotoView.this);
            }
        }

        public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (PhotoView.this.state == i.NONE || PhotoView.this.state == i.FLING || PhotoView.this.state == i.DRAG) {
                if (PhotoView.this.flingRunnable != null) {
                    PhotoView.this.flingRunnable.a();
                }
                PhotoView.this.flingRunnable = new c((int) velocityX, (int) velocityY);
                PhotoView.this.postAnimation(PhotoView.this.flingRunnable);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        public final boolean onDoubleTap(MotionEvent e) {
            float targetScale;
            if (PhotoView.this.state != i.NONE) {
                return false;
            }
            if (((double) Math.abs(PhotoView.this.scaleFactor - PhotoView.this.normalFactor)) <= 0.001d) {
                targetScale = PhotoView.this.zoomFactor;
            } else {
                targetScale = PhotoView.this.normalFactor;
            }
            PhotoView.this.postAnimation(new h(targetScale, PhotoView.this.calScaleFocus(targetScale, new PointF(e.getX(), e.getY()))));
            return true;
        }

        public final boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
    }

    private class e {
        private PointF b = new PointF();
        private Paint c = new Paint();
        private int d;
        private RectF e = new RectF();
        private RectF f = new RectF();
        private int g;
        private int h;
        private float i;
        private int j;

        public e() {
            this.h = PhotoUtil.dp2px(PhotoView.this.getContext(), 30);
            this.g = PhotoUtil.dp2px(PhotoView.this.getContext(), 4);
            this.j = PhotoUtil.dp2px(PhotoView.this.getContext(), 2);
            this.i = 4.0f * ((float) this.h);
            this.c.setColor(-1);
            this.c.setStyle(Style.STROKE);
        }

        public final RectF a() {
            return this.e;
        }

        public final void a(Canvas canvas) {
            int centerSize = PhotoUtil.dp2px(PhotoView.this.getContext(), 6);
            int sideSize = centerSize * 2;
            int size = this.g / 2;
            this.c.setStrokeWidth((float) (this.g / 3));
            this.f.set(this.e.left + ((float) size), this.e.top + ((float) size), this.e.right - ((float) size), this.e.bottom - ((float) size));
            canvas.drawLine(this.e.centerX(), this.e.top, this.e.centerX(), this.e.bottom, this.c);
            canvas.drawLine(this.e.left, this.e.centerY(), this.e.right, this.e.centerY(), this.c);
            this.c.setStyle(Style.FILL);
            Path path = new Path();
            path.moveTo(this.e.centerX() - ((float) centerSize), this.e.centerY());
            path.lineTo(this.e.centerX(), this.e.centerY() - ((float) centerSize));
            path.lineTo(this.e.centerX() + ((float) centerSize), this.e.centerY());
            path.lineTo(this.e.centerX(), this.e.centerY() + ((float) centerSize));
            path.close();
            canvas.drawPath(path, this.c);
            path.reset();
            path.moveTo(this.e.left, this.e.top);
            path.lineTo(this.e.left + ((float) sideSize), this.e.top);
            path.lineTo(this.e.left, this.e.top + ((float) sideSize));
            path.close();
            canvas.drawPath(path, this.c);
            path.reset();
            path.moveTo(this.e.right, this.e.top);
            path.lineTo(this.e.right - ((float) sideSize), this.e.top);
            path.lineTo(this.e.right, this.e.top + ((float) sideSize));
            path.close();
            canvas.drawPath(path, this.c);
            path.reset();
            path.moveTo(this.e.left, this.e.bottom);
            path.lineTo(this.e.left + ((float) sideSize), this.e.bottom);
            path.lineTo(this.e.left, this.e.bottom - ((float) sideSize));
            path.close();
            canvas.drawPath(path, this.c);
            path.reset();
            path.moveTo(this.e.right, this.e.bottom);
            path.lineTo(this.e.right - ((float) sideSize), this.e.bottom);
            path.lineTo(this.e.right, this.e.bottom - ((float) sideSize));
            path.close();
            canvas.drawPath(path, this.c);
            if (VERSION.SDK_INT >= 28) {
                this.c.setStrokeWidth((float) this.g);
                this.c.setStyle(Style.STROKE);
                this.c.setStrokeWidth((float) this.g);
                canvas.drawRect(this.e, this.c);
                return;
            }
            canvas.clipRect(this.f, Op.XOR);
            canvas.drawColor(1711276032);
            this.c.setStrokeWidth((float) this.g);
            this.c.setStyle(Style.STROKE);
            canvas.drawRect(this.f, this.c);
        }

        public final boolean a(MotionEvent event) {
            int action = event.getAction() & 255;
            if (action == 0) {
                this.d = b(event);
                this.b.set(event.getX(), event.getY());
            } else if (action == 5 || action == 1 || action == 6) {
                this.d = 0;
            } else if (action == 2 && this.d != 0) {
                c(event);
                PhotoView.this.invalidate();
            }
            if (this.d != 0) {
                return true;
            }
            return false;
        }

        public final void b() {
            float rectSize;
            float width = ((float) PhotoView.this.viewWidth) > PhotoView.this.photoRect.width() ? PhotoView.this.photoRect.width() : (float) PhotoView.this.viewWidth;
            float height = ((float) PhotoView.this.viewHeight) > PhotoView.this.photoRect.height() ? PhotoView.this.photoRect.height() : (float) PhotoView.this.viewHeight;
            if (PhotoView.this.isInitToMaxSquare) {
                rectSize = Math.min(width, height) - ((float) this.j);
            } else {
                rectSize = (Math.min(width, height) * 2.0f) / 3.0f;
            }
            this.e.set((((float) PhotoView.this.viewWidth) - rectSize) / 2.0f, (((float) PhotoView.this.viewHeight) - rectSize) / 2.0f, (((float) PhotoView.this.viewWidth) + rectSize) / 2.0f, (((float) PhotoView.this.viewHeight) + rectSize) / 2.0f);
        }

        private int b(MotionEvent event) {
            int type = 0;
            float px = event.getX();
            float py = event.getY();
            if (py >= this.e.top - ((float) this.h) && py <= this.e.bottom + ((float) this.h)) {
                if (Math.abs(px - this.e.left) < ((float) this.h)) {
                    type = 1;
                }
                if (Math.abs(px - this.e.right) < ((float) this.h)) {
                    type |= 4;
                }
            }
            if (px >= this.e.left - ((float) this.h) && px <= this.e.right + ((float) this.h)) {
                if (Math.abs(py - this.e.top) < ((float) this.h)) {
                    type |= 2;
                }
                if (Math.abs(py - this.e.bottom) < ((float) this.h)) {
                    type |= 8;
                }
            }
            if (Math.abs(px - ((this.e.left + this.e.right) / 2.0f)) < ((float) this.h) && Math.abs(py - ((this.e.top + this.e.bottom) / 2.0f)) < ((float) this.h)) {
                type = 16;
            }
            if (!PhotoView.this.cropSquare || !PhotoUtil.isPowerOfTwo(type)) {
                return type;
            }
            return 16;
        }

        private void c(MotionEvent event) {
            float bottom;
            float deltaX = event.getX() - this.b.x;
            float deltaY = event.getY() - this.b.y;
            this.b.set(event.getX(), event.getY());
            float left = PhotoView.this.photoRect.left > 0.0f ? PhotoView.this.photoRect.left : 0.0f;
            float top = PhotoView.this.photoRect.top > 0.0f ? PhotoView.this.photoRect.top : 0.0f;
            float right = PhotoView.this.photoRect.right > ((float) PhotoView.this.viewWidth) ? (float) PhotoView.this.viewWidth : PhotoView.this.photoRect.right;
            if (PhotoView.this.photoRect.bottom > ((float) PhotoView.this.viewHeight)) {
                bottom = (float) PhotoView.this.viewHeight;
            } else {
                bottom = PhotoView.this.photoRect.bottom;
            }
            if ((this.d & 16) != 0) {
                RectF temp = new RectF(this.e);
                temp.offset(deltaX, deltaY);
                if (temp.top >= top && temp.left >= left && temp.right <= right && temp.bottom <= bottom) {
                    this.e.offset(deltaX, deltaY);
                    return;
                }
                return;
            }
            if (PhotoView.this.cropSquare && this.d != 16) {
                float leftSpace = this.e.left - left;
                float topSpace = this.e.top - top;
                float rightSpace = right - this.e.right;
                float bottomSpace = bottom - this.e.bottom;
                if (deltaX > 0.0f) {
                    if (deltaX >= rightSpace) {
                        deltaX = rightSpace;
                    }
                } else if ((-deltaX) >= leftSpace) {
                    deltaX = -leftSpace;
                }
                if (deltaY > 0.0f) {
                    if (deltaY >= bottomSpace) {
                        deltaY = bottomSpace;
                    }
                } else if ((-deltaY) >= topSpace) {
                    deltaY = -topSpace;
                }
                float delta = Math.min(Math.abs(deltaX), Math.abs(deltaY));
                if (delta != 0.0f) {
                    deltaX = delta * (deltaX / Math.abs(deltaX));
                    deltaY = delta * (deltaY / Math.abs(deltaY));
                } else {
                    return;
                }
            }
            if ((this.d & 1) != 0) {
                if (this.e.left + deltaX < left) {
                    deltaX = left - this.e.left;
                }
                if (this.e.left + deltaX + this.i > this.e.right) {
                    deltaX = (this.e.right - this.e.left) - this.i;
                }
                this.e.left += deltaX;
            }
            if ((this.d & 2) != 0) {
                if (this.e.top + deltaY < top) {
                    deltaY = top - this.e.top;
                }
                if (this.e.top + deltaY + this.i > this.e.bottom) {
                    deltaY = (this.e.bottom - this.e.top) - this.i;
                }
                this.e.top += deltaY;
            }
            if ((this.d & 4) != 0) {
                if (this.e.right + deltaX > right) {
                    deltaX = right - this.e.right;
                }
                if ((this.e.right + deltaX) - this.i < this.e.left) {
                    deltaX = (this.i - this.e.right) + this.e.left;
                }
                this.e.right += deltaX;
            }
            if ((this.d & 8) != 0) {
                if (this.e.bottom + deltaY > bottom) {
                    deltaY = bottom - this.e.bottom;
                }
                if ((this.e.bottom + deltaY) - this.i < this.e.top) {
                    deltaY = (this.i - this.e.bottom) + this.e.top;
                }
                this.e.bottom += deltaY;
            }
        }
    }

    private class f {
        private Interpolator b;
        private long c = System.currentTimeMillis();
        private float d;

        public f(Interpolator inter, float duration) {
            this.b = inter;
            this.d = duration;
        }

        public final float a() {
            return this.b.getInterpolation(Math.min(1.0f, ((float) (System.currentTimeMillis() - this.c)) / this.d));
        }
    }

    private class g extends SimpleOnScaleGestureListener {
        private boolean b;

        private g() {
        }

        /* synthetic */ g(PhotoView x0, byte b2) {
            this();
        }

        public final boolean onScaleBegin(ScaleGestureDetector detector) {
            if (PhotoView.this.canScale) {
                this.b = PhotoView.this.scaleFactor >= PhotoView.this.zoomFactor;
                PhotoView.this.setState(i.SCALE);
            }
            return true;
        }

        public final boolean onScale(ScaleGestureDetector detector) {
            if (PhotoView.this.state == i.NONE || PhotoView.this.state == i.SCALE) {
                float factor = detector.getScaleFactor();
                float px = detector.getFocusX();
                float py = detector.getFocusY();
                float nextScale = PhotoView.this.scaleFactor * factor;
                if (nextScale >= PhotoView.this.zoomFactor) {
                    factor = PhotoView.this.zoomFactor / PhotoView.this.scaleFactor;
                } else if (!this.b && nextScale > PhotoView.this.zoomFactor) {
                    factor = PhotoView.this.zoomFactor / PhotoView.this.scaleFactor;
                } else if (nextScale < PhotoView.this.minFactor) {
                    factor = PhotoView.this.minFactor / PhotoView.this.scaleFactor;
                }
                PhotoView.this.postScale(factor, px, py);
                PhotoView.this.applyMatrix();
            }
            return true;
        }

        public final void onScaleEnd(ScaleGestureDetector detector) {
            super.onScaleEnd(detector);
            this.b = false;
            if (PhotoView.this.state == i.SCALE) {
                PhotoView.this.setState(i.NONE);
                if (PhotoView.this.adjustToScale(new PointF(detector.getFocusX(), detector.getFocusY()))) {
                    PhotoLogger.debug(PhotoView.TAG, "adjustToScale");
                } else if (PhotoView.this.adjustToBounds()) {
                    PhotoLogger.debug(PhotoView.TAG, "adjustToBounds");
                }
            }
        }
    }

    private class h implements Runnable {
        private float b;
        private float c;
        private f d;
        private PointF e;

        h(float targetScale, PointF focusPoint) {
            PhotoView.this.setState(i.SCALE);
            this.d = new f(new LinearInterpolator(), 200.0f);
            this.b = PhotoView.this.scaleFactor;
            this.c = targetScale;
            this.e = focusPoint;
        }

        public final void run() {
            float interpolate = this.d.a();
            PhotoView.this.postScale(a(interpolate), this.e.x, this.e.y);
            if (interpolate < 1.0f) {
                PhotoView.this.postAnimation(this);
            } else {
                PhotoView.this.setState(i.NONE);
            }
        }

        private float a(float interpolate) {
            return (this.b + (interpolate * (this.c - this.b))) / PhotoView.this.scaleFactor;
        }
    }

    private enum i {
        NONE,
        DRAG,
        SCALE,
        FLING,
        TRANSLATE
    }

    private class j implements Runnable {
        private float b;
        private float c;
        private float d = 0.0f;
        private f e;

        public j(float px, float py) {
            this.e = new f(new LinearInterpolator(), 150.0f);
            this.b = px;
            this.c = py;
            PhotoView.this.setState(i.TRANSLATE);
        }

        public final void run() {
            if (PhotoView.this.state == i.NONE || PhotoView.this.state == i.TRANSLATE) {
                float interpolate = this.e.a();
                float delta = interpolate - this.d;
                this.d = interpolate;
                PhotoView.this.postTranslate(this.b * delta, this.c * delta);
                if (interpolate < 1.0f) {
                    PhotoView.this.postAnimation(this);
                } else {
                    PhotoView.this.setState(i.NONE);
                }
            } else {
                PhotoLogger.debug(PhotoView.TAG, "cancel translate for current state " + PhotoView.this.state);
            }
        }
    }

    static {
        boolean z = false;
        if (VERSION.SDK_INT >= 14) {
            z = true;
        }
        isCapableTakeNewMethod = z;
    }

    @Deprecated
    public void setForceFullScreen(boolean forceFullScreenPreview) {
    }

    public void setDisplayType(int displayType2) {
        this.displayType = displayType2;
    }

    public void setInitToMaxSquare(boolean initToMaxSquare) {
        this.isInitToMaxSquare = initToMaxSquare;
    }

    public PhotoView(Context context) {
        this(context, null);
    }

    public PhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.displayType = 2;
        initPhotoView();
    }

    private void initPhotoView() {
        setScaleType(ScaleType.MATRIX);
        this.scaleFactor = 1.0f;
        this.fitSpace = true;
        this.photoValid = false;
        this.matrix = new Matrix();
        this.state = i.NONE;
        this.photoRect = new RectF();
        this.matrixValues = new float[9];
        Context context = getContext();
        this.dragDetector = new a(new b(this, 0));
        this.scaleDetector = new ScaleGestureDetector(context, new g(this, 0));
        this.gestureDetector = new GestureDetector(context.getApplicationContext(), new d(this, 0));
        setEnableCrop(false);
        setClickable(true);
        setEnableScale(true);
        if (!isCapableTakeNewMethod) {
            maxTextureSize = getMaxTextureSize();
        }
    }

    public void setFitSpace(boolean fitSpace2) {
        this.fitSpace = fitSpace2;
    }

    public void setEnableScale(boolean enableScale2) {
        this.enableScale = enableScale2;
    }

    public void setCropSquare(boolean cropSquare2) {
        this.cropSquare = cropSquare2;
    }

    public boolean getEnableCrop() {
        return this.enableCrop;
    }

    public void setEnableCrop(boolean enableCrop2) {
        this.enableCrop = enableCrop2;
        if (enableCrop2) {
            this.gridDetector = new e();
            this.gridDetector.b();
        } else {
            this.gridDetector = null;
        }
        invalidate();
    }

    public Bitmap applyCrop() {
        if (this.matrix == null || !this.photoValid || !this.enableCrop) {
            return null;
        }
        Drawable drawable = getDrawable();
        if (!(drawable instanceof BitmapDrawable)) {
            return null;
        }
        Bitmap origin = ((BitmapDrawable) drawable).getBitmap();
        RectF rect = this.gridDetector.a();
        Bitmap bitmap = Bitmap.createBitmap((int) (rect.right - rect.left), (int) (rect.bottom - rect.top), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(-rect.left, -rect.top);
        canvas.drawBitmap(origin, this.matrix, null);
        return bitmap;
    }

    public void setImageResource(int resId) {
        super.setImageResource(resId);
        initMatrix();
    }

    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        initMatrix();
        adjustLayerType(bitmap, null);
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        initMatrix();
        adjustLayerType(null, drawable);
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        initMatrix();
    }

    /* access modifiers changed from: private */
    public void applyMatrix() {
        setImageMatrix(this.matrix);
    }

    /* access modifiers changed from: private */
    public void setState(i state2) {
        this.state = state2;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isCapableTakeNewMethod) {
            getMaxTextureSizeV2(canvas);
        }
        if (this.enableCrop) {
            this.gridDetector.a(canvas);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean handled = false;
        if (this.enableCrop) {
            handled = this.gridDetector.a(event);
        }
        int action = event.getAction() & 255;
        if (action == 1) {
            this.canScale = false;
        }
        if (action == 5) {
            this.canScale = true;
        }
        if (!handled && this.enableScale) {
            this.scaleDetector.onTouchEvent(event);
            this.gestureDetector.onTouchEvent(event);
            this.dragDetector.a(event);
        }
        return super.dispatchTouchEvent(event);
    }

    public void onShow() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            adjustLayerType(null, drawable);
        }
    }

    private boolean forceDisableAcc() {
        if (!PhotoBrowseView.gDisablePhotoViewHardwareAcc) {
            return false;
        }
        if (VERSION.SDK_INT < 11 || !isHardwareAccelerated()) {
            return true;
        }
        setLayerType(1, null);
        return true;
    }

    private void adjustLayerType(Bitmap bmp, Drawable drawable) {
        if (!forceDisableAcc() && VERSION.SDK_INT >= 11) {
            int maxDim = 0;
            if (bmp == null && drawable != null) {
                maxDim = Math.max(drawable.getMinimumHeight(), drawable.getMinimumWidth());
            }
            if (bmp != null) {
                maxDim = Math.max(bmp.getWidth(), bmp.getHeight());
            }
            if (maxDim > maxTextureSize) {
                if (getLayerType() != 1) {
                    setLayerType(1, null);
                }
            } else if (getLayerType() != 2) {
                setLayerType(2, null);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            this.viewWidth = ((right - left) - getPaddingLeft()) - getPaddingRight();
            this.viewHeight = ((bottom - top) - getPaddingTop()) - getPaddingBottom();
            initMatrix();
        }
    }

    /* access modifiers changed from: private */
    public float checkTranslage(float delta, float viewSize, float min, float max) {
        if (max - min <= viewSize || delta == 0.0f) {
            return 0.0f;
        }
        if (delta <= 0.0f || min + delta <= 0.0f) {
            if (delta >= 0.0f || max + delta >= viewSize || viewSize - max < delta) {
                return delta;
            }
            return viewSize - max;
        } else if (0.0f - min <= delta) {
            return 0.0f - min;
        } else {
            return delta;
        }
    }

    /* access modifiers changed from: private */
    public boolean adjustToBounds() {
        boolean z;
        float left = this.photoRect.left;
        float top = this.photoRect.top;
        float right = this.photoRect.right;
        float bottom = this.photoRect.bottom;
        float px = 0.0f;
        float py = 0.0f;
        float horSpace = (((float) this.viewWidth) - this.photoRect.width()) / 2.0f;
        float verSpace = (((float) this.viewHeight) - this.photoRect.height()) / 2.0f;
        if (left > 0.0f) {
            px = horSpace > 0.0f ? horSpace - left : -left;
        }
        if (top > 0.0f) {
            py = verSpace > 0.0f ? verSpace - top : -top;
        }
        if (right < ((float) this.viewWidth)) {
            px = horSpace > 0.0f ? (((float) this.viewWidth) - right) - horSpace : ((float) this.viewWidth) - right;
        }
        if (bottom < ((float) this.viewHeight)) {
            py = verSpace > 0.0f ? (((float) this.viewHeight) - bottom) - verSpace : ((float) this.viewHeight) - bottom;
        }
        if (px == 0.0f && py == 0.0f) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            return false;
        }
        post(new j(px, py));
        return true;
    }

    /* access modifiers changed from: private */
    public boolean adjustToScale(PointF point) {
        if (this.scaleFactor >= this.zoomFactor) {
            return true;
        }
        float targetScale = -1.0f;
        if (this.scaleFactor > this.maxFactor) {
            targetScale = this.maxFactor;
        } else if (this.scaleFactor > this.zoomFactor) {
            targetScale = this.zoomFactor;
        } else if (this.scaleFactor < this.normalFactor) {
            targetScale = this.normalFactor;
        }
        if (targetScale == -1.0f) {
            return false;
        }
        postAnimation(new h(targetScale, calScaleFocus(targetScale, point)));
        return true;
    }

    /* access modifiers changed from: private */
    public PointF calScaleFocus(float targetScale, PointF point) {
        if (point == null) {
            point = new PointF((float) (this.viewWidth / 2), (float) (this.viewHeight / 2));
        }
        Matrix preMatrix = new Matrix(this.matrix);
        float factor = targetScale / this.scaleFactor;
        preMatrix.postScale(factor, factor, point.x, point.y);
        float[] values = new float[9];
        preMatrix.getValues(values);
        float left = values[2];
        float top = values[5];
        float right = left + (((float) this.drawableWidth) * targetScale);
        float bottom = top + (((float) this.drawableHeight) * targetScale);
        if (left <= 0.0f && top <= 0.0f && right >= ((float) this.viewWidth) && bottom >= ((float) this.viewHeight)) {
            return point;
        }
        float targetWidth = ((float) this.drawableWidth) * targetScale;
        float targetHeight = ((float) this.drawableHeight) * targetScale;
        float drawableX = this.photoRect.left;
        float drawableY = this.photoRect.top;
        float targetLeft = left < 0.0f ? left : 0.0f;
        float targetTop = top < 0.0f ? top : 0.0f;
        if (targetWidth <= ((float) this.viewWidth)) {
            targetLeft = (((float) this.viewWidth) - targetWidth) / 2.0f;
        } else if (right < ((float) this.viewWidth)) {
            targetLeft = ((float) this.viewWidth) - targetWidth;
        }
        if (targetHeight <= ((float) this.viewHeight)) {
            targetTop = (((float) this.viewHeight) - targetHeight) / 2.0f;
        } else if (bottom < ((float) this.viewHeight)) {
            targetTop = ((float) this.viewHeight) - targetHeight;
        }
        PointF pointF = new PointF(((drawableX * targetWidth) - (this.photoRect.width() * targetLeft)) / (targetWidth - this.photoRect.width()), ((drawableY * targetHeight) - (this.photoRect.height() * targetTop)) / (targetHeight - this.photoRect.height()));
        return pointF;
    }

    /* access modifiers changed from: private */
    @TargetApi(16)
    public void postAnimation(Runnable runnable) {
        if (VERSION.SDK_INT >= 16) {
            postOnAnimation(runnable);
        } else {
            postDelayed(runnable, 16);
        }
    }

    private void initMatrix() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            this.photoValid = false;
            return;
        }
        this.matrix.reset();
        this.scaleFactor = 1.0f;
        this.state = i.NONE;
        this.drawableWidth = drawable.getIntrinsicWidth();
        this.drawableHeight = drawable.getIntrinsicHeight();
        if (this.drawableWidth <= 0 || this.drawableWidth <= 0) {
            this.photoValid = false;
            return;
        }
        this.photoValid = true;
        this.viewWidth = getMeasuredWidth();
        this.viewHeight = getMeasuredHeight();
        if (this.viewWidth != 0 && this.viewHeight != 0) {
            initDepends();
            postScale(this.normalFactor);
            float px = (((float) this.viewWidth) - this.photoRect.width()) / 2.0f;
            float py = (((float) this.viewHeight) - this.photoRect.height()) / 2.0f;
            if (this.photoRect.height() > ((float) this.viewHeight)) {
                postTranslate(px, 0.0f);
            } else {
                postTranslate(px, py);
            }
            if (this.enableCrop) {
                this.gridDetector.b();
            }
        }
    }

    private void initDepends() {
        float temp;
        float shoterFitScale;
        this.currentDrawableHashCode = getDrawable().hashCode();
        if (this.currentDrawableHashCode == this.defaultDrawableHashCode || this.currentDrawableHashCode == this.failDrawableHashCode) {
            PhotoLogger.debug(TAG, "default drawable or fial drawable,disable scale");
            this.normalFactor = 1.0f;
            this.minFactor = this.normalFactor;
            this.zoomFactor = this.normalFactor;
            this.maxFactor = this.normalFactor;
            return;
        }
        float scaleX = ((float) this.viewWidth) / ((float) this.drawableWidth);
        float scaleY = ((float) this.viewHeight) / ((float) this.drawableHeight);
        PhotoLogger.debug(TAG, "PhotoView displayType = ");
        switch (this.displayType) {
            case 0:
                PhotoLogger.debug(TAG, "TYPE_THUMBORI_ORI");
                initAsOri(scaleX);
                break;
            case 1:
                PhotoLogger.debug(TAG, "TYPE_THUMBORI_ORIFITWIDTH");
                if (this.currentDrawableHashCode != this.thumbDrawableHashCode) {
                    initFitWidth(scaleX);
                    break;
                } else {
                    initAsOri(scaleX);
                    break;
                }
            case 2:
                PhotoLogger.debug(TAG, "TYPE_THUMBORI_ORIFITWITDH_3P2LIMIT");
                if (this.currentDrawableHashCode != this.thumbDrawableHashCode) {
                    initMax3P2Scal(scaleX);
                    break;
                } else {
                    initAsOri(scaleX);
                    break;
                }
            case 4:
                PhotoLogger.debug(TAG, "TYPE_THUMBFITWIDTH_ORIFITWIDTH");
                initFitWidth(scaleX);
                break;
            case 8:
                PhotoLogger.debug(TAG, "TYPE_THUMBFITWITDH_LIMIT_ORIFITWITD_LIMIT");
                initMax3P2Scal(scaleX);
                break;
        }
        if (scaleX > scaleY) {
            temp = scaleX;
        } else {
            temp = scaleY;
        }
        if (temp > this.normalFactor * 2.5f) {
            shoterFitScale = temp;
        } else {
            shoterFitScale = 2.5f * this.normalFactor;
        }
        this.minFactor = this.normalFactor * MIN_FACTOR;
        this.zoomFactor = shoterFitScale;
        this.maxFactor = 1.2f * shoterFitScale;
    }

    public boolean isPhotoScaled() {
        return this.scaleFactor != this.normalFactor;
    }

    public boolean isPhotoUpTop() {
        return this.photoRect.top < 0.0f;
    }

    private void initAsOri(float scaleX) {
        PhotoLogger.debug(TAG, "is thumbnail = " + (this.currentDrawableHashCode == this.thumbDrawableHashCode));
        if (!this.fitSpace || scaleX >= 1.0f) {
            this.normalFactor = 1.0f;
        } else {
            this.normalFactor = scaleX * 1.0f;
        }
    }

    private void initFitWidth(float scaleX) {
        PhotoLogger.debug(TAG, "is thumbnail = " + (this.currentDrawableHashCode == this.thumbDrawableHashCode));
        if (this.fitSpace) {
            this.normalFactor = scaleX * 1.0f;
        } else {
            this.normalFactor = 1.0f;
        }
    }

    private void initMax3P2Scal(float scaleX) {
        PhotoLogger.debug(TAG, "is thumbnail = " + (this.currentDrawableHashCode == this.thumbDrawableHashCode));
        if (this.fitSpace) {
            this.normalFactor = scaleX * 1.0f;
            if (this.normalFactor > MAX_FACTOR) {
                this.normalFactor = MAX_FACTOR;
                return;
            }
            return;
        }
        this.normalFactor = 1.0f;
    }

    /* access modifiers changed from: private */
    public void postTranslate(float px, float py) {
        if (!this.photoValid) {
            PhotoLogger.debug(TAG, "invalid photo content");
            return;
        }
        this.matrix.postTranslate(px, py);
        updatePhotoRect();
        applyMatrix();
    }

    private void postScale(float scale) {
        if (!this.photoValid) {
            PhotoLogger.debug(TAG, "invalid photo content");
            return;
        }
        this.scaleFactor *= scale;
        this.matrix.postScale(scale, scale);
        updatePhotoRect();
        applyMatrix();
    }

    /* access modifiers changed from: private */
    public void postScale(float scale, float px, float py) {
        if (!this.photoValid) {
            PhotoLogger.debug(TAG, "invalid photo content");
            return;
        }
        this.scaleFactor *= scale;
        this.matrix.postScale(scale, scale, px, py);
        updatePhotoRect();
        applyMatrix();
    }

    public void postRotate(float degrees) {
        if (!this.photoValid) {
            PhotoLogger.debug(TAG, "invlaid photo content!");
            return;
        }
        Drawable drawable = getDrawable();
        if (drawable instanceof BitmapDrawable) {
            Bitmap origin = ((BitmapDrawable) drawable).getBitmap();
            Matrix m = new Matrix();
            m.postRotate(degrees, (float) (this.drawableWidth / 2), (float) (this.drawableHeight / 2));
            setImageBitmap(Bitmap.createBitmap(origin, 0, 0, this.drawableWidth, this.drawableHeight, m, true));
        }
    }

    public RectF getPhotoRect() {
        return this.photoRect;
    }

    private void updatePhotoRect() {
        this.matrix.getValues(this.matrixValues);
        float left = this.matrixValues[2];
        float top = this.matrixValues[5];
        this.photoRect.set(left, top, left + (this.scaleFactor * ((float) this.drawableWidth)), top + (this.scaleFactor * ((float) this.drawableHeight)));
    }

    public boolean canScrollHorizontally(int direction) {
        if (!this.photoValid) {
            PhotoLogger.debug(TAG, "canScrollHorizontally false");
            return false;
        }
        float horizontalTranslate = this.photoRect.left;
        float photoWidth = this.photoRect.width();
        if (photoWidth <= ((float) this.viewWidth)) {
            return false;
        }
        if (direction < 0 && horizontalTranslate >= 0.0f) {
            return false;
        }
        if (direction <= 0 || horizontalTranslate + photoWidth > ((float) this.viewWidth)) {
            return true;
        }
        return false;
    }

    public void setOnLongClickListener(OnLongClickListener listener) {
        this.onLongClickListener = listener;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    public static int getMaxTextureSize() {
        if (isMaxTexureInited) {
            return maxTextureSize;
        }
        new Canvas().getMaximumBitmapHeight();
        EGL10 egl = (EGL10) EGLContext.getEGL();
        EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl.eglInitialize(display, new int[2]);
        int[] totalConfigurations = new int[1];
        egl.eglGetConfigs(display, null, 0, totalConfigurations);
        EGLConfig[] configurationsList = new EGLConfig[totalConfigurations[0]];
        egl.eglGetConfigs(display, configurationsList, totalConfigurations[0], totalConfigurations);
        int[] textureSize = new int[1];
        int maximumTextureSize = 0;
        for (int i2 = 0; i2 < totalConfigurations[0]; i2++) {
            egl.eglGetConfigAttrib(display, configurationsList[i2], 12330, textureSize);
            if (maximumTextureSize < textureSize[0]) {
                maximumTextureSize = textureSize[0];
            }
        }
        egl.eglTerminate(display);
        PhotoLogger.info(TAG, "getMaxTextureSize = " + Math.max(maximumTextureSize, 2048));
        isMaxTexureInited = true;
        return Math.max(maximumTextureSize, 2048);
    }

    private static void getMaxTextureSizeV2(Canvas canvas) {
        if (!isMaxTexureInited && canvas.isHardwareAccelerated()) {
            int maxTS = Math.min(canvas.getMaximumBitmapHeight(), canvas.getMaximumBitmapWidth());
            if (2048 > maxTS || maxTS >= SKIA_DEFAULT_MAX_TEXURE_SIZE) {
                PhotoLogger.info(TAG, "getMaxTextureSizeV2 size abnormal,handle to  previous method!");
                maxTextureSize = getMaxTextureSize();
            } else {
                maxTextureSize = maxTS;
                PhotoLogger.info(TAG, "getMaxTextureSizeV2 = " + maxTextureSize);
            }
            isMaxTexureInited = true;
        }
    }

    public boolean isShowingThumbnail() {
        return this.currentDrawableHashCode == this.thumbDrawableHashCode;
    }

    public Bitmap getValidShowingPhoto() {
        if (this.currentDrawableHashCode == this.defaultDrawableHashCode || this.currentDrawableHashCode == this.failDrawableHashCode || !(getDrawable() instanceof BitmapDrawable)) {
            return null;
        }
        return ((BitmapDrawable) getDrawable()).getBitmap();
    }
}
