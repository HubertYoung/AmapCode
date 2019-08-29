package defpackage;

import android.content.res.Configuration;
import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.route.bus.busline.page.BusLineSearchPage;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineSearchPresenter$1;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.route.bus.inter.impl.BusLineSearch;
import com.autonavi.sdk.location.LocationInstrument;
import com.tencent.connect.common.Constants;

/* renamed from: duu reason: default package */
/* compiled from: BusLineSearchPresenter */
public final class duu extends eaf<BusLineSearchPage> {
    public String a = "";
    public String b = "";
    public TipItem c;
    public final Callback<IBusLineSearchResult> d = new BusLineSearchPresenter$1(this);
    private String e = "";

    /* renamed from: duu$1 reason: invalid class name */
    /* compiled from: BusLineSearchPresenter */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[ResultType.values().length];

        static {
            try {
                a[ResultType.OK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public duu(BusLineSearchPage busLineSearchPage) {
        super(busLineSearchPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        c();
    }

    private void c() {
        if (!a(((BusLineSearchPage) this.mPage).getArguments())) {
            String string = ((BusLineSearchPage) this.mPage).getString(R.string.busline_beijing);
            long j = 0;
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            if (latestPosition != null) {
                j = (long) latestPosition.getAdCode();
                if (TextUtils.isEmpty(this.e) || TextUtils.isEmpty(this.b)) {
                    string = latestPosition.getCity();
                    j = (long) latestPosition.getAdCode();
                    this.e = string;
                    this.b = String.valueOf(j);
                }
            }
            MapManager mapManager = DoNotUseTool.getMapManager();
            int w = mapManager.getMapView().w();
            GeoPoint o = mapManager.getMapView().o();
            if (TextUtils.isEmpty(this.e) || TextUtils.isEmpty(this.b) || w >= 8) {
                string = o.getCity();
                j = (long) o.getAdCode();
                this.e = string;
                this.b = String.valueOf(j);
            }
            BusLineSearchPage busLineSearchPage = (BusLineSearchPage) this.mPage;
            busLineSearchPage.a(latestPosition, j, o, string, this.a);
        }
    }

    private boolean a(PageBundle pageBundle) {
        if (pageBundle != null) {
            if (pageBundle.containsKey(TrafficUtil.KEYWORD)) {
                this.a = pageBundle.getString(TrafficUtil.KEYWORD);
                ((BusLineSearchPage) this.mPage).a(this.a);
            }
            if (pageBundle.containsKey("city") && pageBundle.containsKey("busname")) {
                this.a = pageBundle.getString("busname");
                String string = pageBundle.getString("city");
                if (string == null || !Character.isDigit(string.charAt(0))) {
                    lj b2 = li.a().b(string);
                    if (b2 != null) {
                        this.b = String.valueOf(b2.i);
                    } else {
                        this.b = "010";
                    }
                } else {
                    this.b = string;
                }
                try {
                    lj a2 = li.a().a(this.b);
                    if (a2 != null && !TextUtils.isEmpty(a2.a)) {
                        ((BusLineSearchPage) this.mPage).b(a2.a);
                    }
                } catch (Exception e2) {
                    kf.a((Throwable) e2);
                }
                ((BusLineSearchPage) this.mPage).a.setSelfCall(true);
                ((BusLineSearchPage) this.mPage).a(this.a);
                b();
                return true;
            }
        }
        return false;
    }

    public final void a() {
        ((BusLineSearchPage) this.mPage).b.clearFocus();
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("SWITCH_CITY_FOR", 0);
        ((BusLineSearchPage) this.mPage).startPageForResult((String) "amap.basemap.action.switch_city_node_page", pageBundle, 1);
    }

    public final void b() {
        if (TextUtils.isEmpty(this.a) || TextUtils.isEmpty(this.a.trim())) {
            ToastHelper.showLongToast(((BusLineSearchPage) this.mPage).getString(R.string.act_search_error_empty));
            eko.a(10001);
            return;
        }
        this.c = new TipItem();
        this.c.name = this.a;
        BusLineSearch.a(this.a, 1, this.b, this.d);
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (AnonymousClass1.a[resultType.ordinal()] == 1 && i == 1 && "action_switch_city".equals(pageBundle.getString(Constants.KEY_ACTION))) {
            String str = "";
            if (pageBundle.containsKey("key_city_adcode")) {
                str = pageBundle.getString("key_city_adcode");
                try {
                    BusLineSearchPage busLineSearchPage = (BusLineSearchPage) this.mPage;
                    busLineSearchPage.a.setAdcode(Long.parseLong(str));
                    busLineSearchPage.d.setAdcode(Long.parseLong(str));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (TextUtils.equals(str, this.b)) {
                ((BusLineSearchPage) this.mPage).a.clearSuggestionData();
            }
            this.b = str;
            if (pageBundle.containsKey("key_area_name")) {
                this.e = pageBundle.getString("key_area_name");
                ((BusLineSearchPage) this.mPage).b(this.e);
            }
        }
    }

    public final void onStop() {
        super.onStop();
        BusLineSearchPage busLineSearchPage = (BusLineSearchPage) this.mPage;
        busLineSearchPage.c.cancelTask();
        busLineSearchPage.d.cancelTask();
    }

    public final void onStart() {
        super.onStart();
        SuperId.getInstance().reset();
        SuperId.getInstance().setBit1("i");
        ((BusLineSearchPage) this.mPage).a.setSuperIdBit1("i");
    }

    public final void onDestroy() {
        super.onDestroy();
        BusLineSearchPage busLineSearchPage = (BusLineSearchPage) this.mPage;
        if (busLineSearchPage.a != null) {
            busLineSearchPage.a.onDestory();
        }
        if (busLineSearchPage.d != null) {
            busLineSearchPage.d.onDestroy();
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        BusLineSearchPage busLineSearchPage = (BusLineSearchPage) this.mPage;
        if (busLineSearchPage.c != null) {
            busLineSearchPage.c.initNoHistoryTipText();
        }
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        c();
    }

    public final void onResume() {
        super.onResume();
        aho.a(new Runnable() {
            public final void run() {
                if (BusLineSearchPage.this.a.hasFocus()) {
                    BusLineSearchPage.this.a.showInputMethod();
                } else {
                    BusLineSearchPage.this.a.hideInputMethod();
                }
            }
        }, 200);
    }
}
