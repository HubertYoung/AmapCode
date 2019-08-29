package defpackage;

import android.graphics.Rect;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sdk.location.LocationInstrument;
import java.lang.reflect.Field;
import org.json.JSONObject;

/* renamed from: bbv reason: default package */
/* compiled from: InfoliteParamBuilder */
public final class bbv {
    public static InfoliteParam a(String str, String str2, Rect rect) {
        return a(str, str2, bbw.a(rect), "TQUERY", 1);
    }

    public static InfoliteParam a(String str, String str2, String str3) {
        InfoliteParam a = a(str, str2, null, "IDQ", 1);
        a.id = str3;
        return a;
    }

    public static InfoliteParam a(String str, String str2, GeoPoint geoPoint, Rect rect) {
        InfoliteParam a = a(str, str2, bbw.a(rect), "RQBXY", 2);
        DPoint a2 = cfg.a((long) geoPoint.x, (long) geoPoint.y);
        a.longitude = a2.x;
        a.latitude = a2.y;
        return a;
    }

    public static JSONObject a(InfoliteParam infoliteParam) {
        Field[] declaredFields;
        JSONObject jSONObject = new JSONObject();
        try {
            for (Field field : infoliteParam.getClass().getDeclaredFields()) {
                String name = field.getName();
                field.setAccessible(true);
                Object obj = field.get(infoliteParam);
                if (obj != null) {
                    jSONObject.put(name, obj.toString());
                }
            }
        } catch (Exception unused) {
        }
        return jSONObject;
    }

    private static InfoliteParam a(String str, String str2, String str3, String str4, int i) {
        InfoliteParam infoliteParam = new InfoliteParam();
        infoliteParam.pagesize = 10;
        infoliteParam.pagenum = 1;
        infoliteParam.search_operate = 0;
        infoliteParam.version = "2.19";
        StringBuilder sb = new StringBuilder();
        sb.append(LocationInstrument.getInstance().getLatestPosition().getAdCode());
        infoliteParam.user_city = sb.toString();
        infoliteParam.qii = true;
        infoliteParam.need_utd = true;
        infoliteParam.direct_jump = true;
        infoliteParam.citysuggestion = true;
        infoliteParam.addr_poi_merge = true;
        infoliteParam.need_codepoint = true;
        infoliteParam.need_parkinfo = true;
        infoliteParam.is_classify = true;
        infoliteParam.query_mode = "normal";
        infoliteParam.transfer_filter_flag = "0";
        infoliteParam.cluster_state = "5";
        infoliteParam.transfer_pdheatmap = "0";
        infoliteParam.need_recommend = "1";
        infoliteParam.utd_sceneid = "101000";
        infoliteParam.scenario = 1;
        infoliteParam.superid = SuperId.getInstance().getScenceId();
        infoliteParam.user_loc = str;
        infoliteParam.query_type = str4;
        infoliteParam.keywords = str2;
        infoliteParam.geoobj = str3;
        infoliteParam.search_operate = i;
        infoliteParam.geoobj_adjust = null;
        infoliteParam.longitude = -1000.0d;
        infoliteParam.latitude = -1000.0d;
        infoliteParam.pagesize = 10;
        return infoliteParam;
    }
}
