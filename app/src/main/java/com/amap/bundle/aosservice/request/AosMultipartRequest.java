package com.amap.bundle.aosservice.request;

import android.text.TextUtils;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AosMultipartRequest extends AosRequest {
    protected final List<a> a = new LinkedList();
    protected int b = 0;
    protected int c = 0;
    protected String d;
    protected bpi e;

    public static class a {
        public String a;
        public File b;
        public String c;

        public a(File file, String str) {
            this.b = file;
            this.a = str;
        }

        public a(String str, String str2) {
            this.c = str;
            this.a = str2;
        }
    }

    public AosMultipartRequest() {
        setMethod(1);
        setChannel(1);
    }

    public final void a() {
        this.b = 2;
    }

    public final void b() {
        this.c = 2;
    }

    public final void a(String str, File file) {
        this.a.add(new a(file, str));
    }

    public final void a(String str, String str2) {
        this.a.add(new a(str2, str));
    }

    /* access modifiers changed from: protected */
    public bph createHttpRequest() {
        bpi bpi = new bpi();
        if (this.a.size() > 0) {
            for (a next : this.a) {
                if (next.b != null) {
                    bpi.a(next.a, next.b);
                } else if (!TextUtils.isEmpty(next.c)) {
                    bpi.a(next.a, next.c);
                }
            }
        }
        this.e = bpi;
        return bpi;
    }

    /* access modifiers changed from: protected */
    public void processParams(jc jcVar, Map<String, String> map, Map<String, String> map2) {
        if (this.c == 2 || this.b == 2) {
            HashMap hashMap = new HashMap();
            HashMap hashMap2 = new HashMap();
            boolean z = false;
            if (this.c != 2) {
                hashMap.putAll(map);
            } else {
                hashMap2.putAll(map);
            }
            if (this.b != 2) {
                z = true;
            }
            if (z) {
                hashMap.putAll(map2);
            } else {
                hashMap2.putAll(map2);
            }
            if (!hashMap.isEmpty()) {
                for (Entry entry : hashMap.entrySet()) {
                    if (!TextUtils.isEmpty((CharSequence) entry.getKey())) {
                        jcVar.a((String) entry.getKey(), (String) entry.getValue());
                    }
                }
            }
            if (!hashMap2.isEmpty()) {
                String paramsToString = paramsToString(hashMap2);
                is a2 = ip.a();
                if (this.mEncryptStrategy == 2) {
                    paramsToString = a2.a(paramsToString);
                    if (this.e != null) {
                        this.e.a((String) "ent", String.valueOf(this.mEncryptStrategy));
                    }
                }
                if (this.e != null) {
                    this.e.a((String) "in", paramsToString);
                }
                this.d = paramsToString;
            }
            return;
        }
        super.processParams(jcVar, map, map2);
    }

    /* access modifiers changed from: protected */
    public void securityGuardSign(bph bph, String str) {
        securityGuardSign(bph, str, this.d);
    }
}
