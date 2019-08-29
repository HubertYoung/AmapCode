package com.amap.bundle.drive.entrance;

import android.content.Context;
import android.view.View;
import com.amap.bundle.drivecommon.mvp.view.DriveBaseMapPage;
import com.autonavi.minimap.R;

public class NaviEntrancePage extends DriveBaseMapPage<nq> {
    public View a;
    public View b;
    public View c;
    public View d;
    public View e;
    public View f;

    public View getMapSuspendView() {
        return null;
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public nq createPresenter() {
        return new nq(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.drive_navi_type_choose_page);
    }

    public final void a() {
        if (this.b != null) {
            this.b.setVisibility(8);
        }
    }
}
