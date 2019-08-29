package com.autonavi.bundle.uitemplate.mapwidget.widget.indoorguide;

import android.content.Context;
import android.view.View;
import com.autonavi.bundle.uitemplate.indoor.MapGuideViewCenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;

public class IndoorGuideWidget extends AbstractMapWidget<IndoorGuideWidgetPresenter> {
    public IndoorGuideWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        if (this.mContentView != null) {
            return this.mContentView;
        }
        MapGuideViewCenter mapGuideViewCenter = new MapGuideViewCenter(context);
        bec bec = (bec) ank.a(bec.class);
        if (bec != null) {
            bea a = bec.a();
            if (a != null) {
                a.a((bef) mapGuideViewCenter);
            }
        }
        this.mContentView = mapGuideViewCenter;
        this.mContentView.setVisibility(8);
        return this.mContentView;
    }
}
