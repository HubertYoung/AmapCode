package com.autonavi.map.wallet.Page;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.wallet.Presenter.WalletBillPresenter;
import com.autonavi.minimap.R;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.ui.TitleBar;

public class WalletBillPage extends AbstractBasePage<WalletBillPresenter> {
    public ListView a;
    public PullToRefreshListView b;
    public View c;
    public View d;
    public View e;
    public TextView f;
    public TextView g;
    public TextView h;
    public ImageView i;
    public ImageView j;
    public LinearLayout k;
    public LinearLayout l;
    public RelativeLayout m;
    public View n;
    private TitleBar o;
    private RelativeLayout p;
    private RelativeLayout q;
    private RelativeLayout r;
    private TextView s;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.wallet_bill_layout);
        View contentView = getContentView();
        this.o = (TitleBar) contentView.findViewById(R.id.title);
        this.o.setTitle(getString(R.string.wallet_detail));
        this.o.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                ((WalletBillPresenter) WalletBillPage.this.mPresenter).a();
            }
        });
        this.b = (PullToRefreshListView) contentView.findViewById(R.id.bill_list);
        this.b.setMode(Mode.BOTH);
        this.b.setOnRefreshListener(((WalletBillPresenter) this.mPresenter).c);
        this.b.setHeaderTextTextColor(getResources().getColor(R.color.black));
        this.m = (RelativeLayout) contentView.findViewById(R.id.empty_rl);
        this.a = (ListView) this.b.getRefreshableView();
        this.a.setSelector(new ColorDrawable(getResources().getColor(R.color.transparent)));
        this.l = (LinearLayout) contentView.findViewById(R.id.status_selection_table_ll);
        this.k = (LinearLayout) contentView.findViewById(R.id.source_selection_table_ll);
        this.c = contentView.findViewById(R.id.wallet_bill_total_bot_line);
        this.d = contentView.findViewById(R.id.wallet_bill_status_bot_line);
        this.e = contentView.findViewById(R.id.wallet_bill_source_bot_line);
        this.p = (RelativeLayout) contentView.findViewById(R.id.wallet_bill_total_rl);
        this.p.setOnClickListener(((WalletBillPresenter) this.mPresenter).e);
        this.q = (RelativeLayout) contentView.findViewById(R.id.wallet_bill_status_rl);
        this.q.setOnClickListener(((WalletBillPresenter) this.mPresenter).f);
        this.r = (RelativeLayout) contentView.findViewById(R.id.wallet_bill_source_rl);
        this.r.setOnClickListener(((WalletBillPresenter) this.mPresenter).g);
        this.s = (TextView) contentView.findViewById(R.id.tip_info_tv);
        this.f = (TextView) contentView.findViewById(R.id.wallet_bill_total_tv);
        this.g = (TextView) contentView.findViewById(R.id.wallet_bill_status_tv);
        this.h = (TextView) contentView.findViewById(R.id.wallet_bill_source_tv);
        this.i = (ImageView) contentView.findViewById(R.id.wallet_bill_status_arrow);
        this.j = (ImageView) contentView.findViewById(R.id.wallet_bill_source_arrow);
        this.n = contentView.findViewById(R.id.transparent_view);
        this.n.setOnClickListener(((WalletBillPresenter) this.mPresenter).d);
    }

    public final void a(String str) {
        if (TextUtils.isEmpty(str)) {
            a();
            return;
        }
        this.s.setText(str);
        this.s.setVisibility(0);
    }

    public final void a() {
        this.s.setVisibility(8);
        this.s.setText("");
    }

    public final void b() {
        this.j.setImageDrawable(getResources().getDrawable(R.drawable.wallet_bill_nav_down));
        this.k.setVisibility(8);
        this.n.setVisibility(8);
        this.b.setMode(Mode.BOTH);
    }

    public final void c() {
        this.i.setImageDrawable(getResources().getDrawable(R.drawable.wallet_bill_nav_down));
        this.l.setVisibility(8);
        this.n.setVisibility(8);
        this.b.setMode(Mode.BOTH);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new WalletBillPresenter(this);
    }
}
