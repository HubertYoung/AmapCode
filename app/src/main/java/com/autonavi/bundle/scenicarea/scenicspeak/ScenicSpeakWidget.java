package com.autonavi.bundle.scenicarea.scenicspeak;

import android.content.Context;
import android.view.View;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;

public class ScenicSpeakWidget extends AbstractMapWidget<ScenicSpeakWidgetPresenter> {
    public ScenicSpeakWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        if (this.mContentView != null) {
            return this.mContentView;
        }
        this.mContentView = new ScenicSpeakView(context);
        this.mContentView.setVisibility(0);
        return this.mContentView;
    }
}
