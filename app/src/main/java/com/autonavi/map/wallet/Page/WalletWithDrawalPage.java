package com.autonavi.map.wallet.Page;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.amap.bundle.drivecommon.inter.NetConstant;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.wallet.Presenter.WalletWithDrawalPresenter$1;
import com.autonavi.map.wallet.WalletRequestCallback;
import com.autonavi.minimap.R;
import com.autonavi.minimap.wallet.WalletRequestHolder;
import com.autonavi.minimap.wallet.param.CashoutRequest;

public class WalletWithDrawalPage extends AbstractBasePage<cfi> implements OnClickListener {
    public cfl a;
    private String b;
    private TextView c;
    private TextView d;
    private Button e;
    private ImageButton f;
    private ImageButton g;
    private TextView h;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.wallet_alert_dlg);
        View contentView = getContentView();
        if (contentView != null) {
            contentView.setPadding(contentView.getPaddingLeft(), euk.a(getContext()) + contentView.getPaddingTop(), contentView.getPaddingRight(), contentView.getPaddingBottom());
            this.g = (ImageButton) contentView.findViewById(R.id.title_btn_right);
            this.g.setVisibility(8);
            this.d = (TextView) contentView.findViewById(R.id.title_text_name);
            this.d.setText(R.string.wallet_withdraw);
            this.f = (ImageButton) contentView.findViewById(R.id.title_btn_left);
            this.f.setVisibility(8);
            this.f.setOnClickListener(this);
            this.c = (TextView) contentView.findViewById(R.id.resultB);
            this.e = (Button) contentView.findViewById(R.id.submit);
            this.e.setOnClickListener(this);
            this.h = (TextView) contentView.findViewById(R.id.wallet_withdraw_other);
            this.h.setOnClickListener(this);
            this.a = new cfl(getPageContext());
        }
        a(getArguments());
    }

    public final void a(PageBundle pageBundle) {
        if (pageBundle != null) {
            String string = pageBundle.getString("name");
            String string2 = pageBundle.getString(NetConstant.KEY_MONEY_ACCOUNT);
            boolean z = pageBundle.getBoolean("sso");
            this.b = "";
            this.f.setVisibility(0);
            if (!z) {
                this.c.setText(Html.fromHtml(String.format(getString(R.string.withdraw_ask), new Object[]{string, string2})));
                this.e.setText(R.string.Ok);
                return;
            }
            this.c.setText(Html.fromHtml(String.format(getString(R.string.withdraw_makesure), new Object[]{string, string2})));
            this.e.setText(R.string.Ok);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.title_btn_left) {
            setResult(ResultType.OK, new PageBundle());
            finish();
        } else if (id == R.id.submit) {
            String str = this.b;
            cfv cfv = new cfv();
            WalletWithDrawalPresenter$1 walletWithDrawalPresenter$1 = new WalletWithDrawalPresenter$1((cfi) this.mPresenter);
            CashoutRequest cashoutRequest = new CashoutRequest();
            if (!TextUtils.isEmpty(str)) {
                cashoutRequest.b = "2";
                cashoutRequest.c = str;
            }
            WalletRequestCallback walletRequestCallback = new WalletRequestCallback(cfv, walletWithDrawalPresenter$1);
            CompatDialog a2 = aav.a(cashoutRequest, AMapPageUtil.getAppContext().getString(R.string.wallet_withdraw_request));
            walletRequestCallback.a = a2;
            a2.show();
            WalletRequestHolder.getInstance().sendCashout(cashoutRequest, walletRequestCallback);
        } else {
            if (id == R.id.wallet_withdraw_other) {
                this.a.a((a) this.mPresenter);
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cfi(this);
    }
}
