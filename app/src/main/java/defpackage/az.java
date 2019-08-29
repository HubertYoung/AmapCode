package defpackage;

import org.android.spdy.SpdyErrorException;
import org.android.spdy.SpdySession;

/* renamed from: az reason: default package */
/* compiled from: TnetCancelable */
public final class az implements aw {
    public static final az a = new az(null, 0, null);
    private final int b;
    private final SpdySession c;
    private final String d;

    public az(SpdySession spdySession, int i, String str) {
        this.c = spdySession;
        this.b = i;
        this.d = str;
    }

    public final void a() {
        try {
            if (!(this.c == null || this.b == 0)) {
                cl.b("awcn.TnetCancelable", "cancel tnet request", this.d, "streamId", Integer.valueOf(this.b));
                this.c.streamReset((long) this.b, 5);
            }
        } catch (SpdyErrorException e) {
            cl.e("awcn.TnetCancelable", "request cancel failed.", this.d, "errorCode", Integer.valueOf(e.SpdyErrorGetCode()));
        }
    }
}
