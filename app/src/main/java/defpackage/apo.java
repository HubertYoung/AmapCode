package defpackage;

import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.airticket.module.ModuleAirTicket;
import com.autonavi.bundle.airticket.page.AjxAirTicketResultPage;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: apo reason: default package */
/* compiled from: AjxAirTicketResultPresenter */
public final class apo<Page extends AjxAirTicketResultPage> extends Ajx3PagePresenter {
    protected Page a;
    public POI b;
    public POI c;
    /* access modifiers changed from: private */
    public acg d;
    /* access modifiers changed from: private */
    public b e;
    private a f;
    /* access modifiers changed from: private */
    public boolean g;

    /* renamed from: apo$a */
    /* compiled from: AjxAirTicketResultPresenter */
    class a implements ada {
        private a() {
        }

        /* synthetic */ a(apo apo, byte b) {
            this();
        }

        public final void onTypeChange(RouteType routeType, RouteType routeType2) {
            if (RouteType.AIRTICKET != routeType2) {
                AmapAjxView ajxView = apo.this.a.getAjxView();
                if (ajxView != null) {
                    ModuleAirTicket moduleAirTicket = (ModuleAirTicket) ajxView.getJsModule(ModuleAirTicket.MODULE_NAME);
                    if (moduleAirTicket != null) {
                        moduleAirTicket.onTabChanged();
                        return;
                    }
                    return;
                }
                return;
            }
            if (apo.this.d != null) {
                POI f = apo.this.d.f();
                POI h = apo.this.d.h();
                boolean z = true;
                boolean z2 = !bnx.a(h, apo.this.c);
                if (!(!bnx.a(f, apo.this.b)) && !z2) {
                    z = false;
                }
                if (z) {
                    apo.this.b = f;
                    apo.this.c = h;
                    apo.this.e.a(new a());
                }
                apo.this.a();
            }
        }
    }

    /* renamed from: apo$b */
    /* compiled from: AjxAirTicketResultPresenter */
    static class b {
        apo a;
        LinkedList<a> b = new LinkedList<>();

        /* renamed from: apo$b$a */
        /* compiled from: AjxAirTicketResultPresenter */
        public static class a {
        }

        public b(apo apo) {
            this.a = apo;
        }

        public final void a(a aVar) {
            if (!this.b.contains(aVar)) {
                this.b.push(aVar);
            }
        }

        public final void a() {
            if (this.a.a.isResumed() && this.b.size() > 0) {
                this.a.a.a();
                this.b.clear();
            }
        }
    }

    public apo(Page page) {
        super(page);
        this.a = page;
    }

    public final void onPageCreated() {
        super.onPageCreated();
        this.e = new b(this);
        this.e.a(new a());
        this.f = new a<>(this, 0);
        this.d = (acg) defpackage.esb.a.a.a(acg.class);
        if (this.d != null) {
            this.b = this.d.f();
            this.c = this.d.h();
            this.d.a(new String[]{"出发城市", "到达城市"});
            this.d.a((ada) this.f);
            a();
        }
        AMapPageUtil.setPageStateListener(this.a, new IPageStateListener() {
            public final void onAppear() {
            }

            public final void onCover() {
                apo.this.g = true;
            }
        });
    }

