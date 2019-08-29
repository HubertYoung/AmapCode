package com.autonavi.minimap.agroup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.MotionEvent;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.agroup.ajx.ModuleAgroup;
import com.autonavi.bundle.mapevent.listener.MainMapEventListener;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle.GLAmapFocusHits;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IAMapHomePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.bundle.agroup.api.IDataService.a;
import java.util.List;

public class AgroupVApp extends esh {
    b a = new a() {
        public final boolean a(List<als> list) {
            cji.e().a(0);
            return super.a(list);
        }

        public final boolean b() {
            return AMapPageUtil.getPageContext() instanceof IAMapHomePage;
        }

        public final bty a() {
            return DoNotUseTool.getMapView();
        }

        public final boolean c() {
            return AMapPageUtil.getPageContext() instanceof IAMapHomePage;
        }
    };
    bdw b = new bdw(this.a) {
        public final boolean onBlankClick() {
            super.onBlankClick();
            cji.e().a(0);
            return false;
        }

        public final void onLongPress(MotionEvent motionEvent) {
            super.onLongPress(motionEvent);
            cji.e().a(0);
        }

        public final void onPointOverlayClick(GLAmapFocusHits gLAmapFocusHits) {
            super.onPointOverlayClick(gLAmapFocusHits);
            cji.e().a(gLAmapFocusHits.mOverlayHashCode);
        }

        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            super.onSingleTapUp(motionEvent);
            cji e = cji.e();
            if (e.h == 0) {
                return e.b(true);
            }
            return false;
        }
    };
    private boolean c = false;
    private BroadcastReceiver d;
    private anp e = new anp() {
        public final void onLoginStateChanged(boolean z, boolean z2) {
            if (z2) {
                cuh cuh = (cuh) a.a.a(cuh.class);
                if (cuh != null) {
                    cuh.p();
                    IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                    if (iAccountService != null) {
                        ant e = iAccountService.e();
                        cuh.l().a(e.a, e.e, e.b);
                    }
                }
            }
        }

        public final void onUserInfoUpdate(ant ant) {
            cuh cuh = (cuh) a.a.a(cuh.class);
            if (cuh != null && ((IAccountService) a.a.a(IAccountService.class)) != null) {
                cuh.l().a(ant.a, ant.e, ant.b);
            }
        }
    };

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        Ajx.getInstance().registerModule(ModuleAgroup.class);
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            cuh.d();
        }
        getClass();
        brl.a((a) new a() {
            public final void a(bty bty) {
                cji e = cji.e();
                if (!e.e) {
                    bty.F();
                    e.c = bty;
                    e.d = new cjl(e.c);
                    e.n = new HandlerThread("agroup-draw");
                    e.n.start();
                    e.o = new Handler(e.n.getLooper());
                    e.d.a(0, 0, 0, 0);
                    e.m = 0;
                    e.j = new cir(bty.ap(), e);
                    e.b = new cjh(e);
                    cjh cjh = e.b;
                    cjt a2 = cjt.a();
                    if (a2 != null) {
                        a2.a(cjh);
                    }
                    e.e = true;
                    if (e.g) {
                        e.g = false;
                        e.a(true);
                    }
                }
            }

            public final void a() {
                cji e = cji.e();
                if (e.e) {
                    if (e.b != null) {
                        cjh cjh = e.b;
                        cjt a2 = cjt.a();
                        if (a2 != null) {
                            a2.b((a) cjh);
                        }
                    }
                    e.g();
                    e.c.F();
                    e.j.onDestroy();
                    e.j = null;
                    if (e.o != null) {
                        e.o.removeCallbacksAndMessages(null);
                        e.o = null;
                    }
                    if (e.n != null) {
                        e.n.quit();
                        e.n = null;
                    }
                    e.k = null;
                    e.l = null;
                    e.d = null;
                    cji.a = null;
                    e.e = false;
                }
            }

            public final boolean b() {
                cji.e().a(0);
                return false;
            }
        });
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        this.d = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                cuh cuh = (cuh) a.a.a(cuh.class);
                if (cuh != null) {
                    cuh.a(context);
                }
            }
        };
        if (!this.c) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            AMapPageUtil.getAppContext().registerReceiver(this.d, intentFilter);
            this.c = true;
        }
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a(this.e);
        }
        awb awb = (awb) a.a.a(awb.class);
        if (awb != null) {
            awb.a((MainMapEventListener) this.b);
        }
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        if (this.c) {
            try {
                AMapPageUtil.getAppContext().unregisterReceiver(this.d);
            } catch (Exception unused) {
            }
        }
        this.c = false;
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.b(this.e);
        }
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            cuh.j();
        }
        awb awb = (awb) a.a.a(awb.class);
        if (awb != null) {
            awb.b((MainMapEventListener) this.b);
        }
    }
}
