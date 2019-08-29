package com.alipay.android.phone.inside.offlinecode.engine;

import java.util.List;

public interface DynamicCodePlugin {
    List<String> bridgeMethodNames();

    void handleEvent(DPECallEvent dPECallEvent);
}
