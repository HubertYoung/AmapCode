package defpackage;

import android.content.res.Resources;
import org.json.JSONObject;

/* renamed from: gt reason: default package */
/* compiled from: JsonCompositionLoader */
public final class gt extends gn<JSONObject> {
    private final Resources a;
    private final ey b;

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object doInBackground(Object[] objArr) {
        return a.a(this.a, ((JSONObject[]) objArr)[0]);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void onPostExecute(Object obj) {
        this.b.onCompositionLoaded((ev) obj);
    }

    public gt(Resources resources, ey eyVar) {
        this.a = resources;
        this.b = eyVar;
    }
}
