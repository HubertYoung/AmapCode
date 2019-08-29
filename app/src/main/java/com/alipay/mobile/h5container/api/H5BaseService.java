package com.alipay.mobile.h5container.api;

import android.os.Bundle;

public abstract class H5BaseService {
    public abstract void onCreate(Bundle bundle);

    public abstract void onDestroy(Bundle bundle);
}
