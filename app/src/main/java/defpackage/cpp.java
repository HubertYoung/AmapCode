package defpackage;

import android.content.Context;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;

/* renamed from: cpp reason: default package */
/* compiled from: OperationIntentDispatcherPresenter */
public class cpp extends ajf {
    private Context b;

    public cpp(Context context) {
        this.b = context;
        if (this.b != null) {
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService != null) {
                MapSharePreference mapSharePreference = new MapSharePreference((String) "AlipayClearCookies");
                boolean booleanValue = mapSharePreference.getBooleanValue("isReplaceAlipay", false);
                if (!iAccountService.a() || !iAccountService.a(AccountType.Alipay)) {
                    ajp.a(this.b);
                } else if (booleanValue) {
                    ajp.a(this.b);
                    mapSharePreference.putBooleanValue("isReplaceAlipay", false);
                }
            }
        }
    }

    public final boolean e() {
        this.a.a().setUseWideViewPort(true);
        this.a.a().setLoadWithOverviewMode(true);
        this.a.a().setSavePassword(false);
        return true;
    }
}
