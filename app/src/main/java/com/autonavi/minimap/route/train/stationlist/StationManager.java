package com.autonavi.minimap.route.train.stationlist;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class StationManager {
    public static final String[] c = {"A", DiskFormatter.B, "C", "D", "E", "F", DiskFormatter.GB, "H", "J", DiskFormatter.KB, "L", DiskFormatter.MB, "N", "O", "P", "Q", "R", "S", "T", "W", "X", "Y", "Z"};
    public List<ejq> a;
    public List<ejq> b;
    public List<String> d = new ArrayList();
    List<String> e = new ArrayList();
    public String f;
    private Context g;
    private ejq h;
    private ejq i;
    private int j = 0;
    private boolean k = false;

    public enum JSONParseType {
        PARSE_FROM_LOCAL_FILE,
        PARSE_FROM_SERVER_RESPONSE
    }

    public StationManager(Context context, int i2, boolean z) {
        this.g = context;
        this.j = i2;
        this.k = z;
        this.f = context.getString(R.string.view_more_city_name);
    }

    public final synchronized boolean a(JSONArray jSONArray, JSONParseType jSONParseType) {
        try {
            this.b = new ArrayList();
            Station station = new Station();
            station.name = "暂无定位";
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            li a2 = li.a();
            if (latestPosition != null) {
                if (a2 != null) {
                    lj b2 = a2.b(latestPosition.x, latestPosition.y);
                    if (!(b2 == null || b2.a == null)) {
                        if (!b2.a.isEmpty()) {
                            station = new Station(b2);
                        }
                    }
                }
            }
            ejq ejq = new ejq();
            ejq.a = "定位城市";
            ejq.b = new ArrayList();
            ejq.b.add(station);
            this.b.add(ejq);
            if (jSONParseType == JSONParseType.PARSE_FROM_LOCAL_FILE) {
                this.i = f();
            } else {
                this.i = new ejq();
                this.i.a = "热门城市";
                this.i.b = new ArrayList();
            }
            this.a = a(jSONArray, this.i, jSONParseType);
            this.h = b(this.a);
            boolean d2 = d();
            if (d2) {
                this.b.add(this.h);
            }
            boolean e2 = e();
            if (e2) {
                this.b.add(this.i);
            }
            this.b.addAll(this.a);
            this.e.clear();
            this.e.add("定位城市");
            if (d2) {
                this.e.add("历史城市");
            }
            if (e2) {
                this.e.add("热门城市");
            }
            if (!this.d.isEmpty()) {
                this.e.addAll(this.d);
            } else {
                this.e.addAll(Arrays.asList(c));
            }
        }
        return true;
    }

    public final JSONArray a() {
        byte[] bArr;
        JSONArray jSONArray;
        StationRequestManger.a();
        ejr b2 = StationRequestManger.b(this.j);
        if (b2 != null) {
            bArr = b2.a();
        } else {
            bArr = null;
        }
        if (bArr == null) {
            return null;
        }
        try {
            jSONArray = new JSONArray(new String(bArr));
        } catch (JSONException e2) {
            e2.printStackTrace();
            jSONArray = null;
        }
        return jSONArray;
    }

    private List<ejq> a(JSONArray jSONArray, ejq ejq, JSONParseType jSONParseType) {
        ejq ejq2 = ejq;
        JSONParseType jSONParseType2 = jSONParseType;
        ArrayList arrayList = new ArrayList();
        TreeMap treeMap = new TreeMap(new Comparator<Integer>() {
            public final /* synthetic */ int compare(Object obj, Object obj2) {
                return ((Integer) obj).compareTo((Integer) obj2);
            }
        });
        this.d.clear();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i2);
            String optString = optJSONObject.optString("index");
            if (!TextUtils.isEmpty(optString)) {
                this.d.add(optString.toUpperCase());
                ejq ejq3 = new ejq();
                ejq3.a = optString;
                ejq3.b = new ArrayList();
                JSONArray optJSONArray = optJSONObject.optJSONArray("list");
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    int length = optJSONArray.length();
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    int i3 = 0;
                    int i4 = 0;
                    while (i3 < length) {
                        JSONObject optJSONObject2 = optJSONArray.optJSONObject(i3);
                        if (optJSONObject2 != null) {
                            Station station = new Station(optJSONObject2);
                            int i5 = jSONParseType2 == JSONParseType.PARSE_FROM_LOCAL_FILE ? a(station, ejq2) : optJSONObject2 == null ? 0 : optJSONObject2.optInt("ishotCity");
                            if (i5 > 0) {
                                if (jSONParseType2 == JSONParseType.PARSE_FROM_SERVER_RESPONSE) {
                                    treeMap.put(Integer.valueOf(i5), station);
                                }
                                if (i4 < 5) {
                                    arrayList3.add(station);
                                    i4++;
                                }
                            }
                            arrayList2.add(station);
                        }
                        i3++;
                        JSONArray jSONArray2 = jSONArray;
                    }
                    ejq3.b.addAll(arrayList3);
                    int i6 = 5 - i4;
                    if (i6 > arrayList2.size()) {
                        i6 = arrayList2.size();
                    }
                    while (i6 > 0) {
                        ejq3.b.add((Station) arrayList2.remove(0));
                        i6--;
                    }
                    if (!arrayList2.isEmpty()) {
                        Station station2 = new Station();
                        station2.name = this.f;
                        ejq3.b.add(station2);
                        ejq3.c = arrayList2;
                        ejq3.d = true;
                    }
                }
                arrayList.add(ejq3);
            }
        }
        if (jSONParseType2 == JSONParseType.PARSE_FROM_SERVER_RESPONSE) {
            ejq2.b.addAll(treeMap.values());
        }
        return arrayList;
    }

    private static int a(Station station, ejq ejq) {
        if (ejq == null || ejq.b == null) {
            return 0;
        }
        for (Station next : ejq.b) {
            if (station.name != null && next != null && station.name.equals(next.name)) {
                return 1;
            }
        }
        return 0;
    }

    private ejq b(List<ejq> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String next : b()) {
            String substring = next.substring(0, 1);
            String substring2 = next.substring(1, next.length());
            for (int i2 = 0; i2 < list.size(); i2++) {
                ejq ejq = list.get(i2);
                if (substring.equalsIgnoreCase(ejq.a)) {
                    List<Station> list2 = ejq.b;
                    if (list2 != null && !list2.isEmpty()) {
                        int i3 = 0;
                        while (true) {
                            if (i3 >= list2.size()) {
                                break;
                            } else if (substring2.equals(String.valueOf(list2.get(i3).name))) {
                                arrayList.add(list2.get(i3));
                                break;
                            } else {
                                i3++;
                            }
                        }
                    }
                    List<Station> list3 = ejq.c;
                    if (ejq.d && list3 != null && !list3.isEmpty()) {
                        int i4 = 0;
                        while (true) {
                            if (i4 >= list3.size()) {
                                break;
                            } else if (substring2.equals(String.valueOf(list3.get(i4).name))) {
                                arrayList.add(list3.get(i4));
                                break;
                            } else {
                                i4++;
                            }
                        }
                    }
                }
            }
        }
        ejq ejq2 = new ejq();
        ejq2.a = "历史城市";
        ejq2.b = arrayList;
        return ejq2;
    }

    private ejq f() {
        JSONArray jSONArray;
        StationRequestManger.a();
        ejr b2 = StationRequestManger.b(this.j);
        if (b2 != null) {
            jSONArray = b2.b();
        } else {
            jSONArray = null;
        }
        ejq ejq = new ejq();
        ejq.a = "热门城市";
        ejq.b = new ArrayList();
        if (jSONArray == null) {
            return ejq;
        }
        try {
            int length = jSONArray.length();
            for (int i2 = 0; i2 < length; i2++) {
                ejq.b.add(new Station(jSONArray.getJSONObject(i2)));
            }
        } catch (JSONException unused) {
        }
        return ejq;
    }

    public static List<ejq> a(List<Station> list) {
        ejq ejq = new ejq();
        ejq.a = "";
        ejq.b = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            ejq.b.add(list.get(i2));
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(ejq);
        return arrayList;
    }

    public final List<String> b() {
        SharedPreferences sharedPrefs = new MapSharePreference((String) "SharedPreferences").sharedPrefs();
        StringBuilder sb = new StringBuilder();
        sb.append(c());
        sb.append("history_station");
        String string = sharedPrefs.getString(sb.toString(), "");
        if (TextUtils.isEmpty(string)) {
            return new ArrayList();
        }
        return Arrays.asList(string.split(","));
    }

    public final String c() {
        StringBuilder sb = new StringBuilder();
        if (this.j == 0) {
            sb.append("train_");
        } else if (this.j == 1) {
            sb.append("coach_");
            if (this.k) {
                sb.append("start_");
            } else {
                sb.append("end_");
            }
        }
        return sb.toString();
    }

    public static String a(Station station, List<String> list) {
        if (station == null || list == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(station.index);
        sb.append(station.name);
        String sb2 = sb.toString();
        LinkedList linkedList = new LinkedList(list);
        if (linkedList.contains(sb2)) {
            linkedList.remove(linkedList.indexOf(sb2));
        } else if (list.size() == 9) {
            linkedList.removeLast();
        }
        linkedList.addFirst(sb2);
        StringBuilder sb3 = new StringBuilder();
        for (int i2 = 0; i2 < linkedList.size(); i2++) {
            sb3.append((String) linkedList.get(i2));
            if (i2 != linkedList.size() - 1) {
                sb3.append(",");
            }
        }
        return sb3.toString();
    }

    public final boolean d() {
        return this.h.b != null && !this.h.b.isEmpty();
    }

    public final boolean e() {
        return this.i != null && !this.i.b.isEmpty();
    }
}
