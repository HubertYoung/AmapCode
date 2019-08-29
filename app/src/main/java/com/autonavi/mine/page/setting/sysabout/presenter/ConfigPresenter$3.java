package com.autonavi.mine.page.setting.sysabout.presenter;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.amap.bundle.logs.AMapLog;

public class ConfigPresenter$3 implements OnCheckedChangeListener {
    final /* synthetic */ cgr this$0;
    final /* synthetic */ String val$groupName;

    public ConfigPresenter$3(cgr cgr, String str) {
        this.this$0 = cgr;
        this.val$groupName = str;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.this$0.a.putBooleanValue(this.val$groupName, z);
        AMapLog.switchGroupEnable(wp.a(this.val$groupName, bno.a), z);
    }
}
