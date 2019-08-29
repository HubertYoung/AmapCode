package com.autonavi.minimap.route.coach.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.R;
import com.autonavi.minimap.order.OrderRequestHolder;
import com.autonavi.minimap.order.param.BusListRequest;
import com.autonavi.minimap.route.coach.adapter.CoachOrderAdapter;
import com.autonavi.minimap.route.coach.net.CoachOrderRequestCallback;
import com.autonavi.minimap.route.coach.page.CoachOrderListPage;
import com.autonavi.minimap.route.coach.util.CoachPurchaseUtil;
import com.autonavi.minimap.route.train.presenter.BaseOrderPresentertWithTitle;
import java.util.List;

public class CoachOrderListPresenter extends BaseOrderPresentertWithTitle<CoachOrderListPage> {
    private CoachOrderAdapter mAdapter;

    public CoachOrderListPresenter(CoachOrderListPage coachOrderListPage) {
        super(coachOrderListPage);
    }

    public AosRequest requestOrderList(int i) {
        BusListRequest busListRequest = new BusListRequest();
        busListRequest.b = i;
        CoachOrderRequestCallback coachOrderRequestCallback = new CoachOrderRequestCallback(new dzn(i), this);
        this.dialog = aav.a(busListRequest, ((CoachOrderListPage) this.mPage).getString(R.string.coach_order_loading));
        this.dialog.show();
        OrderRequestHolder.getInstance().sendBusList(busListRequest, coachOrderRequestCallback);
        return busListRequest;
    }

    public void initAdapter(ListView listView) {
        this.mAdapter = new CoachOrderAdapter(((CoachOrderListPage) this.mPage).getContext());
        listView.setAdapter(this.mAdapter);
    }

    public void setAdapterData(List<eit> list) {
        this.mAdapter.setDataAndChangeDataSet(this.mOrderList);
        this.mAdapter.notifyDataSetChanged();
    }

    public void onListItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        dzc dzc = (dzc) this.mOrderList.get(i);
        if (dzc != null && !TextUtils.isEmpty(dzc.b)) {
            aja aja = new aja(CoachPurchaseUtil.a(dzc.b));
            aja.b = getWebConfigPresenter();
            aix aix = (aix) a.a.a(aix.class);
            if (aix != null) {
                aix.a((bid) this.mPage, aja);
            }
            LogManager.actionLogV2("P00287", "B001");
        }
    }

    @NonNull
    public ajf getWebConfigPresenter() {
        return new ajf() {
            public final boolean g() {
                return true;
            }

            public final boolean h() {
                return true;
            }

            public final b c() {
                return new b() {
                    public final boolean a() {
                        return false;
                    }

                    public final long c() {
                        return 1000;
                    }

                    public final String b() {
                        return AnonymousClass1.this.a.getContext().getString(R.string.life_order_train_feizhu);
                    }
                };
            }
        };
    }
}
