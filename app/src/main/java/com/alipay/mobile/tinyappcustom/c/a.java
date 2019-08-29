package com.alipay.mobile.tinyappcustom.c;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.nebula.provider.H5LoginProvider;
import com.alipay.mobile.nebula.util.H5Utils;

/* compiled from: H5TinyAppUtils */
public final class a {
    public static String a() {
        String userId = LoggerFactory.getLogContext().getUserId();
        if (!TextUtils.isEmpty(userId)) {
            return userId;
        }
        H5LoginProvider h5LoginProvider = (H5LoginProvider) H5Utils.getProvider(H5LoginProvider.class.getName());
        if (h5LoginProvider != null) {
            return h5LoginProvider.getUserId();
        }
        return userId;
    }
}
