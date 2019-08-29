package defpackage;

import com.alibaba.fastjson.JSONObject;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* renamed from: abm reason: default package */
/* compiled from: Persister */
public final class abm {
    private static MapSharePreference a = new MapSharePreference((String) "SP_NAME_layer_list");
    private static MapSharePreference b = new MapSharePreference((String) "SP_NAME_layer_checked");

    public static void a(awp awp) {
        a.edit().clear().apply();
        ArrayList<LayerItem> arrayList = awp.c;
        if (arrayList != null && arrayList.size() > 0) {
            Iterator<LayerItem> it = arrayList.iterator();
            while (it.hasNext()) {
                LayerItem next = it.next();
                a.putStringValue(String.valueOf(next.getLayer_id()), JSONObject.toJSONString(next));
            }
        }
        a.putStringValue("key_layer_list_md5", awp.b);
        a.putLongValue("key_layer_list_updated_time", System.currentTimeMillis());
    }

    public static void a(int i, boolean z) {
        b.putBooleanValue(String.valueOf(i), z);
    }

    public static void a(int i) {
        b.remove(String.valueOf(i));
    }

    public static boolean b(int i) {
        return b.getBooleanValue(String.valueOf(i), false);
    }

    public static ArrayList<LayerItem> a() {
        Map<String, ?> all = a.sharedPrefs().getAll();
        all.remove("key_layer_list_md5");
        all.remove("key_layer_list_updated_time");
        ArrayList<LayerItem> arrayList = new ArrayList<>();
        Iterator<?> it = all.values().iterator();
        while (it.hasNext()) {
            arrayList.add(JSONObject.parseObject((String) it.next(), LayerItem.class));
        }
        return arrayList;
    }

    public static ArrayList<Integer> b() {
        Map<String, ?> all = b.sharedPrefs().getAll();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (String next : all.keySet()) {
            if (((Boolean) all.get(next)).booleanValue()) {
                arrayList.add(Integer.valueOf(next));
            }
        }
        return arrayList;
    }

    public static String c() {
        return a.getStringValue("key_layer_list_md5", "");
    }

    public static long d() {
        return a.getLongValue("key_layer_list_updated_time", 0);
    }

    public static List<Integer> e() {
        Map<String, ?> all = a.sharedPrefs().getAll();
        all.remove("key_layer_list_md5");
        all.remove("key_layer_list_updated_time");
        ArrayList arrayList = new ArrayList();
        for (String valueOf : all.keySet()) {
            arrayList.add(Integer.valueOf(valueOf));
        }
        return arrayList;
    }
}
