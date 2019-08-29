package com.alipay.mobile.nebula.provider;

import com.alipay.mobile.h5container.api.H5Page;

public interface H5SchemeInterceptProvider {
    boolean handlerOnScheme(String str, H5Page h5Page);
}
