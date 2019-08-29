package com.alipay.deviceid.module.rpc.json;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class a {
    private ArrayList a;

    public a() {
        this.a = new ArrayList();
    }

    public a(c cVar) {
        char c;
        ArrayList arrayList;
        Object d;
        this();
        char c2 = cVar.c();
        if (c2 == '[') {
            c = ']';
        } else if (c2 == '(') {
            c = ')';
        } else {
            throw cVar.a((String) "A JSONArray text must start with '['");
        }
        if (cVar.c() != ']') {
            do {
                cVar.a();
                if (cVar.c() == ',') {
                    cVar.a();
                    arrayList = this.a;
                    d = null;
                } else {
                    cVar.a();
                    arrayList = this.a;
                    d = cVar.d();
                }
                arrayList.add(d);
                char c3 = cVar.c();
                if (c3 != ')') {
                    if (c3 != ',' && c3 != ';') {
                        if (c3 != ']') {
                            throw cVar.a((String) "Expected a ',' or ']'");
                        }
                    }
                }
                if (c != c3) {
                    StringBuilder sb = new StringBuilder("Expected a '");
                    sb.append(Character.valueOf(c));
                    sb.append("'");
                    throw cVar.a(sb.toString());
                }
                return;
            } while (cVar.c() != ']');
        }
    }

    public a(Object obj) {
        this();
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                this.a.add(Array.get(obj, i));
            }
            return;
        }
        throw new JSONException((String) "JSONArray initial value should be a string or collection or array.");
    }

    public a(String str) {
        this(new c(str));
    }

    public a(Collection collection) {
        this.a = collection == null ? new ArrayList() : new ArrayList(collection);
    }

    private String a(String str) {
        int size = this.a.size();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                stringBuffer.append(str);
            }
            stringBuffer.append(b.a(this.a.get(i)));
        }
        return stringBuffer.toString();
    }

    public final int a() {
        return this.a.size();
    }

    public final Object a(int i) {
        Object obj = (i < 0 || i >= this.a.size()) ? null : this.a.get(i);
        if (obj != null) {
            return obj;
        }
        StringBuilder sb = new StringBuilder("JSONArray[");
        sb.append(i);
        sb.append("] not found.");
        throw new JSONException(sb.toString());
    }

    public String toString() {
        try {
            StringBuilder sb = new StringBuilder("[");
            sb.append(a((String) ","));
            sb.append(']');
            return sb.toString();
        } catch (Exception unused) {
            return null;
        }
    }
}
