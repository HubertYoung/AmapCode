package defpackage;

import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.account.bind.BindRequestHolder;
import com.autonavi.minimap.account.login.LoginRequestHolder;
import com.autonavi.minimap.account.login.param.LoginQQParam;
import com.autonavi.server.aos.serverkey;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import org.json.JSONObject;

/* renamed from: aom reason: default package */
/* compiled from: QQHandler */
public final class aom extends aor {

    /* renamed from: aom$a */
    /* compiled from: QQHandler */
    class a implements IUiListener {
        private a() {
        }

        /* synthetic */ a(aom aom, byte b) {
            this();
        }

        public final void onError(UiError uiError) {
            aom.this.e.a(new Exception("QQHandler IUiListener onError"));
        }

        public final void onComplete(Object obj) {
            if (obj == null) {
                aom.this.e.a(new Exception("QQHandler onComplete response is null"));
                return;
            }
            try {
                if (obj instanceof JSONObject) {
                    String optString = ((JSONObject) obj).optString("access_token");
                    aos.b = optString;
                    switch (aom.this.f) {
                        case 0:
                            aom.a(aom.this, optString, aom.this.e);
                            return;
                        case 1:
                        case 2:
                            aom.this.a(optString, aom.this.h, aom.this.i);
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public final void onCancel() {
            if (aom.this.f == 2 || aom.this.f == 1) {
                defpackage.aov.a.a.b();
                return;
            }
            if (aom.this.k && aom.this.f == 0) {
                defpackage.aov.a.a.a();
            }
        }
    }

    private void b() {
        Tencent createInstance = Tencent.createInstance(serverkey.getQQCustomKey(), AMapAppGlobal.getApplication());
        a aVar = new a(this, 0);
        defpackage.aon.a.a.a(aVar);
        createInstance.login(AMapPageUtil.getMVPActivityContext().a(), (String) "all", (IUiListener) aVar);
    }

    public final void a(String str, int i, int i2) {
        chr chr = new chr();
        chr.b = str;
        chr.d = i;
        chr.e = i2;
        BindRequestHolder.getInstance().sendBindQQ(chr, this.e);
        apd.a();
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        if (!ahp.a((String) "com.tencent.mobileqq")) {
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
                a(aos.b, this.h, this.i);
                break;
        }
    }

    static /* synthetic */ void a(aom aom, String str, aox aox) {
        LoginQQParam loginQQParam = new LoginQQParam();
        loginQQParam.token = str;
        loginQQParam.code = "";
        loginQQParam.limit_login = aom.g;
        LoginRequestHolder.getInstance().sendLoginQQ(loginQQParam, aox);
        apd.a();
    }
}
