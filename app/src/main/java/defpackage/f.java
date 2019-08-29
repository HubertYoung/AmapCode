package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: f reason: default package */
/* compiled from: RecommendSearchConvert */
public final class f {
    public static JSONObject a(C0087e eVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(eVar.a)) {
            jSONObject.put("subtitle", eVar.a);
        }
        if (!TextUtils.isEmpty(eVar.b)) {
            jSONObject.put("title", eVar.b);
        }
        if (!TextUtils.isEmpty(eVar.c)) {
            jSONObject.put("toplist_id", eVar.c);
        }
        if (!TextUtils.isEmpty(eVar.d)) {
            jSONObject.put(H5AppUtil.scene, eVar.d);
        }
        if (eVar.e != null) {
            jSONObject.put("head_pic", a(eVar.e));
        }
        jSONObject.put("has_tab", eVar.f);
        if (eVar.g != null && eVar.g.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (g a : eVar.g) {
                jSONArray.put(a(a));
            }
            jSONObject.put("toplist_tab_list", jSONArray);
        }
        return jSONObject;
    }

    private static JSONObject a(g gVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!TextUtils.isEmpty(gVar.a)) {
            jSONObject.put("data_source", gVar.a);
        }
        if (!TextUtils.isEmpty(gVar.b)) {
            jSONObject.put("tab_name", gVar.b);
        }
        jSONObject.put("poi_num", gVar.c);
        if (gVar.d != null && gVar.d.length > 0) {
            JSONArray jSONArray = new JSONArray();
            for (defpackage.e.f fVar : gVar.d) {
                JSONObject jSONObject2 = new JSONObject();
                if (fVar.a != null) {
                    jSONObject2.put("item_pic", a(fVar.a));
                }
                jSONObject2.put(DictionaryKeys.CTRLXY_X, (double) fVar.b);
                if (!TextUtils.isEmpty(fVar.c)) {
                    jSONObject2.put("poi_tag", fVar.c);
                }
                if (!TextUtils.isEmpty(fVar.d)) {
                    jSONObject2.put("item_distance", fVar.d);
                }
                if (!TextUtils.isEmpty(fVar.e)) {
                    jSONObject2.put("item_price", fVar.e);
                }
                if (!TextUtils.isEmpty(fVar.f)) {
                    jSONObject2.put("district", fVar.f);
                }
                if (!TextUtils.isEmpty(fVar.g)) {
                    jSONObject2.put("recommend_dish", fVar.g);
                }
                if (!TextUtils.isEmpty(fVar.h)) {
                    jSONObject2.put("item_name", fVar.h);
                }
                if (!TextUtils.isEmpty(fVar.i)) {
                    jSONObject2.put("poi_business", fVar.i);
                }
                jSONObject2.put("item_comment_num", fVar.j);
                if (!TextUtils.isEmpty(fVar.k)) {
                    jSONObject2.put("recommend_reason", fVar.k);
                }
                jSONObject2.put(DictionaryKeys.CTRLXY_Y, (double) fVar.l);
                if (!TextUtils.isEmpty(fVar.m)) {
                    jSONObject2.put("business_name", fVar.m);
                }
                if (!TextUtils.isEmpty(fVar.n)) {
                    jSONObject2.put("rec_tag", fVar.n);
                }
                if (!TextUtils.isEmpty(fVar.o)) {
                    jSONObject2.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, fVar.o);
                }
                if (!TextUtils.isEmpty(fVar.p)) {
                    jSONObject2.put("item_rate", fVar.p);
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("toplist_item_list", jSONArray);
        }
        jSONObject.put("show_flag", gVar.e);
        if (!TextUtils.isEmpty(gVar.f)) {
            jSONObject.put("toplist_id", gVar.f);
        }
        if (!TextUtils.isEmpty(gVar.g)) {
            jSONObject.put("toplist_template_id", gVar.g);
        }
        if (!TextUtils.isEmpty(gVar.h)) {
            jSONObject.put("type", gVar.h);
        }
        return jSONObject;
    }

    private static JSONObject a(c cVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("pic_size", cVar.a);
        jSONObject.put("pic_pos", cVar.b);
        if (!TextUtils.isEmpty(cVar.c)) {
            jSONObject.put("pic_url", cVar.c);
        }
        return jSONObject;
    }
}
