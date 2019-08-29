package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cca reason: default package */
/* compiled from: PageBundleUtil */
public final class cca {
    public static void a(Uri uri, String str, BaseIntentDispatcher baseIntentDispatcher) {
        if (!TextUtils.isEmpty(str)) {
            Class<Ajx3Page> cls = Ajx3Page.class;
            PageBundle pageBundle = new PageBundle();
            if (!TextUtils.isEmpty(str)) {
                pageBundle.putString("url", str);
                pageBundle.putString("jsData", a(uri).toString());
            }
            baseIntentDispatcher.startPage(cls, pageBundle);
        }
    }

    public static JSONObject a(Uri uri) {
        JSONObject jSONObject = new JSONObject();
        if (uri == null) {
            return jSONObject;
        }
        for (String next : uri.getQueryParameterNames()) {
            try {
                jSONObject.put(next, uri.getQueryParameter(next));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject;
    }
}
