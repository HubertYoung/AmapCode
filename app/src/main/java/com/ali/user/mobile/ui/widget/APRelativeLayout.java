package com.ali.user.mobile.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;

public class APRelativeLayout extends RelativeLayout {
    public APRelativeLayout(Context context) {
        super(context);
    }

    public APRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public APRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        try {
            if (getLayoutParams() == null) {
                setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), i), getDefaultSize(getSuggestedMinimumHeight(), i2));
            } else {
                super.onMeasure(i, i2);
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("APRelativeLayout:");
            sb.append(getId());
            StringBuffer stringBuffer = new StringBuffer(sb.toString());
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt != null) {
                    StringBuilder sb2 = new StringBuilder(MergeUtil.SEPARATOR_KV);
                    sb2.append(childAt.getId());
                    stringBuffer.append(sb2.toString());
                }
            }
            throw new IllegalStateException(stringBuffer.toString(), e);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        super.setOnClickListener(APViewEventHelper.a(onClickListener));
    }
}
