package com.autonavi.bundle.account.ajx;

import android.os.Build.VERSION;
import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.account.alipay.model.AlipayAuthorizeInfoResponse;
import com.autonavi.minimap.account.deactivate.model.DeactivateResponse;
import com.autonavi.minimap.account.logout.model.LogoutResponse;
import com.autonavi.minimap.account.modify.model.ModifyPWResponse;
import com.autonavi.minimap.account.password.model.PasswordInitResponse;
import com.autonavi.minimap.account.reset.model.ResetPWResponse;
import com.autonavi.minimap.account.unbind.model.UnbindResponse;
import com.autonavi.minimap.account.verify.model.VerifycodeResponse;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("account")
public class ModuleAccount extends AbstractModule implements anp {
    private static int ERROR = -2;
    private static final String TAG = "ModuleAccount";
    private JsFunctionCallback accountStatusChangedCallback;
    private boolean login = false;

    public void onUserInfoUpdate(ant ant) {
    }

    public ModuleAccount(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("userNameLogin")
    public void userNameLogin(String str, String str2, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            aod.b(str, str2, new aox(aox.d) {
                public final void a(chm chm) {
                    super.a(chm);
                    ModuleAccount.this.successCallback(chm, jsFunctionCallback);
                }

                public final void a(Exception exc) {
                    super.a(exc);
                    ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                }
            });
        }
    }

