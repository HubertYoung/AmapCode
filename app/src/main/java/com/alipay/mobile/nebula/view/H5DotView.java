package com.alipay.mobile.nebula.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5DotView extends View {
    private static final int DEF_COLOR = Color.parseColor("#FD3737");
    private Context context;
    private int dotColor;
    private int dotWidth;
    private Paint paint;

    public H5DotView(Context context2) {
        this(context2, null);
    }

    public H5DotView(Context context2, @Nullable AttributeSet attrs) {
        this(context2, attrs, 0);
    }

    public H5DotView(Context context2, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context2, attrs, defStyleAttr);
        TypedArray array = context2.obtainStyledAttributes(attrs, R.styleable.H5DotView);
        this.context = context2;
        this.dotColor = array.getColor(R.styleable.H5DotView_dotColor, DEF_COLOR);
        this.paint = new Paint(1);
        this.paint.setColor(this.dotColor);
        this.dotWidth = 0;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        canvas.drawCircle((float) centerX, (float) centerY, (float) getDotRadius(), this.paint);
    }

    public void setDotColor(int color) {
        if (color != 0) {
            this.paint.setColor(-16777216 | color);
            invalidate();
        }
    }

    private int getDotRadius() {
        if (this.dotWidth <= 0 || this.dotWidth >= getWidth()) {
            return getWidth() / 2;
        }
        return this.dotWidth / 2;
    }

    public void setDotWidth(int width) {
        this.dotWidth = H5Utils.dip2px(this.context, width);
        invalidate();
    }
}
