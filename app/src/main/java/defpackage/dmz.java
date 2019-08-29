package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import org.json.JSONObject;

@Deprecated
/* renamed from: dmz reason: default package */
/* compiled from: OpenHtmlStringWebViewAction */
public class dmz extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            String optString = jSONObject.optString(PoiLayoutTemplate.HTML);
            final String optString2 = jSONObject.optString("title");
            aja aja = new aja(optString);
            aja.b = new ajf() {
                public final boolean d() {
                    return true;
                }

                public final String b() {
                    return optString2;
                }

                public final b c() {
                    return new b() {
                        public final boolean a() {
                            return false;
                        }

                        public final long c() {
                            return 0;
                        }

                        public final String b() {
                            return optString2;
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
