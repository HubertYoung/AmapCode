package defpackage;

import android.content.SharedPreferences.Editor;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.amap.app.AMapAppGlobal;
import java.text.SimpleDateFormat;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: eis reason: default package */
/* compiled from: TrainSelectedDataAction */
public class eis extends vz {
    public static String a;
    public static String b;

    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        JsAdapter a2 = a();
        if (a2 != null) {
            Editor edit = AMapAppGlobal.getApplication().getSharedPreferences(a, 0).edit();
            try {
                edit.putLong(b, new SimpleDateFormat("yyyy-MM-dd").parse(jSONObject.optString("date")).getTime());
                edit.apply();
            } catch (Throwable th) {
                th.printStackTrace();
            }
            if (a2.mPageContext != null) {
                a2.mPageContext.finish();
            }
        }
    }
}
