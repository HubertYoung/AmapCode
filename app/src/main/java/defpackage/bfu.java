package defpackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* renamed from: bfu reason: default package */
/* compiled from: AjxParams */
public final class bfu implements bfw {
    private List<String> a = new ArrayList();
    private List<Object> b = new ArrayList();

    public final void a() {
        this.a.clear();
        this.b.clear();
        Object a2 = bfi.a();
        if (a2 != null) {
            JSONObject b2 = bgr.b(a2);
            if (b2 != null) {
                JSONObject optJSONObject = b2.optJSONObject("data");
                if (optJSONObject != null) {
                    Iterator<String> keys = optJSONObject.keys();
                    while (keys.hasNext()) {
                        String next = keys.next();
                        Object opt = b2.opt(next);
                        this.a.add(next);
                        this.b.add(opt);
                    }
                }
            }
        }
    }

    public final List<String> b() {
        return this.a;
    }

    public final List c() {
        return this.b;
    }
}
