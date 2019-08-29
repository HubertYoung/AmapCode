package defpackage;

import android.os.Handler;
import mtopsdk.mtop.domain.MtopResponse;

/* renamed from: fed reason: default package */
/* compiled from: FilterUtils */
public final class fed {
    public static final fdl a = new fdl();

    public static void a(final fdf fdf) {
        final MtopResponse mtopResponse = fdf.c;
        if (mtopResponse != null && (fdf.e instanceof b)) {
            mtopResponse.setMtopStat(fdf.g);
            final feu feu = new feu(mtopResponse);
            feu.b = fdf.h;
            a.a(fdf);
            a(fdf.d.handler, new Runnable() {
                public final void run() {
                    try {
                        fdf.g.H = fcz.a(mtopResponse.getHeaderFields(), "x-s-traceid");
                        fdf.g.n = mtopResponse.getResponseCode();
                        fdf.g.o = mtopResponse.getRetCode();
                        fdf.g.q = mtopResponse.getMappingCode();
                        if (mtopResponse.isApiSuccess() && 3 == fdf.g.j) {
                            fdf.g.n = 304;
                        }
                        fdf.g.b();
                        ((b) fdf.e).onFinished(feu, fdf.d.reqContext);
                    } catch (Exception unused) {
                    }
                }
            }, fdf.h.hashCode());
        }
    }

    public static void a(Handler handler, Runnable runnable, int i) {
        if (handler != null) {
            handler.post(runnable);
        } else {
            ffy.a(i, runnable);
        }
    }

    public static void a(MtopResponse mtopResponse) {
        if (mtopResponse != null) {
            String a2 = fcz.a(mtopResponse.getHeaderFields(), "x-retcode");
            mtopResponse.mappingCodeSuffix = fcz.a(mtopResponse.getHeaderFields(), "x-mapping-code");
            if (fdd.a(a2)) {
                mtopResponse.setRetCode(a2);
            } else {
                mtopResponse.parseJsonByte();
            }
        }
    }

    public static void a(fdy fdy, fdf fdf) {
        if (fdy == null) {
            MtopResponse mtopResponse = new MtopResponse("ANDROID_SYS_MTOPSDK_INIT_ERROR", "MTOPSDK初始化失败");
            if (fdf.b != null) {
                mtopResponse.setApi(fdf.b.getApiName());
                mtopResponse.setV(fdf.b.getVersion());
            }
            fdf.c = mtopResponse;
            a(fdf);
        }
    }
}
