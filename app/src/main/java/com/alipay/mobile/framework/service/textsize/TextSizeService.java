package com.alipay.mobile.framework.service.textsize;

import com.alipay.mobile.framework.service.ext.ExternalService;

public abstract class TextSizeService extends ExternalService {
    public abstract float getScaleByGear(int i);

    public abstract int getSizeGear();

    public abstract void setSizeGear(int i);

    public abstract float transformSize(float f);
}
