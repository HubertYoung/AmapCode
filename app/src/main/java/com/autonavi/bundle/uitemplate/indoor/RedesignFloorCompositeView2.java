package com.autonavi.bundle.uitemplate.indoor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.autonavi.minimap.R;

public class RedesignFloorCompositeView2 extends LinearLayout {
    private RedesignFloorWidgetView mFloorWidget;
    private ViewGroup mRootView;

    public RedesignFloorCompositeView2(Context context) {
        super(context);
        init(context);
    }

    public RedesignFloorCompositeView2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.redesign_floor_composite_view2, this);
        initView();
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        this.mRootView = (ViewGroup) findViewById(R.id.floor_composite_root);
        this.mFloorWidget = (RedesignFloorWidgetView) findViewById(R.id.floor_widget);
    }

    public RedesignFloorWidgetView getFloorWidgetView() {
        return this.mFloorWidget;
    }
}
