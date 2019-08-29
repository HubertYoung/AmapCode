package defpackage;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: arm reason: default package */
/* compiled from: QuickServiceCloudManager */
public final class arm {
    public int a = -1;
    private final String b = "redesign_quickservice";
    private final String c = "mid_offset";

    public final arm a() {
        String a2 = lo.a().a((String) "redesign_quickservice");
        bez.a(getClass().getSimpleName(), "Quick Service load cloud config ", new bew("json", a2));
        if (!TextUtils.isEmpty(a2)) {
            try {
                this.a = new JSONObject(a2).getInt("mid_offset");
            } catch (JSONException unused) {
                this.a = -1;
            }
        }
        String simpleName = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder("Quick Service load cloud config height:");
        sb.append(this.a);
        bez.a(simpleName, sb.toString(), new bew[0]);
        return this;
    }
}
