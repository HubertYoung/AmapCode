package com.alipay.android.phone.mobilecommon.multimedia.api.data.image.processor.decode;

import android.graphics.Bitmap.Config;
import android.os.Build.VERSION;

class APBaseDecodeOptions {
    public boolean autoRotate;
    public boolean autoUseAshmem;
    public Integer forceRotate;
    public boolean inPreferQualityOverSpeed;
    public Config inPreferredConfig = Config.ARGB_8888;
    public boolean jniDebug;

    APBaseDecodeOptions() {
        this.autoUseAshmem = VERSION.SDK_INT < 21;
        this.autoRotate = true;
        this.inPreferQualityOverSpeed = false;
        this.jniDebug = false;
    }
}
