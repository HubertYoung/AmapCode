package com.autonavi.bundle.entity.infolite.internal.template;

import android.text.TextUtils;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;

public class PoiWebImageTemplate extends PoiLayoutTemplate {
    private static final long serialVersionUID = -5163421485364126017L;
    private String value;

    public int isShown() {
        return TextUtils.isEmpty(this.value) ? 8 : 0;
    }

    public String getValue() {
        return getScaleAndCutUrl(160, 130);
    }

    public String getOriginalValue() {
        return this.value;
    }

    public String getScaleAndCutUrl(int i, int i2) {
        String str = this.value;
        if (TextUtils.isEmpty(str) || i <= 0 || i2 <= 0) {
            return str;
        }
        String str2 = "?";
        if (str.contains("?")) {
            str2 = "&";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        sb.append(String.format("operate=merge&w=%d&h=%d&position=5", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
        return sb.toString();
    }

    public void setValue(String str) {
        this.value = str;
    }
}
