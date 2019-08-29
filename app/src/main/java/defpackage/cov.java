package defpackage;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.MapPointOverlay;
import com.autonavi.minimap.base.overlay.MapPointOverlayItem;
import com.autonavi.minimap.basemap.favorites.FavoriteServiceImpl$1;
import com.autonavi.minimap.map.FavoriteLayer;
import com.autonavi.minimap.map.FavoriteOverlayItem;
import com.autonavi.sync.beans.GirfFavoritePoint;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* renamed from: cov reason: default package */
/* compiled from: FavoriteServiceImpl */
public class cov implements brn {
    /* access modifiers changed from: private */
    public static boolean f = false;
    a a;
    private bty b;
    private FavoriteLayer c;
    private boolean d;
    private con e;
    private cou g;
    private c h;
    private HandlerThread i;

    /* renamed from: cov$a */
    /* compiled from: FavoriteServiceImpl */
    static class a extends Handler {
        WeakReference<cov> a;

        public a(cov cov, Looper looper) {
            super(looper);
            this.a = new WeakReference<>(cov);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            if (this.a != null) {
                cov cov = (cov) this.a.get();
                if (cov != null) {
                    switch (message.what) {
                        case 0:
                        case 3:
                            cov.a(cov, message.obj, message.arg1);
                            return;
                        case 1:
                            cov.a(bim.aa().y(), 2);
                            return;
                        case 2:
                            cov.a(bim.aa().y(), 4);
                            break;
                        case 4:
                            List<String> y = bim.aa().y();
                            cov.a.removeMessages(3);
                            Message obtainMessage = cov.a.obtainMessage(3);
                            ArrayList arrayList = new ArrayList();
                            if (y != null) {
                                arrayList.addAll(y);
                            }
                            obtainMessage.obj = arrayList;
                            obtainMessage.arg1 = 5;
                            cov.a.sendMessage(obtainMessage);
                            return;
                    }
                }
            }
        }
    }

    /* renamed from: cov$b */
    /* compiled from: FavoriteServiceImpl */
    static class b implements biz {
        private WeakReference<cov> a;

        public b(cov cov) {
            this.a = new WeakReference<>(cov);
        }

        public final void a() {
            cov cov = (cov) this.a.get();
            if (cov != null) {
                cov.c(cov);
            }
        }
    }

    /* renamed from: cov$c */
    /* compiled from: FavoriteServiceImpl */
    static class c extends Thread {
        private List<String> a;
        private WeakReference<cov> b;
        private int c;

        c(cov cov, Object obj, int i) {
            this.b = new WeakReference<>(cov);
            this.a = (List) obj;
            this.c = i;
        }

        public final void run() {
            if (this.b != null && !cov.f) {
                cov cov = (cov) this.b.get();
                if (cov != null) {
                    cov.a(cov, (List) this.a, this.c);
                }
            }
        }
    }

    public final void a(bty bty, FavoriteLayer favoriteLayer) {
        f = false;
        this.b = bty;
        this.c = favoriteLayer;
        this.i = new HandlerThread("handler_thread_favorites");
        this.i.start();
        this.a = new a(this, this.i.getLooper());
        this.e = (con) ank.a(con.class);
        bim.aa().a((biz) new b(this));
        this.g = new cou();
        this.c.setDataProvider(this.g.createHandle());
    }

    public final void a() {
        if (this.e != null) {
            this.e.a((Callback<Boolean>) null);
        }
        this.a.removeCallbacksAndMessages(null);
        this.i.quitSafely();
        f = true;
        this.c.destroy();
        this.g.releaseHandle();
    }

    public final void b() {
        if (this.e != null) {
            if (this.d && !this.e.a()) {
                this.e.a((Context) AMapAppGlobal.getApplication());
                this.d = false;
            }
            if (!this.e.a() && !this.e.b()) {
                this.e.a((Callback<Boolean>) new FavoriteServiceImpl$1<Boolean>(this));
                return;
            }
        }
        if (bim.aa().k((String) "104")) {
            ahm.a(new Runnable() {
                public final void run() {
                    cov.this.d();
                }
            });
        }
    }

