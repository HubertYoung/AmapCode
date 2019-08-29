package com.alipay.mobile.security.bio.api;

import java.util.Map;

public interface BioProgressCallback extends BioCallback {
    boolean onFaceDetected(Map<String, String> map);
}
