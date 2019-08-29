package com.autonavi.minimap.life.order.base.presenter;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import com.autonavi.common.Callback.b;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.order.base.model.IOrderSearchResult;
import com.autonavi.minimap.life.order.base.page.BaseOrderPagetWithTitle;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseOrderPresentertWithTitle<Page extends BaseOrderPagetWithTitle> extends AbstractBasePresenter<Page> implements OnClickListener, OnItemClickListener, Callback<dpp>, b, d<ListView> {
    protected Handler mHandler = new Handler();
    public List<dpl> mOrderList = new ArrayList();
    public int page;
    public int total;

    public abstract void initAdapter(ListView listView);

    public abstract void onListItemClick(AdapterView<?> adapterView, View view, int i, long j);

    public abstract a requestOrderList(int i);

    public abstract void setAdapterData(List<dpl> list);

    public BaseOrderPresentertWithTitle(Page page2) {
        super(page2);
    }

    public void onPageCreated() {
        super.onPageCreated();
    }

    public void callback(dpp dpp) {
        new dpo();
        if (!dpo.a(dpp)) {
            IOrderSearchResult c = ((dpm) dpp).c();
            this.total = c.getTotalOrderSize();
            this.page = c.getPage();
            if (this.page == 1) {
                this.mOrderList = c.getTotalOrdersList();
            } else {
                this.mOrderList.addAll(c.getTotalOrdersList());
            }
            setAdapterData(this.mOrderList);
        } else {
            if (dpo.b(dpp)) {
                login();
            }
            dpo.a(dpp.c, dpp.a());
        }
        ((BaseOrderPagetWithTitle) this.mPage).g();
    }

    public void error(Throwable th, boolean z) {
        ((BaseOrderPagetWithTitle) this.mPage).g();
        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.ic_net_error_tipinfo));
    }

    public void onCancelled() {
        if (this.mPage != null) {
            ((BaseOrderPagetWithTitle) this.mPage).c();
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.login_or_bind) {
            if (isLogin()) {
                ((BaseOrderPagetWithTitle) this.mPage).b();
            } else {
                login();
            }
        } else if (view.getId() == R.id.btn_clean) {
            ((BaseOrderPagetWithTitle) this.mPage).d();
        } else {
            if (view.getId() == R.id.btn_clean_verify) {
                ((BaseOrderPagetWithTitle) this.mPage).e();
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
            ToastHelper.showLongToast(((BaseOrderPagetWithTitle) this.mPage).getString(R.string.no_more_voucher));
            ((BaseOrderPagetWithTitle) this.mPage).g();
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
                                ((BaseOrderPagetWithTitle) BaseOrderPresentertWithTitle.this.mPage).f();
                            }
                        }, 500);
                    }
                }
            });
        }
    }

    private boolean isLogin() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a();
    }
}
