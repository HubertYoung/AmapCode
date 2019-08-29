package defpackage;

import android.support.annotation.NonNull;
import java.util.LinkedHashMap;

/* renamed from: ciu reason: default package */
/* compiled from: AGroupOverlayConfigManager */
public final class ciu {
    public LinkedHashMap<Class<?>, cuf> a;
    public cuf b;

    /* renamed from: ciu$a */
    /* compiled from: AGroupOverlayConfigManager */
    public static final class a {
        /* access modifiers changed from: private */
        public static final ciu a = new ciu(0);
    }

    /* synthetic */ ciu(byte b2) {
        this();
    }

    private ciu() {
        this.a = new LinkedHashMap<>();
    }

    public final void a(@NonNull Class<?> cls, @NonNull cuf cuf) {
        if (this.a.get(cls) == null) {
            this.a.put(cls, cuf);
            return;
        }
        if (this.a.containsKey(cls)) {
            this.a.put(cls, cuf);
        }
    }
}
