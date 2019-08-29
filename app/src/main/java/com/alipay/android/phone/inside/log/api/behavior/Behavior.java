package com.alipay.android.phone.inside.log.api.behavior;

import java.util.HashMap;
import java.util.Map;

public class Behavior {
    public String a;
    public BehaviorType b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public Map<String, String> j;

    public final void a(String str, String str2) {
        if (this.j == null) {
            this.j = new HashMap();
        }
        this.j.put(str, str2);
    }

    public final Behavior a(String str) {
        a("", str);
        return this;
    }
}
