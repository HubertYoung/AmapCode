package com.autonavi.minimap.route.bus.busline.presenter;

import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.busline.page.BusLineResultPage;
import com.autonavi.minimap.route.bus.inter.IBusLineResult;
import com.autonavi.minimap.route.bus.model.Bus;

public final class BusLineResultPresenter extends eaf<BusLineResultPage> {
    public String a = "BusLineSearchFragment";
    public Bus[] b = null;
    public int c = 1;
    public int d = 0;
    public IBusLineResult e;
    private String f;

    abstract class BusLineResultCallback<Result> implements Callback<Result> {
        private BusLineResultCallback() {
        }

        /* synthetic */ BusLineResultCallback(BusLineResultPresenter busLineResultPresenter, byte b2) {
            this();
        }
    }

    public BusLineResultPresenter(BusLineResultPage busLineResultPage) {
        super(busLineResultPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        PageBundle arguments = ((BusLineResultPage) this.mPage).getArguments();
        if (arguments != null && arguments.containsKey("BusLineResultFragment.IBusLineResult")) {
            if (arguments.containsKey("from")) {
                this.a = (String) arguments.get("from");
            }
            if (arguments.containsKey("real_time_bus_adcode")) {
                this.f = arguments.getString("real_time_bus_adcode");
            }
            Object object = arguments.getObject("BusLineResultFragment.IBusLineResult");
            if (object != null && (object instanceof IBusLineResult)) {
                this.e = (IBusLineResult) object;
            }
            if (arguments.containsKey("BusLineResultFragment.KEYWORD") && this.e != null) {
                this.e.setSearchKeyword(arguments.getString("BusLineResultFragment.KEYWORD"));
            }
            if (this.e != null) {
                a();
                return;
            }
            BusLineResultPage busLineResultPage = (BusLineResultPage) this.mPage;
            ToastHelper.showToast(busLineResultPage.getString(R.string.busline_no_busline_data));
            busLineResultPage.finish();
        }
    }

    public final void onResume() {
        super.onResume();
        BusLineResultPage busLineResultPage = (BusLineResultPage) this.mPage;
        if (busLineResultPage.b != null) {
            busLineResultPage.b.notifyDataSetChanged();
        }
    }

    public final void a() {
        this.c = this.e.getCurPoiPage();
        this.d = this.e.getTotalPoiPage();
        this.b = this.e.getBusLineArray(this.c);
        if (this.b == null) {
            this.b = new Bus[0];
        }
        ((BusLineResultPage) this.mPage).a(this.b);
        this.e.setFocusStationIndex(-1);
        this.e.setFocusBusLineIndex((this.c - 1) * 10);
        ((BusLineResultPage) this.mPage).a(this.c, this.d);
    }

    public static Uri a(int i, Bus bus) {
        if (bus == null || bus.id == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder("amapuri://realtimeBus/detail?param={");
        if (!TextUtils.isEmpty(bus.id)) {
            sb.append("lineId:");
            sb.append(bus.id);
        }
        if (bus.stationIds.length > i) {
            String str = bus.stationIds[i];
            if (!TextUtils.isEmpty(str)) {
                sb.append(",stationId:");
                sb.append(str);
            }
        }
        sb.append(",from:find_bus}");
        return Uri.parse(sb.toString());
    }

    static /* synthetic */ void f(BusLineResultPresenter busLineResultPresenter) {
        busLineResultPresenter.c--;
        ToastHelper.showToast(((BusLineResultPage) busLineResultPresenter.mPage).getString(R.string.error_no_record_found));
        busLineResultPresenter.e.setCurPoiPage(busLineResultPresenter.c);
        busLineResultPresenter.a();
    }
}
