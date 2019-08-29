package com.amap.bundle.drivecommon.mvp.view;

import android.content.Context;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import defpackage.sv;

public abstract class DriveBaseMapPage<Presenter extends sv> extends AbstractBaseMapPage<Presenter> {
    public void onCreate(Context context) {
        super.onCreate(context);
        ((sv) this.mPresenter).a(context);
    }
}
