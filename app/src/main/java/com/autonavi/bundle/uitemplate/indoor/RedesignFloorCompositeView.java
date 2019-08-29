package com.autonavi.bundle.uitemplate.indoor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.autonavi.minimap.R;

public class RedesignFloorCompositeView extends LinearLayout {
    private RedesignFloorWidgetView mFloorWidget;
    private RedesignMapGuideView mMapGuideView;
    private ViewGroup mRootView;

    public RedesignFloorCompositeView(Context context) {
        super(context);
        init(context);
    }

    public RedesignFloorCompositeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    /* access modifiers changed from: 0000 */
    public void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.redesign_floor_composite_view, this);
        initView();
    }

    /* access modifiers changed from: 0000 */
    public void initView() {
        this.mRootView = (ViewGroup) findViewById(R.id.floor_composite_root);
        this.mMapGuideView = (RedesignMapGuideView) findViewById(R.id.map_guide_widget);
        this.mFloorWidget = (RedesignFloorWidgetView) findViewById(R.id.floor_widget);
    }

    public RedesignMapGuideView getMapGuideView() {
        return this.mMapGuideView;
    }

    public RedesignFloorWidgetView getFloorWidgetView() {
        return this.mFloorWidget;
    }
}
