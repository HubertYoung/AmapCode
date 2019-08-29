package com.autonavi.minimap.ajx3.widget.barchart;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import org.json.JSONArray;
import org.json.JSONException;

public class Ajx3BarchartProperty extends BaseProperty<Ajx3Barchart> {
    private static final String BAT_CHART_DATA = "barchartData";

    public Ajx3BarchartProperty(@NonNull Ajx3Barchart ajx3Barchart, @NonNull IAjxContext iAjxContext) {
        super(ajx3Barchart, iAjxContext);
    }

    public void updateAttribute(String str, Object obj) {
        if (((str.hashCode() == -2087287307 && str.equals(BAT_CHART_DATA)) ? (char) 0 : 65535) != 0) {
            super.updateAttribute(str, obj);
        } else if (obj != null) {
            String str2 = (String) obj;
            if (!TextUtils.isEmpty(str2)) {
                try {
                    ((Ajx3Barchart) this.mView).setData(new JSONArray(str2));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
