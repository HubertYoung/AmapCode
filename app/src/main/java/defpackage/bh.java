package defpackage;

import android.content.Context;
import anet.channel.entity.ConnType;
import anet.channel.statist.RequestStatistic;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLSocketFactory;
import tv.danmaku.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;

/* renamed from: bh reason: default package */
/* compiled from: HttpSession */
public final class bh extends p {
    private SSLSocketFactory w;

    public final Runnable c() {
        return null;
    }

    public bh(Context context, af afVar) {
        super(context, afVar);
        if (this.k == null) {
            this.j = (this.c == null || !this.c.startsWith("https")) ? ConnType.a : ConnType.b;
            return;
        }
        if (j.c() && this.j.equals(ConnType.b)) {
            this.w = new da(this.d);
        }
    }

    public final boolean e() {
        return this.n == 4;
    }

    public final void a() {
        try {
            a a = new a().a(this.c);
            a.m = this.p;
            a a2 = a.b((int) (((float) this.r) * db.b())).a((int) (((float) this.s) * db.b()));
            a2.h = false;
            if (this.w != null) {
                a2.k = this.w;
            }
            if (this.m) {
                a2.a("Host", this.e);
            }
            ct.a();
            cl.b("awcn.HttpSession", "HttpSession connect", null, "host", this.c, OnNativeInvokeListener.ARG_IP, this.f, "port", Integer.valueOf(this.g));
            final ay a3 = a2.a();
            a3.a(this.f, this.g);
            ck.a(new Runnable() {
                public final void run() {
                    a a2 = bg.a(a3, (o) null);
                    if (a2.a > 0) {
                        bh.this.b(4, new ag(1));
                    } else {
                        bh.this.a(256, new ag(256, a2.a, "Http connect fail"));
                    }
                }
            }, c.c);
        } catch (Throwable unused) {
            cl.e("awcn.HttpSession", "HTTP connect fail.", null, new Object[0]);
        }
    }

    public final void b() {
        b(6, null);
    }

    public final void a(boolean z) {
        this.t = false;
        b(6, null);
    }

    public final aw a(final ay ayVar, final o oVar) {
        ax axVar = ax.a;
        a aVar = null;
        final RequestStatistic requestStatistic = ayVar != null ? ayVar.k : new RequestStatistic(this.d, null);
        requestStatistic.setConnType(this.j);
        if (requestStatistic.start == 0) {
            long currentTimeMillis = System.currentTimeMillis();
            requestStatistic.reqStart = currentTimeMillis;
            requestStatistic.start = currentTimeMillis;
        }
        if (ayVar == null) {
            oVar.onFinish(-102, co.a(-102), requestStatistic);
            return axVar;
        }
        try {
            if (ayVar.j == null && this.w != null) {
                aVar = ayVar.a();
                aVar.k = this.w;
            }
            if (this.m) {
                if (aVar == null) {
                    aVar = ayVar.a();
                }
                aVar.a("Host", this.e);
            }
            if (aVar != null) {
                ayVar = aVar.a();
            }
            if (this.f == null) {
                ct.a();
            }
            ayVar.a(this.f, this.g);
            ayVar.a(this.j.c());
            if (this.k != null) {
                ayVar.k.setIpInfo(this.k.c(), this.k.b());
            } else {
                ayVar.k.setIpInfo(1, 1);
            }
            ayVar.k.unit = this.l;
            axVar = new ax(ck.a(new Runnable() {
                public final void run() {
                    ayVar.k.sendBeforeTime = System.currentTimeMillis() - ayVar.k.reqStart;
                    bg.a(ayVar, (o) new o() {
                        public final void onResponseCode(int i, Map<String, List<String>> map) {
                            cl.b("awcn.HttpSession", "", ayVar.e, "httpStatusCode", Integer.valueOf(i));
                            cl.b("awcn.HttpSession", "", ayVar.e, "response headers", map);
                            oVar.onResponseCode(i, map);
                            requestStatistic.serverRT = cq.c(map);
                            bh.this.a(ayVar, i);
                            bh.this.a(ayVar, map);
                        }

                        public final void onDataReceive(aa aaVar, boolean z) {
                            oVar.onDataReceive(aaVar, z);
                        }

                        public final void onFinish(int i, String str, RequestStatistic requestStatistic) {
                            if (i <= 0) {
                                bh.this.a(2, new ag(2, 0, "Http connect fail"));
                            }
                            oVar.onFinish(i, str, requestStatistic);
                        }
                    });
                }
            }, cw.a(ayVar)), ayVar.e);
        } catch (Throwable th) {
            oVar.onFinish(-101, co.a(-101, th.toString()), requestStatistic);
        }
        return axVar;
    }
}
