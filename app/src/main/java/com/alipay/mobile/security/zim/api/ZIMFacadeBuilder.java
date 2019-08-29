package com.alipay.mobile.security.zim.api;

import android.content.Context;
import com.alipay.mobile.security.zim.a.d;

public class ZIMFacadeBuilder {
    public static ZIMFacade create(Context context) {
        if (context != null) {
            return new d(context);
        }
        throw new RuntimeException("context Can't be null");
    }
}
