package defpackage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.android.phone.inside.api.accountopenauth.AccountOAuthServiceManager;
import com.alipay.android.phone.inside.api.accountopenauth.IFastOAuthService;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.account.AccountActivity;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.bundle.account.api.IAccountService.HistoryLoginType;
import com.autonavi.bundle.account.api.IAccountService.a;
import com.autonavi.bundle.account.api.IThirdAuth;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.account.logout.model.LogoutResponse;
import com.autonavi.minimap.account.trust_token.TaobaoTrustLoginTokenRequestHolder;
import com.autonavi.minimap.account.trust_token.model.TaobaoTrustLoginTokenResponse;
import com.autonavi.minimap.account.trust_token.param.TaobaoTrustLoginTokenParam;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.util.LocalStorageHelper;
import com.autonavi.server.aos.serverkey;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@BundleInterface(IAccountService.class)
/* renamed from: aof reason: default package */
/* compiled from: AccountService */
public class aof extends esi implements IAccountService {
    public final void a(final a aVar) {
        AccountOAuthServiceManager.getInstance().setFastOAuthService(new IFastOAuthService() {
            public final void openH5Page(Bundle bundle) {
                aVar.openH5Page(bundle);
            }

            public final boolean canShowFastPage(long j) {
                return aol.a().a(j);
            }
        });
    }

    public final void a(AccountType accountType, final anq anq) {
        if (anq != null) {
            aoq.a = "AMAP";
            a.a.a(anq);
            aog.a(accountType, "0", new aox(aox.d) {
                public final void a(chm chm) {
                    super.a(chm);
                    anq anq = anq;
                    boolean z = true;
                    if (chm == null || chm.code != 1) {
                        z = false;
                    }
                    anq.onComplete(z);
                }

                public final void a(Exception exc) {
                    super.a(exc);
                    anq.onComplete(false);
                }
            }, true);
        }
    }

    public final void a(final dko<LogoutResponse> dko) {
        aod.a((aoy) new aoy() {
            public final void a(LogoutResponse logoutResponse) {
                super.a(logoutResponse);
                dko.a(logoutResponse);
            }

            public final void a(Exception exc) {
                super.a(exc);
                dko.a(exc);
            }
        });
    }

    public final boolean a() {
        return aoe.a().d();
    }

    public final String b() {
        return aoe.a().e();
    }

    public final void a(@Nullable bid bid, final AccountType accountType, @Nullable anq anq) {
        if (bid == null || accountType == null) {
            AMapLog.d("accountTAG", "thirdPartyBind pageContext is null");
            return;
        }
        aoq.a = "AMAP";
        aos.a();
        a.a.b(anq);
        aog.a(accountType, 0, new aox(aox.e) {
            public final void a(chm chm) {
                super.a(chm);
                if (chm == null) {
                    a.a.b(false);
                    return;
                }
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("accountType", accountType.name());
                    jSONObject.put("isBind", "1");
                    jSONObject.put("responseData", chm.toJson().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Ajx.getInstance().startService("basemap_account_DefaultThirdPartAccountBindService", "path://amap_bundle_account/src/service/DefaultThirdPartAccountBindService.js", jSONObject.toString(), null);
            }

            public final void a(Exception exc) {
                super.a(exc);
                a.a.b(false);
            }
        });
    }

    public final void a(@Nullable bid bid, @Nullable anq anq) {
        if (bid == null) {
            AMapLog.d("accountTAG", "openLoginHomePage pageContext is null");
            return;
        }
        aoq.a = "AMAP";
        a.a.a(anq);
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_account/src/pages/DefaultLogin.page.js");
        pageBundle.putString("pageId", "DefaultLogin");
        bid.startPage(Ajx3Page.class, pageBundle);
    }

    public final void a(@Nullable bid bid) {
        if (bid == null) {
            AMapLog.d("accountTAG", "openLoginHomePage pageContext is null");
            return;
        }
        aoq.a = "AMAP";
        a.a.a((anq) null);
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_account/src/pages/DefaultLogin.page.js");
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject.put("checkMobileOnce", "1");
            jSONObject2.put("data", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pageBundle.putString("jsData", jSONObject2.toString());
        bid.startPage(Ajx3Page.class, pageBundle);
    }

    public final void b(bid bid) {
        if (aoe.a().d()) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", "path://amap_bundle_account/src/pages/UserInfo.page.js");
            pageBundle.putString("pageId", "UserInfo");
            bid.startPage(Ajx3Page.class, pageBundle);
        }
    }

    public final void c(bid bid) {
        if (aoe.a().d()) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", "path://amap_bundle_account/src/pages/AccountAndSecurity.page.js");
            pageBundle.putString("pageId", "AccountAndSecurity");
            bid.startPage(Ajx3Page.class, pageBundle);
        }
    }

