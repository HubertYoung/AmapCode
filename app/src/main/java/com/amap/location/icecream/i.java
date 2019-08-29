package com.amap.location.icecream;

import android.content.Context;
import com.amap.location.common.d.a;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* compiled from: IcecreamLauncher */
public class i {
    private JSONObject a;
    private Context b;
    private List<c> c = new ArrayList();

    protected i(Context context, JSONObject jSONObject) {
        this.b = context;
        this.a = jSONObject;
    }

    /* access modifiers changed from: protected */
    public void a(List<h> list) {
        if (list != null && list.size() > 0 && list.size() <= 20) {
            StringBuilder sb = new StringBuilder("start size:");
            sb.append(list.size());
            a.b("icelauncher", sb.toString());
            synchronized (this.c) {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    c cVar = new c();
                    cVar.a(list.get(i), this.b, this.a);
                    this.c.add(cVar);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void a() {
        StringBuilder sb = new StringBuilder("releaes size:");
        sb.append(this.c.size());
        a.b("icelauncher", sb.toString());
        synchronized (this.c) {
            int size = this.c.size();
            for (int i = 0; i < size; i++) {
                this.c.get(i).a();
            }
            this.c.clear();
        }
    }
}
