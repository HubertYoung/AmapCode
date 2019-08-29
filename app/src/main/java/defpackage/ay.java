package defpackage;

import android.text.TextUtils;
import anet.channel.request.BodyEntry;
import anet.channel.request.ByteArrayEntry;
import anet.channel.statist.RequestStatistic;
import com.alipay.mobile.common.transport.http.RequestMethodConstants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/* renamed from: ay reason: default package */
/* compiled from: Request */
public final class ay {
    public cs a;
    public String b;
    public Map<String, String> c;
    public boolean d;
    public String e;
    public int f;
    public int g;
    public int h;
    public HostnameVerifier i;
    public SSLSocketFactory j;
    public final RequestStatistic k;
    private cs l;
    private cs m;
    private URL n;
    private Map<String, String> o;
    private String p;
    private BodyEntry q;
    private String r;

    /* renamed from: ay$a */
    /* compiled from: Request */
    public static class a {
        cs a;
        cs b;
        String c = "GET";
        Map<String, String> d = new HashMap();
        Map<String, String> e;
        String f;
        public BodyEntry g;
        public boolean h = true;
        public int i = 0;
        public HostnameVerifier j;
        public SSLSocketFactory k;
        public String l;
        public String m;
        int n = 10000;
        int o = 10000;
        public RequestStatistic p = null;

        public final a a(cs csVar) {
            this.a = csVar;
            this.b = null;
            return this;
        }

        public final a a(String str) {
            this.a = cs.a(str);
            this.b = null;
            if (this.a != null) {
                return this;
            }
            throw new IllegalArgumentException("toURL is invalid! toURL = ".concat(String.valueOf(str)));
        }

        public final a b(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("method is null or empty");
            }
            if (!"GET".equalsIgnoreCase(str)) {
                if ("POST".equalsIgnoreCase(str)) {
                    this.c = "POST";
                } else if (RequestMethodConstants.OPTIONS_METHOD.equalsIgnoreCase(str)) {
                    this.c = RequestMethodConstants.OPTIONS_METHOD;
                } else if (RequestMethodConstants.HEAD_METHOD.equalsIgnoreCase(str)) {
                    this.c = RequestMethodConstants.HEAD_METHOD;
                } else if (RequestMethodConstants.PUT_METHOD.equalsIgnoreCase(str)) {
                    this.c = RequestMethodConstants.PUT_METHOD;
                } else if (RequestMethodConstants.DELETE_METHOD.equalsIgnoreCase(str)) {
                    this.c = RequestMethodConstants.DELETE_METHOD;
                }
                return this;
            }
            this.c = "GET";
            return this;
        }

        public final a a(Map<String, String> map) {
            this.d.clear();
            if (map != null) {
                this.d.putAll(map);
            }
            return this;
        }

        public final a a(String str, String str2) {
            this.d.put(str, str2);
            return this;
        }

        public final a b(Map<String, String> map) {
            this.e = map;
            this.b = null;
            return this;
        }

        public final a c(String str) {
            this.f = str;
            this.b = null;
            return this;
        }

        public final a a(int i2) {
            if (i2 > 0) {
                this.o = i2;
            }
            return this;
        }

        public final a b(int i2) {
            if (i2 > 0) {
                this.n = i2;
            }
            return this;
        }

