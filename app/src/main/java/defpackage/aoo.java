package defpackage;

import android.text.TextUtils;
import com.ali.auth.third.accountlink.AccountLinkService;
import com.ali.auth.third.accountlink.AccountLinkType;
import com.ali.auth.third.accountlink.BindCallback;
import com.ali.auth.third.accountlink.IbbParams;
import com.ali.auth.third.core.MemberSDK;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.login.LoginService;
import com.ali.auth.third.login.callback.LogoutCallback;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.account.bind.BindRequestHolder;
import com.autonavi.minimap.account.login.LoginRequestHolder;
import com.autonavi.minimap.account.login.param.LoginTaobaoParam;
import com.autonavi.minimap.account.trust_token.TaobaoTrustLoginTokenRequestHolder;
import com.autonavi.minimap.account.trust_token.model.TaobaoTrustLoginTokenResponse;
import com.autonavi.minimap.account.trust_token.param.TaobaoTrustLoginTokenParam;

/* renamed from: aoo reason: default package */
/* compiled from: TaobaoHandler */
public final class aoo extends aor {
    Callback<String> a;

    /* access modifiers changed from: 0000 */
    public final void a() {
        switch (this.f) {
            case 0:
                c();
                return;
            case 1:
                c();
                return;
            case 2:
                a(aos.c, this.h, this.i, this.j);
                break;
        }
    }

    public final void a(Callback<String> callback) {
        if (callback != null) {
            this.a = callback;
            this.f = 3;
            c();
        }
    }

    private void c() {
        TaobaoTrustLoginTokenRequestHolder.getInstance().sendTaobaoTrustLoginToken(new TaobaoTrustLoginTokenParam(), new dko<TaobaoTrustLoginTokenResponse>() {
            public final /* synthetic */ void a(dkm dkm) {
                TaobaoTrustLoginTokenResponse taobaoTrustLoginTokenResponse = (TaobaoTrustLoginTokenResponse) dkm;
                if (taobaoTrustLoginTokenResponse != null) {
                    String str = taobaoTrustLoginTokenResponse.token_data.token;
                    if (!TextUtils.isEmpty(str)) {
                        aoo.a(aoo.this, str, null);
                        return;
                    }
                }
                aoo.a(aoo.this);
            }

            public final void a(Exception exc) {
                aoo.a(aoo.this);
            }
        });
    }

    public static void b() {
        LoginService loginService = (LoginService) MemberSDK.getService(LoginService.class);
        if (loginService != null) {
            loginService.logout(AMapPageUtil.getMVPActivityContext().a(), new LogoutCallback() {
                public final void onFailure(int i, String str) {
                }

                public final void onSuccess() {
                }
            });
        }
    }

    private void a(String str, int i, int i2, int i3) {
        AMapLog.e("accountTAG", String.format("TaoBaoHandler doTaobaoBind. openSid: %s, type:%s, replace_type: %s, update_mode: %s", new Object[]{str, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)}));
        chs chs = new chs();
        chs.b = str;
        chs.d = i;
        chs.e = i2;
        if (i3 > 0) {
            chs.f = i3;
        }
        BindRequestHolder.getInstance().sendBindTaobao(chs, this.e);
        apd.a();
    }

    static /* synthetic */ void a(aoo aoo, final String str, final anq anq) {
        AccountLinkService accountLinkService = (AccountLinkService) MemberSDK.getService(AccountLinkService.class);
        if (accountLinkService != null) {
            accountLinkService.setBindCallback(new BindCallback() {
                public final IbbParams getBindIbb() {
                    IbbParams ibbParams = new IbbParams();
                    ibbParams.code = AccountLinkType.COOPERATION_TB_TRUST_LOGIN;
                    ibbParams.ibb = "";
                    ibbParams.trustLoginToken = str;
                    return ibbParams;
                }
            });
            accountLinkService.bind(new LoginCallback() {
                public final void onSuccess(Session session) {
                    aod.a(159, aoo.this.e);
                    if (anq != null) {
                        anq.onComplete(true);
                    }
                }

                public final void onFailure(int i, String str) {
                    if (anq != null) {
                        anq.loginOrBindCancel();
                    }
                    aoo.a(aoo.this);
                }
            });
        }
    }

    static /* synthetic */ void a(aoo aoo) {
        AccountLinkService accountLinkService = (AccountLinkService) MemberSDK.getService(AccountLinkService.class);
        if (accountLinkService != null) {
            accountLinkService.setBindCallback(new BindCallback() {
                public final IbbParams getBindIbb() {
                    IbbParams ibbParams = new IbbParams();
                    ibbParams.code = aoo.b(aoo.this);
                    ibbParams.ibb = "";
                    return ibbParams;
                }
            });
            accountLinkService.bind(new LoginCallback() {
                public final void onSuccess(Session session) {
                    if (session != null) {
                        aoo.a(aoo.this, session);
                    }
                }

                public final void onFailure(int i, String str) {
                    if (i == 10004 || i == 10003 || i == 15) {
                        AMapLog.d("TaobaoHandler", "TaoBao user cancal");
                        if (aoo.this.f == 2 || aoo.this.f == 1) {
                            a.a.b();
                            return;
                        }
                        if (aoo.this.k && aoo.this.f == 0) {
                            a.a.a();
                        }
                        return;
                    }
                    aox aox = aoo.this.e;
                    StringBuilder sb = new StringBuilder("code:");
                    sb.append(i);
                    sb.append("\n ");
                    sb.append(str);
                    aox.a(new Exception(sb.toString()));
                    if (aoo.this.a != null) {
                        Callback<String> callback = aoo.this.a;
                        StringBuilder sb2 = new StringBuilder("code");
                        sb2.append(i);
                        sb2.append(Token.SEPARATOR);
                        sb2.append(str);
                        callback.error(new Exception(sb2.toString()), false);
                    }
                }
            });
        }
    }

    static /* synthetic */ int b(aoo aoo) {
        if (aoo.f == 0) {
            return AccountLinkType.COOPERATION_TB_LOGIN;
        }
        if (aoo.f == 2 || aoo.f == 1) {
            return AccountLinkType.COOPERATION_TB_BIND;
        }
        return 0;
    }

    static /* synthetic */ void a(aoo aoo, Session session) {
        String str = session.openSid;
        aos.c = str;
        switch (aoo.f) {
            case 0:
                aox aox = aoo.e;
                LoginTaobaoParam loginTaobaoParam = new LoginTaobaoParam();
                loginTaobaoParam.open_sid = str;
                loginTaobaoParam.limit_login = aoo.g;
                LoginRequestHolder.getInstance().sendLoginTaobao(loginTaobaoParam, aox);
                apd.a();
                return;
            case 1:
            case 2:
                aoo.a(str, aoo.h, aoo.i, aoo.j);
                return;
            case 3:
                if (aoo.a != null) {
                    aoo.a.callback(str);
                    break;
                }
                break;
        }
    }
}
