package com.autonavi.minimap.ajx3.util;

import com.alipay.mobile.h5container.api.H5PageData;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor.Callout;
import org.json.JSONException;
import org.json.JSONObject;

public class ComputeUtils {
    private static final float EPSILON = 1.0E-5f;

    public static boolean floatsEqual(float f, float f2) {
        return (Float.isNaN(f) || Float.isNaN(f2)) ? Float.isNaN(f) && Float.isNaN(f2) : Math.abs(f2 - f) < EPSILON;
    }

    public static int computeYScrollBy(String str, int i, int i2, int i3, int i4) {
        int i5 = i - i4;
        String str2 = H5PageData.KEY_UC_START;
        if (str != null) {
            try {
                str2 = new JSONObject(str).getString("block");
            } catch (JSONException unused) {
                return 0;
            }
        }
        if (!H5PageData.KEY_UC_START.equals(str2)) {
            i5 = Callout.CALLOUT_TEXT_ALIGN_CENTER.equals(str2) ? i5 - ((i3 - i2) / 2) : "end".equals(str2) ? i5 - (i3 - i2) : 0;
        }
        return i5;
    }

    private ComputeUtils() {
    }
}
