package com.autonavi.bundle.scenicarea.scenicguide;

import android.content.Context;
import android.view.View;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;

public class ScenicGuideWidget extends AbstractMapWidget<ScenicGuideWidgetPresenter> {
    public ScenicGuideWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        if (this.mContentView != null) {
            return this.mContentView;
        }
        this.mContentView = new ScenicGuideView(context);
        this.mContentView.setVisibility(0);
        return this.mContentView;
    }
}
