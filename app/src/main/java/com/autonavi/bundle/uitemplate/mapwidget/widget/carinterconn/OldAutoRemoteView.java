package com.autonavi.bundle.uitemplate.mapwidget.widget.carinterconn;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.autonavi.minimap.R;

public class OldAutoRemoteView extends AutoRemoteView {
    public OldAutoRemoteView(Context context) {
        super(context);
    }

    public OldAutoRemoteView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public OldAutoRemoteView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public int getLayoutId() {
        return R.layout.old_map_widget_route_layout;
    }
}
