package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

/* renamed from: brb reason: default package */
/* compiled from: SuspendUtils */
public class brb implements cct {
    public final boolean a(boolean z) {
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        bid pageContext = AMapPageUtil.getPageContext();
        boolean z2 = (pageContext == null || AMapPageUtil.isInstance(awd.class, pageContext) == null) ? false : true;
        boolean U = bin.a.U();
        boolean V = bin.a.V();
        boolean z3 = U || V || (!U && !V && !mapSharePreference.getBooleanValue("satellite", false));
        if (!z2 || !z || !z3) {
            return false;
        }
        return true;
    }
}
