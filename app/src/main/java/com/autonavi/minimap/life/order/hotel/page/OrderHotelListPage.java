package com.autonavi.minimap.life.order.hotel.page;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.common.widget.recyclerviewwrapper.FixedGridLayoutManager;
import com.autonavi.minimap.life.order.base.model.OrderRequest;
import com.autonavi.minimap.life.order.base.page.BaseOrderListTitlePage;
import com.autonavi.minimap.life.order.hotel.net.OrderHotelAosCallback;
import com.autonavi.minimap.life.order.hotel.net.OrderHotelDeleteParam;
import com.autonavi.minimap.life.order.hotel.net.OrderHotelDeleteParamNew;
import com.autonavi.minimap.life.order.hotel.presenter.OrderHotelListPresenter$3;
import com.autonavi.minimap.life.order.hotel.presenter.OrderHotelListPresenter$4;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import java.util.ArrayList;
import java.util.List;

public class OrderHotelListPage extends BaseOrderListTitlePage<dqg> implements OnItemClickListener, LocationNone, d<ListView> {
    public static final int ACTION_DELETE = 8193;
    public static final int ACTION_DELETE_NEW = 8194;
    public static final int ACTION_QUERY_BY_CATEGORY = 4097;
    public static final int ACTION_QUERY_NEW = 4098;
    public static final String CATEGORY = "category";
    public static final int CATEGORY_DAY = 2;
    public static final int CATEGORY_HOUR = 1;
    protected int mCurrentPageDay = 0;
    protected int mCurrentPageHour = 0;
    private RecyclerView mHotelTabView;
    private dpk mTabAdapter;
    private int mTotalDay = 0;
    private int mTotalHour = 0;

    public boolean isShowGoOrdingBtn() {
        return true;
    }

    public void startHistoryOrderFragment() {
    }

