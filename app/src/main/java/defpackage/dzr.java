package defpackage;

import android.view.View;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.coach.controller.CoachUIStatusController.ResultStatus;
import com.autonavi.minimap.route.coach.page.CoachResultListPage;
import java.util.LinkedList;
import java.util.List;

/* renamed from: dzr reason: default package */
/* compiled from: CoachResultListPresenter */
public final class dzr extends eaf<CoachResultListPage> implements bgo {
    public IRouteUI a;
    public dzt b;
    public POI c;
    public POI d;
    public dzj e;
    /* access modifiers changed from: private */
    public acg f;
    /* access modifiers changed from: private */
    public b g;

    /* renamed from: dzr$a */
    /* compiled from: CoachResultListPresenter */
    class a implements ada {
        private a() {
        }

        /* synthetic */ a(dzr dzr, byte b) {
            this();
        }

        public final void onTypeChange(RouteType routeType, RouteType routeType2) {
            if (RouteType.COACH == routeType2) {
                POI f = dzr.this.f.f();
                POI h = dzr.this.f.h();
                boolean z = true;
                boolean z2 = (bnx.a(f, dzr.this.c) ^ true) || (bnx.a(h, dzr.this.d) ^ true);
                boolean z3 = (((CoachResultListPage) dzr.this.mPage).a() == ResultStatus.FAILED_NET_ERROR || ((CoachResultListPage) dzr.this.mPage).a() == null) && NetworkReachability.b();
                dzr.this.e.a(z2);
                if (z2) {
                    dzr.this.c = f;
                    dzr.this.d = h;
                    dzr.this.g.a(new a(true));
                } else if (z3) {
                    dzr.this.g.a(new a(false));
                } else if (((CoachResultListPage) dzr.this.mPage).c.c) {
                    dzr.this.g.a(new a(false));
                } else if (((CoachResultListPage) dzr.this.mPage).n) {
                    dzr.this.g.a(new a(false));
                } else {
                    z = false;
                }
                if (z) {
                    ((CoachResultListPage) dzr.this.mPage).c();
                    ((CoachResultListPage) dzr.this.mPage).n = false;
                    ((CoachResultListPage) dzr.this.mPage).c.c = false;
                    ((CoachResultListPage) dzr.this.mPage).c.b();
                }
                dzr.this.b.a(RouteType.COACH, dzr.this.c, dzr.this.d);
                dzr.this.g.a();
            }
        }
    }

    /* renamed from: dzr$b */
    /* compiled from: CoachResultListPresenter */
    static class b {
        dzr a;
        LinkedList<a> b = new LinkedList<>();

        /* renamed from: dzr$b$a */
        /* compiled from: CoachResultListPresenter */
        public static class a {
            boolean a;

            a(boolean z) {
                this.a = z;
            }
        }

        public b(dzr dzr) {
            this.a = dzr;
        }

        public final void a(a aVar) {
            if (!this.b.contains(aVar)) {
                this.b.push(aVar);
            }
        }

        public final void a() {
            if (this.a.mPage != null && ((CoachResultListPage) this.a.mPage).isResumed()) {
                b();
            }
        }

        private void b() {
            if (this.a.mPage != null) {
                int size = this.b.size();
                if (size > 0) {
                    boolean z = false;
                    boolean z2 = false;
                    while (true) {
                        if (size <= 0) {
                            z = z2;
                            break;
                        }
                        a pop = this.b.pop();
                        if (pop != null && pop.a) {
                            ((CoachResultListPage) this.a.mPage).b(ResultStatus.LOADING_NO_DATE);
                            break;
                        } else {
                            z2 = true;
                            size = this.b.size();
                        }
                    }
                    if (z) {
                        ((CoachResultListPage) this.a.mPage).b(ResultStatus.LOADING);
                    }
                    this.b.clear();
                }
            }
        }
    }

