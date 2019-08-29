package defpackage;

import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.lang.ref.WeakReference;

/* renamed from: ail reason: default package */
/* compiled from: VoiceResumeMessageManager */
public final class ail {
    private static ail b;
    public WeakReference<aih> a = null;

    private ail() {
    }

    public static synchronized ail a() {
        ail ail;
        synchronized (ail.class) {
            try {
                if (b == null) {
                    b = new ail();
                }
                ail = b;
            }
        }
        return ail;
    }

    public final void b() {
        if (AMapPageUtil.isHomePage() && this.a != null) {
            aih aih = (aih) this.a.get();
            if (aih != null && aih.a()) {
                aik.a().c();
                aik.a().b();
            }
        }
    }
}
