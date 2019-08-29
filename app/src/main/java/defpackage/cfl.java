package defpackage;

import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.wallet.WalletUiController$4;
import com.autonavi.map.wallet.WalletUiController$5;
import com.autonavi.map.wallet.WalletUiController$6;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AlertView;

/* renamed from: cfl reason: default package */
/* compiled from: WalletUiController */
public final class cfl {
    public bid a;

    /* renamed from: cfl$a */
    /* compiled from: WalletUiController */
    public interface a {
        int a(PageBundle pageBundle, int i);
    }

    public cfl(bid bid) {
        this.a = bid;
    }

    public final void a(String str, a aVar, boolean z) {
        cfk.a(new cft(), new WalletUiController$5(this, aVar, z, str), str);
    }

    public static void a() {
        cfk.a(new cfu(), new WalletUiController$6());
    }

    public static void a(final bid bid, CharSequence charSequence, CharSequence charSequence2, final defpackage.ern.a aVar, CharSequence charSequence3, CharSequence charSequence4, boolean z, final defpackage.ern.a aVar2) {
        defpackage.ern.a aVar3;
        if (bid != null) {
            com.autonavi.widget.ui.AlertView.a aVar4 = new com.autonavi.widget.ui.AlertView.a(bid.getActivity());
            aVar4.a(charSequence).b(charSequence2);
            AnonymousClass6 r3 = new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    bid.dismissViewLayer(alertView);
                    aVar.onClick(alertView, i);
                }
            };
            if (charSequence3 == null || charSequence3.length() <= 0) {
                aVar4.a((CharSequence) AMapAppGlobal.getApplication().getString(R.string.confirm), (defpackage.ern.a) r3);
            } else {
                aVar4.a(charSequence3, (defpackage.ern.a) r3);
            }
            if (z) {
                if (aVar2 != null) {
                    aVar3 = new defpackage.ern.a() {
                        public final void onClick(AlertView alertView, int i) {
                            bid.dismissViewLayer(alertView);
                            aVar2.onClick(alertView, i);
                        }
                    };
                } else {
                    aVar3 = new defpackage.ern.a() {
                        public final void onClick(AlertView alertView, int i) {
                            bid.dismissViewLayer(alertView);
                        }
                    };
                }
                if (charSequence4 == null || charSequence4.length() <= 0) {
                    aVar4.b((CharSequence) AMapAppGlobal.getApplication().getString(R.string.cancel), aVar3);
                } else {
                    aVar4.b(charSequence4, aVar3);
                }
            }
            aVar4.b = new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                }
            };
            aVar4.c = new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                }
            };
            aVar4.a(false);
            AlertView a2 = aVar4.a();
            bid.showViewLayer(a2);
            a2.startAnimation();
        }
    }

    public final void a(AccountType accountType, final a aVar) {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService != null) {
            AnonymousClass4 r1 = new anq() {
                public final void onComplete(boolean z) {
                    cfl.a(aVar, Boolean.valueOf(z));
                }

                public final void loginOrBindCancel() {
                    cfl.a(aVar, Boolean.FALSE);
                }
            };
            if (accountType == null || accountType == AccountType.Mobile) {
                iAccountService.a(this.a, (anq) r1);
            } else {
                iAccountService.a(accountType, (anq) r1);
            }
        }
    }

    public final void a(a aVar) {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.c().a(new WalletUiController$4(this, aVar));
        }
    }

    static /* synthetic */ void a(a aVar, Boolean bool) {
        if (aVar != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putBoolean("withdraw_account_not_login", bool.booleanValue());
            aVar.a(pageBundle, 0);
        }
    }
}