    public final IThirdAuth c() {
        return new aop();
    }

    public final boolean a(AccountType accountType) {
        ant b = aoe.a().b();
        if (b == null) {
            return false;
        }
        switch (accountType) {
            case QQ:
                if (!TextUtils.isEmpty(b.o)) {
                    return true;
                }
                return false;
            case Sina:
                if (!TextUtils.isEmpty(b.i)) {
                    return true;
                }
                return false;
            case Taobao:
                if (!TextUtils.isEmpty(b.l)) {
                    return true;
                }
                return false;
            case Alipay:
                if (!TextUtils.isEmpty(b.t)) {
                    return true;
                }
                return false;
            case Weixin:
                if (!TextUtils.isEmpty(b.p)) {
                    return true;
                }
                return false;
            case Mobile:
                if (!TextUtils.isEmpty(b.h)) {
                    return true;
                }
                return false;
            case Email:
                if (!TextUtils.isEmpty(b.g)) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    public final void d() {
        aoy.a();
    }

    public final ant e() {
        return aoe.a().b();
    }

    public final void a(bid bid, @NonNull anq anq, String str, String str2) {
        aok aok = new aok();
        if (!TextUtils.isEmpty(str)) {
            aok.a(Arrays.asList(new String[]{str}));
        }
        aok.a(bid, anq, str2);
    }

    public final String a(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        ant b = aoe.a().b();
        if (b == null) {
            return "";
        }
        String str2 = b.r.get(str);
        return TextUtils.isEmpty(str2) ? "" : str2;
    }

    @Nullable
    public final HistoryLoginType a(@NonNull Context context) {
        String str;
        String str2;
        LocalStorageHelper localStorageHelper = new LocalStorageHelper(context, "amap_account_local_storage");
        String item = localStorageHelper.getItem("last_login_data_base64", null);
        if (TextUtils.isEmpty(item)) {
            str = serverkey.amapDecodeV2(localStorageHelper.getItem("last_login_data", null));
            localStorageHelper.setItem("last_login_data_base64", serverkey.amapEncodeV2(new String(Base64.encode(str.getBytes(), 0))));
            localStorageHelper.removeItem("last_login_data");
        } else {
            str = new String(Base64.decode(serverkey.amapDecodeV2(item).getBytes(), 0));
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            str2 = new JSONObject(str).getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
            str2 = null;
        }
        return HistoryLoginType.getHistoryLoginType(str2);
    }

    public final void a(final Activity activity, String str, List<String> list, List<String> list2, String str2, Bundle bundle, anq anq) {
        if (anq != null) {
            if (activity == null) {
                anq.onComplete(false);
                return;
            }
            String str3 = null;
            if (bundle != null) {
                str3 = bundle.getString("currentPageUrl");
            }
            AMapLog.d("accountTAG", "openAlipayBindPageForMiniApp, miniAppReturnUrl: ".concat(String.valueOf(str3)));
            aos.a();
            a.a.b(anq);
            AnonymousClass3 r10 = new aox(aox.e) {
                public final void a(chm chm) {
                    super.a(chm);
                    if (chm == null) {
                        a.a.b(false);
                        return;
                    }
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("accountType", AccountType.Alipay.name());
                        jSONObject.put("isBind", "1");
                        jSONObject.put("responseData", chm.toJson().toString());
                        jSONObject.put("fromMiniapp", "1");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Ajx.getInstance().startService(activity, "basemap_account_DefaultThirdPartAccountBindService", "path://amap_bundle_account/src/service/DefaultThirdPartAccountBindService.js", jSONObject.toString(), null);
                }

                public final void a(Exception exc) {
                    super.a(exc);
                    a.a.b(false);
                }
            };
            aok aok = new aok();
            aok.a(list);
            aok.a(str, list2, str3);
            aok.d = str2;
            aok.a(0, r10);
        }
    }

    public final void a(String str, List<String> list, List<String> list2, String str2, Bundle bundle, final anq anq) {
        String string = bundle != null ? bundle.getString("currentPageUrl") : null;
        AMapLog.d("accountTAG", "alipayLoginForMiniApp, miniAppReturnUrl: ".concat(String.valueOf(string)));
        a.a.a(anq);
        AnonymousClass4 r0 = new aox(aox.d) {
            public final void a(chm chm) {
                super.a(chm);
                anq anq = anq;
                boolean z = true;
                if (chm == null || chm.code != 1) {
                    z = false;
                }
                anq.onComplete(z);
            }

            public final void a(Exception exc) {
                super.a(exc);
                anq.onComplete(false);
            }
        };
        aok aok = new aok();
        aok.a(list);
        aok.a(str, list2, string);
        aok.d = str2;
        aok.a("0", r0, true);
    }

    public final void a(Activity activity, String str, anq anq) {
        if (activity != null) {
            aoq.a = str;
            ToastHelper.showToast("登录并授权商家获取您的信息");
            a.a.a(anq);
            activity.startActivity(AccountActivity.a((Context) activity, (String) "path://amap_bundle_account/src/pages/DefaultLogin.page.js", (String) "DefaultLogin"));
        }
    }

    public final void f() {
        aol a = aol.a();
        AMapLog.d("accountTAG", "cancel");
        synchronized (a.b) {
            a.d = -1;
            a.e = -1;
        }
        if (a.a != null) {
            a.a.a();
            a.a = null;
        }
    }

    public final void a(anq anq) {
        TaobaoTrustLoginTokenRequestHolder.getInstance().sendTaobaoTrustLoginToken(new TaobaoTrustLoginTokenParam(), new dko<TaobaoTrustLoginTokenResponse>(anq) {
            final /* synthetic */ anq a;

            {
                this.a = r2;
            }

            public final /* synthetic */ void a(dkm dkm) {
                TaobaoTrustLoginTokenResponse taobaoTrustLoginTokenResponse = (TaobaoTrustLoginTokenResponse) dkm;
                if (taobaoTrustLoginTokenResponse != null) {
                    String str = taobaoTrustLoginTokenResponse.token_data.token;
                    if (!TextUtils.isEmpty(str)) {
                        aoo.a(aoo.this, str, this.a);
                    }
                }
            }

            public final void a(Exception exc) {
                if (this.a != null) {
                    this.a.loginOrBindCancel();
                }
            }
        });
    }

    public final void a(@Nullable bid bid, @Nullable String str, anq anq) {
        if (aoe.a().d()) {
            ant b = aoe.a().b();
            if (b != null && !TextUtils.isEmpty(b.h)) {
                anq.onComplete(true);
            } else if (bid == null) {
                AMapLog.d("accountTAG", "openLoginHomePageAndShowBindMobilePage pageContext is null");
            } else {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("url", "path://amap_bundle_account/src/pages/AccountBindPhone.page.js");
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject.put("bindMobileTip", str);
                    jSONObject2.put("data", jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pageBundle.putString("jsData", jSONObject2.toString());
                pageBundle.putString("pageId", "AccountBindPhone");
                a.a.b(anq);
                bid.startPage(Ajx3Page.class, pageBundle);
            }
        } else if (bid == null) {
            AMapLog.d("accountTAG", "openLoginHomePageAndShowBindMobilePage pageContext is null");
        } else {
            aoq.a = "AMAP";
            PageBundle pageBundle2 = new PageBundle();
            pageBundle2.putString("url", "path://amap_bundle_account/src/pages/DefaultLogin.page.js");
            pageBundle2.putString("pageId", "DefaultBind");
            JSONObject jSONObject3 = new JSONObject();
            JSONObject jSONObject4 = new JSONObject();
            try {
                jSONObject3.put("bindMobileTip", str);
                jSONObject3.put("bindMobileCheck", "1");
                jSONObject4.put("data", jSONObject3);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            a.a.c(anq);
            pageBundle2.putString("jsData", jSONObject4.toString());
            bid.startPage(Ajx3Page.class, pageBundle2);
        }
    }

    public final void a(anp anp) {
        a.a.a(anp);
    }

    public final void b(anp anp) {
        a.a.b(anp);
    }
}
