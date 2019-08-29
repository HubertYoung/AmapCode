package defpackage;

import android.text.TextUtils;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.common.OfflineSearchMode;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;

/* renamed from: bxy reason: default package */
/* compiled from: SearchResulProcesstHelper */
public final class bxy {
    public static boolean a(InfoliteResult infoliteResult) {
        int i;
        InfoliteParam infoliteParam = infoliteResult.mWrapper;
        if (infoliteResult.searchInfo.l == null || infoliteResult.searchInfo.l.size() <= 0) {
            if (!TextUtils.isEmpty(infoliteParam.city)) {
                if (infoliteParam.city.length() < 6) {
                    lj a = li.a().a(infoliteParam.city);
                    if (a(infoliteParam)) {
                        i = a.j;
                    }
                } else {
                    i = bby.a(infoliteParam.city);
                }
            } else if (infoliteParam.search_operate == 1) {
                OfflineSearchMode a2 = aez.a("");
                if (a2 != null) {
                    i = bby.a(a2.strAdCode);
                }
            } else {
                i = new GeoPoint(infoliteParam.longitude, infoliteParam.latitude).getAdCode();
            }
            i = 0;
        } else {
            i = infoliteResult.searchInfo.l.get(0).getPoint().getAdCode();
        }
        IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
        if (iOfflineManager != null) {
            return iOfflineManager.checkCityDownload(i);
        }
        return false;
    }

    private static boolean a(InfoliteParam infoliteParam) {
        return (infoliteParam == null || li.a().a(infoliteParam.city) == null) ? false : true;
    }
}
