package com.autonavi.minimap.route.bus.realtimebus.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.route.bus.inter.impl.BusLineSearch;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.bus.realtimebus.adapter.RTBusLineDetailAdapter;
import com.autonavi.minimap.route.bus.realtimebus.presenter.RTBusListPresenter$1;
import com.autonavi.widget.ui.TitleBar;

public class RTBusListPage extends AbstractBasePage<dyp> implements OnClickListener, dvn {
    public ListView a;
    public RTBusLineDetailAdapter b;
    private TextView c;
    private TextView d;
    private TextView e;
    private TextView f;
    private TextView g;
    private TextView h;
    private TextView i;
    private View j;
    private TitleBar k;

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.route_realtime_busline_detail_view);
        View contentView = getContentView();
        this.k = (TitleBar) contentView.findViewById(R.id.title_bar);
        this.k.setBackImgContentDescription(getString(R.string.autonavi_back));
        this.k.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RTBusListPage.this.finish();
            }
        });
        this.k.setOnActionClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RTBusListPage.a(RTBusListPage.this);
            }
        });
        this.a = (ListView) contentView.findViewById(R.id.realtime_bus_detail_list);
        this.c = (TextView) contentView.findViewById(R.id.buline_name);
        this.d = (TextView) contentView.findViewById(R.id.txtStartEndStationName);
        this.j = contentView.findViewById(R.id.btn_busline_return);
        this.j.setOnClickListener(this);
        this.e = (TextView) contentView.findViewById(R.id.timeStart);
        this.f = (TextView) contentView.findViewById(R.id.timeEnd);
        this.g = (TextView) contentView.findViewById(R.id.txtInterval);
        this.h = (TextView) contentView.findViewById(R.id.distance);
        this.i = (TextView) contentView.findViewById(R.id.price);
    }

    public final void a(Bus bus) {
        if (bus != null) {
            String str = bus.key_name;
            if (TextUtils.isEmpty(bus.key_name)) {
                str = bus.name.replace(bus.startName, "").replace(bus.endName, "").replace("--", "").replace("(", "").replace(")", "");
                bus.key_name = str;
            }
            this.c.setText(str);
            this.k.setActionTextEnable(false);
            StringBuilder sb = new StringBuilder();
            sb.append(bus.startName);
            sb.append(" > ");
            sb.append(bus.endName);
            this.d.setText(axs.a(sb.toString()));
            if (bus.startTime < 0) {
                this.e.setVisibility(8);
            } else {
                this.e.setText(axt.b(bus.startTime));
                this.e.setVisibility(0);
            }
            if (bus.endTime < 0) {
                this.f.setVisibility(8);
            } else {
                this.f.setText(axt.b(bus.endTime));
                this.f.setVisibility(0);
            }
            if (TextUtils.isEmpty(bus.interval)) {
                this.g.setVisibility(8);
            } else {
                this.g.setVisibility(0);
                this.g.setText(bus.interval);
            }
            if (bus.length > 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(getString(R.string.busline_whole_journey));
                sb2.append(bus.length);
                sb2.append(getString(R.string.km));
                this.h.setText(sb2.toString());
                this.h.setVisibility(0);
            } else {
                this.h.setVisibility(8);
            }
            String ticketDesc = bus.getTicketDesc();
            if (TextUtils.isEmpty(ticketDesc)) {
                this.i.setVisibility(8);
            } else {
                this.i.setText(ticketDesc);
                this.i.setVisibility(0);
            }
            if (TextUtils.isEmpty(((dyp) this.mPresenter).c)) {
                this.j.setVisibility(8);
            } else {
                this.j.setVisibility(0);
            }
        }
    }

    public final void a(boolean z) {
        this.k.setActionTextEnable(z);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_busline_return) {
            dyp dyp = (dyp) this.mPresenter;
            if (dyp.b == null) {
                BusLineSearch.a(dyp.c, dyp.d, (Callback<IBusLineSearchResult>) new RTBusListPresenter$1<IBusLineSearchResult>(dyp), true);
                return;
            }
            ((RTBusListPage) dyp.mPage).a();
        }
    }

    public final void a() {
        boolean z;
        dyp dyp = (dyp) this.mPresenter;
        if (dyp.b == null) {
            z = false;
        } else {
            Bus bus = dyp.a;
            dyp.a = dyp.b;
            dyp.b = bus;
            z = true;
        }
        if (z) {
            a(((dyp) this.mPresenter).a);
            if (this.b != null) {
                this.b.setData(((dyp) this.mPresenter).a);
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dyp(this);
    }

    static /* synthetic */ void a(RTBusListPage rTBusListPage) {
        if (rTBusListPage.b != null) {
            btd wannaAttentionStation = rTBusListPage.b.getWannaAttentionStation();
            if (wannaAttentionStation == null) {
                ToastHelper.showToast(rTBusListPage.getString(R.string.realtime_dao_no_data_can_save));
                return;
            }
            rTBusListPage.getContext();
            if (bso.a().a(wannaAttentionStation).booleanValue()) {
                new MapSharePreference(SharePreferenceName.user_route_method_info).putBooleanValue("realbus_position_attention_need_roaledDB", true);
                ToastHelper.showToast(rTBusListPage.getString(R.string.realtime_dao_save_suc));
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("start_from_busline_follow", "rt_position_start_from_busline_follow");
                rTBusListPage.startPage(RTBusPositionPage.class, pageBundle);
                return;
            }
            ToastHelper.showToast(rTBusListPage.getString(R.string.realtime_dao_fail));
        }
    }
}
