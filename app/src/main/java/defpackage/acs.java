package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.util.ArrayList;
import java.util.List;

/* renamed from: acs reason: default package */
/* compiled from: RouteTabIndexUtil */
public final class acs {
    public static List<RouteType> a() {
        ArrayList arrayList = new ArrayList();
        if (AMapPageUtil.getAppContext() == null) {
            return arrayList;
        }
        String stringValue = new MapSharePreference((String) "route_tab_index_sp_data").getStringValue("route_tab_index_id_key_900", "");
        if (!TextUtils.isEmpty(stringValue)) {
            String[] split = stringValue.split(MetaRecord.LOG_SEPARATOR);
            if (split != null) {
                for (String a : split) {
                    RouteType a2 = a(a);
                    if (a2 != null) {
                        arrayList.add(a2);
                    }
                }
            }
        }
        return arrayList;
    }

    public static boolean a(List<RouteType> list) {
        if (AMapPageUtil.getAppContext() == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        MapSharePreference mapSharePreference = new MapSharePreference((String) "route_tab_index_sp_data");
        if (list == null || list.size() == 0) {
            mapSharePreference.edit().clear().apply();
        } else {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                sb.append(list.get(i).getValue());
                sb.append(MetaRecord.LOG_SEPARATOR);
            }
            mapSharePreference.putStringValue("route_tab_index_id_key_900", sb.toString());
        }
        return true;
    }

    public static RouteType a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return RouteType.getType(Integer.parseInt(str));
        } catch (Exception unused) {
            return null;
        }
    }
}
