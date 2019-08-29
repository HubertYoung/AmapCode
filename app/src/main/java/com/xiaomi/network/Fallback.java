package com.xiaomi.network;

import android.text.TextUtils;
import com.alipay.sdk.app.statistic.c;
import com.xiaomi.channel.commonutils.string.d;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;

public class Fallback {
    public String a = "";
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    protected String h;
    private long i;
    private ArrayList<c> j = new ArrayList<>();
    private String k;
    private double l = 0.1d;
    private String m = "s.mi1.cc";
    private long n = 86400000;

    public Fallback(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("the host is empty");
        }
        this.i = System.currentTimeMillis();
        this.j.add(new c(str, -1));
        this.a = HostManager.getActiveNetworkLabel();
        this.b = str;
    }

    private synchronized void d(String str) {
        Iterator<c> it = this.j.iterator();
        while (it.hasNext()) {
            if (TextUtils.equals(it.next().a, str)) {
                it.remove();
            }
        }
    }

    public synchronized Fallback a(JSONObject jSONObject) {
        this.a = jSONObject.optString(c.a);
        this.n = jSONObject.getLong("ttl");
        this.l = jSONObject.getDouble("pct");
        this.i = jSONObject.getLong("ts");
        this.d = jSONObject.optString("city");
        this.c = jSONObject.optString("prv");
        this.g = jSONObject.optString("cty");
        this.e = jSONObject.optString("isp");
        this.f = jSONObject.optString(OnNativeInvokeListener.ARG_IP);
        this.b = jSONObject.optString("host");
        this.h = jSONObject.optString(xf.a);
        JSONArray jSONArray = jSONObject.getJSONArray("fbs");
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            a(new c().a(jSONArray.getJSONObject(i2)));
        }
        return this;
    }

    public ArrayList<String> a(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("the url is empty.");
        }
        URL url = new URL(str);
        if (TextUtils.equals(url.getHost(), this.b)) {
            ArrayList<String> arrayList = new ArrayList<>();
            Iterator<String> it = a(true).iterator();
            while (it.hasNext()) {
                Host a2 = Host.a(it.next(), url.getPort());
                arrayList.add(new URL(url.getProtocol(), a2.b(), a2.a(), url.getFile()).toString());
            }
            return arrayList;
        }
        throw new IllegalArgumentException("the url is not supported by the fallback");
    }

    public synchronized ArrayList<String> a(boolean z) {
        ArrayList<String> arrayList;
        String str;
        c[] cVarArr = new c[this.j.size()];
        this.j.toArray(cVarArr);
        Arrays.sort(cVarArr);
        arrayList = new ArrayList<>();
        int length = cVarArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            c cVar = cVarArr[i2];
            if (z) {
                str = cVar.a;
            } else {
                int indexOf = cVar.a.indexOf(":");
                str = indexOf != -1 ? cVar.a.substring(0, indexOf) : cVar.a;
            }
            arrayList.add(str);
        }
        return arrayList;
    }

    public void a(double d2) {
        this.l = d2;
    }

    public void a(long j2) {
        if (j2 <= 0) {
            throw new IllegalArgumentException("the duration is invalid ".concat(String.valueOf(j2)));
        }
        this.n = j2;
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(c cVar) {
        d(cVar.a);
        this.j.add(cVar);
    }

    public void a(String str, int i2, long j2, long j3, Exception exc) {
        AccessHistory accessHistory = new AccessHistory(i2, j2, j3, exc);
        a(str, accessHistory);
    }

    public void a(String str, long j2, long j3) {
        try {
            b(new URL(str).getHost(), j2, j3);
        } catch (MalformedURLException unused) {
        }
    }

    public void a(String str, long j2, long j3, Exception exc) {
        try {
            b(new URL(str).getHost(), j2, j3, exc);
        } catch (MalformedURLException unused) {
        }
    }

    public synchronized void a(String str, AccessHistory accessHistory) {
        Iterator<c> it = this.j.iterator();
        while (it.hasNext()) {
            c next = it.next();
            if (TextUtils.equals(str, next.a)) {
                next.a(accessHistory);
                return;
            }
        }
    }

    public synchronized void a(String[] strArr) {
        int i2;
        int size = this.j.size() - 1;
        while (true) {
            i2 = 0;
            if (size < 0) {
                break;
            }
            int length = strArr.length;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                if (TextUtils.equals(this.j.get(size).a, strArr[i2])) {
                    this.j.remove(size);
                    break;
                }
                i2++;
            }
            size--;
        }
        Iterator<c> it = this.j.iterator();
        int i3 = 0;
        while (it.hasNext()) {
            c next = it.next();
            if (next.b > i3) {
                i3 = next.b;
            }
        }
        while (i2 < strArr.length) {
            a(new c(strArr[i2], (strArr.length + i3) - i2));
            i2++;
        }
    }

    public boolean a() {
        return TextUtils.equals(this.a, HostManager.getActiveNetworkLabel());
    }

    public boolean a(Fallback fallback) {
        return TextUtils.equals(this.a, fallback.a);
    }

    public synchronized void b(String str) {
        a(new c(str));
    }

    public void b(String str, long j2, long j3) {
        a(str, 0, j2, j3, null);
    }

    public void b(String str, long j2, long j3, Exception exc) {
        a(str, -1, j2, j3, exc);
    }

    public boolean b() {
        return System.currentTimeMillis() - this.i < this.n;
    }

    public void c(String str) {
        this.m = str;
    }

    /* access modifiers changed from: 0000 */
    public boolean c() {
        long j2 = 864000000;
        if (864000000 < this.n) {
            j2 = this.n;
        }
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis - this.i > j2 || (currentTimeMillis - this.i > this.n && this.a.startsWith("WIFI-"));
    }

    public synchronized ArrayList<String> d() {
        return a(false);
    }

    public synchronized String e() {
        if (!TextUtils.isEmpty(this.k)) {
            return this.k;
        } else if (TextUtils.isEmpty(this.e)) {
            return "hardcode_isp";
        } else {
            this.k = d.a((Object[]) new String[]{this.e, this.c, this.d, this.g, this.f}, (String) "_");
            return this.k;
        }
    }

    public synchronized JSONObject f() {
        JSONObject jSONObject;
        jSONObject = new JSONObject();
        jSONObject.put(c.a, this.a);
        jSONObject.put("ttl", this.n);
        jSONObject.put("pct", this.l);
        jSONObject.put("ts", this.i);
        jSONObject.put("city", this.d);
        jSONObject.put("prv", this.c);
        jSONObject.put("cty", this.g);
        jSONObject.put("isp", this.e);
        jSONObject.put(OnNativeInvokeListener.ARG_IP, this.f);
        jSONObject.put("host", this.b);
        jSONObject.put(xf.a, this.h);
        JSONArray jSONArray = new JSONArray();
        Iterator<c> it = this.j.iterator();
        while (it.hasNext()) {
            jSONArray.put(it.next().a());
        }
        jSONObject.put("fbs", jSONArray);
        return jSONObject;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append("\n");
        sb.append(e());
        Iterator<c> it = this.j.iterator();
        while (it.hasNext()) {
            sb.append("\n");
            sb.append(it.next().toString());
        }
        sb.append("\n");
        return sb.toString();
    }
}
