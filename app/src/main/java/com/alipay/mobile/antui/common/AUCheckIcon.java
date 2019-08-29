package com.alipay.mobile.antui.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import com.alipay.mobile.antui.R;

public class AUCheckIcon extends CheckBox {
    public static final int STATE_CANNOT_CHECKED = 4;
    public static final int STATE_CANNOT_UNCHECKED = 3;
    public static final int STATE_CHECKED = 1;
    public static final int STATE_UNCHECKED = 2;

    public AUCheckIcon(Context context) {
        super(context);
        initView(context, (AttributeSet) null);
    }

    public AUCheckIcon(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    public AUCheckIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public AUCheckIcon(Context context, int checkIconState, boolean scaleAuto) {
        super(context);
        initView(checkIconState, scaleAuto);
    }

    private void initView(Context context, AttributeSet attrs) {
        int mIconState = 2;
        boolean scaleAuto = false;
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AUCheckIcon);
            mIconState = array.getInt(1, 2);
            scaleAuto = array.getBoolean(0, false);
            array.recycle();
        }
        initView(mIconState, scaleAuto);
    }

    private void initView(int checkIconState, boolean scaleAuto) {
        if (scaleAuto) {
            setBackgroundResource(R.drawable.drawable_check_icon);
            setButtonDrawable(new ColorDrawable(0));
        } else {
            setButtonDrawable(R.drawable.drawable_check_icon);
        }
        setIconState(checkIconState);
    }

    public void setIconState(int state) {
        switch (state) {
            case 1:
                setEnabled(true);
                setChecked(true);
                return;
            case 2:
                setEnabled(true);
                setChecked(false);
                return;
            case 3:
                setEnabled(false);
                setChecked(true);
                return;
            case 4:
                setEnabled(false);
                setChecked(false);
                return;
            default:
                return;
        }
    }

    public int getIconState() {
        if (isChecked() && isEnabled()) {
            return 1;
        }
        if (!isChecked() && isEnabled()) {
            return 2;
        }
        if (!isChecked() || isEnabled()) {
            return 4;
        }
        return 3;
    }
}
