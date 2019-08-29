package defpackage;

import android.text.TextUtils;
import android.widget.ListView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.Callback;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.R;
import com.autonavi.minimap.poi.PoiRequestHolder;
import com.autonavi.minimap.poi.param.BusRequest;
import com.autonavi.minimap.route.bus.realtimebus.callback.RealTimeBusLineSearchCallback;
import com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage;
import com.autonavi.minimap.route.bus.realtimebus.presenter.RTBusSearchPresenter$1;
import com.autonavi.minimap.search.view.SearchHistoryList;
import com.autonavi.minimap.search.view.SearchSuggestList;
import com.autonavi.minimap.widget.SearchEdit;

/* renamed from: dyr reason: default package */
/* compiled from: RTBusSearchPresenter */
public final class dyr extends dyq<RTBusSearchPage> {
    private CompatDialog c;
    private final Callback<duk> d = new RTBusSearchPresenter$1(this);

    public dyr(RTBusSearchPage rTBusSearchPage) {
        super(rTBusSearchPage);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0080 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0081  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a() {
        /*
            r11 = this;
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r11.mPage
            com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage r0 = (com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage) r0
            com.autonavi.common.PageBundle r0 = r0.getArguments()
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x007d
            java.lang.String r3 = "keyword"
            boolean r3 = r0.containsKey(r3)
            if (r3 == 0) goto L_0x0023
            java.lang.String r3 = "keyword"
            java.lang.String r3 = r0.getString(r3)
            com.autonavi.map.fragmentcontainer.page.IPage r4 = r11.mPage
            com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchBasePage r4 = (com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchBasePage) r4
            dwp r4 = r4.a
            r4.a(r3)
        L_0x0023:
            java.lang.String r3 = "city"
            boolean r3 = r0.containsKey(r3)
            if (r3 == 0) goto L_0x007d
            java.lang.String r3 = "busname"
            boolean r3 = r0.containsKey(r3)
            if (r3 == 0) goto L_0x007d
            java.lang.String r3 = "busname"
            java.lang.String r3 = r0.getString(r3)
            java.lang.String r4 = "city"
            java.lang.String r0 = r0.getString(r4)
            char r2 = r0.charAt(r2)
            boolean r2 = java.lang.Character.isDigit(r2)
            if (r2 == 0) goto L_0x004c
            r11.b = r0
            goto L_0x0063
        L_0x004c:
            li r2 = defpackage.li.a()
            lj r0 = r2.b(r0)
            if (r0 == 0) goto L_0x005f
            java.lang.String r0 = r0.i
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r11.b = r0
            goto L_0x0063
        L_0x005f:
            java.lang.String r0 = "010"
            r11.b = r0
        L_0x0063:
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r11.mPage
            com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchBasePage r0 = (com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchBasePage) r0
            dwp r0 = r0.a
            com.autonavi.minimap.widget.SearchEdit r0 = r0.e()
            r0.setSelfCall(r1)
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r11.mPage
            com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchBasePage r0 = (com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchBasePage) r0
            dwp r0 = r0.a
            r0.a(r3)
            r11.a(r3)
            goto L_0x007e
        L_0x007d:
            r1 = 0
        L_0x007e:
            if (r1 == 0) goto L_0x0081
            return
        L_0x0081:
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r11.mPage
            com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage r0 = (com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage) r0
            com.autonavi.common.PageBundle r0 = r0.getArguments()
            com.autonavi.sdk.location.LocationInstrument r1 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            com.autonavi.common.model.GeoPoint r3 = r1.getLatestPosition()
            r1 = 0
            if (r3 == 0) goto L_0x00cd
            int r1 = r3.getAdCode()
            long r1 = (long) r1
            com.autonavi.map.fragmentcontainer.page.IPage r4 = r11.mPage
            com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage r4 = (com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage) r4
            java.lang.String r4 = r4.c()
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x00b0
            java.lang.String r4 = r11.b
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 == 0) goto L_0x00cd
        L_0x00b0:
            com.autonavi.map.fragmentcontainer.page.IPage r4 = r11.mPage
            com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage r4 = (com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage) r4
            java.lang.String r5 = r3.getCity()
            r4.a(r5)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            int r5 = r3.getAdCode()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r11.b = r4
        L_0x00cd:
            bty r4 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapView()
            if (r4 == 0) goto L_0x013c
            bty r4 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapView()
            int r4 = r4.w()
            com.autonavi.common.model.GeoPoint r5 = new com.autonavi.common.model.GeoPoint
            bty r6 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapView()
            int r6 = r6.p()
            bty r7 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapView()
            int r7 = r7.q()
            r5.<init>(r6, r7)
            com.autonavi.map.fragmentcontainer.page.IPage r6 = r11.mPage
            com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage r6 = (com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage) r6
            com.autonavi.minimap.search.view.SearchHistoryList r6 = r6.d()
            if (r6 == 0) goto L_0x0105
            com.autonavi.map.fragmentcontainer.page.IPage r6 = r11.mPage
            com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage r6 = (com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage) r6
            com.autonavi.minimap.search.view.SearchHistoryList r6 = r6.d()
            r6.setCenterPoint(r5)
        L_0x0105:
            com.autonavi.map.fragmentcontainer.page.IPage r6 = r11.mPage
            com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage r6 = (com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage) r6
            java.lang.String r6 = r6.c()
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x011f
            java.lang.String r6 = r11.b
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x011f
            r6 = 9
            if (r4 < r6) goto L_0x013c
        L_0x011f:
            com.autonavi.map.fragmentcontainer.page.IPage r4 = r11.mPage
            com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage r4 = (com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage) r4
            java.lang.String r6 = r5.getCity()
            r4.a(r6)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            int r5 = r5.getAdCode()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r11.b = r4
        L_0x013c:
            java.lang.String r4 = "real_time_bus_adcode"
            boolean r4 = r0.containsKey(r4)
            if (r4 == 0) goto L_0x014c
            java.lang.String r4 = "real_time_bus_adcode"
            java.lang.String r0 = r0.getString(r4)
            r11.b = r0
        L_0x014c:
            java.lang.String r0 = r11.b
            if (r0 == 0) goto L_0x0159
            java.lang.String r0 = r11.b
            int r0 = defpackage.ahh.b(r0)
            long r0 = (long) r0
            r6 = r0
            goto L_0x015a
        L_0x0159:
            r6 = r1
        L_0x015a:
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r11.mPage
            com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage r0 = (com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage) r0
            java.lang.String r1 = r11.b
            dwp r2 = r0.a
            if (r2 == 0) goto L_0x0169
            dwp r0 = r0.a
            r0.a(r1, r6)
        L_0x0169:
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r11.mPage
            com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage r0 = (com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage) r0
            com.autonavi.minimap.search.view.SearchSuggestList r0 = r0.e()
            if (r0 == 0) goto L_0x01b4
            if (r3 == 0) goto L_0x0189
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r11.mPage
            com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage r0 = (com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage) r0
            com.autonavi.minimap.search.view.SearchSuggestList r2 = r0.e()
            r0 = 7
            java.lang.String r1 = "busline"
            r8 = 10077(0x275d, float:1.4121E-41)
            r4 = r6
            r6 = r0
            r7 = r1
            r2.initPosSearch(r3, r4, r6, r7, r8)
            return
        L_0x0189:
            bty r0 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapView()
            if (r0 == 0) goto L_0x01b4
            com.autonavi.common.model.GeoPoint r5 = new com.autonavi.common.model.GeoPoint
            bty r0 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapView()
            int r0 = r0.p()
            bty r1 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapView()
            int r1 = r1.q()
            r5.<init>(r0, r1)
            com.autonavi.map.fragmentcontainer.page.IPage r0 = r11.mPage
            com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage r0 = (com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage) r0
            com.autonavi.minimap.search.view.SearchSuggestList r4 = r0.e()
            r8 = 7
            java.lang.String r9 = "busline"
            r10 = 10077(0x275d, float:1.4121E-41)
            r4.initPosSearch(r5, r6, r8, r9, r10)
        L_0x01b4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dyr.a():void");
    }

    public final SearchHistoryList a(ListView listView) {
        SearchHistoryList searchHistoryList = new SearchHistoryList(((RTBusSearchPage) this.mPage).getContext(), listView, 10077, 7);
        searchHistoryList.setHistoryAmount(10);
        return searchHistoryList;
    }

    public final SearchSuggestList a(SearchEdit searchEdit, ListView listView) {
        SearchSuggestList searchSuggestList = new SearchSuggestList(((RTBusSearchPage) this.mPage).getContext(), searchEdit, listView, 10077, "", false);
        return searchSuggestList;
    }

    public final void a(SearchEdit searchEdit) {
        searchEdit.setHint(((RTBusSearchPage) this.mPage).getString(R.string.real_time_route_search_hint));
    }

    public final void a(String str, String str2) {
        Callback<duk> callback = this.d;
        String scenceId = SuperId.getInstance().getScenceId();
        BusRequest busRequest = new BusRequest();
        busRequest.c = str;
        busRequest.e = str2;
        busRequest.d = "";
        busRequest.l = scenceId;
        busRequest.a = 1;
        busRequest.j = 10;
        PoiRequestHolder.getInstance().sendBus(busRequest, new RealTimeBusLineSearchCallback(callback, busRequest));
        a((AosRequest) busRequest);
    }

    private void a(AosRequest aosRequest) {
        c();
        this.c = aav.a(aosRequest, "加载中...");
        this.c.show();
    }

    /* access modifiers changed from: private */
    public void c() {
        if (this.c != null && this.c.isShowing()) {
            this.c.dismiss();
        }
    }

    public final void b() {
        SuperId.getInstance().setBit7("k_03");
    }

    public final void a(String str) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str.trim())) {
            ToastHelper.showLongToast(((RTBusSearchPage) this.mPage).getString(R.string.act_search_error_empty));
            return;
        }
        this.a = new TipItem();
        this.a.name = str;
        this.a.adcode = this.b;
        String str2 = this.b;
        Callback<duk> callback = this.d;
        String scenceId = SuperId.getInstance().getScenceId();
        BusRequest busRequest = new BusRequest();
        busRequest.c = str;
        busRequest.e = str2;
        busRequest.d = "";
        busRequest.l = scenceId;
        busRequest.a = 1;
        busRequest.j = 10;
        busRequest.n = "k_03";
        PoiRequestHolder.getInstance().sendBus(busRequest, new RealTimeBusLineSearchCallback(callback, busRequest));
        a((AosRequest) busRequest);
    }

    public final void onResume() {
        super.onResume();
        aho.a(new Runnable() {
            public final void run() {
                if (RTBusSearchPage.this.c.hasFocus()) {
                    RTBusSearchPage.this.c.showInputMethod();
                } else {
                    RTBusSearchPage.this.c.hideInputMethod();
                }
            }
        }, 200);
    }

    public final void onPause() {
        super.onPause();
    }
}
