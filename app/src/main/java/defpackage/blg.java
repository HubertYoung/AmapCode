package defpackage;

import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: blg reason: default package */
/* compiled from: NativeAlertAction */
public class blg extends vz {
    public final void a(JSONObject jSONObject, final wa waVar) {
        final JsAdapter a = a();
        if (a != null && a.mPageContext != null) {
            String optString = jSONObject.optString("title");
            String optString2 = jSONObject.optString("message");
            String optString3 = jSONObject.optString("cancelbutton");
            JSONArray optJSONArray = jSONObject.optJSONArray("otherbuttons");
            if (!TextUtils.isEmpty(optString2) && !TextUtils.isEmpty(optString3)) {
                a aVar = new a(a.mPageContext.getActivity());
                if (!TextUtils.isEmpty(optString)) {
                    aVar.a((CharSequence) optString);
                }
                aVar.b((CharSequence) optString2);
                aVar.b((CharSequence) optString3, (a) new a() {
                    public final void onClick(AlertView alertView, int i) {
                        a.mPageContext.dismissViewLayer(alertView);
                        blg.a(a, waVar, 0);
                    }
                });
                final int i = 1;
                while (optJSONArray != null && i <= optJSONArray.length()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(optJSONArray.optString(i - 1));
                    aVar.a((CharSequence) sb.toString(), (a) new a() {
                        public final void onClick(AlertView alertView, int i) {
                            a.mPageContext.dismissViewLayer(alertView);
                            blg.a(a, waVar, i);
                        }
                    });
                    i++;
                }
                aVar.a(true);
                AlertView a2 = aVar.a();
                a.mPageContext.showViewLayer(a2);
                a2.startAnimation();
            }
        }
    }

    static /* synthetic */ void a(JsAdapter jsAdapter, wa waVar, int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("_action", waVar.b);
            jSONObject.put("result", i);
            jsAdapter.callJs(waVar.a, jSONObject.toString());
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
    }
}
