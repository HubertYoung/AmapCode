package defpackage;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dpi reason: default package */
/* compiled from: MarketDetailResponseModel */
public final class dpi {
    public int a;
    public int b;
    int c;
    public String d;
    public String e;
    public String f;
    public List<String> g;
    public List<String> h;
    public List<String> i;
    public List<dph> j;

    /* renamed from: dpi$a */
    /* compiled from: MarketDetailResponseModel */
    public static class a {
        public static dpi a(String str) {
            dpi dpi = new dpi();
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.getString("result").equals("false")) {
                    if (jSONObject.getString("code").equals("7")) {
                        ToastHelper.showToast(AMapAppGlobal.getApplication().getResources().getString(R.string.market_detail_no_search_result));
                    }
                    return null;
                }
                dpi.a = jSONObject.getInt("pagenum");
                dpi.b = jSONObject.getInt("page_total");
                dpi.c = jSONObject.getInt("total");
                dpi.d = jSONObject.getString("select_floor");
                dpi.e = jSONObject.getString("select_classify");
                dpi.f = jSONObject.getString("select_filter");
                if (jSONObject.has("floor")) {
                    ArrayList arrayList = new ArrayList();
                    new JSONArray();
                    JSONArray jSONArray = jSONObject.getJSONArray("floor");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        arrayList.add(jSONArray.getString(i));
                    }
                    dpi.h = arrayList;
                }
                if (jSONObject.has("classify")) {
                    ArrayList arrayList2 = new ArrayList();
                    new JSONArray();
                    JSONArray jSONArray2 = jSONObject.getJSONArray("classify");
                    for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                        arrayList2.add(jSONArray2.getString(i2));
                    }
                    dpi.g = arrayList2;
                }
                if (jSONObject.has("service_filter")) {
                    ArrayList arrayList3 = new ArrayList();
                    JSONArray jSONArray3 = jSONObject.getJSONArray("service_filter");
                    for (int i3 = 0; i3 < jSONArray3.length(); i3++) {
                        arrayList3.add(jSONArray3.getString(i3));
                    }
                    dpi.i = arrayList3;
                }
                ArrayList arrayList4 = new ArrayList();
                JSONArray jSONArray4 = jSONObject.getJSONArray("poilist");
                if (jSONArray4 != null) {
                    for (int i4 = 0; i4 < jSONArray4.length(); i4++) {
                        JSONObject jSONObject2 = jSONArray4.getJSONObject(i4);
                        if (jSONObject2 != null) {
                            arrayList4.add(a(jSONObject2));
                        }
                    }
                }
                dpi.j = arrayList4;
                return dpi;
            } catch (JSONException unused) {
                throw new RuntimeException();
            }
        }

        private static dph a(JSONObject jSONObject) {
            dph dph = new dph();
            try {
                dph.a = jSONObject.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
                dph.b = jSONObject.getString("name");
                dph.c = jSONObject.getString(DictionaryKeys.CTRLXY_X);
                dph.d = jSONObject.getString(DictionaryKeys.CTRLXY_Y);
                dph.e = jSONObject.getString("price");
                dph.i = jSONObject.getString("pic_url");
                dph.f = jSONObject.getString("score");
                dph.g = jSONObject.getString("floor");
                dph.j = jSONObject.getString("t_tag");
                dph.k = jSONObject.getString("c_tag");
                dph.h = jSONObject.getInt("tuan_flag");
                return dph;
            } catch (JSONException unused) {
                throw new RuntimeException();
            }
        }
    }
}
