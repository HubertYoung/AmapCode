package defpackage;

import java.io.ByteArrayInputStream;
import java.lang.ref.WeakReference;

/* renamed from: bof reason: default package */
/* compiled from: HurlNetworkImpl */
public final class bof implements bpb, bpc {
    private WeakReference<bpn> a = null;

    /* renamed from: bof$a */
    /* compiled from: HurlNetworkImpl */
    static class a implements bou {
        private WeakReference<bph> a;
        private WeakReference<bpn> b;

        /* synthetic */ a(bph bph, WeakReference weakReference, byte b2) {
            this(bph, weakReference);
        }

        private a(bph bph, WeakReference<bpn> weakReference) {
            this.a = new WeakReference<>(bph);
            this.b = weakReference;
        }

        public final void a(long j, long j2) {
            bph bph = (bph) this.a.get();
            bpn bpn = this.b == null ? null : (bpn) this.b.get();
            if (bph != null && bpn != null) {
                bpn.a(bph, j, j2);
            }
        }
    }

    public final void a(bpn bpn) {
        this.a = new WeakReference<>(bpn);
    }

    public final bpa a(bph bph) throws Exception {
        bot bot = new bot();
        if (bph.getMethod() == 1) {
            if (bph instanceof bpe) {
                bpe bpe = (bpe) bph;
                bon bon = new bon(bpe.b());
                bon.a = bpe.a();
                bot.b = bon;
            } else if (bph instanceof bpi) {
                bot.a(((bpi) bph).a());
            } else if (bph instanceof bpj) {
                bpj bpj = (bpj) bph;
                if (bpj.getBody() != null) {
                    bot.a(new ByteArrayInputStream(bpj.getBody()), bpj.getContentType());
                }
            }
        }
        bot.a(bph.getHeaders());
        bot.d = bph.getRetryTimes();
        bot.a(bph.getTimeout());
        String a2 = bpu.a(bph.getMethod());
        String url = bph.getUrl();
        bol bol = new bol(url, a2, bot);
        bol.c = new a(bph, this.a, 0);
        if (bpv.a(3)) {
            bpv.b("ANet-HurlNetworkImpl", "send hurl, url:".concat(String.valueOf(url)));
        }
        bph.requestStatistics.l = System.currentTimeMillis();
        try {
            bov a3 = bol.a();
            bph.requestStatistics.h = bph.getRetryTimes();
            bph.requestStatistics.m = bol.d;
            bph.requestStatistics.n = System.currentTimeMillis();
            int i = a3.c;
            bph.requestStatistics.e = i;
            if ((i < 200 || i >= 400) && i != bok.e) {
                bph.requestStatistics.d = bok.a;
            } else {
                bph.requestStatistics.d = bok.b;
            }
            return new bog(a3);
        } catch (Exception e) {
            bph.requestStatistics.h = bph.getRetryTimes();
            bph.requestStatistics.m = bol.d;
            throw e;
        }
    }
}