    /* access modifiers changed from: protected */
    public dqg createPresenter() {
        return new dqg(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        initTabView(getContentView());
        setListViewListener();
        initData();
    }

    public int getEmptyIconRes() {
        return R.drawable.order_hotel_list_empty_icon;
    }

    public int getEmptyTipStringRes() {
        return R.string.life_order_hotel_empty_tip;
    }

    private void initTabView(View view) {
        this.mHotelTabView = (RecyclerView) view.findViewById(R.id.order_base_tab);
        this.mHotelTabView.setVisibility(0);
        this.mHotelTabView.setLayoutManager(new FixedGridLayoutManager(getContext(), 2));
        this.mTabAdapter = new dpk(getContext());
        this.mTabAdapter.c = 40;
        this.mTabAdapter.b = new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                OrderHotelListPage.this.onTabClick(adapterView, view, i, j);
            }
        };
        this.mHotelTabView.setAdapter(this.mTabAdapter);
        dpk dpk = this.mTabAdapter;
        dpk.a = getTabStrings();
        if (dpk.a != null) {
            dpk.notifyDataSetChanged();
            if (this.mCurrentCategory == 2) {
                this.mTabAdapter.a(0);
            } else {
                this.mTabAdapter.a(1);
            }
        } else {
            throw new IllegalArgumentException("NearbyQuickSearchAdapter don't support null data.");
        }
    }

    /* access modifiers changed from: protected */
    public void onTabClick(AdapterView<?> adapterView, View view, int i, long j) {
        switch (i) {
            case 0:
                this.mCurrentCategory = 2;
                this.mPullRefreshListView.setAdapter(this.mAdapterNew);
                if (!this.mAdapterNew.isEmpty()) {
                    this.mPullRefreshListView.invalidate();
                    break;
                } else {
                    ((dqg) this.mPresenter).a(1, getString(R.string.life_order_hotel_list_loading));
                    break;
                }
            case 1:
                this.mCurrentCategory = 1;
                this.mPullRefreshListView.setAdapter(this.mAdapterOld);
                if (!this.mAdapterOld.isEmpty()) {
                    this.mPullRefreshListView.invalidate();
                    break;
                } else {
                    ((dqg) this.mPresenter).a(this.mCurrentCategory, 1, getString(R.string.life_order_hotel_list_loading));
                    break;
                }
        }
        cancelEidtorMode();
    }

    private List<String> getTabStrings() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R.string.life_order_hotel_day));
        arrayList.add(getString(R.string.life_order_hotel_hour));
        return arrayList;
    }

    private void setListViewListener() {
        this.mListView.setOnItemClickListener(this);
        this.mPullRefreshListView.setOnRefreshListener((d<T>) this);
    }

    private void initData() {
        if (this.mCurrentCategory == 2) {
            ((dqg) this.mPresenter).a(1, getString(R.string.life_order_hotel_list_loading));
        } else {
            ((dqg) this.mPresenter).a(this.mCurrentCategory, 1, getString(R.string.life_order_hotel_list_loading));
        }
    }

    public int getTitleTextRes() {
        return R.string.hotel_order_title;
    }

    public void startGoOrderingFragment() {
        dnl.a(getContext(), dnk.a);
    }

    public void onListUpdate(List<dpl> list, int i, int i2, int i3) {
        boolean z = false;
        if (i2 == 4098) {
            List data = this.mAdapterNew.getData();
            if (data == null || i == 1) {
                this.mCurrentPageDay = i;
                this.mAdapterNew.setDataAndChangeDataSet(list);
                this.mAdapterNew.notifyDataSetInvalidated();
                this.mTotalDay = i3;
            } else {
                if (list != null && !list.isEmpty()) {
                    this.mCurrentPageDay = i;
                    data.addAll(list);
                    this.mAdapterNew.setDataAndChangeDataSet(data);
                    this.mAdapterNew.notifyDataSetInvalidated();
                    this.mTotalDay = i3;
                }
                cancelEidtorMode();
            }
            z = true;
            cancelEidtorMode();
        } else {
            if (i == 1) {
                this.mCurrentPageHour = 1;
                this.mAdapterOld.setDataList(convertOldEntity(list));
                this.mAdapterOld.notifyDataSetChanged();
                this.mAdapterOld.notifyDataSetInvalidated();
                this.mTotalHour = i3;
            } else if (list != null && !list.isEmpty()) {
                this.mCurrentPageHour = i;
                this.mAdapterOld.addDataList(convertOldEntity(list));
                this.mAdapterOld.notifyDataSetChanged();
                this.mAdapterOld.notifyDataSetInvalidated();
                this.mTotalHour = i3;
            }
            z = true;
        }
        cancelEidtorMode();
        onListViewInvalid(z);
    }

    private ArrayList<dpl> convertOldEntity(List<dpl> list) {
        ArrayList<dpl> arrayList = new ArrayList<>();
        for (dpl next : list) {
            if (next instanceof dpz) {
                dpz dpz = (dpz) next;
                dpy dpy = new dpy();
                dpy.e = dpz.e;
                dpy.f = dpz.g;
                dpy.a = dpz.b;
                dpy.d = dpz.c;
                dpy.c = dpz.l;
                dpy.b = dpz.m;
                arrayList.add(dpy);
            }
        }
        return arrayList;
    }

    public void onNetError() {
        ToastHelper.showLongToast(getString(R.string.network_error_message));
        listViewRefreshComplete();
    }

    public void onListViewInvalid(boolean z) {
        if (!z) {
            ToastHelper.showToast(getString(R.string.no_more_voucher));
        }
        listViewRefreshComplete();
    }

    public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        onPullDownToRefresh();
    }

    private void onPullDownToRefresh() {
        if (this.mCurrentCategory == 2) {
            ((dqg) this.mPresenter).a(1, getString(R.string.life_order_hotel_list_loading));
        } else {
            ((dqg) this.mPresenter).a(this.mCurrentCategory, 1, getString(R.string.life_order_hotel_list_loading));
        }
    }

    public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        if (this.mCurrentCategory == 2) {
            int i = this.mCurrentPageDay + 1;
            if (this.mTotalDay == 0 || this.mAdapterNew.getCount() < this.mTotalDay) {
                ((dqg) this.mPresenter).a(i, getString(R.string.life_order_hotel_list_loading));
            } else {
                onListViewInvalid(false);
            }
        } else {
            int i2 = this.mCurrentPageHour + 1;
            if (this.mTotalHour == 0 || this.mAdapterOld.getCount() < this.mTotalHour) {
                ((dqg) this.mPresenter).a(this.mCurrentCategory, i2, getString(R.string.life_order_hotel_list_loading));
            } else {
                onListViewInvalid(false);
            }
        }
    }

    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.order_delete) {
            LogManager.actionLogV2("P00043", "B007");
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int i2 = i - 1;
        OrderRequest orderRequest = new OrderRequest();
        boolean z = false;
        switch (this.mCurrentCategory) {
            case 1:
                dpy dpy = (dpy) this.mAdapterOld.getItem(i2);
                orderRequest.oid = dpy.a;
                orderRequest.type = dpy.b;
                orderRequest.src_type = dpy.c;
                break;
            case 2:
                dpl dpl = (dpl) this.mAdapterNew.getItem(i2);
                if (dpl instanceof dpz) {
                    dpz dpz = (dpz) dpl;
                    orderRequest.oid = dpz.b;
                    orderRequest.type = dpz.m;
                    orderRequest.src_type = dpz.l;
                }
                z = true;
                break;
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject(OrderRequest.INTENT_KEY, orderRequest);
        pageBundle.putBoolean("IsNew", z);
        startPage(OrderHotelDetailPage.class, pageBundle);
        LogManager.actionLogV2("P00043", "B012");
    }

    public void deleteOrderByOids(String str) {
        if (this.mCurrentCategory == 2) {
            dqg dqg = (dqg) this.mPresenter;
            String string = getString(R.string.life_order_list_deling);
            OrderHotelDeleteParamNew orderHotelDeleteParamNew = new OrderHotelDeleteParamNew();
            orderHotelDeleteParamNew.order_ids = str;
            AosGetRequest a = aax.a(orderHotelDeleteParamNew);
            CompatDialog a2 = aav.a(a, string);
            OrderHotelListPresenter$4 orderHotelListPresenter$4 = new OrderHotelListPresenter$4(dqg, new OrderHotelAosCallback(dqg), a2);
            a2.show();
            in.a().a((AosRequest) a, (AosResponseCallback<T>) orderHotelListPresenter$4);
            return;
        }
        dqg dqg2 = (dqg) this.mPresenter;
        String string2 = getString(R.string.life_order_list_deling);
        OrderHotelDeleteParam orderHotelDeleteParam = new OrderHotelDeleteParam();
        orderHotelDeleteParam.oids = str;
        AosGetRequest a3 = aax.a(orderHotelDeleteParam);
        CompatDialog a4 = aav.a(a3, string2);
        OrderHotelListPresenter$3 orderHotelListPresenter$3 = new OrderHotelListPresenter$3(dqg2, new OrderHotelAosCallback(dqg2), a4);
        a4.show();
        in.a().a((AosRequest) a3, (AosResponseCallback<T>) orderHotelListPresenter$3);
    }

    public void onHistoryOrder(boolean z) {
        super.haveHistoryOrder(z);
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDelete(int r1, int r2) {
        /*
            r0 = this;
            switch(r2) {
                case 8193: goto L_0x0020;
                case 8194: goto L_0x0004;
                default: goto L_0x0003;
            }
        L_0x0003:
            goto L_0x002b
        L_0x0004:
            r2 = 14
            if (r1 == r2) goto L_0x001d
            switch(r1) {
                case 1: goto L_0x001a;
                case 2: goto L_0x0017;
                case 3: goto L_0x0014;
                case 4: goto L_0x0011;
                case 5: goto L_0x000e;
                default: goto L_0x000b;
            }
        L_0x000b:
            int r2 = com.autonavi.minimap.R.string.error_unknown
            goto L_0x0024
        L_0x000e:
            int r2 = com.autonavi.minimap.R.string.error_outdated_license
            goto L_0x0024
        L_0x0011:
            int r2 = com.autonavi.minimap.R.string.error_incorrect_signature
            goto L_0x0024
        L_0x0014:
            int r2 = com.autonavi.minimap.R.string.error_incorrect_parameter
            goto L_0x0024
        L_0x0017:
            int r2 = com.autonavi.minimap.R.string.error_request_failure
            goto L_0x0024
        L_0x001a:
            int r2 = com.autonavi.minimap.R.string.result_success2
            goto L_0x0024
        L_0x001d:
            int r2 = com.autonavi.minimap.R.string.error_require_login
            goto L_0x0024
        L_0x0020:
            int r2 = defpackage.dpx.a(r1)
        L_0x0024:
            java.lang.String r2 = r0.getString(r2)
            com.amap.bundle.utils.ui.ToastHelper.showToast(r2)
        L_0x002b:
            r2 = 1
            if (r1 != r2) goto L_0x0031
            r0.onPullDownToRefresh()
        L_0x0031:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.life.order.hotel.page.OrderHotelListPage.onDelete(int, int):void");
    }
}