    @AjxMethod("mobileLogin")
    public void mobileLogin(String str, String str2, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            aod.a(str, str2, (aox) new aox(aox.d) {
                public final void a(chm chm) {
                    super.a(chm);
                    ModuleAccount.this.successCallback(chm, jsFunctionCallback);
                }

                public final void a(Exception exc) {
                    super.a(exc);
                    ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                }
            });
        }
    }

    @AjxMethod("getVerifyCode")
    public void getVerifyCode(String str, String str2, String str3, String str4, final JsFunctionCallback jsFunctionCallback) {
        int i;
        if (jsFunctionCallback != null) {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
                jsFunctionCallback.callback(buildError(ERROR, "params error"));
                return;
            }
            if (str4 != null) {
                try {
                    i = Integer.valueOf(str4).intValue();
                } catch (NumberFormatException unused) {
                }
                aod.a(str, str2, str3, i, (dko<VerifycodeResponse>) new dko<VerifycodeResponse>() {
                    public final /* synthetic */ void a(dkm dkm) {
                        ModuleAccount.this.successCallback((VerifycodeResponse) dkm, jsFunctionCallback);
                    }

                    public final void a(Exception exc) {
                        ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                    }
                });
            }
            i = 0;
            aod.a(str, str2, str3, i, (dko<VerifycodeResponse>) new dko<VerifycodeResponse>() {
                public final /* synthetic */ void a(dkm dkm) {
                    ModuleAccount.this.successCallback((VerifycodeResponse) dkm, jsFunctionCallback);
                }

                public final void a(Exception exc) {
                    ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                }
            });
        }
    }

    @AjxMethod("thirdPartyLogin")
    public void thirdPartyLogin(String str, String str2, final JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && jsFunctionCallback != null) {
            aog.a(AccountType.valueOf(str), str2, new aox(aox.d) {
                public final void a(chm chm) {
                    super.a(chm);
                    ModuleAccount.this.successCallback(chm, jsFunctionCallback);
                }

                public final void a(Exception exc) {
                    super.a(exc);
                    ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                }
            }, false);
        }
    }

    @AjxMethod("logout")
    public void logout(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            aod.a((aoy) new aoy() {
                public final void a(LogoutResponse logoutResponse) {
                    super.a(logoutResponse);
                    ModuleAccount.this.successCallback(logoutResponse, jsFunctionCallback);
                }

                public final void a(Exception exc) {
                    super.a(exc);
                    ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                }
            });
        }
    }

    @AjxMethod("resetPassword")
    public void resetPassword(String str, String str2, String str3, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            aod.a(str, str2, str3, (dko<ResetPWResponse>) new dko<ResetPWResponse>() {
                public final /* synthetic */ void a(dkm dkm) {
                    ModuleAccount.this.successCallback((ResetPWResponse) dkm, jsFunctionCallback);
                }

                public final void a(Exception exc) {
                    ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                }
            });
        }
    }

    @AjxMethod("modifyPassword")
    public void modifyPassword(String str, String str2, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            aod.a(str, str2, (dko<ModifyPWResponse>) new dko<ModifyPWResponse>() {
                public final /* synthetic */ void a(dkm dkm) {
                    ModuleAccount.this.successCallback((ModifyPWResponse) dkm, jsFunctionCallback);
                }

                public final void a(Exception exc) {
                    ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                }
            });
        }
    }

    @AjxMethod("initPassword")
    public void initPassword(String str, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            aod.a(str, (dko<PasswordInitResponse>) new dko<PasswordInitResponse>() {
                public final /* synthetic */ void a(dkm dkm) {
                    ModuleAccount.this.successCallback((PasswordInitResponse) dkm, jsFunctionCallback);
                }

                public final void a(Exception exc) {
                    ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                }
            });
        }
    }

    @AjxMethod("checkVerifyCode")
    public void checkVerifyCode(String str, String str2, String str3, String str4, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            aod.a(str, str2, str3, str4, (dko<VerifycodeResponse>) new dko<VerifycodeResponse>() {
                public final /* synthetic */ void a(dkm dkm) {
                    ModuleAccount.this.successCallback((VerifycodeResponse) dkm, jsFunctionCallback);
                }

                public final void a(Exception exc) {
                    ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                }
            });
        }
    }

    @AjxMethod("updateUserProfile")
    public void updateUserProfile(String str, String str2, String str3, String str4, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            if (!TextUtils.isEmpty(str) && str.startsWith("file:///")) {
                str = str.substring(7);
            }
            aod.a(str, str2, str3, str4, (aox) new aox(aox.g) {
                public final void a(chm chm) {
                    super.a(chm);
                    ModuleAccount.this.successCallback(chm, jsFunctionCallback);
                }

                public final void a(Exception exc) {
                    super.a(exc);
                    ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                }
            });
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getUserInfo")
    public String getUserInfo() {
        ant b = aoe.a().b();
        String str = bny.c;
        if (b != null) {
            str = apg.a(b);
        }
        AMapLog.e("accountTAG", "ajx getUserInfo".concat(String.valueOf(str)));
        return str;
    }

    @AjxMethod("getMobile")
    public void getMobile(JsFunctionCallback jsFunctionCallback) {
        String str;
        ant b = aoe.a().b();
        if (b == null) {
            str = null;
        } else {
            str = b.h;
        }
        Object[] objArr = new Object[1];
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        objArr[0] = str;
        jsFunctionCallback.callback(objArr);
    }

    @AjxMethod(invokeMode = "sync", value = "isLogin")
    public boolean isLogin() {
        return aoe.a().d();
    }

    @AjxMethod("fetchUserInfo")
    public void fetchUserInfo(int i, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            aod.a(i, (aox) new aox(aox.f) {
                public final void a(chm chm) {
                    super.a(chm);
                    ModuleAccount.this.successCallback(chm, jsFunctionCallback);
                }

                public final void a(Exception exc) {
                    super.a(exc);
                    ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                }
            });
        }
    }

    @AjxMethod("emailBind")
    public void emailBind(String str, String str2, String str3, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null && !TextUtils.isEmpty(str3)) {
            try {
                aod.a(str, str2, Integer.valueOf(str3).intValue(), (aox) new aox(aox.e) {
                    public final void a(chm chm) {
                        super.a(chm);
                        ModuleAccount.this.successCallback(chm, jsFunctionCallback);
                    }

                    public final void a(Exception exc) {
                        super.a(exc);
                        ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                    }
                });
            } catch (NumberFormatException e) {
                e.printStackTrace();
                errorCallback(e, jsFunctionCallback);
            }
        }
    }

    @AjxMethod("mobileBind")
    public void mobileBind(String str, String str2, String str3, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null && !TextUtils.isEmpty(str3)) {
            try {
                aod.b(str, str2, Integer.valueOf(str3).intValue(), new aox(aox.e) {
                    public final void a(chm chm) {
                        super.a(chm);
                        ModuleAccount.this.successCallback(chm, jsFunctionCallback);
                    }

                    public final void a(Exception exc) {
                        super.a(exc);
                        ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                    }
                });
            } catch (NumberFormatException e) {
                e.printStackTrace();
                errorCallback(e, jsFunctionCallback);
            }
        }
    }

    @AjxMethod("thirdPartyAuthorizationAndBind")
    public void thirdPartyAuthorizationAndBind(String str, String str2, final JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str) && jsFunctionCallback != null && !TextUtils.isEmpty(str2)) {
            try {
                aog.a(AccountType.valueOf(str), Integer.valueOf(str2).intValue(), new aox(aox.e) {
                    public final void a(chm chm) {
                        super.a(chm);
                        ModuleAccount.this.successCallback(chm, jsFunctionCallback);
                    }

                    public final void a(Exception exc) {
                        super.a(exc);
                        ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                    }
                });
            } catch (NumberFormatException e) {
                e.printStackTrace();
                errorCallback(e, jsFunctionCallback);
            }
        }
    }

    @AjxMethod("thirdPartyBind")
    public void thirdPartyBind(String str, String str2, final JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str) && jsFunctionCallback != null && !TextUtils.isEmpty(str2)) {
            try {
                int intValue = Integer.valueOf(str2).intValue();
                AccountType valueOf = AccountType.valueOf(str);
                AnonymousClass7 r0 = new aox(aox.e) {
                    public final void a(chm chm) {
                        super.a(chm);
                        ModuleAccount.this.successCallback(chm, jsFunctionCallback);
                    }

                    public final void a(Exception exc) {
                        super.a(exc);
                        ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                    }
                };
                aor a = aog.a(valueOf);
                if (a != null) {
                    a.b(intValue, r0);
                } else {
                    r0.a(new Exception(""));
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                errorCallback(e, jsFunctionCallback);
            }
        }
    }

    @AjxMethod("unbind")
    public void unbind(String str, String str2, final JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str) && jsFunctionCallback != null) {
            AccountType valueOf = AccountType.valueOf(str);
            aod.a(valueOf, str2, (aoz) new aoz(valueOf) {
                public final void a(UnbindResponse unbindResponse) {
                    super.a(unbindResponse);
                    ModuleAccount.this.successCallback(unbindResponse, jsFunctionCallback);
                }

                public final void a(Exception exc) {
                    super.a(exc);
                    ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                }
            });
        }
    }

    @AjxMethod("deactivate")
    public void deactivate(String str, String str2, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            aod.a(str, str2, (aow) new aow() {
                public final void a(DeactivateResponse deactivateResponse) {
                    super.a(deactivateResponse);
                    ModuleAccount.this.successCallback(deactivateResponse, jsFunctionCallback);
                }

                public final void a(Exception exc) {
                    super.a(exc);
                    ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                }
            });
        }
    }

    @AjxMethod("alipayAuthorizeInfo")
    public void alipayAuthorizeInfo(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            aod.a((dko<AlipayAuthorizeInfoResponse>) new dko<AlipayAuthorizeInfoResponse>() {
                public final /* synthetic */ void a(dkm dkm) {
                    ModuleAccount.this.successCallback((AlipayAuthorizeInfoResponse) dkm, jsFunctionCallback);
                }

                public final void a(Exception exc) {
                    ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                }
            });
        }
    }

    @AjxMethod("bindMobileWithPaymentForLogin")
    public void bindMobileWithPaymentForLogin(String str, String str2, String str3, String str4, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null && !TextUtils.isEmpty(str4)) {
            try {
                aod.a(str, str2, str3, Integer.valueOf(str4).intValue(), (aox) new aox(aox.e) {
                    public final void a(chm chm) {
                        super.a(chm);
                        ModuleAccount.this.successCallback(chm, jsFunctionCallback);
                    }

                    public final void a(Exception exc) {
                        super.a(exc);
                        ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                    }
                });
            } catch (NumberFormatException e) {
                e.printStackTrace();
                errorCallback(e, jsFunctionCallback);
            }
        }
    }

    @AjxMethod("clearLoginData")
    public void clearLoginData() {
        aoe.a().c();
    }

    @AjxMethod("appealAndBindMobile")
    public void appealAndBindMobile(String str, String str2, String str3, String str4, final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null && !TextUtils.isEmpty(str4)) {
            try {
                aod.b(str, str2, str3, Integer.valueOf(str4).intValue(), new aox(aox.e) {
                    public final void a(chm chm) {
                        super.a(chm);
                        ModuleAccount.this.successCallback(chm, jsFunctionCallback);
                    }

                    public final void a(Exception exc) {
                        super.a(exc);
                        ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                    }
                });
            } catch (NumberFormatException e) {
                e.printStackTrace();
                errorCallback(e, jsFunctionCallback);
            }
        }
    }

    @AjxMethod("openUserLevelWebView")
    public void openUserLevelWebView() {
        aja aja = new aja(ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.USER_LEVEL_URL));
        aja.b = new ajf() {
            public final boolean g() {
                return false;
            }
        };
        aix aix = (aix) a.a.a(aix.class);
        if (aix != null) {
            aix.a(AMapPageUtil.getPageContext(), aja);
        }
    }

    @AjxMethod("openUserCheckinWebView")
    public void openUserCheckinWebView() {
        aja aja = new aja(ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.USER_CHECKIN_URL));
        aja.b = new ajf();
        aix aix = (aix) a.a.a(aix.class);
        if (aix != null) {
            aix.a(AMapPageUtil.getPageContext(), aja);
        }
    }

    @AjxMethod("doTaoBaoSDKLogout")
    public void doTaoBaoSDKLogout() {
        aoo.b();
    }

    @AjxMethod("doFastLogin")
    public void doFastLogin(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            AnonymousClass15 r0 = new aox(aox.d) {
                public final void a(chm chm) {
                    super.a(chm);
                    ModuleAccount.this.successCallback(chm, jsFunctionCallback);
                }

                public final void a(Exception exc) {
                    AMapLog.e("fastLogin", "call onError()");
                    ToastHelper.showToast("支付宝授权失败");
                    super.a(exc);
                    ModuleAccount.this.errorCallback(exc, jsFunctionCallback);
                }
            };
            if (VERSION.SDK_INT != 26) {
                aok aok = new aok();
                aok.c = true;
                aok.a("1", r0, false);
            }
        }
    }

    @AjxMethod("setAccountStatusChangedCallback")
    public void setAccountStatusChangedCallback(JsFunctionCallback jsFunctionCallback) {
        this.accountStatusChangedCallback = jsFunctionCallback;
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a((anp) this);
        }
    }

    @AjxMethod("removeAccountStatusChangedCallback")
    public void removeAccountStatusChangedCallback() {
        if (this.accountStatusChangedCallback != null) {
            this.accountStatusChangedCallback = null;
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                iAccountService.b((anp) this);
            }
        }
    }

    @AjxMethod("testNotifyAccountStatusChangedCallback")
    public void testNotifyAccountStatusChangedCallback() {
        boolean z = !this.login;
        this.login = z;
        notifyAccountStatusChangedCallback(z);
    }

    private void notifyAccountStatusChangedCallback(boolean z) {
        if (this.accountStatusChangedCallback != null && this.accountStatusChangedCallback != null) {
            this.accountStatusChangedCallback.callback(Boolean.valueOf(z));
        }
    }

    /* access modifiers changed from: private */
    public void successCallback(dkm dkm, JsFunctionCallback jsFunctionCallback) {
        if (dkm != null) {
            try {
                JSONObject json = dkm.toJson();
                if (json != null) {
                    String jSONObject = json.toString();
                    AMapLog.e("accountTAG", jSONObject);
                    jsFunctionCallback.callback(jSONObject);
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        jsFunctionCallback.callback(buildError(ERROR, ""));
    }

    /* access modifiers changed from: private */
    public void errorCallback(Exception exc, JsFunctionCallback jsFunctionCallback) {
        AMapLog.e("accountTAG", buildError(ERROR, exc.getLocalizedMessage()));
        jsFunctionCallback.callback(buildError(ERROR, exc.getLocalizedMessage()));
    }

    private String buildError(int i, String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("code", i);
            jSONObject.put("message", str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public void onLoginStateChanged(boolean z, boolean z2) {
        if (z2) {
            notifyAccountStatusChangedCallback(true);
        } else {
            notifyAccountStatusChangedCallback(false);
        }
    }

    @AjxMethod("onCancelLogin")
    public void onCancelLogin() {
        a.a.a();
        aov a = a.a;
        for (anq loginOrBindCancel : a.c()) {
            loginOrBindCancel.loginOrBindCancel();
        }
        a.c().clear();
    }

    @AjxMethod("onCancelThirdPartyBind")
    public void onCancelThirdPartyBind(String str) {
        a.a.b();
    }

    @AjxMethod("cancelFastLogin")
    public void cancelFastLogin() {
        boolean z;
        AMapLog.d("accountTAG", "cancelAlipayFastLogin");
        aol a = aol.a();
        AMapLog.d("accountTAG", "cancelFastLogin");
        synchronized (a.b) {
            z = a.c;
            if (z) {
                a.d = -1;
                a.e = -1;
            }
        }
        if (a.a != null && z) {
            a.a.a();
            a.a = null;
        }
    }

    @AjxMethod("openLoginHomePageAndCheckMobileOnce")
    public void openLoginHomePageAndCheckMobileOnce() {
        final IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            aho.a(new Runnable() {
                public final void run() {
                    iAccountService.a(AMapPageUtil.getPageContext());
                }
            });
        }
    }
}
