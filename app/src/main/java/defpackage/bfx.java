package defpackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* renamed from: bfx reason: default package */
/* compiled from: SessionParamsManager */
public final class bfx {
    private ArrayList<bfw> a = new ArrayList<>();

    public final <T> bfx a(bfw<T> bfw) {
        if (this.a.contains(bfw)) {
            return this;
        }
        this.a.add(bfw);
        return this;
    }

    public final JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        Iterator<bfw> it = this.a.iterator();
        while (it.hasNext()) {
            bfw next = it.next();
            next.a();
            List<String> b = next.b();
            List c = next.c();
            if (!(b == null || c == null)) {
                int i = 0;
                while (i < b.size()) {
                    try {
                        jSONObject.put(b.get(i), c.get(i));
                        i++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return jSONObject;
    }
}
