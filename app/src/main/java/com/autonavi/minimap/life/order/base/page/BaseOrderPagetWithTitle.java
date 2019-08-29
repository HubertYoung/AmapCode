package com.autonavi.minimap.life.order.base.page;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.order.base.presenter.BaseOrderPresentertWithTitle;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;

public abstract class BaseOrderPagetWithTitle<Presenter extends BaseOrderPresentertWithTitle> extends AbstractBasePage<Presenter> implements LocationNone {
    protected PullToRefreshListView a;
    protected Handler b;
    protected Button c;
    protected Button d;
    protected EditText e;
    protected EditText f;
    protected View g;
    private OnClickListener h = new OnClickListener() {
        public final void onClick(View view) {
            BaseOrderPagetWithTitle.this.finish();
        }
    };

    public class a implements Runnable {
        public a() {
        }

        public final void run() {
            BaseOrderPagetWithTitle.this.a.onRefreshComplete();
        }
    }

    /* access modifiers changed from: protected */
    public final void a() {
        TextView textView;
        View contentView = getContentView();
        TextView textView2 = null;
        if (contentView != null) {
            textView2 = (TextView) contentView.findViewById(R.id.login_or_bind_tip);
            textView = (TextView) contentView.findViewById(R.id.login_or_bind);
        } else {
            textView = null;
        }
        if (this.g == null || !h() || (!a(AccountType.Taobao) && TextUtils.isEmpty(i()))) {
            if (!h()) {
                if (textView2 != null) {
                    textView2.setText(R.string.login_alert);
                }
                if (textView != null) {
                    textView.setText(R.string.weibo_register);
                }
                if (this.g != null) {
                    this.g.setVisibility(0);
                    return;
                }
            } else {
                if (textView2 != null) {
                    textView2.setText(R.string.life_order_bind_phone_or_taobao_tip);
                }
                if (textView != null) {
                    textView.setText(R.string.bind_phone);
                }
                if (this.g != null) {
                    this.g.setVisibility(0);
                }
            }
            return;
        }
        this.g.setVisibility(8);
    }

    public final void c() {
        if (this.a != null) {
            this.a.onRefreshComplete();
        }
    }

    public final void d() {
        this.e.setText("");
        this.c.setVisibility(8);
    }

    public final void e() {
        this.f.setText("");
        this.d.setVisibility(8);
    }

    public final void f() {
        a();
        if (h() && (a(AccountType.Taobao) || !TextUtils.isEmpty(i()))) {
            ((BaseOrderPresentertWithTitle) this.mPresenter).requestOrderList(1);
        } else if (!h()) {
            ToastHelper.showLongToast(getString(R.string.life_order_login_tip));
        } else {
            b();
        }
    }

    public final void g() {
        if (this.a != null && this.a.isRefreshing()) {
            this.b.post(new a());
        }
    }

    private static boolean h() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a();
    }

    private static boolean a(AccountType accountType) {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a(accountType);
    }

    private static String i() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e2 = iAccountService.e();
        if (e2 == null) {
            return "";
        }
        return e2.h;
    }

    public final void b() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a(getPageContext(), getString(R.string.life_order_bind_phone_tip), (anq) new anq() {
                public final void loginOrBindCancel() {
                }

                public final void onComplete(boolean z) {
                    if (z) {
                        ((BaseOrderPresentertWithTitle) BaseOrderPagetWithTitle.this.mPresenter).requestOrderList(1);
                        BaseOrderPagetWithTitle.this.a();
                    }
                }
            });
            ToastHelper.showLongToast(getString(R.string.life_order_bind_phone_tip));
        }
    }
}
