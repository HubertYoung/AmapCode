package com.autonavi.mine.page.setting.sysabout.presenter;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.amap.bundle.logs.AMapLog;

public class ConfigPresenter$2 implements OnCheckedChangeListener {
    final /* synthetic */ cgr this$0;

    public ConfigPresenter$2(cgr cgr) {
        this.this$0 = cgr;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (z) {
            this.this$0.a.putIntValue("alc_console", 2 | this.this$0.a.getIntValue("alc_console", 2));
        } else {
            this.this$0.a.putIntValue("alc_console", 2 ^ this.this$0.a.getIntValue("alc_console", 2));
        }
        AMapLog.setSwitchFile(z);
    }
}
