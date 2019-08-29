package com.alipay.mobile.security.zim.api;

import java.util.Map;

public interface ZimProgressCallback extends ZIMCallback {
    boolean onFaceDetected(Map<String, String> map);
}
