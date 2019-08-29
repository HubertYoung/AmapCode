package defpackage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/* renamed from: yt reason: default package */
/* compiled from: AccsResponseImpl */
public final class yt implements bpa {
    private dk a;
    private InputStream b;

    public yt(dk dkVar) {
        this.a = dkVar;
    }

    public final int getStatusCode() {
        return this.a.a();
    }

    public final long getContentLength() {
        int i;
        String str;
        if ((this.a != null ? this.a.c() : null) != null) {
            List list = this.a.c().get("Content-Length");
            if (list == null) {
                str = "0";
            } else {
                str = (String) list.get(list.size() - 1);
            }
            i = Integer.parseInt(str);
            if (i <= 0) {
                InputStream bodyInputStream = getBodyInputStream();
                if (bodyInputStream != null) {
                    try {
                        i = bodyInputStream.available();
                    } catch (IOException unused) {
                    }
                }
            }
        } else {
            InputStream bodyInputStream2 = getBodyInputStream();
            if (bodyInputStream2 != null) {
                try {
                    i = bodyInputStream2.available();
                } catch (IOException unused2) {
                }
            }
            i = 0;
        }
        return (long) i;
    }

    public final String getHeader(String str) {
        if (this.a == null) {
            return "";
        }
        List list = this.a.c().get(str);
        if (list == null) {
            return "";
        }
        return (String) list.get(list.size() - 1);
    }

    public final Map<String, List<String>> getHeaders() {
        return this.a.c();
    }

    public final InputStream getBodyInputStream() {
        if (this.b == null && this.a != null && this.a.a() > 0) {
            this.b = new ByteArrayInputStream(this.a.b());
        }
        return this.b;
    }
}
