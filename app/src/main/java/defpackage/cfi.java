package defpackage;

import android.view.KeyEvent;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.wallet.Page.WalletWithDrawalPage;

/* renamed from: cfi reason: default package */
/* compiled from: WalletWithDrawalPresenter */
public final class cfi extends AbstractBasePresenter<WalletWithDrawalPage> implements a {
    public cfi(WalletWithDrawalPage walletWithDrawalPage) {
        super(walletWithDrawalPage);
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (((WalletWithDrawalPage) this.mPage).hasViewLayer()) {
            return true;
        }
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        ((WalletWithDrawalPage) this.mPage).setResult(ResultType.OK, new PageBundle());
        ((WalletWithDrawalPage) this.mPage).finish();
        return true;
    }

    public final int a(PageBundle pageBundle, int i) {
        switch (i) {
            case 0:
                ((WalletWithDrawalPage) this.mPage).setResult(ResultType.OK, pageBundle);
                ((WalletWithDrawalPage) this.mPage).finish();
                break;
            case 1:
                ((WalletWithDrawalPage) this.mPage).a(pageBundle);
                break;
            case 2:
                String string = pageBundle.getString("withdraw_taobao_token");
                PageBundle pageBundle2 = new PageBundle();
                pageBundle2.putString("withdraw_taobao_token", string);
                ((WalletWithDrawalPage) this.mPage).setResult(ResultType.OK, pageBundle2);
                ((WalletWithDrawalPage) this.mPage).finish();
                break;
        }
        return 0;
    }

    public static /* synthetic */ void a(cfi cfi, boolean z) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putBoolean("withdraw_account_not_login", z);
        ((WalletWithDrawalPage) cfi.mPage).setResult(ResultType.OK, pageBundle);
        ((WalletWithDrawalPage) cfi.mPage).finish();
    }
}
