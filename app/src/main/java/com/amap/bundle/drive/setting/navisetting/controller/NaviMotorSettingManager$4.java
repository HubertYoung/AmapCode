package com.amap.bundle.drive.setting.navisetting.controller;

import android.text.TextUtils;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;

public class NaviMotorSettingManager$4 implements OnCheckedChangeListener {
    final /* synthetic */ qg this$0;

    public NaviMotorSettingManager$4(qg qgVar) {
        this.this$0 = qgVar;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        StringBuilder sb = new StringBuilder("onCheckChanged isPressed : ");
        sb.append(compoundButton.isPressed());
        sb.append(" b: ");
        sb.append(z);
        AMapLog.d("Daniel-27", sb.toString());
        AbstractBasePage abstractBasePage = (AbstractBasePage) this.this$0.a.get();
        if (abstractBasePage != null && abstractBasePage.isAlive()) {
            if (!TextUtils.isEmpty(DriveUtil.getMotorPlateNum())) {
                ro.a(z ? 1 : 0);
                qg.b(z ^ true ? 1 : 0);
            } else if (z && !this.this$0.d) {
                compoundButton.setChecked(false);
                this.this$0.c = true;
                this.this$0.d = true;
                ro.c();
            }
        }
    }
}
