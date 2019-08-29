package com.google.gson.b.a;

import com.alipay.mobile.antui.theme.AUThemeManager;
import com.google.gson.Gson;
import com.google.gson.b.g;
import com.google.gson.d.b;
import com.google.gson.d.c;
import com.google.gson.j;
import com.google.gson.k;
import com.google.gson.l;
import com.google.gson.m;
import com.google.gson.o;
import com.google.gson.r;
import com.google.gson.t;
import com.google.gson.u;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/* compiled from: TypeAdapters */
public final class n {
    public static final t<String> A = new t<String>() {
        /* renamed from: a */
        public final String b(com.google.gson.d.a aVar) throws IOException {
            b f = aVar.f();
            if (f == b.NULL) {
                aVar.j();
                return null;
            } else if (f == b.BOOLEAN) {
                return Boolean.toString(aVar.i());
            } else {
                return aVar.h();
            }
        }

        public final void a(c cVar, String str) throws IOException {
            cVar.b(str);
        }
    };
    public static final t<BigDecimal> B = new t<BigDecimal>() {
        /* renamed from: a */
        public final BigDecimal b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() == b.NULL) {
                aVar.j();
                return null;
            }
            try {
                return new BigDecimal(aVar.h());
            } catch (NumberFormatException e) {
                throw new r((Throwable) e);
            }
        }

        public final void a(c cVar, BigDecimal bigDecimal) throws IOException {
            cVar.a((Number) bigDecimal);
        }
    };
    public static final t<BigInteger> C = new t<BigInteger>() {
        /* renamed from: a */
        public final BigInteger b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() == b.NULL) {
                aVar.j();
                return null;
            }
            try {
                return new BigInteger(aVar.h());
            } catch (NumberFormatException e) {
                throw new r((Throwable) e);
            }
        }

        public final void a(c cVar, BigInteger bigInteger) throws IOException {
            cVar.a((Number) bigInteger);
        }
    };
    public static final u D = a(String.class, A);
    public static final t<StringBuilder> E = new t<StringBuilder>() {
        /* renamed from: a */
        public final StringBuilder b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() != b.NULL) {
                return new StringBuilder(aVar.h());
            }
            aVar.j();
            return null;
        }

        public final void a(c cVar, StringBuilder sb) throws IOException {
            cVar.b(sb == null ? null : sb.toString());
        }
    };
    public static final u F = a(StringBuilder.class, E);
    public static final t<StringBuffer> G = new t<StringBuffer>() {
        /* renamed from: a */
        public final StringBuffer b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() != b.NULL) {
                return new StringBuffer(aVar.h());
            }
            aVar.j();
            return null;
        }

        public final void a(c cVar, StringBuffer stringBuffer) throws IOException {
            cVar.b(stringBuffer == null ? null : stringBuffer.toString());
        }
    };
    public static final u H = a(StringBuffer.class, G);
    public static final t<URL> I = new t<URL>() {
        /* renamed from: a */
        public final URL b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() == b.NULL) {
                aVar.j();
                return null;
            }
            String h = aVar.h();
            if ("null".equals(h)) {
                return null;
            }
            return new URL(h);
        }

        public final void a(c cVar, URL url) throws IOException {
            cVar.b(url == null ? null : url.toExternalForm());
        }
    };
    public static final u J = a(URL.class, I);
    public static final t<URI> K = new t<URI>() {
        /* renamed from: a */
        public final URI b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() == b.NULL) {
                aVar.j();
                return null;
            }
            try {
                String h = aVar.h();
                if ("null".equals(h)) {
                    return null;
                }
                return new URI(h);
            } catch (URISyntaxException e) {
                throw new k((Throwable) e);
            }
        }

        public final void a(c cVar, URI uri) throws IOException {
            cVar.b(uri == null ? null : uri.toASCIIString());
        }
    };
    public static final u L = a(URI.class, K);
    public static final t<InetAddress> M = new t<InetAddress>() {
        /* renamed from: a */
        public final InetAddress b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() != b.NULL) {
                return InetAddress.getByName(aVar.h());
            }
            aVar.j();
            return null;
        }

        public final void a(c cVar, InetAddress inetAddress) throws IOException {
            cVar.b(inetAddress == null ? null : inetAddress.getHostAddress());
        }
    };
    public static final u N = b(InetAddress.class, M);
    public static final t<UUID> O = new t<UUID>() {
        /* renamed from: a */
        public final UUID b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() != b.NULL) {
                return UUID.fromString(aVar.h());
            }
            aVar.j();
            return null;
        }

        public final void a(c cVar, UUID uuid) throws IOException {
            cVar.b(uuid == null ? null : uuid.toString());
        }
    };
    public static final u P = a(UUID.class, O);
    public static final t<Currency> Q = new t<Currency>() {
        /* renamed from: a */
        public final Currency b(com.google.gson.d.a aVar) throws IOException {
            return Currency.getInstance(aVar.h());
        }

        public final void a(c cVar, Currency currency) throws IOException {
            cVar.b(currency.getCurrencyCode());
        }
    }.a();
    public static final u R = a(Currency.class, Q);
    public static final u S = new u() {
        public final <T> t<T> a(Gson gson, com.google.gson.c.a<T> aVar) {
            if (aVar.a() != Timestamp.class) {
                return null;
            }
            final t<Date> adapter = gson.getAdapter(Date.class);
            return new t<Timestamp>() {
                /* renamed from: a */
                public Timestamp b(com.google.gson.d.a aVar) throws IOException {
                    Date date = (Date) adapter.b(aVar);
                    if (date != null) {
                        return new Timestamp(date.getTime());
                    }
                    return null;
                }

                public void a(c cVar, Timestamp timestamp) throws IOException {
                    adapter.a(cVar, timestamp);
                }
            };
        }
    };
    public static final t<Calendar> T = new t<Calendar>() {
        /* renamed from: a */
        public final Calendar b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() == b.NULL) {
                aVar.j();
                return null;
            }
            aVar.c();
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            while (aVar.f() != b.END_OBJECT) {
                String g = aVar.g();
                int m = aVar.m();
                if ("year".equals(g)) {
                    i = m;
                } else if ("month".equals(g)) {
                    i2 = m;
                } else if ("dayOfMonth".equals(g)) {
                    i3 = m;
                } else if ("hourOfDay".equals(g)) {
                    i4 = m;
                } else if ("minute".equals(g)) {
                    i5 = m;
                } else if (AUThemeManager.THEMEKEY_SECOND.equals(g)) {
                    i6 = m;
                }
            }
            aVar.d();
            GregorianCalendar gregorianCalendar = new GregorianCalendar(i, i2, i3, i4, i5, i6);
            return gregorianCalendar;
        }

        public final void a(c cVar, Calendar calendar) throws IOException {
            if (calendar == null) {
                cVar.f();
                return;
            }
            cVar.d();
            cVar.a((String) "year");
            cVar.a((long) calendar.get(1));
            cVar.a((String) "month");
            cVar.a((long) calendar.get(2));
            cVar.a((String) "dayOfMonth");
            cVar.a((long) calendar.get(5));
            cVar.a((String) "hourOfDay");
            cVar.a((long) calendar.get(11));
            cVar.a((String) "minute");
            cVar.a((long) calendar.get(12));
            cVar.a((String) AUThemeManager.THEMEKEY_SECOND);
            cVar.a((long) calendar.get(13));
            cVar.e();
        }
    };
    public static final u U = b(Calendar.class, GregorianCalendar.class, T);
    public static final t<Locale> V = new t<Locale>() {
        /* renamed from: a */
        public final Locale b(com.google.gson.d.a aVar) throws IOException {
            String str = null;
            if (aVar.f() == b.NULL) {
                aVar.j();
                return null;
            }
            StringTokenizer stringTokenizer = new StringTokenizer(aVar.h(), "_");
            String nextToken = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
            String nextToken2 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
            if (stringTokenizer.hasMoreElements()) {
                str = stringTokenizer.nextToken();
            }
            if (nextToken2 == null && str == null) {
                return new Locale(nextToken);
            }
            if (str == null) {
                return new Locale(nextToken, nextToken2);
            }
            return new Locale(nextToken, nextToken2, str);
        }

        public final void a(c cVar, Locale locale) throws IOException {
            cVar.b(locale == null ? null : locale.toString());
        }
    };
    public static final u W = a(Locale.class, V);
    public static final t<j> X = new t<j>() {
        /* renamed from: a */
        public final j b(com.google.gson.d.a aVar) throws IOException {
            switch (AnonymousClass29.a[aVar.f().ordinal()]) {
                case 1:
                    return new o((Number) new g(aVar.h()));
                case 2:
                    return new o(Boolean.valueOf(aVar.i()));
                case 3:
                    return new o(aVar.h());
                case 4:
                    aVar.j();
                    return l.a;
                case 5:
                    com.google.gson.g gVar = new com.google.gson.g();
                    aVar.a();
                    while (aVar.e()) {
                        gVar.a(b(aVar));
                    }
                    aVar.b();
                    return gVar;
                case 6:
                    m mVar = new m();
                    aVar.c();
                    while (aVar.e()) {
                        mVar.a(aVar.g(), b(aVar));
                    }
                    aVar.d();
                    return mVar;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public final void a(c cVar, j jVar) throws IOException {
            if (jVar == null || jVar.j()) {
                cVar.f();
            } else if (jVar.i()) {
                o m = jVar.m();
                if (m.p()) {
                    cVar.a(m.a());
                } else if (m.o()) {
                    cVar.a(m.f());
                } else {
                    cVar.b(m.b());
                }
            } else if (jVar.g()) {
                cVar.b();
                Iterator<j> it = jVar.l().iterator();
                while (it.hasNext()) {
                    a(cVar, it.next());
                }
                cVar.c();
            } else if (jVar.h()) {
                cVar.d();
                for (Entry next : jVar.k().o()) {
                    cVar.a((String) next.getKey());
                    a(cVar, (j) next.getValue());
                }
                cVar.e();
            } else {
                StringBuilder sb = new StringBuilder("Couldn't write ");
                sb.append(jVar.getClass());
                throw new IllegalArgumentException(sb.toString());
            }
        }
    };
    public static final u Y = b(j.class, X);
    public static final u Z = new u() {
        public final <T> t<T> a(Gson gson, com.google.gson.c.a<T> aVar) {
            Class<? super T> a = aVar.a();
            if (!Enum.class.isAssignableFrom(a) || a == Enum.class) {
                return null;
            }
            if (!a.isEnum()) {
                a = a.getSuperclass();
            }
            return new a(a);
        }
    };
    public static final t<Class> a = new t<Class>() {
        public final void a(c cVar, Class cls) throws IOException {
            StringBuilder sb = new StringBuilder("Attempted to serialize java.lang.Class: ");
            sb.append(cls.getName());
            sb.append(". Forgot to register a type adapter?");
            throw new UnsupportedOperationException(sb.toString());
        }

        /* renamed from: a */
        public final Class b(com.google.gson.d.a aVar) throws IOException {
            throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }
    }.a();
    public static final u b = a(Class.class, a);
    public static final t<BitSet> c = new t<BitSet>() {
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0036, code lost:
            if (java.lang.Integer.parseInt(r1) != 0) goto L_0x0056;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0039, code lost:
            r5 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0054, code lost:
            if (r7.m() != 0) goto L_0x0056;
         */
        /* renamed from: a */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.util.BitSet b(com.google.gson.d.a r7) throws java.io.IOException {
            /*
                r6 = this;
                java.util.BitSet r0 = new java.util.BitSet
                r0.<init>()
                r7.a()
                com.google.gson.d.b r1 = r7.f()
                r2 = 0
                r3 = 0
            L_0x000e:
                com.google.gson.d.b r4 = com.google.gson.d.b.END_ARRAY
                if (r1 == r4) goto L_0x0062
                int[] r4 = com.google.gson.b.a.n.AnonymousClass29.a
                int r5 = r1.ordinal()
                r4 = r4[r5]
                r5 = 1
                switch(r4) {
                    case 1: goto L_0x0050;
                    case 2: goto L_0x004b;
                    case 3: goto L_0x002e;
                    default: goto L_0x001e;
                }
            L_0x001e:
                com.google.gson.r r7 = new com.google.gson.r
                java.lang.String r0 = "Invalid bitset value type: "
                java.lang.String r1 = java.lang.String.valueOf(r1)
                java.lang.String r0 = r0.concat(r1)
                r7.<init>(r0)
                throw r7
            L_0x002e:
                java.lang.String r1 = r7.h()
                int r4 = java.lang.Integer.parseInt(r1)     // Catch:{ NumberFormatException -> 0x003b }
                if (r4 == 0) goto L_0x0039
                goto L_0x0056
            L_0x0039:
                r5 = 0
                goto L_0x0056
            L_0x003b:
                com.google.gson.r r7 = new com.google.gson.r
                java.lang.String r0 = "Error: Expecting: bitset number value (1, 0), Found: "
                java.lang.String r1 = java.lang.String.valueOf(r1)
                java.lang.String r0 = r0.concat(r1)
                r7.<init>(r0)
                throw r7
            L_0x004b:
                boolean r5 = r7.i()
                goto L_0x0056
            L_0x0050:
                int r1 = r7.m()
                if (r1 == 0) goto L_0x0039
            L_0x0056:
                if (r5 == 0) goto L_0x005b
                r0.set(r3)
            L_0x005b:
                int r3 = r3 + 1
                com.google.gson.d.b r1 = r7.f()
                goto L_0x000e
            L_0x0062:
                r7.b()
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gson.b.a.n.AnonymousClass12.b(com.google.gson.d.a):java.util.BitSet");
        }

        public final void a(c cVar, BitSet bitSet) throws IOException {
            cVar.b();
            int length = bitSet.length();
            for (int i = 0; i < length; i++) {
                cVar.a(bitSet.get(i) ? 1 : 0);
            }
            cVar.c();
        }
    }.a();
    public static final u d = a(BitSet.class, c);
    public static final t<Boolean> e = new t<Boolean>() {
        /* renamed from: a */
        public final Boolean b(com.google.gson.d.a aVar) throws IOException {
            b f = aVar.f();
            if (f == b.NULL) {
                aVar.j();
                return null;
            } else if (f == b.STRING) {
                return Boolean.valueOf(Boolean.parseBoolean(aVar.h()));
            } else {
                return Boolean.valueOf(aVar.i());
            }
        }

        public final void a(c cVar, Boolean bool) throws IOException {
            cVar.a(bool);
        }
    };
    public static final t<Boolean> f = new t<Boolean>() {
        /* renamed from: a */
        public final Boolean b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() != b.NULL) {
                return Boolean.valueOf(aVar.h());
            }
            aVar.j();
            return null;
        }

        public final void a(c cVar, Boolean bool) throws IOException {
            cVar.b(bool == null ? "null" : bool.toString());
        }
    };
    public static final u g = a(Boolean.TYPE, Boolean.class, e);
    public static final t<Number> h = new t<Number>() {
        /* renamed from: a */
        public final Number b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() == b.NULL) {
                aVar.j();
                return null;
            }
            try {
                return Byte.valueOf((byte) aVar.m());
            } catch (NumberFormatException e) {
                throw new r((Throwable) e);
            }
        }

        public final void a(c cVar, Number number) throws IOException {
            cVar.a(number);
        }
    };
    public static final u i = a(Byte.TYPE, Byte.class, h);
    public static final t<Number> j = new t<Number>() {
        /* renamed from: a */
        public final Number b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() == b.NULL) {
                aVar.j();
                return null;
            }
            try {
                return Short.valueOf((short) aVar.m());
            } catch (NumberFormatException e) {
                throw new r((Throwable) e);
            }
        }

        public final void a(c cVar, Number number) throws IOException {
            cVar.a(number);
        }
    };
    public static final u k = a(Short.TYPE, Short.class, j);
    public static final t<Number> l = new t<Number>() {
        /* renamed from: a */
        public final Number b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() == b.NULL) {
                aVar.j();
                return null;
            }
            try {
                return Integer.valueOf(aVar.m());
            } catch (NumberFormatException e) {
                throw new r((Throwable) e);
            }
        }

        public final void a(c cVar, Number number) throws IOException {
            cVar.a(number);
        }
    };
    public static final u m = a(Integer.TYPE, Integer.class, l);
    public static final t<AtomicInteger> n = new t<AtomicInteger>() {
        /* renamed from: a */
        public final AtomicInteger b(com.google.gson.d.a aVar) throws IOException {
            try {
                return new AtomicInteger(aVar.m());
            } catch (NumberFormatException e) {
                throw new r((Throwable) e);
            }
        }

        public final void a(c cVar, AtomicInteger atomicInteger) throws IOException {
            cVar.a((long) atomicInteger.get());
        }
    }.a();
    public static final u o = a(AtomicInteger.class, n);
    public static final t<AtomicBoolean> p = new t<AtomicBoolean>() {
        /* renamed from: a */
        public final AtomicBoolean b(com.google.gson.d.a aVar) throws IOException {
            return new AtomicBoolean(aVar.i());
        }

        public final void a(c cVar, AtomicBoolean atomicBoolean) throws IOException {
            cVar.a(atomicBoolean.get());
        }
    }.a();
    public static final u q = a(AtomicBoolean.class, p);
    public static final t<AtomicIntegerArray> r = new t<AtomicIntegerArray>() {
        /* renamed from: a */
        public final AtomicIntegerArray b(com.google.gson.d.a aVar) throws IOException {
            ArrayList arrayList = new ArrayList();
            aVar.a();
            while (aVar.e()) {
                try {
                    arrayList.add(Integer.valueOf(aVar.m()));
                } catch (NumberFormatException e) {
                    throw new r((Throwable) e);
                }
            }
            aVar.b();
            int size = arrayList.size();
            AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(size);
            for (int i = 0; i < size; i++) {
                atomicIntegerArray.set(i, ((Integer) arrayList.get(i)).intValue());
            }
            return atomicIntegerArray;
        }

        public final void a(c cVar, AtomicIntegerArray atomicIntegerArray) throws IOException {
            cVar.b();
            int length = atomicIntegerArray.length();
            for (int i = 0; i < length; i++) {
                cVar.a((long) atomicIntegerArray.get(i));
            }
            cVar.c();
        }
    }.a();
    public static final u s = a(AtomicIntegerArray.class, r);
    public static final t<Number> t = new t<Number>() {
        /* renamed from: a */
        public final Number b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() == b.NULL) {
                aVar.j();
                return null;
            }
            try {
                return Long.valueOf(aVar.l());
            } catch (NumberFormatException e) {
                throw new r((Throwable) e);
            }
        }

        public final void a(c cVar, Number number) throws IOException {
            cVar.a(number);
        }
    };
    public static final t<Number> u = new t<Number>() {
        /* renamed from: a */
        public final Number b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() != b.NULL) {
                return Float.valueOf((float) aVar.k());
            }
            aVar.j();
            return null;
        }

        public final void a(c cVar, Number number) throws IOException {
            cVar.a(number);
        }
    };
    public static final t<Number> v = new t<Number>() {
        /* renamed from: a */
        public final Number b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() != b.NULL) {
                return Double.valueOf(aVar.k());
            }
            aVar.j();
            return null;
        }

        public final void a(c cVar, Number number) throws IOException {
            cVar.a(number);
        }
    };
    public static final t<Number> w = new t<Number>() {
        /* renamed from: a */
        public final Number b(com.google.gson.d.a aVar) throws IOException {
            b f = aVar.f();
            int i = AnonymousClass29.a[f.ordinal()];
            if (i != 1) {
                switch (i) {
                    case 3:
                        break;
                    case 4:
                        aVar.j();
                        return null;
                    default:
                        throw new r("Expecting number, got: ".concat(String.valueOf(f)));
                }
            }
            return new g(aVar.h());
        }

        public final void a(c cVar, Number number) throws IOException {
            cVar.a(number);
        }
    };
    public static final u x = a(Number.class, w);
    public static final t<Character> y = new t<Character>() {
        /* renamed from: a */
        public final Character b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() == b.NULL) {
                aVar.j();
                return null;
            }
            String h = aVar.h();
            if (h.length() == 1) {
                return Character.valueOf(h.charAt(0));
            }
            throw new r("Expecting character, got: ".concat(String.valueOf(h)));
        }

        public final void a(c cVar, Character ch) throws IOException {
            cVar.b(ch == null ? null : String.valueOf(ch));
        }
    };
    public static final u z = a(Character.TYPE, Character.class, y);

    /* renamed from: com.google.gson.b.a.n$29 reason: invalid class name */
    /* compiled from: TypeAdapters */
    static /* synthetic */ class AnonymousClass29 {
        static final /* synthetic */ int[] a = new int[b.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(20:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|(3:19|20|22)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(22:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|22) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0056 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0062 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.google.gson.d.b[] r0 = com.google.gson.d.b.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.gson.d.b r1 = com.google.gson.d.b.NUMBER     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.gson.d.b r1 = com.google.gson.d.b.BOOLEAN     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.gson.d.b r1 = com.google.gson.d.b.STRING     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.google.gson.d.b r1 = com.google.gson.d.b.NULL     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.google.gson.d.b r1 = com.google.gson.d.b.BEGIN_ARRAY     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x004b }
                com.google.gson.d.b r1 = com.google.gson.d.b.BEGIN_OBJECT     // Catch:{ NoSuchFieldError -> 0x004b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0056 }
                com.google.gson.d.b r1 = com.google.gson.d.b.END_DOCUMENT     // Catch:{ NoSuchFieldError -> 0x0056 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0056 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0056 }
            L_0x0056:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0062 }
                com.google.gson.d.b r1 = com.google.gson.d.b.NAME     // Catch:{ NoSuchFieldError -> 0x0062 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0062 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0062 }
            L_0x0062:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x006e }
                com.google.gson.d.b r1 = com.google.gson.d.b.END_OBJECT     // Catch:{ NoSuchFieldError -> 0x006e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006e }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006e }
            L_0x006e:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x007a }
                com.google.gson.d.b r1 = com.google.gson.d.b.END_ARRAY     // Catch:{ NoSuchFieldError -> 0x007a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x007a }
                r2 = 10
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x007a }
            L_0x007a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gson.b.a.n.AnonymousClass29.<clinit>():void");
        }
    }

    /* compiled from: TypeAdapters */
    static final class a<T extends Enum<T>> extends t<T> {
        private final Map<String, T> a = new HashMap();
        private final Map<T, String> b = new HashMap();

        public a(Class<T> cls) {
            Enum[] enumArr;
            try {
                for (Enum enumR : (Enum[]) cls.getEnumConstants()) {
                    String name = enumR.name();
                    com.google.gson.a.c cVar = (com.google.gson.a.c) cls.getField(name).getAnnotation(com.google.gson.a.c.class);
                    if (cVar != null) {
                        name = cVar.a();
                        for (String put : cVar.b()) {
                            this.a.put(put, enumR);
                        }
                    }
                    this.a.put(name, enumR);
                    this.b.put(enumR, name);
                }
            } catch (NoSuchFieldException e) {
                throw new AssertionError(e);
            }
        }

        /* renamed from: a */
        public final T b(com.google.gson.d.a aVar) throws IOException {
            if (aVar.f() != b.NULL) {
                return (Enum) this.a.get(aVar.h());
            }
            aVar.j();
            return null;
        }

        public final void a(c cVar, T t) throws IOException {
            cVar.b(t == null ? null : this.b.get(t));
        }
    }

    public static <TT> u a(final Class<TT> cls, final t<TT> tVar) {
        return new u() {
            public final <T> t<T> a(Gson gson, com.google.gson.c.a<T> aVar) {
                if (aVar.a() == cls) {
                    return tVar;
                }
                return null;
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("Factory[type=");
                sb.append(cls.getName());
                sb.append(",adapter=");
                sb.append(tVar);
                sb.append("]");
                return sb.toString();
            }
        };
    }

    public static <TT> u a(final Class<TT> cls, final Class<TT> cls2, final t<? super TT> tVar) {
        return new u() {
            public final <T> t<T> a(Gson gson, com.google.gson.c.a<T> aVar) {
                Class<? super T> a2 = aVar.a();
                if (a2 == cls || a2 == cls2) {
                    return tVar;
                }
                return null;
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("Factory[type=");
                sb.append(cls2.getName());
                sb.append("+");
                sb.append(cls.getName());
                sb.append(",adapter=");
                sb.append(tVar);
                sb.append("]");
                return sb.toString();
            }
        };
    }

    public static <TT> u b(final Class<TT> cls, final Class<? extends TT> cls2, final t<? super TT> tVar) {
        return new u() {
            public final <T> t<T> a(Gson gson, com.google.gson.c.a<T> aVar) {
                Class<? super T> a2 = aVar.a();
                if (a2 == cls || a2 == cls2) {
                    return tVar;
                }
                return null;
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("Factory[type=");
                sb.append(cls.getName());
                sb.append("+");
                sb.append(cls2.getName());
                sb.append(",adapter=");
                sb.append(tVar);
                sb.append("]");
                return sb.toString();
            }
        };
    }

    public static <T1> u b(final Class<T1> cls, final t<T1> tVar) {
        return new u() {
            public final <T2> t<T2> a(Gson gson, com.google.gson.c.a<T2> aVar) {
                final Class<? super T> a2 = aVar.a();
                if (!cls.isAssignableFrom(a2)) {
                    return null;
                }
                return new t<T1>() {
                    public void a(c cVar, T1 t1) throws IOException {
                        tVar.a(cVar, t1);
                    }

                    public T1 b(com.google.gson.d.a aVar) throws IOException {
                        T1 b2 = tVar.b(aVar);
                        if (b2 == null || a2.isInstance(b2)) {
                            return b2;
                        }
                        StringBuilder sb = new StringBuilder("Expected a ");
                        sb.append(a2.getName());
                        sb.append(" but was ");
                        sb.append(b2.getClass().getName());
                        throw new r(sb.toString());
                    }
                };
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("Factory[typeHierarchy=");
                sb.append(cls.getName());
                sb.append(",adapter=");
                sb.append(tVar);
                sb.append("]");
                return sb.toString();
            }
        };
    }
}
