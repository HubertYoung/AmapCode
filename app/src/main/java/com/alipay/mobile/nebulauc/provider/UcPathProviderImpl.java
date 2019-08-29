package com.alipay.mobile.nebulauc.provider;

import android.content.Context;
import com.alipay.mobile.nebula.provider.H5WebViewPathProvider;
import com.alipay.mobile.nebulaucsdk.UcSdkConstants;
import java.io.File;

public class UcPathProviderImpl implements H5WebViewPathProvider {
    public String getWebViewPath(Context context) {
        return new File(context.getDir("h5container", 0), "uc/" + UcSdkConstants.UC_VERSION + "/so").getAbsolutePath();
    }
}
