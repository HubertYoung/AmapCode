package com.autonavi.bundle.uitemplate.mapwidget.widget.compass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.autonavi.minimap.R;

@SuppressLint({"AppCompatCustomView"})
public class UICompassView extends ImageView {
    private Drawable mBackGroundDrawable = getResources().getDrawable(R.drawable.map_widget_compass_icon);
    private int mBackGroundHeight;
    private int mBackGroundWidth;
    private int mCompassBitmapID = 0;
    private CompassAngleChangeListener mListener;
    private float mMapAngle = -1.0f;
    private float mMapCamera = -1.0f;

    public interface CompassAngleChangeListener {
        void onAngleChanged(float f, float f2);
    }

    private boolean isZero(int i, int i2) {
        return (i >= 359 || i <= 1) && i2 < 2;
    }

    public UICompassView(Context context) {
        super(context);
        setContentDescription(getResources().getString(R.string.compass));
    }

    public UICompassView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setContentDescription(getResources().getString(R.string.compass));
    }

    public void paintCompass(float f, float f2) {
        if (!(this.mMapAngle == f2 && this.mMapCamera == f)) {
            this.mMapAngle = f2;
            this.mMapCamera = f;
            try {
                postInvalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setCompassRes(int i) {
        this.mCompassBitmapID = i;
        setImageResource(i);
        this.mBackGroundDrawable = getResources().getDrawable(this.mCompassBitmapID);
        postInvalidate();
    }

    public void setAngleListener(CompassAngleChangeListener compassAngleChangeListener) {
        this.mListener = compassAngleChangeListener;
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"NewApi"})
    public void onDraw(Canvas canvas) {
        if (this.mCompassBitmapID != 0) {
            if (this.mListener != null) {
                this.mListener.onAngleChanged(this.mMapAngle, this.mMapCamera);
            }
            canvas.rotate(-this.mMapAngle, (float) (this.mBackGroundWidth / 2), (float) (this.mBackGroundHeight / 2));
            this.mBackGroundDrawable.setBounds(0, 0, this.mBackGroundWidth, this.mBackGroundHeight);
            this.mBackGroundDrawable.draw(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        this.mBackGroundHeight = this.mBackGroundDrawable.getMinimumHeight();
        this.mBackGroundWidth = this.mBackGroundDrawable.getMinimumWidth();
        setMeasuredDimension(this.mBackGroundWidth, this.mBackGroundHeight);
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
    }
}
