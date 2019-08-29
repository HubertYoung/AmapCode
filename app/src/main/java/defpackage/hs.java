package defpackage;

import android.support.annotation.Nullable;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.common.SuperId;
import org.json.JSONObject;

/* renamed from: hs reason: default package */
/* compiled from: Repeater */
public final class hs implements hn {
    public final String a;
    public final gy b;
    public final gy c;
    public final hi d;

    /* renamed from: hs$a */
    /* compiled from: Repeater */
    static final class a {
        static hs a(JSONObject jSONObject, ev evVar) {
            return new hs(jSONObject.optString(LogItem.MM_C18_K4_NM), defpackage.gy.a.a(jSONObject.optJSONObject(SuperId.BIT_1_NEARBY_SEARCH), evVar, false), defpackage.gy.a.a(jSONObject.optJSONObject("o"), evVar, false), defpackage.hi.a.a(jSONObject.optJSONObject("tr"), evVar));
        }
    }

    hs(String str, gy gyVar, gy gyVar2, hi hiVar) {
        this.a = str;
        this.b = gyVar;
        this.c = gyVar2;
        this.d = hiVar;
    }

    @Nullable
    public final fe a(ew ewVar, hx hxVar) {
        return new fq(ewVar, hxVar, this);
    }
}
