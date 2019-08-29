package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.annotation.MultipleImpl;
import com.autonavi.bundle.routecommon.model.RouteType;

@MultipleImpl(awa.class)
/* renamed from: ace reason: default package */
/* compiled from: PlanHomeInit */
public class ace implements awa {
    public final void a() {
        RouteType routeType;
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.user_route_method_info);
        if (mapSharePreference.contains("last_route_type") && !mapSharePreference.contains("last_route_type_900")) {
            switch (mapSharePreference.getIntValue("last_route_type", RouteType.CAR.getValue())) {
                case 0:
                    routeType = RouteType.BUS;
                    break;
                case 1:
                    routeType = RouteType.CAR;
                    break;
                case 2:
                    routeType = RouteType.ONFOOT;
                    break;
                case 3:
                    routeType = RouteType.TRAIN;
                    break;
                case 4:
                    routeType = RouteType.RIDE;
                    break;
                case 5:
                    routeType = RouteType.COACH;
                    break;
                case 6:
                    routeType = RouteType.TAXI;
                    break;
                case 7:
                    routeType = RouteType.TRUCK;
                    break;
                case 8:
                    routeType = RouteType.ETRIP;
                    break;
                case 9:
                    routeType = RouteType.FREERIDE;
                    break;
                case 10:
                    routeType = RouteType.AIRTICKET;
                    break;
                case 11:
                    routeType = RouteType.MOTOR;
                    break;
                default:
                    routeType = RouteType.CAR;
                    break;
            }
            acr.a(routeType);
            mapSharePreference.remove("last_route_type");
        }
        MapSharePreference mapSharePreference2 = new MapSharePreference((String) "route_tab_index_sp_data");
        if (mapSharePreference2.contains("route_tab_index_id_key") && !mapSharePreference2.contains("route_tab_index_id_key_900")) {
            String stringValue = mapSharePreference2.getStringValue("route_tab_index_id_key", "");
            if (!TextUtils.isEmpty(stringValue)) {
                String[] split = stringValue.split(MetaRecord.LOG_SEPARATOR);
                if (split != null && split.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < split.length; i++) {
                        if (TextUtils.equals(split[i], "0")) {
                            sb.append(RouteType.BUS.getValue());
                        } else if (TextUtils.equals(split[i], "1")) {
                            sb.append(RouteType.CAR.getValue());
                        } else if (TextUtils.equals(split[i], "3")) {
                            sb.append(RouteType.TRAIN.getValue());
                        } else if (TextUtils.equals(split[i], "4")) {
                            sb.append(RouteType.RIDE.getValue());
                        } else {
                            sb.append(split[i]);
                        }
                        sb.append(MetaRecord.LOG_SEPARATOR);
                    }
                    mapSharePreference2.putStringValue("route_tab_index_id_key_900", sb.toString());
                }
            }
            mapSharePreference2.remove("route_tab_index_id_key");
        }
    }
}
