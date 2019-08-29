package defpackage;

import android.text.TextUtils;
import android.view.View;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.inter.IRouteUI.ContainerType;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.common.view.RouteBanner;
import com.autonavi.minimap.route.train.controller.TrainUIStatusController.RequestStatus;
import com.autonavi.minimap.route.train.page.TrainPlanListPage;
import com.autonavi.minimap.route.train.presenter.TrainPlanListPresenter$2;
import com.autonavi.minimap.route.train.stationlist.StationRequestManger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/* renamed from: ejm reason: default package */
/* compiled from: TrainPlanListPresenter */
public final class ejm extends eaf<TrainPlanListPage> implements bgo {
    public POI a;
    public POI b;
    public b c;
    /* access modifiers changed from: private */
    public ctl d;
    private IRouteUI e;
    /* access modifiers changed from: private */
    public acg f;
    private a g;

    /* renamed from: ejm$a */
    /* compiled from: TrainPlanListPresenter */
    class a implements ada {
        private a() {
        }

        /* synthetic */ a(ejm ejm, byte b) {
            this();
        }

        public final void onTypeChange(RouteType routeType, RouteType routeType2) {
            RequestStatus requestStatus;
            if (RouteType.TRAIN == routeType2 && ejm.this.f != null) {
                TrainPlanListPage trainPlanListPage = (TrainPlanListPage) ejm.this.mPage;
                ein ein = trainPlanListPage.j;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 E");
                boolean z = true;
                boolean z2 = !TextUtils.equals(simpleDateFormat.format(new Date(ejt.a().b())), simpleDateFormat.format(ein.h));
                if (z2) {
                    trainPlanListPage.j.f();
                    trainPlanListPage.j.d();
                    trainPlanListPage.j.a();
                }
                TrainPlanListPage trainPlanListPage2 = (TrainPlanListPage) ejm.this.mPage;
                if (trainPlanListPage2.g != null) {
                    requestStatus = trainPlanListPage2.g.b;
                } else {
                    requestStatus = null;
                }
                boolean z3 = requestStatus == RequestStatus.FAILED_NET_ERROR && NetworkReachability.b();
                POI f = ejm.this.f.f();
                POI h = ejm.this.f.h();
                boolean z4 = !bnx.a(h, ejm.this.b);
                if (!(!bnx.a(f, ejm.this.a)) && !z4) {
                    z = false;
                }
                if (z2) {
                    ejm.this.c.a(new a());
                } else if (z3) {
                    ejm.this.c.a(new a());
                } else if (z) {
                    ejm.this.a = f;
                    ejm.this.b = h;
                    ejm.this.c.a(new a());
                }
                ejm.this.d();
            }
        }
    }

    /* renamed from: ejm$b */
    /* compiled from: TrainPlanListPresenter */
    public static class b {
        ejm a;
        LinkedList<a> b = new LinkedList<>();

        /* renamed from: ejm$b$a */
        /* compiled from: TrainPlanListPresenter */
        public static class a {
        }

        public b(ejm ejm) {
            this.a = ejm;
        }

        public final void a(a aVar) {
            if (!this.b.contains(aVar)) {
                this.b.push(aVar);
            }
        }

        public final void a() {
            if (((TrainPlanListPage) this.a.mPage).isResumed()) {
                b();
            }
        }

        private void b() {
            if (this.b.size() > 0) {
                ((TrainPlanListPage) this.a.mPage).f();
                this.b.clear();
            }
        }
    }

    public final boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public final void a() {
        if (this.d != null) {
            this.d.a("15");
        }
    }

