package com.alipay.mobile.nebulauc.nativeinput;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.autonavi.jni.ae.pos.SpeedState;

public class H5NativeInput extends H5NativeEditText {
    public H5NativeInput(Context context) {
        super(context);
    }

    public H5NativeInput(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public H5NativeInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initViews(Context context) {
        setBackgroundDrawable(null);
        setFocusable(true);
        setGravity(19);
        setInputType(1);
        setMaxLines(1);
        setEllipsize(TruncateAt.END);
        setTextColor(Color.parseColor("#000000"));
        setHintTextColor(Color.rgb(SpeedState.ENO_DEFINE, SpeedState.ENO_DEFINE, SpeedState.ENO_DEFINE));
        setTextSize((float) H5DimensionUtil.dip2px(context, 17.0f));
        setCursorVisible(true);
        setPadding(2, 0, 0, 0);
    }
}
