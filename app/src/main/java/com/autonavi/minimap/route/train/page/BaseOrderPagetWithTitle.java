package com.autonavi.minimap.route.train.page;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.presenter.BaseOrderPresentertWithTitle;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.ui.TitleBar;

public abstract class BaseOrderPagetWithTitle<Presenter extends BaseOrderPresentertWithTitle> extends AbstractBasePage<Presenter> implements LocationNone {
    protected TitleBar a;
    protected PullToRefreshListView b;
    protected ListView c;
    protected View d;
    protected Handler e;
    protected Button f;
    protected Button g;
    protected EditText h;
    protected EditText i;
    protected View j;
    protected TextView k;
    private OnClickListener l = new OnClickListener() {
        public final void onClick(View view) {
            BaseOrderPagetWithTitle.this.finish();
        }
    };

    public class a implements Runnable {
        public a() {
        }

        public final void run() {
            BaseOrderPagetWithTitle.this.b.onRefreshComplete();
        }
    }

    /* access modifiers changed from: protected */
    public abstract int a();

    public void onCreate(Context context) {
        super.onCreate(context);
        this.e = new Handler();
        setContentView(R.layout.order_base_layout_with_title);
        View contentView = getContentView();
        if (contentView != null) {
            this.a = (TitleBar) contentView.findViewById(R.id.title);
            this.a.setTitle(getString(a()));
            this.a.setOnBackClickListener(this.l);
        }
        View contentView2 = getContentView();
        if (contentView2 != null) {
            this.j = contentView2.findViewById(R.id.login_layout);
            this.k = (TextView) contentView2.findViewById(R.id.login_or_bind);
            this.k.setOnClickListener((OnClickListener) this.mPresenter);
            this.b = (PullToRefreshListView) contentView2.findViewById(R.id.order_list);
            this.b.setMode(Mode.BOTH);
            this.c = (ListView) this.b.getRefreshableView();
            this.c.setSelector(R.drawable.transparent);
            contentView2.findViewById(R.id.order_empty_layout_new).setVisibility(8);
            this.d = contentView2.findViewById(R.id.empty);
            this.c.setEmptyView(this.d);
            this.c.setOnItemClickListener((OnItemClickListener) this.mPresenter);
            this.b.setOnRefreshListener((d) this.mPresenter);
            ((BaseOrderPresentertWithTitle) this.mPresenter).initAdapter(this.c);
        }
        b();
        k();
    }

    /* access modifiers changed from: protected */
    public final void b() {
        TextView textView;
        View contentView = getContentView();
        TextView textView2 = null;
        if (contentView != null) {
            textView2 = (TextView) contentView.findViewById(R.id.login_or_bind_tip);
            textView = (TextView) contentView.findViewById(R.id.login_or_bind);
        } else {
            textView = null;
        }
        if (this.j == null || !i() || (!a(AccountType.Taobao) && TextUtils.isEmpty(j()))) {
            if (!i()) {
                if (textView2 != null) {
                    textView2.setText(R.string.login_alert);
                }
                if (textView != null) {
                    textView.setText(R.string.weibo_register);
                }
                if (this.j != null) {
                    this.j.setVisibility(0);
                    return;
                }
            } else {
                if (textView2 != null) {
                    textView2.setText(R.string.order_bind_phone_or_taobao_tip);
                }
                if (textView != null) {
                    textView.setText(R.string.route_bind_phone);
                }
                if (this.j != null) {
                    this.j.setVisibility(0);
                }
            }
            return;
        }
        this.j.setVisibility(8);
    }

    private void k() {
        if (i() && (a(AccountType.Taobao) || !TextUtils.isEmpty(j()))) {
            ((BaseOrderPresentertWithTitle) this.mPresenter).requestOrderList(1);
        } else if (!i()) {
            ToastHelper.showLongToast(getString(R.string.order_login_tip));
        } else {
            c();
        }
    }

    public final void d() {
        if (this.b != null) {
            this.b.onRefreshComplete();
        }
    }

    public final void e() {
        this.h.setText("");
        this.f.setVisibility(8);
    }

    public final void f() {
        this.i.setText("");
        this.g.setVisibility(8);
    }

    public final void g() {
        b();
        k();
    }

    public final void h() {
        if (this.b != null && this.b.isRefreshing()) {
            this.e.post(new a());
        }
    }

    private static boolean i() {
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

    private static String j() {
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

    public final void c() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a(getPageContext(), getString(R.string.order_bind_phone_tip), (anq) new anq() {
                public final void loginOrBindCancel() {
                }

                public final void onComplete(boolean z) {
                    if (z) {
                        ((BaseOrderPresentertWithTitle) BaseOrderPagetWithTitle.this.mPresenter).requestOrderList(1);
                        BaseOrderPagetWithTitle.this.b();
                    }
                }
            });
            ToastHelper.showLongToast(getString(R.string.order_bind_phone_tip));
        }
    }
}
