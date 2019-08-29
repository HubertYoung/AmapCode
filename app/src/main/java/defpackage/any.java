package defpackage;

import android.text.TextUtils;
import com.ali.auth.third.login.LoginConstants;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import org.json.JSONObject;

/* renamed from: any reason: default package */
/* compiled from: LoginBindAction */
public class any extends vz {
    public final void a(JSONObject jSONObject, final wa waVar) {
        final JsAdapter a = a();
        if (a != null) {
            String optString = jSONObject.optString("type");
            AnonymousClass2 r7 = new anq() {
                public final void onComplete(boolean z) {
                    any.b(a, waVar);
                }

                public final void loginOrBindCancel() {
                    any.b(a, waVar);
                }
            };
            final wa waVar2 = waVar;
            final JsAdapter jsAdapter = a;
            final String str = optString;
            final AnonymousClass2 r5 = r7;
            AnonymousClass1 r0 = new anq() {
                public final void onComplete(boolean z) {
                    if (z) {
                        any.this.a(waVar2, jsAdapter, str, r5);
                    } else {
                        any.b(jsAdapter, waVar2);
                    }
                }

                public final void loginOrBindCancel() {
                    any.b(jsAdapter, waVar2);
                }
            };
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                if (iAccountService.a()) {
                    a(waVar, a, optString, r7);
                } else if (optString == null || !optString.equals(LoginConstants.TAOBAO_LOGIN)) {
                    if (a() != null) {
                        iAccountService.a(a().mPageContext, (String) "", (anq) r0);
                    }
                } else {
                    iAccountService.a(AccountType.Taobao, (anq) r0);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static void b(JsAdapter jsAdapter, wa waVar) {
        String str;
        JSONObject jSONObject = new JSONObject();
        try {
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                ant e = iAccountService.e();
                String b = iAccountService.b();
                if (e == null) {
                    str = null;
                } else {
                    str = e.h;
                }
                boolean a = iAccountService.a(AccountType.Taobao);
                jSONObject.put("_action", waVar.b);
                if (TextUtils.isEmpty(b)) {
                    b = "";
                }
                jSONObject.put("userid", b);
                if (TextUtils.isEmpty(str)) {
                    str = "";
                }
                jSONObject.put("phone", str);
                jSONObject.put(LoginConstants.TAOBAO_LOGIN, a ? 1 : 0);
                jsAdapter.callJs(waVar.a, jSONObject.toString());
                AMapLog.i("LoginBindAction", "jsCallback:");
            }
        } catch (Exception e2) {
            kf.a((Throwable) e2);
        }
    }

    /* access modifiers changed from: private */
    public void a(wa waVar, JsAdapter jsAdapter, String str, anq anq) {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            if (str.equalsIgnoreCase("phone") && !iAccountService.a(AccountType.Mobile)) {
                a(str, anq);
            } else if (str.equalsIgnoreCase(LoginConstants.TAOBAO_LOGIN) && !iAccountService.a(AccountType.Taobao)) {
                a(str, anq);
            } else if (!str.equalsIgnoreCase("phone") || !iAccountService.a(AccountType.Mobile)) {
                if (str.equalsIgnoreCase(LoginConstants.TAOBAO_LOGIN) && iAccountService.a(AccountType.Taobao)) {
                    b(jsAdapter, waVar);
                }
            } else {
                b(jsAdapter, waVar);
            }
        }
    }

    private void a(String str, anq anq) {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null && a() != null) {
            if ("phone".equals(str)) {
                iAccountService.a(a().mPageContext, (String) "", anq);
                return;
            }
            if (LoginConstants.TAOBAO_LOGIN.equals(str)) {
                iAccountService.a(a().mPageContext, AccountType.Taobao, anq);
            }
        }
    }
}
