package com.alipay.mobile.framework.service.voice;

import java.util.HashMap;

public interface RecognizeListenerSupprot {
    void onRecognizingResult(int i, String str, HashMap<String, Object> hashMap);

    void onServiceStatChanged(boolean z, boolean z2);
}
