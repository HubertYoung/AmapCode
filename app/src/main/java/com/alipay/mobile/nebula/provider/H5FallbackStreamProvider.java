package com.alipay.mobile.nebula.provider;

import com.alipay.mobile.nebula.data.H5CustomHttpResponse;
import java.io.InputStream;
import java.util.Map;

public interface H5FallbackStreamProvider {
    InputStream getFallbackInputStream(String str);

    H5CustomHttpResponse httpRequest(String str, String str2, Map<String, String> map, byte[] bArr, long j, boolean z);
}
