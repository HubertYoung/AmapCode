package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.amap.bundle.blutils.log.DebugLog;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: aaz reason: default package */
/* compiled from: URIBuilder */
public final class aaz {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private int g;
    private String h;
    private String i;
    private String j;
    private List<abg> k;
    private String l;
    private String m;

    public aaz() {
        this.g = -1;
    }

    public aaz(String str) {
        try {
            a(new URI(str));
        } catch (URISyntaxException e2) {
            URL url = new URL(str);
            URI uri = new URI(url.getProtocol(), url.getAuthority(), url.getPath(), url.getQuery(), null);
            a(uri);
        } catch (Throwable unused) {
            DebugLog.error(e2.getMessage(), e2);
        }
    }

    private void a(URI uri) {
        this.a = uri.getScheme();
        this.b = uri.getRawSchemeSpecificPart();
        this.c = uri.getRawAuthority();
        this.f = uri.getHost();
        this.g = uri.getPort();
        this.e = uri.getRawUserInfo();
        this.d = uri.getUserInfo();
        this.i = uri.getRawPath();
        this.h = uri.getPath();
        this.j = uri.getRawQuery();
        this.k = c(uri.getRawQuery());
        this.m = uri.getRawFragment();
        this.l = uri.getFragment();
    }

    private static List<abg> c(String str) {
        if (!TextUtils.isEmpty(str)) {
            return aba.a(str);
        }
        return null;
    }

    public final String a(String str) {
        String str2;
        if (this.k != null && this.k.size() > 0) {
            Iterator<abg> it = this.k.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                abg next = it.next();
                if (next.a.equals(str)) {
                    str2 = next.b;
                    it.remove();
                    break;
                }
            }
        }
        str2 = null;
        if (!TextUtils.isEmpty(str2)) {
            this.j = null;
            this.b = null;
        }
        return str2;
    }

    public final String b(String str) {
        Charset forName = Charset.forName(str);
        StringBuilder sb = new StringBuilder();
        if (this.a != null) {
            sb.append(this.a);
            sb.append(':');
        }
        if (this.b != null) {
            sb.append(this.b);
        } else {
            if (this.c != null) {
                sb.append("//");
                sb.append(this.c);
            } else if (this.f != null) {
                sb.append("//");
                if (this.e != null) {
                    sb.append(this.e);
                    sb.append(AUScreenAdaptTool.PREFIX_ID);
                } else if (this.d != null) {
                    sb.append(aba.a(this.d, forName));
                    sb.append(AUScreenAdaptTool.PREFIX_ID);
                }
                if (abf.a(this.f)) {
                    sb.append("[");
                    sb.append(this.f);
                    sb.append("]");
                } else {
                    sb.append(this.f);
                }
                if (this.g >= 0) {
                    sb.append(":");
                    sb.append(this.g);
                }
            }
            if (this.i != null) {
                sb.append(d(this.i));
            } else if (this.h != null) {
                sb.append(a(d(this.h), forName));
            }
            if (this.j != null) {
                sb.append("?");
                sb.append(this.j);
            } else if (this.k != null) {
                sb.append("?");
                sb.append(aba.a((Iterable<? extends abg>) this.k, forName));
            }
        }
        if (this.m != null) {
            sb.append(MetaRecord.LOG_SEPARATOR);
            sb.append(this.m);
        } else if (this.l != null) {
            sb.append(MetaRecord.LOG_SEPARATOR);
            sb.append(aba.b(this.l, forName));
        }
        return sb.toString();
    }

    private static String a(String str, Charset charset) {
        String c2 = aba.c(str, charset);
        if (c2 != null) {
            c2.replace("+", "20%");
        }
        return c2;
    }

    public final aaz a(String str, String str2) {
        if (this.k == null) {
            this.k = new ArrayList();
        }
        this.k.add(new abg(str, str2));
        this.j = null;
        this.b = null;
        return this;
    }

    public final List<abg> a() {
        if (this.k != null) {
            return new ArrayList(this.k);
        }
        return new ArrayList();
    }

    private static String d(String str) {
        if (str == null) {
            return null;
        }
        int i2 = 0;
        while (i2 < str.length() && str.charAt(i2) == '/') {
            i2++;
        }
        if (i2 > 1) {
            str = str.substring(i2 - 1);
        }
        return str;
    }
}