    public ejm(TrainPlanListPage trainPlanListPage) {
        super(trainPlanListPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        this.c = new b(this);
        this.c.a(new a());
        this.f = (acg) defpackage.esb.a.a.a(acg.class);
        this.g = new a(this, 0);
        if (this.f != null) {
            this.a = this.f.f();
            this.b = this.f.h();
            d();
            this.f.a(new String[]{"出发城市", "到达城市"});
            this.f.a((ada) this.g);
        }
    }

    public final void onPause() {
        super.onPause();
        ((axd) ((TrainPlanListPage) this.mPage).getContentView().getParent()).getRouteInputUI().a((axe) null);
    }

    public final void onStop() {
        ((TrainPlanListPage) this.mPage).j.e();
        ((TrainPlanListPage) this.mPage).setArguments(null);
        super.onStop();
    }

    public final void onResume() {
        super.onResume();
        StationRequestManger.a().a(0);
        ((TrainPlanListPage) this.mPage).requestScreenOrientation(1);
        TrainPlanListPage trainPlanListPage = (TrainPlanListPage) this.mPage;
        ((axd) trainPlanListPage.getContentView().getParent()).getRouteInputUI().a(trainPlanListPage.p);
        this.e = ((axd) ((TrainPlanListPage) this.mPage).getContentView().getParent()).getRouteInputUI();
        if (this.f != null) {
            this.f.a((acy) new acy() {
                public final void onDataChange(POI poi, List<POI> list, POI poi2) {
                    boolean z = !ejm.this.f.a(poi, ejm.this.a);
                    boolean z2 = !ejm.this.f.a(poi2, ejm.this.b);
                    boolean a2 = bnx.a(poi, poi2);
                    if (bno.a) {
                        StringBuilder sb = new StringBuilder("(onDataChange) startChange: ");
                        sb.append(z);
                        sb.append(" endChange: ");
                        sb.append(z2);
                        sb.append(" isSamePOI: ");
                        sb.append(a2);
                        eao.a((String) "TrainPlanList", sb.toString());
                    }
                    if (!a2) {
                        ejm.this.a = poi;
                        ejm.this.b = poi2;
                        if (z || z2) {
                            ejm.this.d();
                            ejm.this.c.a(new a());
                            ejm.this.c.a();
                        }
                    } else {
                        ToastHelper.showToast(((TrainPlanListPage) ejm.this.mPage).getString(R.string.route_same_from_to));
                    }
                }
            });
        }
        ((TrainPlanListPage) this.mPage).a(((TrainPlanListPage) this.mPage).getArguments());
        ((TrainPlanListPage) this.mPage).a(false);
        TrainPlanListPage trainPlanListPage2 = (TrainPlanListPage) this.mPage;
        IRouteUI iRouteUI = this.e;
        if (!trainPlanListPage2.c && trainPlanListPage2.d != null) {
            iRouteUI.a((View) trainPlanListPage2.d);
            trainPlanListPage2.c = true;
        }
        eez a2 = eez.a();
        if ((a2.a == null || a2.b == null || a2.c == null) ? false : true) {
            ContainerType containerType = ContainerType.FLOW_VIEW;
            ContainerType[] containerTypeArr = a2.d;
            if (!(containerType == null || containerTypeArr == null)) {
                int length = containerTypeArr.length;
                ArrayList arrayList = new ArrayList();
                Arrays.asList(new Object[]{arrayList, containerTypeArr});
                arrayList.remove(containerType);
                arrayList.add(0, containerType);
                if (length == arrayList.size()) {
                    arrayList.toArray(containerTypeArr);
                }
            }
            a2.a(containerTypeArr);
        }
        TrainPlanListPage trainPlanListPage3 = (TrainPlanListPage) this.mPage;
        if (trainPlanListPage3.i != null) {
            trainPlanListPage3.i.resetbanner();
            trainPlanListPage3.i.loadbanner(RouteBanner.REQUEST_KEY_TRAIN);
        }
        if (this.e.o()) {
            PageBundle arguments = ((TrainPlanListPage) this.mPage).getArguments();
            if (arguments != null) {
                int a3 = eqg.a(arguments.getString("bundleKeyVoiceCmd"));
                if (a3 != -1) {
                    d.a.a(a3, 10000, (String) null);
                }
            }
        }
        this.c.a();
        if (((TrainPlanListPage) this.mPage).k) {
            this.d = (ctl) defpackage.esb.a.a.a(ctl.class);
            ((TrainPlanListPage) this.mPage).k = false;
            if (this.d != null) {
                this.d.a("15", new TrainPlanListPresenter$2(this));
            }
        }
        AMapPageUtil.setPageStateListener((bid) this.mPage, new IPageStateListener() {
            public final void onAppear() {
            }

            public final void onCover() {
                ejm.this.a();
            }
        });
    }

    public final void onDestroy() {
        b bVar = this.c;
        bVar.a = null;
        bVar.b.clear();
        AMapPageUtil.removePageStateListener((bid) this.mPage);
        if (this.f != null) {
            this.f.b((ada) this.g);
        }
        super.onDestroy();
    }

    public final void onNewIntent(PageBundle pageBundle) {
        ((TrainPlanListPage) this.mPage).f = false;
        super.onNewIntent(pageBundle);
        ((TrainPlanListPage) this.mPage).a(pageBundle);
        ((TrainPlanListPage) this.mPage).a(true);
    }

    public final ON_BACK_TYPE onBackPressed() {
        boolean z;
        TrainPlanListPage trainPlanListPage = (TrainPlanListPage) this.mPage;
        if (trainPlanListPage.d == null || !trainPlanListPage.d.isShown()) {
            z = false;
        } else {
            trainPlanListPage.d.setVisibility(8);
            z = true;
        }
        return z ? ON_BACK_TYPE.TYPE_IGNORE : super.onBackPressed();
    }

    public final String b() {
        if (this.a == null || this.f.j() != RouteType.TRAIN || "我的位置".equals(this.a.getName())) {
            return ejx.a(this.a);
        }
        return this.a.getName();
    }

    public final String c() {
        if (this.b == null || this.f.k() != RouteType.TRAIN || "我的位置".equals(this.b.getName())) {
            return ejx.a(this.b);
        }
        return this.b.getName();
    }

    /* access modifiers changed from: private */
    public void d() {
        String b2 = b();
        String c2 = c();
        if (TextUtils.equals(b2, c2)) {
            this.f.a(this.a != null ? this.a.getName() : "");
            this.f.b(this.b != null ? this.b.getName() : "");
            return;
        }
        this.f.a(b2);
        this.f.b(c2);
    }

    public final void a(POI poi) {
        if (this.f != null) {
            this.b = poi;
            this.f.b(poi);
        }
    }
}
