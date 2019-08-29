package com.autonavi.bundle.uitemplate.mapwidget.widget.diy;

import android.content.Context;
import android.view.View;
import com.autonavi.bundle.uitemplate.mapwidget.widget.AbstractMapWidget;

public class DIYMapWidget extends AbstractMapWidget<DIYMainMapPresenter> {
    public DIYMapWidget(Context context) {
        super(context);
    }

    public View createContentView(Context context) {
        return new DIYMainMapView(context);
    }

    public void onInit(Context context) {
        if (getPresenter() != null) {
            ((DIYMainMapPresenter) getPresenter()).addListenerType(-1);
        }
    }
}
