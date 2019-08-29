package com.autonavi.map.wallet;

import com.amap.bundle.drivecommon.inter.NetConstant;
import com.amap.bundle.network.response.exception.ServerException;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AlertView;

public class WalletUiController$5 implements Callback<cft> {
    final /* synthetic */ a a;
    final /* synthetic */ boolean b;
    final /* synthetic */ String c;
    final /* synthetic */ cfl d;

    public WalletUiController$5(cfl cfl, a aVar, boolean z, String str) {
        this.d = cfl;
        this.a = aVar;
        this.b = z;
        this.c = str;
    }

    public void error(Throwable th, boolean z) {
        if (th != null && (th instanceof ServerException)) {
            int code = ((ServerException) th).getCode();
            if (code != 0) {
                if (code == 14) {
                    cfl.a(this.d.a, AMapAppGlobal.getApplication().getString(R.string.prompt_msg), AMapAppGlobal.getApplication().getString(R.string.wallet_relogin), new a() {
                        public final void onClick(AlertView alertView, int i) {
                            WalletUiController$5.this.d.a((AccountType) null, WalletUiController$5.this.a);
                        }
                    }, null, null, false, null);
                    return;
                } else if (code == 24) {
                    cfl.a(this.d.a, AMapAppGlobal.getApplication().getString(R.string.authorization_expires_title), AMapAppGlobal.getApplication().getString(R.string.authorization_expires_desc), new a() {
                        public final void onClick(AlertView alertView, int i) {
                            WalletUiController$5.this.d.a(AccountType.Taobao, WalletUiController$5.this.a);
                        }
                    }, null, null, true, null);
                    return;
                } else if (code == 59) {
                    cfl.a(this.d.a, AMapAppGlobal.getApplication().getString(R.string.wallet_taobao_unbind), AMapAppGlobal.getApplication().getString(R.string.wallet_taobao_login), new a() {
                        public final void onClick(AlertView alertView, int i) {
                            cfl cfl = WalletUiController$5.this.d;
                            a aVar = WalletUiController$5.this.a;
                            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                            if (iAccountService != null) {
                                iAccountService.a(cfl.a, AccountType.Taobao, (anq) new anq(aVar) {
                                    final /* synthetic */ a a;

                                    {
                                        this.a = r2;
                                    }

                                    public final void onComplete(boolean z) {
                                        cfl.this.a(null, this.a, true);
                                    }

                                    public final void loginOrBindCancel() {
                                        cfl.a(this.a, Boolean.FALSE);
                                    }
                                });
                            }
                        }
                    }, AMapAppGlobal.getApplication().getString(R.string.action_authorize), null, true, null);
                    return;
                } else if (code != 10052) {
                    switch (code) {
                        case 2:
                        case 3:
                        case 4:
                            break;
                        default:
                            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.wallet_repuest_fail));
                            break;
                    }
                } else {
                    ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.wallet_taobao_account_not_bind_alipay));
                    return;
                }
            }
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.wallet_repuest_fail));
        }
    }

    public void callback(cft cft) {
        PageBundle pageBundle = new PageBundle();
        if (cft.errorCode == 1) {
            if (cft.b.d.booleanValue()) {
                String str = cft.b.a;
                StringBuilder sb = new StringBuilder("*");
                sb.append(str.substring(1, str.length()));
                String sb2 = sb.toString();
                String str2 = cft.b.c;
                pageBundle.putString("name", sb2);
                pageBundle.putString(NetConstant.KEY_MONEY_ACCOUNT, str2);
                pageBundle.putBoolean("sso", this.b);
                if (this.b) {
                    pageBundle.putString("token", this.c);
                } else {
                    pageBundle.putString("token", "");
                }
                if (this.a != null) {
                    this.a.a(pageBundle, 1);
                }
                return;
            }
            cfl.a(this.d.a, AMapAppGlobal.getApplication().getString(R.string.prompt_msg), AMapAppGlobal.getApplication().getString(R.string.alipay_certification), new a() {
                public final void onClick(AlertView alertView, int i) {
                }
            }, null, AMapAppGlobal.getApplication().getString(R.string.alipay_withdraw_others), false, null);
        }
    }
}
