package com.autonavi.minimap.route.train.presenter;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.amap.bundle.utils.ui.CompatDialog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.b;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.model.IOrderSearchResult;
import com.autonavi.minimap.route.train.page.BaseOrderPagetWithTitle;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseOrderPresentertWithTitle<Page extends BaseOrderPagetWithTitle> extends eaf<Page> implements OnClickListener, OnItemClickListener, Callback<ejc>, b, d<ListView> {
    public static final int SHOW_NUM_ONCE = 20;
    protected CompatDialog dialog;
    protected Handler mHandler = new Handler();
    public List<eit> mOrderList = new ArrayList();
    public int page;
    public int total;

    public abstract void initAdapter(ListView listView);

    public abstract void onListItemClick(AdapterView<?> adapterView, View view, int i, long j);

    public abstract AosRequest requestOrderList(int i);

    public abstract void setAdapterData(List<eit> list);

    public BaseOrderPresentertWithTitle(Page page2) {
        super(page2);
    }

    public void onPageCreated() {
        super.onPageCreated();
    }

    public void callback(ejc ejc) {
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
        new eiz();
        boolean z = false;
        if (!(ejc.errorCode != 1)) {
            IOrderSearchResult c = ((eiy) ejc).c();
            this.total = c.getTotalOrderSize();
            this.page = c.getPage();
            if (this.page == 1) {
                this.mOrderList = c.getTotalOrdersList();
            } else {
                this.mOrderList.addAll(c.getTotalOrdersList());
            }
            setAdapterData(this.mOrderList);
        } else {
            if (ejc.errorCode == 14) {
                IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    iAccountService.d();
                    z = true;
                }
            }
            if (z) {
                login();
            }
            int i = ejc.errorCode;
            String errorDesc = ejc.getErrorDesc(ejc.errorCode);
            if (i == -1) {
                ToastHelper.showLongToast(AbstractAOSParser.ERROR_NETWORK);
            } else if (14 == i || 92 == i) {
                IAccountService iAccountService2 = (IAccountService) a.a.a(IAccountService.class);
                if (iAccountService2 != null) {
                    iAccountService2.d();
                }
            }
            if (!TextUtils.isEmpty(errorDesc)) {
                ToastHelper.showLongToast(errorDesc);
            }
        }
        ((BaseOrderPagetWithTitle) this.mPage).h();
    }

    public void error(Throwable th, boolean z) {
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
        ((BaseOrderPagetWithTitle) this.mPage).h();
        ToastHelper.showLongToast(eay.a(R.string.ic_net_error_tipinfo));
    }

    public void onCancelled() {
        if (this.mPage != null) {
            ((BaseOrderPagetWithTitle) this.mPage).d();
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.login_or_bind) {
            if (isLogin()) {
                ((BaseOrderPagetWithTitle) this.mPage).c();
            } else {
                login();
            }
        } else if (view.getId() == R.id.btn_clean) {
            ((BaseOrderPagetWithTitle) this.mPage).e();
        } else {
            if (view.getId() == R.id.btn_clean_verify) {
                ((BaseOrderPagetWithTitle) this.mPage).f();
            }
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        onListItemClick(adapterView, view, i - 1, j);
    }

    public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        requestOrderList(1);
    }

    public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        if (this.page == 0 || this.total == 0 || this.total <= this.page * 20) {
            ((BaseOrderPagetWithTitle) this.mPage).h();
            return;
        }
        this.page++;
        requestOrderList(this.page);
    }

    public void login() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a(((BaseOrderPagetWithTitle) this.mPage).getPageContext(), (anq) new anq() {
                public final void loginOrBindCancel() {
                }

                public final void onComplete(boolean z) {
                    if (z) {
                        BaseOrderPresentertWithTitle.this.mHandler.postDelayed(new Runnable() {
                            public final void run() {
                                ((BaseOrderPagetWithTitle) BaseOrderPresentertWithTitle.this.mPage).g();
                            }
                        }, 500);
                    }
                }
            });
        }
    }

    private boolean isLogin() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        return iAccountService != null && iAccountService.a();
    }
}
