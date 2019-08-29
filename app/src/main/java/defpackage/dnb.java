package defpackage;

import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.PageBundle;
import org.json.JSONObject;

/* renamed from: dnb reason: default package */
/* compiled from: OpenLightAppAction */
public class dnb extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            String optString = jSONObject.optString("licenseUrl");
            String optString2 = jSONObject.optString("wapUrl");
            final String optString3 = jSONObject.optString("websiteName");
            if (!a.mPageContext.getActivity().getSharedPreferences("category_save_v2", 0).getBoolean(optString, false)) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("license_url", optString);
                pageBundle.putString("url", optString2);
                pageBundle.putString("website_name", optString3);
                pageBundle.putBoolean("native_web", false);
                a.mPageContext.startPage((String) "amap.basemap.action.licenseconfirm_page", pageBundle);
                return;
            }
            aja aja = new aja(optString2);
            aja.b = new ajf() {
                public final b c() {
                    return new b() {
                        public final long c() {
                            return 1000;
                        }

                        public final boolean a() {
                            return !TextUtils.isEmpty(optString3);
                        }

                        public final String b() {
                            if (!TextUtils.isEmpty(optString3)) {
                                return optString3;
                            }
                            return null;
                        }
                    };
                }
            };
            aix aix = (aix) a.a.a(aix.class);
            if (aix != null) {
                aix.a(a.mPageContext, aja);
            }
        }
    }
}
