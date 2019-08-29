package defpackage;

import android.content.Intent;
import com.amap.bundle.blutils.log.DebugLog;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.account.bind.BindRequestHolder;
import com.autonavi.minimap.account.login.LoginRequestHolder;
import com.autonavi.minimap.account.login.param.LoginWeiboParam;
import com.autonavi.server.aos.serverkey;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

/* renamed from: aot reason: default package */
/* compiled from: WeiboHandler */
public final class aot extends aor {
    WbAuthListener a = new WbAuthListener() {
        public final void onSuccess(Oauth2AccessToken oauth2AccessToken) {
            if (oauth2AccessToken == null) {
                aot.this.e.a(new Exception("weibo auth oauth2AccessToken is null"));
                return;
            }
            String token = oauth2AccessToken.getToken();
            if (oauth2AccessToken.isSessionValid()) {
                try {
                    aos.e = token;
                    switch (aot.this.f) {
                        case 0:
                            aot.a(aot.this, token, aot.this.e);
                            return;
                        case 1:
                        case 2:
                            aot.this.a(token, aot.this.h, aot.this.i);
                            break;
                    }
                } catch (Exception e) {
                    DebugLog.warn(e);
                }
            }
        }

        public final void cancel() {
            if (aot.this.f == 2 || aot.this.f == 1) {
                a.a.b();
                return;
            }
            if (aot.this.k && aot.this.f == 0) {
                a.a.a();
            }
        }

        public final void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
            aot.this.e.a(new Exception("weibo auth failed"));
        }
    };
    private SsoHandler b;

    /* access modifiers changed from: 0000 */
    public final void a() {
        switch (this.f) {
            case 0:
                b();
                return;
            case 1:
                b();
                return;
            case 2:
                a(aos.e, this.h, this.i);
                break;
        }
    }

    public final void a(int i, int i2, Intent intent) {
        try {
            if (this.b != null) {
                this.b.authorizeCallBack(i, i2, intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, int i, int i2) {
        cht cht = new cht();
        cht.c = str;
        cht.e = i;
        cht.f = i2;
        BindRequestHolder.getInstance().sendBindWeibo(cht, this.e);
        agw.a(str, serverkey.getSsoKey());
        apd.a();
    }

    private void b() {
        try {
            a.a.a(this);
            this.b = new SsoHandler(AMapPageUtil.getMVPActivityContext().a());
            this.b.authorize(this.a);
        } catch (Exception e) {
            a.a.a();
            e.printStackTrace();
        }
    }

    static /* synthetic */ void a(aot aot, String str, aox aox) {
        LoginWeiboParam loginWeiboParam = new LoginWeiboParam();
        loginWeiboParam.token = str;
        loginWeiboParam.limit_login = aot.g;
        LoginRequestHolder.getInstance().sendLoginWeibo(loginWeiboParam, aox);
        apd.a();
    }
}
