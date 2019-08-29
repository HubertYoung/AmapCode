package com.autonavi.bundle.uitemplate.mapwidget.widget.scenicarea;

import android.content.Context;
import android.view.View;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;

public class ScenicAreaWidget extends AbstractMapWidget<ScenicAreaPresenter> {
    public ScenicAreaWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        if (this.mContentView != null) {
            return this.mContentView;
        }
        this.mContentView = new ScenicAreaView(context);
        this.mContentView.setVisibility(8);
        return this.mContentView;
    }
}
