package com.alipay.mobile.beehive.api;

import java.util.Map;

public interface ScanExecutor {
    String scan(String str);

    Map<String, String> scanVariantQrCodeCompact(String str);
}
