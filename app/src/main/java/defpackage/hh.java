package defpackage;

import android.support.annotation.Nullable;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import org.json.JSONObject;

/* renamed from: hh reason: default package */
/* compiled from: AnimatableTextProperties */
public final class hh {
    @Nullable
    public final gx a;
    @Nullable
    public final gx b;
    @Nullable
    public final gy c;
    @Nullable
    public final gy d;

    /* renamed from: hh$a */
    /* compiled from: AnimatableTextProperties */
    public static final class a {
        public static hh a(JSONObject jSONObject, ev evVar) {
            gy gyVar;
            gy gyVar2 = null;
            if (jSONObject == null || !jSONObject.has("a")) {
                return new hh(null, null, null, null);
            }
            JSONObject optJSONObject = jSONObject.optJSONObject("a");
            JSONObject optJSONObject2 = optJSONObject.optJSONObject(DictionaryKeys.EVENT_TYPE_FOCUS);
            gx a = optJSONObject2 != null ? defpackage.gx.a.a(optJSONObject2, evVar) : null;
            JSONObject optJSONObject3 = optJSONObject.optJSONObject(H5Param.SAFEPAY_CONTEXT);
            gx a2 = optJSONObject3 != null ? defpackage.gx.a.a(optJSONObject3, evVar) : null;
            JSONObject optJSONObject4 = optJSONObject.optJSONObject("sw");
            if (optJSONObject4 != null) {
                gyVar = defpackage.gy.a.a(optJSONObject4, evVar, true);
            } else {
                gyVar = null;
            }
            JSONObject optJSONObject5 = optJSONObject.optJSONObject(LogItem.MM_C15_K4_TIME);
            if (optJSONObject5 != null) {
                gyVar2 = defpackage.gy.a.a(optJSONObject5, evVar, true);
            }
            return new hh(a, a2, gyVar, gyVar2);
        }
    }

    hh(@Nullable gx gxVar, @Nullable gx gxVar2, @Nullable gy gyVar, @Nullable gy gyVar2) {
        this.a = gxVar;
        this.b = gxVar2;
        this.c = gyVar;
        this.d = gyVar2;
    }
}
