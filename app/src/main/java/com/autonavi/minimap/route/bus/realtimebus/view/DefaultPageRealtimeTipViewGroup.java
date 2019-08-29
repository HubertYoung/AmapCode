package com.autonavi.minimap.route.bus.realtimebus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class DefaultPageRealtimeTipViewGroup extends RelativeLayout {
    private bty mMapView;
    private a mTipStatusListener;

    public interface a {
        void a();

        void b();
    }

    public DefaultPageRealtimeTipViewGroup(Context context) {
        super(context);
    }

    public DefaultPageRealtimeTipViewGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DefaultPageRealtimeTipViewGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setTipStatusListener(a aVar) {
        this.mTipStatusListener = aVar;
    }

    public void setMapView(bty bty) {
        this.mMapView = bty;
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        if (this.mTipStatusListener != null) {
            if (i == 0) {
                this.mTipStatusListener.a();
                bmn.a(this.mMapView);
            } else {
                this.mTipStatusListener.b();
                bmn.b(this.mMapView);
            }
        }
        if (this.mMapView != null) {
            if (i == 0) {
                bmn.a(this.mMapView);
                return;
            }
            bmn.b(this.mMapView);
        }
    }
}