    public final boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public dzr(CoachResultListPage coachResultListPage) {
        super(coachResultListPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        dzs.a().c();
        this.g = new b(this);
        this.g.a(new a(false));
        this.e = new dzj(this);
        this.f = (acg) defpackage.esb.a.a.a(acg.class);
        if (this.f != null) {
            this.f.a((ada) new a(this, 0));
            this.f.a(new String[]{"出发城市", "到达城市"});
            this.c = this.f.f();
            this.d = this.f.h();
        }
        this.b = new dzt();
        this.b.h = this.e;
        this.b.a(RouteType.COACH, this.c, this.d);
    }

    public final void onStart() {
        super.onStart();
    }

    public final void onResume() {
        super.onResume();
        CoachResultListPage coachResultListPage = (CoachResultListPage) this.mPage;
        if (defpackage.eqc.a.a.b) {
            IRouteUI routeInputUI = ((axd) coachResultListPage.getContentView().getParent()).getRouteInputUI();
            if (!(routeInputUI == null || !routeInputUI.o() || ((dzr) coachResultListPage.mPresenter).c == null || ((dzr) coachResultListPage.mPresenter).d == null)) {
                PageBundle arguments = coachResultListPage.getArguments();
                if (arguments != null) {
                    POI poi = (POI) arguments.getObject("bundle_key_poi_end");
                    if (bnx.a(poi, ((dzr) coachResultListPage.mPresenter).d)) {
                        ((dzr) coachResultListPage.mPresenter).a(poi);
                        coachResultListPage.a(coachResultListPage.p);
                    } else {
                        ((dzr) coachResultListPage.mPresenter).a(poi);
                        coachResultListPage.b(ResultStatus.LOADING);
                    }
                }
            }
        }
        this.a = ((axd) ((CoachResultListPage) this.mPage).getContentView().getParent()).getRouteInputUI();
        this.a.a((axe) this.mPage);
        View view = ((CoachResultListPage) this.mPage).e;
        boolean z = false;
        if (view != null) {
            this.a.a(view);
            view.setVisibility(0);
        }
        this.f.a((acy) new acy() {
            public final void onDataChange(POI poi, List<POI> list, POI poi2) {
                int i;
                int i2;
                int i3;
                if (dzr.this.f.a() == RouteType.COACH) {
                    boolean z = !dzr.this.f.a(poi, dzr.this.c);
                    boolean z2 = !dzr.this.f.a(poi2, dzr.this.d);
                    int i4 = 0;
                    boolean z3 = z || z2;
                    dzr.this.e.a(z3);
                    if (z3) {
                        dzr.this.c = z ? dzr.this.f.f() : dzr.this.c;
                        dzr.this.d = z2 ? dzr.this.f.h() : dzr.this.d;
                        if (z && z2) {
                            i2 = dzr.this.b.b;
                            i = dzr.this.b.a;
                        } else {
                            if (z) {
                                i4 = dzr.this.b.b;
                                i3 = 1;
                            } else {
                                i3 = 0;
                            }
                            if (z2) {
                                i2 = dzr.this.b.a;
                                i = 1;
                            } else {
                                i2 = i3;
                                i = i4;
                            }
                        }
                        dzr.this.b.a(RouteType.COACH, poi, poi2, i2, i, true);
                        ((CoachResultListPage) dzr.this.mPage).c();
                        dzr.this.g.a(new a(true));
                        dzr.this.g.a();
                        return;
                    }
                    ToastHelper.showToast(((CoachResultListPage) dzr.this.mPage).getString(R.string.route_same_from_to));
                }
            }
        });
        if (((CoachResultListPage) this.mPage).c.c && ((CoachResultListPage) this.mPage).c.e) {
            ((CoachResultListPage) this.mPage).c.c = false;
            ((CoachResultListPage) this.mPage).c.b();
            boolean z2 = !bnx.a(this.f.h(), this.d);
            if ((!bnx.a(this.f.f(), this.c)) || z2) {
                z = true;
            }
            this.e.a(z);
            this.g.a(new a(z));
        }
        this.g.a();
        if (this.a.o()) {
            PageBundle arguments2 = ((CoachResultListPage) this.mPage).getArguments();
            if (arguments2 != null) {
                int a2 = eqg.a(arguments2.getString("bundleKeyVoiceCmd"));
                if (a2 != -1) {
                    d.a.a(a2, 10000, (String) null);
                }
            }
        }
    }

    public final void onPause() {
        super.onPause();
        this.a.a((axe) null);
    }

    public final ON_BACK_TYPE onBackPressed() {
        CoachResultListPage coachResultListPage = (CoachResultListPage) this.mPage;
        if (coachResultListPage.f != null ? coachResultListPage.f.onBackPressed() : false) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }

    public final void onStop() {
        if (this.a != null) {
            View view = ((CoachResultListPage) this.mPage).e;
            if (view != null) {
                this.a.b(view);
            }
            if (view != null) {
                view.setVisibility(8);
            }
        }
        super.onStop();
    }

    public final void onDestroy() {
        b bVar = this.g;
        bVar.a = null;
        bVar.b.clear();
        dzs.a().c();
        this.b.c.a();
        super.onDestroy();
    }

    private void a(POI poi) {
        if (this.f != null) {
            this.d = poi;
            this.f.b(poi);
        }
    }
}
