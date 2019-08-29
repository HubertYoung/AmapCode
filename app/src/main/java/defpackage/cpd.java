package defpackage;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.server.aos.serverkey;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/* renamed from: cpd reason: default package */
/* compiled from: FavoriteFactoryImpl */
public class cpd implements bie, com {
    public final coq a(String str) {
        return cpg.c(str);
    }

    public final cop b(String str) {
        return cpf.b(str);
    }

    public final String a() {
        String str;
        try {
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService == null) {
                str = "";
            } else {
                ant e = iAccountService.e();
                if (e == null) {
                    str = "";
                } else {
                    str = e.a;
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
            str = null;
        }
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str)) {
            SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getSharedPreferences("SecurityPerson", 0);
            if (sharedPreferences != null) {
                String string = sharedPreferences.getString(Oauth2AccessToken.KEY_UID, null);
                if (string != null) {
                    str = serverkey.amapDecode(string);
                }
            }
        }
        return TextUtils.isEmpty(str) ? "public" : str;
    }
}
