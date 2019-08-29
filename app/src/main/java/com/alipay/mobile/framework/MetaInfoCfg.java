package com.alipay.mobile.framework;

import com.alipay.mobile.quinox.utils.TraceLogger;
import java.util.List;
import java.util.Map;

public class MetaInfoCfg {
    private static boolean a = false;
    private static MetaInfoCfg b = null;

    public static synchronized MetaInfoCfg getInstance() {
        MetaInfoCfg metaInfoCfg;
        synchronized (MetaInfoCfg.class) {
            if (!a || b == null) {
                a = true;
                try {
                    b = (MetaInfoCfg) Class.forName("com.alipay.mobile.core.impl.MetaInfoConfig").newInstance();
                } catch (Throwable tr) {
                    TraceLogger.w((String) "MetaInfoCfg", tr);
                }
                if (b == null) {
                    b = new MetaInfoCfg();
                }
                metaInfoCfg = b;
            } else {
                metaInfoCfg = b;
            }
        }
        return metaInfoCfg;
    }

    public boolean hasDescriptions() {
        return false;
    }

    public boolean hasDescriptionsSave() {
        boolean hasDescription = false;
        try {
            hasDescription = hasDescriptions();
        } catch (Throwable tr) {
            TraceLogger.w((String) "MetaInfoCfg", tr);
        }
        TraceLogger.i((String) "MetaInfoCfg", "hasDescription=" + hasDescription);
        return hasDescription;
    }

    public Map<String, List<MicroDescription<?>>> getDescriptions() {
        return null;
    }
}
