package com.autonavi.bundle.uitemplate.mapwidget.widget.carinterconn;

import android.content.Context;

public class OldAutoRemoteMapWidget extends AutoRemoteMapWidget {
    public OldAutoRemoteMapWidget(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public AutoRemoteView createAutoRemoteView(Context context) {
        return new OldAutoRemoteView(context);
    }

    public Class<AutoRemoteWidgetPresenter> getPresenterClass() {
        return AutoRemoteWidgetPresenter.class;
    }
}
