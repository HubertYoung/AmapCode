package com.alipay.mobile.beehive.photo.view.video;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;

public class VideoWindowView extends View {
    private static final int MASK_COLOR = Color.parseColor("#80000000");
    private static final int PROGRESS_LINE_COLOR = Color.parseColor("#99FFFFFF");
    private static final String TAG = "VideoWindowView";
    private Runnable doMeasureJob;
    private boolean isNeedDrag;
    private a mDirection;
    private b mDragStatusListener;
    /* access modifiers changed from: private */
    public int mDuration;
    private int mMaxCutDuration;
    /* access modifiers changed from: private */
    public c mMeasureFinishListener;
    private int mPlayingProgress;
    /* access modifiers changed from: private */
    public Rect mPlayingProgressBounds;
    private ColorDrawable mProgressLine;
    private int mProgressLineWidth;
    /* access modifiers changed from: private */
    public NinePatchDrawable mRectDrawable;
    /* access modifiers changed from: private */
    public Rect mWindowBounds;
    private NinePatchDrawable mWindowDrawable;
    private Paint mWindowEraserPaint;
    /* access modifiers changed from: private */
    public int mWindowMarginTopAndBottom;
    /* access modifiers changed from: private */
    public int minDurationWidth;
    /* access modifiers changed from: private */
    public int unitWidth;
    /* access modifiers changed from: private */
    public int widthRecord;

    enum a {
        NONE,
        LEFT,
        RIGHT
    }

    interface b {
        void a();

        void b();
    }

    interface c {
        void a(int i, int i2, int i3, float f);
    }

    public VideoWindowView(Context context) {
        this(context, null);
    }

