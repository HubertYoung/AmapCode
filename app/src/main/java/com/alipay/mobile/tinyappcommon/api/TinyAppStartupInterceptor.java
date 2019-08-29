package com.alipay.mobile.tinyappcommon.api;

import android.content.Context;
import android.os.Bundle;
import com.alipay.mobile.h5container.api.H5Page;

public interface TinyAppStartupInterceptor {
    Bundle handlerAppResume(H5Page h5Page, Bundle bundle);

    void handlerStartParamsReady(Context context, Bundle bundle);

    Bundle handlerStartupParams(H5Page h5Page, Bundle bundle);
}
