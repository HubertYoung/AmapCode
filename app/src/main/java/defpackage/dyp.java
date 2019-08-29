package defpackage;

import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.route.bus.inter.IBusLineResult;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.bus.realtimebus.adapter.RTBusLineDetailAdapter;
import com.autonavi.minimap.route.bus.realtimebus.page.RTBusListPage;

/* renamed from: dyp reason: default package */
/* compiled from: RTBusListPresenter */
public final class dyp extends eaf<RTBusListPage> {
    public Bus a;
    public Bus b;
    public String c;
    public String d;
    /* access modifiers changed from: private */
    public IBusLineResult e;
    private String f;

    public dyp(RTBusListPage rTBusListPage) {
        super(rTBusListPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        PageBundle arguments = ((RTBusListPage) this.mPage).getArguments();
        if (arguments != null) {
            if (arguments.containsKey("real_time_bus_adcode")) {
                this.f = arguments.getString("real_time_bus_adcode");
            }
            if (arguments.containsKey("BusLineDetailFragment.IBusLineResult")) {
                this.e = (IBusLineResult) arguments.getObject("BusLineDetailFragment.IBusLineResult");
                Bus[] busLineArray = this.e.getBusLineArray(this.e.getCurPoiPage());
                int focusBusLineIndex = this.e.getFocusBusLineIndex();
                if (!(busLineArray == null || busLineArray.length <= focusBusLineIndex || busLineArray[focusBusLineIndex] == null)) {
                    this.a = busLineArray[focusBusLineIndex].copyObject();
                    if (!this.a.id.equals(this.a.returnId)) {
                        this.c = this.a.returnId;
                        this.d = this.a.areacode;
                    }
                }
            }
        }
        ((RTBusListPage) this.mPage).a(this.a);
        RTBusListPage rTBusListPage = (RTBusListPage) this.mPage;
        String str = this.f;
        Bus bus = this.a;
        rTBusListPage.b = new RTBusLineDetailAdapter(rTBusListPage.getContext());
        rTBusListPage.b.setListItemInteraction(rTBusListPage);
        rTBusListPage.b.setAdcode(str);
        rTBusListPage.a.setAdapter(rTBusListPage.b);
        rTBusListPage.b.setData(bus);
    }

    public final void onStart() {
        super.onStart();
    }

    public final void onStop() {
        super.onStop();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
    }

    public final void onDestroy() {
        super.onDestroy();
        RTBusListPage rTBusListPage = (RTBusListPage) this.mPage;
        if (rTBusListPage.b != null) {
            rTBusListPage.b.clearData();
        }
    }
}
