package defpackage;

import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.bundle.routecommon.model.RouteType;
import org.json.JSONException;
import org.json.JSONObject;

@MultipleImpl(awa.class)
/* renamed from: deb reason: default package */
/* compiled from: DriveInit */
public class deb implements awa {
    public final void a() {
        DriveUtil.initLastCarsCount();
        emt a = emu.a((String) "VERSION_LASTVERINFO");
        if (a != null) {
            try {
                JSONObject jSONObject = new JSONObject(a.toString());
                if (jSONObject.has("versionName")) {
                    String optString = jSONObject.optString("versionName");
                    if (!TextUtils.isEmpty(optString) && Integer.parseInt(optString.replace(".", "")) < 8220000) {
                        DriveUtil.removalTruckChoice();
                    }
                }
            } catch (NumberFormatException | JSONException unused) {
            }
        } else {
            DriveUtil.setNeedGuideTruck(false);
        }
        bax bax = (bax) a.a.a(bax.class);
        if (DriveUtil.isTruckAvoidLimitedPath() && bax.a() == RouteType.CAR) {
            bax.a(RouteType.TRUCK);
            DriveUtil.setTruckAvoidLimitedPath(false);
        }
        ewt.a(ConfigerHelper.getInstance().getMMLogConfiger());
    }
}
