package com.autonavi.widget.badgeview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class BadgeView extends TextView {
    private static final String DEFAULT_BG_COLOR = "#d3321b";
    private static final int DEFAULT_PADDING_BOTTOM = 2;
    private static final int DEFAULT_PADDING_LEFT = 5;
    private static final int DEFAULT_PADDING_RIGHT = 5;
    private static final int DEFAULT_PADDING_TOP = 1;
    private static final int DEFAULT_RADII = 9;
    private static final int DEFAULT_TEXT_SIZE = 11;
    private boolean mHideOnNull;

    public BadgeView(Context context) {
        this(context, null);
    }

    public BadgeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public BadgeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHideOnNull = false;
        init();
    }

    private void init() {
        if (!(getLayoutParams() instanceof LayoutParams)) {
            setLayoutParams(new LayoutParams(-2, -2, 53));
        }
        setTextColor(-1);
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(2, 11.0f);
        setPadding(dip2Px(5.0f), dip2Px(1.0f), dip2Px(5.0f), dip2Px(2.0f));
        setBackground(9, Color.parseColor(DEFAULT_BG_COLOR));
        setGravity(17);
    }

    public void setBackground(int i, int i2) {
        float f = (float) i;
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{f, f, f, f, f, f, f, f}, null, null));
        shapeDrawable.getPaint().setColor(i2);
        setBackgroundDrawable(shapeDrawable);
    }

    public void setBackground(int i, int i2, int i3) {
        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        int i4 = i3 * 2;
        layoutParams.width = i4;
        layoutParams.height = i4;
        setLayoutParams(layoutParams);
        setBackground(i, i2);
    }

    public boolean isHideOnNull() {
        return this.mHideOnNull;
    }

    public void setHideOnNull(boolean z) {
        this.mHideOnNull = z;
        setText(getText());
    }

    public void setText(CharSequence charSequence, BufferType bufferType) {
        if (!isHideOnNull() || (charSequence != null && !charSequence.toString().equalsIgnoreCase("0"))) {
            setVisibility(0);
        } else {
            setVisibility(8);
        }
        super.setText(charSequence, bufferType);
    }

    public void setImageDrawable(Drawable drawable) {
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            LayoutParams layoutParams = (LayoutParams) getLayoutParams();
            layoutParams.width = intrinsicWidth;
            layoutParams.height = intrinsicHeight;
            setLayoutParams(layoutParams);
            setBackgroundDrawable(drawable);
        }
    }

    public void setImageResource(int i) {
        setImageDrawable(getResources().getDrawable(i));
    }

    public void setBadgeCount(int i) {
        setText(String.valueOf(i));
    }

    public Integer getBadgeCount() {
        if (getText() == null) {
            return null;
        }
        try {
            return Integer.valueOf(Integer.parseInt(getText().toString()));
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    public void setBadgeGravity(int i) {
        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        layoutParams.gravity = i;
        setLayoutParams(layoutParams);
    }

    public void setBadgeMargin(int i) {
        setBadgeMargin(i, i, i, i);
    }

    public void setBadgeMargin(int i, int i2, int i3, int i4) {
        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        layoutParams.leftMargin = i;
        layoutParams.topMargin = i2;
        layoutParams.rightMargin = i3;
        layoutParams.bottomMargin = i4;
        setLayoutParams(layoutParams);
    }

    public void incrementBadgeCount(int i) {
        Integer badgeCount = getBadgeCount();
        if (badgeCount == null) {
            setBadgeCount(i);
        } else {
            setBadgeCount(i + badgeCount.intValue());
        }
    }

    public void decrementBadgeCount(int i) {
        incrementBadgeCount(-i);
    }

    public void setTargetView(TabWidget tabWidget, int i) {
        setTargetView(tabWidget.getChildTabViewAt(i));
    }

    public void setTargetView(ViewGroup viewGroup, int i) {
        setTargetView(viewGroup.getChildAt(i));
    }

    public void setTargetView(View view) {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }
        if (view != null) {
            if (view.getParent() instanceof FrameLayout) {
                ((FrameLayout) view.getParent()).addView(this);
            } else if (view.getParent() instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view.getParent();
                int indexOfChild = viewGroup.indexOfChild(view);
                viewGroup.removeView(view);
                FrameLayout frameLayout = new FrameLayout(getContext());
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                frameLayout.setLayoutParams(layoutParams);
                view.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                viewGroup.addView(frameLayout, indexOfChild, layoutParams);
                frameLayout.addView(view);
                frameLayout.addView(this);
            } else {
                if (view.getParent() == null) {
                    getClass().getSimpleName();
                }
            }
        }
    }

    private int dip2Px(float f) {
        return (int) ((f * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }
}
