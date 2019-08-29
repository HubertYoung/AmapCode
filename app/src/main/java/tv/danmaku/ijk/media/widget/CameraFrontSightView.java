package tv.danmaku.ijk.media.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

class CameraFrontSightView extends View {
    private boolean mDoFading = false;
    private boolean mDoScaling = false;
    private int mHalfHeight;
    private int mHalfWidth;
    protected int mHeight;
    private LayoutParams mLayoutParams;
    private int mLineWidth;
    private Paint mPaint = new Paint();
    private long mTime = 0;
    protected int mWidth;

    public CameraFrontSightView(Context context) {
        super(context);
    }

    public CameraFrontSightView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public CameraFrontSightView(Context context, AttributeSet attr, int style) {
        super(context, attr, style);
    }

    public final void startDraw() {
        setVisibility(0);
        this.mDoScaling = true;
        this.mDoFading = false;
        this.mTime = System.currentTimeMillis();
        invalidate();
    }

    public final void init(int w, int h) {
        this.mWidth = dp2Px((float) w);
        this.mHeight = dp2Px((float) h);
        this.mLayoutParams = getLayoutParams();
        if (this.mLayoutParams != null) {
            this.mLayoutParams.width = this.mWidth;
            this.mLayoutParams.height = this.mHeight;
        }
        setLayoutParams(this.mLayoutParams);
        this.mHalfWidth = this.mWidth / 2;
        this.mHalfHeight = this.mHeight / 2;
        this.mLineWidth = dp2Px(1.0f);
        this.mPaint.setColor(-8393929);
        this.mPaint.setStrokeWidth((float) this.mLineWidth);
    }

    public void draw(Canvas canvas) {
        canvas.translate((float) (this.mHalfWidth / 2), (float) (this.mHalfHeight / 2));
        long timeElapse = System.currentTimeMillis() - this.mTime;
        if (timeElapse > 200) {
            this.mDoScaling = false;
        }
        if (timeElapse > 1100) {
            this.mDoFading = true;
        }
        if (timeElapse > 1300) {
            setVisibility(8);
            return;
        }
        if (this.mDoScaling) {
            float f1 = 1.0f + (((float) (200 - timeElapse)) / 200.0f);
            canvas.scale(f1, f1, (float) (this.mHalfWidth / 2), (float) (this.mHalfHeight / 2));
            this.mPaint.setAlpha((int) (255.0f * (2.0f - f1)));
        }
        if (this.mDoFading) {
            this.mPaint.setAlpha((int) (255.0f * ((1300.0f - ((float) timeElapse)) / 200.0f)));
        }
        canvas.drawLine(0.0f, 0.0f, (float) this.mHalfWidth, 0.0f, this.mPaint);
        canvas.drawLine(0.0f, 0.0f, 0.0f, (float) this.mHalfHeight, this.mPaint);
        canvas.drawLine((float) this.mHalfWidth, 0.0f, (float) this.mHalfWidth, (float) this.mHalfHeight, this.mPaint);
        canvas.drawLine(0.0f, (float) this.mHalfHeight, (float) this.mHalfWidth, (float) this.mHalfHeight, this.mPaint);
        canvas.drawLine(0.0f, (float) (this.mHalfHeight / 2), (float) (this.mHalfWidth / 10), (float) (this.mHalfHeight / 2), this.mPaint);
        canvas.drawLine((float) this.mHalfWidth, (float) (this.mHalfHeight / 2), (float) ((this.mHalfWidth * 9) / 10), (float) (this.mHalfHeight / 2), this.mPaint);
        canvas.drawLine((float) (this.mHalfWidth / 2), 0.0f, (float) (this.mHalfWidth / 2), (float) (this.mHalfHeight / 10), this.mPaint);
        canvas.drawLine((float) (this.mHalfWidth / 2), (float) this.mHalfHeight, (float) (this.mHalfWidth / 2), (float) ((this.mHalfHeight * 9) / 10), this.mPaint);
        invalidate();
    }

    private int dp2Px(float dp) {
        return (int) ((dp * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }
}
