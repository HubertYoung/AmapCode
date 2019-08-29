package com.alipay.mobile.mrtc.api.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import com.alipay.mobile.mrtc.api.enums.APScalingType;

public class ARTVCView extends FrameLayout {
    public ARTVCView(Context context) {
        super(context);
    }

    public ARTVCView(Context context, AttributeSet set) {
        super(context, set);
    }

    public void setAPScalingType(APScalingType scalingType) {
    }

    public void setZOrderMediaOverlay(boolean isMediaOverlay) {
    }

    public void setOnClickListener(OnClickListener listener) {
    }
}
