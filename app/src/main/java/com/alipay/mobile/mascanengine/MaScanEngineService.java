package com.alipay.mobile.mascanengine;

import android.graphics.Bitmap;
import com.alipay.mobile.bqcscanservice.BQCScanEngine;
import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class MaScanEngineService extends ExternalService {
    public abstract Class<? extends BQCScanEngine> getEngineClazz();

    public abstract MaScanResult process(Bitmap bitmap);

    public abstract MaScanResult process(String str);

    public abstract MaScanResult processARCode(byte[] bArr, int i, int i2, int i3);
}
