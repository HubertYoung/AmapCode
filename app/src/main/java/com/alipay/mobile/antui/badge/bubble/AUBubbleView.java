package com.alipay.mobile.antui.badge.bubble;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.basic.AUViewInterface;

public class AUBubbleView extends AUTextView implements AUViewInterface {
    public static final int POSITION_TOP_LEFT = 0;
    public static final int POSITION_TOP_MID = 2;
    public static final int POSITION_TOP_RIGHT = 1;
    private a mDrawable;

    public AUBubbleView(Context context) {
        super(new ContextThemeWrapper(context, R.style.bubbleViewStyle));
        init(context, null);
    }

    public AUBubbleView(Context context, AttributeSet attrs) {
        super(new ContextThemeWrapper(context, R.style.bubbleViewStyle), attrs);
        init(context, attrs);
    }

    public AUBubbleView(Context context, AttributeSet attrs, int defStyle) {
        super(new ContextThemeWrapper(context, R.style.bubbleViewStyle), attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        int position = 1;
        int color = Color.parseColor("#FF3B30");
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AUBubbleView);
            position = ta.getInt(0, 1);
            color = ta.getColor(1, color);
            ta.recycle();
        }
        this.mDrawable = new a();
        this.mDrawable.c(position);
        this.mDrawable.d(color);
        if (VERSION.SDK_INT >= 16) {
            setBackground(this.mDrawable);
        } else {
            setBackgroundDrawable(this.mDrawable);
        }
        setGravity(17);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setExtraPadding();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void setExtraPadding() {
        double extraPaddingWidth = ((double) getTextSize()) / 2.2d;
        double extraPaddingHeight = (double) (getTextSize() / 13.0f);
        setPadding((int) extraPaddingWidth, (int) extraPaddingHeight, (int) extraPaddingWidth, (int) (1.5d * extraPaddingHeight));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.mDrawable.a(getWidth());
        this.mDrawable.b(getHeight());
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(0.0f, (-(((float) getHeight()) - this.mDrawable.a())) / 1.5f);
        super.onDraw(canvas);
        canvas.restore();
    }

    public AUBubbleView setBubblePosition(int position) {
        if (position == 0 || position == 1 || position == 2) {
            this.mDrawable.c(position);
            return this;
        }
        throw new IllegalArgumentException("position 必须是 POSITION_TOP_LEFT、POSITION_TOP_RIGHT、POSITION_TOP_MID");
    }

    public AUBubbleView setBubbleColor(int color) {
        this.mDrawable.d(color);
        return this;
    }
}
