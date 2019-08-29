package defpackage;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.wallet.Page.WalletBillPage;
import com.autonavi.map.wallet.Page.WalletMainPage;
import com.autonavi.map.wallet.Page.WalletWithDrawalPage;
import com.autonavi.map.wallet.Presenter.WalletMainPresenter$1;
import com.autonavi.map.wallet.model.WalletBillStatus;
import com.autonavi.minimap.R;

/* renamed from: cfh reason: default package */
/* compiled from: WalletMainPresenter */
public final class cfh extends AbstractBasePresenter<WalletMainPage> implements a {
    public cfh(WalletMainPage walletMainPage) {
        super(walletMainPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
    }

    public static void a(View view, String str, boolean z) {
        if (z) {
            if (TextUtils.isEmpty(str) || str.equals("0.00")) {
                view.setVisibility(8);
                return;
            }
            view.setVisibility(0);
        }
        if (TextUtils.isEmpty(str)) {
            str = "0.00";
        }
        ((TextView) view.findViewById(R.id.cash)).setText(str);
    }

    private void a() {
        cfk.a(new cfu(), new WalletMainPresenter$1(this));
    }

    public final void a(WalletBillStatus walletBillStatus) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("status", walletBillStatus);
        ((WalletMainPage) this.mPage).startPageForResult(WalletBillPage.class, pageBundle, 1);
    }

    public final int a(PageBundle pageBundle, int i) {
        switch (i) {
            case 0:
                if (pageBundle.getBoolean("withdraw_account_not_login")) {
                    a();
                    break;
                }
                break;
            case 1:
                ((WalletMainPage) this.mPage).startPageForResult(WalletWithDrawalPage.class, pageBundle, 1);
                break;
        }
        return 0;
    }

    public final void onStart() {
        super.onStart();
        if (((WalletMainPage) this.mPage).b) {
            ((WalletMainPage) this.mPage).b = false;
        } else {
            a();
        }
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        if (((WalletMainPage) this.mPage).hasViewLayer()) {
            return true;
        }
        ((WalletMainPage) this.mPage).finish();
        return true;
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (pageBundle != null) {
            boolean z = pageBundle.getBoolean("withdraw_account_done", false);
            boolean z2 = pageBundle.getBoolean("withdraw_account_not_login");
            String string = pageBundle.getString("withdraw_taobao_token");
            if (!TextUtils.isEmpty(string)) {
                ((WalletMainPage) this.mPage).a.a(string, this, true);
            } else if (z) {
                a();
            } else if (z2) {
                a();
            } else {
                ((WalletMainPage) this.mPage).finish();
            }
        }
    }
}
