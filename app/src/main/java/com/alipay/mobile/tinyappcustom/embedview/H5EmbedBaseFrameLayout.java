package com.alipay.mobile.tinyappcustom.embedview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5EmbedBaseFrameLayout extends FrameLayout {
    private H5BorderDrawable a;
    private float b;
    private float c;
    private RectF d = new RectF();
    private Path e = new Path();

    public H5EmbedBaseFrameLayout(Context context) {
        super(context);
    }

    public H5EmbedBaseFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public H5EmbedBaseFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return true;
    }

    public void onReceivedRender(Context context, JSONObject jsonObject) {
        this.a = H5EmbedViewUtil.generateBackgroundDrawable(context, jsonObject, -1);
        this.b = H5Utils.parseFloat(jsonObject.getString("borderWidth"));
        if (this.b > 0.0f) {
            this.b = (float) H5DimensionUtil.dip2px(context, this.b);
            this.c = H5Utils.parseFloat(jsonObject.getString("borderRadius"));
            this.c = (float) H5DimensionUtil.dip2px(context, this.c);
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        if (this.c > 0.0f) {
            canvas.save();
            this.e.reset();
            this.d.set(this.b / 2.0f, this.b / 2.0f, ((float) canvas.getWidth()) - (this.b / 2.0f), ((float) canvas.getHeight()) - (this.b / 2.0f));
            this.e.addRoundRect(this.d, this.c, this.c, Direction.CW);
            canvas.clipPath(this.e);
            if (this.a != null) {
                this.a.drawBackground(canvas);
            }
            super.dispatchDraw(canvas);
            canvas.restore();
            if (this.a != null) {
                this.a.drawBorder(canvas);
                return;
            }
            return;
        }
        if (this.a != null) {
            this.a.drawBackground(canvas);
        }
        super.dispatchDraw(canvas);
        if (this.a != null) {
            this.a.drawBorder(canvas);
        }
    }
}
