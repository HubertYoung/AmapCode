package defpackage;

import android.content.Context;
import mtopsdk.network.domain.Request;

/* renamed from: fgk reason: default package */
/* compiled from: ANetworkCallFactory */
public final class fgk implements a {
    private Context a;

    public fgk(Context context) {
        this.a = context;
    }

    public final fge a(Request request) {
        return new fgl(request, this.a);
    }
}
