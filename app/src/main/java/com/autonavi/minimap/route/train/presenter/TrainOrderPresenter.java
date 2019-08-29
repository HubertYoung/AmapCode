package com.autonavi.minimap.route.train.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.autonavi.minimap.R;
import com.autonavi.minimap.order.OrderRequestHolder;
import com.autonavi.minimap.order.param.OrderListRequest;
import com.autonavi.minimap.route.train.adapter.TrainOrderAdapter;
import com.autonavi.minimap.route.train.page.TrainOrderListPage;
import java.util.List;

public class TrainOrderPresenter extends BaseOrderPresentertWithTitle<TrainOrderListPage> {
    private TrainOrderAdapter mAdapter;

    public TrainOrderPresenter(TrainOrderListPage trainOrderListPage) {
        super(trainOrderListPage);
    }

    public AosRequest requestOrderList(int i) {
        OrderListRequest orderListRequest = new OrderListRequest();
        orderListRequest.c = i;
        LifeRequestCallback lifeRequestCallback = new LifeRequestCallback(new eja(i), this);
        this.dialog = aav.a(orderListRequest, ((TrainOrderListPage) this.mPage).getString(R.string.life_order_train_loading));
        this.dialog.show();
        OrderRequestHolder.getInstance().sendOrderList(orderListRequest, lifeRequestCallback);
        return orderListRequest;
    }

    public void initAdapter(ListView listView) {
        this.mAdapter = new TrainOrderAdapter(((TrainOrderListPage) this.mPage).getContext());
        listView.setAdapter(this.mAdapter);
    }

    public void setAdapterData(List<eit> list) {
        this.mAdapter.setDataAndChangeDataSet(this.mOrderList);
        this.mAdapter.notifyDataSetChanged();
    }

    public void onListItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        eiu eiu = (eiu) this.mOrderList.get(i);
        if (eiu != null && !TextUtils.isEmpty(eiu.i)) {
            aja aja = new aja(ejx.a(eiu.i));
            aja.b = getWebConfigPresenter();
            aix aix = (aix) a.a.a(aix.class);
            if (aix != null) {
                aix.a((bid) this.mPage, aja);
            }
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

    public static void startThirdPartyWeb(bid bid, String str, String str2, String str3) {
        startThirdPartyWeb(bid, str, str2, str3, false, false, true);
    }

    public static void startThirdPartyWeb(bid bid, String str, String str2, String str3, boolean z, boolean z2, boolean z3) {
        innerStartThirdPartyWeb(bid, str, str2, str3, z, z2, 0, false, z3);
    }

    private static void innerStartThirdPartyWeb(bid bid, final String str, String str2, final String str3, final boolean z, boolean z2, int i, boolean z3, final boolean z4) {
        aja aja = new aja(str2);
        aja.b = new ajf() {
            public final String b() {
                return str;
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
                        return str3;
                    }
                };
            }

            public final boolean e() {
                return z;
            }

            public final boolean d() {
                return z4;
            }
        };
        if (bid != null) {
            aix aix = (aix) a.a.a(aix.class);
            if (aix != null) {
                if (z3) {
                    aix.a(bid, aja, i);
                    return;
                }
                aix.a(bid, aja);
            }
        }
    }
}
