package defpackage;

import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager.a;
import com.autonavi.minimap.bundle.msgbox.util.MessageBoxManager.b;

/* renamed from: das reason: default package */
/* compiled from: MsgRequest */
public final class das {
    public b a;
    public a b;

    public das(b bVar, a aVar) {
        this.a = bVar;
        this.b = aVar;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final boolean equals(Object obj) {
        if (obj instanceof das) {
            return this.a.equals(((das) obj).a);
        }
        return false;
    }
}
