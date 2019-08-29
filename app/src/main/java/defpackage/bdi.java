package defpackage;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor.Callout;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bdi reason: default package */
/* compiled from: SmartPointHelper */
public final class bdi {
    @NonNull
    public bty a;
    public JsFunctionCallback b;

    public bdi(@NonNull bty bty, JsFunctionCallback jsFunctionCallback) {
        this.a = bty;
        this.b = jsFunctionCallback;
    }

    public static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!(jSONObject.opt(WidgetType.SCALE) == null || (jSONObject.opt(Callout.CALLOUT_TEXT_ALIGN_CENTER) == null && jSONObject.opt("geoobj") == null))) {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
