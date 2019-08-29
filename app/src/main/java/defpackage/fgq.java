package defpackage;

import android.support.annotation.NonNull;
import mtopsdk.mtop.domain.EnvModeEnum;

/* renamed from: fgq reason: default package */
/* compiled from: AbstractSignImpl */
public abstract class fgq implements fgr {
    ffd a = null;
    EnvModeEnum b = null;

    public String a(String str, String str2, int i) {
        return null;
    }

    public void a(@NonNull ffd ffd) {
        this.a = ffd;
        if (this.a != null) {
            this.b = this.a.c;
        }
    }

    /* access modifiers changed from: 0000 */
    public final int a() {
        if (this.b == null) {
            return 0;
        }
        switch (this.b) {
            case ONLINE:
                return 0;
            case PREPARE:
                return 1;
            case TEST:
            case TEST_SANDBOX:
                return 2;
            default:
                return 0;
        }
    }

    /* access modifiers changed from: 0000 */
    public final String b() {
        return this.a != null ? this.a.h : "";
    }

    /* access modifiers changed from: 0000 */
    public final String c() {
        return this.a != null ? this.a.a : "";
    }
}
