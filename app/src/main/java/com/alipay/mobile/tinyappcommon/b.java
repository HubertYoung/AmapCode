package com.alipay.mobile.tinyappcommon;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;

/* compiled from: TinyAppLoggerUtils */
public final class b {
    public static void a(String seedId, String key, String value) {
        Behavor behavor = new Behavor();
        behavor.setBehaviourPro("TINYAPP");
        behavor.setSeedID(seedId);
        behavor.addExtParam(key, value);
        LoggerFactory.getBehavorLogger().click(behavor);
    }
}
