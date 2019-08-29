package defpackage;

import com.autonavi.bundle.entity.infolite.external.PoiLocationInfo;
import com.autonavi.bundle.entity.infolite.external.ResponseHeaderModule;
import java.util.ArrayList;

/* renamed from: aud reason: default package */
/* compiled from: PoiSearchResult */
public final class aud implements Cloneable {
    public PoiLocationInfo a;
    public auc b;
    public ResponseHeaderModule c;
    public String d;

    public final Object clone() throws CloneNotSupportedException {
        aud aud = (aud) super.clone();
        if (this.b != null) {
            aud.b = (auc) this.b.clone();
        }
        if (this.a != null) {
            aud.a = (PoiLocationInfo) this.a.clone();
        }
        return aud;
    }

    public static aud a() {
        aud aud = new aud();
        if (aud.c == null) {
            aud.c = new ResponseHeaderModule();
        }
        if (aud.b == null) {
            aud.b = new auc();
        }
        if (aud.b.a == null) {
            aud.b.a = new aub();
        }
        if (aud.b.d == null) {
            aud.b.d = new ArrayList<>();
        }
        return aud;
    }
}
