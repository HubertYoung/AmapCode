package com.uc.webview.export.internal.setup;

import com.uc.webview.export.Build;
import java.util.HashMap;

/* compiled from: ProGuard */
final class ce extends HashMap<String, String> {
    ce() {
        put("ucCoreImplVersion", String.format("%s_%s", new Object[]{Build.UCM_VERSION, Build.CORE_TIME}));
    }
}
