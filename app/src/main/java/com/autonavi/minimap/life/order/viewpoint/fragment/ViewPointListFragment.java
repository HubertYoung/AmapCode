package com.autonavi.minimap.life.order.viewpoint.fragment;

import android.view.View;
import android.widget.AdapterView;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.order.base.fragment.BaseOrderFragment;
import com.autonavi.minimap.life.order.base.model.IOrderSearchResult;
import com.autonavi.minimap.life.order.viewpoint.page.ViewPointOrderDetailPage;

public class ViewPointListFragment extends BaseOrderFragment {
    private dqi mController;

    public void onDelete(String str) {
    }

    public void onDeleteFinished(dqe dqe) {
    }

    public void onOrderDetailNetDataFinished(dpp dpp) {
    }

    public void onOrderListByPhoneNetDataFinished(dpp dpp) {
    }

    public void onOrderListNetDataFinishedNew(dpp dpp) {
    }

    public void onSubmitOnClick() {
    }

    public void verifyLog() {
    }

    public ViewPointListFragment(bid bid) {
        super(bid);
        this.showPhoneVerify = Boolean.FALSE;
        this.mController = new dqi(getContext(), this);
    }

    public void onPullDownToRefresh() {
        this.mNetCancel = this.mController.a();
    }

    public void onPullUpToRefresh() {
        this.mNetCancel = this.mController.a(this.page + 1, getString(R.string.life_order_viewpoint_list_loading));
    }

    public void onOrderListNetDataFinished(dpp dpp) {
        new dpo();
        if (!dpo.a(dpp)) {
            IOrderSearchResult c = ((dpm) dpp).c();
            this.total = c.getTotalOrderSize();
            this.page = c.getPage();
            if (this.page == 1) {
                this.mTotalOrdersList = c.getTotalOrdersList();
            } else {
                this.mTotalOrdersList.addAll(c.getTotalOrdersList());
            }
            invalidateUI(true);
            return;
        }
        if (dpo.b(dpp)) {
            initLogin();
        }
        dpo.a(dpp.c, dpp.a());
        invalidateUI(false);
    }

    public void onListItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("IOrderListEntityKey", this.mTotalOrdersList.get(i));
        pageBundle.putString("INTENT_KEY_H5_TEMPLATE_PATH", "life/viewpoint/exViewpointDetail.html");
        this.mPageContext.startPage(ViewPointOrderDetailPage.class, pageBundle);
    }

    public void afterLogin() {
        this.mNetCancel = this.mController.a();
        initLogin();
    }

    public void initDate() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null && iAccountService.a()) {
            this.mNetCancel = this.mController.a();
        }
    }

    public void onError() {
        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.ic_net_error_tipinfo));
        invalidateUI(false);
    }
}
