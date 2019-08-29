package com.autonavi.minimap.life.order.viewpoint.fragment;

import android.view.View;
import android.widget.AdapterView;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.order.base.fragment.BaseOrderFragment;
import com.autonavi.minimap.life.order.base.model.IOrderSearchResult;
import com.autonavi.minimap.life.order.base.model.OrderRequest;
import com.autonavi.minimap.life.order.viewpoint.page.ViewPointListByPhonePage;
import com.autonavi.minimap.life.order.viewpoint.page.ViewPointOrderDetailPage;

public class ViewPointPhoneFragment extends BaseOrderFragment {
    private dqi controller;
    private OrderRequest or;

    public void afterLogin() {
    }

    public void initDate() {
    }

    public void onDelete(String str) {
    }

    public void onDeleteFinished(dqe dqe) {
    }

    public void onOrderDetailNetDataFinished(dpp dpp) {
    }

    public void onOrderListNetDataFinished(dpp dpp) {
    }

    public void onOrderListNetDataFinishedNew(dpp dpp) {
    }

    public void onPullDownToRefresh() {
    }

    public void onPullUpToRefresh() {
    }

    public void verifyLog() {
    }

    public ViewPointPhoneFragment(bid bid) {
        super(bid);
        this.showPhoneVerify = Boolean.TRUE;
        this.controller = new dqi(getContext(), this);
    }

    public void onSubmitOnClick() {
        this.or = new OrderRequest();
        this.or.phone = this.edtPhone.getText().toString();
        this.or.code = this.edtVerify.getText().toString();
        this.mNetCancel = this.controller.a(this.or);
    }

    public void onOrderListByPhoneNetDataFinished(dpp dpp) {
        new dpo();
        if (!dpo.a(dpp)) {
            IOrderSearchResult c = ((dpm) dpp).c();
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("resultData", c);
            pageBundle.putObject("phoneData", this.or);
            this.mPageContext.startPage(ViewPointListByPhonePage.class, pageBundle);
            return;
        }
        dpo.a(dpp.c, dpp.a());
    }

    public void onListItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("IOrderListEntityKey", this.mTotalOrdersList.get(i));
        pageBundle.putString("INTENT_KEY_H5_TEMPLATE_PATH", "life/viewpoint/exViewpointDetail.html");
        this.mPageContext.startPage(ViewPointOrderDetailPage.class, pageBundle);
    }

    public void onError() {
        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.ic_net_error_tipinfo));
    }
}