    public final void a(List<String> list, int i2) {
        this.a.removeMessages(0);
        Message obtainMessage = this.a.obtainMessage(0);
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            arrayList.addAll(list);
        }
        obtainMessage.obj = arrayList;
        obtainMessage.arg1 = i2;
        this.a.sendMessage(obtainMessage);
    }

    public final void d() {
        this.a.removeMessages(0);
        this.a.removeMessages(1);
        this.a.sendEmptyMessage(1);
    }

    public final void e() {
        this.a.removeMessages(0);
        this.a.removeMessages(2);
        this.a.sendEmptyMessage(2);
    }

    public final void f() {
        this.a.removeMessages(3);
        this.a.removeMessages(4);
        this.a.sendEmptyMessage(4);
    }

    public final FavoriteLayer g() {
        return this.c;
    }

    public final int h() {
        cou cou = this.g;
        if (cou.a == null) {
            return 0;
        }
        return cou.a.size();
    }

    public final void i() {
        this.c.setBubbleEnable(false);
    }

    public final void c() {
        this.c.refreshHomeAndCompany();
    }

    static /* synthetic */ void a(cov cov, List list, int i2) {
        cov.b.c((Runnable) new Runnable() {
            public final void run() {
                if (cov.k()) {
                    cov.b(cov.this);
                }
            }
        });
        List<GirfFavoritePoint> o = bim.aa().o();
        List<GirfFavoritePoint> p = bim.aa().p();
        if (i2 == 0) {
            cov.g.a(o);
            cov.c.refreshHome();
        } else if (i2 == 1) {
            cov.g.b(p);
            cov.c.refreshCompany();
        } else if (i2 == 2 || i2 == 4) {
            cov.g.a(o);
            cov.c.refreshHome();
            cov.g.b(p);
            cov.c.refreshCompany();
        }
        if (i2 != 4) {
            if (list == null || list.size() <= 0) {
                cov.g.c(null);
            } else {
                ArrayList arrayList = new ArrayList();
                int size = list.size();
                for (int i3 = 0; i3 < size && !Thread.currentThread().isInterrupted(); i3++) {
                    arrayList.add(bim.aa().f((String) list.get(i3)));
                }
                cov.g.c(arrayList);
            }
            cov.c.refreshFavorite();
        }
    }

    static /* synthetic */ boolean k() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            return pageContext instanceof MapBasePage;
        }
        return false;
    }

    static /* synthetic */ void b(cov cov) {
        try {
            FavoriteOverlayItem focusItem = cov.c.getFocusItem();
            if (focusItem != null) {
                MapPointOverlayItem mapPointOverlayItem = new MapPointOverlayItem(focusItem.getPOI().getPoint(), R.drawable.b_poi_hl);
                IOverlayManager overlayManager = DoNotUseTool.getMapManager().getOverlayManager();
                MapPointOverlay mapPointOverlay = overlayManager.getMapPointOverlay();
                mapPointOverlay.setAnimatorType(0);
                mapPointOverlay.setItem(mapPointOverlayItem);
                mapPointOverlay.setOverlayOnTop(true);
                overlayManager.resetMapPointAnimatorType();
            }
        } catch (Exception unused) {
        }
    }

    static /* synthetic */ void a(cov cov, Object obj, int i2) {
        if (cov.h != null && !cov.h.isInterrupted()) {
            cov.h.interrupt();
            try {
                cov.h.join();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        cov.h = new c(cov, obj, i2);
        cov.h.start();
    }

    static /* synthetic */ void c(cov cov) {
        List<GirfFavoritePoint> o = bim.aa().o();
        List<GirfFavoritePoint> p = bim.aa().p();
        cov.g.a(o);
        cov.g.b(p);
        cov.c.refreshHomeAndCompany();
    }
}
