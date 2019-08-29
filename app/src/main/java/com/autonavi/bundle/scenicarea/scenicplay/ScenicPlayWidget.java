package com.autonavi.bundle.scenicarea.scenicplay;

import android.content.Context;
import android.view.View;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;

public class ScenicPlayWidget extends AbstractMapWidget<ScenicPlayWidgetPresenter> {
    public ScenicPlayWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        if (this.mContentView != null) {
            return this.mContentView;
        }
        this.mContentView = new ScenicPlayView(context);
        this.mContentView.setVisibility(0);
        return this.mContentView;
    }
}
