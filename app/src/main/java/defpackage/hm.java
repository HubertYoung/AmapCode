package defpackage;

import android.graphics.PointF;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.common.SuperId;
import org.json.JSONObject;

/* renamed from: hm reason: default package */
/* compiled from: CircleShape */
public final class hm implements hn {
    public final String a;
    public final hj<PointF, PointF> b;
    public final hc c;
    public final boolean d;

    /* renamed from: hm$a */
    /* compiled from: CircleShape */
    static class a {
        static hm a(JSONObject jSONObject, ev evVar) {
            hm hmVar = new hm(jSONObject.optString(LogItem.MM_C18_K4_NM), hb.a(jSONObject.optJSONObject(SuperId.BIT_1_MAIN_VOICE_ASSISTANT), evVar), defpackage.hc.a.a(jSONObject.optJSONObject("s"), evVar), jSONObject.optInt("d", 2) == 3, 0);
            return hmVar;
        }
    }

    /* synthetic */ hm(String str, hj hjVar, hc hcVar, boolean z, byte b2) {
        this(str, hjVar, hcVar, z);
    }

    private hm(String str, hj<PointF, PointF> hjVar, hc hcVar, boolean z) {
        this.a = str;
        this.b = hjVar;
        this.c = hcVar;
        this.d = z;
    }

    public final fe a(ew ewVar, hx hxVar) {
        return new fh(ewVar, hxVar, this);
    }
}
