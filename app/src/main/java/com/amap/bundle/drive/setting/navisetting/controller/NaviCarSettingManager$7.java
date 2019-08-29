package com.amap.bundle.drive.setting.navisetting.controller;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

public class NaviCarSettingManager$7 implements OnCheckedChangeListener {
    final /* synthetic */ qe this$0;

    public NaviCarSettingManager$7(qe qeVar) {
        this.this$0 = qeVar;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        AbstractBasePage abstractBasePage = (AbstractBasePage) this.this$0.a.get();
        if (abstractBasePage != null && abstractBasePage.isAlive()) {
            DriveSpUtil.setSearchRouteInNeMode(abstractBasePage.getContext(), !z);
        }
    }
}
