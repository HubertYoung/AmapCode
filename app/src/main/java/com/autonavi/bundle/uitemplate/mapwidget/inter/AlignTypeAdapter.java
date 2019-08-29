package com.autonavi.bundle.uitemplate.mapwidget.inter;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.monitor.Performance;

public enum AlignTypeAdapter {
    LEFT_TOP(1, "left_top"),
    LEFT_CENTER(2, "left_center"),
    LEFT_BOTTOM(3, "left_bottom"),
    RIGHT_TOP(4, "right_top"),
    RIGHT_CENTER(5, "right_center"),
    RIGHT_BOTTOM(6, "right_bottom"),
    CENTER_TOP(7, "center_top"),
    CENTER_BOTTOM(8, "center_bottom"),
    HEADER(9, Performance.KEY_LOG_HEADER),
    FOOTER(10, "footer");
    
    int code;
    String desc;

    private AlignTypeAdapter(int i, String str) {
        this.code = i;
        this.desc = str;
    }

    public static int getAlignTypeForDesc(String str) {
        AlignTypeAdapter[] values;
        if (TextUtils.isEmpty(str)) {
            return LEFT_TOP.code;
        }
        for (AlignTypeAdapter alignTypeAdapter : values()) {
            if (alignTypeAdapter.desc.equals(str)) {
                return alignTypeAdapter.code;
            }
        }
        return LEFT_TOP.code;
    }
}
