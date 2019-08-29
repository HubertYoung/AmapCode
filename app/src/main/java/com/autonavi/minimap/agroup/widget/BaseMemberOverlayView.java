package com.autonavi.minimap.agroup.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.autonavi.minimap.agroup.entity.MemberInfo;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.MemberIconStyle;

public abstract class BaseMemberOverlayView extends RelativeLayout {
    /* access modifiers changed from: protected */
    public abstract void initView();

    public abstract boolean initWithStyle(MemberInfo memberInfo, MemberIconStyle memberIconStyle, cjf cjf);

    public BaseMemberOverlayView(Context context) {
        super(context);
    }

    public BaseMemberOverlayView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BaseMemberOverlayView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }
}
