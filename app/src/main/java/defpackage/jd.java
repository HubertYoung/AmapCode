package defpackage;

import android.net.Uri;
import android.support.v4.media.TransportMediator;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

/* renamed from: jd reason: default package */
/* compiled from: URLEncodedUtils */
public final class jd {
    private static final char[] a = {'&'};
    private static final BitSet b = new BitSet(256);
    private static final BitSet c = new BitSet(256);
    private static final BitSet d = new BitSet(256);
    private static final BitSet e = new BitSet(256);
    private static final BitSet f = new BitSet(256);
    private static final BitSet g = new BitSet(256);
    private static final BitSet h = new BitSet(256);

    static {
        for (int i = 97; i <= 122; i++) {
            b.set(i);
        }
        for (int i2 = 65; i2 <= 90; i2++) {
            b.set(i2);
        }
        for (int i3 = 48; i3 <= 57; i3++) {
            b.set(i3);
        }
        b.set(95);
        b.set(45);
        b.set(46);
        b.set(42);
        h.or(b);
        b.set(33);
        b.set(TransportMediator.KEYCODE_MEDIA_PLAY);
        b.set(39);
        b.set(40);
        b.set(41);
        c.set(44);
        c.set(59);
        c.set(58);
        c.set(36);
        c.set(38);
        c.set(43);
        c.set(61);
        d.or(b);
        d.or(c);
        e.or(b);
        e.set(47);
        e.set(59);
        e.set(58);
        e.set(64);
        e.set(38);
        e.set(61);
        e.set(43);
        e.set(36);
        e.set(44);
        g.set(59);
        g.set(47);
        g.set(63);
        g.set(58);
        g.set(64);
        g.set(38);
        g.set(61);
        g.set(43);
        g.set(36);
        g.set(44);
        g.set(91);
        g.set(93);
        f.or(g);
        f.or(b);
    }

    public static List<ja> a(String str) {
        if (str == null) {
            return Collections.emptyList();
        }
        iw iwVar = iw.a;
        ix ixVar = new ix(str.length());
        ixVar.a(str);
        jb jbVar = new jb(ixVar.b);
        ArrayList arrayList = new ArrayList();
        while (true) {
            if (jbVar.b >= jbVar.a) {
                return arrayList;
            }
            ja a2 = iw.a(ixVar, jbVar, a);
            if (a2.a.length() > 0) {
                arrayList.add(new ja(a2.a, Uri.decode(a2.b)));
            }
        }
    }

    public static String a(List<? extends ja> list, String str) {
        StringBuilder sb = new StringBuilder();
        for (ja jaVar : list) {
            String a2 = a(jaVar.a, str);
            String a3 = a(jaVar.b, str);
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(a2);
            if (a3 != null) {
                sb.append("=");
                sb.append(a3);
            }
        }
        return sb.toString();
    }

    public static String a(Iterable<? extends ja> iterable, Charset charset) {
        StringBuilder sb = new StringBuilder();
        for (ja jaVar : iterable) {
            String d2 = d(jaVar.a, charset);
            String d3 = d(jaVar.b, charset);
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(d2);
            if (d3 != null) {
                sb.append("=");
                sb.append(d3);
            }
        }
        return sb.toString();
    }

    private static String a(String str, Charset charset, BitSet bitSet, boolean z) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        ByteBuffer encode = charset.encode(str);
        while (encode.hasRemaining()) {
            byte b2 = encode.get() & 255;
            if (bitSet.get(b2)) {
                sb.append((char) b2);
            } else if (!z || b2 != 32) {
                sb.append("%");
                char upperCase = Character.toUpperCase(Character.forDigit((b2 >> 4) & 15, 16));
                char upperCase2 = Character.toUpperCase(Character.forDigit(b2 & 15, 16));
                sb.append(upperCase);
                sb.append(upperCase2);
            } else {
                sb.append('+');
            }
        }
        return sb.toString();
    }

    private static String a(String str, String str2) {
        Charset charset;
        if (str == null) {
            return null;
        }
        if (str2 != null) {
            charset = Charset.forName(str2);
        } else {
            charset = Charset.forName("UTF-8");
        }
        return a(str, charset, h, true);
    }

    private static String d(String str, Charset charset) {
        if (str == null) {
            return null;
        }
        if (charset == null) {
            charset = Charset.forName("UTF-8");
        }
        return a(str, charset, h, true);
    }

    public static String a(String str, Charset charset) {
        return a(str, charset, d, false);
    }

    public static String b(String str, Charset charset) {
        return a(str, charset, f, false);
    }

    public static String c(String str, Charset charset) {
        return a(str, charset, e, false);
    }
}
