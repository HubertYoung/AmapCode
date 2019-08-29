package com.uc.webview.export.business.setup;

import com.amap.location.sdk.fusion.LocationParams;
import com.uc.webview.export.extension.UCCore;
import java.util.HashMap;

/* compiled from: ProGuard */
final class m extends HashMap<String, String> {
    final /* synthetic */ a a;

    m(a aVar) {
        this.a = aVar;
        put(UCCore.EVENT_INIT_CORE_SUCCESS, "success");
        put("die_delegate", LocationParams.PARA_COMMON_DIE);
    }
}
