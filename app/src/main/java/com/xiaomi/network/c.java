package com.xiaomi.network;

import com.alipay.mobile.h5container.api.H5Param;
import java.util.Iterator;
import java.util.LinkedList;
import org.json.JSONArray;
import org.json.JSONObject;

class c implements Comparable<c> {
    String a;
    protected int b;
    private final LinkedList<AccessHistory> c;
    private long d;

    public c() {
        this(null, 0);
    }

    public c(String str) {
        this(str, 0);
    }

    public c(String str, int i) {
        this.c = new LinkedList<>();
        this.d = 0;
        this.a = str;
        this.b = i;
    }

    /* renamed from: a */
    public int compareTo(c cVar) {
        if (cVar == null) {
            return 1;
        }
        return cVar.b - this.b;
    }

    public synchronized c a(JSONObject jSONObject) {
        try {
            this.d = jSONObject.getLong(H5Param.TRANSPARENT);
            this.b = jSONObject.getInt(wt.a);
            this.a = jSONObject.getString("host");
            JSONArray jSONArray = jSONObject.getJSONArray("ah");
            for (int i = 0; i < jSONArray.length(); i++) {
                this.c.add(new AccessHistory().a(jSONArray.getJSONObject(i)));
            }
        }
        return this;
    }

    public synchronized JSONObject a() {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject();
            jSONObject.put(H5Param.TRANSPARENT, this.d);
            jSONObject.put(wt.a, this.b);
            jSONObject.put("host", this.a);
            JSONArray jSONArray = new JSONArray();
            Iterator it = this.c.iterator();
            while (it.hasNext()) {
                jSONArray.put(((AccessHistory) it.next()).b());
            }
            jSONObject.put("ah", jSONArray);
        }
        return jSONObject;
    }

    /* access modifiers changed from: protected */
    public synchronized void a(AccessHistory accessHistory) {
        if (accessHistory != null) {
            this.c.add(accessHistory);
            int a2 = accessHistory.a();
            if (a2 > 0) {
                this.b += accessHistory.a();
            } else {
                int i = 0;
                int size = this.c.size() - 1;
                while (size >= 0 && this.c.get(size).a() < 0) {
                    i++;
                    size--;
                }
                this.b += a2 * i;
            }
            if (this.c.size() > 30) {
                this.b -= this.c.remove().a();
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append(":");
        sb.append(this.b);
        return sb.toString();
    }
}
