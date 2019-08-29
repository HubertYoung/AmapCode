package com.alipay.mobile.tinyappcommon.api;

import android.content.Context;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;

public interface TinyAppShareInterface {
    void startShare(String str, String str2, String str3, String str4, String str5, String str6, H5Page h5Page, Context context, H5BridgeContext h5BridgeContext);
}