    public final void onResume() {
        super.onResume();
        if (this.g) {
            this.g = false;
        }
        if (this.d != null) {
            this.d.a((acy) new acy() {
                public final void onDataChange(POI poi, List<POI> list, POI poi2) {
                    boolean z = !apo.this.d.a(poi, apo.this.b);
                    boolean z2 = !apo.this.d.a(poi2, apo.this.c);
                    boolean a2 = bnx.a(poi, poi2);
                    if (bno.a) {
                        StringBuilder sb = new StringBuilder("(onDataChange) startChange: ");
                        sb.append(z);
                        sb.append(" endChange: ");
                        sb.append(z2);
                        sb.append(" isSamePOI: ");
                        sb.append(a2);
                        eao.a((String) "AjxAirTicketResult", sb.toString());
                    }
                    if (!a2) {
                        apo.this.b = poi;
                        apo.this.c = poi2;
                        if (z || z2) {
                            apo.this.a();
                            apo.this.e.a(new a());
                            apo.this.e.a();
                        }
                    } else {
                        ToastHelper.showToast(apo.this.a.getString(R.string.route_same_from_to));
                    }
                }
            });
        }
        Page page = this.a;
        eao.a((String) "JS:#", (String) "onResume");
        if (page.a == null) {
            page.a = ((axd) page.getContentView().getParent()).getRouteInputUI();
        } else if (page.a.o() && defpackage.eqc.a.a.b) {
            IRouteUI routeInputUI = ((axd) page.getContentView().getParent()).getRouteInputUI();
            if (!(routeInputUI == null || !routeInputUI.o() || ((apo) page.mPresenter).b == null || ((apo) page.mPresenter).c == null || page.getArguments() == null)) {
                POI poi = (POI) page.getArguments().getObject("bundle_key_poi_end");
                if (bnx.a(poi, ((apo) page.mPresenter).c)) {
                    ((apo) page.mPresenter).b(poi);
                    page.a(page.c);
                } else {
                    ((apo) page.mPresenter).b(poi);
                    page.a();
                }
            }
        }
        this.e.a();
        if (((axd) this.a.getContentView().getParent()).getRouteInputUI().o()) {
            PageBundle arguments = this.a.getArguments();
            if (arguments != null) {
                int a2 = eqg.a(arguments.getString("bundleKeyVoiceCmd"));
                if (a2 != -1) {
                    d.a.a(a2, 10000, (String) null);
                }
            }
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        b bVar = this.e;
        bVar.a = null;
        bVar.b.clear();
        this.a.destroy();
    }

    public static String a(POI poi, POI poi2) {
        JSONObject jSONObject = new JSONObject();
        if (poi != null) {
            try {
                jSONObject.put("X1", poi.getPoint().getLongitude());
                jSONObject.put("Y1", poi.getPoint().getLatitude());
                if (poi.getPoiExtra() != null) {
                    jSONObject.put("dep_city", poi.getPoiExtra().get("IATA_CODE"));
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        if (poi2 != null) {
            jSONObject.put("X2", poi2.getPoint().getLongitude());
            jSONObject.put("Y2", poi2.getPoint().getLatitude());
            if (poi2.getPoiExtra() != null) {
                jSONObject.put("arr_city", poi2.getPoiExtra().get("IATA_CODE"));
            }
        }
        return jSONObject.toString();
    }

    /* access modifiers changed from: private */
    public void a() {
        if (this.d != null) {
            String a2 = a(this.b);
            String a3 = a(this.c);
            if (bno.a) {
                StringBuilder sb = new StringBuilder("(setStartEndPoiContent) startCityName: ");
                sb.append(a2);
                sb.append(" endCityName: ");
                sb.append(a3);
                eao.a((String) "AjxAirTicketResult", sb.toString());
            }
            if (TextUtils.equals(a2, a3)) {
                this.d.a(this.b != null ? this.b.getName() : "");
                this.d.b(this.c != null ? this.c.getName() : "");
                return;
            }
            this.d.a(a2);
            this.d.b(a3);
        }
    }

    private static String a(POI poi) {
        if (poi == null) {
            return null;
        }
        lj b2 = li.a().b(poi.getPoint().x, poi.getPoint().y);
        String str = "";
        if (b2 != null) {
            str = b2.a;
            if (str.endsWith("市")) {
                str = str.substring(0, str.length() - 1);
            } else if (str.endsWith("地区")) {
                str = str.substring(0, str.length() - 2);
            }
        }
        return TextUtils.isEmpty(str) ? poi.getName() : str;
    }

    private void b(POI poi) {
        if (this.d != null) {
            this.c = poi;
            this.d.b(poi);
        }
    }
}
