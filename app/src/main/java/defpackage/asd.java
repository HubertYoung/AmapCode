package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.uitemplate.mapwidget.widget.user.IUserEventDelegate;
import com.autonavi.minimap.bundle.msgbox.util.LaboratoryStatusStringDef;

/* renamed from: asd reason: default package */
/* compiled from: UserEventDelegate */
public final class asd implements IUserEventDelegate {
    public final boolean getLaboratoryHeadRedShowFlag() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue(LaboratoryStatusStringDef.SP_KEY_LABORATORY_RED_SHOW_FLAG, false);
    }

    public final void setLaboratoryHeadRedShowFlag(boolean z) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue(LaboratoryStatusStringDef.SP_KEY_LABORATORY_RED_SHOW_FLAG, z);
    }

    public final void setMainHeaderRedFlag(boolean z) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue(LaboratoryStatusStringDef.SP_KEY_MAIN_HEADER_RED_FLAG, z);
    }

    public final String getPortraitUrl() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (!iAccountService.a()) {
            return "";
        }
        if (e == null) {
            return "";
        }
        return e.b;
    }

    public final String getUserLevel() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return "";
        }
        ant e = iAccountService.e();
        if (!iAccountService.a()) {
            return "";
        }
        if (e == null) {
            return "";
        }
        return e.A;
    }
}
