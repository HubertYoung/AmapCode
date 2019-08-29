package com.amap.bundle.drive.setting.navisetting.controller;

import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.offline.inter.inner.ICheckOfflineResponse;

public class NaviCarSettingManager$9 implements ICheckOfflineResponse {
    final /* synthetic */ AbstractBasePage a;
    final /* synthetic */ qe b;

    public NaviCarSettingManager$9(qe qeVar, AbstractBasePage abstractBasePage) {
        this.b = qeVar;
        this.a = abstractBasePage;
    }

    public void callback(boolean z) {
        this.b.d = false;
        if (z) {
            this.a.getActivity().runOnUiThread(new Runnable() {
                public final void run() {
                    if (NaviCarSettingManager$9.this.b.c.isChecked()) {
                        NaviCarSettingManager$9.this.b.c.toggle();
                    } else {
                        NaviCarSettingManager$9.this.b.b();
                    }
                }
            });
        }
    }
}
