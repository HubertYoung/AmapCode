package com.alipay.mobile.nebulauc.nativetextarea;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebulauc.nativeinput.H5NativeEditText;
import com.autonavi.jni.ae.pos.SpeedState;

public class H5NativeTextArea extends H5NativeEditText {
    public H5NativeTextArea(Context context) {
        super(context);
    }

    public H5NativeTextArea(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public H5NativeTextArea(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initViews(Context context) {
        setBackgroundDrawable(null);
        setFocusable(true);
        setGravity(51);
        setInputType(131072);
        setSingleLine(false);
        setHorizontallyScrolling(false);
        setMinLines(1);
        setTextColor(Color.parseColor("#000000"));
        setHintTextColor(Color.rgb(SpeedState.ENO_DEFINE, SpeedState.ENO_DEFINE, SpeedState.ENO_DEFINE));
        setTextSize((float) H5DimensionUtil.dip2px(context, 17.0f));
        setCursorVisible(true);
        setPadding(2, 0, 0, 0);
    }
}
