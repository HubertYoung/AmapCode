package com.amap.bundle.drive.setting.navisetting.controller;

import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

public class NaviCarSettingManager$1 implements OnCheckedChangeListener {
    final /* synthetic */ qe this$0;

    public NaviCarSettingManager$1(qe qeVar) {
        this.this$0 = qeVar;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        AbstractBasePage abstractBasePage = (AbstractBasePage) this.this$0.a.get();
        if (abstractBasePage != null && abstractBasePage.isAlive()) {
            String carPlateNumber = DriveUtil.getCarPlateNumber();
            if (!z) {
                DriveUtil.setAvoidLimitedPath(z);
            } else if (TextUtils.isEmpty(carPlateNumber)) {
                abstractBasePage.startPageForResult((String) "amap.basemap.action.car_plate_input", new PageBundle(), 65536);
            } else {
                DriveUtil.setAvoidLimitedPath(z);
            }
        }
    }
}
