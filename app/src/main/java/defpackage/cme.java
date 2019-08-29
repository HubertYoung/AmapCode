package defpackage;

import android.content.Context;
import com.amap.bundle.logs.AMapLog;

/* renamed from: cme reason: default package */
/* compiled from: NetworkContextImpl */
public final class cme implements aae {
    private Context a;
    private volatile cma b;
    private volatile cmi c;
    private volatile cmc d;
    private volatile cmj e;
    private volatile clx f;
    private volatile clz g;
    private volatile cmb h;
    private volatile cmf i;
    private volatile cmg j;
    private volatile cmd k;
    private volatile clw l;
    private volatile cly m;
    private volatile cmh n;

    public cme(Context context) {
        this.a = context.getApplicationContext();
    }

    public final void b() {
        lo.a().a((String) "accs_network", ((clx) g()).a);
        lo.a().a((String) "accs_ipv6_config", clx.b);
        lo.a().a((String) "network_apm_config", (lp) (clz) h());
        lo.a().a((String) "amap_basemap_config", (lp) new lp() {
            public final void onConfigCallBack(int i) {
            }

            public final void onConfigResultCallBack(int i, String str) {
                AMapLog.d("network.FreeCdnConfigProvider", "==>onConfigResultCallBack()");
                switch (i) {
                    case 0:
                    case 1:
                    case 4:
                        cmb.a(cmb.this, str);
                        break;
                    case 3:
                        cmb.a(cmb.this);
                        return;
                }
            }
        });
        lo.a().a((String) "opt_param_config", (lp) (cmf) j());
        lo.a().a((String) "scene_log", (lp) (cmg) k());
        lo.a().a((String) "start_monitor_log_config", (lp) (cmd) l());
        lo.a().a((String) "_ab_test_module", ((clw) n()).a);
        lo.a().a((String) "x_sign_level", (lp) (cmh) o());
    }

    public final Context a() {
        return this.a;
    }

    public final d c() {
        if (this.b == null) {
            this.b = new cma();
        }
        return this.b;
    }

    public final k d() {
        if (this.c == null) {
            synchronized (cmi.class) {
                if (this.c == null) {
                    this.c = new cmi();
                }
            }
        }
        return this.c;
    }

    public final f e() {
        if (this.d == null) {
            synchronized (cmc.class) {
                if (this.d == null) {
                    this.d = new cmc();
                }
            }
        }
        return this.d;
    }

    public final l f() {
        if (this.e == null) {
            synchronized (cmj.class) {
                if (this.e == null) {
                    this.e = new cmj();
                }
            }
        }
        return this.e;
    }

    public final aad m() {
        if (this.m == null) {
            synchronized (cly.class) {
                if (this.m == null) {
                    this.m = new cly();
                }
            }
        }
        return this.m;
    }

    public final b g() {
        if (this.f == null) {
            synchronized (clx.class) {
                if (this.f == null) {
                    this.f = new clx();
                }
            }
        }
        return this.f;
    }

    public final c h() {
        if (this.g == null) {
            synchronized (clz.class) {
                if (this.g == null) {
                    this.g = new clz();
                }
            }
        }
        return this.g;
    }

    public final e i() {
        if (this.h == null) {
            synchronized (cmb.class) {
                if (this.h == null) {
                    this.h = new cmb();
                }
            }
        }
        return this.h;
    }

    public final h j() {
        if (this.i == null) {
            synchronized (cmf.class) {
                if (this.i == null) {
                    this.i = new cmf();
                }
            }
        }
        return this.i;
    }

    public final i k() {
        if (this.j == null) {
            synchronized (cmg.class) {
                if (this.j == null) {
                    this.j = new cmg();
                }
            }
        }
        return this.j;
    }

    public final g l() {
        if (this.k == null) {
            synchronized (cmd.class) {
                if (this.k == null) {
                    this.k = new cmd();
                }
            }
        }
        return this.k;
    }

    public final a n() {
        if (this.l == null) {
            synchronized (clw.class) {
                if (this.l == null) {
                    this.l = new clw(this.a);
                }
            }
        }
        return this.l;
    }

    public final j o() {
        if (this.n == null) {
            synchronized (clw.class) {
                if (this.n == null) {
                    this.n = new cmh();
                }
            }
        }
        return this.n;
    }
}
