package com.uc.webview.export.internal.utility;

import com.autonavi.minimap.ajx3.util.RomUtil;
import java.util.HashMap;

/* compiled from: ProGuard */
final class k extends HashMap<String, String> {
    k() {
        put("ro.build.hw_emui_api_level", RomUtil.ROM_EMUI);
        put("ro.miui.ui.version.name", RomUtil.ROM_MIUI);
        put("ro.build.version.opporom", "COLOROS");
        put("ro.vivo.os.name", "FuntouchOS");
        put("ro.yunos.version", "YunOS");
        put("ro.flyme.published", RomUtil.ROM_FLYME);
        put("ro.meizu.product.model", RomUtil.ROM_FLYME);
    }
}
