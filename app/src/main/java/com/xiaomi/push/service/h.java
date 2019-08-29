package com.xiaomi.push.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.misc.n;
import com.xiaomi.xmpush.thrift.j;
import com.xiaomi.xmpush.thrift.m;
import com.xiaomi.xmpush.thrift.o;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class h {
    private static volatile h b = null;
    private static String c = "GeoFenceDao.";
    private Context a;

    private h(Context context) {
        this.a = context;
    }

    private synchronized Cursor a(SQLiteDatabase sQLiteDatabase) {
        n.a(false);
        try {
        } catch (Exception unused) {
            return null;
        }
        return sQLiteDatabase.query(BaseIntentDispatcher.INTENT_CALL_OWNER_GEOFENCE, null, null, null, null, null, null);
    }

    public static h a(Context context) {
        if (b == null) {
            synchronized (h.class) {
                try {
                    if (b == null) {
                        b = new h(context);
                    }
                }
            }
        }
        return b;
    }

    private synchronized com.xiaomi.xmpush.thrift.n a(Cursor cursor) {
        com.xiaomi.xmpush.thrift.n[] values;
        try {
            for (com.xiaomi.xmpush.thrift.n nVar : com.xiaomi.xmpush.thrift.n.values()) {
                if (TextUtils.equals(cursor.getString(cursor.getColumnIndex("type")), nVar.name())) {
                    return nVar;
                }
            }
            return null;
        } catch (Exception e) {
            b.d(e.toString());
            return null;
        }
    }

    private synchronized String a(List<o> list) {
        if (list != null) {
            if (list.size() >= 3) {
                JSONArray jSONArray = new JSONArray();
                try {
                    for (o next : list) {
                        if (next != null) {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("point_lantitude", next.c());
                            jSONObject.put("point_longtitude", next.a());
                            jSONArray.put(jSONObject);
                        }
                    }
                    return jSONArray.toString();
                } catch (JSONException e) {
                    b.d(e.toString());
                    return null;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(c);
        sb.append(" points unvalidated");
        b.a(sb.toString());
        return null;
    }

    private synchronized o b(Cursor cursor) {
        o oVar;
        oVar = new o();
        try {
            oVar.b(Double.parseDouble(cursor.getString(cursor.getColumnIndex("center_lantitude"))));
            oVar.a(Double.parseDouble(cursor.getString(cursor.getColumnIndex("center_longtitude"))));
        } catch (Exception e) {
            b.d(e.toString());
            return null;
        }
        return oVar;
    }

    private synchronized ArrayList<o> c(Cursor cursor) {
        ArrayList<o> arrayList;
        arrayList = new ArrayList<>();
        try {
            JSONArray jSONArray = new JSONArray(cursor.getString(cursor.getColumnIndex("polygon_points")));
            for (int i = 0; i < jSONArray.length(); i++) {
                o oVar = new o();
                JSONObject jSONObject = (JSONObject) jSONArray.get(i);
                oVar.b(jSONObject.getDouble("point_lantitude"));
                oVar.a(jSONObject.getDouble("point_longtitude"));
                arrayList.add(oVar);
            }
        } catch (JSONException e) {
            b.d(e.toString());
            return null;
        }
        return arrayList;
    }

    private synchronized j d(Cursor cursor) {
        try {
        } catch (IllegalArgumentException e) {
            b.d(e.toString());
            return null;
        }
        return j.valueOf(cursor.getString(cursor.getColumnIndex("coordinate_provider")));
    }

    public synchronized int a(String str, String str2) {
        n.a(false);
        try {
            if (!"Enter".equals(str2) && !"Leave".equals(str2) && !"Unknown".equals(str2)) {
                return 0;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("current_status", str2);
            int update = i.a(this.a).a().update(BaseIntentDispatcher.INTENT_CALL_OWNER_GEOFENCE, contentValues, "id=?", new String[]{str});
            i.a(this.a).b();
            return update;
        } catch (Exception e) {
            b.d(e.toString());
            return 0;
        }
    }

    public synchronized long a(m mVar) {
        long insert;
        n.a(false);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", mVar.a());
            contentValues.put("appId", Long.valueOf(mVar.e()));
            contentValues.put("name", mVar.c());
            contentValues.put("package_name", mVar.g());
            contentValues.put("create_time", Long.valueOf(mVar.i()));
            contentValues.put("type", mVar.k().name());
            contentValues.put("center_longtitude", String.valueOf(mVar.m().a()));
            contentValues.put("center_lantitude", String.valueOf(mVar.m().c()));
            contentValues.put("circle_radius", Double.valueOf(mVar.o()));
            contentValues.put("polygon_point", a(mVar.q()));
            contentValues.put("coordinate_provider", mVar.s().name());
            contentValues.put("current_status", "Unknown");
            insert = i.a(this.a).a().insert(BaseIntentDispatcher.INTENT_CALL_OWNER_GEOFENCE, null, contentValues);
            i.a(this.a).b();
        } catch (Exception e) {
            b.d(e.toString());
            return -1;
        }
        return insert;
    }

    public synchronized m a(String str) {
        n.a(false);
        try {
            Iterator<m> it = a().iterator();
            while (it.hasNext()) {
                m next = it.next();
                if (TextUtils.equals(next.a(), str)) {
                    return next;
                }
            }
            return null;
        } catch (Exception e) {
            b.d(e.toString());
            return null;
        }
    }

    public synchronized ArrayList<m> a() {
        ArrayList<m> arrayList;
        String sb;
        n.a(false);
        try {
            Cursor a2 = a(i.a(this.a).a());
            arrayList = new ArrayList<>();
            if (a2 != null) {
                while (a2.moveToNext()) {
                    try {
                        m mVar = new m();
                        mVar.a(a2.getString(a2.getColumnIndex("id")));
                        mVar.b(a2.getString(a2.getColumnIndex("name")));
                        mVar.a((long) a2.getInt(a2.getColumnIndex("appId")));
                        mVar.c(a2.getString(a2.getColumnIndex("package_name")));
                        mVar.b((long) a2.getInt(a2.getColumnIndex("create_time")));
                        com.xiaomi.xmpush.thrift.n a3 = a(a2);
                        if (a3 == null) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(c);
                            sb2.append("findAllGeoFencing: geo type null");
                            sb = sb2.toString();
                        } else {
                            mVar.a(a3);
                            if (TextUtils.equals("Circle", a3.name())) {
                                mVar.a(b(a2));
                                mVar.a(a2.getDouble(a2.getColumnIndex("circle_radius")));
                            } else if (TextUtils.equals("Polygon", a3.name())) {
                                ArrayList<o> c2 = c(a2);
                                if (c2 != null) {
                                    if (c2.size() >= 3) {
                                        mVar.a((List<o>) c2);
                                    }
                                }
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(c);
                                sb3.append("findAllGeoFencing: geo points null or size<3");
                                sb = sb3.toString();
                            }
                            j d = d(a2);
                            if (d == null) {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append(c);
                                sb4.append("findAllGeoFencing: geo Coordinate Provider null ");
                                b.c(sb4.toString());
                            } else {
                                mVar.a(d);
                                arrayList.add(mVar);
                            }
                        }
                        b.c(sb);
                    } catch (Exception e) {
                        b.d(e.toString());
                    }
                }
                a2.close();
            }
            i.a(this.a).b();
        } catch (Exception e2) {
            b.d(e2.toString());
            return null;
        }
        return arrayList;
    }

    public synchronized ArrayList<m> b(String str) {
        ArrayList<m> arrayList;
        n.a(false);
        try {
            ArrayList<m> a2 = a();
            arrayList = new ArrayList<>();
            Iterator<m> it = a2.iterator();
            while (it.hasNext()) {
                m next = it.next();
                if (TextUtils.equals(next.g(), str)) {
                    arrayList.add(next);
                }
            }
        } catch (Exception e) {
            b.d(e.toString());
            return null;
        }
        return arrayList;
    }

    public synchronized String c(String str) {
        n.a(false);
        try {
            Cursor a2 = a(i.a(this.a).a());
            if (a2 != null) {
                while (a2.moveToNext()) {
                    if (TextUtils.equals(a2.getString(a2.getColumnIndex("id")), str)) {
                        String string = a2.getString(a2.getColumnIndex("current_status"));
                        StringBuilder sb = new StringBuilder();
                        sb.append(c);
                        sb.append("findGeoStatueByGeoId: geo current statue is ");
                        sb.append(string);
                        sb.append(" geoId:");
                        sb.append(str);
                        b.c(sb.toString());
                        a2.close();
                        return string;
                    }
                }
                a2.close();
            }
            i.a(this.a).b();
            return "Unknown";
        } catch (Exception e) {
            b.d(e.toString());
            return "Unknown";
        }
    }

    public synchronized int d(String str) {
        n.a(false);
        try {
            if (a(str) == null) {
                return 0;
            }
            int delete = i.a(this.a).a().delete(BaseIntentDispatcher.INTENT_CALL_OWNER_GEOFENCE, "id = ?", new String[]{str});
            i.a(this.a).b();
            return delete;
        } catch (Exception e) {
            b.d(e.toString());
            return 0;
        }
    }

    public synchronized int e(String str) {
        n.a(false);
        try {
            if (TextUtils.isEmpty(str)) {
                return 0;
            }
            int delete = i.a(this.a).a().delete(BaseIntentDispatcher.INTENT_CALL_OWNER_GEOFENCE, "package_name = ?", new String[]{str});
            i.a(this.a).b();
            return delete;
        } catch (Exception e) {
            b.d(e.toString());
            return 0;
        }
    }
}