        public final ay a() {
            if (this.g == null && this.e == null && b.a(this.c)) {
                StringBuilder sb = new StringBuilder("method ");
                sb.append(this.c);
                sb.append(" must have a request body");
                cl.d("awcn.Request", sb.toString(), null, new Object[0]);
            }
            if (this.g != null && !b.b(this.c)) {
                StringBuilder sb2 = new StringBuilder("method ");
                sb2.append(this.c);
                sb2.append(" should not have a request body");
                cl.d("awcn.Request", sb2.toString(), null, new Object[0]);
                this.g = null;
            }
            if (!(this.g == null || this.g.a() == null)) {
                a("Content-Type", this.g.a());
            }
            return new ay(this, 0);
        }
    }

    /* renamed from: ay$b */
    /* compiled from: Request */
    public static final class b {
        static boolean a(String str) {
            return str.equals("POST") || str.equals(RequestMethodConstants.PUT_METHOD);
        }

        static boolean b(String str) {
            return a(str) || str.equals(RequestMethodConstants.DELETE_METHOD) || str.equals(RequestMethodConstants.OPTIONS_METHOD);
        }
    }

    /* synthetic */ ay(a aVar, byte b2) {
        this(aVar);
    }

    private ay(a aVar) {
        RequestStatistic requestStatistic;
        this.b = "GET";
        this.d = true;
        this.f = 0;
        this.g = 10000;
        this.h = 10000;
        this.b = aVar.c;
        this.c = aVar.d;
        this.o = aVar.e;
        this.q = aVar.g;
        this.p = aVar.f;
        this.d = aVar.h;
        this.f = aVar.i;
        this.i = aVar.j;
        this.j = aVar.k;
        this.r = aVar.l;
        this.e = aVar.m;
        this.g = aVar.n;
        this.h = aVar.o;
        this.l = aVar.a;
        this.a = aVar.b;
        if (this.a == null) {
            String a2 = ci.a(this.o, e());
            if (!TextUtils.isEmpty(a2)) {
                if (!b.a(this.b) || this.q != null) {
                    String str = this.l.e;
                    StringBuilder sb = new StringBuilder(str);
                    if (sb.indexOf("?") == -1) {
                        sb.append('?');
                    } else if (str.charAt(str.length() - 1) != '&') {
                        sb.append('&');
                    }
                    sb.append(a2);
                    cs a3 = cs.a(sb.toString());
                    if (a3 != null) {
                        this.a = a3;
                    }
                } else {
                    try {
                        this.q = new ByteArrayEntry(a2.getBytes(e()));
                        Map<String, String> map = this.c;
                        StringBuilder sb2 = new StringBuilder("application/x-www-form-urlencoded; charset=");
                        sb2.append(e());
                        map.put("Content-Type", sb2.toString());
                    } catch (UnsupportedEncodingException unused) {
                    }
                }
            }
            if (this.a == null) {
                this.a = this.l;
            }
        }
        if (aVar.p != null) {
            requestStatistic = aVar.p;
        } else {
            requestStatistic = new RequestStatistic(this.a.b, this.r);
        }
        this.k = requestStatistic;
    }

    public final a a() {
        a aVar = new a();
        aVar.c = this.b;
        aVar.d = this.c;
        aVar.e = this.o;
        aVar.g = this.q;
        aVar.f = this.p;
        aVar.h = this.d;
        aVar.i = this.f;
        aVar.j = this.i;
        aVar.k = this.j;
        aVar.a = this.l;
        aVar.b = this.a;
        aVar.l = this.r;
        aVar.m = this.e;
        aVar.n = this.g;
        aVar.o = this.h;
        aVar.p = this.k;
        return aVar;
    }

    public final URL b() {
        if (this.n == null) {
            this.n = (this.m != null ? this.m : this.a).a();
        }
        return this.n;
    }

    public final void a(String str, int i2) {
        if (str != null) {
            if (this.m == null) {
                this.m = new cs(this.a);
            }
            this.m.a(str, i2);
        } else {
            this.m = null;
        }
        this.n = null;
        this.k.setIPAndPort(str, i2);
    }

    public final void a(boolean z) {
        if (this.m == null) {
            this.m = new cs(this.a);
        }
        cs csVar = this.m;
        String str = z ? "https" : "http";
        if (!csVar.g && !str.equalsIgnoreCase(csVar.a)) {
            csVar.a = str;
            csVar.e = cz.a(str, ":", csVar.e.substring(csVar.e.indexOf("//")));
            csVar.f = cz.a(str, ":", csVar.f.substring(csVar.e.indexOf("//")));
        }
        this.n = null;
    }

    private String e() {
        return this.p != null ? this.p : "UTF-8";
    }

    public final int a(OutputStream outputStream) throws IOException {
        if (this.q != null) {
            return this.q.a(outputStream);
        }
        return 0;
    }

    public final byte[] c() {
        if (this.q == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(128);
        try {
            a((OutputStream) byteArrayOutputStream);
        } catch (IOException unused) {
        }
        return byteArrayOutputStream.toByteArray();
    }

    public final boolean d() {
        return this.q != null;
    }
}
