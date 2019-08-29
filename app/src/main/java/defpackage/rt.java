package defpackage;

import android.text.TextUtils;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: rt reason: default package */
/* compiled from: RouteCarResultUtil */
public final class rt {
    public static JSONArray a(List<POI> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        for (POI b : list) {
            jSONArray.put(bnx.b(b));
        }
        return jSONArray;
    }

    public static JSONArray a(ArrayList<ISearchPoiData> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        Iterator<ISearchPoiData> it = arrayList.iterator();
        while (it.hasNext()) {
            jSONArray.put(bnx.b(it.next()));
        }
        return jSONArray;
    }

    public static boolean a() {
        String a = lo.a().a((String) "etd_cloud");
        if (TextUtils.isEmpty(a)) {
            return false;
        }
        try {
            return TextUtils.equals("1", new JSONObject(a).optString("etd_enter"));
        } catch (Exception unused) {
            return false;
        }
    }

    public static POI a(POI poi) {
        if (poi == null) {
            return null;
        }
        HashMap<String, Serializable> poiExtra = poi.getPoiExtra();
        if (poiExtra == null) {
            return poi;
        }
        Serializable serializable = poiExtra.get("build_type");
        if (serializable != null && (serializable instanceof Integer) && ((Integer) serializable).intValue() == 0) {
            poiExtra.remove("main_poi");
            poiExtra.remove("sub_poi_name");
            poiExtra.remove("build_type");
            poiExtra.remove("scene_poi");
            poiExtra.remove("build_type_train_station_entrance_exit_poies");
        }
        return poi;
    }
}
