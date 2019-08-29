package com.amap.location.uptunnel.a;

import org.json.JSONObject;

/* compiled from: BaseCommand */
public abstract class a {
    protected int a;
    protected JSONObject b;

    public abstract void a(JSONObject jSONObject);

    /* access modifiers changed from: protected */
    public abstract boolean b();

    public a(JSONObject jSONObject) {
        this.b = jSONObject;
    }

    public JSONObject a() {
        return this.b;
    }
}
