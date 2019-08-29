package com.autonavi.map.wallet.Presenter;

import android.text.Html;
import com.amap.bundle.network.response.exception.ServerException;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.wallet.Page.WalletWithDrawalPage;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AlertView;

public class WalletWithDrawalPresenter$1 implements Callback<cfv> {
    final /* synthetic */ cfi a;

    public WalletWithDrawalPresenter$1(cfi cfi) {
        this.a = cfi;
    }

    public void callback(cfv cfv) {
        if (cfv.errorCode == 1) {
            cfl.a(((WalletWithDrawalPage) this.a.mPage).getPageContext(), ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.wallet_withdraw_success), Html.fromHtml(((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.wallet_withdraw_success_detail)), new a() {
                public final void onClick(AlertView alertView, int i) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putBoolean("withdraw_account_done", true);
                    ((WalletWithDrawalPage) WalletWithDrawalPresenter$1.this.a.mPage).setResult(ResultType.OK, pageBundle);
                    ((WalletWithDrawalPage) WalletWithDrawalPresenter$1.this.a.mPage).finish();
                }
            }, null, null, false, null);
        }
    }

    public void error(Throwable th, boolean z) {
        if (th != null && (th instanceof ServerException)) {
            int code = ((ServerException) th).getCode();
            if (code != 0) {
                if (code == 14) {
                    cfl.a(((WalletWithDrawalPage) this.a.mPage).getPageContext(), ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.prompt_msg), ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.wallet_relogin), new a() {
                        public final void onClick(AlertView alertView, int i) {
                            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                            if (iAccountService != null) {
                                iAccountService.a(((WalletWithDrawalPage) WalletWithDrawalPresenter$1.this.a.mPage).getPageContext(), (anq) new anq() {
                                    public final void onComplete(boolean z) {
                                        cfi.a(WalletWithDrawalPresenter$1.this.a, z);
                                    }

                                    public final void loginOrBindCancel() {
                                        cfi.a(WalletWithDrawalPresenter$1.this.a, false);
                                    }
                                });
                            }
                        }
                    }, null, null, false, null);
                    return;
                } else if (code != 144) {
                    switch (code) {
                        case 2:
                        case 3:
                        case 4:
                            break;
                        case 5:
                            cfl.a(((WalletWithDrawalPage) this.a.mPage).getPageContext(), ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.authorization_expires_title), ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.authorization_expires_desc), new a() {
                                public final void onClick(AlertView alertView, int i) {
                                    ((WalletWithDrawalPage) WalletWithDrawalPresenter$1.this.a.mPage).a.a(WalletWithDrawalPresenter$1.this.a);
                                }
                            }, null, null, true, null);
                            return;
                        default:
                            switch (code) {
                                case 122:
                                    cfl.a(((WalletWithDrawalPage) this.a.mPage).getPageContext(), ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.withdraw_fail), ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.organizing_taobao_data), new a() {
                                        public final void onClick(AlertView alertView, int i) {
                                        }
                                    }, null, ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.alipay_withdraw_others), true, new a() {
                                        public final void onClick(AlertView alertView, int i) {
                                            ((WalletWithDrawalPage) WalletWithDrawalPresenter$1.this.a.mPage).a.a(WalletWithDrawalPresenter$1.this.a);
                                        }
                                    });
                                    return;
                                case 123:
                                    cfl.a(((WalletWithDrawalPage) this.a.mPage).getPageContext(), ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.withdraw_fail), ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.alipay_certification), new a() {
                                        public final void onClick(AlertView alertView, int i) {
                                        }
                                    }, null, ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.alipay_withdraw_others), true, new a() {
                                        public final void onClick(AlertView alertView, int i) {
                                            ((WalletWithDrawalPage) WalletWithDrawalPresenter$1.this.a.mPage).a.a(WalletWithDrawalPresenter$1.this.a);
                                        }
                                    });
                                    return;
                                default:
                                    cfl.a(((WalletWithDrawalPage) this.a.mPage).getPageContext(), ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.withdraw_fail), ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.pls_retry_later), new a() {
                                        public final void onClick(AlertView alertView, int i) {
                                        }
                                    }, null, null, false, null);
                                    break;
                            }
                    }
                } else {
                    cfl.a(((WalletWithDrawalPage) this.a.mPage).getPageContext(), ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.withdraw_fail), ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.withdraw_reach_limit), new a() {
                        public final void onClick(AlertView alertView, int i) {
                        }
                    }, null, null, false, null);
                    return;
                }
            }
            cfl.a(((WalletWithDrawalPage) this.a.mPage).getPageContext(), ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.withdraw_fail), ((WalletWithDrawalPage) this.a.mPage).getResources().getString(R.string.pls_retry_later), new a() {
                public final void onClick(AlertView alertView, int i) {
                }
            }, null, null, false, null);
        }
    }
}
