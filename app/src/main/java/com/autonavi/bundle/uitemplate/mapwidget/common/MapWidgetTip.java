package com.autonavi.bundle.uitemplate.mapwidget.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class MapWidgetTip extends FrameLayout {
    private int arrowDirection;
    private CharSequence mContextStr;
    private TextView mContextView;
    private int maxLines;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int textColor;
    private float textSizeDp;

    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
        public static final int DOWN = -1;
        public static final int DOWN_LEFT = -12;
        public static final int DOWN_RIGHT = 12;
        public static final int LEFT = 2;
        public static final int LEFT_DOWN = -22;
        public static final int LEFT_UP = 22;
        public static final int RIGHT = -2;
        public static final int RIGHT_DOWN = 21;
        public static final int RIGHT_UP = -21;
        public static final int UP = 1;
        public static final int UP_LEFT = 11;
        public static final int UP_RIGHT = -11;
    }

    public MapWidgetTip(@NonNull Context context) {
        this(context, null);
    }

    public MapWidgetTip(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MapWidgetTip(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.arrowDirection = -1;
        this.maxLines = 1;
        this.textSizeDp = 14.0f;
        this.textColor = -1;
        initAttrs(context, attributeSet);
        addContextView();
        setTipBackground();
        setTextColor(this.textColor);
        setTextSizeDp(this.textSizeDp);
        setText(this.mContextStr);
        setMaxLines(this.maxLines);
    }

    private void addContextView() {
        this.mContextView = new TextView(getContext());
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        this.mContextView.setLayoutParams(layoutParams);
        addView(this.mContextView);
        setTipPadding(this.paddingLeft, this.paddingTop, this.paddingRight, this.paddingBottom);
    }

    private void setTipBackground() {
        int i;
        switch (this.arrowDirection) {
            case -22:
                i = R.drawable.map_widget_tips_left_down;
                break;
            case -21:
                i = R.drawable.map_widget_tips_right_up;
                break;
            case -12:
                i = R.drawable.map_widget_tips_down_left;
                break;
            case -11:
                i = R.drawable.map_widget_tips_up_right;
                break;
            case -2:
                i = R.drawable.map_widget_tips_right;
                break;
            case -1:
                i = R.drawable.map_widget_tips_down;
                break;
            case 1:
                i = R.drawable.map_widget_tips_up;
                break;
            case 2:
                i = R.drawable.map_widget_tips_left;
                break;
            case 11:
                i = R.drawable.map_widget_tips_up_left;
                break;
            case 12:
                i = R.drawable.map_widget_tips_down_right;
                break;
            case 21:
                i = R.drawable.map_widget_tips_right_down;
                break;
            case 22:
                i = R.drawable.map_widget_tips_left_up;
                break;
            default:
                i = R.drawable.map_widget_tips_down;
                break;
        }
        setBackgroundResource(i);
    }

    public void initAttrs(Context context, @Nullable AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.map_widget_common_tip);
            this.arrowDirection = obtainStyledAttributes.getInt(R.styleable.map_widget_common_tip_arrow_direction, -1);
            this.maxLines = obtainStyledAttributes.getInt(R.styleable.map_widget_common_tip_max_lines, 1);
            this.mContextStr = obtainStyledAttributes.getString(R.styleable.map_widget_common_tip_text_context);
            this.textSizeDp = obtainStyledAttributes.getDimension(R.styleable.map_widget_common_tip_text_size, 13.0f);
            this.textColor = obtainStyledAttributes.getColor(R.styleable.map_widget_common_tip_text_color, -1);
            this.paddingLeft = (int) obtainStyledAttributes.getDimension(R.styleable.map_widget_common_tip_padding_left, 15.0f);
            this.paddingRight = (int) obtainStyledAttributes.getDimension(R.styleable.map_widget_common_tip_padding_right, 15.0f);
            this.paddingTop = (int) obtainStyledAttributes.getDimension(R.styleable.map_widget_common_tip_padding_top, 8.0f);
            this.paddingBottom = (int) obtainStyledAttributes.getDimension(R.styleable.map_widget_common_tip_padding_bottom, 8.0f);
            obtainStyledAttributes.recycle();
        }
    }

    public void setText(CharSequence charSequence) {
        if (this.mContextView != null && charSequence != null) {
            this.mContextView.setText(charSequence);
        }
    }

    public void setArrowDirection(int i) {
        this.arrowDirection = i;
        setTipBackground();
    }

    public void setMaxLines(int i) {
        if (this.mContextView != null) {
            this.mContextView.setMaxLines(i);
            this.maxLines = i;
        }
    }

    public void setTextSizeDp(float f) {
        if (this.mContextView != null) {
            this.mContextView.setTextSize(1, f);
            this.textSizeDp = f;
        }
    }

    public void setTextColor(int i) {
        if (this.mContextView != null) {
            this.mContextView.setTextColor(i);
            this.textColor = i;
        }
    }

    public void setTipPadding(int i, int i2, int i3, int i4) {
        if (this.mContextView != null) {
            this.mContextView.setPadding(i, i2, i3, i4);
            this.paddingLeft = i;
            this.paddingRight = i3;
            this.paddingBottom = i4;
            this.paddingTop = i2;
        }
    }
}
