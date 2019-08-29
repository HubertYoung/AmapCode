package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.account.bind.BindRequestHolder;
import com.autonavi.minimap.account.login.LoginRequestHolder;
import com.autonavi.minimap.account.login.param.LoginWxParam;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelmsg.SendAuth.Req;
import com.tencent.mm.opensdk.modelmsg.SendAuth.Resp;

/* renamed from: aou reason: default package */
/* compiled from: WeixinHandler */
public class aou extends aor {
    private void b() {
        try {
            aoj.d().a(this);
            Req req = new Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_amap_login";
            aoj.d().a((BaseReq) req);
        } catch (Exception e) {
            e.printStackTrace();
            aoj.d().e();
        }
    }

    public void a(Resp resp) {
        if (resp == null || !(resp.errCode == 0 || resp.errCode == -2)) {
            String concat = "wechat response error:".concat(String.valueOf(resp));
            if (resp != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(concat);
                sb.append(" errorCode:");
                sb.append(resp.errCode);
                concat = sb.toString();
            }
            AMapLog.e("accountTAG", concat);
            this.e.a(new Exception(concat));
        } else if (resp.errCode != -2) {
            String str = resp.code;
            aos.d = str;
            switch (this.f) {
                case 0:
                    aox aox = this.e;
                    LoginWxParam loginWxParam = new LoginWxParam();
                    loginWxParam.code = str;
                    loginWxParam.limit_login = this.g;
                    LoginRequestHolder.getInstance().sendLoginWx(loginWxParam, aox);
                    apd.a();
                    return;
                case 1:
                case 2:
                    a(str, this.h, this.i);
                    break;
            }
        } else if (this.f == 2 || this.f == 1) {
            a.a.b();
        } else {
            if (this.k && this.f == 0) {
                a.a.a();
            }
        }
    }

    private void a(String str, int i, int i2) {
        chu chu = new chu();
        chu.a = str;
        chu.d = i;
        chu.e = i2;
        BindRequestHolder.getInstance().sendBindWx(chu, this.e);
        apd.a();
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        aoj d = aoj.d();
        if (!(d != null && d.b())) {
            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.uninstall_app_tip));
            return;
        }
        switch (this.f) {
            case 0:
                b();
                return;
            case 1:
                b();
                return;
            case 2:
                a(aos.d, this.h, this.i);
                break;
        }
    }
}
