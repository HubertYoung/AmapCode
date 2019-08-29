package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: vz reason: default package */
/* compiled from: JsAction */
public abstract class vz {
    private WeakReference<JsAdapter> a;

    public abstract void a(JSONObject jSONObject, wa waVar) throws JSONException;

    public final void a(JsAdapter jsAdapter) {
        this.a = new WeakReference<>(jsAdapter);
    }

    public final JsAdapter a() {
        return (JsAdapter) this.a.get();
    }
}
