package defpackage;

import android.os.Handler;
import android.os.Message;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.suspend.refactor.scale.ScaleLineView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: dai reason: default package */
/* compiled from: SuspendEventControllerImpl */
public class dai implements cda {
    /* access modifiers changed from: private */
    public MapManager a = DoNotUseTool.getMapManager();
    private agl<g> b = new agl<>();
    /* access modifiers changed from: private */
    public List<WeakReference<cdf>> c = new ArrayList();
    /* access modifiers changed from: private */
    public List<WeakReference<f>> d = new ArrayList();
    /* access modifiers changed from: private */
    public List<WeakReference<defpackage.cda.a>> e = new ArrayList();
    private List<WeakReference<e>> f = new ArrayList();
    private List<WeakReference<d>> g = new ArrayList();
    private List<WeakReference<c>> h = new ArrayList();
    private List<Object> i;
    private WeakReference<b> j;
    private WeakReference<Object> k;
    private ccz l;
    private boolean m = false;
    private Float n = null;
    private Float o = null;
    /* access modifiers changed from: private */
    public float p = 0.0f;
    /* access modifiers changed from: private */
    public float q = 0.0f;
    /* access modifiers changed from: private */
    public Float r = null;
    private boolean s = false;
    private Float t = null;
    private a u = new a(this);
    private biy v = new biy() {
        public final void updateSuccess() {
            dai.this.h();
        }
    };
    private bio w = new bio() {
        public final void a() {
            dai.this.h();
        }
    };

    /* renamed from: dai$a */
    /* compiled from: SuspendEventControllerImpl */
    static class a extends Handler {
        WeakReference<dai> a = null;

        public a(dai dai) {
            this.a = new WeakReference<>(dai);
        }

        public final void handleMessage(Message message) {
            if (!(this.a == null || this.a.get() == null)) {
                switch (message.what) {
                    case 111:
                        if (this.a != null && this.a.get() != null) {
                            this.a.get();
                            ((dai) this.a.get()).p = dai.p();
                            this.a.get();
                            ((dai) this.a.get()).q = dai.q();
                            for (WeakReference weakReference : ((dai) this.a.get()).c) {
                                cdf cdf = (cdf) weakReference.get();
                                if (cdf != null) {
                                    cdf.paintCompass();
                                }
                            }
                            return;
                        }
                        return;
                    case 113:
                        if (this.a != null && this.a.get() != null) {
                            for (WeakReference weakReference2 : ((dai) this.a.get()).d) {
                                f fVar = (f) weakReference2.get();
                                if (fVar != null) {
                                    fVar.refreshScaleLineView();
                                }
                            }
                            return;
                        }
                        return;
                    case 114:
                        if (this.a != null && this.a.get() != null) {
                            for (WeakReference weakReference3 : ((dai) this.a.get()).d) {
                                f fVar2 = (f) weakReference3.get();
                                if (fVar2 != null) {
                                    fVar2.setScaleColor(message.arg1, message.arg2);
                                }
                            }
                            return;
                        }
                        return;
                    case 115:
                        if (this.a != null && this.a.get() != null) {
                            for (WeakReference weakReference4 : ((dai) this.a.get()).d) {
                                f fVar3 = (f) weakReference4.get();
                                if (fVar3 != null) {
                                    fVar3.refreshScaleLogo();
                                }
                            }
                            return;
                        }
                        return;
                    case 116:
                        if (this.a != null && this.a.get() != null) {
                            boolean booleanValue = ((Boolean) message.obj).booleanValue();
                            for (WeakReference weakReference5 : ((dai) this.a.get()).d) {
                                f fVar4 = (f) weakReference5.get();
                                if (fVar4 != null) {
                                    fVar4.changeLogoStatus(booleanValue);
                                }
                            }
                            break;
                        } else {
                            return;
                        }
                        break;
                }
            }
        }
    }

    public final void m() {
    }

    public void setFrontViewVisibility(int i2, boolean z) {
    }

    public final void a() {
        this.a = DoNotUseTool.getMapManager();
        if (this.a != null) {
            this.a.setMapWidgetListener(this);
            this.a.setIndoorBuildingListener(this);
        }
        bim.aa().b(this.v);
        bim.aa().a(this.w);
    }

    @Deprecated
    public final void a(g gVar) {
        this.b.a(gVar);
    }

    @Deprecated
    public final void a(cdf cdf) {
        this.c.add(new WeakReference(cdf));
        a(this.c);
    }

    @Deprecated
    public final void a(f fVar) {
        this.d.add(new WeakReference(fVar));
        a(this.d);
    }

    @Deprecated
    public final void a(defpackage.cda.a aVar) {
        this.e.add(new WeakReference(aVar));
        a(this.e);
    }

    @Deprecated
    public final void a(e eVar) {
        this.f.add(new WeakReference(eVar));
        a(this.f);
    }

    @Deprecated
    public final void a(d dVar) {
        this.g.add(new WeakReference(dVar));
        a(this.g);
    }

    @Deprecated
    public final void a(c cVar) {
        this.h.add(new WeakReference(cVar));
        a(this.h);
    }

    public final void a(b bVar) {
        this.j = new WeakReference<>(bVar);
    }

    public final void a(ccz ccz) {
        this.l = ccz;
        this.a.setIRealtimeBusStateListener(this.l);
    }

    public final ccz b() {
        return this.l;
    }

    public void refreshScaleLineView(int i2) {
        f(false);
    }

    public void paintCompass(int i2) {
        g(false);
    }

    public final void c() {
        g();
        d();
    }

    public final void d() {
        if (this.l != null) {
            this.l.c();
        }
    }