    public VideoWindowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoWindowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.doMeasureJob = new Runnable() {
            public final void run() {
                int w = VideoWindowView.this.getWidth();
                int h = VideoWindowView.this.getHeight();
                if (w > 0 && h > 0 && w != VideoWindowView.this.widthRecord && VideoWindowView.this.mMeasureFinishListener != null) {
                    PhotoLogger.d(VideoWindowView.TAG, "w = " + w + " h= " + h);
                    VideoWindowView.this.widthRecord = w;
                    VideoWindowView.this.unitWidth = w / 12;
                    float msPerPixel = VideoWindowView.this.calculateMsPerPixel(VideoWindowView.this.mDuration, w);
                    VideoWindowView.this.minDurationWidth = VideoWindowView.this.calculateMinDurationWidth(msPerPixel, VideoWindowView.this.unitWidth);
                    VideoWindowView.this.mWindowBounds = new Rect(VideoWindowView.this.unitWidth, 0, VideoWindowView.this.unitWidth * 11, h);
                    VideoWindowView.this.mRectDrawable.setBounds(new Rect(VideoWindowView.this.unitWidth, VideoWindowView.this.mWindowMarginTopAndBottom, VideoWindowView.this.unitWidth * 11, h - VideoWindowView.this.mWindowMarginTopAndBottom));
                    VideoWindowView.this.mPlayingProgressBounds = new Rect(0, 0, 3, h);
                    VideoWindowView.this.mMeasureFinishListener.a(w, VideoWindowView.this.unitWidth, h, msPerPixel);
                }
            }
        };
        this.mProgressLineWidth = (int) Math.ceil((double) getResources().getDimension(R.dimen.di_video_cut_progress_line_width));
        this.mWindowMarginTopAndBottom = (int) getResources().getDimension(R.dimen.di_video_list_margin);
        this.mWindowDrawable = (NinePatchDrawable) getResources().getDrawable(R.drawable.ic_video_cut_window);
        this.mRectDrawable = (NinePatchDrawable) getResources().getDrawable(R.drawable.ic_video_cut_window_bg);
        this.mWindowEraserPaint = new Paint();
        this.mWindowEraserPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        this.mProgressLine = new ColorDrawable(PROGRESS_LINE_COLOR);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mDuration > 0) {
            post(this.doMeasureJob);
        }
    }

    /* access modifiers changed from: private */
    public int calculateMinDurationWidth(float msPerPixel, int unitWidth2) {
        return Math.max((int) (1000.0f / msPerPixel), unitWidth2);
    }

    /* access modifiers changed from: private */
    public float calculateMsPerPixel(int duration, int width) {
        if (duration < this.mMaxCutDuration) {
            return (((float) duration) * 12.0f) / ((float) (width * 10));
        }
        return (((float) this.mMaxCutDuration) * 12.0f) / ((float) (width * 10));
    }

    public void setOnMeasureFinishListener(c listener) {
        this.mMeasureFinishListener = listener;
    }

    public int getProgress() {
        return this.mPlayingProgress;
    }

    public void setOnDragWindowStatusChangeListener(b listener) {
        this.mDragStatusListener = listener;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDuration > 0 && this.mWindowBounds != null) {
            drawVideoWindow(canvas);
            drawProgress(canvas);
        }
    }

    private void drawProgress(Canvas canvas) {
        canvas.save();
        if (this.mPlayingProgress > 0) {
            this.mPlayingProgressBounds.left = this.mWindowBounds.left + ((int) ((((float) this.mPlayingProgress) * ((float) (this.mWindowBounds.right - this.mWindowBounds.left))) / 100.0f));
            this.mPlayingProgressBounds.right = this.mPlayingProgressBounds.left + this.mProgressLineWidth;
            this.mProgressLine.setBounds(this.mPlayingProgressBounds);
            if (this.mPlayingProgressBounds.left >= this.mWindowBounds.left + this.mProgressLineWidth && this.mPlayingProgressBounds.right <= this.mWindowBounds.right - this.mProgressLineWidth) {
                this.mProgressLine.draw(canvas);
            } else {
                return;
            }
        }
        canvas.restore();
    }

    private void drawVideoWindow(Canvas canvas) {
        canvas.saveLayer(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), null, 31);
        canvas.drawColor(MASK_COLOR);
        if (this.isNeedDrag) {
            this.mRectDrawable.draw(canvas);
        }
        canvas.drawRect(this.mWindowBounds, this.mWindowEraserPaint);
        this.mWindowDrawable.setBounds(this.mWindowBounds);
        this.mWindowDrawable.draw(canvas);
        canvas.restore();
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean z = false;
        switch (event.getAction()) {
            case 0:
                this.mDirection = pendingDragDirection((int) event.getX());
                if (this.mDirection != a.NONE) {
                    z = true;
                }
                this.isNeedDrag = z;
                if (this.isNeedDrag) {
                    invalidate();
                    if (this.mDragStatusListener != null) {
                        this.mDragStatusListener.a();
                    }
                }
                return this.isNeedDrag;
            case 1:
                if (this.isNeedDrag && this.mDragStatusListener != null) {
                    this.mDragStatusListener.b();
                }
                this.isNeedDrag = false;
                invalidate();
                break;
            case 2:
                performDrag(event);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void performDrag(MotionEvent event) {
        int x = (int) event.getX();
        int maxX = getWidth() - this.unitWidth;
        if (x < this.unitWidth) {
            x = this.unitWidth;
        } else if (x > maxX) {
            x = maxX;
        }
        if (this.mDirection == a.LEFT) {
            if (this.minDurationWidth + x > this.mWindowBounds.right) {
                x = this.mWindowBounds.right - this.minDurationWidth;
            }
            this.mWindowBounds.left = x;
        } else {
            if (x - this.minDurationWidth < this.mWindowBounds.left) {
                x = this.mWindowBounds.left + this.minDurationWidth;
            }
            this.mWindowBounds.right = x;
        }
        invalidate();
    }

    private a pendingDragDirection(int x) {
        int halfUnitWidth = this.unitWidth / 2;
        if (this.mWindowBounds.left - halfUnitWidth <= x && this.mWindowBounds.left + halfUnitWidth > x) {
            return a.LEFT;
        }
        if (this.mWindowBounds.right - halfUnitWidth > x || this.mWindowBounds.right + halfUnitWidth <= x) {
            return a.NONE;
        }
        return a.RIGHT;
    }

    public void setVideoDuration(int duration, int maxCutDuration) {
        PhotoLogger.d(TAG, "setVideoDuration:### duration=" + duration + ",maxCutDuration=" + maxCutDuration);
        if (duration < 0) {
            PhotoLogger.d(TAG, "Invalid video duration,which = " + duration);
            return;
        }
        this.mDuration = duration;
        this.mMaxCutDuration = maxCutDuration;
        requestLayout();
    }

    public int getLeftEdgeShift() {
        return this.mWindowBounds.left - this.unitWidth;
    }

    public int getWindowWidth() {
        return this.mWindowBounds.right - this.mWindowBounds.left;
    }

    public void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        }
        if (progress > 100) {
            progress = 100;
        }
        this.mPlayingProgress = progress;
        invalidate();
    }
}
