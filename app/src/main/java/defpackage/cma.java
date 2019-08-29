package defpackage;

import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.bundle.account.api.IAccountService;

/* renamed from: cma reason: default package */
/* compiled from: CommonParamsProvider */
final class cma implements d {
    public final String a() {
        return ik.a().d;
    }

    public final String b() {
        return ik.a().e;
    }

    public final String d() {
        vc.a();
        return vc.a;
    }

    public final String e() {
        return ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.AETRAFFIC_KEY);
    }

    public final String f() {
        return ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.CONF_DIB_KEY);
    }

    public final String g() {
        return ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.PRODUCT_ID_KEY);
    }

    public final String h() {
        return ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.CUSTOM_ID_KEY);
    }

    public final String i() {
        return ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.TERMINAL_ID_KEY);
    }

    public final String j() {
        return ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.SEARCH_API_VERSION);
    }

    public final String c() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        return iAccountService.b();
    }
}
