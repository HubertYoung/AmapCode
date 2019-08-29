package defpackage;

import android.content.Context;
import mtopsdk.mtop.domain.EnvModeEnum;
import mtopsdk.mtop.intf.Mtop;

@Deprecated
/* renamed from: ffe reason: default package */
/* compiled from: SDKConfig */
public final class ffe {
    private static final ffe a = new ffe();

    private ffe() {
    }

    public static ffe a() {
        return a;
    }

    @Deprecated
    public static EnvModeEnum b() {
        return Mtop.a((Context) null).c.c;
    }
}
