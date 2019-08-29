package defpackage;

import android.graphics.Color;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: hg reason: default package */
/* compiled from: AnimatableTextFrame */
public final class hg extends hl<gp, gp> {

    /* renamed from: hg$a */
    /* compiled from: AnimatableTextFrame */
    public static final class a {
        public static hg a(JSONObject jSONObject, ev evVar) {
            if (jSONObject != null && jSONObject.has(DictionaryKeys.CTRLXY_X)) {
                evVar.a((String) "Lottie doesn't support expressions.");
            }
            a a = hk.a(jSONObject, 1.0f, evVar, b.a).a();
            return new hg(a.a, (gp) a.b);
        }
    }

    /* renamed from: hg$b */
    /* compiled from: AnimatableTextFrame */
    static class b implements defpackage.hj.a<gp> {
        /* access modifiers changed from: private */
        public static final b a = new b();

        private b() {
        }

        public final /* synthetic */ Object a(Object obj, float f) {
            JSONObject jSONObject = (JSONObject) obj;
            String optString = jSONObject.optString(LogItem.MM_C15_K4_TIME);
            String optString2 = jSONObject.optString("f");
            int optInt = jSONObject.optInt("s");
            int optInt2 = jSONObject.optInt("j");
            int optInt3 = jSONObject.optInt("tr");
            double optDouble = jSONObject.optDouble("lh");
            double optDouble2 = jSONObject.optDouble("ls");
            JSONArray optJSONArray = jSONObject.optJSONArray(DictionaryKeys.EVENT_TYPE_FOCUS);
            String str = optString;
            String str2 = optString2;
            int argb = Color.argb(255, (int) (optJSONArray.optDouble(0) * 255.0d), (int) (optJSONArray.optDouble(1) * 255.0d), (int) (optJSONArray.optDouble(2) * 255.0d));
            JSONArray optJSONArray2 = jSONObject.optJSONArray(H5Param.SAFEPAY_CONTEXT);
            gp gpVar = new gp(str, str2, optInt, optInt2, optInt3, optDouble, optDouble2, argb, optJSONArray2 != null ? Color.argb(255, (int) (optJSONArray2.optDouble(0) * 255.0d), (int) (optJSONArray2.optDouble(1) * 255.0d), (int) (optJSONArray2.optDouble(2) * 255.0d)) : 0, jSONObject.optInt("sw"), jSONObject.optBoolean("of"));
            return gpVar;
        }
    }

    hg(List<fc<gp>> list, gp gpVar) {
        super(list, gpVar);
    }

    /* renamed from: c */
    public final gi a() {
        return new gi(this.a);
    }
}
