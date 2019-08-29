package defpackage;

import android.text.TextUtils;
import com.autonavi.sdk.location.LocationInstrument;

/* renamed from: dkf reason: default package */
/* compiled from: CarPlateUtil */
public final class dkf {
    public static String a() {
        long adCode = (long) LocationInstrument.getInstance().getLatestPosition().getAdCode();
        if (bno.a) {
            ku.a().c("bug_12188981", "CarPlateUtil   cityCode:".concat(String.valueOf(adCode)));
        }
        if (adCode == 0) {
            adCode = 110000;
        }
        String valueOf = String.valueOf(adCode);
        return (TextUtils.isEmpty(valueOf) || valueOf.length() < 2) ? "" : valueOf.substring(0, 2);
    }
}
