package com.autonavi.map.suspend.refactor.compass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.autonavi.minimap.R;

public class UICompassWidget extends ImageView {
    private Drawable mBackGroudDrawable = getResources().getDrawable(R.drawable.icon_c22);
    private int mBackGroundHeight;
    private int mBackGroundWidth;
    private int mCompassBitmapID = 0;
    private cdj mListener;
    private float mMapAngle = -1.0f;
    private float mMapCamera = -1.0f;
    private bty mMapView;

    private boolean isZero(int i, int i2) {
        return (i >= 359 || i <= 1) && i2 < 2;
    }

    public UICompassWidget(Context context) {
        super(context);
        setContentDescription(getResources().getString(R.string.compass));
    }

    public UICompassWidget(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setContentDescription(getResources().getString(R.string.compass));
    }

    public void attachMapView(bty bty) {
        this.mMapView = bty;
    }

    public void paintCompass() {
        float f;
        float f2 = 0.0f;
        if (this.mMapView != null) {
            f2 = this.mMapView.J();
            f = this.mMapView.I();
        } else {
            f = 0.0f;
        }
        if (!(this.mMapAngle == f && this.mMapCamera == f2)) {
            this.mMapAngle = f;
            this.mMapCamera = f2;
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
        this.mBackGroudDrawable = getResources().getDrawable(this.mCompassBitmapID);
        postInvalidate();
    }

    public void setAngleListener(cdj cdj) {
        this.mListener = cdj;
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"NewApi"})
    public void onDraw(Canvas canvas) {
        if (this.mCompassBitmapID != 0) {
            if (this.mListener != null) {
                this.mListener.a();
            }
            canvas.rotate(-this.mMapAngle, (float) (this.mBackGroundWidth / 2), (float) (this.mBackGroundHeight / 2));
            this.mBackGroudDrawable.setBounds(0, 0, this.mBackGroundWidth, this.mBackGroundHeight);
            this.mBackGroudDrawable.draw(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mBackGroundHeight = this.mBackGroudDrawable.getMinimumHeight();
        this.mBackGroundWidth = this.mBackGroudDrawable.getMinimumWidth();
        setMeasuredDimension(this.mBackGroundWidth, this.mBackGroundHeight);
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
    }
}
