package com.amap.bundle.drivecommon.mvp.view;

import android.content.Context;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManagerService;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import defpackage.sw;

public abstract class DriveBasePage<Presenter extends sw> extends AbstractBasePage<Presenter> {
    private boolean mFinished = false;

    public void onCreate(Context context) {
        super.onCreate(context);
        ((sw) this.mPresenter).a(context);
        if (!(this instanceof Transparent)) {
            IMapWidgetManagerService iMapWidgetManagerService = (IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class);
            if (iMapWidgetManagerService != null) {
                iMapWidgetManagerService.setContainerVisible(8);
            }
        }
    }

    public void finishAllFragmentsWithoutRoot() {
        finish();
        apr apr = (apr) a.a.a(apr.class);
        if (apr != null) {
            apr.b(this);
        }
    }

    public boolean isDriveBasePageFinish() {
        return this.mFinished || !isAlive();
    }
}
