package com.autonavi.minimap.life.order.viewpoint.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.common.widget.recyclerviewwrapper.FixedGridLayoutManager;
import com.autonavi.minimap.life.order.base.model.IOrderSearchResult;
import com.autonavi.minimap.life.order.base.page.BaseOrderListTitlePage;
import com.autonavi.minimap.life.order.hotel.net.OrderDeleteNetWorkListener;
import com.autonavi.minimap.life.order.viewpoint.ViewPointUIController$1;
import com.autonavi.minimap.life.order.viewpoint.page.ViewPointOrderDetailPage;
import com.autonavi.minimap.order.OrderRequestHolder;
import com.autonavi.minimap.order.param.ScenicDeleteRequest;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import java.util.List;

public class ViewPointListFragmentNew extends BaseOrderListTitlePage<dqp> implements OnItemClickListener, d<ListView>, dpq, dqj {
    private static final int PAGE_SIZE = 10;
    private dqi mController;
    private dqo mPresenter;
    protected int page;
    protected int total;

    private boolean isShowTab() {
        return false;
    }

    public boolean isShowGoOrdingBtn() {
        return false;
    }

    public void onOrderDetailNetDataFinished(dpp dpp) {
    }

    public void onOrderListByPhoneNetDataFinished(dpp dpp) {
    }

    public void onOrderListNetDataFinished(dpp dpp) {
    }

    public void startGoOrderingFragment() {
    }

    /* access modifiers changed from: protected */
    public dqp createPresenter() {
        return new dqp(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        initTab(getContentView());
        this.mController = new dqi(getContext(), this);
        this.mPresenter = new dqq(getContext());
        setListViewListener();
        initData();
    }

    public int getEmptyIconRes() {
        return R.drawable.order_viewpoint_list_empty_icon;
    }

    public int getEmptyTipStringRes() {
        return R.string.life_order_viewpoint_empty_tip;
    }

    private void initData() {
        this.mController.b(1, getString(R.string.life_order_viewpoint_list_loading));
        this.mPresenter.a(this);
    }

    private void setListViewListener() {
        this.mListView.setOnItemClickListener(this);
        this.mPullRefreshListView.setOnRefreshListener((d<T>) this);
    }

    private void initTab(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.order_base_tab);
        recyclerView.setLayoutManager(new FixedGridLayoutManager(getContext(), 2));
        if (!isShowTab()) {
            recyclerView.setVisibility(8);
        }
    }

    public void onOrderListNetDataFinishedNew(dpp dpp) {
        new dpo();
        if (!dpo.a(dpp)) {
            IOrderSearchResult c = ((dpm) dpp).c();
            this.total = c.getTotalOrderSize();
            this.page = c.getPage();
            if (this.page == 1) {
                this.mAdapterNew.setDataAndChangeDataSet(c.getTotalOrdersList());
            } else {
                List data = this.mAdapterNew.getData();
                if (data != null) {
                    data.addAll(c.getTotalOrdersList());
                } else {
                    data = c.getTotalOrdersList();
                }
                this.mAdapterNew.setDataAndChangeDataSet(data);
            }
            this.mAdapterNew.notifyDataSetChanged();
            cancelEidtorMode();
            listViewRefreshComplete();
            return;
        }
        dpo.a(dpp.c, dpp.a());
        listViewRefreshComplete();
    }

    public void onDeleteFinished(dqe dqe) {
        if (dqe != null) {
            ToastHelper.showToast(getString(dpx.a(dqe.c)));
            if (dqe.c == 1) {
                this.mController.b(1, getString(R.string.life_order_viewpoint_list_loading));
            }
        }
    }

    public int getTitleTextRes() {
        return R.string.viewpoint_order_title;
    }

    public void startHistoryOrderFragment() {
        startPage(ViewPointTabPage.class, (PageBundle) null);
    }

    public void deleteOrderByOids(String str) {
        dqi dqi = this.mController;
        String string = getString(R.string.life_order_list_deling);
        OrderDeleteNetWorkListener orderDeleteNetWorkListener = new OrderDeleteNetWorkListener(dqi.b);
        ScenicDeleteRequest scenicDeleteRequest = new ScenicDeleteRequest();
        scenicDeleteRequest.b = str;
        CompatDialog a = !TextUtils.isEmpty(string) ? aav.a(scenicDeleteRequest, string) : null;
        if (a != null) {
            a.show();
        }
        OrderRequestHolder.getInstance().sendScenicDelete(scenicDeleteRequest, new ViewPointUIController$1(dqi, orderDeleteNetWorkListener, a));
    }

    public void haveHistory(boolean z) {
        super.haveHistoryOrder(z);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        dpl dpl = (dpl) this.mAdapterNew.getItem(i - 1);
        new blh();
        if (dpl != null && !TextUtils.isEmpty(dpl.m())) {
            blh.a((String) "scenic_order_id", dpl.m());
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("INTENT_KEY_H5_TEMPLATE_PATH", "exScenicOrderDetail.html");
        startPage(ViewPointOrderDetailPage.class, pageBundle);
    }

    public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        this.mController.b(1, getString(R.string.life_order_viewpoint_list_loading));
    }

    public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        if (this.page == 0 || this.total == 0 || this.total <= this.page * 10) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.no_more_voucher));
            listViewRefreshComplete();
            return;
        }
        this.mController.b(this.page + 1, getString(R.string.life_order_viewpoint_list_loading));
    }

    public void onError() {
        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.ic_net_error_tipinfo));
        listViewRefreshComplete();
    }
}
