package com.alipay.mobile.nebula.provider;

import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.process.H5HttpRequestResult;
import java.util.Map;

public interface NebulaAdapterProvider {
    H5HttpRequestResult httpRequest(String str, String str2, Map<String, String> map, byte[] bArr, long j, String str3, String str4, boolean z, H5Page h5Page);
}
