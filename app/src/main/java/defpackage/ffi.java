package defpackage;

import android.support.annotation.NonNull;
import java.io.IOException;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.mtop.domain.MtopResponse;
import mtopsdk.mtop.util.MtopStatistics;

/* renamed from: ffi reason: default package */
/* compiled from: NetworkCallbackAdapter */
public final class ffi implements fgf {
    public b a;
    public c b;
    final fdf c;
    fdy d;

    public ffi(@NonNull fdf fdf) {
        this.c = fdf;
        if (fdf != null) {
            if (fdf.a != null) {
                this.d = fdf.a.c.K;
            }
            few few = fdf.e;
            if (few instanceof c) {
                this.b = (c) few;
            }
            if (few instanceof b) {
                this.a = (b) few;
            }
        }
    }

    private void a(final fgi fgi, Object obj) {
        this.c.g.z = MtopStatistics.a();
        this.c.d.reqContext = obj;
        fed.a(this.c.d.handler, new Runnable() {
            public final void run() {
                MtopResponse mtopResponse;
                try {
                    ffi.this.c.g.A = MtopStatistics.a();
                    ffi.this.c.g.C = fgi.f;
                    ffi.this.c.n = fgi;
                    mtopResponse = new MtopResponse(ffi.this.c.b.getApiName(), ffi.this.c.b.getVersion(), null, null);
                    mtopResponse.setResponseCode(fgi.b);
                    mtopResponse.setHeaderFields(fgi.d);
                    mtopResponse.setMtopStat(ffi.this.c.g);
                    if (fgi.e != null) {
                        mtopResponse.setBytedata(fgi.e.c());
                    }
                } catch (IOException e) {
                    TBSdkLog.b("mtopsdk.NetworkCallbackAdapter", ffi.this.c.h, "call getBytes of response.body() error.", e);
                } catch (Throwable th) {
                    TBSdkLog.b("mtopsdk.NetworkCallbackAdapter", ffi.this.c.h, "onFinish failed.", th);
                    return;
                }
                ffi.this.c.c = mtopResponse;
                ffi.this.d.a(ffi.this.c);
            }
        }, this.c.h.hashCode());
    }

    public final void a(fge fge, Exception exc) {
        a aVar = new a();
        aVar.a = fge.a();
        aVar.b = -7;
        aVar.c = exc.getMessage();
        fgi a2 = aVar.a();
        a(a2, a2.a.m);
    }

    public final void a(final fgi fgi) {
        final Object obj = fgi.a.m;
        fed.a(this.c.d.handler, new Runnable() {
            public final void run() {
                try {
                    if (ffi.this.b != null) {
                        fev fev = new fev(fgi.b, fgi.d);
                        fev.a = ffi.this.c.h;
                        ffi.this.b.onHeader(fev, obj);
                    }
                } catch (Throwable th) {
                    TBSdkLog.b("mtopsdk.NetworkCallbackAdapter", ffi.this.c.h, "onHeader failed.", th);
                }
            }
        }, this.c.h.hashCode());
        a(fgi, fgi.a.m);
    }

    public final void a(fge fge) {
        a aVar = new a();
        aVar.a = fge.a();
        aVar.b = -8;
        fgi a2 = aVar.a();
        a(a2, a2.a.m);
    }
}
