package defpackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.intent.BaseIntent;

@Deprecated
/* renamed from: dlp reason: default package */
/* compiled from: VoiceIntent */
public final class dlp extends BaseIntent {
    public final void a(String str) {
    }

    public dlp(Activity activity, Intent intent) {
        super(activity, intent);
    }

    public final boolean c() {
        POI poi = (POI) this.b.getSerializableExtra("fromPOI");
        POI poi2 = (POI) this.b.getSerializableExtra("toPOI");
        switch (this.b.getIntExtra("routeType", -1)) {
            case 0:
                atb atb = (atb) a.a.a(atb.class);
                if (atb == null) {
                    return false;
                }
                this.h = atb.d();
                if (this.h != null) {
                    this.h.setFromPOI(poi);
                    this.h.setToPOI(poi2);
                    this.h.setMethod(atb.f());
                    this.f = true;
                }
                return true;
            case 1:
                dhz dhz = (dhz) ank.a(dhz.class);
                dhx dhx = new dhx(poi, poi2);
                if (dhz != null) {
                    dhz.a(dhx);
                }
                this.f = false;
                return true;
            case 2:
                avi avi = (avi) a.a.a(avi.class);
                if (avi == null) {
                    return false;
                }
                this.i = avi.a((Context) this.a);
                if (this.i != null) {
                    this.i.setFromPOI(poi);
                    this.i.setToPOI(poi2);
                    this.i.setMethod("0");
                }
                return true;
            default:
                return false;
        }
    }
}