    public final void e() {
        for (WeakReference<e> weakReference : this.f) {
            e eVar = (e) weakReference.get();
            if (eVar != null) {
                eVar.onResetViewState();
            }
        }
    }

    public final void a(boolean z) {
        for (WeakReference<d> weakReference : this.g) {
            d dVar = (d) weakReference.get();
            if (dVar != null) {
                dVar.a();
            }
        }
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) defpackage.esb.a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            iAutoRemoteController.restoreViewByConnectionState();
        }
    }

    public final void f() {
        if (this.u != null) {
            this.u.removeMessages(116);
        }
    }

    public final void b(boolean z) {
        if (this.u != null) {
            Message obtainMessage = this.u.obtainMessage(116);
            obtainMessage.obj = Boolean.valueOf(z);
            this.u.sendMessage(obtainMessage);
        }
    }

    public final void g() {
        this.b.a((defpackage.agl.a<T>) new defpackage.agl.a<g>() {
            public final /* synthetic */ void onNotify(Object obj) {
                ((g) obj).updateZoomButtonState(dai.this.a.getMapView());
            }
        });
    }

    public final void h() {
        this.b.a((defpackage.agl.a<T>) new defpackage.agl.a<g>() {
            public final /* synthetic */ void onNotify(Object obj) {
                ((g) obj).updateZoomViewVisibility();
            }
        });
    }

    public final void i() {
        if (this.t != null) {
            f(true);
        }
    }

    public final void j() {
        if (this.n != null && this.o != null) {
            g(true);
        }
    }

    public final void k() {
        for (WeakReference<c> weakReference : this.h) {
            c cVar = (c) weakReference.get();
            if (cVar != null) {
                cVar.c();
            }
        }
    }

    public final void c(boolean z) {
        if (this.i != null) {
            Iterator<Object> it = this.i.iterator();
            while (it.hasNext()) {
                it.next();
            }
        }
    }

    private void f(boolean z) {
        bty mapView = this.a.getMapView();
        if (z || this.t == null || ScaleLineView.isLevelChanged(this.t.floatValue(), mapView)) {
            if (mapView != null) {
                this.t = Float.valueOf(mapView.v());
            }
            if (this.u != null) {
                this.u.sendMessage(this.u.obtainMessage(115));
            }
            if (this.u != null) {
                this.u.sendMessage(this.u.obtainMessage(113));
            }
        }
    }

    private void g(boolean z) {
        float p2 = p();
        float q2 = q();
        if (z || this.n == null || this.n.floatValue() != p2 || this.n.floatValue() != this.p || this.o == null || this.o.floatValue() != q2 || this.o.floatValue() != this.q) {
            this.n = Float.valueOf(p2);
            this.o = Float.valueOf(q2);
            this.p = p2;
            this.q = q2;
            if (this.u != null) {
                this.u.sendMessage(this.u.obtainMessage(111));
            }
            bty mapView = this.a.getMapView();
            if (mapView != null) {
                float J = mapView.J();
                if (this.r == null || ((this.r.floatValue() == 0.0f && J > 0.0f) || (this.r.floatValue() > 0.0f && J == 0.0f))) {
                    aho.a(new Runnable() {
                        public final void run() {
                            float J = dai.this.a.getMapView().J();
                            if (dai.this.r == null || ((dai.this.r.floatValue() == 0.0f && J > 0.0f) || (dai.this.r.floatValue() > 0.0f && J == 0.0f))) {
                                for (WeakReference weakReference : dai.this.e) {
                                    defpackage.cda.a aVar = (defpackage.cda.a) weakReference.get();
                                    if (aVar != null) {
                                        aVar.updateStateWhenCompassPaint();
                                    }
                                }
                                dai.this.r = Float.valueOf(J);
                            }
                        }
                    });
                }
            }
        }
    }

    private static <T> void a(List<WeakReference<T>> list) {
        if (list != null) {
            Iterator<WeakReference<T>> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().get() == null) {
                    it.remove();
                }
            }
        }
    }

    public void indoorBuildingActivity(int i2, ami ami) {
        bty mapView = this.a.getMapView();
        if (mapView == null || i2 == mapView.j()) {
            if (this.j != null) {
                b bVar = (b) this.j.get();
                if (bVar != null) {
                    bVar.onIndoorBuildingActive(ami);
                }
            }
            g();
            if (this.k != null) {
                this.k.get();
            }
            e();
            bec bec = (bec) ank.a(bec.class);
            if (bec != null) {
                bea a2 = bec.a();
                if (a2 != null) {
                    a2.a(ami);
                }
            }
        }
    }

    public final void d(boolean z) {
        if (this.s != z) {
            this.s = z;
            e();
        }
    }

    public final boolean l() {
        return this.s;
    }

    public final void e(boolean z) {
        this.m = z;
    }

    /* access modifiers changed from: private */
    public static float p() {
        return DoNotUseTool.getMapManager().getMapView().I();
    }

    /* access modifiers changed from: private */
    public static float q() {
        return DoNotUseTool.getMapManager().getMapView().J();
    }

    public void setScaleColor(int i2, int i3, int i4) {
        for (WeakReference<f> weakReference : this.d) {
            f fVar = (f) weakReference.get();
            if (fVar != null) {
                fVar.setScaleLineColor(i3, i4);
            }
        }
        if (this.u != null) {
            Message obtainMessage = this.u.obtainMessage(114);
            obtainMessage.arg1 = i3;
            obtainMessage.arg2 = i4;
            this.u.sendMessage(obtainMessage);
        }
    }

    public void fadeCompassWidget(int i2) {
        if (this.u != null) {
            this.u.sendMessage(this.u.obtainMessage(112));
        }
    }
}
