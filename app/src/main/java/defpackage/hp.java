package defpackage;

import android.graphics.Path.FillType;
import android.support.annotation.Nullable;
import com.airbnb.lottie.model.content.GradientType;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.common.SuperId;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: hp reason: default package */
/* compiled from: GradientFill */
public final class hp implements hn {
    public final GradientType a;
    public final FillType b;
    public final gz c;
    public final ha d;
    public final hc e;
    public final hc f;
    public final String g;
    @Nullable
    private final gy h;
    @Nullable
    private final gy i;

    /* renamed from: hp$a */
    /* compiled from: GradientFill */
    static class a {
        static hp a(JSONObject jSONObject, ev evVar) {
            String optString = jSONObject.optString(LogItem.MM_C18_K4_NM);
            JSONObject optJSONObject = jSONObject.optJSONObject(SuperId.BIT_1_NAVI);
            if (optJSONObject != null && optJSONObject.has(SuperId.BIT_1_REALTIMEBUS_BUSSTATION)) {
                int optInt = optJSONObject.optInt(SuperId.BIT_1_MAIN_VOICE_ASSISTANT);
                optJSONObject = optJSONObject.optJSONObject(SuperId.BIT_1_REALTIMEBUS_BUSSTATION);
                try {
                    optJSONObject.put(SuperId.BIT_1_MAIN_VOICE_ASSISTANT, optInt);
                } catch (JSONException unused) {
                }
            }
            gz a = optJSONObject != null ? defpackage.gz.a.a(optJSONObject, evVar) : null;
            JSONObject optJSONObject2 = jSONObject.optJSONObject("o");
            ha a2 = optJSONObject2 != null ? defpackage.ha.a.a(optJSONObject2, evVar) : null;
            FillType fillType = jSONObject.optInt(UploadQueueMgr.MSGTYPE_REALTIME, 1) == 1 ? FillType.WINDING : FillType.EVEN_ODD;
            GradientType gradientType = jSONObject.optInt(LogItem.MM_C15_K4_TIME, 1) == 1 ? GradientType.Linear : GradientType.Radial;
            JSONObject optJSONObject3 = jSONObject.optJSONObject("s");
            hc a3 = optJSONObject3 != null ? defpackage.hc.a.a(optJSONObject3, evVar) : null;
            JSONObject optJSONObject4 = jSONObject.optJSONObject("e");
            hp hpVar = new hp(optString, gradientType, fillType, a, a2, a3, optJSONObject4 != null ? defpackage.hc.a.a(optJSONObject4, evVar) : null, 0);
            return hpVar;
        }
    }

    /* synthetic */ hp(String str, GradientType gradientType, FillType fillType, gz gzVar, ha haVar, hc hcVar, hc hcVar2, byte b2) {
        this(str, gradientType, fillType, gzVar, haVar, hcVar, hcVar2);
    }

    private hp(String str, GradientType gradientType, FillType fillType, gz gzVar, ha haVar, hc hcVar, hc hcVar2) {
        this.a = gradientType;
        this.b = fillType;
        this.c = gzVar;
        this.d = haVar;
        this.e = hcVar;
        this.f = hcVar2;
        this.g = str;
        this.h = null;
        this.i = null;
    }

    public final fe a(ew ewVar, hx hxVar) {
        return new fj(ewVar, hxVar, this);
    }
}
