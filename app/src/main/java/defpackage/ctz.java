package defpackage;

import com.alipay.mobile.h5container.api.H5Param;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONObject;

/* renamed from: ctz reason: default package */
/* compiled from: ActivityPreloadPoi */
public final class ctz {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;

    public ctz() {
    }

    public ctz(JSONObject jSONObject) {
        if (jSONObject != null) {
            this.a = jSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
            this.b = jSONObject.optString("typecode");
            this.c = jSONObject.optString("lat");
            this.d = jSONObject.optString(CameraControllerManager.MY_POILOCATION_LNG);
            this.e = jSONObject.optString(H5Param.MENU_ICON);
        }
    }
}
