package com.autonavi.bundle.uitemplate.mapwidget.custom.nearby;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import com.autonavi.bundle.uitemplate.mapwidget.widget.search.NearBySearchMapWidget;
import com.autonavi.minimap.R;

public class OldNearbySearchMapWidget extends NearBySearchMapWidget {
    private View mCutLineView;
    private RelativeLayout mNearbyContainer;

    public OldNearbySearchMapWidget(Context context) {
        super(context);
    }

    public int getLayoutId() {
        return R.layout.old_map_widget_nearby_search_layout;
    }

    public void onInit(Context context) {
        if (this.mContentView != null) {
            this.mNearbyContainer = (RelativeLayout) this.mContentView.findViewById(R.id.nearby_search_container);
            this.mCutLineView = this.mContentView.findViewById(R.id.cut_line_view);
        }
    }

    public void setCutLineVisible(int i) {
        if (this.mCutLineView != null) {
            this.mCutLineView.setVisibility(i);
        }
    }

    /* access modifiers changed from: protected */
    public OldNearbySearchWidgetPresenter getCustomPresenter() {
        return new OldNearbySearchWidgetPresenter();
    }

    public boolean isInSafeRegion() {
        this.mNearbyContainer.measure(0, 0);
        this.mBottomRegion.measure(0, 0);
        if ((ags.a(getContext()).width() - (this.mBottomRegion == null ? 0 : this.mBottomRegion.getMeasuredWidth())) - (this.mNearbyContainer == null ? 0 : this.mNearbyContainer.getMeasuredWidth()) >= bet.a(getContext(), 36)) {
            return true;
        }
        return false;
    }
}
