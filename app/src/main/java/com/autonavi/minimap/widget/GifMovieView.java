package com.autonavi.minimap.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import com.autonavi.minimap.R;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class GifMovieView extends View {
    private static final int DEFAULT_MOVIEW_DURATION = 1000;
    static final String TAG = "GifMovieView";
    private int mCurrentAnimationTime;
    private float mLeft;
    private GifHandler mListener;
    private int mMeasuredMovieHeight;
    private int mMeasuredMovieWidth;
    private Movie mMovie;
    private int mMovieResourceId;
    private long mMovieStart;
    private volatile boolean mPaused;
    private boolean mRunOnce;
    private int mRunOverCount;
    private ScaleType mScaleType;
    private float mScaleX;
    private float mScaleY;
    private float mTop;
    private boolean mVisible;

    public enum ErrorType {
        DEFAULT,
        FILENOTFOUND,
        DURATIONZERO
    }

    public static class GifHandler {
        public void onError(ErrorType errorType, String str, Throwable th) {
        }

        public void onRunOver(int i, int i2) {
        }
    }

    public enum ScaleType {
        DEFAULT,
        CENTER_CROP,
        FIT_XY,
        FIT_CENTER
    }

    public GifMovieView(Context context) {
        this(context, null);
    }

    public GifMovieView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.styleable.CustomTheme_gifMovieViewStyle);
    }

    public GifMovieView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentAnimationTime = 0;
        this.mLeft = 0.0f;
        this.mTop = 0.0f;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.mPaused = false;
        this.mVisible = true;
        this.mScaleType = ScaleType.DEFAULT;
        this.mRunOnce = false;
        this.mRunOverCount = 0;
        this.mListener = null;
        try {
            setViewAttributes(context, attributeSet, i);
        } catch (Throwable th) {
            if (this.mListener != null) {
                this.mListener.onError(ErrorType.DEFAULT, null, th);
            }
        }
    }

    @SuppressLint({"NewApi"})
    private void setViewAttributes(Context context, AttributeSet attributeSet, int i) {
        try {
            if (VERSION.SDK_INT >= 11) {
                setLayerType(1, null);
            }
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.GifMovieView, i, R.style.Widget_GifMovieView);
            this.mMovieResourceId = obtainStyledAttributes.getResourceId(R.styleable.GifMovieView_gif, -1);
            this.mPaused = obtainStyledAttributes.getBoolean(R.styleable.GifMovieView_paused, false);
            obtainStyledAttributes.recycle();
            if (this.mMovieResourceId != -1) {
                this.mMovie = Movie.decodeStream(getResources().openRawResource(this.mMovieResourceId));
            }
        } catch (Throwable th) {
            if (this.mListener != null) {
                this.mListener.onError(ErrorType.DEFAULT, null, th);
            }
        }
    }

    public void setListener(GifHandler gifHandler) {
        this.mListener = gifHandler;
    }

    public void setScaleType(ScaleType scaleType) {
        this.mScaleType = scaleType;
    }

    public void setRunOnce(boolean z) {
        this.mRunOnce = z;
    }

    public void setMovie(int i) {
        try {
            this.mMovieResourceId = i;
            this.mMovie = Movie.decodeStream(getResources().openRawResource(this.mMovieResourceId));
            if ((this.mMovie == null || this.mMovie.duration() == 0) && this.mListener != null) {
                this.mListener.onError(ErrorType.DURATIONZERO, null, null);
            }
            requestLayout();
        } catch (Throwable th) {
            if (this.mListener != null) {
                this.mListener.onError(ErrorType.DEFAULT, null, th);
            }
        }
    }

    public void setMovie(Movie movie) {
        try {
            this.mMovie = movie;
            if ((this.mMovie == null || this.mMovie.duration() == 0) && this.mListener != null) {
                this.mListener.onError(ErrorType.DURATIONZERO, null, null);
            }
            requestLayout();
        } catch (Throwable th) {
            if (this.mListener != null) {
                this.mListener.onError(ErrorType.DEFAULT, null, th);
            }
        }
    }

    public void setMovie(String str) {
        InputStream inputStream;
        try {
            if (new File(str).exists()) {
                this.mMovie = Movie.decodeFile(str);
                if (this.mMovie == null || this.mMovie.duration() == 0) {
                    String.format("try load gif by decodeStream", new Object[0]);
                    inputStream = new BufferedInputStream(new FileInputStream(str), 16384);
                    inputStream.mark(16384);
                    if (inputStream != null) {
                        this.mMovie = Movie.decodeStream(inputStream);
                    }
                }
                if ((this.mMovie == null || this.mMovie.duration() == 0) && this.mListener != null) {
                    this.mListener.onError(ErrorType.DURATIONZERO, null, null);
                }
                requestLayout();
                return;
            }
            if (this.mListener != null) {
                this.mListener.onError(ErrorType.FILENOTFOUND, null, null);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            inputStream = null;
        } catch (Throwable th) {
            if (this.mListener != null) {
                this.mListener.onError(ErrorType.DEFAULT, null, th);
            }
        }
    }

    public Movie getMovie() {
        return this.mMovie;
    }

    public void setMovieTime(int i) {
        try {
            this.mCurrentAnimationTime = i;
            invalidate();
        } catch (Throwable th) {
            if (this.mListener != null) {
                this.mListener.onError(ErrorType.DEFAULT, null, th);
            }
        }
    }

    public void setPaused(boolean z) {
        try {
            this.mPaused = z;
            if (!z) {
                this.mMovieStart = SystemClock.uptimeMillis() - ((long) this.mCurrentAnimationTime);
                invalidate();
            }
        } catch (Throwable th) {
            if (this.mListener != null) {
                this.mListener.onError(ErrorType.DEFAULT, null, th);
            }
        }
    }

    public boolean isPaused() {
        return this.mPaused;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0037 A[Catch:{ Throwable -> 0x0088 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004f A[Catch:{ Throwable -> 0x0088 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r5, int r6) {
        /*
            r4 = this;
            android.graphics.Movie r0 = r4.mMovie     // Catch:{ Throwable -> 0x0088 }
            if (r0 == 0) goto L_0x007c
            android.graphics.Movie r0 = r4.mMovie     // Catch:{ Throwable -> 0x0088 }
            int r0 = r0.width()     // Catch:{ Throwable -> 0x0088 }
            android.graphics.Movie r1 = r4.mMovie     // Catch:{ Throwable -> 0x0088 }
            int r1 = r1.height()     // Catch:{ Throwable -> 0x0088 }
            if (r0 <= 0) goto L_0x0070
            if (r1 <= 0) goto L_0x0070
            com.autonavi.minimap.widget.GifMovieView$ScaleType r2 = r4.mScaleType     // Catch:{ Throwable -> 0x0088 }
            com.autonavi.minimap.widget.GifMovieView$ScaleType r3 = com.autonavi.minimap.widget.GifMovieView.ScaleType.DEFAULT     // Catch:{ Throwable -> 0x0088 }
            if (r2 != r3) goto L_0x006c
            int r2 = android.view.View.MeasureSpec.getMode(r5)     // Catch:{ Throwable -> 0x0088 }
            r3 = 1065353216(0x3f800000, float:1.0)
            if (r2 == 0) goto L_0x002f
            int r5 = android.view.View.MeasureSpec.getSize(r5)     // Catch:{ Throwable -> 0x0088 }
            if (r0 <= r5) goto L_0x002f
            if (r5 <= 0) goto L_0x002f
            float r2 = (float) r0     // Catch:{ Throwable -> 0x0088 }
            float r5 = (float) r5     // Catch:{ Throwable -> 0x0088 }
            float r5 = r2 / r5
            goto L_0x0031
        L_0x002f:
            r5 = 1065353216(0x3f800000, float:1.0)
        L_0x0031:
            int r2 = android.view.View.MeasureSpec.getMode(r6)     // Catch:{ Throwable -> 0x0088 }
            if (r2 == 0) goto L_0x0044
            int r6 = android.view.View.MeasureSpec.getSize(r6)     // Catch:{ Throwable -> 0x0088 }
            if (r1 <= r6) goto L_0x0044
            if (r6 <= 0) goto L_0x0044
            float r2 = (float) r1     // Catch:{ Throwable -> 0x0088 }
            float r6 = (float) r6     // Catch:{ Throwable -> 0x0088 }
            float r6 = r2 / r6
            goto L_0x0046
        L_0x0044:
            r6 = 1065353216(0x3f800000, float:1.0)
        L_0x0046:
            float r5 = java.lang.Math.max(r5, r6)     // Catch:{ Throwable -> 0x0088 }
            r6 = 0
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x0054
            float r3 = r3 / r5
            r4.mScaleY = r3     // Catch:{ Throwable -> 0x0088 }
            r4.mScaleX = r3     // Catch:{ Throwable -> 0x0088 }
        L_0x0054:
            float r5 = (float) r0     // Catch:{ Throwable -> 0x0088 }
            float r6 = r4.mScaleX     // Catch:{ Throwable -> 0x0088 }
            float r5 = r5 * r6
            int r5 = (int) r5     // Catch:{ Throwable -> 0x0088 }
            r4.mMeasuredMovieWidth = r5     // Catch:{ Throwable -> 0x0088 }
            float r5 = (float) r1     // Catch:{ Throwable -> 0x0088 }
            float r6 = r4.mScaleY     // Catch:{ Throwable -> 0x0088 }
            float r5 = r5 * r6
            int r5 = (int) r5     // Catch:{ Throwable -> 0x0088 }
            r4.mMeasuredMovieHeight = r5     // Catch:{ Throwable -> 0x0088 }
            int r5 = r4.mMeasuredMovieWidth     // Catch:{ Throwable -> 0x0088 }
            int r6 = r4.mMeasuredMovieHeight     // Catch:{ Throwable -> 0x0088 }
            r4.setMeasuredDimension(r5, r6)     // Catch:{ Throwable -> 0x0088 }
            return
        L_0x006c:
            super.onMeasure(r5, r6)     // Catch:{ Throwable -> 0x0088 }
            return
        L_0x0070:
            int r5 = r4.getSuggestedMinimumWidth()     // Catch:{ Throwable -> 0x0088 }
            int r6 = r4.getSuggestedMinimumHeight()     // Catch:{ Throwable -> 0x0088 }
            r4.setMeasuredDimension(r5, r6)     // Catch:{ Throwable -> 0x0088 }
            return
        L_0x007c:
            int r5 = r4.getSuggestedMinimumWidth()     // Catch:{ Throwable -> 0x0088 }
            int r6 = r4.getSuggestedMinimumHeight()     // Catch:{ Throwable -> 0x0088 }
            r4.setMeasuredDimension(r5, r6)     // Catch:{ Throwable -> 0x0088 }
            return
        L_0x0088:
            r5 = move-exception
            com.autonavi.minimap.widget.GifMovieView$GifHandler r6 = r4.mListener
            if (r6 == 0) goto L_0x0095
            com.autonavi.minimap.widget.GifMovieView$GifHandler r6 = r4.mListener
            com.autonavi.minimap.widget.GifMovieView$ErrorType r0 = com.autonavi.minimap.widget.GifMovieView.ErrorType.DEFAULT
            r1 = 0
            r6.onError(r0, r1, r5)
        L_0x0095:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.widget.GifMovieView.onMeasure(int, int):void");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0061 A[Catch:{ Throwable -> 0x0089 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onLayout(boolean r7, int r8, int r9, int r10, int r11) {
        /*
            r6 = this;
            super.onLayout(r7, r8, r9, r10, r11)     // Catch:{ Throwable -> 0x0089 }
            android.graphics.Movie r7 = r6.mMovie     // Catch:{ Throwable -> 0x0089 }
            r8 = 0
            r9 = 1
            if (r7 == 0) goto L_0x007f
            int r7 = r6.getWidth()     // Catch:{ Throwable -> 0x0089 }
            int r10 = r6.getHeight()     // Catch:{ Throwable -> 0x0089 }
            android.graphics.Movie r11 = r6.mMovie     // Catch:{ Throwable -> 0x0089 }
            int r11 = r11.width()     // Catch:{ Throwable -> 0x0089 }
            android.graphics.Movie r0 = r6.mMovie     // Catch:{ Throwable -> 0x0089 }
            int r0 = r0.height()     // Catch:{ Throwable -> 0x0089 }
            if (r7 <= 0) goto L_0x007f
            if (r10 <= 0) goto L_0x007f
            if (r11 <= 0) goto L_0x007f
            if (r0 <= 0) goto L_0x007f
            float r11 = (float) r11     // Catch:{ Throwable -> 0x0089 }
            float r1 = (float) r7     // Catch:{ Throwable -> 0x0089 }
            float r1 = r11 / r1
            float r0 = (float) r0     // Catch:{ Throwable -> 0x0089 }
            float r2 = (float) r10     // Catch:{ Throwable -> 0x0089 }
            float r2 = r0 / r2
            com.autonavi.minimap.widget.GifMovieView$ScaleType r3 = r6.mScaleType     // Catch:{ Throwable -> 0x0089 }
            com.autonavi.minimap.widget.GifMovieView$ScaleType r4 = com.autonavi.minimap.widget.GifMovieView.ScaleType.FIT_XY     // Catch:{ Throwable -> 0x0089 }
            r5 = 1065353216(0x3f800000, float:1.0)
            if (r3 != r4) goto L_0x003d
            float r1 = r5 / r1
            r6.mScaleX = r1     // Catch:{ Throwable -> 0x0089 }
            float r5 = r5 / r2
            r6.mScaleY = r5     // Catch:{ Throwable -> 0x0089 }
            goto L_0x005c
        L_0x003d:
            com.autonavi.minimap.widget.GifMovieView$ScaleType r3 = r6.mScaleType     // Catch:{ Throwable -> 0x0089 }
            com.autonavi.minimap.widget.GifMovieView$ScaleType r4 = com.autonavi.minimap.widget.GifMovieView.ScaleType.CENTER_CROP     // Catch:{ Throwable -> 0x0089 }
            if (r3 != r4) goto L_0x004d
            float r1 = java.lang.Math.min(r1, r2)     // Catch:{ Throwable -> 0x0089 }
            float r5 = r5 / r1
            r6.mScaleY = r5     // Catch:{ Throwable -> 0x0089 }
            r6.mScaleX = r5     // Catch:{ Throwable -> 0x0089 }
            goto L_0x005c
        L_0x004d:
            com.autonavi.minimap.widget.GifMovieView$ScaleType r3 = r6.mScaleType     // Catch:{ Throwable -> 0x0089 }
            com.autonavi.minimap.widget.GifMovieView$ScaleType r4 = com.autonavi.minimap.widget.GifMovieView.ScaleType.FIT_CENTER     // Catch:{ Throwable -> 0x0089 }
            if (r3 != r4) goto L_0x005e
            float r1 = java.lang.Math.max(r1, r2)     // Catch:{ Throwable -> 0x0089 }
            float r5 = r5 / r1
            r6.mScaleY = r5     // Catch:{ Throwable -> 0x0089 }
            r6.mScaleX = r5     // Catch:{ Throwable -> 0x0089 }
        L_0x005c:
            r1 = 1
            goto L_0x005f
        L_0x005e:
            r1 = 0
        L_0x005f:
            if (r1 == 0) goto L_0x006f
            float r1 = r6.mScaleX     // Catch:{ Throwable -> 0x0089 }
            float r11 = r11 * r1
            int r11 = (int) r11     // Catch:{ Throwable -> 0x0089 }
            r6.mMeasuredMovieWidth = r11     // Catch:{ Throwable -> 0x0089 }
            float r11 = r6.mScaleY     // Catch:{ Throwable -> 0x0089 }
            float r0 = r0 * r11
            int r11 = (int) r0     // Catch:{ Throwable -> 0x0089 }
            r6.mMeasuredMovieHeight = r11     // Catch:{ Throwable -> 0x0089 }
        L_0x006f:
            int r11 = r6.mMeasuredMovieWidth     // Catch:{ Throwable -> 0x0089 }
            int r7 = r7 - r11
            float r7 = (float) r7     // Catch:{ Throwable -> 0x0089 }
            r11 = 1073741824(0x40000000, float:2.0)
            float r7 = r7 / r11
            r6.mLeft = r7     // Catch:{ Throwable -> 0x0089 }
            int r7 = r6.mMeasuredMovieHeight     // Catch:{ Throwable -> 0x0089 }
            int r10 = r10 - r7
            float r7 = (float) r10     // Catch:{ Throwable -> 0x0089 }
            float r7 = r7 / r11
            r6.mTop = r7     // Catch:{ Throwable -> 0x0089 }
        L_0x007f:
            int r7 = r6.getVisibility()     // Catch:{ Throwable -> 0x0089 }
            if (r7 != 0) goto L_0x0086
            r8 = 1
        L_0x0086:
            r6.mVisible = r8     // Catch:{ Throwable -> 0x0089 }
            return
        L_0x0089:
            r7 = move-exception
            com.autonavi.minimap.widget.GifMovieView$GifHandler r8 = r6.mListener
            if (r8 == 0) goto L_0x0096
            com.autonavi.minimap.widget.GifMovieView$GifHandler r8 = r6.mListener
            com.autonavi.minimap.widget.GifMovieView$ErrorType r9 = com.autonavi.minimap.widget.GifMovieView.ErrorType.DEFAULT
            r10 = 0
            r8.onError(r9, r10, r7)
        L_0x0096:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.widget.GifMovieView.onLayout(boolean, int, int, int, int):void");
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        try {
            if (this.mMovie != null) {
                if (this.mPaused || this.mMovie.duration() <= 0) {
                    drawMovieFrame(canvas);
                } else {
                    updateAnimationTime();
                    drawMovieFrame(canvas);
                    invalidateView();
                }
            }
        } catch (Throwable th) {
            if (this.mListener != null) {
                this.mListener.onError(ErrorType.DEFAULT, null, th);
            }
        }
    }

    @SuppressLint({"NewApi"})
    private void invalidateView() {
        try {
            if (this.mVisible) {
                if (VERSION.SDK_INT >= 16) {
                    postInvalidateOnAnimation();
                    return;
                }
                invalidate();
            }
        } catch (Throwable th) {
            if (this.mListener != null) {
                this.mListener.onError(ErrorType.DEFAULT, null, th);
            }
        }
    }

    private void updateAnimationTime() {
        try {
            if (this.mMovie != null && this.mMovie.duration() > 0) {
                long uptimeMillis = SystemClock.uptimeMillis();
                if (this.mMovieStart == 0) {
                    this.mMovieStart = uptimeMillis;
                }
                int duration = this.mMovie.duration();
                if (duration == 0) {
                    duration = 1000;
                }
                if (!this.mRunOnce || uptimeMillis - this.mMovieStart < ((long) duration)) {
                    this.mCurrentAnimationTime = (int) ((uptimeMillis - this.mMovieStart) % ((long) duration));
                } else {
                    this.mCurrentAnimationTime = duration;
                    setPaused(true);
                }
                int i = ((int) (uptimeMillis - this.mMovieStart)) / duration;
                if (i > this.mRunOverCount) {
                    this.mRunOverCount = i;
                    if (this.mListener != null) {
                        this.mListener.onRunOver(this.mRunOverCount, duration);
                    }
                }
            }
        } catch (Throwable th) {
            if (this.mListener != null) {
                this.mListener.onError(ErrorType.DEFAULT, null, th);
            }
        }
    }

    private void drawMovieFrame(Canvas canvas) {
        try {
            if (this.mMovie != null && this.mScaleX > 0.0f && this.mScaleY > 0.0f) {
                this.mMovie.setTime(this.mCurrentAnimationTime);
                canvas.save();
                canvas.scale(this.mScaleX, this.mScaleY);
                this.mMovie.draw(canvas, this.mLeft / this.mScaleX, this.mTop / this.mScaleY);
                canvas.restore();
            }
        } catch (Throwable th) {
            if (this.mListener != null) {
                this.mListener.onError(ErrorType.DEFAULT, null, th);
            }
        }
    }

    @SuppressLint({"NewApi"})
    public void onScreenStateChanged(int i) {
        try {
            super.onScreenStateChanged(i);
            boolean z = true;
            if (i != 1) {
                z = false;
            }
            this.mVisible = z;
            invalidateView();
        } catch (Throwable th) {
            if (this.mListener != null) {
                this.mListener.onError(ErrorType.DEFAULT, null, th);
            }
        }
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"NewApi"})
    public void onVisibilityChanged(View view, int i) {
        try {
            super.onVisibilityChanged(view, i);
            this.mVisible = i == 0;
            invalidateView();
        } catch (Throwable th) {
            if (this.mListener != null) {
                this.mListener.onError(ErrorType.DEFAULT, null, th);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int i) {
        try {
            super.onWindowVisibilityChanged(i);
            this.mVisible = i == 0;
            invalidateView();
        } catch (Throwable th) {
            if (this.mListener != null) {
                this.mListener.onError(ErrorType.DEFAULT, null, th);
            }
        }
    }
}
