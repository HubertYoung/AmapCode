package com.alipay.android.phone.mobilecommon.multimedia.material;

import android.os.Build.VERSION;

public class APFalconAbility {
    public boolean deviceSupport;
    public boolean falconSwitch = true;

    public APFalconAbility() {
        boolean z = true;
        this.deviceSupport = VERSION.SDK_INT < 18 ? false : z;
    }
}
