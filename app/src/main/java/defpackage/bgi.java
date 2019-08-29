package defpackage;

import android.text.TextUtils;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.vui.business.poiselector.PoiSelectPage;
import com.autonavi.bundle.vui.business.poiselector.PoiSelectPage.a;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.uc.webview.export.internal.SDKFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bgi reason: default package */
/* compiled from: PoiSelectModel */
public final class bgi extends bgd {
    public final String a() {
        return "selectPoiWorkflow";
    }

    public final boolean a(final bgb bgb, bfb bfb) {
        try {
            boolean z = false;
            if (bgr.a(bfi.a()) != bgb.e) {
                return false;
            }
            JSONObject jSONObject = new JSONObject(bgb.b);
            String str = null;
            if (!jSONObject.has("voiceCommandResponse")) {
                StringBuilder sb = new StringBuilder("JSON_KEY_CMD_RESPONSE taskId=");
                sb.append(bgb.f);
                a(sb.toString());
            } else {
                JSONObject optJSONObject = jSONObject.optJSONObject("voiceCommandResponse");
                if (!optJSONObject.has("voiceResult")) {
                    StringBuilder sb2 = new StringBuilder("JSON_KEY_VOICERESULTS taskId=");
                    sb2.append(bgb.f);
                    a(sb2.toString());
                } else {
                    str = optJSONObject.optString("voiceResult");
                    if (TextUtils.isEmpty(str)) {
                        StringBuilder sb3 = new StringBuilder("JSON_KEY_VOICERESULTS empty taskId=");
                        sb3.append(bgb.f);
                        a(sb3.toString());
                    } else if (b(str)) {
                        z = true;
                    }
                }
            }
            if (!z) {
                a(bgb);
            } else {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("jsData", str);
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null) {
                    aho.a(new Runnable() {
                        public final void run() {
                            d.a.a(bgb.a, 10000, (String) null, true);
                        }
                    }, 1000);
                    pageContext.startPage(PoiSelectPage.class, pageBundle);
                    if (a.a != null) {
                        bid bid = (bid) a.a.get();
                        if (bid != null) {
                            bid.finish();
                        }
                    }
                }
            }
            return true;
        } catch (JSONException e) {
            a(bgb);
            StringBuilder sb4 = new StringBuilder("JSONException:");
            sb4.append(e.getMessage());
            sb4.append(" taskId=");
            sb4.append(bgb.f);
            a(sb4.toString());
        }
    }

    private void a(final bgb bgb) {
        StringBuilder sb = new StringBuilder("data:");
        sb.append(bgb.b);
        bfh.a("selectPoiWorkflow", sb.toString());
        aho.a(new Runnable() {
            public final void run() {
                d.a.a(bgb.a, (int) SDKFactory.getCoreType, (String) null, false);
            }
        }, 1000);
    }

    private static void a(String str) {
        bfp.a(h.a, 1, str);
    }

    private static boolean b(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        JSONArray jSONArray = jSONObject.getJSONArray("poi_list");
        if (!jSONObject.optBoolean("is_success", false) || jSONArray == null || jSONArray.length() == 0) {
            return false;
        }
        int i = 0;
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
            String optString = jSONObject2.optString("name");
            String optString2 = jSONObject2.optString(LocationParams.PARA_FLP_AUTONAVI_LON);
            String optString3 = jSONObject2.optString("lat");
            if (!TextUtils.isEmpty(optString) && !TextUtils.isEmpty(optString2) && !TextUtils.isEmpty(optString3)) {
                i++;
            }
        }
        if (i > 0) {
            return true;
        }
        return false;
    }
}
