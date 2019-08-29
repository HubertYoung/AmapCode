package com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View.OnLayoutChangeListener;
import android.widget.LinearLayout;
import com.autonavi.minimap.R;

public class ZoomBoardViewBg extends LinearLayout {
    private int MAX_CORNER;
    private int MIN_CORNER;
    private int SHADOW_PADDING;
    private int SHADOW_X_DETA;
    private int SHADOW_Y_DETA;
    private int STROKE;
    private Paint mBackgroundPaint;
    private boolean mSelected;
    private Paint mSelectedBgPaint;
    private Paint mShadowPaint;

    public void addOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
        super.addOnLayoutChangeListener(onLayoutChangeListener);
    }

    public ZoomBoardViewBg(Context context) {
        super(context);
        initBgPaint();
    }

    public ZoomBoardViewBg(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initBgPaint();
    }

    public ZoomBoardViewBg(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initBgPaint();
    }

    @TargetApi(21)
    public ZoomBoardViewBg(Context context, @Nullable AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        initBgPaint();
    }

    private void initBgPaint() {
        setWillNotDraw(false);
        this.MIN_CORNER = agn.a(getContext(), 22.0f);
        this.MAX_CORNER = agn.a(getContext(), 32.0f);
        this.STROKE = agn.a(getContext(), 2.0f);
        this.SHADOW_PADDING = agn.a(getContext(), 3.0f);
        this.SHADOW_Y_DETA = agn.a(getContext(), 1.0f);
        setMinimumHeight((this.MIN_CORNER * 2) + (this.SHADOW_PADDING * 2));
        this.mBackgroundPaint = new Paint();
        this.mBackgroundPaint.setColor(getResources().getColor(R.color.f_c_1));
        this.mBackgroundPaint.setAntiAlias(true);
        this.mSelectedBgPaint = new Paint();
        this.mSelectedBgPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
        this.mSelectedBgPaint.setColor(getResources().getColor(R.color.c_12));
        this.mSelectedBgPaint.setAntiAlias(true);
        this.mShadowPaint = new Paint();
        this.mShadowPaint.setColor(getResources().getColor(R.color.c_t));
        this.mShadowPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
        this.mShadowPaint.setShadowLayer((float) this.SHADOW_PADDING, (float) this.SHADOW_X_DETA, (float) this.SHADOW_Y_DETA, Color.argb(64, 0, 0, 0));
        this.mShadowPaint.setAntiAlias(true);
        setLayerType(1, null);
    }

    public void setSelectItem(boolean z) {
        this.mSelected = z;
    }

    public boolean getSelectItem() {
        return this.mSelected;
    }

    public void draw(Canvas canvas) {
        getLeft();
        float bottom = (float) getBottom();
        canvas.saveLayer(0.0f, 0.0f, (float) getRight(), bottom, null, 31);
        super.draw(canvas);
        float top = ((bottom - ((float) getTop())) / 2.0f) - ((float) this.SHADOW_PADDING);
        if (top < ((float) this.MIN_CORNER)) {
            top = (float) this.MIN_CORNER;
        } else if (top > ((float) this.MAX_CORNER)) {
            top = (float) this.MAX_CORNER;
        }
        int i = this.mSelected ? this.STROKE : 0;
        RectF rectF = new RectF((float) (this.SHADOW_PADDING + i), (float) (this.SHADOW_PADDING + i), (float) ((getWidth() - i) - this.SHADOW_PADDING), (float) ((getHeight() - i) - this.SHADOW_PADDING));
        this.mBackgroundPaint.setColor(getResources().getColor(R.color.f_c_1));
        this.mBackgroundPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
        canvas.drawRoundRect(rectF, top, top, this.mBackgroundPaint);
        RectF rectF2 = new RectF((float) this.SHADOW_PADDING, (float) this.SHADOW_PADDING, (float) (getWidth() - this.SHADOW_PADDING), (float) (getHeight() - this.SHADOW_PADDING));
        if (this.mSelected) {
            this.mSelectedBgPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
            canvas.drawRoundRect(rectF2, top, top, this.mSelectedBgPaint);
        }
        this.mShadowPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
        canvas.drawRoundRect(rectF2, top, top, this.mShadowPaint);
        canvas.saveLayer(0.0f, 0.0f, (float) getRight(), (float) getBottom(), null, 31);
    }
}
